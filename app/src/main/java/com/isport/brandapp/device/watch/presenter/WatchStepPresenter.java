package com.isport.brandapp.device.watch.presenter;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.Home.bean.http.Wristbandstep;
import com.isport.brandapp.device.watch.view.WatchStepView;
import com.isport.brandapp.device.watch.watchModel.IW516Model;
import com.isport.brandapp.device.watch.watchModel.W516Model;

import java.util.ArrayList;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonnet.interceptor.BaseObserver;
import brandapp.isport.com.basicres.commonnet.interceptor.ExceptionHandle;
import brandapp.isport.com.basicres.commonutil.StringUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.entry.WatchTargetBean;
import brandapp.isport.com.basicres.mvp.BasePresenter;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WatchStepPresenter extends BasePresenter<WatchStepView> {//implements ArrayPickerView.ItemSelectedValue
    private WatchStepView view;
    IW516Model iw516Model;

    public WatchStepPresenter(WatchStepView view) {
        this.view = view;
        iw516Model = new W516Model();
    }

    /**
     * 获取有步数的天
     *
     * @param strDate
     */
    public void getMonthDataStrDate(String strDate) {
        Observable.create(new ObservableOnSubscribe<ArrayList<String>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<String>> emitter) throws Exception {
                ArrayList<String> strDates = iw516Model.getMonthData(strDate);
                emitter.onNext(strDates);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new Consumer<ArrayList<String>>() {
            @Override
            public void accept(ArrayList<String> wristbandstep) throws Exception {
                NetProgressObservable.getInstance().hide();
                if (view != null) {
                    view.successMonthDate(wristbandstep);
                }
            }
        });

    }

    public void getWatchDayData(String strDate) {
        Observable.create(new ObservableOnSubscribe<Wristbandstep>() {
            @Override
            public void subscribe(ObservableEmitter<Wristbandstep> emitter) throws Exception {
               // AppConfiguration.watchID = "1";
                Wristbandstep wristbandstep = iw516Model.getWatchDayData(strDate);
                emitter.onNext(wristbandstep);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new BaseObserver<Wristbandstep>(BaseApp.getApp()) {
            @Override
            public void onNext(Wristbandstep wristbandstep) {
                if (view != null) {
                   /* wristbandstep = new Wristbandstep();
                    wristbandstep.setStepKm("23");
                    wristbandstep.setCalorie("25");
                    wristbandstep.setStepNum("220");
                    wristbandstep.setSumFat("0.25");
                    wristbandstep.setSumGasoline("56");
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < 24; i++) {
                        arrayList.add(i + 200);
                    }
                    wristbandstep.setStepArry(arrayList);*/
                    view.successLastSportsummary(wristbandstep);
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
                Wristbandstep wristbandstep = new Wristbandstep();
                /*wristbandstep.setStepKm("23");
                wristbandstep.setCalorie("25");
                wristbandstep.setStepNum("220");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < 24; i++) {
                    arrayList.add(i + 200);
                }
                wristbandstep.setStepArry(arrayList);*/
                if (view != null) {
                    view.successLastSportsummary(wristbandstep);
                }

            }

        });

    }

    public void getWatchTargetSteps(String deviceId, String userId) {
        Observable.create(new ObservableOnSubscribe<WatchTargetBean>() {
            @Override
            public void subscribe(ObservableEmitter<WatchTargetBean> emitter) throws Exception {
                WatchTargetBean wristbandstep = iw516Model.getWatchTargetStep(deviceId, userId);
                if (wristbandstep == null) {
                    wristbandstep = new WatchTargetBean();
                }
                emitter.onNext(wristbandstep);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new Consumer<WatchTargetBean>() {
            @Override
            public void accept(WatchTargetBean wristbandstep) throws Exception {
                NetProgressObservable.getInstance().hide();
                if (view != null) {
                    view.successTargetStep(wristbandstep);
                }
            }
        });

    }

    //获取近30天的数据
    public void getLastMonthData(String deviceId) {
        Observable.create(new ObservableOnSubscribe<Wristbandstep>() {
            @Override
            public void subscribe(ObservableEmitter<Wristbandstep> emitter) throws Exception {
                Wristbandstep wristbandstep = iw516Model.getWatchLastMonthData(TokenUtil.getInstance().getPeopleIdInt(BaseApp.getApp()), deviceId);
                emitter.onNext(wristbandstep);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new Consumer<Wristbandstep>() {
            @Override
            public void accept(Wristbandstep wristbandstep) throws Exception {
                NetProgressObservable.getInstance().hide();
                if (view != null) {
                    view.succcessLastMontData(wristbandstep.avgStep, wristbandstep.avgDis, wristbandstep.sumGasoline, wristbandstep.sumFat);
                }
            }
        });
    }


    public void getLastSpprtData() {

        Observable.create(new ObservableOnSubscribe<Wristbandstep>() {
            @Override
            public void subscribe(ObservableEmitter<Wristbandstep> emitter) throws Exception {
                Wristbandstep wristbandstep = iw516Model.getLastSprotData();
                emitter.onNext(wristbandstep);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).as(view.bindAutoDispose()).subscribe(new Consumer<Wristbandstep>() {
            @Override
            public void accept(Wristbandstep wristbandstep) throws Exception {
                NetProgressObservable.getInstance().hide();
                if (view != null) {
                    if (wristbandstep == null) {
                        view.successLastSportsummary(null);
                    } else {
                        //未连接进入时，展示历史数据,连接状态下展示实时数据
                        if (StringUtil.isBlank(wristbandstep.getStrDate()) || (AppConfiguration.isConnected && TimeUtils.isToday(wristbandstep.getStrDate()))) {
                            view.successLastSportsummary(null);
                        } else {
                            view.successLastSportsummary(wristbandstep);
                        }
                    }
                }
            }
        });
    }


    public void calStepToCalAndKM(long currentStep) {
        Wristbandstep wristbandstep = iw516Model.calStepToKmAndCal(currentStep);
        if (view != null) {
            view.successLastSportsummary(wristbandstep);
        }
    }

//
//    private View mMenuView;
//    private String localData[];
//    private String localDataNoUnit[];
//    private String stepData[] = new String[101]; // 步数
//    private String stepDataNoUnit[] = new String[101];
//    private PopupWindow popupWindow;
//    private String localChooseStr;
//    private String selectType;
//    private int showIndex;
//
//    /**
//     * @param context
//     * @param view
//     * @param type
//     * @param lastData 上次的数据
//     */
//    public void popWindowSelect(Context context, View view, String type, String lastData, boolean isCyclic) {
//        this.selectType = type;
//        switch (type) {
//
//            case JkConfiguration.GymUserInfo.STEP:
//                for (int n = 1; n <= 5; n++) {
//                    int stepTemp=2000*n;
//                    stepDataNoUnit[n] = String.valueOf(stepTemp);
//                    stepData[n] = stepTemp + " 步";
//                    if (stepData[n].equals(lastData)) {
//                        showIndex = n;
//                    }
//                }
//                localData = stepData;
//                localDataNoUnit = stepDataNoUnit;
//                break;
//        }
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.app_pop_bottom_setting, null);
//        TextView tv_determine = (TextView) mMenuView.findViewById(R.id.tv_determine);
//        TextView tv_cancel = (TextView) mMenuView.findViewById(R.id.tv_cancel);
//        ArrayPickerView datePicker = (ArrayPickerView) mMenuView.findViewById(R.id.datePicker);
//
//        datePicker.setData(localData);
//        datePicker.setCyclic(isCyclic);
//        datePicker.setItemOnclick(this);
//        datePicker.setSelectItem(showIndex);
//        localChooseStr = localData[showIndex];
//        popupWindow = new PopupWindow(context);
//        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setContentView(mMenuView);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        popupWindow.setOutsideTouchable(false);
//        popupWindow.setFocusable(true);
//        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
//        popupWindow.showAtLocation(view, Gravity.BOTTOM
//                | Gravity.CENTER_HORIZONTAL, 0, 0);
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        popupWindow.dismiss();
//                    }
//                }
//                return true;
//            }
//        });
//
//        tv_determine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isViewAttached()) {
//                    mActView.get().dataSetSuccess(selectType, localChooseStr);
//                }
//                Logger.e("tag", localChooseStr);
//                popupWindow.dismiss();
//            }
//        });
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//    }
//
//    @Override
//    public void onItemSelectedValue(String str) {
//        localChooseStr = str;
//    }
}
