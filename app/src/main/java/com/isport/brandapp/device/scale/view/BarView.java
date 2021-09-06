package com.isport.brandapp.device.scale.view;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.isport.brandapp.R;

import androidx.core.content.ContextCompat;

public class BarView extends LinearLayout {
    Context mContext;

    Float[][] value = {{0f, 18.5f}, {18.5f, 24.0f}, {24.0f, 28.0f}, {28.0f, 30.0f}, {30.0f, 50.1f}};
    float crrentBMIvalue;

    public BarView(Context context) {
        this(context, null);
    }

    public BarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
       /* TypedArray a = context.obtainStyledAttributes(attrs, bike.gymproject.viewlibray.R.styleable.ItemView,
                defStyleAttr, 0);
        titleText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_itemText);
        titleColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_itemTextColor, context.getResources()
        .getColor(bike.gymproject.viewlibray.R.color.common_white));

        contentText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_contentText);
        contentColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_contentTextColor, context
        .getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        showRemind = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showRemind, false);
        showArrow = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showArrow, true);
        showBottomLine = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showBottomLine, false);
        showCheckModel = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showCheckModel, false);
        isChecked = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_isChecked, false);
        remindIcon = a.getDrawable(bike.gymproject.viewlibray.R.styleable.ItemView_remindIcon);
        a.recycle();*/

        initView();
        //setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();


    }

    View viewBlue, viewGreen, viewOrange, viewYello, viewRed;
    LinearLayout layoutBar;
    ImageView ivRemove;

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.app_view_bar, this, true);
        viewBlue = findViewById(R.id.view_bule);
        viewGreen = findViewById(R.id.view_green);
        viewOrange = findViewById(R.id.view_orange);
        viewYello = findViewById(R.id.view_yello);
        viewRed = findViewById(R.id.view_red);
        layoutBar = findViewById(R.id.layout_bar);
        ivRemove = findViewById(R.id.iv_remove);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (right != 0 && bottom != 0) {
            ivRemove.layout(left, top, right, bottom);
        }

    }

    Paint thumbPaint;

    private void initData() {

        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);
        GradientDrawable drawable = (GradientDrawable) viewBlue.getBackground();
        drawable.setColor(ContextCompat.getColor(mContext, R.color.common_stande_blue));
        drawable = (GradientDrawable) viewGreen.getBackground();
        drawable.setColor(ContextCompat.getColor(mContext, R.color.common_stande_green));
        drawable = (GradientDrawable) viewYello.getBackground();
        drawable.setColor(ContextCompat.getColor(mContext, R.color.common_stande_yello));
        drawable = (GradientDrawable) viewOrange.getBackground();
        drawable.setColor(ContextCompat.getColor(mContext, R.color.common_stande_orange));
        drawable = (GradientDrawable) viewRed.getBackground();
        drawable.setColor(ContextCompat.getColor(mContext, R.color.common_stande_red));
    }


    int left = 0;
    int right = 0;
    int top = 0;
    int bottom = 0;

    public void setCurrentBMIvalue(float currentBMIvalue) {


        this.crrentBMIvalue = currentBMIvalue;
        int viewWidth = viewRed.getWidth();
        int imageWidth = ivRemove.getWidth();
        float minValue, maxValue;
        for (int i = 0; i < value.length; i++) {
            minValue = value[i][0];
            maxValue = value[i][1];
            if (currentBMIvalue > 50) {
                currentBMIvalue = 50;
            }
            if (currentBMIvalue < 0) {
                currentBMIvalue = 0;
            }
            if (crrentBMIvalue >= minValue && crrentBMIvalue < maxValue) {

                left = (int) (viewWidth * i + (crrentBMIvalue - minValue) / (maxValue - minValue) * viewWidth) -
                        imageWidth / 2;
                right = left + imageWidth;
                top = ivRemove.getTop();
                bottom = ivRemove.getBottom();
                ivRemove.layout(left, top, right, bottom);
                return;

                //rect =CGRectMake(30 +59*i+(BMIValue-minValue)/(maxValue-minValue)*59-2, 203, 4, 20);
            }
        }
        //int left = ivRemove.getLeft();
        //int right = ivRemove.getRight();


    }

}
