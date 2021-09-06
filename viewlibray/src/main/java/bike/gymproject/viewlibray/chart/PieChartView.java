package bike.gymproject.viewlibray.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import java.util.List;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


/**
 * Created by 李超凡 on 2019/2/28.
 */

public class PieChartView extends View {

    //-------------必须给的数据相关-------------
    private String[] str = new String[]{"一年级", "二年级", "三年级", "四年级", "五年级", "六年级"};
    //分配比例大小，总比例大小为100,由于经过运算后最后会是99.55左右的数值，导致圆不能够重合，会留出点空白，所以这里的总比例大小我们用101
//    private int[] strPercent = new int[]{10, 25, 18, 41, 7};
    private List<PieChartData> pieChartDatas;// = new int[]{10, 25, 18, 41, 7};
    //选中条目,值是0-5
    private int selectItem = 5;
    //圆的直径
    private float mRadius;
    //圆的粗细
    private float mStrokeWidth = 40;
    //文字大小
    private int textSize = 20;
    //-------------画笔相关-------------
    //圆环的画笔
    private Paint cyclePaint;
    //文字的画笔
    private Paint textPaint;
    //标注的画笔
    private Paint labelPaint;
    //-------------颜色相关-------------
    //边框颜色和标注颜色
//    private int[] mColor = new int[]{0xFFF06292, 0xFF9575CD, 0xFFE57373, 0xFF4FC3F7, 0xFFFFF176, 0xFF81C784};
    private int[] mColor = new int[]{0xFFF16565, 0xFFFFED77, 0xFF36D7B7, 0xFF3558DA, 0xFF868A8E};
    //文字颜色
    private int textColor = 0xFF000000;
    //-------------View相关-------------
    //View自身的宽和高
    private int mHeight;
    private int mWidth;
    private Context mContext;

    public void updateData(List<PieChartData> pieChartDatas) {
        this.pieChartDatas = pieChartDatas;
        invalidate();
    }

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mRadius = DisplayUtils.dip2px(context, 75);
        //初始化画笔
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //移动画布到圆环的左上角
        canvas.translate(mWidth / 2 - mRadius / 2, mHeight / 2 - mRadius / 2);

        //画圆环
        drawCycle(canvas);
        //画文字和标注
        drawTextAndLabel(canvas);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setStrokeWidth(DisplayUtils.dip2px(mContext, 25));
        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(1);
        textPaint.setTextSize(textSize);
        //标注画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setStrokeWidth(2);
    }

    /**
     * 画圆环
     *
     * @param canvas
     */
    private void drawCycle(Canvas canvas) {
        float startPercent = 0;
        float sweepPercent = 0;
        float pre = 0;
        if (pieChartDatas != null && pieChartDatas.size() > 0) {
            for (int i = 0; i < pieChartDatas.size(); i++) {
                pre += pieChartDatas.get(i).percent;
            }
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
                canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, cyclePaint);
            }
        }
    }

    /**
     * 画文字和标注
     *
     * @param canvas
     */
    private void drawTextAndLabel(Canvas canvas) {
        if (pieChartDatas != null && pieChartDatas.size() > 0) {
            for (int i = 0; i < pieChartDatas.size(); i++) {
//            //文字离右边环边距为60，文字与文字之间的距离为40
//            canvas.drawText(str[i], mRadius + 60, i * 40, textPaint);
//            //画标注,标注离右边环边距为40,y轴则要减去半径（10）的一半才能对齐文字
//            labelPaint.setColor(mColor[i]);
//            canvas.drawCircle(mRadius + 40, i * 40 - 5, 10, labelPaint);
            }
        }
    }

}

