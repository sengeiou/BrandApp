package bike.gymproject.viewlibray;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import bike.gymproject.viewlibray.chart.LineChartEntity;
import phone.gym.jkcq.com.commonres.commonutil.CalculateUtil;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

/**
 * 作者：chs on 2016/9/6 14:17
 * 邮箱：657083984@qq.com
 * 线形图表
 */
public class LineChartView extends View {
    private static final String TAG = LineChartView.class.getSimpleName();
    private Context mContext;
    /**
     * 背景的颜色
     */
    private static final int BG_COLOR = Color.WHITE;


    private int maxHr, minHr;

    private int maxIndex, minIndex;

    /**
     * 视图的宽和高
     */
    private int mTotalWidth, mTotalHeight;
    private int paddingRight, paddingBottom, paddingTop;
    /**
     * x轴 y轴 起始坐标
     */
    private float mStartX, mStartY;
    /**
     * 图表绘制区域的顶部和底部  图表绘制区域的最大高度
     */
    private float paintTop, paintBottom, maxHeight;
    //距离底部的多少 用来显示底部的文字
    private int bottomMargin;
    //距离顶部的多少 用来显示顶部的文字
    private int topMargin;
    /**
     * 左边和上边的边距
     */
    private int leftMargin, rightMargin;
    /**
     * 画笔 背景，轴 ，线 ，text ,点 提示线
     */
    private Paint bgPaint, axisPaint, linePaint, textPaint, pointPaint;//, hintPaint;
    /**
     * 原点的半径
     */
    private static final float RADIUS = 8;
    private List<LineChartEntity> mData;//数据集合
    /**
     * 右边的最大和最小值
     */
    private int maxRight, minRight;
    /**
     * item中的Y轴最大值
     */
    private float maxYValue;
    /**
     * 最大分度值
     */
    private float maxYDivisionValue;
    /**
     * 线的路径
     */
    Path linePath;
    /**
     * 向右边滑动的距离
     */
    private float leftMoving;
    //左边Y轴的单位
    private String leftAxisUnit = "单位";
    /**
     * 两个点之间的距离
     */
    private float space;
    /**
     * 绘制的区域
     */
    private RectF mDrawArea, mHintArea;
    private Rect leftWhiteRect, rightWhiteRect;
    /**
     * 保存点的x坐标
     */
    private List<Point> linePoints = new ArrayList<>();
    /**
     * 左后一次的x坐标
     */
    private float lastPointX;
    /**
     * 当前移动的距离
     */
    private float movingThisTime = 0.0f;

    /**
     * 是不是绘制曲线
     */
    private boolean isCurv = false;
    /**
     * 点击的点的位置
     */
    private int selectIndex;

    /**
     * 是否绘制提示文字
     */
//    private boolean isDrawHint = false;
//    private int hintColor = Color.RED;


    protected Paint mMaxOrMinPaint;

    protected Paint mTextPaint;

    protected Rect mTextBound;

    protected Paint mPaint;

    public LineChartView(Context context) {
        super(context);
        init(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private GestureDetector mGestureListener;

    private void init(Context context) {
        mGestureListener = new GestureDetector(context, new RangeBarOnGestureListener());
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        shadeColors = new int[]{0xFFF3546A, 0x88F3546A, 0x00F3546A};
        setWillNotDraw(false);
        mContext = context;

        initMagin(context);
        initBgPaint();

        initAxisPaint(context);

        initTextPaint(context);


        initMaxOrMinPaint(context);

        initPointPaint();

//        initHintPaint(context);


    }

    private void initMaxOrMinPaint(Context context) {

        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(context.getResources().getColor(R.color.common_item_line_color));//#F3546A
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            mPaint.setStrokeWidth(2);
            mPaint.setPathEffect(new DashPathEffect(new float[]{15, 2}, 0));
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
        }
        if (mMaxOrMinPaint == null) {
            mMaxOrMinPaint = new Paint();
            mMaxOrMinPaint.setStyle(Paint.Style.FILL);
            mMaxOrMinPaint.setAntiAlias(true);
            //mTextPaint.setStrokeWidth(5);
            mMaxOrMinPaint.setTextAlign(Paint.Align.CENTER);
            mMaxOrMinPaint.setTextSize(DisplayUtils.dip2px(context, 10));
            mMaxOrMinPaint.setColor(Color.BLACK);   // 绘画字体的颜色
        }

        if (mTextPaint == null) {
            mTextPaint = new Paint();
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setAntiAlias(true);
            //mTextPaint.setStrokeWidth(5);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(DisplayUtils.dip2px(context, 12));
            mTextPaint.setColor(context.getResources().getColor(R.color.common_rec));//#F3546A
            mTextBound = new Rect();
            String mText = "023";

        }
    }

    private void initMagin(Context context) {
        bottomMargin = DisplayUtils.dip2px(getContext(), 30);
        topMargin = DisplayUtils.dip2px(context, 30);
        rightMargin = DisplayUtils.dip2px(getContext(), 20);
        leftMargin = DisplayUtils.dip2px(getContext(), 0);
    }

    private void initBgPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(Color.YELLOW);
    }

