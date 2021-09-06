package com.isport.brandapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.isport.brandapp.R;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


public class VerBatteryView extends View {


    private Paint mRingPaint;
    private int mRingColor;

    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private float progress;
    Bitmap bitmap = null;
    int imgWidth = 0;
    int ingHight = 0;
    Context context;

    Drawable icon;

    RectF oval;

    public VerBatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
// 获取自定义的属性

        initAttrs(context, attrs);
        this.context = context;
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
// mRadius =
// typeArray.getDimension(R.styleable.TasksCompletedView_radius,
// 80);
//
        icon = typeArray.getDrawable(R.styleable.TasksCompletedView_certreIcon);
        boolean isLock = typeArray.getBoolean(R.styleable.TasksCompletedView_completedview_lock, false);
        if (isLock) {

            //   bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_sport_lock);
        } else {


        }
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_main_device_battery_def);
        imgWidth = bitmap.getWidth();
        ingHight = bitmap.getHeight();

        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        typeArray.recycle();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    private void initVariable() {

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.FILL);


        oval = new RectF();

    }

    float s;

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;
        s = (getWidth() - (DisplayUtils.dip2px(context, 2) * 2)) / 100f;
        oval.left = DisplayUtils.dip2px(context, 2);
        oval.top = 0;
        if (progress == 100) {
            oval.right = getWidth() - DisplayUtils.dip2px(context, 2);
        } else {
            oval.right = s * progress;
        }


        if (oval.left >= oval.right) {
            oval.right = oval.left * 2;
        }
        oval.bottom = getHeight();
        canvas.drawRect(oval, mRingPaint);
        canvas.drawBitmap(bitmap, mXCenter - imgWidth / 2, mYCenter - ingHight / 2, mRingPaint);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;// invalidate();
        invalidate();


    }


}
