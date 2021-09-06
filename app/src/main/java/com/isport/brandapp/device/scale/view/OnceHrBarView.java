package com.isport.brandapp.device.scale.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class OnceHrBarView extends LinearLayout {
    Context mContext;

    Float[][] value = {{0f, 50f}, {50f, 60f}, {60f, 70f}, {70f, 80f}, {80f, 90f}, {90f, 100f}};
    float crrentBMIvalue;

    public OnceHrBarView(Context context) {
        this(context, null);
    }

    public OnceHrBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnceHrBarView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    View view_leisure, view_warm_up, view_fat_burning_exercise, view_fat_aerobic_exercise, view_anaerobic_exercise, view_limit;
    LinearLayout layoutBar;
    ImageView ivRemove;
    View view_line;

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.app_once_hr_view_bar, this, true);
        view_leisure = findViewById(R.id.view_leisure);
        view_warm_up = findViewById(R.id.view_warm_up);
        view_fat_burning_exercise = findViewById(R.id.view_fat_burning_exercise);
        view_fat_aerobic_exercise = findViewById(R.id.view_fat_aerobic_exercise);
        view_anaerobic_exercise = findViewById(R.id.view_anaerobic_exercise);
        view_limit = findViewById(R.id.view_limit);
        layoutBar = findViewById(R.id.layout_bar);
        ivRemove = findViewById(R.id.iv_remove);
        view_line = findViewById(R.id.view_line);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (right != 0 && bottom != 0) {
            ivRemove.layout(left, top, right, bottom);
            view_line.layout(viewLeft, viewtop, viewRight, viewbottom);
        }

    }

    Paint thumbPaint;

    private void initData() {

        /*thumbPaint = new Paint();
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
        drawable.setColor(ContextCompat.getColor(mContext, R.color.common_stande_red));*/
    }


    int left = 0;
    int right = 0;
    int top = 0;
    int bottom = 0;

    int viewLeft = 0;
    int viewRight = 0;
    int viewtop = 0;
    int viewbottom = 0;

    public void setCurrentBMIvalue(float currentBMIvalue, int color) {

        ivRemove.setVisibility(VISIBLE);
        view_line.setVisibility(VISIBLE);

        this.crrentBMIvalue = currentBMIvalue;
        int viewWidth = view_leisure.getWidth();
        int imageWidth = ivRemove.getWidth();
        float minValue, maxValue;
        for (int i = 0; i < value.length; i++) {
            minValue = value[i][0];
            maxValue = value[i][1];
            if (crrentBMIvalue >= 100) {
                crrentBMIvalue = 99.9f;
            }
            if (crrentBMIvalue < 0) {
                crrentBMIvalue = 0;
            }
            if (crrentBMIvalue >= minValue && crrentBMIvalue < maxValue) {
                Logger.myLog("crrentBMIvalue=" + crrentBMIvalue);

                left = (int) (viewWidth * i + (crrentBMIvalue - minValue) / (maxValue - minValue) * viewWidth) -
                        imageWidth / 2;
                left = left + DisplayUtils.dip2px(mContext, 9);
                right = left + imageWidth;
                top = ivRemove.getTop();
                bottom = ivRemove.getBottom();


                viewLeft = (int) (viewWidth * i + (crrentBMIvalue - minValue) / (maxValue - minValue) * viewWidth) - view_line.getWidth() / 2;
                viewLeft = viewLeft + DisplayUtils.dip2px(mContext, 9);
                viewRight = viewLeft + DisplayUtils.dip2px(mContext, 1);
                viewtop = view_line.getTop();
                viewbottom = view_line.getBottom();


                ivRemove.setColorFilter(color);

                ivRemove.layout(left, top, right, bottom);
                view_line.layout(viewLeft, viewtop, viewRight, viewbottom);

                Log.e("setCurrentBMIvalue","viewLeft="+viewLeft+"viewtop="+viewtop+"viewRight="+viewRight+"viewbottom="+viewbottom);
                Log.e("setCurrentBMIvalue","left="+left+"top="+top+"right="+right+"bottom="+bottom);


                return;

                //rect =CGRectMake(30 +59*i+(BMIValue-minValue)/(maxValue-minValue)*59-2, 203, 4, 20);
            }
        }
        //int left = ivRemove.getLeft();
        //int right = ivRemove.getRight();


    }

}