    private void initAxisPaint(Context context) {
        axisPaint = new Paint();
        axisPaint.setColor(context.getResources().getColor(R.color.common_bg));//#F3546A
        axisPaint.setStrokeWidth(DisplayUtils.dip2px(context, 0.5f));
    }

    private void initTextPaint(Context context) {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(DisplayUtils.dip2px(getContext(), 10));
        textPaint.setColor(context.getResources().getColor(R.color.common_tips_color));//#F3546A//common_tips_color
    }

    private void initLinePaint(Context context) {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(DisplayUtils.dip2px(context, 1));
        linePaint.setColor(context.getResources().getColor(R.color.common_rec));//#F3546A
        linePaint.setStyle(Paint.Style.STROKE);
    }

    private void initPointPaint() {
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);


    }

    private void initHintPaint(Context context) {
//        float txtSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                12, context.getResources().getDisplayMetrics());
//        hintPaint = new Paint();
//        hintPaint.setAntiAlias(true);
//        hintPaint.setTextSize(txtSize);
//        hintPaint.setStyle(Paint.Style.FILL);
//        hintPaint.setAlpha(100);
//        hintPaint.setStrokeWidth(2);
//        hintPaint.setTextAlign(Paint.Align.CENTER);
//        hintPaint.setColor(hintColor);
    }

    public void setData(List<LineChartEntity> list, boolean isCurv, int maxHr, int minHr) {
        this.mData = list;
        this.isCurv = isCurv;
        //计算最大值
        if (list.size() > 0) {
            maxYValue = list.get(0).yValue;
            maxYValue = calculateMax(list, maxHr, minHr);
            getRange((int) maxYValue);
        }

        this.minHr = minHr;
        this.maxHr = maxHr;
        linePath = new Path();
        initLinePaint(mContext);

        invalidate();

    }

    /**
     * 计算出Y轴最大值
     *
     * @return
     */

    ArrayList<String> yList = new ArrayList<>();
    ArrayList<String> xList = new ArrayList<>();

    int jishu = 5;

    private float calculateMax(List<LineChartEntity> list, int maxHr, int minHr) {
        /**
         * NSMutableArray *yMutableArray = [NSMutableArray array];

         int b=maxValue%5;
         if (b!=0)
         {
         maxValue+=5-b;
         }

         maxValue=maxValue==0?maxValue=4:maxValue;
         int a= maxValue/4;
         b=a%5;
         if (b!=0&&a>=5)
         {
         a+=5-b;
         }
         for (int i = 0; i <5*a ; i+=a) {
         [yMutableArray addObject:[NSString stringWithFormat:@"%d",i]];
         }
         self.yArray = [NSArray arrayWithArray:yMutableArray];
         */


        float maxValue = (list.get(0).yValue);
        //  for (LineChartEntity entity : list) {
        //获取最大值和最小只的index
        for (int i = 0; i < list.size(); i++) {
            LineChartEntity entity = list.get(i);
            if (entity.yValue > maxValue) {
                maxValue = entity.yValue;
            }
            //  maxValue+=10;
            if (entity.yValue == maxHr) {
                maxIndex = i;

            }
            if (entity.yValue == minHr) {
                minIndex = i;
            }
        }
        maxValue += 10;

        int b = (int) (maxValue) % jishu;
        if (b != 0) {
            maxValue += jishu - b;
        }

        maxValue = maxValue == 0 ? maxValue = 4 : maxValue;
        int a = (int) (maxValue / 4);
        b = a % jishu;
        if (b != 0 && a >= jishu) {
            a += jishu - b;
        }
        yList.clear();
        for (int i = a; i < jishu * a; i += a) {
            yList.add(i + "");
        }
        xList.clear();
        for (int i = 0; i <= 24; i += 4) {
            xList.add(i + "");
        }
        return maxValue;
    }

    /**
     * 得到柱状图的最大和最小的分度值
     *
     * @param maxValueInItems
     */
    private void getRange(int maxValueInItems) {//1200
        int scale = CalculateUtil.getScale(maxValueInItems);//获取这个最大数 数总共有几位 3
        float unScaleValue = (float) (maxValueInItems / Math.pow(10, scale));//最大值除以位数之后剩下的值  比如1200/1000 后剩下1.2

        maxYDivisionValue = (float) (unScaleValue * Math.pow(10, scale));//获取Y轴的最大的分度值 1500
//        mStartX = CalculateUtil.getDivisionTextMaxWidth(maxYDivisionValue, mContext) + 20;//获取1500的宽度
        mStartX = CalculateUtil.getDivisionTextMaxWidth(1000, mContext) + DisplayUtils.dip2px(mContext, 10);//获取1500的宽度
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//一般是视图大小发生变化的回调
        paddingBottom = getPaddingBottom();
        paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        mTotalWidth = w - paddingLeft - paddingRight;//除去内间距的实际视图的宽度
        mTotalHeight = h - paddingTop - paddingBottom;//除去内间距的实际视图的高度
        maxHeight = h - getPaddingTop() - getPaddingBottom() - bottomMargin - topMargin;//图表绘制区域的最大高度

        // Log.e(TAG, "paddingLeft:" + paddingLeft + "--paddingTop:" + paddingTop + "--paddingRight:" + paddingRight + "--paddingBottom:" + paddingBottom);
        // Log.e(TAG, "mTotalWidth:" + mTotalWidth + "--mTotalHeight:" + mTotalHeight + "--maxHeight:" + maxHeight);
        // Log.e(TAG, "w:" + w + "--h:" + h);
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    //获取滑动范围和指定区域
    private void getArea() {
        if (mData != null) {
            //给右边一点空间,多减右边的5dp
            space = (mTotalWidth - mStartX - DisplayUtils.dip2px(mContext, 15)) / (float) (mData.size() - 1);
            mStartY = mTotalHeight - bottomMargin - paddingBottom;
            mDrawArea = new RectF(mStartX, paddingTop, mTotalWidth , mTotalHeight - paddingBottom);
//            mHintArea = new RectF(mDrawArea.right - mDrawArea.right / 4, mDrawArea.top + topMargin / 2,
//                    mDrawArea.right, mDrawArea.top + mDrawArea.height() / 4 + topMargin / 2);
            //  Log.e(TAG, " space: " + space + "--mStartY:" + mStartY);//+"--maxRight:"+maxRight+"--minRight:"+minRight
        }
    }


    float scrollX = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null || mData.isEmpty()) return;
        // Logger.e("MyLog == onDraw");
        getArea();
        //linePoints.clear();
        //canvas.drawColor(BG_COLOR);
        //重置线
        // linePath.reset();
        // linePath.incReserve(mData.size());
