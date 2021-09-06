package com.isport.brandapp.ropeskipping.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.brandapp.R;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class RealRopeTextView extends LinearLayout {
    LinearLayout layout_bottom;
    TextView tvBottom;
    TextView tvTopValue;
    Context context;

    public RealRopeTextView(Context context) {
        this(context, null);
    }

    public RealRopeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RealRopeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RealRopeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, bike.gymproject.viewlibray.R.styleable.ItemView,
                defStyleAttr, 0);
        this.context = context;
        a.recycle();

        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.text_rope_text, this, true);
        tvBottom = (TextView) findViewById(R.id.tv_tottom);
        layout_bottom = findViewById(R.id.layout_bottom);
        tvTopValue = findViewById(R.id.tv_top);
    }

    public void setRopeTopeValue(int currentData, int targetData, String showValue) {

        int precent = (int) (currentData * 1.0f / targetData * 100);

        precent = 50;

        tvBottom.setText("" + showValue);
        tvTopValue.setText("" + showValue);

        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) layout_bottom.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

        linearParams.height = tvTopValue.getHeight() * precent;// 控件的宽强制设成30

        layout_bottom.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

    }

    public String getValue() {
        if (tvBottom != null) {
            return tvBottom.getText().toString();
        }
        return "";
    }

}