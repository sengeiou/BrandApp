package com.isport.brandapp.wu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class BpBarView extends View {

    private int mValueHigh;
    private int mValueLow;
    private int mViewHeight;
    private int mViewWidth;
    private int mBgColor[];


    private Paint mPaint;

    public BpBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BpBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mBgColor = new int[3];
        mBgColor[0] = Color.parseColor("#FD3C30");
        mBgColor[1] = Color.parseColor("#9C56D7");
        mBgColor[2] = Color.parseColor("#127AFF");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }

    Rect rect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int bottom = mViewHeight - mViewHeight * mValueLow / 200;
        int top = mViewHeight - mViewHeight * mValueHigh / 200;
        rect.set(0, top, mViewWidth, bottom);
        mPaint.setShader(new LinearGradient(rect.centerX(), rect.top, rect.centerX(),
                rect.bottom, mBgColor, null, Shader.TileMode.CLAMP));
        canvas.drawRect(rect, mPaint);
    }

    public void setProgress(int high, int low) {

        this.mValueHigh = high;
        this.mValueLow = low;
        invalidate();
    }
}