//        checkTheLeftMoving();
        //画中间的线
//        drawWhiteLine(canvas);

        //画左边的Y轴
//        canvas.drawLine(mStartX, mStartY, mStartX, topMargin / 2, axisPaint);
        //画右边的Y轴
//        canvas.drawLine(mTotalWidth - leftMargin * 2, mStartY, mTotalWidth - leftMargin * 2, topMargin / 2, axisPaint);

        //左边Y轴的单位
//        canvas.drawText(leftAxisUnit, mStartX, topMargin / 2 - 14, textPaint);//这里的14表示字的大小

        //画左边的Y轴text
        drawLeftYAxis(canvas);


//        canvas.drawLine(mStartX, topMargin / 2, mTotalWidth - leftMargin * 2, topMargin / 2, axisPaint);

        //画X轴的text
       // drawXAxisText(canvas);

        //画线形图
        isFirst = false;
        isFirstEmptyIndex = -1;
        // drawLines(canvas);
        drawXline(canvas);
        // drawMaxOrMin(canvas, textPaint);
        //canvas.drawLine(scrollX, mStartY, scrollX, 0, textPaint);

        //调用clipRect()方法后，只会显示被裁剪的区域
//        canvas.clipRect(mDrawArea.left, mDrawArea.top, mDrawArea.right, mDrawArea.bottom + mDrawArea.height());

        //画线上的点
