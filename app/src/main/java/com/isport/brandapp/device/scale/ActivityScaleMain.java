package com.isport.brandapp.device.scale;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.db.table.scale.Scale_FourElectrode_DataModel;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;
import com.isport.brandapp.Home.bean.http.Fatsteelyard;
import com.isport.brandapp.R;
import com.isport.brandapp.device.scale.bean.ScaleBMIResultBean;
import com.isport.brandapp.login.ActivityLogin;
import com.isport.brandapp.util.AppSP;
import com.isport.brandapp.util.DeviceDataUtil;
import com.isport.brandapp.util.UserAcacheUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import bike.gymproject.viewlibray.pickerview.utils.DateUtils;
import brandapp.isport.com.basicres.ActivityManager;
import brandapp.isport.com.basicres.BaseTitleActivity;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.MessageEvent;
import brandapp.isport.com.basicres.commonutil.ToastUtils;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonview.TitleBarView;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import brandapp.isport.com.basicres.service.observe.BleProgressObservable;
import brandapp.isport.com.basicres.service.observe.NetProgressObservable;

public class ActivityScaleMain extends BaseTitleActivity implements View.OnClickListener {

    TextView tvTime;
    TextView tvReport, tvHistory;
    TextView tvData;
    ImageView ivResult;
    TextView tvResult;
    TextView tvBodyPrencent, tvIbmValue;

    UserInfoBean infoBean;


    long time;
    String data;
    String bmiValue;
    String bodyPrencent;
    String result;

