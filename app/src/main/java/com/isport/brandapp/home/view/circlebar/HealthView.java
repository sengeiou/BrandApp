package com.isport.brandapp.home.view.circlebar;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
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

public class HealthView extends View {
    float radius, innerRadius;

    float progress = 0;
    RectF arcRectF = new RectF();
    Context mContext;

    int currentType = JkConfiguration.DeviceType.WATCH_W516;

    float goalValue;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint circlepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint arcCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ColorInt
    private int startColor;
    // ColorInt
    private int endColor;


    LinearGradient lg;

    public HealthView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public HealthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public HealthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        innerRadius = (getWidth() - DisplayUtils.dip2px(mContext, 35)) / 2;
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


        linePaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 2));
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);


        circlepaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        circlepaint.setColor(mContext.getResources().getColor(R.color.common_start_color));
        circlepaint.setStyle(Paint.Style.STROKE);
        circlepaint.setStrokeCap(Paint.Cap.ROUND);

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

    private int mainColor = Color.parseColor("#000000");//画笔颜色
    private float mWidth, mHeight;//视图宽高
    private float arcRa = 0;//圆半径
    private Double rr = 2 * Math.PI / 60;//2π即360度的圆形分成60份,一秒钟与一分钟
    private Double rr2 = 2 * Math.PI / 12;//2π圆形分成12份,圆形显示12个小时的刻度
    private PointF secondStartPoint, minuteStartPoint, hourStartPoint;//秒,分,时的坐标点
    private int startSecond, startMinute, startHour;//初始化时秒,分,时获取的系统时间
    private Rect textBound = new Rect();//字体被全部包裹的最小的矩形边框

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawCircle(centerX, centerY, radius, linePaint);
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.rotate(135, centerX, centerY);

        lg = new LinearGradient(0, 0, 100, arcRectF.right, endColor, startColor, Shader.TileMode.MIRROR);
        arcCirclepaint.setShader(lg);


        canvas.drawArc(arcRectF, 0, 100 * 2.7f, false, circlepaint);

        canvas.drawArc(arcRectF, 0, progress * 2.7f, false, arcCirclepaint);
        canvas.save();

        //画圆,通过获取宽高算出最短一边作为直径，坐标原点默认在手机屏幕左上角
        //canvas.drawCircle(arcRa, arcRa, arcRa, paint);

        //围绕圆形绘制刻度,坐标原点默认在手机屏幕左上角
        canvas.rotate(88, centerX, centerY);
        for (int i = 0; i < 47; i++) {///2π圆形分成60份,一秒钟与一分钟,所以要绘制60次,这里是从0到59
            float x1, y1, x2, y2;//刻度的两端的坐标即起始于结束的坐标
            float scale;//每个刻度离圆心的最近端坐标点到圆心的距离
            Double du = rr * i;//当前所占的角度

            // Double hudu = 6 * (Math.PI / 180) *  i;

            Double sinx = Math.sin(du);//该角度的sin值
            Double cosy = Math.cos(du);//该角度的cos值
            x1 = (float) (centerX + innerRadius * sinx);//以默认坐标系通过三角函数算出刻度离圆心最远的端点的x轴坐标
            y1 = (float) (centerY - innerRadius * cosy);//以默认坐标系通过三角函数算出刻度离圆心最远的端点的y轴坐标
           /* if (i % 5 == 0) {//筛选刻度长度
                scale = 5 * radius / 6;//长刻度绘制,刻度离圆心的最近端坐标点到圆心的距离,这里取半径的五分之六的长度,可以通过情况来定
            } else {
                scale = 9 * radius / 10;//短刻度绘制,这里取半径的十分之六九的长度,可以通过情况来定
            }*/
            scale = 9 * innerRadius / 10;
            x2 = (float) (centerX + scale * sinx);//以默认坐标系通过三角函数算出该刻度离圆心最近的端点的x轴坐标
            y2 = (float) (centerY - scale * cosy);//以默认坐标系通过三角函数算出该刻度离圆心最近的端点的y轴坐标
            canvas.drawLine(x1, y1, x2, y2, linePaint);//通过两端点绘制刻度
        }


    }

    public void setCurrentType(int currentType) {
        this.currentType = currentType;
        goalValue = DeviceDataUtil.getDeviceGoalValue(currentType);
    }
}
