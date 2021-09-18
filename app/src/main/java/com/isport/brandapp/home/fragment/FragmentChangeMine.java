package com.isport.brandapp.home.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.action.DeviceInformationTableAction;
import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_DeviceInfoModelAction;
import com.isport.blelibrary.db.table.DeviceInformationTable;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DeviceInfoModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.sleep.SleepBatteryResult;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.LogActivity;
import com.isport.brandapp.R;
import com.isport.brandapp.Third_party_access.util.GoogleFitUtil;
import com.isport.brandapp.banner.recycleView.RefrushRecycleView;
import com.isport.brandapp.banner.recycleView.adapter.RefrushAdapter;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;
import com.isport.brandapp.banner.recycleView.inter.DefaultAdapterViewLisenter;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.bind.ActivityDeviceSetting;
import com.isport.brandapp.device.bracelet.ActivityBraceletMain;
import com.isport.brandapp.device.watch.ActivityWatchMain;
import com.isport.brandapp.home.AccessThridAppActivity;
import com.isport.brandapp.home.SettingActivity;
import com.isport.brandapp.home.presenter.FragmentMinePresenter;
import com.isport.brandapp.home.view.DeviceHolder;
import com.isport.brandapp.home.view.FragmentMineView;
import com.isport.brandapp.home.view.ItemHolder;
import com.isport.brandapp.home.view.MineFooterHolder;
import com.isport.brandapp.home.view.MineHeaderHolder;
import com.isport.brandapp.home.view.MyRankingHolder;
import com.isport.brandapp.home.view.MyThridHolder;
import com.isport.brandapp.login.ActivitySettingUserInfo;
import com.isport.brandapp.message.MessageActivity;
import com.isport.brandapp.util.ActivitySwitcher;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceTypeUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import bike.gymproject.viewlibray.FriendItemView;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonbean.CommonFriendRelation;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonnet.net.RxScheduler;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.entry.bean.PhotoEventBean;
import brandapp.isport.com.basicres.mvp.BaseMVPFragment;
import brandapp.isport.com.basicres.mvp.NetworkBoundResource;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.net.userNet.CommonUserPresenter;
import brandapp.isport.com.basicres.net.userNet.CommonUserView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.activity.FansActivity;
import phone.gym.jkcq.com.socialmodule.activity.FollowActivity;
import phone.gym.jkcq.com.socialmodule.activity.FriendActivity;
import phone.gym.jkcq.com.socialmodule.activity.FriendScanActivity;
import phone.gym.jkcq.com.socialmodule.activity.PersonalHomepageActivity;
import phone.gym.jkcq.com.socialmodule.activity.SportRankActivity;
import phone.gym.jkcq.com.socialmodule.manager.QRCodeManager;
import phone.gym.jkcq.com.socialmodule.net.APIService;
import phone.gym.jkcq.com.socialmodule.net.RetrofitClient;
import phone.gym.jkcq.com.socialmodule.personal.ShowImageActivity;

/**
 * Created by huashao on 2017/10/24.
 */
