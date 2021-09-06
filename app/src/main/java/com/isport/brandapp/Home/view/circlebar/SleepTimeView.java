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

public class SleepTimeView extends View {
    float radius;

    float progress = 0;

    float deepSleepTime, sleepTime;

    RectF arcRectF = new RectF();
    Context mContext;

    int currentType = JkConfiguration.DeviceType.WATCH_W516;
    int progreesVaule;

    float goalValue;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint sleepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textUnitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SleepTimeView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public SleepTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public SleepTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        sleepPaint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        sleepPaint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        AssetManager assets = mContext.getAssets();
        Typeface font = Typeface.createFromAsset(assets, "fonts/BebasNeue.otf");
        paint.setTypeface(font);
        paint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        paint.setColor(mContext.getResources().getColor(R.color.common_view_color));
        sleepPaint.setColor(mContext.getResources().getColor(R.color.common_sleep_time_color));
        circlepaint.setColor(mContext.getResources().getColor(R.color.common_item_line_color));
        circlepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
/*        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);*/
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = (getWidth() - DisplayUtils.dip2px(mContext, 38)) / 2;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        //这里需要做一次转换
        this.deepSleepTime = progress / 2;
        this.progress = progress;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        canvas.drawCircle(centerX, centerY, radius, circlepaint);
        paint.setColor(mContext.getResources().getColor(R.color.common_sleep_time_color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(arcRectF, -90, progress * 3.6f, false, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);


        float sweep1 = 360 * progress / 100; //进度划过的角度
        float progressRadians = (float) ((360.0f / 2 + sweep1) / 180 * Math.PI);
        float progressStartRadians = (float) ((360.0f / 2) / 180 * Math.PI);
        float thumbX;
        float thumbY;
        if (progress >= 50) {
            thumbX = centerX - radius * (float) Math.sin(progressRadians) + DisplayUtils.dip2px(mContext, 0);
        } else {
            thumbX = centerY - radius * (float) Math.sin(progressRadians) - DisplayUtils.dip2px(mContext, 0);

        }
        if (progress >= 25 && progress <= 75) {
            thumbY = centerX + radius * (float) Math.cos(progressRadians) - DisplayUtils.dip2px(mContext, 0);
        } else {
            thumbY = centerY + radius * (float) Math.cos(progressRadians) + DisplayUtils.dip2px(mContext, 0);
        }
        //画进度条上的圈圈
        Paint thumbPaint;
        thumbPaint = new Paint();
        thumbPaint.setAntiAlias(true);
        Bitmap progressMark = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_sleep_start);
        canvas.drawBitmap(progressMark, thumbX - progressMark.getWidth() / 2, thumbY - progressMark.getHeight() / 2, thumbPaint);
    }

}
