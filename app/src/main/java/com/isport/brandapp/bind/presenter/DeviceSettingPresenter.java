package com.isport.brandapp.bind.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.isport.blelibrary.db.action.BleAction;
import com.isport.blelibrary.db.action.sleep.Sleep_Sleepace_DataModelAction;
import com.isport.blelibrary.db.table.sleep.Sleep_Sleepace_DataModel;
import com.isport.blelibrary.gen.Sleep_Sleepace_DataModelDao;
import com.isport.blelibrary.utils.SyncCacheUtils;
import com.isport.brandapp.App;
import com.isport.brandapp.home.bean.http.UpdateWatchResultBean;
import com.isport.brandapp.home.bean.http.Wristbandstep;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.bind.bean.BindInsertOrUpdateBean;
import com.isport.brandapp.bind.bean.UpdatSleepClockTime;
import com.isport.brandapp.bind.view.DeviceSettingView;
import com.isport.brandapp.device.UpdateSuccessBean;
import com.isport.brandapp.device.history.util.HistoryParmUtil;
import com.isport.brandapp.parm.db.AutoSleepTimeParms;
import com.isport.brandapp.parm.db.DeviceIdParms;
import com.isport.brandapp.parm.db.DeviceTypeParms;
import brandapp.isport.com.basicres.entry.bean.BaseParms;
import com.isport.brandapp.parm.http.SleepSetTimeParms;
import com.isport.brandapp.repository.MainResposition;
import com.isport.brandapp.repository.SleepRepository;
import com.isport.brandapp.repository.UpdateResposition;
import com.isport.brandapp.repository.WatchRepository;
import com.isport.brandapp.util.InitCommonParms;
import com.isport.brandapp.util.RequestCode;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import bike.gymproject.viewlibray.pickerview.ArrayPickerView;
import bike.gymproject.viewlibray.pickerview.DatePickerView;
import brandapp.isport.com.basicres.BaseApp;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonbean.BaseUrl;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.Logger;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

/**
 * @Author
 * @Date 2018/10/17
 * @Fuction
 */

