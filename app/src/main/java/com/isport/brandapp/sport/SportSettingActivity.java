package com.isport.brandapp.sport;

import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.isport.brandapp.App;
import com.isport.brandapp.R;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.sport.bean.SportSettingBean;
import com.isport.brandapp.sport.service.Seekbars;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.SportAcacheUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.mvp.BaseMVPTitleActivity;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class SportSettingActivity extends BaseMVPTitleActivity<SportSettingView, SportSettingPresent> implements View.OnClickListener, Seekbars.OnListenCurrentValue {

    ItemView itemviewScreenAwaysON, itemPlayer, itemviewPaceRemind, itemviewHrRemind;
    Seekbars seekbarviewHr, seekbarsPace;
    int sportType;
    SportSettingBean settingBean;
    View viewHr, viewPace;


    @Override
    protected SportSettingPresent createPresenter() {
        return new SportSettingPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_sport_setting;
    }


    @Override
    protected void initView(View view) {
        itemviewScreenAwaysON = view.findViewById(R.id.itemview_screen_aways_on);
        itemPlayer = view.findViewById(R.id.itemview_palyer);


        seekbarviewHr = view.findViewById(R.id.seekbars_hr);


        viewHr = view.findViewById(R.id.view_hr);


        /*paceText = new TextView(this);
        paceText.setTextColor(Color.parseColor("#101D37"));
        paceText.setTextSize(13);*/
        //paceText.setBackgroundResource( R.drawable.icon_progress_tips);
      /*  Drawable drawableLeft = getResources().getDrawable(
                R.drawable.icon_progress_tips);

        paceText.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, drawableLeft);
        paceText.setCompoundDrawablePadding(4);*/



        /* textMovePaceLayout.addView(paceText, layoutParams);
        paceText.layout(0, 20, screenWidth, 35);*/


        itemviewHrRemind = view.findViewById(R.id.itemview_hr_remind);
        itemviewPaceRemind = view.findViewById(R.id.itemview_pace_remind);
        viewPace = view.findViewById(R.id.view_pace);


        seekbarsPace = view.findViewById(R.id.seekbar_pace);


    }

    Handler handler = new Handler();

    @Override
    protected void initData() {

        sportType = getIntent().getIntExtra("sportType", 0);


        switch (sportType) {
            case JkConfiguration.SportType.sportIndoor:
                titleBarView.setTitle(R.string.indoor_set);
                //itemviewPaceRemind.setVisibility(View.GONE);
                //layoutPace.setVisibility(View.GONE);
                //tvTips.setText(UIUtils.getString(R.string.nute_per_km));
                //tvPaceTips.setText(UIUtils.getString(R.string.below_the_pace));
                break;
            case JkConfiguration.SportType.sportOutRuning:
                //itemviewPaceRemind.setVisibility(View.VISIBLE);
                titleBarView.setTitle(R.string.outdoor_set);
                //tvPaceTips.setText(UIUtils.getString(R.string.below_the_pace));
                // tvTips.setText(UIUtils.getString(R.string.nute_per_km));
                break;
            case JkConfiguration.SportType.sportBike:
                titleBarView.setTitle(R.string.icycle_set);
                //layoutPace.setVisibility(View.VISIBLE);
                //tvPaceTips.setText(UIUtils.getString(R.string.below_the_speed));
                // tvTips.setText(UIUtils.getString(R.string.unit_speed));
                break;
            case JkConfiguration.SportType.sportWalk:
                titleBarView.setTitle(R.string.walking_set);
                //itemviewPaceRemind.setVisibility(View.VISIBLE);
                //layoutPace.setVisibility(View.VISIBLE);
                //tvPaceTips.setText(UIUtils.getString(R.string.below_the_pace));
                //  tvTips.setText(UIUtils.getString(R.string.nute_per_km));
                break;
        }

        settingBean = SportAcacheUtil.getSportTypeSetting(sportType);
        if (settingBean != null) {
            itemviewScreenAwaysON.setShowCheckModel(true);
            itemPlayer.setShowCheckModel(true);
            itemviewPaceRemind.setShowCheckModel(true);
            itemviewHrRemind.setShowCheckModel(true);
            //tvTips.setText(settingBean.tips);
            itemviewPaceRemind.showBottomLine(false);

            itemviewScreenAwaysON.setCheckBox(settingBean.isOnScreen);
            itemPlayer.setCheckBox(settingBean.isPlayer);
            itemviewPaceRemind.setCheckBox(settingBean.isPaceRemind);
            itemviewHrRemind.setCheckBox(settingBean.isHrRemind);
            itemviewHrRemind.showBottomLine(!settingBean.isHrRemind);
            viewHr.setVisibility(settingBean.isHrRemind ? View.GONE : View.VISIBLE);
            seekbarviewHr.setNorClick(settingBean.isHrRemind);
            viewPace.setVisibility(settingBean.isPaceRemind ? View.GONE : View.VISIBLE);
            seekbarsPace.setNorClick(settingBean.isPaceRemind);
            // viewHrMind.setVisibility(View.GONE);
            // viewPace.setVisibility(View.GONE);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int paceprgess = 50;
                    try {
                        paceprgess = (int) ((settingBean.currentPaceValue - settingBean.paceMinValue) / ((settingBean.paceMaxValue - settingBean.paceMinValue) / 100.0));
                    } catch (Exception e) {
                        paceprgess = 0;
                    } finally {
                        if (paceprgess == 0) {
                            paceprgess = 1;
                        }
                        seekbarsPace.setProgess(paceprgess);
                        int hrprgess = 0;
                        try {

                            hrprgess = (int) ((settingBean.currentHrValue - settingBean.hrMinValue) / ((settingBean.hrMaxValue - settingBean.hrMinValue) / 100.0));
                        } catch (Exception e) {
                            hrprgess = 0;
                        } finally {
                            if (hrprgess == 0) {
                                hrprgess = 1;
                            }
                            seekbarviewHr.setProgess(hrprgess);
                        }
                    }
                }
            }, 200);

            seekbarsPace.setSettingBean(settingBean, sportType);
            seekbarsPace.setCurrentValue(this);
            seekbarviewHr.setSettingBean(settingBean, sportType);
            seekbarviewHr.setCurrentValue(this);
            if (sportType == JkConfiguration.SportType.sportBike) {
                itemviewPaceRemind.setTitleText(UIUtils.getString(R.string.speed_remind));
                seekbarsPace.setTips();
            }
        }


        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {

                SportAcacheUtil.SaveSportTypeSetting(settingBean, sportType);
                finish();
            }

            @Override
            public void onRightClicked(View view) {

            }
        });


    }

    @Override
    protected void initEvent() {

        //viewHrMind.setOnClickListener(this);
        viewPace.setOnClickListener(this);
        itemPlayer.setOnCheckedChangeListener(new ItemView.OnItemViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int id, boolean isChecked) {
                settingBean.isPlayer = isChecked;

            }
        });
        itemviewScreenAwaysON.setOnCheckedChangeListener(new ItemView.OnItemViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int id, boolean isChecked) {
                settingBean.isOnScreen = isChecked;

            }
        });
        viewHr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewPace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        itemviewHrRemind.setOnCheckedChangeListener(new ItemView.OnItemViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int id, boolean isChecked) {
                if (isChecked) {
                    viewHr.setVisibility(View.GONE);
                    seekbarviewHr.setNorClick(true);
                } else {
                    viewHr.setVisibility(View.VISIBLE);
                    seekbarviewHr.setNorClick(false);

                }
                settingBean.isHrRemind = isChecked;


            }
        });
        itemviewPaceRemind.setOnCheckedChangeListener(new ItemView.OnItemViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int id, boolean isChecked) {
                if (isChecked) {
                    viewPace.setVisibility(View.GONE);
                    seekbarsPace.setNorClick(true);
                } else {
                    viewPace.setVisibility(View.VISIBLE);
                    seekbarsPace.setNorClick(false);

                }
                settingBean.isPaceRemind = isChecked;
            }
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }

    @Override
    public void backeCurrentValue(Integer value, int currenttype) {

        if (currenttype == 0) {
            settingBean.currentPaceValue = value;
        } else if (currenttype == 1) {
            settingBean.currentHrValue = value;
        }
    }

    @Override
    public void onBackPressed() {
        SportAcacheUtil.SaveSportTypeSetting(settingBean, sportType);
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.NEED_LOGIN:
                ToastUtils.showToast(context, UIUtils.getString(com.isport.brandapp.basicres.R.string.login_again));
                NetProgressObservable.getInstance().hide();
                BleProgressObservable.getInstance().hide();
                TokenUtil.getInstance().clear(context);
                UserAcacheUtil.clearAll();
                AppSP.putBoolean(context, AppSP.CAN_RECONNECT, false);
                App.initAppState();
                Intent intent = new Intent(context, ActivityLogin.class);
                context.startActivity(intent);
                ActivityManager.getInstance().finishAllActivity(ActivityLogin.class.getSimpleName());
                break;
            default:
                break;
        }
    }

}