//        drawCircles(canvas);

//        if (isDrawHint) {
//            drawHint(canvas);
//        }
    }


    protected void drawMaxOrMin(Canvas canvas, Paint mPaint) {
        if (isAnimatorStart) {
            return;
        }
        if (mData == null) {
            return;
        }
        // canvas.drawPath(linePath, setPaintShader());

        float widtha = space;

        Log.e("drawMaxOrMin", "maxIndex=" + maxIndex + ",minIndex" + minIndex + "maxIndex != minIndex:" + (maxIndex != minIndex));

        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(maxIndex).yValue == 0) {
                return;
            }
            if (maxIndex == i) {
                float pointX = space * i;//- leftMoving;
                float lineHeight = mData.get(i).yValue * maxHeight / maxYDivisionValue;
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float baseline = mTextBound.centerY() + distance;
                canvas.drawText(maxHr == 0 ? "" : maxHr + "", (mStartX + widtha / 2 + (pointX)), (mStartY - lineHeight - baseline) * percent, mMaxOrMinPaint);
                canvas.drawCircle((mStartX + widtha / 2 + (pointX)), mStartY - lineHeight, 8, mTextPaint);


            }

            if (minIndex == i && maxIndex != minIndex) {

                float pointX = space * i;//- leftMoving;
                float lineHeight = mData.get(i).yValue * maxHeight / maxYDivisionValue;

                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float baseline = mTextBound.centerY() + distance;
                canvas.drawText(minHr == 0 ? "" : minHr + "", (mStartX + widtha / 2 + (pointX)), (mStartY - lineHeight - baseline) - baseline, mMaxOrMinPaint);
                canvas.drawCircle((mStartX + widtha / 2 + (pointX)), mStartY - lineHeight, 8, mTextPaint);
            }
        }
    }


    private void drawXline(Canvas canvas) {
        canvas.drawLine(mStartX, mStartY, mTotalWidth - leftMargin * 2, mStartY, axisPaint);
        float eachHeight = (maxHeight / yList.size());
        for (int i = 0; i < yList.size(); i++) {
            // float startY = mStartY - eachHeight * i;
         /*   String text = yList.get(i - 1);
            canvas.drawText(text, mStartX - textPaint.measureText(text) - 5, startY + textPaint.measureText("0"), textPaint);*/
            //画X轴

            //  float lineHeight = currentvalue * maxHeight / maxYDivisionValue;
            float maxlineHeight = Integer.parseInt(yList.get(i)) * maxHeight / maxYDivisionValue;


            // canvas.drawLine(scrollX, mStartY - lineHeight, scrollX, mStartY - maxlineHeight, mTextPaint);
            canvas.drawLine(mStartX, mStartY - maxlineHeight, mTotalWidth - leftMargin * 2, mStartY - maxlineHeight, axisPaint);
          /*  Path path = new Path();
            path.moveTo(mStartX, startY);
            path.lineTo(mStartX + 30, startY);
            canvas.drawPath(path, textPaint);*/
        }
    }

    private void drawXAxisText(Canvas canvas) {
        //这里设置 x 轴的字一条最多显示3个，大于三个就换行
        int xItem = (int) ((mTotalWidth - leftMargin * 2 - mStartX) / 6);
        for (int i = 0; i < xList.size(); i++) {

            if (i == 0) {
                canvas.drawText(xList.get(i), mStartX, mStartY + (textPaint.measureText("24")), textPaint);
                canvas.drawLine(mStartX, mStartY, mStartX, mStartY, axisPaint);
            } else if (i == xList.size() - 1) {
                canvas.drawText(xList.get(i), (mTotalWidth - leftMargin * 2) - (textPaint.measureText(xList.get(i))), mStartY + (textPaint.measureText("24")), textPaint);
                canvas.drawLine((mTotalWidth - leftMargin * 2) - (textPaint.measureText(xList.get(i))), mStartY, (mTotalWidth - leftMargin * 2) - (textPaint.measureText(xList.get(i))) / 2 - 10, mStartY, axisPaint);
            } else {
                canvas.drawText(xList.get(i), mStartX + (i * xItem) - (textPaint.measureText(xList.get(i))) / 2, mStartY + (textPaint.measureText("24")), textPaint);
                canvas.drawLine(mStartX + (i * xItem) - (textPaint.measureText(xList.get(i))) / 2, mStartY, mStartX + (i * xItem) - (textPaint.measureText(xList.get(i))) / 2 - 10, mStartY, axisPaint);
            }
        }

        /*for (int i = 0; i < mData.size(); i++) {
            if (i % 60 == 0) {
                int temp = i / 60;
                if (temp % 4 == 0) {
                    int xAxisScale = temp;
                    String text = String.valueOf(xAxisScale);
                    int minScale = (int) (mStartX - (textPaint.measureText(text)) / 2);
                    int maxScale = mTotalWidth - leftMargin * 2;
                    if (linePoints.get(i).x >= minScale && linePoints.get(i).x < maxScale) {
                        canvas.drawText(text, linePoints.get(i).x - (textPaint.measureText(text)) / 2, mStartY + (textPaint.measureText("24")), textPaint);
                        canvas.drawLine(linePoints.get(i).x - (textPaint.measureText(text)) / 2, mStartY, linePoints.get(i).x - (textPaint.measureText(text)) / 2 - 10, mStartY, axisPaint);
                    }
                }
            }
        }*/
        //canvas.drawText("时", mTotalWidth - (textPaint.measureText("时")), mStartY + (textPaint.measureText("24")), textPaint);

    }

    //绘制中间线
    private void drawWhiteLine(Canvas canvas) {
        axisPaint.setColor(Color.parseColor("#FF0000"));
        float eachHeight = (maxHeight / 5f);//最大高度除以5份
        for (int i = 1; i <= 5; i++) {
            float startY = mStartY - eachHeight * i;
            if (startY < topMargin / 2) {
                break;
            }
            canvas.drawLine(mStartX, startY, mTotalWidth - leftMargin * 2, startY, axisPaint);
        }
        axisPaint.setColor(Color.BLACK);
    }

    boolean isAnimatorStart;

    private float percent = 1f;
    private TimeInterpolator pointInterpolator = new DecelerateInterpolator();

    public void startAnimation(int duration) {
        isAnimatorStart = true;
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0f, 1);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(pointInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                linePaint.setPathEffect(new DashPathEffect(new float[]{pathLength, pathLength}, pathLength - pathLength * percent));
                invalidate();
            }

        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimatorStart = false;
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimatorStart = true;
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
        mAnimator.start();
    }

    private float pathLength;

    /**
     * 画线形图  记录第一个不为0的数据
     */
    int isFirstEmptyIndex = -1;
    boolean isFirst = false;

    private void drawLines(Canvas canvas) {
        float distance = 0;
//        float lineStart = mStartX + textPaint.measureText(mData.get(0).getxLabel()) / 2 + 20;
        float lineStart = mStartX;
        for (int i = 0; i < mData.size(); i++) {
            distance = space * i;//- leftMoving;
            if (mData.get(i).yValue != 0 && isFirstEmptyIndex == -1) {
                isFirstEmptyIndex = i;
                isFirst = true;
            }
            if (mData.get(i).yValue == 0) {
                continue;
            }

            float lineHeight = mData.get(i).yValue * maxHeight / maxYDivisionValue;
            // Logger.e("MyLog == i:" + i + "mData.get(i).yValue:" + mData.get(i).yValue + "lineStart + distance:" + lineStart + distance + "(mStartY - lineHeight) * percent:" + (mStartY - lineHeight) * percent);
            if (isFirst) {
                isFirst = false;
                linePath.setLastPoint(lineStart + distance, (mStartY - lineHeight) * percent);
            } else {

                if (!isCurv) {
                    linePath.lineTo(lineStart + distance, (mStartY - lineHeight) * percent);
                } else {
                    linePath.lineTo(lineStart + distance, (mStartY - lineHeight) * percent);
//                    float lineHeightPre = mData.get(i - 1).yValue * maxHeight / maxYDivisionValue;
//                    linePath.cubicTo(lineStart + distance - space / 2, (mStartY - lineHeightPre) * percent,
//                            lineStart + distance - space / 2, (mStartY - lineHeight) * percent,
//                            lineStart + distance, (mStartY - lineHeight) * percent);
                }
            }
            linePoints.add(new Point((int) (lineStart + distance), (int) (mStartY - lineHeight)));
        }

        //canvas.drawPath(linePath, setPaintShader());
        if (isFirstEmptyIndex != -1) {
            PathMeasure measure = new PathMeasure(linePath, false);
            pathLength = measure.getLength();
            canvas.drawPath(linePath, linePaint);
            if (!isAnimatorStart) {
                // linePath.lineTo(linePoints.get(linePoints.size() - 1).x + (linePoints.get(linePoints.size() - 1).y - linePoints.get(linePoints.size() - 2).y) / 50 * linePoints.size(), mStartY);
                linePath.lineTo(linePoints.get(linePoints.size() - 1).x, mStartY);
                linePath.lineTo(linePoints.get(0).x, mStartY);
                linePath.lineTo(linePoints.get(0).x, linePoints.get(0).y);
                linePaint.setStyle(Paint.Style.FILL);// 设置为空心
                canvas.drawPath(linePath, setPaintShader());
                linePaint.reset();
            }
        }


    }


    //  初始化渐变色
    private int[] shadeColors;


    /**
     * 画线上的点
     */
    private void drawCircles(Canvas canvas) {
        for (int i = 0; i < mData.size(); i++) {
            pointPaint.setColor(Color.parseColor("#EF6868"));
//            canvas.drawCircle(linePoints.get(i), (mStartY - mData.get(i).getyValue() * maxHeight / maxYDivisionValue) * percent, RADIUS, pointPaint);
            if (linePoints.get(i).x >= mStartX && linePoints.get(i).x < (mTotalWidth - leftMargin * 2)) {
                canvas.drawCircle(linePoints.get(i).x, linePoints.get(i).y * percent, RADIUS, pointPaint);
            }
        }
    }

    /**
     * 画Y轴上的text (1)当最大值大于1 的时候 将其分成5份 计算每个部分的高度  分成几份可以自己定
     * （2）当最大值大于0小于1的时候  也是将最大值分成5份
     * （3）当为0的时候使用默认的值
     *
     * @param canvas
     */
    private void drawLeftYAxis(Canvas canvas) {
        float eachHeight = (maxHeight / yList.size());
        /*if (maxYValue > 1) {
            for (int i = 1; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                if (startY < topMargin / 2) {
                    break;
                }
                BigDecimal maxValue = new BigDecimal(maxYDivisionValue);
                BigDecimal fen = new BigDecimal(0.2 * i);
                String text = null;
                //因为图表分了5条线，如果能除不进，需要显示小数点不然数据不准确
                if (maxYDivisionValue % 5 != 0) {
                    text = String.valueOf(maxValue.multiply(fen).floatValue());
                } else {
                    text = String.valueOf(maxValue.multiply(fen).longValue());
                }
                canvas.drawText(text, mStartX - textPaint.measureText(text) - 5, startY + textPaint.measureText("0"), textPaint);
            }
        } else if (maxYValue > 0 && maxYValue <= 1) {
            for (int i = 1; i <= 5; i++) {
                float startY = mStartY - eachHeight * i;
                if (startY < topMargin / 2) {
                    break;
                }
                float textValue = CalculateUtil.numMathMul(maxYDivisionValue, (float) (0.2 * i));
                String text = String.valueOf(textValue);
                canvas.drawText(text, mStartX - textPaint.measureText(text) - 5, startY + textPaint.measureText("0"), textPaint);
            }
        } else {*/
        for (int i = 0; i < yList.size(); i++) {
            float startY = mStartY - eachHeight * i;
            String text = yList.get(i);
            float maxlineHeight = Integer.parseInt(yList.get(i)) * maxHeight / maxYDivisionValue;
            // canvas.drawLine(mStartX, mStartY - maxlineHeight, mTotalWidth - leftMargin * 2, mStartY - maxlineHeight, mPaint);
            canvas.drawText(text, mStartX - textPaint.measureText(text) - 5, mStartY - maxlineHeight + textPaint.measureText("0") - DisplayUtils.dip2px(mContext, 5), textPaint);
            //画X轴
            //canvas.drawLine(mStartX - rightMargin, mStartY, mTotalWidth - leftMargin * 2, mStartY, axisPaint);
          /*  Path path = new Path();
            path.moveTo(mStartX, startY);
            path.lineTo(mStartX + 30, startY);
            canvas.drawPath(path, textPaint);*/
        }
        //  }
    }


    /*protected void drawXAxis(Canvas canvas, Paint mPaint) {

        float start = originalX + lineStartX;
        float any = height / 4;

        Path path = new Path();

        for (int i = 0; i < 5; i++) {
            path.moveTo(start, originalY - any * i);
            path.lineTo(width, originalY - any * i);
        }


        canvas.drawPath(path, mPaint);

    }*/

