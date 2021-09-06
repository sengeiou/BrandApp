package bike.gymproject.viewlibray;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import phone.gym.jkcq.com.commonres.commonutil.CalculateUtil;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


/**
 * Created by lsp on 2017/10/13 10 47
 * Email:6161391073@qq.com
 */

public class HeartRateIngView extends View {
    private static final String TAG = "HeartRateIngView";
    /**
     * 2个大刻度之间间距，默认为1
     */
    private int scaleLimit = 1;
    /**
     * 尺子高度
     */
    private int rulerHeight = 50;

    /**
     * 尺子和屏幕顶部以及结果之间的高度
     */
    private int rulerToResultgap = rulerHeight / 4;
    /**
     * 刻度平分多少份
     */
    private int scaleCount = 1;  //刻度评分多少份
    /**
     * 刻度最小值
     */
    private int minScale = 0;
    /**
     * 刻度间距
     */
    private int scaleGap = 1;
    /**
     * 第一次显示的刻度
     */
    private float firstScale = 0f;
    /**
     * 刻度最大值
     */
    private int maxScale = 100;

    /**
     * 背景颜色
     */
    private int bgColor = 0xfffcfffc;
    /**
     * 小刻度的颜色
     */
    private int smallScaleColor = 0xff999999;
    /**
     * 中刻度的颜色
     */
    private int midScaleColor = 0xff666666;
    /**
     * 大刻度的颜色
     */
    private int largeScaleColor = 0xff50b586;
    /**
     * 刻度颜色
     */
    private int scaleNumColor = 0xff333333;
    /**
     * 结果值颜色
     */
    private int resultNumColor = 0xffFD3C30;
    private int axisColor = 0xff9399A5;
    /**
     * kg颜色
     */
    private String unit = "BPM";
    /**
     * kg颜色
     */
    private int unitColor = 0xff50b586;
    /**
     * 小刻度粗细大小
     */
    private int smallScaleStroke = 1;
    /**
     * 中刻度粗细大小
     */
    private int midScaleStroke = 2;
    /**
     * 大刻度粗细大小
     */
    private int largeScaleStroke = 3;
    /**
     * 结果字体大小
     */
    private int resultNumTextSize = 20;
    /**
     * 刻度字体大小
     */
    private int scaleNumTextSize = 16;
    /**
     * 单位字体大小
     */
    private int unitTextSize = 12;
    /**
     * 是否显示刻度结果
     */
    private boolean showScaleResult = true;
    /**
     * 是否背景显示圆角
     */
    private boolean isBgRoundRect = true;

    /**
     * 结果回调
     */
    private OnChooseResulterListener onChooseResulterListener;
    /**
     * 滑动选择刻度
     */
    private float computeScale = -1;
    /**
     * 当前刻度
     */
    public float currentScale = firstScale;

    int currentValue = 0;

    List<Integer> hrList = new ArrayList<>();
    List<String> timesList = new ArrayList<>();

    private ValueAnimator valueAnimator;
    private VelocityTracker velocityTracker = VelocityTracker.obtain();
    private String resultText = "0", timesText = "0";
    private Paint bgPaint;
    private Paint smallScalePaint;
    private Paint midScalePaint;
    private Paint lagScalePaint;
    private Paint scaleNumPaint;
    private Paint resultNumPaint;
    private Paint kgPaint;
    private Rect scaleNumRect;
    private Rect resultNumRect;
    private Rect kgRect;
    private RectF bgRect;
    private int height, width;
    private int smallScaleHeight;
    private int midScaleHeight;
    private int lagScaleHeight;
    private int rulerRight = 0;
    private int resultNumRight;
    private float downX;
    private float moveX = 0;
    private float currentX;
    private float lastMoveX = 0;
    private boolean isUp = false;
    private int leftScroll;
    private int rightScroll;
    private int xVelocity;
    private Paint axisPaint;
    private Context mContext;
    private int paddingRight = 0;
    private int rightMargin = 40;
    private int paddingBottom = 0;
    private Paint mPaintShader;
    private RectF mDrawArea;
    private Paint mPaint;
//    private Path mPathShader;

    public HeartRateIngView(Context context) {
        this(context, null);
    }

