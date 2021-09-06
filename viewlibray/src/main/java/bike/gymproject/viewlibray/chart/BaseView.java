package bike.gymproject.viewlibray.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

import bike.gymproject.viewlibray.DptopxUtil;
import bike.gymproject.viewlibray.R;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

/**
 * Created by peng on 2018/6/13.
 */

public abstract class BaseView extends View {

    public Context mContex;
    protected Paint mPaint;
    protected Paint mLinePaint;
    protected Paint mShardePaint;
    protected Paint mCirclePaint;
    protected Paint mTextPaint;
    protected Rect mTextBound;
    protected Paint mBgPaint;
    protected Paint mMaxOrMinPaint;


    /**
     * 视图的宽和高
     */
    protected float width, height;

    /**
     * 起点的X与起点的Y坐标值
     */
    protected float originalX = 20, originalY = 500;
    protected float lineStartX = 120;


    protected float marginTop = 20, marginBottom = 20;

    protected int currentMaxValue, currentMinValue;
    protected int topValue;

    protected int maxIndex, minIndex;


    private int colorLine, colorBg, colorText, colorY, colorMaxOrMin;

    private float textsize;


    protected float sumdis;


    ArrayList<Integer> lineBeans;


    protected float mAxisTextSize;
    protected int mAxisTextColor;

    /**
     * 刻度等分
     */
    protected float axisDivideSiseX;
    protected float axisDivideSiseY;


    protected int margin;

    protected float handTop;


    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义样式

