package com.isport.brandapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;

import org.apache.commons.lang.StringUtils;

import brandapp.isport.com.basicres.commonutil.UIUtils;

//挑战弹窗
public class RopePkCompletyDialog {

    private static final String TAG = "RopePkCompletyDialog";

    private OnTypeClickListenter onTypeClickListenter;
    private Activity mActivity;
    private ImageView iv_success;
    private TextView tv_success, tv_time, tv_challeg_type_title, tv_rope_count, tv_cal_value, tv_back;
    private TextView dialogRopeShareTv;
    public AlertDialog dialog;
    //平均心率
    private TextView tv_rope_avg_heartTv;
    //目标的个数
    private TextView ropePkDialogGoalTv;
    //目标总时间
    private TextView dialogRopeGoalTimeTv;

    public RopePkCompletyDialog(Activity activity, String ropeTypeName, String ropetime, String sumcout, String cal, boolean isSuccess, int avgHeart, final OnTypeClickListenter onTypeClickListenter) {

        Logger.myLog(TAG, "------RopePkCompletyDialog=" + ropeTypeName + "\n" + ropetime + "\n" + sumcout + "\n" + cal + "\n" + avgHeart);

        this.mActivity = activity;
        this.onTypeClickListenter = onTypeClickListenter;
        dialog = new AlertDialog.Builder(mActivity, R.style.alert_dialog).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_pk_rope);

        initViews();


        initData(ropeTypeName, ropetime, sumcout, cal, isSuccess, avgHeart, onTypeClickListenter);
    }


    private void initData(String ropeTypeName, String ropetime, String sumcout, String cal, boolean isSuccess, int avgHeart, final OnTypeClickListenter onTypeClickListenter) {
        tv_rope_avg_heartTv.setText(avgHeart == 0 ? "--" : avgHeart + "");
        dialogRopeShareTv.setVisibility(isSuccess ? View.VISIBLE : View.GONE);
        if (isSuccess) {
            iv_success.setImageResource(R.drawable.bg_rope_success);
            tv_success.setText(mActivity.getString(R.string.rope_challeg_success));
            tv_challeg_type_title.setText(String.format(mActivity.getString(R.string.rope_challenge_success2), ropeTypeName));
        } else {
            dialogRopeGoalTimeTv.setText("/" + StringUtils.substringAfter(ropetime, "/") + "");
            iv_success.setImageResource(R.drawable.bg_rope_fail);
            tv_success.setText(mActivity.getString(R.string.rope_challeg_fail));
            tv_challeg_type_title.setText(String.format(mActivity.getString(R.string.rope_challenge_failed2), ropeTypeName));
        }
        tv_cal_value.setText(cal);


        tv_rope_count.setText(StringUtils.substringBefore(sumcout, "/"));
        String afterGoal =  StringUtils.substringAfter(sumcout, "/").trim();
        ropePkDialogGoalTv.setText(TextUtils.isEmpty(afterGoal) ? "":"/" +afterGoal);
        tv_time.setText(StringUtils.substringBefore(ropetime, "/") + "");


        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeDevcieonClick(1);
                }
            }
        });

        //分享
        dialogRopeShareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTypeClickListenter != null) {
                    onTypeClickListenter.changeSuccessClick();
                }
            }
        });
    }


    private void initViews() {
        if (dialog == null)
            return;
        iv_success = dialog.getWindow().findViewById(R.id.iv_state);
        tv_success = dialog.getWindow().findViewById(R.id.tv_success);
        tv_time = dialog.getWindow().findViewById(R.id.tv_time);
        tv_rope_count = dialog.getWindow().findViewById(R.id.tv_rope_count);
        tv_challeg_type_title = dialog.getWindow().findViewById(R.id.tv_challeg_type_title);
        tv_cal_value = dialog.getWindow().findViewById(R.id.tv_cal_value);
        tv_back = dialog.getWindow().findViewById(R.id.tv_back);
        dialogRopeShareTv = dialog.getWindow().findViewById(R.id.dialogRopeShareTv);

        tv_rope_avg_heartTv = dialog.getWindow().findViewById(R.id.tv_rope_avg_heartTv);
        ropePkDialogGoalTv = dialog.getWindow().findViewById(R.id.ropePkDialogGoalTv);
        dialogRopeGoalTimeTv = dialog.getWindow().findViewById(R.id.dialogRopeGoalTimeTv);

    }


    public interface OnTypeClickListenter {
        void changeDevcieonClick(int type);

        void changeSuccessClick();
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
