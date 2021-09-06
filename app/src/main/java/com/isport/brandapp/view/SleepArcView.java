package com.isport.brandapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import brandapp.isport.com.basicres.commonutil.Logger;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


public class SleepArcView extends View {

    private Context mContext;

    private int mRadius;
    private int mArcWidth = 13;

    public SleepArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private int mCenterX;
    private int mCenterY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenterX = (right - left) / 2;
        mCenterY = (bottom - top) / 2;
        mRadius = getWidth() / 2 - DisplayUtils.dip2px(mContext, mArcWidth);
    }

    RectF arcRectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {

        arcRectF.set(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius);
//        arcRectF.set(0, 0, getWidth(), getHeight());

        Paint paint = new Paint();
        paint.setStrokeWidth(DisplayUtils.dip2px(mContext, mArcWidth));
        paint.setStyle(Paint.Style.STROKE);

        if (isNoValue) {
            paint.setColor(0xFFF2F2F2);
            canvas.drawArc(arcRectF, -90, 360, false, paint);
        } else {
            //第一阶段  清醒
            // paint.setColor(Color.parseColor("#4BC4FF"));
            paint.setColor(0xFF4DDA64);
            canvas.drawArc(arcRectF, -90, mFirstAngle, false, paint);
            //第二阶段 浅睡
            paint.setColor(0xFF4194FF);
            canvas.drawArc(arcRectF, mFirstAngle - 90, mSecondAngle, false, paint);
            //第三阶段 深睡
            // paint.setColor(Color.parseColor("#FD944A"));

            paint.setColor(0xFF5856D7);
            canvas.drawArc(arcRectF, mFirstAngle + mSecondAngle - 90, mThirdAngle, false, paint);
        }

        super.onDraw(canvas);
    }

    private boolean isNoValue = false;

    private float mFirstAngle;
    private float mSecondAngle;
    private float mThirdAngle;


    public void setValue(int value1, int value2, int value3) {
        int totalValue;
        isNoValue = false;
        totalValue = value1 + value2 + value3;
        if ((value1 + value2 + value3) == 0) {
            isNoValue = true;
        } else {
            mFirstAngle = value1 * 360 / totalValue;
            mSecondAngle = value2 * 360 / totalValue;
//        mThirdAngle = 360 - value3 * 360 / 100;
            mThirdAngle = 360 - mFirstAngle - mSecondAngle;
            if (value3 == 0) {
                mSecondAngle = mSecondAngle + mThirdAngle;
                mThirdAngle = 0;
            }


            Logger.e("mFirstAngle:" + mFirstAngle + "mSecondAngle:" + mSecondAngle + "mThirdAngle：" + mThirdAngle);

        }

        invalidate();
    }
}