public class DeviceSettingPresenter extends BasePresenter<DeviceSettingView> implements ArrayPickerView
        .ItemSelectedValue {

    private DeviceSettingView view;
    private int mCurrentSleepIndex;
    private List<Sleep_Sleepace_DataModel> mSleep_Sleepace_DataModel;

    public DeviceSettingPresenter(DeviceSettingView view) {
        this.view = view;
    }

    public void unBind(DeviceBean deviceBean) {

        UpdateResposition<Integer, BindInsertOrUpdateBean, BaseUrl, DeviceTypeParms> customRepository = new UpdateResposition();
        customRepository.update(HistoryParmUtil.setDevice(deviceBean)).as(view.bindAutoDispose()).subscribe(new BaseObserver<Integer>(context) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                NetProgressObservable.getInstance().hide();
                SyncCacheUtils.setUnBindState(false);
            }

            @Override
            public void onNext(Integer s) {
                SyncCacheUtils.setUnBindState(false);
                NetProgressObservable.getInstance().hide();
                brandapp.isport.com.basicres.commonutil.Logger.e("解绑成功");
                mActView.get().onUnBindSuccess();
                if (deviceBean.deviceType == JkConfiguration.DeviceType.SLEEP) {
                    brandapp.isport.com.basicres.commonutil.Logger.e("睡眠带解绑成功");
                    TokenUtil.getInstance().saveSleepTime(BaseApp.getApp(), "");
                }


            }
        });

    }

    public void findClockTime(String deviceMac, String deviceID) {
        MainResposition<UpdatSleepClockTime, BaseParms, BaseUrl, DeviceIdParms> mainResposition = new MainResposition<>();
        InitCommonParms<BaseParms, BaseUrl, DeviceIdParms> initCommonParms = new InitCommonParms<>();
        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.SLEEPBELT;
        baseUrl.url2 = JkConfiguration.Url.SLEEPBELTCONFIG;
        baseUrl.url3 = JkConfiguration.Url.SELECTBYCONDITION;
        baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());
        ;
        DeviceIdParms dbPar = new DeviceIdParms();
        dbPar.deviceId = deviceID;
        dbPar.requestCode = RequestCode.Request_getClockTime;
        mainResposition.requst(initCommonParms.setPostBody(!(App.appType() == App.httpType)).setBaseDbParms(dbPar).setBaseUrl(baseUrl)
                .setType(JkConfiguration.RequstType.SLEEP_GET_CLOCK_TIME).getPostBody())
                .as(view.bindAutoDispose()).subscribe(new BaseObserver<UpdatSleepClockTime>(context) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                com.isport.blelibrary.utils.Logger.myLog("ResponeThrowable == " + e.toString());
                if (e.message.startsWith("操作成功")) {
                    if (isViewAttached()) {
                        NetProgressObservable.getInstance().hide();
                        mActView.get().getClockTimeSuccess("");
                    }
                }
            }

            @Override
            public void onNext(UpdatSleepClockTime clockTimeBean) {
                NetProgressObservable.getInstance().hide();
                if (isViewAttached()) {
                    mActView.get().setClockTimeSuccess();
                }
                com.isport.blelibrary.utils.Logger.myLog("onNext000");
                String str = clockTimeBean == null ? UIUtils.getString(R.string.app_no_setting) : StringUtil.isBlank
                        (clockTimeBean
                                .getClockTime()) ? UIUtils.getString(R.string.app_no_setting) : clockTimeBean
                        .getClockTime();
                com.isport.blelibrary.utils.Logger.myLog("onNext000" + str);
                if (clockTimeBean != null && !StringUtil.isBlank(str)) {
                    TokenUtil.getInstance().saveSleepTime(BaseApp.getApp(), clockTimeBean.getClockTime());
                }
                if (isViewAttached()) {
                    mActView.get().getClockTimeSuccess(str);
                }
            }
        });


    }

    public void setClockTime(String clockTime, DeviceBean deviceBean) {

        UpdateResposition<String, SleepSetTimeParms, BaseUrl, AutoSleepTimeParms> updateResposition = new
                UpdateResposition<>();

        InitCommonParms<SleepSetTimeParms, BaseUrl, AutoSleepTimeParms> initCommonParms = new InitCommonParms<>();
        SleepSetTimeParms sleepSetTimeParms = new SleepSetTimeParms();
        sleepSetTimeParms.clockTime = clockTime;
        sleepSetTimeParms.mac = deviceBean.deviceID;
        sleepSetTimeParms.deviceId = deviceBean.deviceID;
        sleepSetTimeParms.devicetType = deviceBean.deviceType;
        sleepSetTimeParms.userId = Integer.parseInt(TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()));
        BaseUrl baseUrl = new BaseUrl();
        baseUrl.url1 = JkConfiguration.Url.SLEEPBELT;
        baseUrl.url2 = JkConfiguration.Url.SLEEPBELTCONFIG;
        baseUrl.userid = TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp());
        ;
        AutoSleepTimeParms baseDbParms = new AutoSleepTimeParms();
        baseDbParms.requestCode = RequestCode.Request_setClockTime;
        baseDbParms.clockTime = clockTime;

        updateResposition.update(initCommonParms.setPostBody(!(App.appType() == App.httpType)).setBaseDbParms(baseDbParms).setParms
                (sleepSetTimeParms).setBaseUrl(baseUrl).setType(JkConfiguration.RequstType.SLEEP_SET_CLOCK_TIME).getPostBody())
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<String>(context) {
                    @Override
                    protected void hideDialog() {

                    }

                    @Override
                    protected void showDialog() {

                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        com.isport.blelibrary.utils.Logger.myLog("ResponeThrowable == " + e.toString());
                        if (e.message.startsWith("操作成功")) {
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_CLOCKTIME_SUCCESS));
                            if (isViewAttached()) {
                                NetProgressObservable.getInstance().hide();
                                com.isport.blelibrary.utils.Logger.myLog("设置自动睡眠时间成功-去发消息结束页面，更新设置页");
                                mActView.get().setClockTimeSuccess();
                            }
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        NetProgressObservable.getInstance().hide();
                        EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_CLOCKTIME_SUCCESS));
                        if (isViewAttached()) {
                            com.isport.blelibrary.utils.Logger.myLog("设置自动睡眠时间成功-去发消息结束页面，更新设置页");
                            mActView.get().setClockTimeSuccess();
                        }
                    }
                });


    }

    public void updateSportData(Wristbandstep mWristbandstep, DeviceBean deviceBean) {
        //scanModel.updateSportData(mWristbandstep, deviceBean);


        WatchRepository.requst(mWristbandstep).as(view.bindAutoDispose()).subscribe(new BaseObserver<UpdateWatchResultBean>(context) {
            @Override
            protected void hideDialog() {

            }

            @Override
            protected void showDialog() {

            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                // ToastUtils.showToast(context, e.message);
            }

            @Override
            public void onNext(UpdateWatchResultBean updateWatchResultBean) {
                NetProgressObservable.getInstance().hide();
                if (isViewAttached()) {
                    mActView.get().updateWatchDataSuccess(deviceBean);
                }
            }
        });


    }

    public void updateSleepHistoryData(DeviceBean deviceBean) {
        if (!(App.appType() == App.httpType)) {
            if (isViewAttached()) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_SLEEP_DATA_SUCCESS));
                mActView.get().updateSleepDataSuccess(deviceBean);
            }
        } else {
            List<Sleep_Sleepace_DataModel> sleep_sleepace_dataModels =
                    Sleep_Sleepace_DataModelAction
                            .findSleep_Sleepace_DataModelByDeviceIdAndTimeTamp1
                                    (TokenUtil.getInstance().getPeopleIdStr(BaseApp.getApp()),
                                            App.getSleepBindTime());
            com.isport.blelibrary.utils.Logger.myLog("getSleepBindTime == " + App.getSleepBindTime());
            if (sleep_sleepace_dataModels != null) {
                mSleep_Sleepace_DataModel = sleep_sleepace_dataModels;
                mCurrentSleepIndex = 0;
                updateSleepHistory(deviceBean);
            } else {
                if (isViewAttached())
                    mActView.get().updateSleepDataSuccess(deviceBean);
            }
        }
        //model.updateSleepHistoryData(sleepHistoryDataResult, name);
    }

    private void updateSleepHistory(DeviceBean deviceBean) {
        Sleep_Sleepace_DataModel sleep_sleepace_dataModel = mSleep_Sleepace_DataModel.get(mCurrentSleepIndex);
        SleepRepository.requst(sleep_sleepace_dataModel)
                .as(view.bindAutoDispose())
                .subscribe(new BaseObserver<UpdateSuccessBean>(context) {
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
                    public void onNext(UpdateSuccessBean updateSleepReportBean) {
                        NetProgressObservable.getInstance().hide();
                        if (isViewAttached()) {
                            Sleep_Sleepace_DataModelDao sleep_sleepace_dataModelDao = BleAction
                                    .getSleep_Sleepace_DataModelDao();
                            sleep_sleepace_dataModel.setReportId( updateSleepReportBean.getPublicId());
                            sleep_sleepace_dataModelDao.update(sleep_sleepace_dataModel);
                            mCurrentSleepIndex++;
                            if (mCurrentSleepIndex > mSleep_Sleepace_DataModel.size() - 1) {
                                EventBus.getDefault().post(new MessageEvent(MessageEvent
                                        .UPDATE_SLEEP_DATA_SUCCESS));
                                mActView.get().updateSleepDataSuccess(deviceBean);
                            } else {
                                updateSleepHistory(deviceBean);
                            }
                        }
                    }
                });
    }

    private View mMenuViewBirth;
    private PopupWindow popupWindowBirth;
    // 时间选择器

    public void setPopupWindow(Context context, View view, final String type, String defaultDay) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuViewBirth = inflater.inflate(R.layout.app_pop_date, null);
        TextView tv_determine = (TextView) mMenuViewBirth.findViewById(R.id.tv_determine);
        TextView tv_cancel = (TextView) mMenuViewBirth.findViewById(R.id.tv_cancel);
        final DatePickerView datePicker = (DatePickerView) mMenuViewBirth.findViewById(R.id.datePicker);
        if (type.equals("3")) {
            datePicker.setType(3);
        }
        datePicker.setDefaultItemAdapter(defaultDay);
        datePicker.setCyclic(false);
        popupWindowBirth = new PopupWindow(context);
        popupWindowBirth.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindowBirth.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindowBirth.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //popupWindowBirth.setHeight(DisplayUtils.dip2px(context, 150));
        popupWindowBirth.setContentView(mMenuViewBirth);
        popupWindowBirth.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindowBirth.setOutsideTouchable(false);
        popupWindowBirth.setFocusable(true);
        popupWindowBirth.setAnimationStyle(R.style.popwin_anim_style);
        popupWindowBirth.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        mMenuViewBirth.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuViewBirth.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindowBirth.dismiss();
                    }
                }
                return true;
            }
        });

        tv_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calculationAgeAndConstellation(datePicker.getTime());
