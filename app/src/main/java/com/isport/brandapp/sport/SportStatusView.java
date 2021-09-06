package com.isport.brandapp.sport;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.brandapp.R;

import brandapp.isport.com.basicres.commonview.ShadowDrawable;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class SportStatusView extends LinearLayout {

    LinearLayout layoutBgStart;
    ImageView ivStatues;
    ImageView ivStatuseBg;


    TextView tvValue;
    TextView tvTitile;
    TextView tvUnit;


    String strValue;
    String strTitle;
    String strUnit;


    Drawable iconDrawable;

    ImageView leftIcon;

    public SportStatusView(Context context) {
        this(context, null);
    }

    public SportStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SportStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
       /* TypedArray a = context.obtainStyledAttributes(attrs, bike.gymproject.viewlibray.R.styleable.SleepItem,
                defStyleAttr, 0);
        strValue = a.getString(bike.gymproject.viewlibray.R.styleable.SleepItem_valuseText);
        strTitle = a.getString(bike.gymproject.viewlibray.R.styleable.SleepItem_titleText);
        strUnit = a.getString(bike.gymproject.viewlibray.R.styleable.SleepItem_unitText);

        colorValue = a.getColor(bike.gymproject.viewlibray.R.styleable.SleepItem_valuseColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        colorTitle = a.getColor(bike.gymproject.viewlibray.R.styleable.SleepItem_titleColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        colorUnit = a.getColor(bike.gymproject.viewlibray.R.styleable.SleepItem_unitColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));

        sizeTitle = a.getDimension(bike.gymproject.viewlibray.R.styleable.SleepItem_titleSize, 14);
        sizeValue = a.getDimension(bike.gymproject.viewlibray.R.styleable.SleepItem_valuseSize, 14);
        sizeUnit = a.getDimension(bike.gymproject.viewlibray.R.styleable.SleepItem_unitSize, 14);

        iconDrawable = a.getDrawable(bike.gymproject.viewlibray.R.styleable.SleepItem_leftIcon);

        a.recycle();*/

        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_sport_status_layout, this, true);

        ivStatues = findViewById(R.id.iv_status);
        ivStatuseBg = findViewById(R.id.iv_status_bg);
        layoutBgStart = findViewById(R.id.layout_bg_start);
        ShadowDrawable.setShadowDrawable(layoutBgStart, ShadowDrawable.SHAPE_CIRCLE, getResources().getColor(R.color.white), DisplayUtils.dip2px(getContext(), 8),
                Color.parseColor("#14000000"), DisplayUtils.dip2px(getContext(), 2), 0, DisplayUtils.dip2px(getContext(), 2));
    }

    private void initData() {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitile.setText(strTitle);
        }

        if (!TextUtils.isEmpty(strValue)) {
            tvValue.setText(strValue);
        }

        if (!TextUtils.isEmpty(strUnit)) {
            tvUnit.setText(strUnit);
        }

    }

    private void setListener() {
    }

    public void setTitleText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setValueText(getContext().getString(resId));
    }

    public void setValueText(String text) {
        if (null == tvValue) {
            return;
        }
        this.strValue = text;
        tvValue.setText(TextUtils.isEmpty(text) ? "" : text);
    }


    /**
     * 根据资源ID获取Drawable/设置边框
     *
     * @param resId
     * @return
     */
    private Drawable getDrawable(int resId) {
        if ((resId >>> 24) < 2) {
            return null;
        }
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getContext().getDrawable(resId);
        } else {
            drawable = getContext().getResources().getDrawable(resId);
        }

        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }


    /*public RoundImageView getRemindImageView() {
        return remindImageView;
    }*/


    public interface OnItemViewCheckedChangeListener {
        void onCheckedChanged(int id, boolean isChecked);
    }


    public void setImage(Integer res, String bgColor) {


        ivStatues.setImageResource(res);

        if (ivStatuseBg != null) {

            GradientDrawable drawable = (GradientDrawable) ivStatuseBg.getBackground();
            drawable.setColor(Color.parseColor(bgColor));
            ivStatuseBg.setBackground(drawable);
        }


    }
}