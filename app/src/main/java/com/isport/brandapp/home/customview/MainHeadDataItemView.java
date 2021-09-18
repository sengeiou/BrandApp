package com.isport.brandapp.home.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.brandapp.R;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class MainHeadDataItemView extends LinearLayout {

    TextView tvTitle;
    TextView tvValue;
    TextView tvUnit;


    String strValue;
    String strTitle;
    String strUnit;

    int colorValue;
    int colorTitle;
    int colorUnit;

    float sizeValue;
    float sizeTitle;
    float sizeUnit;


    public MainHeadDataItemView(Context context) {
        this(context, null);
    }

    public MainHeadDataItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainHeadDataItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MainHeadDataItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }


    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MainHeadDataItemView,
                defStyleAttr, 0);
        strValue = a.getString(R.styleable.MainHeadDataItemView_mainheadDataValue);
        strTitle = a.getString(R.styleable.MainHeadDataItemView_mainheadDataTitle);
        strUnit = a.getString(R.styleable.MainHeadDataItemView_mainheadDataUnit);

        colorValue = a.getColor(R.styleable.MainHeadDataItemView_mainheadDataValueColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        colorTitle = a.getColor(R.styleable.MainHeadDataItemView_mainheadDataTitleColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        colorUnit = a.getColor(R.styleable.MainHeadDataItemView_mainheadDataUnitColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));


        sizeTitle = a.getDimension(R.styleable.MainHeadDataItemView_mainheadDataTitleSize, 14);
        sizeValue = a.getDimension(R.styleable.MainHeadDataItemView_mainheadDataValueUnitSize, 14);
        sizeUnit = a.getDimension(R.styleable.MainHeadDataItemView_mainheadDataUnitSize, 14);


        a.recycle();

        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_main_head_data, this, true);

        tvValue = findViewById(bike.gymproject.viewlibray.R.id.tv_value);
        tvTitle = findViewById(bike.gymproject.viewlibray.R.id.tv_title);
        tvUnit = findViewById(bike.gymproject.viewlibray.R.id.tv_unit);


        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeValue);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeTitle);
        tvUnit.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeUnit);


        tvValue.setTextColor(colorValue);
        tvUnit.setTextColor(colorUnit);

    }

    public void setTitleSize(float size) {
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    private void initData() {


        if (!TextUtils.isEmpty(strValue)) {
            tvValue.setText(strValue);
        }

        if (!TextUtils.isEmpty(strUnit)) {
            tvUnit.setText(strUnit);
        }

        //isShowValue(isShowValue);
        //isShowTitle(isShowTitle);
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(strTitle);
        }
    }

    private void setListener() {
    }

    public void setTitleText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setTitleText(getContext().getString(resId));
    }


    public void setTitleText(String text) {
        if (null == tvTitle) {
            return;
        }
        tvTitle.setVisibility(VISIBLE);
        this.strTitle = text;
        tvTitle.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setValueText(String text) {
        if (null == tvValue) {
            return;
        }
        this.strValue = text;
        tvValue.setText(TextUtils.isEmpty(text) ? "" : text);
    }


    public void setUnitText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setUnitText(getContext().getString(resId));
    }

    public void setUnitText(String text) {
        if (null == tvUnit) {
            return;
        }
        this.strUnit = text;
        tvUnit.setVisibility(VISIBLE);
        tvUnit.setText(TextUtils.isEmpty(text) ? "" : text);
    }


    public void setTitleIcon(int resId) {
        if ((resId >>> 24) < 2 || tvTitle == null) {
            return;
        }
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("");
        tvTitle.setCompoundDrawables(null, null, getDrawable(resId), null);
    }

    public void setTitleIcon() {
        if (tvTitle != null) {
            tvTitle.setVisibility(View.GONE);
        }
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


    public void setValue(String strValue) {
        if (tvValue != null) {
            tvValue.setText(strValue);
        }
    }

}