        mContex = context;
        handTop = 100;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphStyle);
        mAxisTextColor = typedArray.getColor(R.styleable.GraphStyle_axisTextColor, Color.BLACK);
        colorMaxOrMin = typedArray.getColor(R.styleable.GraphStyle_maxorminColor, Color.BLACK);
        mAxisTextSize = typedArray.getFloat(R.styleable.GraphStyle_axisTextSize, 14);
        colorBg = typedArray.getColor(R.styleable.GraphStyle_graphBgColor, context.getResources().getColor(R.color.common_view_Bg));
        colorY = typedArray.getColor(R.styleable.GraphStyle_yAxisColor, context.getResources().getColor(R.color.common_item_line_color));
        colorLine = typedArray.getColor(R.styleable.GraphStyle_lineColor, context.getResources().getColor(R.color.common_rec));
        marginBottom = typedArray.getDimension(R.styleable.GraphStyle_viewMargin, 10);
        lineStartX = typedArray.getDimension(R.styleable.GraphStyle_lineStartX, 30);
        originalX = typedArray.getDimension(R.styleable.GraphStyle_startMargin, 10);
        colorText = typedArray.getColor(R.styleable.GraphStyle_axisTextColor, context.getResources().getColor(R.color.common_item_line_color));
        margin = DptopxUtil.dp2px(3, context);
        if (typedArray != null) {
            typedArray.recycle();
        }

        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // height = getHeight();

        /*
         * originalY = -height; width = getWidth() - originalX;
         */

        // originalY = height - DisplayUtils.px2dip(mContex, 20);
        width = getWidth();
        height = getHeight() - 2 * marginBottom;
        originalY = getHeight() - marginBottom;
        // height = (originalY > getHeight() ? getHeight() : originalY) - 400;
       /* if (originalY == 0 || originalY == handTop || originalY == 50) {
            height = getHeight();
        } else {
            height = (originalY > getHeight() ? getHeight() : originalY);
        }*/
        /*for (int i = 0; i < lineBeans.size(); i++) {

            lineBeans.get(i).screenStartPoint = width / sumdis * lineBeans.get(i).startPoint;
            lineBeans.get(i).screenEndPoint = width / sumdis * lineBeans.get(i).endPoint;
        }*/
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(colorY);
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            mPaint.setStrokeWidth(2);
            mPaint.setPathEffect(new DashPathEffect(new float[]{15, 2}, 0));
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
        }
        if (mMaxOrMinPaint == null) {
            mMaxOrMinPaint = new Paint();
            mMaxOrMinPaint.setStyle(Paint.Style.FILL);
            //mTextPaint.setStrokeWidth(5);
            mMaxOrMinPaint.setTextAlign(Paint.Align.CENTER);
            mMaxOrMinPaint.setTextSize(DisplayUtils.dip2px(mContex, 12));
            mMaxOrMinPaint.setColor(colorMaxOrMin);   // 绘画字体的颜色
        }
        if (mLinePaint == null) {
            mLinePaint = new Paint();
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setColor(colorLine);
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            mLinePaint.setStrokeWidth(3);
            mLinePaint.setDither(true);
            mLinePaint.setAntiAlias(true);
        }
        if (mBgPaint == null) {
            mBgPaint = new Paint();
            mBgPaint.setStyle(Paint.Style.FILL);
            mBgPaint.setColor(colorBg);
            mBgPaint.setDither(true);
            mBgPaint.setAntiAlias(true);
            mBgPaint.setDither(true);
            mBgPaint.setAntiAlias(true);

        }
        if (mShardePaint == null) {
            mShardePaint = new Paint();
            mShardePaint.setStyle(Paint.Style.STROKE);
            //mShardePaint.setShadowLayer(5, 0, 0, mContex.getColor(R.color.white));
            mShardePaint.setColor(ContextCompat.getColor(mContex, R.color.common_text_color));   // 绘画字体的颜色
            mShardePaint.setStrokeWidth(3);
            mShardePaint.setDither(true);
            mShardePaint.setAntiAlias(true);
        }
        if (mCirclePaint == null) {
            mCirclePaint = new Paint();
            mCirclePaint.setStyle(Paint.Style.FILL);
            mCirclePaint.setColor(colorLine);
            mCirclePaint.setStrokeWidth(5);
            mCirclePaint.setDither(true);
            mCirclePaint.setAntiAlias(true);
        }
        if (mTextPaint == null) {
            mTextPaint = new Paint();
            mTextPaint.setStyle(Paint.Style.FILL);
            //mTextPaint.setStrokeWidth(5);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(DisplayUtils.dip2px(mContex, 12));
            mTextPaint.setColor(Color.parseColor("#9399A5"));   // 绘画字体的颜色
            mTextBound = new Rect();
            String mText = "023";
            //mPaint.getTextBounds(mText, 0, 3, mTextBound);


            /*Paint bgRect = new Paint();
            bgRect.setStyle(Paint.Style.FILL);
            bgRect.setColor(Color.YELLOW);
            RectF rectF = new RectF(200, 200, 800, 600);
            // canvas.drawRect(rectF, bgRect);

            Paint textPaint = new Paint();
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setStrokeWidth(8);
            textPaint.setTextSize(50);
            textPaint.setTextAlign(Paint.Align.CENTER);

            String text = "测试：my text";
            //计算baseline
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            float baseline = rectF.centerY() + distance;*/
            //canvas.drawText(text, rectF.centerX(), baseline, textPaint);


            //mTextPaint.setTextSize(30);
           /* String mText = "02";
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
            mTextBound = new Rect();
            mPaint.getTextBounds(mText, 0, 2, mTextBound);*/
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 屏幕的宽减去起始点X轴的坐标
         */

        drawBg(canvas, mBgPaint);
        drawXAxis(canvas, mPaint);
        drawColumn(canvas, mLinePaint);
        drawYAxis(canvas, mTextPaint);

        drawTile(canvas, mPaint);

        drawXAxisScale(canvas, mTextPaint);

        drawXAxisScaleValue(canvas, mPaint);

        drawYAxisScale(canvas, mTextPaint);

        drawYAxisScaleValue(canvas, mPaint);

        drawXAxisArrow(canvas, mPaint);

        drawYAxisArrow(canvas, mPaint);

        drawMaxOrMin(canvas, mMaxOrMinPaint);

    }

    /**
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawColumn(Canvas canvas, Paint mPaint);

    /**
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint mPaint);

    /**
     * @param canvas
     * @param mPaint
     */
    private void drawYAxisArrow(Canvas canvas, Paint mPaint) {
      /*  Path mPathY = new Path();
        mPathY.moveTo(originalX, originalY - height - 30);
        mPathY.moveTo(originalX - 10, originalY - height);
        mPathY.moveTo(originalX + 10, originalY - height);

        mPathY.close();
        canvas.drawPath(mPathY, mPaint);*/
        // canvas.drawText(mYAxisNmae, originalX - 50, originalY - height - 30, mPaint);
    }

    /**
     * 完全自己自定义的方法哦，你甚至可以让不管设置什么都返回固定大小!
     *
     * @param measureSpec 传入的measureSpec
     * @return 处理后的大小
     */
    private int measure(int measureSpec) {
        int result = 0;
        // 分别获取测量模式 和 测量大小
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // 如果是精确度模式，呢就按xml中定义的来
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        // 如果是最大值模式，就按我们定义的来
        else if (specMode == MeasureSpec.AT_MOST) {
            result = DptopxUtil.getScreenWidth(mContex);
            // result = DisplayUtils.getScreenHeight(mContex) / 4;
            // result = 260;
        }

        return result;
    }

    /**
     * 画X轴的箭头
     *
     * @param canvas
     * @param mPaint
     */
    private void drawXAxisArrow(Canvas canvas, Paint mPaint) {
     /*   Path mPathX = new Path();
        mPathX.moveTo(originalX + width + 30, originalY);
        mPathX.moveTo(originalX + width, originalY + 10);
        mPathX.moveTo(originalX + width, originalY - 10);

        mPathX.close();
        canvas.drawPath(mPathX, mPaint);*/
        // canvas.drawText(mXAxisName, originalX + width, originalY + 50, mPaint);
    }

    /**
     * 画X轴的箭头
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawBg(Canvas canvas, Paint mPaint);

    /**
     * 画
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawYAxisScale(Canvas canvas, Paint mPaint);


    protected abstract void drawMaxOrMin(Canvas canvas, Paint mPaint);

    /**
     * 画X轴的刻度值
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint mPaint);

    /**
     * 画X轴刻度
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawXAxisScale(Canvas canvas, Paint mPaint);

    /**
     * 画图表的标题
     *
     * @param canvas
     * @param mPaint
     */
    private void drawTile(Canvas canvas, Paint mPaint) {

    }

    /**
     * 画Y轴
     *
     * @param canvas
     * @param mPaint
     */
    protected abstract void drawYAxis(Canvas canvas, Paint mPaint);

    /**
     * 画X轴
     *
     * @param canvas
     * @param mPaint
     */

    protected abstract void drawXAxis(Canvas canvas, Paint mPaint);


    public void setPointInfo(ArrayList<Integer> listBeans) {

        if (listBeans.size() == 0) {

            topValue = 4;
            lineBeans = listBeans;

        } else {

            lineBeans = listBeans;
            currentMaxValue = Collections.max(listBeans);
            currentMinValue = Collections.min(listBeans);

            maxIndex = listBeans.indexOf(currentMaxValue);
            minIndex = listBeans.indexOf(currentMinValue);

            int remainder = currentMaxValue % 5;

            if (currentMaxValue <= 5) {

            }

            if (remainder != 0) {
                topValue = currentMaxValue + 5 - remainder;
            } else {

                if (currentMaxValue == 0) {
                    topValue = 4;
                } else {
                    topValue = currentMaxValue + 5;
                }
            }
        }
        //invalidate();


        //currentMaxValue=currentMaxValue==0?currentMaxValue=4:currentMaxValue;
       /* int a= currentMaxValue/4;
        topValue=a%5;
        if (topValue!=0&&a>=5)
        {
            a+=5-topValue;
            topValue=a;
        }*/


    }

    public void setYAxisScaleValue(float axisDivideSiseY) {
        this.axisDivideSiseY = axisDivideSiseY;
    }

    public void setOriginal(float originalX, float originalY) {
        this.originalX = originalX;
        this.originalY = originalY;
    }

}

