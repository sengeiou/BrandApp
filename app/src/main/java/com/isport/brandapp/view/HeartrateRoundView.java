package com.isport.brandapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.isport.brandapp.R;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


public class HeartrateRoundView extends View {
    float radius;

    float progress = 0;
    RectF arcRectF = new RectF();
    RectF roundRectF = new RectF();
    Context mContext;


    Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint arcCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private int mValue = 100;


    public HeartrateRoundView(Context context) {
        super(context);
    }

    public HeartrateRoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartrateRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    float centerX;
    float centerY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = getHeight() / 2;
        centerX = getHeight() / 2;
        centerY = getWidth() / 2;
    }

    private void initPaint() {


        circlepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 2));
        circlepaint.setColor(mContext.getResources().getColor(R.color.common_black_15));
        circlepaint.setStyle(Paint.Style.FILL);
        circlepaint.setStrokeCap(Paint.Cap.ROUND);


        //arcCirclepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 36));
        arcCirclepaint.setColor(mContext.getResources().getColor(R.color.white));
        arcCirclepaint.setStyle(Paint.Style.FILL);
        arcCirclepaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        arcRectF.set(0, 0, getWidth(), getHeight());
        //画底部圆柱
        canvas.drawRoundRect(arcRectF, radius, radius, circlepaint);


        if (mValue > 220) {
            mValue = 220;
        }

        if (mValue > 0) {
            //画左边圆角
//            canvas.drawCircle(radius, radius, radius, arcCirclepaint);
            //画进度
//            canvas.drawRect(radius, 0, getWidth()*mValue / 100, getHeight(), arcCirclepaint);
            roundRectF.set(0, 0, getWidth() * mValue / 220, getHeight());
            canvas.drawRoundRect(roundRectF, radius, radius, arcCirclepaint);
        }


    }

    public void setProgress(int value) {


        this.mValue = value;
//        this.mValue=50;
        invalidate();
    }
}