//    private void initOrResetVelocityTracker() {
//        if (velocityTracker == null) {
//            velocityTracker = VelocityTracker.obtain();
//        } else {
//            velocityTracker.clear();
//        }
//    }

//    private void recycleVelocityTracker() {
//        if (velocityTracker != null) {
//            velocityTracker.recycle();
//            velocityTracker = null;
//        }
//    }

    @Override
    public void computeScroll() {
//        if (scroller.computeScrollOffset()) {
//            movingThisTime = (scroller.getCurrX() - lastPointX);
//            leftMoving = leftMoving + movingThisTime;
//            lastPointX = scroller.getCurrX();
//            postInvalidate();
//        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastPointX = event.getX();
//                scroller.abortAnimation();//终止动画
//                initOrResetVelocityTracker();
//                velocityTracker.addMovement(event);//将用户的移动添加到跟踪器中。
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float movex = event.getX();
//                movingThisTime = lastPointX - movex;
//                leftMoving = leftMoving + movingThisTime;
//                lastPointX = movex;
//                invalidate();
//                velocityTracker.addMovement(event);
//                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                clickAction(event);
//                velocityTracker.addMovement(event);
//                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
//                int initialVelocity = (int) velocityTracker.getXVelocity();
//                velocityTracker.clear();
//                scroller.fling((int) event.getX(), (int) event.getY(), -initialVelocity / 2,
//                        0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
//                invalidate();
//                lastPointX = event.getX();
//                recycleVelocityTracker();
//                break;
//            default:
//                return super.onTouchEvent(event);
//        }
//        return true;
//    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            isDrawHint = false;
//            postInvalidate();
        }
    };
    /**
     * 绘制提示文字
     */
