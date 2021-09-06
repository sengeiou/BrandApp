package com.isport.brandapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;

import brandapp.isport.com.basicres.commonutil.UIUtils;

public class PriDialog {
    private OnTypeClickListenter onTypeClickListenter;
    private Activity mActivity;
    public AlertDialog dialog;
    TextView tv_content, tvRefuse, tvOk;

    public PriDialog(Activity activity, final OnTypeClickListenter onTypeClickListenter) {
        this.mActivity = activity;
        this.onTypeClickListenter = onTypeClickListenter;
        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_pri_select);
        tv_content = dialog.getWindow().findViewById(R.id.tv_content);
        tvRefuse = dialog.getWindow().findViewById(R.id.tv_refuse);
        tvOk = dialog.getWindow().findViewById(R.id.tv_ok);
        tv_content.setText(getClickableSpan());
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(0);
                }
            }
        });

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
        spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.common_view_color)), indexAgreement - 1, indexAgreement + connentagreement.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(UIUtils.getColor(R.color.common_view_color)), indexPrivacy - 1, indexPrivacy + connentprivacy.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;

    }

}
