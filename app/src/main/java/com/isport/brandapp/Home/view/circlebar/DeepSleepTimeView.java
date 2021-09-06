package com.isport.brandapp.Home.view.circlebar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.isport.brandapp.R;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class DeepSleepTimeView extends View {
    float radius;

    float progress = 0;
    RectF arcRectF = new RectF();
    Context mContext;

    int currentType = JkConfiguration.DeviceType.WATCH_W516;
    int progreesVaule;

    float goalValue;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textUnitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DeepSleepTimeView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public DeepSleepTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public DeepSleepTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initPaint();
    }

    private void initPaint() {
        textUnitPaint.setTextSize(DisplayUtils.dip2px(mContext, 20));
        textUnitPaint.setColor(Color.WHITE);
        textUnitPaint.setStyle(Paint.Style.FILL);
        textValuePaint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        textUnitPaint.setColor(mContext.getResources().getColor(R.color.common_white));
        textValuePaint.setColor(mContext.getResources().getColor(R.color.common_white));
        textUnitPaint.setStyle(Paint.Style.FILL);
        circlepaint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        AssetManager assets = mContext.getAssets();
        Typeface font = Typeface.createFromAsset(assets, "fonts/BebasNeue.otf");
        paint.setTypeface(font);
        paint.setColor(mContext.getResources().getColor(R.color.common_view_color));
        circlepaint.setColor(mContext.getResources().getColor(R.color.common_item_bg_color));
/*        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);*/
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //212--250
        radius = (getWidth() - DisplayUtils.dip2px(mContext, 38)) / 2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        //canvas.drawCircle(centerX, centerY, radius, circlepaint);
        paint.setColor(mContext.getResources().getColor(R.color.common_sleep_deep_color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(arcRectF, -90, progress * 3.6f, false, paint);


        float progressStartRadians = (float) ((360.0f / 2) / 180 * Math.PI);
        float startX;
        float startY;


        if (progress >= 50) {
            startX = centerX - radius * (float) Math.sin(progressStartRadians) + DisplayUtils.dip2px(mContext, 0);
        } else {
            startX = centerY - radius * (float) Math.sin(progressStartRadians) - DisplayUtils.dip2px(mContext, 0);

        }
        if (progress >= 25 && progress <= 75) {
            startY = centerX + radius * (float) Math.cos(progressStartRadians) - DisplayUtils.dip2px(mContext, 0);
        } else {
            startY = centerY + radius * (float) Math.cos(progressStartRadians) + DisplayUtils.dip2px(mContext, 0);
        }
        //画进度条上的圈圈
        Paint thumbStartPaint;
        thumbStartPaint = new Paint();
        thumbStartPaint.setAntiAlias(true);
        Bitmap progressStartMark = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_sleep_end);
        canvas.drawBitmap(progressStartMark, startX - progressStartMark.getWidth() / 2, startY - progressStartMark.getHeight() / 2, thumbStartPaint);
    }

}
