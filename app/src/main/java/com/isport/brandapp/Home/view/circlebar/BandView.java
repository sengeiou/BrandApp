package com.isport.brandapp.Home.view.circlebar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.isport.brandapp.R;
import com.isport.brandapp.util.DeviceDataUtil;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class BandView extends View {
    float radius;

    float progress = 0;
    RectF arcRectF = new RectF();
    Context mContext;

    int currentType = JkConfiguration.DeviceType.WATCH_W516;

    float goalValue;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint arcCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ColorInt
    private int startColor;
    // ColorInt
    private int endColor;


    LinearGradient lg;

    public BandView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public BandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public BandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initPaint();
    }

    float centerX;
    float centerY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = (getWidth() - DisplayUtils.dip2px(mContext, 17)) / 2;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    private void initPaint() {


        startColor = mContext.getResources().getColor(R.color.common_start_color);
        endColor = mContext.getResources().getColor(R.color.common_end_color);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        AssetManager assets = mContext.getAssets();
        Typeface font = Typeface.createFromAsset(assets, "fonts/BebasNeue.otf");

        circlepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        circlepaint.setColor(mContext.getResources().getColor(R.color.common_item_line_color));
        circlepaint.setStyle(Paint.Style.STROKE);

        paint.setTypeface(font);
        paint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        paint.setColor(mContext.getResources().getColor(R.color.common_view_color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        arcCirclepaint.setTypeface(font);
        arcCirclepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        arcCirclepaint.setColor(mContext.getResources().getColor(R.color.common_view_color));
        arcCirclepaint.setStyle(Paint.Style.STROKE);
        arcCirclepaint.setStrokeCap(Paint.Cap.ROUND);

    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, circlepaint);
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        lg = new LinearGradient(0, 0, 100, arcRectF.right, endColor, startColor, Shader.TileMode.MIRROR);
        arcCirclepaint.setShader(lg);

        canvas.rotate(-90, centerX, centerY);

        canvas.drawArc(arcRectF, 0, progress * 3.6f, false, arcCirclepaint);


    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
        goalValue = DeviceDataUtil.getDeviceGoalValue(currentType);
    }
}