//                localUserChooseBirthday = datePicker.getTime();
                if (isViewAttached()) {
                    mActView.get().dataSetSuccess(type, datePicker.getTime());
                }
                popupWindowBirth.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowBirth.dismiss();
            }
        });
    }


    private View mMenuView;
    private String localData[];
    private String localDataNoUnit[];
    private String screenUpDatas[] = new String[]{"开启", "关闭"};
    //1 每隔2000一个选项，最小2000，最大16000
    private String stepData[] = new String[9]; // 步数
    private String stepDataNoUnit[] = new String[9]; // 步数不带单位
    //每隔5秒一个选项，最小5秒，最大30秒
    private String timeData[] = new String[6];    // 时间
    private String timeDataNoUnit[] = new String[6];    // 时间不带单位
    private PopupWindow popupWindow;
    private String localChooseStr;
    private String selectType;
    private int showIndex;

    /**
     * @param context
     * @param view
     * @param type
     * @param lastData 上次的数据
     */
    public void popWindowSelect(Context context, View view, String type, String lastData, boolean isCyclic) {
        this.selectType = type;
        switch (type) {
            case JkConfiguration.Band.STEP_SCREEN_UP:
                localData = screenUpDatas;
                localDataNoUnit = screenUpDatas;
                for (int i = 0; i < screenUpDatas.length; i++) {

                    if (screenUpDatas[i].equals(lastData)) {
                        showIndex = i;
                    }
                }
                break;
            //1 每隔2000一个选项，最小4000，最大20000
            case JkConfiguration.Band.STEP_GOAL:
                for (int j = 0; j < stepDataNoUnit.length; j++) {
                    stepDataNoUnit[j] = String.valueOf((j + 2) * 2000);
                    stepData[j] = (j + 2) * 2000 + " 步";
                    if (stepData[j].equals(lastData)) {
                        showIndex = j;
                    }
                }
                localData = stepData;
                localDataNoUnit = stepDataNoUnit;
                break;
            //每隔5秒一个选项，最小5秒，最大30秒
            case JkConfiguration.Band.STEP_SCREEN_TIME:
                for (int j = 0; j < timeDataNoUnit.length; j++) {
                    timeDataNoUnit[j] = String.valueOf((j + 1) * 5);
                    timeData[j] = (j + 1) * 5 + " 秒";
                    if (timeData[j].equals(lastData)) {
                        showIndex = j;
                    }
                }
                localData = timeData;
                localDataNoUnit = timeDataNoUnit;
                break;
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.app_pop_bottom_setting, null);
        TextView tv_determine = (TextView) mMenuView.findViewById(R.id.tv_determine);
        TextView tv_cancel = (TextView) mMenuView.findViewById(R.id.tv_cancel);
        ArrayPickerView datePicker = (ArrayPickerView) mMenuView.findViewById(R.id.datePicker);

        datePicker.setData(localData);
        datePicker.setCyclic(isCyclic);
        datePicker.setItemOnclick(this);
        datePicker.setSelectItem(showIndex);
        /* qp_select_bottom.setOnValueChangedListener(this);
        qp_select_bottom
                .setDescendantFocusability(QNumberPickerBlackLine.FOCUS_BLOCK_DESCENDANTS);
        qp_select_bottom.setDisplayedValues(localData);
        qp_select_bottom.setMinValue(0);
        qp_select_bottom.setMaxValue(localData.length - 1);
        qp_select_bottom.setValue(showIndex);
        qp_select_bottom.setWrapSelectorWheel(false);
        qp_select_bottom.setNumberPickerDividerColor(qp_select_bottom);*/
        localChooseStr = localData[showIndex];
//        localChooseStr = localDataNoUnit[showIndex];
        popupWindow = new PopupWindow(context);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(mMenuView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.showAtLocation(view, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {

                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });

        tv_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isViewAttached()) {
                    mActView.get().dataSetSuccess(selectType, localChooseStr);
                }
                Logger.e("tag", localChooseStr);
                popupWindow.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onItemSelectedValue(String str) {
        localChooseStr = str;
    }
}
