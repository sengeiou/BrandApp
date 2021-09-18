package com.isport.brandapp.home.view.circlebar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class CirclebarAnimatorView extends View {
    float radius;

    float progress = 0;
    RectF arcRectF = new RectF();
    Context mContext;

    int currentType = JkConfiguration.DeviceType.WATCH_W516;
    int progreesVaule;

    float goalValue;

    LinearGradient lg;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint arcCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textUnitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ColorInt
    private int startColor;
    // ColorInt
    private int endColor;

    public CirclebarAnimatorView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public CirclebarAnimatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public CirclebarAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initPaint();
    }

    private void initPaint() {

        startColor = mContext.getResources().getColor(R.color.common_start_color);
        endColor = mContext.getResources().getColor(R.color.common_end_color);
        textUnitPaint.setTextSize(DisplayUtils.dip2px(mContext, 20));
        textUnitPaint.setColor(Color.WHITE);
        textUnitPaint.setStyle(Paint.Style.FILL);
        textValuePaint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        textUnitPaint.setColor(mContext.getResources().getColor(R.color.common_white));
        textValuePaint.setColor(mContext.getResources().getColor(R.color.common_white));
        textUnitPaint.setStyle(Paint.Style.FILL);
        circlepaint.setStyle(Paint.Style.STROKE);
        circlepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        AssetManager assets = mContext.getAssets();
        Typeface font = Typeface.createFromAsset(assets, "fonts/BebasNeue.otf");
        paint.setTypeface(font);

        paint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        arcCirclepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        arcCirclepaint.setStyle(Paint.Style.STROKE);
        arcCirclepaint.setStrokeCap(Paint.Cap.ROUND);

        paint.setColor(mContext.getResources().getColor(R.color.common_view_color));
        circlepaint.setColor(mContext.getResources().getColor(R.color.common_item_line_color));
/*        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);*/
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = (getWidth() - DisplayUtils.dip2px(mContext, 10)) / 2;
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


        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        canvas.drawCircle(centerX, centerY, radius, circlepaint);
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        lg = new LinearGradient(0, 0, 100, arcRectF.right, endColor, startColor, Shader.TileMode.MIRROR);
        arcCirclepaint.setShader(lg);

        canvas.rotate(-90, centerX, centerY);

        canvas.drawArc(arcRectF, 0, progress * 3.6f, false, arcCirclepaint);

       /* paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);*/


//        canvas.drawText(strProg, centerX, centerY - (paint.ascent() + paint.descent()) / 2, paint);
//        canvas.drawText(strUnit, centerX + (paint.measureText(strProg) / 2) + DisplayUtils.dip2px(mContext, 8),
//                        centerY - (paint.ascent() + paint.descent()) / 2, textUnitPaint);
    }

    public void setCurrentType(int crrentType) {
        this.currentType = crrentType;
        goalValue = DeviceDataUtil.getDeviceGoalValue(currentType);
    }
}