public class FragmentChangeMine extends BaseMVPFragment<FragmentMineView, FragmentMinePresenter> implements View
        .OnClickListener, FragmentMineView, MineHeaderHolder.OnHeadOnclickLister, MineFooterHolder
        .OnFooterOnclickLister, DeviceHolder.OnDeviceItemClickListener, DeviceHolder.OnDeviceOnclickListenter, MyThridHolder.OnThridItemClickListener, CommonUserView, MyRankingHolder.onRankingLister {

    CommonUserPresenter commonUserPresenter;
    private QRCodeManager mQRCodeManager;

    RefrushRecycleView refrushRecycleView;

    private RefrushAdapter<String> adapter;
    private TextView tvName;
    RoundImageView ivHead;
    ImageView iv_mine_left;
    private ImageView iv_mine_message;
    private TextView tv_message_count;
    private ImageView ivMineScan;

    private MineFooterHolder mineFooterHolder;
    private DeviceHolder mineItemHolder;
    private MyThridHolder myThridHolder;
    private MyRankingHolder myRankingHolder;
    //int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 123456; //whatever you want
    NestedScrollView nestedScroll;

    private int mMessageCounts = 0;
    private RelativeLayout layout_head;
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;

    private UserInfoBean user;

    private Vector<Integer> lists;
    private FriendItemView tv_friend, tv_follow, tv_fans, tv_feed;
    ArrayList<DeviceBean> list = new ArrayList<>();


    @Override
    protected FragmentMinePresenter createPersenter() {
        commonUserPresenter = new CommonUserPresenter(this);
        return new FragmentMinePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.myLog("Fragment Mine onResume");
        getNetWork();
        BaseDevice device = ISportAgent.getInstance().getCurrnetDevice();
        if (device != null) {
            Log.e("quest", device.deviceType + ",connected:" + AppConfiguration.isConnected);
            getDeviceList(device.deviceType, AppConfiguration.isConnected);
        }
    }


    public void getNetWork() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                boolean isA = NetworkUtils.isAvailable();
                emitter.onNext(isA);
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(this.bindAutoDispose()).subscribe(new BaseObserver<Boolean>(BaseApp.getApp(), false) {
            @Override
            public void onNext(Boolean tempInfo) {
                if (!tempInfo) {
                    ToastUtils.showToast(context, UIUtils.getString(R.string.common_please_check_that_your_network_is_connected));
                    return;
                }
                commonUserPresenter.getUserinfo(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                commonUserPresenter.getUserFriendRelation(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

        });

    }


    private boolean isHidden;

    long lastClickTime = 0;
    long currentClickTime = 0;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);


        Log.e("succcessDynamList", "onHiddenChanged" + hidden);
        isHidden = hidden;
        if (!isHidden) {
            currentClickTime = System.currentTimeMillis();
            if (lastClickTime == 0) {
                lastClickTime = System.currentTimeMillis();
                quest();
            } else {
                if (currentClickTime - lastClickTime > 10 * 1000) {
                    lastClickTime = currentClickTime;
                    quest();
                }
            }


        }
        BaseDevice device = ISportAgent.getInstance().getCurrnetDevice();
        if (device != null) {
            Log.e("quest", device.deviceType + ",connected:" + AppConfiguration.isConnected);
            getDeviceList(device.deviceType, AppConfiguration.isConnected);
        }
        //nestedScroll.scrollTo(0,0);
    }

    public void quest() {
        getNetWork();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_fragment_change_mine;
    }

    @Override
    protected void initView(View view) {
        Logger.myLog("Fragment Mine initView");
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        tv_message_count = view.findViewById(R.id.tv_message_count);
        nestedScroll = view.findViewById(R.id.nestedScroll);
        layout_head = view.findViewById(R.id.layout_head);
        refrushRecycleView = view.findViewById(R.id.rc_home);

        ivHead = view.findViewById(R.id.iv_head);
        tvName = view.findViewById(R.id.tv_name);
        ivMineScan = view.findViewById(R.id.iv_mine_scan);
        iv_mine_left = view.findViewById(R.id.iv_mine_left);
        tv_feed = view.findViewById(R.id.tv_feed);
        tv_fans = view.findViewById(R.id.tv_fans);
        tv_follow = view.findViewById(R.id.tv_follow);
        tv_friend = view.findViewById(R.id.tv_friend);
        //tvEdit = view.findViewById(R.id.tv_edit);
        iv_mine_message = view.findViewById(R.id.iv_mine_message);

        lists = new Vector<>();

        //lists.add(JkConfiguration.BODY_HEADER);
        lists.add(JkConfiguration.BODY_DEVICE);
        lists.add(JkConfiguration.BODY_HEARTRATE);
        // if (!App.getApp().isCN()) {
        lists.add(JkConfiguration.BODY_DEVICE1);
        // }
//        lists.add(JkConfiguration.BODY_END);

        adapter = new RefrushAdapter<>(getActivity(), lists, R.layout.item, new DefaultAdapterViewLisenter() {
            @Override
            public CustomHolder getBodyHolder(Context context, List lists, int itemID) {
                return new ItemHolder(context, lists, R.layout.app_fragment_mine_title);
            }

            @Override
            public CustomHolder getHeader(Context context, List lists, int itemID) {

                return null;
            }

            @Override
            public CustomHolder getFooter(Context context, List lists, int itemID) {
                mineFooterHolder = new MineFooterHolder(context, lists, R.layout.app_fragment_mine_footer);
                mineFooterHolder.setOnCourseOnclickLister(FragmentChangeMine.this);
                return mineFooterHolder;
            }

            @Override
            public CustomHolder getConectHeartRate(Context context, List lists, int itemID) {
                myRankingHolder = new MyRankingHolder(context, lists, R.layout.app_mine_ranking);
                myRankingHolder.setOnItemClickListener(FragmentChangeMine.this);
                return myRankingHolder;
                //  return super.getConectHeartRate(context, lists, itemID);
            }

            @Override
            public CustomHolder getConect(Context context, List lists, int itemID) {
                mineItemHolder = new DeviceHolder(context, lists, R.layout.app_mine_item_layout);
                // functionHolder.setOnItemClickListener(NewNewFragmentMain.this);
                mineItemHolder.setOnItemClickListener(FragmentChangeMine.this);
                mineItemHolder.setOnCourseOnclickLister(FragmentChangeMine.this);
                notifyDeviceList(context);
                return mineItemHolder;
            }

            @Override
            public CustomHolder getConect1(Context context, List lists, int itemID) {
                myThridHolder = new MyThridHolder(context, lists, R.layout.app_mine_thrid_item_layout);
                myThridHolder.setOnItemClickListener(FragmentChangeMine.this);
                return myThridHolder;
            }
        });
        //adapter.addHead(mineHeaderHolder);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        refrushRecycleView.setLayoutManager(manager);
        refrushRecycleView.setAdapter(adapter);
    }

    private void notifyDeviceList(Context context) {
        Logger.myLog("Fragment Mine notifyDeviceList:");
        int currentDeviceType = AppSP.getInt(context, AppSP.DEVICE_CURRENTDEVICETYPE, JkConfiguration
                .DeviceType.WATCH_W516);
        getDeviceList(currentDeviceType, AppConfiguration.isConnected);


    }


    private synchronized void getDeviceList(int deviceType, boolean connectstate) {


        Observable.create(new ObservableOnSubscribe<ArrayList<DeviceBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<DeviceBean>> emitter) throws Exception {

                ArrayList<DeviceBean> deviceBeans = new ArrayList<>();
                if (AppConfiguration.deviceBeanList != null) {
                    for (int deviceTypeI : AppConfiguration.deviceBeanList.keySet()) {
                        DeviceBean deviceBean = AppConfiguration.deviceBeanList.get(deviceTypeI);
                        int battery = getVersionOrBattery(deviceBean.deviceType, deviceBean.deviceName);
                        // Logger.myLog("fragmentMine getDeviceList:" + battery + "---------" + deviceBean);
                        BaseDevice currentBase = ISportAgent.getInstance().getCurrnetDevice();

                        if (currentBase == null) {
                            Logger.myLog("FragmentChangeMine 断开状态");
                            deviceBean.connectState = false;
                            deviceBean.battery = battery;
                        } else {
                            if (deviceType == deviceBean.deviceType && currentBase.deviceType == deviceType && connectstate) {
                                Logger.myLog("FragmentChangeMine 连接状态");
                                deviceBean.connectState = true;
                                deviceBean.battery = battery;
                            } else if (deviceType == deviceBean.deviceType && currentBase.deviceType == deviceType && !connectstate) {
                                Logger.myLog("FragmentChangeMine 断开状态");
                                deviceBean.connectState = false;
                                deviceBean.battery = battery;
                            } else {
                                Logger.myLog("FragmentChangeMine 断开状态");
                                deviceBean.connectState = false;
                                deviceBean.battery = battery;
                            }
                        }
                        Logger.myLog("FragmentChangeMine: getDeviceList" + deviceBean);
                        deviceBeans.add(deviceBean);
                    }
                }
                emitter.onNext(deviceBeans);
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<ArrayList<DeviceBean>>(BaseApp.getApp(), false) {
            @Override
            public void onNext(ArrayList<DeviceBean> list) {
                if (mineItemHolder != null) {
                    mineItemHolder.notifyList(list);
                }
            }

            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

        });


    }

    private int getVersionOrBattery(int deviceType, String deviceName) {
        //是W516的设备信息 w81的版本信息存在一起
        if (DeviceTypeUtil.isContainWatch(deviceType) || DeviceTypeUtil.isContaintW81(deviceType)) {
            DeviceInformationTable deviceInfoByDeviceId = DeviceInformationTableAction.findDeviceInfoByDeviceId
                    (deviceName);
            if (deviceInfoByDeviceId != null) {
                Logger.myLog("getVersionOrBattery:" + deviceInfoByDeviceId);
                return deviceInfoByDeviceId.getBattery();
                //getDeviceBattery(deviceType, deviceInfoByDeviceId.getBattery());
            }
        } else if (DeviceTypeUtil.isContainWrishBrand(deviceType)) {

            Bracelet_W311_DeviceInfoModel model = Bracelet_W311_DeviceInfoModelAction.findBraceletW311DeviceInfo(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceName);

            Logger.myLog("getVersionOrBattery:" + model);
            if (model != null) {
                return model.getPowerLevel();
            }
        }
        return 0;
    }


    @Override
    protected void initEvent() {
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        refrushRecycleView.setFocusableInTouchMode(false); //设置不需要焦点
        refrushRecycleView.requestFocus(); //设置焦点不需要
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonalHomepageActivity.class);
                startActivity(intent);
            }
        });
        iv_mine_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonalHomepageActivity.class);
                startActivity(intent);
            }
        });
        tv_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonalHomepageActivity.class);
                startActivity(intent);
            }
        });
        tv_fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), FansActivity.class);
                intent.putExtra(FriendConstant.USER_ID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                startActivity(intent);
            }
        });
        tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), FollowActivity.class);
                intent.putExtra(FriendConstant.USER_ID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                startActivity(intent);
            }
        });
        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                Intent intent = new Intent(getActivity(), FriendActivity.class);
                intent.putExtra(FriendConstant.USER_ID, TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
                startActivity(intent);
            }
        });

        ivMineScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }

                new RxPermissions(getActivity())
                        .request(Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    startActivity(new Intent(getActivity(), FriendScanActivity.class));
                                } else {
                                    com.blankj.utilcode.util.ToastUtils.showLong(R.string.please_open_camera_permission);
                                }
                            }
                        });