//    private void drawHint(Canvas canvas) {
//        //竖线
//        canvas.drawLine(linePoints.get(selectIndex).x, mStartY, linePoints.get(selectIndex).x, topMargin / 2, hintPaint);
//        //横线
//        canvas.drawLine(mStartX, linePoints.get(selectIndex).y, mTotalWidth - leftMargin * 2, linePoints.get(selectIndex).y, hintPaint);
//        hintPaint.setAlpha(60);
//        canvas.drawRect(mHintArea, hintPaint);
//        hintPaint.setColor(Color.WHITE);
//        canvas.drawText("x : " + mData.get(selectIndex).getxLabel(), mHintArea.centerX(), mHintArea.centerY() - 12, hintPaint);
//        canvas.drawText("y : " + mData.get(selectIndex).getyValue(), mHintArea.centerX(),
//                mHintArea.centerY() + 12 - hintPaint.ascent()-hintPaint.descent(), hintPaint);
//        hintPaint.setColor(hintColor);
//        postDelayed(mRunnable,800);
//    }

    /**
     * 点击X轴坐标或者折线节点
     *
     * @param event
     */
//    private void clickAction(MotionEvent event) {
//        int range = DensityUtil.dip2px(getContext(), 8);
//        float eventX = event.getX();
//        float eventY = event.getY();
//        for (int i = 0; i < linePoints.size(); i++) {
//            //节点
//            int x = linePoints.get(i).x;
//            int y = linePoints.get(i).y;
//            if (eventX >= x - range && eventX <= x + range &&
//                    eventY >= y - range && eventY <= y + range) {//每个节点周围4dp都是可点击区域
//                selectIndex = i;
//                isDrawHint = true;
//                removeCallbacks(mRunnable);//移除掉上次点击的runnable
//                invalidate();
//                return;
//            }
//        }
//    }

    /**
     * 检查向左滑动的距离 确保没有画出屏幕
     */
    private void checkTheLeftMoving() {
        if (leftMoving > (maxRight - minRight)) {
            leftMoving = maxRight - minRight;
        }
        if (leftMoving < 0) {
            leftMoving = 0;
        }
    }


    /**
     * 启动和关闭硬件加速   在绘制View的时候支持硬件加速,充分利用GPU的特性,使得绘制更加平滑,但是会多消耗一些内存。
     *
     * @param enabled
     */
    public void setHardwareAccelerationEnabled(boolean enabled) {

        if (android.os.Build.VERSION.SDK_INT >= 11) {

            if (enabled)
                setLayerType(View.LAYER_TYPE_HARDWARE, null);
            else
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } else {
            Log.e("error",
                    "Cannot enable/disable hardware acceleration for devices below API level 11.");
        }
    }

   /* private void drawShader(Canvas canvas, float startY, float endY) {
        setPaintShader();
        Path mPathShader = new Path();
        mPathShader.moveTo(0, startY);
        mPathShader.lineTo(scaleGap, endY);
        mPathShader.lineTo(scaleGap, height);//height
        mPathShader.lineTo(0, height);
        mPathShader.close();
        canvas.drawPath(mPathShader, mPaintShader);
    }*/
    //  初始化渐变色

    private Paint setPaintShader() {

       /* Shader mShader = new LinearGradient(300, 50, 300, 400,
                new int[] { Color.parseColor("#55FF7A00"), Color.TRANSPARENT }, null, Shader.TileMode.CLAMP);*/
        Shader mShader = new LinearGradient(0, 0, 0, mStartY,
                shadeColors, null, Shader.TileMode.CLAMP);
        linePaint.setShader(mShader);
        return linePaint;
        // 新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变


      /*  Shader shader = new LinearGradient(0, 0, 0, mStartY - ((showScaleResult ? resultNumRect.height() : 0) + rulerToResultgap - topMargin / 2), shadeColors, null, Shader.TileMode.CLAMP);
        mPaintShader.setShader(shader);*/
    }

    /**
     * 点击
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastPointX = event.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float movex = event.getX();
//                movingThisTime = lastPointX - movex;
//                leftMoving = leftMoving + movingThisTime;
//                lastPointX = movex;
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                invalidate();
//                lastPointX = event.getX();
//                break;
//            case MotionEvent.ACTION_CANCEL:
////                recycleVelocityTracker();
//                break;
//            default:
//                return super.onTouchEvent(event);
//        }
        if (mGestureListener != null) {
            mGestureListener.onTouchEvent(event);
        }
        return true;
    }

    private class RangeBarOnGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("GestureListener", "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e("GestureListener", "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            scrollX = e.getX();
            invalidate();
            Log.e("GestureListener", "onSingleTapUp");
          /*  int position = identifyWhichItemClick(e.getX(), e.getY());
            if (position != INVALID_POSITION && mOnItemBarClickListener != null) {
                mOnItemBarClickListener.onClick(position);
                setClicked(position);
                invalidate();
            }*/
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("GestureListener", "onScroll");
            //

            getParent().requestDisallowInterceptTouchEvent(true);
            scrollX = e2.getX();
            invalidate();
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

    }

}
