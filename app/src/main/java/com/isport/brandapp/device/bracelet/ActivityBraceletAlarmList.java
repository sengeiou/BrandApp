package com.isport.brandapp.device.bracelet;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.isport.blelibrary.ISportAgent;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.db.table.watch_w516.Watch_W560_AlarmModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.interfaces.BleReciveListener;
import com.isport.blelibrary.result.IResult;
import com.isport.blelibrary.result.impl.sleep.SleepBatteryResult;
import com.isport.blelibrary.utils.BleRequest;
import com.isport.blelibrary.utils.Constants;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.Utils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.utils.ToastUtil;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.device.bracelet.Utils.RepeatUtils;
import com.isport.brandapp.device.bracelet.braceletPresenter.AlarmPresenter;
import com.isport.brandapp.device.bracelet.view.AlarmView;
import com.isport.brandapp.device.dialog.BaseDialog;
import com.isport.brandapp.util.DeviceTypeUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.ItemView;
import bike.gymproject.viewlibray.pickerview.DatePickerView;
import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack;
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;

/**
 * 闹钟设置
 */
public class ActivityBraceletAlarmList extends BaseMVPTitleActivity<AlarmView, AlarmPresenter> implements AlarmView, View.OnClickListener, ItemView.OnItemViewCheckedChangeListener {
    private final static String TAG = ActivityBraceletAlarmList.class.getSimpleName();
    private DeviceBean deviceBean;
    private int currentType;
    private String deviceId;
    private LinearLayout layoutEmty;
    private AlarmListAdapter adapter;
    private boolean isChange = false;
    private RecyclerView recyclerview_message;

