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


public class HoBatteryView extends View {


    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    private Paint nRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    private int nRingColor;

    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private float progress;
    Bitmap bitmap = null;
    int imgWidth = 0;
    int ingHight = 0;
    Context context;

    Drawable icon;

    public HoBatteryView(Context context, AttributeSet attrs) {
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

        mStrokeWidth = typeArray.getDimension(
                R.styleable.TasksCompletedView_strokeWidth, 10);
        // mCircleColor = 0xffffffff;
        mCircleColor = typeArray.getColor(
                R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        typeArray.recycle();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRadius = getWidth() / 2 - DisplayUtils.dip2px(context, 8);
        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.FILL);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        nRingPaint = new Paint();
        nRingPaint.setAntiAlias(true);
        nRingPaint.setColor(mRingColor);
        nRingPaint.setStyle(Paint.Style.STROKE);
        nRingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setARGB(255, 255, 255, 255);
        mTextPaint.setTextSize(mRadius / 2);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;
        // canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
        float s = getWidth() / 100f;
        progress = 50;
        RectF oval = new RectF();
        oval.left = DisplayUtils.dip2px(context, 2);
        oval.top = s * progress;
        oval.right = getWidth();
        oval.bottom = getHeight();
        canvas.drawRect(oval, mRingPaint);
        //canvas.drawArc(oval, -90, 1.0f * progress * 3.6f, false, mRingPaint);
        canvas.drawBitmap(bitmap, mXCenter - imgWidth / 2, mYCenter - ingHight / 2, mTextPaint);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;// invalidate();
        invalidate();


    }


}
