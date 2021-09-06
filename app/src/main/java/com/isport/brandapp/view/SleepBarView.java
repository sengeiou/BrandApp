package com.isport.brandapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import brandapp.isport.com.basicres.commonutil.Logger;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


public class SleepBarView extends View {

    private Context mContext;

    private int mRadius;
    private int mArcWidth = 13;
    private float viewWith;
    private int mViewHeight;
    private int mViewWidth;

    Paint paint;

    public SleepBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private int mCenterX;
    private int mCenterY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenterX = (right - left) / 2;
        mCenterY = (bottom - top) / 2;
        mRadius = getWidth() / 2 - DisplayUtils.dip2px(mContext, mArcWidth);
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }

    RectF arcRectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {

//        arcRectF.set(0, 0, getWidth(), getHeight());


        // paint.setStrokeWidth(DisplayUtils.dip2px(mContext, mArcWidth));
        // paint.setStyle(Paint.Style.STROKE);
        int bottom = mViewHeight;
        int top = mViewHeight;
        if (isNoValue) {
            paint.setColor(0xFFF2F2F2);
            arcRectF.set(0, top, mViewWidth, bottom);
            canvas.drawRoundRect(arcRectF, DisplayUtils.dip2px(mContext, 20), DisplayUtils.dip2px(mContext, 20), paint);
        } else {
            //第一阶段  清醒
            // paint.setColor(Color.parseColor("#4BC4FF"));
            paint.setColor(0xFF4DDA64);
            arcRectF.set(0, top, mViewWidth, bottom);
            canvas.drawRoundRect(arcRectF, DisplayUtils.dip2px(mContext, 20), DisplayUtils.dip2px(mContext, 20), paint);
            //第三阶段 深睡
            // paint.setColor(Color.parseColor("#FD944A"));

            paint.setColor(0xFF4194FF);
            arcRectF.set(0, top, mFirstAngle + DisplayUtils.dip2px(mContext, DisplayUtils.dip2px(mContext, 20)), bottom);
            canvas.drawRoundRect(arcRectF, (arcRectF.right-arcRectF.left)/2, (arcRectF.right-arcRectF.left)/2, paint);
            // canvas.drawRect(0, top, mFirstAngle, bottom, paint);

            // canvas.drawArc(arcRectF, -90, mFirstAngle, false, paint);
            //  canvas.drawRoundRect(0, , paint);
            //canvas.drawRect();
            //第二阶段 浅睡
            paint.setColor(0xFF5856D7);
            canvas.drawRect(mFirstAngle, top, mFirstAngle + mSecondAngle, bottom, paint);
            // canvas.drawArc(arcRectF, mFirstAngle+mSecondAngle, mSecondAngle, false, paint);

        }

        super.onDraw(canvas);
    }

    private boolean isNoValue = false;

    private float mFirstAngle;
    private float mSecondAngle;
    private float mThirdAngle;

    /**
     * @param value1 清醒
     * @param value2 浅睡
     * @param value3 深睡
     */

    public void setValue(int value1, int value2, int value3) {
        int totalValue;
        isNoValue = false;
        totalValue = value1 + value2 + value3;
        if ((value1 + value2 + value3) == 0) {
            isNoValue = true;
        } else {
            viewWith = mViewWidth * 1.0f / totalValue;
            mFirstAngle = value2 * viewWith;
            mSecondAngle = value3 * viewWith;
/*
//        mThirdAngle = 360 - value3 * 360 / 100;
            mThirdAngle = 360 - mFirstAngle - mSecondAngle;
            if (value3 == 0) {
                mSecondAngle = mSecondAngle + mThirdAngle;
                mThirdAngle = 0;
            }
*/


            Logger.e("mFirstAngle:" + mFirstAngle + "mSecondAngle:" + mSecondAngle + "mThirdAngle：" + mThirdAngle);

        }

        invalidate();
    }
}
