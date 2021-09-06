package bike.gymproject.viewlibray.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;

public class SleepColumntChar extends BaseView {
    public SleepColumntChar(Context context) {
        super(context);
    }

    public SleepColumntChar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SleepColumntChar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawColumn(Canvas canvas, Paint mPaint) {


    }

    @Override
    protected void drawYAxisScaleValue(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawBg(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawYAxisScale(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawMaxOrMin(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawXAxisScaleValue(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawXAxisScale(Canvas canvas, Paint mPaint) {

        canvas.drawText("12.12", originalY, lineStartX + originalX, mPaint);

        float start = lineStartX;

        float any = width / 9;

        float textWidth = mTextPaint.measureText("12.12");
        float Textx = start - textWidth / 2;


        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = mTextBound.centerY() + distance;


        for (int i = 1; i < 8; i++) {
            canvas.drawText("12.12", Textx + (any / 2) + any * i, originalY + baseline, mPaint);
        }

    }

    @Override
    protected void drawYAxis(Canvas canvas, Paint mPaint) {
        float start = lineStartX;

        float any = height / 5;

        float textWidth = mTextPaint.measureText("3小时");
        float Textx = start - textWidth / 2;


        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = mTextBound.centerY() + distance;


        for (int i = 1; i < 4; i++) {
            canvas.drawText(i + "小时", Textx, originalY - any * i + baseline, mPaint);
        }
        //canvas.drawText(150 + "", Textx, distance * 2, mPaint);
    }

    @Override
    protected void drawXAxis(Canvas canvas, Paint mPaint) {

        float start = originalX + lineStartX;
        float any = height / 5;

        Path path = new Path();

        for (int i = 1; i < 4; i++) {
            path.moveTo(start, originalY - any * i);
            path.lineTo(width, originalY - any * i);
        }


        canvas.drawPath(path, mPaint);

    }
}