    private int showItemCount = 5;
    ArrayList<Bracelet_W311_AlarmModel> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_wristband_alarm_list;
    }

    @Override
    protected void initView(View view) {
        recyclerview_message = findViewById(R.id.recyclerview_message);
        layoutEmty = findViewById(R.id.layout_emty);

    }


    @Override
    protected void initData() {
        getIntentData();
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(context.getResources().getString(R.string.watch_alarm_setting_str));
        titleBarView.setRightText("");
        titleBarView.setRightIcon(R.drawable.icon_add_device);
        frameBodyLine.setVisibility(View.VISIBLE);


        recyclerview_message.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlarmListAdapter(list, currentType);
        recyclerview_message.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                currentAlarmModel = list.get(position);
                showAddOrEditDiloag(currentAlarmModel.getTimeString());

            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                delDialog(list.get(position));
                return false;
            }
        });
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (!AppConfiguration.isConnected) {

                    ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_disconnect_device));
                    return;
                }
                Bracelet_W311_AlarmModel model = list.get(position);
                boolean curretnOpen = model.getIsOpen();
                model.setIsOpen(!curretnOpen);
                updateItem(model);
            }
        });


        ISportAgent.getInstance().registerListener(mBleReciveListener);
        if (AppConfiguration.isConnected) {
            ISportAgent.getInstance().requsetW81Ble(BleRequest.QUERY_ALAMR_LIST);
        } else {
            //
        }
        mActPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceId);
    }

    public void delDialog(Bracelet_W311_AlarmModel model) {
        PublicAlertDialog.getInstance().showDialog("", this.getString(R.string.ensure_delete), context, this.getString(R.string.common_dialog_cancel), this.getString(R.string.common_dialog_ok), new AlertDialogStateCallBack() {
                    @Override
                    public void determine() {
                        delItem(model);
                    }

                    @Override
                    public void cancel() {

                    }
                }
                , false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //有改变的时候去设置


    }

    private void getIntentData() {

        deviceBean = (DeviceBean) getIntent().getSerializableExtra("deviceBean");
        if (deviceBean != null) {
            currentType = deviceBean.currentType;
            deviceId = deviceBean.deviceName;
        }
        if (DeviceTypeUtil.isContaintW81(currentType)) {
            showItemCount = 3;
        } else {
            showItemCount = 5;
        }
    }


    @Override
    protected void initEvent() {
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
            }

            @Override
            public void onRightClicked(View view) {
                //TODO 跳转到添加闹钟页面
                if (list.size() < showItemCount) {
                    isChange = false;
                    showAddDiloag();


                } else {
                    ToastUtil.showTextToast(context, UIUtils.getString(R.string.only_add_five_alarm));
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ISportAgent.getInstance().unregisterListener(mBleReciveListener);
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
            // Logger.myLog("HandlerContans.mDevcieAlarList2" + mResult);
            if (mResult != null)
                //  Logger.myLog("HandlerContans.mDevcieAlarList2" + mResult.getType());
                switch (mResult.getType()) {
                    case IResult.SLEEP_BATTERY:
                        SleepBatteryResult mResult1 = (SleepBatteryResult) mResult;
                        // TODO: 2018/10/17 更新列表电量
                        break;
                    case IResult.DEVICE_ALARM_LIST:
                        Logger.myLog("HandlerContans.mDevcieAlarList3");
                        if (mHandlerDeviceSetting.hasMessages(0x02)) {
                            mHandlerDeviceSetting.removeMessages(0x02);
                        }
                        if (mHandlerDeviceSetting.hasMessages(0x01)) {
                            mHandlerDeviceSetting.removeMessages(0x01);
                        }
                        mHandlerDeviceSetting.sendEmptyMessageDelayed(0x01, 0);
                        Logger.myLog("DEVICE_ALARM_LIST" + "deviceId:" + deviceId + "TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()):" + TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()));
                        isChange = false;
                        mActPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceId);
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
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onCheckedChanged(int id, boolean isChecked) {
        switch (id) {

        }
    }

    @Override
    protected AlarmPresenter createPresenter() {
        return new AlarmPresenter(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.isChange:
                isChange = true;
                mActPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceId);
                break;
            default:
                break;
        }
    }

    @Override
    public void successAllAlarmItem(ArrayList<Bracelet_W311_AlarmModel> bracelet_w311_displayModel) {


        List<AlarmEntry> listAlarmEntry = new ArrayList<>();
        if (bracelet_w311_displayModel == null || bracelet_w311_displayModel.size() == 0) {
            Log.e("successAllAlarmItem", "bracelet_w311_displayModel=0");
            titleBarView.setRightTextViewStateIsShow(true);
            layoutEmty.setVisibility(View.VISIBLE);
            list.clear();
            adapter.notifyDataSetChanged();
            titleBarView.setRightTextViewStateIsShow(true);

        } else {
            Log.e("successAllAlarmItem", "bracelet_w311_displayModel=" + bracelet_w311_displayModel.size());
            int len = bracelet_w311_displayModel.size();

            if (len == showItemCount) {
                titleBarView.setRightTextViewStateIsShow(false);
            } else {
                titleBarView.setRightTextViewStateIsShow(true);
            }
            for (int i = 0; i < bracelet_w311_displayModel.size(); i++) {
                String split[] = bracelet_w311_displayModel.get(i).getTimeString().split(":");
                int hour = Integer.parseInt(split[0]);
                int min = Integer.parseInt(split[1]);
                listAlarmEntry.add(new AlarmEntry(i, hour, min, (byte) bracelet_w311_displayModel.get(i).getRepeatCount(), bracelet_w311_displayModel.get(i).getIsOpen()));
            }
            layoutEmty.setVisibility(View.GONE);
            list.clear();
            list.addAll(bracelet_w311_displayModel);
            adapter.notifyDataSetChanged();
        }
        Logger.myLog("listAlarmEntry size():" + listAlarmEntry.size() + "");
        for (int i = 0; i < listAlarmEntry.size(); i++) {
            Logger.myLog("listAlarmEntry:" + listAlarmEntry.get(i).toString());
        }
        if (isChange) {
            if (!AppConfiguration.isConnected) {
                ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_disconnect_device));
                return;
            }
            if (DeviceTypeUtil.isContainW81(currentType)) {
                if (mHandlerDeviceSetting.hasMessages(0x02)) {
                    mHandlerDeviceSetting.removeMessages(0x02);
                }
                if (mHandlerDeviceSetting.hasMessages(0x01)) {
                    mHandlerDeviceSetting.removeMessages(0x01);
                }
                mHandlerDeviceSetting.sendEmptyMessageDelayed(0x02, 50);
                mHandlerDeviceSetting.sendEmptyMessageDelayed(0x01, 10000);
            }
            ISportAgent.getInstance().requestBle(BleRequest.bracelet_w311_set_alarm, listAlarmEntry);
            isChange = false;
        }


    }

    @Override
    public void successW560AllAlarmItem(ArrayList<Watch_W560_AlarmModel> watch_w560_alarmModels) {
        // 不作处理
    }

    @Override
    public void successSaveAlarmItem() {
        isChange = true;
        mActPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceId);
    }

    @Override
    public void successDelectAlarmItem() {
       /* if (list.size() == 0) {
            layoutEmty.setVisibility(View.VISIBLE);
        }*/
        isChange = true;
        mActPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceId);
        //ToastUtil.showTextToast(BaseApp.getApp(), "删除成功");
    }


    public void delItem(Object obj) {
        if (obj == null || list == null) {
            return;
        }
        //list.remove(obj);
        if (!AppConfiguration.isConnected) {
            ToastUtil.showTextToast(context, UIUtils.getString(R.string.app_disconnect_device));
            return;
        }
        deleteItem((Bracelet_W311_AlarmModel) obj);
        //删除item

    }


    private String setRepeatStr(int repeat) {
        StringBuilder stringBuilderWeek = new StringBuilder();
        int workeDay = 0;
        int everyDay = 0;
        int wenkend = 0;
        if (repeat == 0) {
            stringBuilderWeek.append(UIUtils.getString(R.string.once) + ",");
        } else {
            byte[] booleanArrayG = Utils.getBooleanArray((byte) repeat);


            if (Utils.byte2Int(booleanArrayG[6]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.mon) + ",");
                workeDay += 1;
                everyDay += 1;
            }
            if (Utils.byte2Int(booleanArrayG[5]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.tue) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[4]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.wed) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[3]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.thu) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[2]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.fri) + ",");
                workeDay += 1;
                everyDay += 1;
            }

            if (Utils.byte2Int(booleanArrayG[1]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.sat) + ",");
                everyDay += 1;
                wenkend += 1;
            }

            if (Utils.byte2Int(booleanArrayG[7]) == 1) {
                stringBuilderWeek.append(UIUtils.getString(R.string.sun) + ",");
                everyDay += 1;
                wenkend += 1;
            }
        }
        // String weekStr = stringBuilderWeek.toString();
        String weekStr = stringBuilderWeek.toString();


        if (everyDay == 7) {
            weekStr = UIUtils.getString(R.string.every_day);
        } else {
            weekStr = stringBuilderWeek.toString();
        }

        if (weekStr.endsWith(",")) {
            weekStr = weekStr.substring(0, weekStr.length() - 1);
        }

        return weekStr;
    }

    public void updateItem(Bracelet_W311_AlarmModel model) {

        for (int i = 0; i < list.size(); i++) {
            Bracelet_W311_AlarmModel tempModle = list.get(i);
            if (tempModle.getId() == model.getId()) {
                model.setRepeatCount(tempModle.getRepeatCount());
                model.setTimeString(tempModle.getTimeString());
            }
        }
        mActPresenter.updateMode(model);

    }

    public void deleteItem(Bracelet_W311_AlarmModel model) {
        mActPresenter.deletArarmItem(model);

    }


    Bracelet_W311_AlarmModel currentAlarmModel;

    private void showReapeat(int repeat) {

        BaseDialog mMenuViewBirth = new BaseDialog.Builder(this)
                .setContentView(R.layout.app_pop_week)
                .fullWidth()
                .setCanceledOnTouchOutside(false)
                .fromBottom(true)
                .setOnClickListener(R.id.tv_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();


        TextView tv_determine = (TextView) mMenuViewBirth.findViewById(R.id.tv_determine);
        final CheckBox cb_sun = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_sun);
        final CheckBox cb_mon = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_mon);
        final CheckBox cb_tue = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_tue);
        final CheckBox cb_wed = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_wed);
        final CheckBox cb_thu = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_thu);
        final CheckBox cb_fri = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_fri);
        final CheckBox cb_sat = (CheckBox) mMenuViewBirth.findViewById(R.id.cb_sat);

        final RelativeLayout layout_sum = mMenuViewBirth.findViewById(R.id.layout_sum);
        final RelativeLayout layout_mon = mMenuViewBirth.findViewById(R.id.layout_mon);
        final RelativeLayout layout_tue = mMenuViewBirth.findViewById(R.id.layout_tue);
        final RelativeLayout layout_web = mMenuViewBirth.findViewById(R.id.layout_wed);
        final RelativeLayout layout_thu = mMenuViewBirth.findViewById(R.id.layout_thu);
        final RelativeLayout layout_fri = mMenuViewBirth.findViewById(R.id.layout_fri);
        final RelativeLayout layout_sat = mMenuViewBirth.findViewById(R.id.layout_sat);

        final ImageView iv_sum = mMenuViewBirth.findViewById(R.id.iv_sun);
        final ImageView iv_mon = mMenuViewBirth.findViewById(R.id.iv_mon);
        final ImageView iv_tue = mMenuViewBirth.findViewById(R.id.iv_tue);
        final ImageView iv_web = mMenuViewBirth.findViewById(R.id.iv_wed);
        final ImageView iv_thu = mMenuViewBirth.findViewById(R.id.iv_thu);
        final ImageView iv_fri = mMenuViewBirth.findViewById(R.id.iv_fri);
        final ImageView iv_sat = mMenuViewBirth.findViewById(R.id.iv_sat);


        byte[] booleanArrayG = Utils.getBooleanArray((byte) repeat);
        cb_sun.setChecked(false);
        cb_mon.setChecked(false);
        cb_tue.setChecked(false);
        cb_wed.setChecked(false);
        cb_thu.setChecked(false);
        cb_fri.setChecked(false);
        cb_sat.setChecked(false);
        Logger.myLog("repeat == " + repeat);
        final StringBuilder stringBuilder = new StringBuilder(booleanArrayG.length);
        for (byte byteChar : booleanArrayG) {
            stringBuilder.append(String.format("%02X ", byteChar));
        }
        Logger.myLog(" booleanArrayG " + stringBuilder.toString());
        if (Utils.byte2Int(booleanArrayG[7]) == 1) {
            cb_sun.setChecked(true);
        }
        if (Utils.byte2Int(booleanArrayG[6]) == 1) {
            cb_mon.setChecked(true);
        }
        if (Utils.byte2Int(booleanArrayG[5]) == 1) {
            cb_tue.setChecked(true);
        }
        if (Utils.byte2Int(booleanArrayG[4]) == 1) {
            cb_wed.setChecked(true);
        }
        if (Utils.byte2Int(booleanArrayG[3]) == 1) {
            cb_thu.setChecked(true);
        }
        if (Utils.byte2Int(booleanArrayG[2]) == 1) {
            cb_fri.setChecked(true);
        }
        if (Utils.byte2Int(booleanArrayG[1]) == 1) {
            cb_sat.setChecked(true);
        }
        showImgView(iv_sum, cb_sun.isChecked());
        showImgView(iv_mon, cb_mon.isChecked());
        showImgView(iv_tue, cb_tue.isChecked());
        showImgView(iv_web, cb_wed.isChecked());
        showImgView(iv_thu, cb_thu.isChecked());
        showImgView(iv_fri, cb_fri.isChecked());
        showImgView(iv_sat, cb_sat.isChecked());
        //展示之前的选择


        layout_fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bFri = cb_fri.isChecked();
                cb_fri.setChecked(!bFri);
                showImgView(iv_fri, cb_fri.isChecked());
                // cb_fri.setChecked(!bFri);

            }
        });
        layout_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cb_mon.setChecked(!cb_mon.isChecked());
                boolean bFri = cb_mon.isChecked();
                cb_mon.setChecked(!bFri);
                showImgView(iv_mon, cb_mon.isChecked());

            }
        });
        layout_sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_sat.setChecked(!cb_sat.isChecked());
                showImgView(iv_sat, cb_sat.isChecked());
            }
        });
        layout_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_sun.setChecked(!cb_sun.isChecked());
                showImgView(iv_sum, cb_sun.isChecked());
            }
        });
        layout_thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_thu.setChecked(!cb_thu.isChecked());
                showImgView(iv_thu, cb_thu.isChecked());
            }
        });

        layout_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_tue.setChecked(!cb_tue.isChecked());
                showImgView(iv_tue, cb_tue.isChecked());
            }
        });

        layout_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_wed.setChecked(!cb_wed.isChecked());
                showImgView(iv_web, cb_wed.isChecked());
            }
        });

        tv_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calculationAgeAndConstellation(datePicker.getTime());
