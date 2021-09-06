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
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;

import brandapp.isport.com.basicres.commonutil.UIUtils;

public class RopePkCompletyDialog {
    private OnTypeClickListenter onTypeClickListenter;
    private Activity mActivity;
    private ImageView iv_success;
    private TextView tv_success, tv_time, tv_challeg_type_title, tv_rope_count, tv_cal_value, tv_back;
    public AlertDialog dialog;

    public RopePkCompletyDialog(Activity activity, String ropeTypeName, String ropetime, String sumcout, String cal, boolean isSuccess, final OnTypeClickListenter onTypeClickListenter) {
        this.mActivity = activity;
        this.onTypeClickListenter = onTypeClickListenter;
        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_pk_rope);
        iv_success = dialog.getWindow().findViewById(R.id.iv_state);
        tv_success = dialog.getWindow().findViewById(R.id.tv_success);
        tv_time = dialog.getWindow().findViewById(R.id.tv_time);
        tv_rope_count = dialog.getWindow().findViewById(R.id.tv_rope_count);
        tv_challeg_type_title = dialog.getWindow().findViewById(R.id.tv_challeg_type_title);
        tv_cal_value = dialog.getWindow().findViewById(R.id.tv_cal_value);
        tv_back = dialog.getWindow().findViewById(R.id.tv_back);


        if (isSuccess) {
            iv_success.setImageResource(R.drawable.bg_rope_success);
            tv_success.setText(activity.getString(R.string.rope_challeg_success));
            tv_challeg_type_title.setText(String.format(activity.getString(R.string.rope_challeg_type_success), ropeTypeName));
        } else {
            iv_success.setImageResource(R.drawable.bg_rope_fail);
            tv_success.setText(activity.getString(R.string.rope_challeg_fail));
            tv_challeg_type_title.setText(String.format(activity.getString(R.string.rope_challeg_type_fail), ropeTypeName));
        }
        tv_cal_value.setText(cal);
        tv_rope_count.setText(sumcout);
        tv_time.setText(ropetime);

        tv_back.setOnClickListener(new View.OnClickListener() {
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
