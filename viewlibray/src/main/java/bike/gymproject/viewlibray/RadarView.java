package bike.gymproject.viewlibray;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class RadarView extends View {


    // 数据个数
    private int count = 5;
    //每个扇形的弧度
    private float angle = (float) (Math.PI * 2 / count);
    // 网格最大半径
    private float radius;
    // 中心X
    private int centerX;
    // 中心Y
    private int centerY;


    // 各标题
    private String[] titles = {"水分量", "内脏脂肪", "蛋白质率", "肌肉量", "BMI"};

    private String[] titlesValue = {"5.4", "24.2", "10.4", "24.2", "24.2"};
    // 各维度分值
    private double[] data = {100, 60, 65, 75, 100,};
    // 刻度值
    private String[] graduationValue = {"0", "10", "20", "30", "40", "50"};
    // 数据最大值
    private float maxValue = 100;
    // 雷达区画笔
    private Paint mainPaint;
    // 数据区画笔
    private Paint valuePaint;
    // title画笔
    private Paint titlePaint;
    // 刻度值画笔
    private Paint graduationPaint;
    //判断点的所在区域的临界值，根据临界值设置点的颜色
    //100~70（包含）之间绿色，30（不包含）~70（不包含）橙色，小于等于30红色
    private float curR4;
    private float curR7;
    private float curR10;
    private int ten;


    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarView(Context context) {
        super(context);
        init();
    }

    // 初始化
    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;

        ten = (int) (0.0094 * screenWidth);
        int forty = (int) (0.036 * screenWidth);
        count = Math.min(data.length, titles.length);

        // 初始化雷达区画笔
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(Color.parseColor("#E7E8EB"));
        mainPaint.setStrokeWidth(2);
        mainPaint.setStyle(Paint.Style.STROKE);

        // 初始化数据区画笔
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setStrokeWidth(2);
        valuePaint.setColor(Color.parseColor("#FFCE01"));
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 初始化title画笔
        titlePaint = new Paint();

        titlePaint.setTextSize(forty);
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setColor(Color.parseColor("#101D37"));

        // 初始化刻度值画笔
        graduationPaint = new Paint();
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        graduationPaint.setTypeface(font);
        graduationPaint.setTextSize(18);
        graduationPaint.setStyle(Paint.Style.FILL);
        graduationPaint.setColor(Color.parseColor("#D0D0D0"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.7f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawLines(canvas);
        drawGraduationValue(canvas);
        drawRegion(canvas);
        drawSmallCircle(canvas);
    }

    /**
     * 绘制圆
     *
     * @param canvas Canvas
     */
    private void drawCircle(Canvas canvas) {
        Path path = new Path();
        float r = radius / 5;
        for (int i = 1; i < 6; i++) {
            float curR = r * i;
            curR4 = r * 4;
            curR7 = r * 7;
            curR10 = r * 10;
            for (int j = 1; j < 11; j += 2) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                }
            }
            canvas.drawCircle(centerX, centerY, curR, mainPaint);
        }
    }

    /**
     * 绘制直线
     *
     * @param canvas Canvas
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
            drawTitles(canvas, i, x, y);
        }
    }

    /**
     * 绘制title
     *
     * @param canvas Canvas
     * @param i      index
     * @param x      每条线的终点的X坐标
     * @param y      每条线的终点的Y坐标
     */
    private void drawTitles(Canvas canvas, int i, float x, float y) {
        Paint.FontMetrics fontMetrics = titlePaint.getFontMetrics();
        //文本高度
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        Rect rect = new Rect();
        titlePaint.getTextBounds(titlesValue[i], 0, titlesValue[i].length(), rect);
        //文本的宽度
        int fontWidth = rect.width();
        //在每条线的终点基础之上去确定title的坐标
        float titleX = (float) (x + fontWidth * Math.cos(angle * i)) - (fontWidth / 2);
        float titleY = (float) (y + fontHeight * Math.sin(angle * i)) + ten*2;
        canvas.drawText(titlesValue[i], titleX, titleY, titlePaint);

        Rect rect1 = new Rect();
        titlePaint.getTextBounds(titlesValue[i], 0, titlesValue[i].length(), rect1);

        int fontValueWidth = rect1.width();
        //在每条线的终点基础之上去确定title的坐标
        float titleValueX = (float) (x + fontValueWidth * Math.cos(angle * i)) - (fontValueWidth / 2);
        float titleValueY = (float) titleY + fontHeight;


        canvas.drawText(titles[i], titleValueX, titleValueY, titlePaint);
    }

    /**
     * 绘制刻度值
     *
     * @param canvas Canvas
     */
    private void drawGraduationValue(Canvas canvas) {
      /*  Paint.FontMetrics fontMetrics = graduationPaint.getFontMetrics();
        //文本高度
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        //刻度值的X轴坐标
        float graduationX = centerX - fontHeight * 6 / 5;
        //网层与网层之间的距离
        float r = radius / 5;
        for (int j = 0; j < graduationValue.length; j++) {
            float curR = r * j;
            float graduationY = centerY - curR;
            canvas.drawText(graduationValue[j], graduationX, graduationY, graduationPaint);
        }*/
    }

    private void drawSmallCircle(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }

            double sx = Math.PI
                    * ((x - centerX) * (x - centerX) + (y - centerY)
                    * (y - centerY));
            double s1 = Math.PI * curR4 * curR4;
            double s2 = Math.PI * curR7 * curR7;
            double s3 = Math.PI * curR10 * curR10;
            if (0 < sx && sx <= s1) {
                valuePaint.setColor(Color.parseColor("#FFCE01"));
                canvas.drawCircle(x, y, ten, valuePaint);
            }
            if (s1 < sx && sx <= s2) {
                valuePaint.setColor(Color.parseColor("#FFCE01"));
                canvas.drawCircle(x, y, ten, valuePaint);
            }
            if (s2 < sx && sx <= s3) {
                valuePaint.setColor(Color.parseColor("#FFCE01"));
                canvas.drawCircle(x, y, ten, valuePaint);
            }
        }
        path.close();
       /* valuePaint.setColor(Color.parseColor("#00BF33"));
        // 描边
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);

        // 绘制填充区域的透明度(不需要填充可注释掉下边三行代码)
        valuePaint.setAlpha(45);
        // 绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);*/
    }

    /**
     * 绘制区域
     *
     * @param canvas Canvas
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }

            double sx = Math.PI
                    * ((x - centerX) * (x - centerX) + (y - centerY)
                    * (y - centerY));
            double s1 = Math.PI * curR4 * curR4;
            double s2 = Math.PI * curR7 * curR7;
            double s3 = Math.PI * curR10 * curR10;
           /* if (0 < sx && sx <= s1) {
                valuePaint.setColor(Color.parseColor("#FF2022"));
                canvas.drawCircle(x, y, ten, valuePaint);
            }
            if (s1 < sx && sx <= s2) {
                valuePaint.setColor(Color.parseColor("#FFA10C"));
                canvas.drawCircle(x, y, ten, valuePaint);
            }
            if (s2 < sx && sx <= s3) {
                valuePaint.setColor(Color.parseColor("#00BF33"));
                canvas.drawCircle(x, y, ten, valuePaint);
            }*/
        }
        path.close();

        // 描边
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);

        // 绘制填充区域的透明度(不需要填充可注释掉下边三行代码)
        valuePaint.setAlpha(45);
        // 绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

    // 设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    // 设置数值
    public void setData(double[] data) {
        this.data = data;
    }

    public float getMaxValue() {
        return maxValue;
    }

    // 设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    // 设置蜘蛛网颜色
    public void setMainPaintColor(int color) {
        mainPaint.setColor(color);
    }

    // 设置标题颜色
    public void setTextPaintColor(int color) {
        titlePaint.setColor(color);
    }

    // 设置覆盖局域颜色
    public void setValuePaintColor(int color) {
        valuePaint.setColor(color);
    }

}
