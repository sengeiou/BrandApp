package com.isport.brandapp.wu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class OxyBarView extends View {

    private int mValue;

    private int mViewHeight;
    private int mViewWidth;

    private Paint mPaint;

    public OxyBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OxyBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int right=mViewWidth*(mValue-80)/20;
        canvas.drawRect(0,0,right,mViewHeight,mPaint);
    }

    public void setProgress(int value) {
        this.mValue = value;
        invalidate();
    }
}