//                localUserChooseBirthday = datePicker.getTime();
                //确定选择，直接返回要发送的指令

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("0");
                if (cb_sat.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                if (cb_fri.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                if (cb_thu.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                if (cb_wed.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                if (cb_tue.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                if (cb_mon.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                if (cb_sun.isChecked()) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }

                int value = Integer.valueOf(stringBuilder.toString(), 2);

                StringBuilder stringBuilderWeek = new StringBuilder();
                if (cb_mon.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.mon) + ",");
                }
                if (cb_tue.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.tue) + ",");
                }

                if (cb_wed.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.wed) + ",");
                }

                if (cb_thu.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.thu) + ",");
                }

                if (cb_fri.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.fri) + ",");
                }

                if (cb_sat.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.sat) + ",");
                }

                if (cb_sun.isChecked()) {
                    stringBuilderWeek.append(UIUtils.getString(R.string.sun) + ",");
                }

                String weekStr = stringBuilderWeek.toString();

                if (weekStr.endsWith(",")) {
                    weekStr = weekStr.substring(0, weekStr.length() - 1);
                }

                //  mActView.get().dataSetSuccess(targetView, type, value + "-" + weekStr);

                String data = value + "-" + weekStr;
                int mRepeat = 0;

                //设置了星期
                if (!TextUtils.isEmpty(data)) {
                    String[] split = data.split("-");
                    if (split.length <= 1) {
                        mRepeat = 0;
                        // settingRepeat.setContentText(RepeatUtils.setRepeat(deviceBean.deviceType,mRepeat));
                        settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
                    }
                    if (split.length >= 1) {

                        mRepeat = Integer.parseInt(split[0]);


                    }
                    if (split.length >= 2) {
                        Logger.myLog("设置mRepeat == " + split[1]);
                        Logger.myLog(split[1]);
                        //settingRepeat.setContentText(split[1]);
                    }

                    //int[] hourAndMin = getHourAndMin();
                    Logger.myLog("设置mRepeat == " + mRepeat);
                    // RepeatUtils.setRepeat(deviceBean.deviceType,mRepeat)
                    settingRepeat.setContentText(RepeatUtils.setRepeat(deviceBean.deviceType, mRepeat));
                    //setRepeatStr(mRepeat);
                } else {
                    mRepeat = 0;
                    settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
                }
                currentAlarmModel.setRepeatCount(mRepeat);
                mMenuViewBirth.dismiss();
            }
        });

    }

    ItemView settingRepeat;

    String editTime;
    int editRepeate;

    private void showAddOrEditDiloag(String itemTimeString) {
        editTime = itemTimeString;
        editRepeate = currentAlarmModel.getRepeatCount();
        BaseDialog dialog = new BaseDialog.Builder(this)
                .setContentView(R.layout.app_activity_bracelet_alarm_setting)
                .fullWidth()
                .setCanceledOnTouchOutside(false)
                .fromBottom(true)
                .setOnClickListener(R.id.iv_watch_alarm_setting_repeat, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (ViewMultiClickUtil.onMultiClick()) {
                                    return;
                                }
                                showReapeat(currentAlarmModel.getRepeatCount());
                                // dialogInterface.cancel();
                            }
                        }
                )
                .setOnClickListener(R.id.tv_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                )
                .show();

        DatePickerView datePicker = dialog.findViewById(R.id.datePicker);

        settingRepeat = dialog.findViewById(R.id.iv_watch_alarm_setting_repeat);
        TextView tvSave = dialog.findViewById(R.id.tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick()) {
                    return;
                }
                Log.e("tvSave", "datePicker.getTime() = " + datePicker.getTime());
                currentAlarmModel.setTimeString(datePicker.getTime());

                if (currentAlarmModel.getTimeString().equals(editTime) && currentAlarmModel.getRepeatCount() == editRepeate) {
                    return;
                }
                currentAlarmModel.setIsOpen(true);
                mActPresenter.updateMode(currentAlarmModel);
                //这里需要去发送指令个硬件
                //ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, currentAlarmModel.getIsOpen(), currentAlarmModel.getRepeatCount(), Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), currentAlarmModel.getAlarmId());
                dialog.dismiss();
            }

        });
        datePicker.setType(3);
        // if (isEdit) {
        datePicker.setDefaultItemAdapter(itemTimeString);
        settingRepeat.setContentText(RepeatUtils.setRepeat(deviceBean.deviceType, currentAlarmModel.getRepeatCount()));
       /* } else {
            datePicker.setDefaultItemAdapter(Constants.defStartTime);
            settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
        }*/
        // alarmPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceName);
        datePicker.setCyclic(false);

    }

    private void showAddDiloag() {
        currentAlarmModel = new Bracelet_W311_AlarmModel();
        BaseDialog dialog = new BaseDialog.Builder(this)
                .setContentView(R.layout.app_activity_bracelet_alarm_setting)
                .fullWidth()
                .setCanceledOnTouchOutside(false)
                .fromBottom(true)
                .setOnClickListener(R.id.iv_watch_alarm_setting_repeat, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                showReapeat(currentAlarmModel.getRepeatCount());
                                // dialogInterface.cancel();
                            }
                        }
                )
                .setOnClickListener(R.id.tv_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                )
                .show();

        DatePickerView datePicker = dialog.findViewById(R.id.datePicker);

        settingRepeat = dialog.findViewById(R.id.iv_watch_alarm_setting_repeat);
        TextView tvSave = dialog.findViewById(R.id.tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tvSave", "datePicker.getTime() = " + datePicker.getTime() + "list" + list.size());
                currentAlarmModel.setTimeString(datePicker.getTime());


                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String time = list.get(i).getTimeString();
                        //  String selecttime = datePicker.getTime();
                        //   Logger.myLog("time:" + time + "selecttime:" + selecttime + "alarmModels.get(i).getRepeatCount():" + alarmModels.get(i).getRepeatCount());

                        if (time.equals(currentAlarmModel.getTimeString()) && list.get(i).getRepeatCount() == currentAlarmModel.getRepeatCount()) {
                            ToastUtil.showTextToast(BaseApp.getApp(), UIUtils.getString(R.string.alarm_tips_reapte));
                            dialog.dismiss();
                            return;
                        }
                    }
                }
                mActPresenter.saveAlarmItem(deviceId, TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), currentAlarmModel.getRepeatCount(), currentAlarmModel.getTimeString());
                //这里需要去发送指令个硬件
                //ISportAgent.getInstance().requestBle(BleRequest.Watch_W516_SET_ALARM, currentAlarmModel.getIsOpen(), currentAlarmModel.getRepeatCount(), Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), currentAlarmModel.getAlarmId());
                dialog.dismiss();
            }

        });
        datePicker.setType(3);
        // datePicker.setDefaultItemAdapter(itemTimeString);
        // settingRepeat.setContentText(RepeatUtils.setRepeat(deviceBean.deviceType, currentAlarmModel.getRepeatCount()));
        datePicker.setDefaultItemAdapter(Constants.defStartTime);
        settingRepeat.setContentText(UIUtils.getString(R.string.only_once));
        // alarmPresenter.getAllAralm(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceName);
        datePicker.setCyclic(false);

    }

    /*private int[] getHourAndMin() {
        String[] split1 = datePicker.getTime().split(":");
        int hour = Integer.parseInt(split1[0]);
        int min = Integer.parseInt(split1[1]);
        int[] result = new int[2];
        result[0] = hour;
        result[1] = min;
        return result;
    }*/

    public void showImgView(ImageView ivImage, boolean isShow) {
      /*  if (isShow) {
            ivImage.setImageResource(R.drawable.sel_week_yes);
        } else {
            ivImage.setImageResource(R.drawable.sel_week_nor);
        }*/
        ivImage.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }


}