    float fBMIValue;
    private Fatsteelyard mMFatsteelyard;
    private Scale_FourElectrode_DataModel mScale_fourElectrode_dataModel;

    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_scale_main;
    }

    @Override
    protected void initView(View view) {
        tvTime = view.findViewById(R.id.tv_time);
        tvReport = view.findViewById(R.id.tv_report);
        tvHistory = view.findViewById(R.id.tv_history);
        tvIbmValue = view.findViewById(R.id.tv_ibm_value);
        tvBodyPrencent = view.findViewById(R.id.tv_body_prenter);
        ivResult = view.findViewById(R.id.iv_result);
        tvResult = view.findViewById(R.id.tv_result);
        tvData = view.findViewById(R.id.tv_data);
        titleBarView.setLeftIconEnable(true);
        titleBarView.setTitle(getString(R.string.last_weight));
        titleBarView.setRightText("");
    }

    @Override
    protected void initData() {
//        setDefaultDisplay();
        mMFatsteelyard = (Fatsteelyard) getIntent().getSerializableExtra("mFatsteelyard");
        mScale_fourElectrode_dataModel = (Scale_FourElectrode_DataModel) getIntent().getSerializableExtra
                ("mScale_fourElectrode_dataModel");
        if (mScale_fourElectrode_dataModel != null) {
            data = mScale_fourElectrode_dataModel.getWeight() + "";
            bodyPrencent = mScale_fourElectrode_dataModel.getBFP() + "";
            time = mScale_fourElectrode_dataModel.getTimestamp();
            fBMIValue = (float) mScale_fourElectrode_dataModel.getBMI();
            bmiValue = CommonDateUtil.formatOnePoint(mScale_fourElectrode_dataModel.getBMI());
            Logger.myLog("mScale_fourElectrode_dataModel ! =null" + DateUtils.getAMTime(time));
        }
        if (mMFatsteelyard != null) {
            data = mMFatsteelyard.getWeight().split("_")[0];
            bodyPrencent = mMFatsteelyard.getPercentageFat();
            time = mMFatsteelyard.getNearestTime();
            fBMIValue = Float.valueOf(mMFatsteelyard.getBmi());
            bmiValue = mMFatsteelyard.getBmi();
        }
        infoBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(this));
        resultBean = DeviceDataUtil.calReslut(fBMIValue);
        //result = DeviceDataUtil.calReslut(fBMIValue);
        setValue();
    }

    ScaleBMIResultBean resultBean;

    private void setDefaultDisplay() {
        tvTime.setText(getString(R.string.no_data));
        tvData.setText(getString(R.string.no_data));
        tvIbmValue.setText(getString(R.string.no_data));
        tvBodyPrencent.setText(getString(R.string.no_data));
        tvResult.setText(UIUtils.getString(R.string.thin));
        setIvResult(R.drawable.icon_body_thin);
    }


    private void setValue() {
        try {
            Logger.myLog("setValue" + DateUtils.getAMTime(time));
            tvTime.setText(DateUtils.getAMTime(time));
            tvData.setText(data);
            tvIbmValue.setText(String.format(getResources().getString(R.string.app_scale_bmi), bmiValue));
            bodyPrencent = bodyPrencent
                    .replace("_", "");
            double doub = 0;
            try {
                doub = Double.valueOf(bodyPrencent);
            } catch (Exception e) {
                doub = 0;
            } finally {
                tvBodyPrencent.setText(String.format(getResources().getString(R.string.bfr_value), CommonDateUtil.formatTwoPoint(doub)));
                tvResult.setText(resultBean.result);
                setIvResult(resultBean.res);
            }

        } catch (Exception e) {

        }


    }

    @Override
    protected void initEvent() {
        tvReport.setOnClickListener(this);
        tvHistory.setOnClickListener(this);
        titleBarView.setOnTitleBarClickListener(new TitleBarView.OnTitleBarClickListener() {
            @Override
            public void onLeftClicked(View view) {
                finish();
                /*if (TextUtils.isEmpty(from)) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("isLogout", true);
                    startActivity(intent);
                    ActivityManager.getInstance().finishAllActivity(MainActivity.class.getSimpleName());

                } else {
                    finish();
                }
*/
            }

            @Override
            public void onRightClicked(View view) {


            }
        });
    }

    @Override
    protected void initHeader() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_report:
                Intent intent = new Intent(this, ActivityScaleReport.class);
                intent.putExtra("mMFatsteelyard", mMFatsteelyard);
                intent.putExtra("mScale_fourElectrode_dataModel", mScale_fourElectrode_dataModel);
                startActivity(intent);
                break;
            case R.id.tv_history:
                Intent historyIntent = new Intent(this, ActivityScaleHistory.class);
                //Intent historyIntent = new Intent(this, ActivitySleepHistory.class);
                historyIntent.putExtra("mScale_fourElectrode_dataModel", mScale_fourElectrode_dataModel);
                startActivity(historyIntent);
                break;
        }

    }


    private void setIvResult(int res) {

        ivResult.setImageResource(res);

        /**      <string name="thin">偏瘦</string>//偏瘦
         <string name="standard">标准</string>//标准
         <string name="chubby">偏胖</string>//偏胖
         <string name="obesity">肥胖</string>//肥胖
         <string name="severe_obesity">重度肥胖</string>//重度
         **/

      /*  if(UIUtils.getString(R.string.severe_obesity).equals(result)){
            ivResult.setImageResource(R.drawable.icon_body_very_fat);
        }else if(UIUtils.getString(R.string.thin).equals(result)){
            ivResult.setImageResource(R.drawable.icon_body_thin);
        }else if(UIUtils.getString(R.string.).equals(result)){

        }else if(UIUtils.getString(R.string.severe_obesity).equals(result)){

        }else if(UIUtils.getString(R.string.severe_obesity).equals(result)){

        }

        switch (result) {
            case JkConfiguration.BodyType.CHUBBY:
                ivResult.setImageResource(R.drawable.icon_body_chubby);
                break;
            case JkConfiguration.BodyType.STANDARD:
                ivResult.setImageResource(R.drawable.icon_body_standards);
                break;
            case JkConfiguration.BodyType.FAT:
                ivResult.setImageResource(R.drawable.icon_body_fat);
                break;
            case JkConfiguration.BodyType.THIN:
                ivResult.setImageResource(R.drawable.icon_body_thin);
                break;
            case JkConfiguration.BodyType.VERY_FAT:
                ivResult.setImageResource(R.drawable.icon_body_very_fat);
                break;
        }*/
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