//                mQRCodeManager.scanQRCode(getActivity());
//                mQRCodeManager.setOnScanResultListener(new QRCodeManager.OnScanResultListener() {
//                    @Override
//                    public void onScanResult(String result) {
//                        getUserIdByQrString(result);
//                    }
//                });
            }
        });
        /*tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditOnclick();
            }
        });*/
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                //  startActivity(new Intent(context,FriendSearchActivity.class));
                Intent intent = new Intent(getActivity(), PersonalHomepageActivity.class);
                startActivity(intent);
            }
        });

     /*   layout_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserInfo.class);
                startActivity(intent);
            }
        });*/

        iv_mine_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });
    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {


            Logger.myLog("FragmentChangeMine: onConnResult" + isConn + ",baseDevice:" + baseDevice);

            int deviceType = 0;
            if (baseDevice != null) {
                deviceType = baseDevice.deviceType;
            }

            if (Constants.isDFU) {
                if (!isConn)
                    getDeviceList(deviceType, isConn);
                return;
            }
            if (AppConfiguration.deviceBeanList != null && !AppConfiguration.deviceBeanList.containsKey(deviceType)) {
                return;
            }
            getDeviceList(deviceType, isConn);
        }

        @Override
        public void setDataSuccess(String s) {

        }

        @Override
        public void receiveData(IResult mResult) {
            if (mResult != null)
                switch (mResult.getType()) {
                    case IResult.SLEEP_BATTERY:
                        SleepBatteryResult mResult1 = (SleepBatteryResult) mResult;
                        // TODO: 2018/10/17 更新列表电量
                        break;
                    default:
                        break;
                }
        }

        @Override
        public void onConnecting(BaseDevice baseDevice) {

        }

        @Override
        public void onBattreyOrVersion(BaseDevice baseDevice) {
            Logger.myLog("FragmentChangeMine: onConnResult onBattreyOrVersion baseDevice:" + baseDevice + "ISportAgent.getInstance().getCurrnetDevice():" + ISportAgent.getInstance().getCurrnetDevice());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try {
                        if (baseDevice != null) {
                            int type = baseDevice.deviceType;
                            BaseDevice baseDevice1 = ISportAgent.getInstance().getCurrnetDevice();
                            int devicetype = JkConfiguration.DeviceType.WATCH_W516;
                            if (baseDevice1 != null) {
                                devicetype = baseDevice1.deviceType;
                            }
                            if (type == devicetype) {
                                getDeviceList(type, AppConfiguration.isConnected);
                            } else {
                                getDeviceList(type, false);
                            }
                        }

                    } catch (Exception e) {


                    }
                }
            }, 1000);

        }
    };

    Handler handler = new Handler();

    @Override
    protected void initData() {
        mQRCodeManager = new QRCodeManager(getActivity());
        // mFragPresenter.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(app));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case JkConfiguration.requestCode.personInformation://TODO 如果有信息修改,则重新获取基本数据
                if (resultCode == JkConfiguration.resultCode.personInformation) {
                    mFragPresenter.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(app));
                }
                break;
            case JkConfiguration.requestCode.changePic:
                break;
            case REQUEST_OAUTH_REQUEST_CODE: {
                Logger.myLog("onActivityResult：");
                GoogleFitUtil.insertData(getActivity());
                break;
            }
            default:
                //handleSignInResult(requestCode, data);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.GET_BIND_DEVICELIST_SUCCESS:
            case MessageEvent.WHEN_CONNECTSTATE_CHANGED:
                if (mineItemHolder != null) {
                    notifyDeviceList(context);
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(PhotoEventBean messageEvent) {
        Log.e("CustomRepository 111", messageEvent.toString());
        updateData(messageEvent.bean.headUrl);
        user.setHeadUrl(messageEvent.bean.headUrl);
        user.setHeadUrlTiny(messageEvent.bean.headUrlTiny);
        AppConfiguration.saveUserInfo(user);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
    }


    @Override
    public void successGetUserInfo(UserInfoBean userInfo) {
        if (userInfo != null) {
            Log.e("CustomRepository 000", userInfo.toString());
            updateData(userInfo);
            user = userInfo;
        }

    }

    public void updateData(String headUrl) {
        LoadImageUtil.getInstance().loadCirc(context, headUrl, ivHead);
    }

    public void updateData(UserInfoBean userInfo) {
        updateData(userInfo.getNickName(), userInfo.getHeadUrl());
    }

    public void updateData(String name, String headUrl) {
        tvName.setText(name);
        if (App.appType() == App.httpType) {
            LoadImageUtil.getInstance().loadCirc(context, headUrl, ivHead);
        } else {
            if (!TextUtils.isEmpty(headUrl)) {
                Log.e("CustomRepository  222", headUrl);
                ivHead.setImageBitmap(BitmapFactory.decodeFile(headUrl));
            }
            //LoadImageUtil.getInstance().displayImagePath(context, headUrl, ivHead);
        }
    }


    @Override
    public void postDeviceList() {

    }

    @Override
    public void onSettingOnclick() {
//        ISportAgent.getInstance().requestBle(BleRequest.WATCH_W516_TESTDATA);
        Intent activitySetting = new Intent(getActivity(), SettingActivity.class);
        startActivity(activitySetting);
//        ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_SN_NORMALMODE,10000000);
//        EventBus.getDefault().post(new MessageEvent(MessageEvent.NEED_LOGIN));
    }

    @Override
    public void onEditOnclick() {
//        startActivity(new Intent(getActivity(), BPResultActivity.class));
        Intent activityBindMobilePhone = new Intent(getActivity(), ActivitySettingUserInfo.class);
        activityBindMobilePhone.putExtra(JkConfiguration.COME_FROM, JkConfiguration.COME_FROM_MAIN);
        startActivityForResult(activityBindMobilePhone, JkConfiguration.requestCode.personInformation);
    }

    @Override
    public void onHeadOnclick() {
        //  Intent intent = new Intent(context, ActivityImageShow.class);
        Intent intent = new Intent(context, ShowImageActivity.class);
//        ArrayList<String> arrayList = new ArrayList<>();
        if (user != null) {
//            arrayList.add(user.getHeadUrl());
            intent.putExtra("pic_list", user.getHeadUrl());
            startActivity(intent);
        } else {
            UserInfoBean loginBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(context));
            //ToastUtil.showTextToast(getContext(), "请检查网络");
            if (loginBean != null) {
//                arrayList.add(loginBean.getHeadUrl());
                intent.putExtra("pic_list", loginBean.getHeadUrl());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onHealthItemOnclick() {
       /* Intent intent = new Intent(context, HealthIndexDetail.class);
        startActivity(intent);*/
        // Intent intentSleep = new Intent(context, WebActivity.class);
        //intentSleep.putExtra("mSleepBel", null);


        //Intent intent = new Intent(context, WebActivity.class);
        // String userid = "1";//TokenUtil.getInstance().getPeopleId(BaseApp.getApp())
        //  String url = "http://192.168.10.15/healthManager/#/health?uid=" + userid;
        // intent.putExtra(BaseWebActivity.EXTRA_URL, url);

        // intentSleep.putExtra(BaseWebActivity.EXTRA_URL, url);
        // intentSleep.putExtra(BaseWebActivity.EXTRA_USERID, userid);
        //startActivity(intentSleep);
    }

    @Override
    public void onAddDeviceOnclick() {
        ActivitySwitcher.goBindAct(context);
    }

    int countLog = 0;

    @Override
    public void onLog() {
        countLog++;
        if (countLog == 5) {
            Intent intent = new Intent(context, LogActivity.class);
            startActivity(intent);
            countLog = 0;
        }
    }


    @Override
    public void onDeviceItemListener(int position, DeviceBean bean) {

        Logger.myLog("onDeviceItemListener" + System.currentTimeMillis());

        if (!bean.connectState) {
            ToastUtils.showToast(context, R.string.app_disconnect_device);
            return;
        }
        if (DeviceTypeUtil.isContainWatch(bean.currentType)) {
            if (AppConfiguration.hasSynced) {
                Intent intent2 = new Intent(context, ActivityWatchMain.class);
                bean.mac = ISportAgent.getInstance().getCurrnetDevice().address;
                intent2.putExtra(JkConfiguration.DEVICE, bean);
                startActivity(intent2);
            } else {
                ToastUtils.showToast(context, R.string.sync_data);
            }
        } else if (DeviceTypeUtil.isContainWrishBrand(bean.currentType) || DeviceTypeUtil.isContaintW81(bean.currentType)) {
            Logger.myLog("onDeviceItemListener11" + System.currentTimeMillis());
            if (AppConfiguration.hasSynced) {
                Intent intent2 = new Intent(context, ActivityBraceletMain.class);
                intent2.putExtra(JkConfiguration.DEVICE, bean);
                startActivity(intent2);
            } else {
                ToastUtils.showToast(context, R.string.sync_data);
            }
        } else {
            Intent intent = new Intent(context, ActivityDeviceSetting.class);
            bean.mac = ISportAgent.getInstance().getCurrnetDevice().address;
            intent.putExtra(JkConfiguration.DEVICE, bean);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onThirdDeviceItemListener() {

        Intent intent = new Intent(getActivity(), AccessThridAppActivity.class);
        startActivity(intent);
       /* Intent intent = new Intent(getActivity(), PlayW311Activity.class);
        startActivity(intent);*/
        //connectGoogle();

    }

    @Override
    public void onDevcieUpgrade() {


        PublicAlertDialog.getInstance().showDialog(UIUtils.getString(R.string.fir_upgrade_title), UIUtils.getString(R.string.fir_upgrade_content), context, getResources().getString(R.string.cancel), getResources().getString(R.string.confirm), new AlertDialogStateCallBack() {
            @Override
            public void determine() {
                Constants.CAN_RECONNECT = false;
                ISportAgent.getInstance().disConDevice(false);
                ActivitySwitcher.goScanActivty(getActivity(), JkConfiguration.DeviceType.DFU);
            }

            @Override
            public void cancel() {

            }
        }, false);


    }

    @Override
    public void onDevcieSetting() {
        onSettingOnclick();
    }


    @Override
    public void onSuccessUserInfo(UserInfoBean userInfo) {
        if (userInfo != null) {
            Log.e("CustomRepository 000", userInfo.toString());
            updateData(userInfo);
            user = userInfo;
        }
    }

    int followState, fans, friend, follow;

    @Override
    public void onSuccessUserFriendRelation(CommonFriendRelation commonFriendRelation) {
        if (commonFriendRelation != null) {
            followState = commonFriendRelation.getFollowStatus();
            fans = commonFriendRelation.getFansNums();
            friend = commonFriendRelation.getFriendNums();
            follow = commonFriendRelation.getFollowNums();
            tv_follow.setValue(follow);
            tv_fans.setValue(fans);
            tv_friend.setValue(friend);
            tv_feed.setValue(commonFriendRelation.getTrendsNums());
        }


    }


    private void getUserIdByQrString(String qrString) {

        new NetworkBoundResource<String>() {
            @Override
            public Observable<String> getFromDb() {
                return null;
            }

            @Override
            public Observable<String> getNoCacheData() {
                return null;
            }

            @Override
            public boolean shouldFetchRemoteSource() {
                return false;
            }

            @Override
            public boolean shouldStandAlone() {
                return false;
            }

            @Override
            public Observable<String> getRemoteSource() {
                return RetrofitClient.getRetrofit().create(APIService.class).getUserIdByQrString(qrString).compose
                        (RxScheduler.Obs_io_main()).compose(RetrofitClient.transformer);

            }

            @Override
            public void saveRemoteSource(String bean) {

            }
        }.getAsObservable().subscribe(new BaseObserver<String>(BaseApp.getApp()) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {

            }

            @Override
            public void onNext(String info) {

                if (info != null) {
                    Intent intent = new Intent(getActivity(), PersonalHomepageActivity.class);
                    intent.putExtra(FriendConstant.USER_ID, info);
                    startActivity(intent);
                }

            }
        });
        ;
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).titleBar(R.id.layout_head)
                .init();
    }

    @Override
    public void onRankingItem() {
        startActivity(new Intent(getActivity(), SportRankActivity.class));
    }

    public void setmMessageCount(int count) {
        mMessageCounts = count;
        if (tv_message_count != null) {

            if (mMessageCounts > 0 && mMessageCounts <= 99) {
                tv_message_count.setVisibility(View.VISIBLE);
                tv_message_count.setText("" + mMessageCounts);
            } else if (mMessageCounts > 99) {
                mMessageCounts = 99;
                tv_message_count.setVisibility(View.VISIBLE);
                tv_message_count.setText("" + mMessageCounts + "+");
            } else {
                tv_message_count.setVisibility(View.INVISIBLE);
            }
        }
    }
}
