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

import bike.gymproject.viewlibray.AkrobatNumberTextView;
import brandapp.isport.com.basicres.commonutil.UIUtils;


//训练弹窗
public class RopeCompletyDialog {

    private OnTypeClickListenter onTypeClickListenter;
    private Activity mActivity;
    public AlertDialog dialog;
    private TextView tvBack, tv_rope_sum_count, tv_rope_cal, tv_rope_sum_time, tv_rope_type;
    private ImageView iv_rope_type;

    //平均心率
    private AkrobatNumberTextView dialogTrainHeartTv;



    public RopeCompletyDialog(Activity activity, String sumcout, String ropetime, String ropeTypeName, String cal, int ropeType,int avgHeart, final OnTypeClickListenter onTypeClickListenter) {
        this.mActivity = activity;
        this.onTypeClickListenter = onTypeClickListenter;

        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_rope);
        tv_rope_sum_time = dialog.getWindow().findViewById(R.id.tv_rope_sum_time);
        tv_rope_type = dialog.getWindow().findViewById(R.id.tv_rope_type);
        tv_rope_cal = dialog.getWindow().findViewById(R.id.tv_rope_cal);
        tvBack = dialog.getWindow().findViewById(R.id.tv_back);
        tv_rope_sum_count = dialog.getWindow().findViewById(R.id.tv_rope_sum_count);
        iv_rope_type = dialog.getWindow().findViewById(R.id.iv_rope_type);
        dialogTrainHeartTv = dialog.getWindow().findViewById(R.id.dialogTrainHeartTv);


        iv_rope_type.setImageResource(ropeType);
        tv_rope_sum_count.setText(sumcout);
        tv_rope_sum_time.setText(ropetime);
        tv_rope_type.setText(ropeTypeName);
        tv_rope_cal.setText(cal);

        dialogTrainHeartTv.setText(avgHeart+"");


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(0);
                }
            }
        });

       /*  tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(1);
                }
            }
        });*/


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
