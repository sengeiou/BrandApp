package com.isport.brandapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;
import com.isport.brandapp.wu.util.HeartRateConvertUtils;

import java.util.ArrayList;

import bike.gymproject.viewlibray.WatchHourMinuteView;
import brandapp.isport.com.basicres.commonutil.UIUtils;

public class HeartStrongDialog {
    private OnTypeClickListenter onTypeClickListenter;
    private Activity mActivity;
    public AlertDialog dialog;
    Button tvOk;
    public WatchHourMinuteView view_limit, view_anaerobic_exercise, view_aerobic_exercise, view_fat_burning_exercise, view_warm_up, view_leisure;

    public HeartStrongDialog(Activity activity, final OnTypeClickListenter onTypeClickListenter, int age, String sex) {
        this.mActivity = activity;
        this.onTypeClickListenter = onTypeClickListenter;
        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_heart_strong);
        tvOk = dialog.getWindow().findViewById(R.id.btn_known);
        view_limit = dialog.getWindow().findViewById(R.id.view_limit);
        view_anaerobic_exercise = dialog.getWindow().findViewById(R.id.view_anaerobic_exercise);
        view_aerobic_exercise = dialog.getWindow().findViewById(R.id.view_aerobic_exercise);
        view_fat_burning_exercise = dialog.getWindow().findViewById(R.id.view_fat_burning_exercise);
        view_warm_up = dialog.getWindow().findViewById(R.id.view_warm_up);
        view_leisure = dialog.getWindow().findViewById(R.id.view_leisure);
        ArrayList<String> strHrList = HeartRateConvertUtils.pointToheartRate(age, sex);

        view_limit.setdata(strHrList.get(0), UIUtils.getString(R.string.BPM));
        view_anaerobic_exercise.setdata(strHrList.get(1), UIUtils.getString(R.string.BPM));
        view_aerobic_exercise.setdata(strHrList.get(2), UIUtils.getString(R.string.BPM));
        view_fat_burning_exercise.setdata(strHrList.get(3), UIUtils.getString(R.string.BPM));
        view_warm_up.setdata(strHrList.get(4), UIUtils.getString(R.string.BPM));
        view_leisure.setdata(strHrList.get(5), UIUtils.getString(R.string.BPM));

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(1);
                }
            }
        });


    }


    public interface OnTypeClickListenter {
        void changeDevcieonClick(int type);
    }


    private SpannableString getClickableSpan() {


        String connent = UIUtils.getString(R.string.privacy_agreement_content);
        String connentagreement = UIUtils.getString(R.string.user_agreement_tips);
        String connentprivacy = UIUtils.getString(R.string.privacy_agreement_tips);
        int indexAgreement = connent.indexOf(connentagreement);
        int indexPrivacy = connent.indexOf(connentprivacy);
        if (indexAgreement >= 0 && indexAgreement < connent.length()) ;
        SpannableString spannableString = new SpannableString(UIUtils.getString(R.string.privacy_agreement_content));


//设置下划线文字

        spannableString.setSpan(new UnderlineSpan(), indexAgreement, indexAgreement + connentagreement.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), indexPrivacy, indexPrivacy + connentprivacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//设置文字的单击事件

        spannableString.setSpan(new ClickableSpan() {

            @Override

            public void onClick(View widget) {
                Logger.myLog("");
                dialog.cancel();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(2);
                }

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                //  super.updateDrawState(false);
            }
        }, indexAgreement, indexAgreement + connentagreement.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {

            @Override

            public void onClick(View widget) {
                dialog.cancel();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(3);
                }

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                // super.updateDrawState(ds);
            }
        }, indexPrivacy, indexPrivacy + connentprivacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.common_white)), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.common_view_color)), indexAgreement, indexAgreement + connentagreement.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.common_view_color)), indexPrivacy, indexPrivacy + connentprivacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;

    }

}