    public HeartRateIngView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartRateIngView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setAttr(attrs, defStyleAttr);
        init(context);
    }

    public void setOnChooseResulterListener(OnChooseResulterListener onChooseResulterListener) {
        this.onChooseResulterListener = onChooseResulterListener;
    }

    private void setAttr(AttributeSet attrs, int defStyleAttr) {

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.HeartRateIngView, defStyleAttr, 0);

        scaleLimit = a.getInt(R.styleable.HeartRateIngView_scaleLimit, scaleLimit);

        rulerHeight = a.getDimensionPixelSize(R.styleable.HeartRateIngView_rulerHeight, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, rulerHeight, getResources().getDisplayMetrics()));

        rulerToResultgap = a.getDimensionPixelSize(R.styleable.HeartRateIngView_rulerToResultgap, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, rulerToResultgap, getResources().getDisplayMetrics()));

        scaleCount = a.getInt(R.styleable.HeartRateIngView_scaleCount, scaleCount);

        scaleGap = a.getDimensionPixelSize(R.styleable.HeartRateIngView_scaleGap, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, scaleGap, getResources().getDisplayMetrics()));

        minScale = a.getInt(R.styleable.HeartRateIngView_minScale, minScale) / scaleLimit;

        firstScale = a.getFloat(R.styleable.HeartRateIngView_firstScale, firstScale) / scaleLimit;

        maxScale = a.getInt(R.styleable.HeartRateIngView_maxScale, maxScale) / scaleLimit;

        bgColor = a.getColor(R.styleable.HeartRateIngView_bgColor, bgColor);

        smallScaleColor = a.getColor(R.styleable.HeartRateIngView_smallScaleColor, smallScaleColor);

        midScaleColor = a.getColor(R.styleable.HeartRateIngView_midScaleColor, midScaleColor);

        largeScaleColor = a.getColor(R.styleable.HeartRateIngView_largeScaleColor, largeScaleColor);

        scaleNumColor = a.getColor(R.styleable.HeartRateIngView_scaleNumColor, scaleNumColor);

        resultNumColor = a.getColor(R.styleable.HeartRateIngView_resultNumColor, resultNumColor);


        String tempUnit = unit;
        unit = a.getString(R.styleable.HeartRateIngView_unit);
        if (TextUtils.isEmpty(unit)) {
            unit = tempUnit;
        }

        smallScaleStroke = a.getDimensionPixelSize(R.styleable.HeartRateIngView_smallScaleStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, smallScaleStroke, getResources().getDisplayMetrics()));

        midScaleStroke = a.getDimensionPixelSize(R.styleable.HeartRateIngView_midScaleStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, midScaleStroke, getResources().getDisplayMetrics()));
        largeScaleStroke = a.getDimensionPixelSize(R.styleable.HeartRateIngView_largeScaleStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, largeScaleStroke, getResources().getDisplayMetrics()));
        resultNumTextSize = a.getDimensionPixelSize(R.styleable.HeartRateIngView_resultNumTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, resultNumTextSize, getResources().getDisplayMetrics()));

        scaleNumTextSize = a.getDimensionPixelSize(R.styleable.HeartRateIngView_scaleNumTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, scaleNumTextSize, getResources().getDisplayMetrics()));

        unitTextSize = a.getDimensionPixelSize(R.styleable.HeartRateIngView_unitTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, unitTextSize, getResources().getDisplayMetrics()));


        showScaleResult = a.getBoolean(R.styleable.HeartRateIngView_showScaleResult, showScaleResult);
        isBgRoundRect = a.getBoolean(R.styleable.HeartRateIngView_isBgRoundRect, isBgRoundRect);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //invalidate();
    }

    private void init(Context context) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);
            mPaint.setColor(context.getResources().getColor(R.color.common_tips_color));//#F3546A
            mPaint.setTextSize(unitTextSize);
        }

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        midScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lagScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resultNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        kgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bgPaint.setColor(bgColor);
        smallScalePaint.setColor(smallScaleColor);
        midScalePaint.setColor(midScaleColor);
        lagScalePaint.setColor(largeScaleColor);
        scaleNumPaint.setColor(scaleNumColor);
        resultNumPaint.setColor(resultNumColor);
        kgPaint.setColor(unitColor);

        resultNumPaint.setStyle(Paint.Style.FILL);
        kgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStyle(Paint.Style.FILL);
        smallScalePaint.setStyle(Paint.Style.FILL);
        midScalePaint.setStyle(Paint.Style.FILL);
        lagScalePaint.setStyle(Paint.Style.FILL);

        lagScalePaint.setStrokeCap(Paint.Cap.ROUND);
        midScalePaint.setStrokeCap(Paint.Cap.ROUND);
        smallScalePaint.setStrokeCap(Paint.Cap.ROUND);

        smallScalePaint.setStrokeWidth(smallScaleStroke);
        midScalePaint.setStrokeWidth(midScaleStroke);
        lagScalePaint.setStrokeWidth(largeScaleStroke);

        resultNumPaint.setTextSize(resultNumTextSize);
        kgPaint.setTextSize(unitTextSize);
        scaleNumPaint.setTextSize(scaleNumTextSize);

        bgRect = new RectF();
        resultNumRect = new Rect();
        scaleNumRect = new Rect();
        kgRect = new Rect();

        resultNumPaint.getTextBounds(resultText, 0, resultText.length(), resultNumRect);
        kgPaint.getTextBounds(unit, 0, unit.length(), kgRect);

        smallScaleHeight = rulerHeight / 4;
        midScaleHeight = rulerHeight / 2;
        lagScaleHeight = rulerHeight / 2 + 5;
        valueAnimator = new ValueAnimator();

       /* axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisPaint.setColor(axisColor);
        axisPaint.setStrokeWidth(1);
        axisPaint.setTextSize(unitTextSize);
        axisPaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));*/
        axisPaint = new Paint();
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setColor(context.getResources().getColor(R.color.common_item_line_color));//#F3546A
        // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
        axisPaint.setStrokeWidth(2);
        axisPaint.setPathEffect(new DashPathEffect(new float[]{15, 2}, 0));
        axisPaint.setDither(true);
        axisPaint.setAntiAlias(true);
        //阴影画笔
        mPaintShader = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintShader.setStrokeWidth(2f);

        //  初始化渐变色
        shadeColors = new int[]{0xFFF3546A, 0x88F3546A, 0x00F3546A};
        mDrawArea = new RectF(0, 0, 0, 0);

    }

    private void setPaintShader() {
        Shader shader = new LinearGradient(0, 0, 0, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), shadeColors, null, Shader.TileMode.CLAMP);
        mPaintShader.setShader(shader);
    }

    //  初始化渐变色
    private int[] shadeColors;

    /**
     * 计算出Y轴最大值
     *
     * @return
     */
    private float calculateMax(List<Integer> hrList) {
        int start = Collections.max(hrList);
        if (start <= 250) {
            start = 250;
        }
        return start;
    }

    /**
     * Y轴最大的刻度值
     */
    private float maxYDivisionValue;
    /**
     * 下面两个相当于图表的原点
     */
    private float mStartX;
    private int mStartY, maxHeight;

    /**
     * 得到柱状图的最大和最小的分度值
     */
    private void getRange(float maxYValue) {
        mStartY = height - bottomMargin - paddingBottom;
        maxHeight = mStartY - paddingBottom - topMargin;
        int scale = CalculateUtil.getScale(maxYValue);//获取这个最大数 数总共有几位
        float unScaleValue = (float) (maxYValue / Math.pow(10, scale));//最大值除以位数之后剩下的值  比如1200/1000 后剩下1.2
        maxYDivisionValue = (float) (unScaleValue * Math.pow(10, scale));//获取Y轴的最大的分度值
        mStartX = CalculateUtil.getDivisionTextMaxWidth(1000, mContext) + DisplayUtils.dip2px(mContext, 10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightModule = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (heightModule) {
            case MeasureSpec.AT_MOST:
                height = rulerHeight + (showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap * 2 + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.EXACTLY:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;
        }

        width = widthSize + getPaddingLeft() + getPaddingRight();

        setMeasuredDimension(width, height);

    }

    private int bottomMargin = 40;
    private int topMargin = 40;

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        //画左边的Y轴
        // canvas.drawLine(mStartX, mStartY + bottomMargin / 2, mStartX, topMargin / 2, axisPaint);
        //画左边的Y轴刻度
        drawScaleLine(canvas);
        canvas.clipRect(new RectF(mStartX, 0, width, height));
        drawScaleAndNum(canvas);

    }

    private void drawScaleLine(Canvas canvas) {
        getRange(250);
        float eachHeight = (maxHeight / 5f);


        Log.i("drawScaleLine", maxHeight + "" + "maxYValue=" + maxYValue);
        if (maxHeight == 0) {
            return;
        }


        ArrayList<Integer> yList = new ArrayList<>();
        yList.add(50);
        yList.add(150);
        yList.add(250);

        for (int i = 0; i < yList.size(); i++) {
            float startY = mStartY - ((yList.get(i)) / 250.0f * maxHeight);//rulerToResultgap - c
            String text = yList.get(i) + "";
            mPaint.setColor(mContext.getResources().getColor(R.color.common_tips_color));//#F3546A
            canvas.drawText(text, mStartX - mPaint.measureText(text) + DisplayUtils.dip2px(mContext, 0), startY + mPaint.measureText("0") / 2, mPaint);
            //mPaint.setColor(Parse.col);

            mPaint.setColor(mContext.getResources().getColor(R.color.common_bg));//#F3546A
            canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, mPaint);
        }


       /* Log.i("drawScaleLine", maxHeight + "" + "maxYValue=" + maxYValue);
        if (maxHeight == 0) {
            return;
        }

        //  canvas.drawLine(width / 2, maxHeight - ((currentValue) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2 + smallScaleStroke, width / 2, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), lagScalePaint);


        //float startY=maxHeight - ((0) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2 + smallScaleStroke;


        //canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, mPaint);


        eachHeight = (maxHeight - rulerToResultgap + topMargin / 2) / 5f;


        float textValue = 0;
        if (maxYValue > 1) {
            for (int i = 0; i <= 5; i++) {
                float startY;
                if (i != 0) {
                    startY = mStartY - eachHeight * i - DisplayUtils.dip2px(mContext, 4)*i;
                } else {
                    startY = mStartY - eachHeight * i;
                }
                BigDecimal maxValue = new BigDecimal(maxYDivisionValue);
                BigDecimal fen = new BigDecimal(0.2 * i);
                String text = null;
                //因为图表分了5条线，如果能除不进，需要显示小数点不然数据不准确
                *//*if (maxYDivisionValue % 5 != 0) {
                    text = String.valueOf(maxValue.multiply(fen).floatValue());
                } else {
                    text = String.valueOf(maxValue.multiply(fen).longValue());
                }*//*
                text = CommonDateUtil.formatInterger(maxValue.multiply(fen).longValue());
                canvas.drawText(text, mStartX - mPaint.measureText(text) + DisplayUtils.dip2px(mContext, 0), startY + mPaint.measureText("0") / 2, mPaint);
                if (i == 0) {

                    // startY = maxHeight - (0 / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2;//rulerToResultgap - c

                    canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, mPaint);
                } else {
                    canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, axisPaint);
                }
            }
        } else if (maxYValue > 0 && maxYValue <= 1) {
            for (int i = 0; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                textValue = CalculateUtil.numMathMul(maxYDivisionValue, (float) (0.2 * i));
                String text = CommonDateUtil.formatInterger(textValue);
                canvas.drawText(text, mStartX - mPaint.measureText(text) + DisplayUtils.dip2px(mContext, 0), startY + mPaint.measureText("0") / 2, mPaint);
                if (i == 0) {
                    // startY = maxHeight + topMargin / 2;//rulerToResultgap - c
                    canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, mPaint);
                } else {
                    canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, axisPaint);

                }
            }
        } else {
            for (int i = 0; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                String text = String.valueOf(10 * i);
                canvas.drawText(text, mStartX - mPaint.measureText(text) + DisplayUtils.dip2px(mContext, 0), startY + mPaint.measureText("0") / 2, mPaint);
                if (i == 0) {
                    // startY = maxHeight + topMargin / 2;//rulerToResultgap - c
                    canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, mPaint);
                } else {
                    canvas.drawLine(mStartX, startY, width - paddingRight - rightMargin, startY, axisPaint);

                }
            }
        }*/
    }

    private float mCurrentX;
    private float mCurrentY;
    private float mTouchStarX;
    private float mTouchStartY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        currentX = event.getX();
        isUp = false;
        velocityTracker.computeCurrentVelocity(100);
        velocityTracker.addMovement(event);
        getParent().requestDisallowInterceptTouchEvent(false);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mTouchStarX = event.getRawX();
                mTouchStartY = event.getRawY();
                isMove = true;
                //按下时如果属性动画还没执行完,就终止,记录下当前按下点的位置
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    valueAnimator.end();
                    valueAnimator.cancel();
                }
                showScaleResult = true;
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                //滑动时候,通过假设的滑动距离,做超出左边界以及右边界的限制。

                if (Math.abs(mCurrentX - mTouchStarX) > Math.abs(mCurrentY - mTouchStartY)) {
                    isMove = true;
                    showScaleResult = true;
                    moveX = currentX - downX + lastMoveX;
                    if (moveX >= width / 2) {
                        moveX = width / 2;
                    } else if (moveX <= getWhichScalMovex(maxScale)) {
                        moveX = getWhichScalMovex(maxScale);
                    }
                }
                mCurrentX = event.getRawX();
                mCurrentY = event.getRawY();

                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                showScaleResult = true;
                isMove = false;
                //手指抬起时候制造惯性滑动
                lastMoveX = moveX;
                xVelocity = (int) velocityTracker.getXVelocity();
                autoVelocityScroll(xVelocity);
                velocityTracker.clear();
                break;
        }
        invalidate();
        return true;
    }

    private void autoVelocityScroll(int xVelocity) {
        //惯性滑动的代码,速率和滑动距离,以及滑动时间需要控制的很好,应该网上已经有关于这方面的算法了吧。。这里是经过N次测试调节出来的惯性滑动
        if (Math.abs(xVelocity) < 50) {
            isUp = true;
            return;
        }
        if (valueAnimator.isRunning()) {
            return;
        }
        valueAnimator = ValueAnimator.ofInt(0, xVelocity / 20).setDuration(Math.abs(xVelocity / 10));
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveX += (int) animation.getAnimatedValue();
                if (moveX >= width / 2) {
                    moveX = width / 2;
                } else if (moveX <= getWhichScalMovex(maxScale)) {
                    moveX = getWhichScalMovex(maxScale);
                }
                lastMoveX = moveX;
                invalidate();
            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isUp = true;
                invalidate();
            }
        });

        valueAnimator.start();
    }

    private float getWhichScalMovex(float scale) {
        return width / 2 - scaleGap * scaleCount * (scale - minScale);
    }

    private void drawScaleAndNum(Canvas canvas) {
        try {
            canvas.translate(mStartX, (showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2);//移动画布到结果值的下面 (showScaleResult ? resultNumRect.height() : 0) +
            Log.i("HeartRateIngView", resultNumRect.height() + "resultNumRect.height()" + "drawScaleAndNum1 mStartX:" + mStartX + "(showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2:" + ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2));

            int num1;//确定刻度位置
            float num2;

            if (firstScale != -1) {   //第一次进来的时候计算出默认刻度对应的假设滑动的距离moveX
                moveX = getWhichScalMovex(firstScale);          //如果设置了默认滑动位置，计算出需要滑动的距离
                lastMoveX = moveX;
                firstScale = -1;                                //将结果置为-1，下次不再计算初始位置
            }

            if (computeScale != -1) {//弹性滑动到目标刻度
                lastMoveX = moveX;
                if (valueAnimator != null && !valueAnimator.isRunning()) {
                    valueAnimator = ValueAnimator.ofFloat(getWhichScalMovex(currentScale), getWhichScalMovex(computeScale));
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            moveX = (float) animation.getAnimatedValue();
                            lastMoveX = moveX;
                            invalidate();
                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //这里是滑动结束时候回调给使用者的结果值
                            computeScale = -1;
                        }
                    });
//                valueAnimator.setDuration(100);
                    valueAnimator.setDuration(Math.abs((long) ((getWhichScalMovex(computeScale) - getWhichScalMovex(currentScale)) / 100)));
                    valueAnimator.start();
                }
            }

            num1 = -(int) (moveX / scaleGap);                   //滑动刻度的整数部分
            num2 = (moveX % scaleGap);                         //滑动刻度的小数部分

            canvas.save();                                      //保存当前画布

            rulerRight = 0;                                    //准备开始绘制当前屏幕,从最左面开始

            if (isUp) {   //这部分代码主要是计算手指抬起时，惯性滑动结束时，刻度需要停留的位置
                num2 = ((moveX - width / 2 % scaleGap) % scaleGap);     //计算滑动位置距离整点刻度的小数部分距离
                if (num2 <= 0) {
                    num2 = scaleGap - Math.abs(num2);
                }
                leftScroll = (int) Math.abs(num2);                        //当前滑动位置距离左边整点刻度的距离
                rightScroll = (int) (scaleGap - Math.abs(num2));         //当前滑动位置距离右边整点刻度的距离

                float moveX2 = num2 <= scaleGap / 2 ? moveX - leftScroll : moveX + rightScroll; //最终计算出当前位置到整点刻度位置需要滑动的距离

                if (valueAnimator != null && !valueAnimator.isRunning()) {      //手指抬起，并且当前没有惯性滑动在进行，创建一个惯性滑动
                    valueAnimator = ValueAnimator.ofFloat(moveX, moveX2);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            moveX = (float) animation.getAnimatedValue();            //不断滑动去更新新的位置
                            lastMoveX = moveX;
                            invalidate();
                        }
                    });
                    valueAnimator.addListener(new AnimatorListenerAdapter() {       //增加一个监听，用来返回给使用者滑动结束后的最终结果刻度值
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            //这里是滑动结束时候回调给使用者的结果值
                            if (onChooseResulterListener != null) {
                                onChooseResulterListener.onEndResult(resultText);
                            }
                        }
                    });
                    valueAnimator.setDuration(100);
                    valueAnimator.start();
                    isUp = false;
                }

                num1 = (int) -(moveX / scaleGap);                //重新计算当前滑动位置的整数以及小数位置
                num2 = (moveX % scaleGap);
            }


            canvas.translate(num2, 0);    //不加该偏移的话，滑动时刻度不会落在0~1之间只会落在整数上面,其实这个都能设置一种模式了，毕竟初衷就是指针不会落在小数上面


            //这里是滑动时候不断回调给使用者的结果值
            currentScale = new WeakReference<>(new BigDecimal(((width / 2 - moveX) / (scaleGap * scaleCount) + minScale) * scaleLimit)).get().setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();


            int incurrentScale = (int) currentScale;
            currentValue = 0;
            if (incurrentScale + 1 >= hrList.size()) {

            } else {
                if (incurrentScale >= 0) {
                    if(timesList.isEmpty())
                        return;
                    //resultText = String.valueOf(hrList.get(incurrentScale + 1));//(incurrentScale+1>hrList.size())?hrList.size()-1:incurrentScale+1)
                    //timesText = timesList.get(incurrentScale + 1);//(incurrentScale+1>hrList.size())?hrList.size()-1:incurrentScale+1)
                    //选中的值
                    //currentValue = hrList.get(incurrentScale + 1);
                    resultText = String.valueOf(hrList.get(incurrentScale + 1));//(incurrentScale+1>hrList.size())?hrList.size()-1:incurrentScale+1)
                    timesText = timesList.get(incurrentScale + 1);//(incurrentScale+1>hrList.size())?hrList.size()-1:incurrentScale+1)
                    //选中的值
                    currentValue = hrList.get(incurrentScale + 1);
                }
            }

            // canvas.drawLine(mStartX - DisplayUtils.dip2px(mContext, 20), maxHeight - 0 - rulerToResultgap + topMargin / 2, width - paddingRight - rightMargin + DisplayUtils.dip2px(mContext, 20), maxHeight - 0 - rulerToResultgap + topMargin / 2, mPaint);

            if (onChooseResulterListener != null) {
                onChooseResulterListener.onScrollResult(resultText); //接口不断回调给使用者结果值
            }

            //绘制当前屏幕可见刻度,不需要裁剪屏幕,while循环只会执行·屏幕宽度/刻度宽度·次,大部分的绘制都是if(curDis<width)这样子内存暂用相对来说会比较高。。
            while (rulerRight < width) {
                if (num1 % scaleCount == 0) {    //绘制整点刻度以及文字
                    if ((moveX >= 0 && rulerRight < moveX - scaleGap) || width / 2 - rulerRight <= getWhichScalMovex(maxScale + 1) - moveX) {
                        //当滑动出范围的话，不绘制，去除左右边界
                    } else {
                        if (num1 + 1 < hrList.size() && num1 >= 0) {
                            float startY = maxHeight - ((hrList.get(num1)) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2;//rulerToResultgap - c
                            float endY = maxHeight - ((hrList.get(num1 + 1)) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2;
                            //  Log.i("HeartRateIngView", "maxHeight" + maxHeight + "startY:" + startY + "endY" + endY + "scaleGap:" + scaleGap);
                            canvas.drawLine(0, startY, scaleGap, endY, midScalePaint);

                            //  float


                            drawShader(canvas, startY, endY);
                        }
                    }
                } else {   //绘制小数刻度
                    if ((moveX >= 0 && rulerRight < moveX) || width / 2 - rulerRight < getWhichScalMovex(maxScale) - moveX) {
                        //当滑动出范围的话，不绘制，去除左右边界
                    } else {
                        // canvas.drawLine(0, 0, scaleGap, 0, smallScalePaint);
                    }
                }
                ++num1;  //刻度加1
                rulerRight += scaleGap;  //绘制屏幕的距离在原有基础上+1个刻度间距
                canvas.translate(scaleGap, 0); //移动画布到下一个刻度
                // Log.i("HeartRateIngView ", "translate 2 scaleGap2 "+scaleGap);
            }
            canvas.restore();
            Log.i("HeartRateIngView ", "drawScaleAndNum 2 translate num2 " + num2 + "incurrentScale:" + incurrentScale + "num1:" + num1);

            //绘制屏幕中间用来选中刻度的最大刻度
            if (hrList.size() > 0) {
                if (moveX == width / 2) {
                    if (showScaleResult) {
                        //正在滑动的时候,绘制当前整点高度的line,绘制时去除心率line的高度midScaleStroke,使用smallScaleStroke让人感觉正好贴合这线在走
                        if (currentValue != 0)
                            canvas.drawLine(width / 2, maxHeight - ((currentValue) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2 + smallScaleStroke, width / 2, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), lagScalePaint);
                    } else {
                        canvas.drawLine(width / 2, maxHeight - ((hrList.get(hrList.size() - 1)) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2, width / 2, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), lagScalePaint);
                    }
                    String text = timesText;
                    mPaint.setColor(mContext.getResources().getColor(R.color.common_tips_color));//#F3546A
                    canvas.drawText(text, width / 2 - mPaint.measureText(text) / 2, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2) + DisplayUtils.dip2px(mContext, 12), mPaint);
                    drawResultText(canvas, resultText, num1);
                } else {
                    if (showScaleResult) {
                        //正在滑动的时候,绘制当前整点高度的line,绘制时去除心率line的高度midScaleStroke,使用smallScaleStroke让人感觉正好贴合这线在走
                        if (currentValue != 0)
                            canvas.drawLine(width / 2 + scaleGap, maxHeight - ((currentValue) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2 + smallScaleStroke, width / 2 + scaleGap, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), lagScalePaint);
                    } else {
                        canvas.drawLine(width / 2 + scaleGap, maxHeight - ((hrList.get(hrList.size() - 1)) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2, width / 2 + scaleGap, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), lagScalePaint);
                    }
                    String text = timesText;
                    mPaint.setColor(mContext.getResources().getColor(R.color.common_tips_color));//#F3546A
                    canvas.drawText(text, width / 2 + scaleGap - mPaint.measureText(text) / 2, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2) + DisplayUtils.dip2px(mContext, 12), mPaint);
                    drawResultText(canvas, resultText, num1);
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void drawShader(Canvas canvas, float startY, float endY) {
        setPaintShader();
        Path mPathShader = new Path();
        mPathShader.moveTo(0, startY);
        mPathShader.lineTo(scaleGap, endY);
        mPathShader.lineTo(scaleGap, height);//height
        mPathShader.lineTo(0, height);
        mPathShader.close();
        canvas.drawPath(mPathShader, mPaintShader);
    }

    //绘制上面的结果 结果值+单位
    private void drawResultText(Canvas canvas, String resultText, int index) {
        Log.i("HeatRateIngView", "drawResultText showScaleResult:" + showScaleResult + "resultText" + resultText.length() + "resultNumRect" + resultNumRect.width() + "resultNumRect.height()" + resultNumRect.height());

        if (!showScaleResult) {   //判断用户是否设置需要显示当前刻度值，如果否,则取消绘制
            return;
        }
        if (currentValue == 0) {
            return;
        }
        //超出边界不绘制
//        - rulerToResultgap / 2
        float valuePosition = maxHeight - ((currentValue) / 250.0f * maxHeight) - rulerToResultgap + topMargin / 2 + smallScaleStroke - resultNumRect.height() - DisplayUtils.dip2px(mContext, 10);
        canvas.translate(0, valuePosition);  //移动画布到正确的位置来绘制结果值
        Log.i("HeatRateIngView", "drawResultText valuePosition:" + valuePosition + "resultText" + resultText.length() + "resultNumRect" + resultNumRect.width() + "resultNumRect.height()" + resultNumRect.height());
        resultNumPaint.getTextBounds(resultText, 0, resultText.length(), resultNumRect);
        if (moveX == width / 2) {
            canvas.drawText(resultText, width / 2 - resultNumPaint.measureText(resultText) / 2, resultNumRect.height() - DisplayUtils.dip2px(mContext, 5), //绘制当前刻度结果值
                    resultNumPaint);
        } else {
            canvas.drawText(resultText, width / 2 + scaleGap - resultNumPaint.measureText(resultText) / 2, resultNumRect.height() - DisplayUtils.dip2px(mContext, 10), //绘制当前刻度结果值
                    resultNumPaint);
        }
        //  resultNumRight = width / 2 + resultNumRect.width() / 2 + 10;
//        canvas.drawText(unit, resultNumRight, resultNumRect.height(), kgPaint);            //在当前刻度结果值的右面10px的位置绘制单位
    }

    private void drawBg(Canvas canvas) {
        bgRect.set(0, 0, width, height);
        if (isBgRoundRect) {
            canvas.drawRoundRect(bgRect, 20, 20, bgPaint); //20->椭圆的用于圆形角x-radius
        } else {
            canvas.drawRect(bgRect, bgPaint);
        }
    }

    public void computeScrollTo(float scale) {
        scale = scale / scaleLimit;
        if (scale < minScale || scale > maxScale) {
            return;
        }

        computeScale = scale;
        invalidate();

    }

    public interface OnChooseResulterListener {
        void onEndResult(String result);

        void onScrollResult(String result);
    }

    public void setRulerHeight(int rulerHeight) {
        this.rulerHeight = rulerHeight;
        invalidate();
    }

    public void setRulerToResultgap(int rulerToResultgap) {
        this.rulerToResultgap = rulerToResultgap;
        invalidate();
    }

    public void setScaleCount(int scaleCount) {
        this.scaleCount = scaleCount;
        invalidate();
    }

    public void setScaleGap(int scaleGap) {
        this.scaleGap = scaleGap;
        invalidate();
    }

    public void setMinScale(int minScale) {
        this.minScale = minScale;
        invalidate();
    }

    boolean isMove = false;
    boolean isEnd;

    public void setFirstScale(int firstScale) {
        //如果是在移动就不
        if (!isMove) {
            this.firstScale = firstScale;
            invalidate();
        }
    }

    public void setMaxScale(int maxScale) {
        this.maxScale = maxScale;
        //invalidate();
    }

    /**
     * item中的Y轴最大值
     */
    private float maxYValue = 250;


    public void setData(List<Integer> list) {
        hrList.clear();
        hrList.addAll(list);
        if (list != null && list.size() > 0) {
            // maxYValue = calculateMax(list);
            // getRange(maxYValue);
        }
    }

    public void setTimesData(List<String> list) {
        timesList.clear();
        timesList.addAll(list);
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }

    public void setSmallScaleColor(int smallScaleColor) {
        this.smallScaleColor = smallScaleColor;
        invalidate();
    }

    public void setMidScaleColor(int midScaleColor) {
        this.midScaleColor = midScaleColor;
        invalidate();
    }

    public void setLargeScaleColor(int largeScaleColor) {
        this.largeScaleColor = largeScaleColor;
    }

    public void setScaleNumColor(int scaleNumColor) {
        this.scaleNumColor = scaleNumColor;
        invalidate();
    }

    public void setResultNumColor(int resultNumColor) {
        this.resultNumColor = resultNumColor;
        invalidate();
    }

    public void setUnit(String unit) {
        this.unit = unit;
        invalidate();
    }

    public void setUnitColor(int unitColor) {
        this.unitColor = unitColor;
        invalidate();
    }

    public void setSmallScaleStroke(int smallScaleStroke) {
        this.smallScaleStroke = smallScaleStroke;
        invalidate();
    }

    public void setMidScaleStroke(int midScaleStroke) {
        this.midScaleStroke = midScaleStroke;
        invalidate();
    }

    public void setLargeScaleStroke(int largeScaleStroke) {
        this.largeScaleStroke = largeScaleStroke;
        invalidate();
    }

    public void setResultNumTextSize(int resultNumTextSize) {
        this.resultNumTextSize = resultNumTextSize;
        invalidate();
    }

    public void setScaleNumTextSize(int scaleNumTextSize) {
        this.scaleNumTextSize = scaleNumTextSize;
        invalidate();
    }

    public void setUnitTextSize(int unitTextSize) {
        this.unitTextSize = unitTextSize;
        invalidate();
    }

    public void setShowScaleResult(boolean showScaleResult) {
        this.showScaleResult = showScaleResult;
        invalidate();
    }

    public void setIsBgRoundRect(boolean bgRoundRect) {
        isBgRoundRect = bgRoundRect;
        invalidate();
    }

    public void setScaleLimit(int scaleLimit) {
        this.scaleLimit = scaleLimit;
        invalidate();
    }
}
