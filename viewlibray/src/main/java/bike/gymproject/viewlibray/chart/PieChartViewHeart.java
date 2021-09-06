package bike.gymproject.viewlibray.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


/**
 * Created by 李超凡 on 2019/2/28.
 */

public class PieChartViewHeart extends View {

    //-------------必须给的数据相关-------------
    private String[] str = new String[]{"一年级", "二年级", "三年级", "四年级", "五年级", "六年级"};
    //分配比例大小，总比例大小为100,由于经过运算后最后会是99.55左右的数值，导致圆不能够重合，会留出点空白，所以这里的总比例大小我们用101
//    private int[] strPercent = new int[]{10, 25, 18, 41, 7};
    private List<PieChartData> pieChartDatas;// = new int[]{10, 25, 18, 41, 7};
    //选中条目,值是0-5
    //圆的直径
    private float mRadius;
    //圆的粗细
    //文字大小
    //-------------画笔相关-------------
    //圆环的画笔
    private Paint cyclePaint, outerLinePaint, innerCircle;
    //文字的画笔
    //-------------View相关-------------
    //View自身的宽和高
    private int mHeight;
    private int mWidth;
    private Context mContext;
    private boolean isShowCircle;

    public void updateData(List<PieChartData> pieChartDatas) {
        this.pieChartDatas = pieChartDatas;
        isShowCircle = true;
        invalidate();

    }

    public void updateData(List<PieChartData> pieChartDatas, boolean isShowCircel) {
        this.pieChartDatas = pieChartDatas;
        isShowCircle = isShowCircel;
        invalidate();
    }

    public PieChartViewHeart(Context context) {
        this(context, null);
    }

    public PieChartViewHeart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartViewHeart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mRadius = DisplayUtils.dip2px(context, 110);
        //初始化画笔
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private static final int BG_COLOR = Color.TRANSPARENT;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //移动画布到圆环的左上角
        canvas.drawColor(BG_COLOR);
        // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        canvas.translate(mWidth / 2 - mRadius / 2, mHeight / 2 - mRadius / 2);

        //画圆环
        drawCycle(canvas);

        //画文字和标注
        drawTextAndLabel(canvas);
    }

    /**
     * 初始化画笔
     */
    private static final float OUTER_LINE_WIDTH = 3f;

    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.FILL);

        innerCircle = new Paint();
        innerCircle.setAntiAlias(true);
        innerCircle.setStyle(Paint.Style.FILL);
        innerCircle.setColor(Color.WHITE);


        // cyclePaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 30));


        outerLinePaint = new Paint();
        outerLinePaint.setAntiAlias(true);
        outerLinePaint.setAlpha(255);
        outerLinePaint.setStyle(Paint.Style.STROKE);
        outerLinePaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 2));
        outerLinePaint.setColor(Color.WHITE);

    }

    /**
     * 画圆环
     *
     * @param canvas
     */
    private RectF rectF = new RectF();

    private void drawCycle(Canvas canvas) {
        float startPercent = 0;
        float sweepPercent = 0;
        float pre = 0;
        if (pieChartDatas != null && pieChartDatas.size() > 0) {
            for (int i = 0; i < pieChartDatas.size(); i++) {
                pre += pieChartDatas.get(i).percent;
            }
            rectF.set(0, 0, mRadius, mRadius);
            // pre = pre - 1;
            pre = pre;
            for (int i = 0; i < pieChartDatas.size(); i++) {
                cyclePaint.setColor(pieChartDatas.get(i).color);
                startPercent = sweepPercent + startPercent;
                //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                if (pre != 0) {
                    sweepPercent = pieChartDatas.get(i).percent * 360 * 1.0f / pre;
                } else {
                    sweepPercent = pieChartDatas.get(i).percent * 360;
                }
                canvas.drawArc(rectF, startPercent, sweepPercent, true, cyclePaint);
                if (isShowCircle) {
                    canvas.drawArc(rectF, startPercent, sweepPercent, true, outerLinePaint);
                }
            }
            canvas.drawCircle(rectF.centerX(), rectF.centerY(), DisplayUtils.dip2px(mContext, 25), innerCircle);


        }
    }

    /**
     * 画文字和标注
     *
     * @param canvas
     */
    private void drawTextAndLabel(Canvas canvas) {
    }

}

