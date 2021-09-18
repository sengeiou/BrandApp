package com.isport.brandapp.device.bracelet;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.w811w814.FaceWatchMode;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.sleep.SleepBatteryResult;
import com.isport.blelibrary.result.impl.watch.WatchFACEResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.braceletPresenter.WatchFacesSetPresenter;
import com.isport.brandapp.device.bracelet.view.WatchFacesSetView;

import java.util.ArrayList;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * 表盘设置
 */
public class ActivityWatchFacesSet extends BaseMVPTitleActivity<WatchFacesSetView, WatchFacesSetPresenter> implements WatchFacesSetView {

    ImageView checkBoxDef, checkBoxOther, checkBoxThree, checkBoxFour;

    String userId, devcieId;
    int currentType;

    ArrayList<ImageView> checkBoxes;


    private int currentSelect;


    ImageView iv_face_1, iv_face_2, iv_face_3, iv_face_4;

    private DeviceBean deviceBean;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_dial_setting_new;
    }

    @Override
    protected void initView(View view) {
        checkBoxDef = view.findViewById(R.id.checkbox_def);
        checkBoxOther = view.findViewById(R.id.checkbox_ohter);
        checkBoxThree = view.findViewById(R.id.checkbox_3);
        checkBoxFour = view.findViewById(R.id.checkbox_21def);
        iv_face_1 = view.findViewById(R.id.iv_face_1);
        iv_face_2 = view.findViewById(R.id.iv_face_2);
        iv_face_3 = view.findViewById(R.id.iv_face_3);
        iv_face_4 = view.findViewById(R.id.iv_face_21);


    }


    public void setPic(int currentType) {
        switch (currentType) {
            case JkConfiguration.DeviceType.Watch_W812B:
            case JkConfiguration.DeviceType.Watch_W556:
            case JkConfiguration.DeviceType.Watch_W557:
            case JkConfiguration.DeviceType.Watch_W560B:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w526_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w526_3);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w526_2);
                break;
            case JkConfiguration.DeviceType.Watch_W560: // xuqian
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w560_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w560_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w560_34);
                break;
            case JkConfiguration.DeviceType.Watch_W819:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w819_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w819_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w819_3);
                break;
            case JkConfiguration.DeviceType.Watch_W910:

                LoadImageUtil.getInstance().loadCirc(this, R.drawable.icon_watch_face_w910_2, iv_face_1, 20);
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w910_2);
                if (App.isZh(BaseApp.getApp())) {
                    iv_face_2.setImageResource(R.drawable.icon_watch_face_w910_1_ch);
                } else {
                    iv_face_2.setImageResource(R.drawable.icon_watch_face_w910_1_en);
                }
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w910_3);
                break;
            case JkConfiguration.DeviceType.Watch_W813:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w813_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w813_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w813_3);
                break;
            case JkConfiguration.DeviceType.Brand_W814:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w814_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w814_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w814_3);
                checkBoxThree.setVisibility(View.VISIBLE);
                break;
            case JkConfiguration.DeviceType.Watch_W812:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w812_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w812_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w812_3);
                checkBoxThree.setVisibility(View.VISIBLE);
                break;
            case JkConfiguration.DeviceType.Brand_W520:
                iv_face_1.setImageResource(R.drawable.icon_watchfaces_w520_1);
                iv_face_2.setImageResource(R.drawable.icon_watchfaces_w520_2);
                iv_face_3.setImageResource(R.drawable.icon_watchfaces_w520_3);
                checkBoxThree.setVisibility(View.GONE);
                break;
            case JkConfiguration.DeviceType.BRAND_W307J:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w307j_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w307j_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w307j_4);
                iv_face_4.setImageResource(R.drawable.icon_watch_face_w307j_3);
                checkBoxThree.setVisibility(View.VISIBLE);
                break;
            case JkConfiguration.DeviceType.Watch_W817:
                iv_face_1.setImageResource(R.drawable.icon_watch_face_w817_1);
                iv_face_2.setImageResource(R.drawable.icon_watch_face_w817_2);
                iv_face_3.setImageResource(R.drawable.icon_watch_face_w817_3);
                checkBoxThree.setVisibility(View.VISIBLE);
                break;
        }

      /*  LoadImageUtil.getInstance().loadCirc(this, res1, iv_face_1, 20);
        LoadImageUtil.getInstance().loadCirc(this, res2, iv_face_2, 20);
        LoadImageUtil.getInstance().loadCirc(this, res3, iv_face_3, 20);*/
    }


    @Override
    protected void initData() {
        getIntentData();
        ISportAgent.getInstance().registerListener(mBleReciveListener);
        if (AppConfiguration.isConnected) {
            ISportAgent.getInstance().requsetW81Ble(BleRequest.QUERY_WATCH_FACE);
        } else {
            //

        }
        mActPresenter.getWatchFace(userId, devcieId);


        checkBoxes = new ArrayList<>();
        checkBoxes.add(checkBoxDef);
        checkBoxes.add(checkBoxOther);
        checkBoxes.add(checkBoxThree);

        if (currentType == JkConfiguration.DeviceType.BRAND_W307J) {
            checkBoxFour.setVisibility(View.VISIBLE);
            iv_face_4.setVisibility(View.VISIBLE);
            checkBoxes.add(checkBoxFour);
        } else {
            checkBoxFour.setVisibility(View.GONE);
            iv_face_4.setVisibility(View.GONE);
        }

        setPic(currentType);
        titleBarView.setTitle(UIUtils.getString(R.string.watch_dial_is_set));
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });
    }

    private void getIntentData() {
        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
        userId = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());
        if (deviceBean != null) {
            devcieId = deviceBean.deviceID;
            currentType = deviceBean.currentType;
        }
    }

    private BleReciveListener mBleReciveListener = new BleReciveListener() {
        @Override
        public void onConnResult(boolean isConn, boolean isConnectByUser, BaseDevice baseDevice) {

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
                    case IResult.DEVICE_WATCH_FACE:

                        WatchFACEResult result = (WatchFACEResult) mResult;
                        setCheckBoxState(result.getWatchFaceIndex());


                        Logger.myLog("DEVICE_WATCH_FACE 333  DEVICE_WATCH_FACE");

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

        }
    };

    @Override
    protected void initEvent() {
        iv_face_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 1) {
                    return;
                }
                setCheckBoxState(1);
                sendCmd(1);
            }
        });
        checkBoxDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 1) {
                    return;
                }
                setCheckBoxState(1);
                sendCmd(1);
            }
        });
        iv_face_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 2) {
                    return;
                }
                setCheckBoxState(2);
                sendCmd(2);
            }
        });
        checkBoxOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 2) {
                    return;
                }
                setCheckBoxState(2);
                sendCmd(2);
            }
        });
        iv_face_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 3 || checkBoxThree.getVisibility() == View.GONE) {
                    return;
                }
                setCheckBoxState(3);
                sendCmd(3);
            }
        });
        checkBoxThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 3) {
                    return;
                }
                setCheckBoxState(3);
                sendCmd(3);
            }
        });
        iv_face_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 4 || checkBoxFour.getVisibility() == View.GONE) {
                    return;
                }
                setCheckBoxState(4);
                sendCmd(4);
            }
        });
        checkBoxFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelect == 4) {
                    return;
                }
                setCheckBoxState(4);
                sendCmd(4);
            }
        });


    }

    @Override
    protected void initHeader() {

    }


    private void sendCmd(int isDef) {
        if (!AppConfiguration.isConnected) {
            ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_disconnect_device));
            return;
        }
        if (!AppConfiguration.hasSynced) {
            ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_syncing));
            return;
        }
       /* if (true) {
            ToastUtil.showTextToast(context, UIUtils.getString(R.string.function));
            return;
        }*/
        currentSelect = isDef;
        mActPresenter.saveWatchFace(devcieId, userId, isDef);
        ISportAgent.getInstance().requestBle(BleRequest.watch_faces_setting, isDef);

    }

    @Override
    protected WatchFacesSetPresenter createPresenter() {
        return new WatchFacesSetPresenter(this);
    }


    //w811-814 的值为 1，2，3
    @Override
    public void successGetWatchFacesMode(FaceWatchMode faceWatchMode) {
        Logger.myLog("ActivityWatchFacesSet:" + faceWatchMode.toString());
        int index = faceWatchMode.getFaceWatchMode();
        currentSelect = index;
        setCheckBoxState(index);
    }


    public void setCheckBoxState(int index) {
        Log.e("index", "index=" + index + "checkBoxes.size()=" + checkBoxes.size());
        if (index >= 0 && index <= checkBoxes.size()) {
            for (int i = 0; i < checkBoxes.size(); i++) {
                checkBoxes.get(i).setVisibility(View.INVISIBLE);
            }
            checkBoxes.get(index - 1).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void successSaveWatchFaceMode() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
        //ISportAgent.getInstance().registerListener(mBleReciveListener);
    }
}
