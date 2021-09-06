package bike.gymproject.viewlibray.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;

public class HeartChar extends BaseView {
    public HeartChar(Context context) {
        super(context);
    }

    public HeartChar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartChar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawColumn(Canvas canvas, Paint mPaint) {


        if (lineBeans == null) {
            return;
        }

        Path path = new Path();
        path.moveTo(0, 0);
        float widtha = (width - (lineStartX + originalX)) / lineBeans.size();
        for (int i = 0; i < lineBeans.size(); i++) {
            if (i == 0) {
                path.moveTo(lineStartX + originalX + widtha / 2, originalY - 1.0f * lineBeans.get(i) / topValue * height);
            }
            path.lineTo(lineStartX + originalX + widtha / 2 + (widtha * i), originalY - 1.0f * lineBeans.get(i) / topValue * height);
        }


        canvas.drawPath(path, mPaint);


    }

    @Override
    protected void drawYAxisScaleValue(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawBg(Canvas canvas, Paint mPaint) {
        Rect rect = new Rect();
        rect.left = (int) (originalX + lineStartX);
        rect.top = (int) -(height);
        rect.bottom = (int) originalY;
        rect.right = (int) width;
        canvas.drawRect(rect, mPaint);
    }

    @Override
    protected void drawYAxisScale(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawMaxOrMin(Canvas canvas, Paint mPaint) {
        if (lineBeans == null) {
            return;
        }
        float widtha = (width - (lineStartX + originalX)) / lineBeans.size();
        for (int i = 0; i < lineBeans.size(); i++) {

            if (maxIndex == i) {
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float baseline = mTextBound.centerY() + distance;
                canvas.drawText(currentMaxValue + "", (lineStartX + originalX + widtha / 2 + (widtha * i)), (originalY - 1.0f * lineBeans.get(i) / topValue * height) - baseline, mPaint);
                canvas.drawCircle((lineStartX + originalX + widtha / 2 + (widtha * i)), (originalY - 1.0f * lineBeans.get(i) / topValue * height), 8, mCirclePaint);
            }
            if (minIndex == i) {
                Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
                float baseline = mTextBound.centerY() + distance;
                canvas.drawText(currentMinValue + "", (lineStartX + originalX + widtha / 2 + (widtha * i)), (originalY - 1.0f * lineBeans.get(i) / topValue * height) + 3 * baseline, mPaint);
                canvas.drawCircle((lineStartX + originalX + widtha / 2 + (widtha * i)), (originalY - 1.0f * lineBeans.get(i) / topValue * height), 8, mCirclePaint);

            }
        }
    }

    @Override
    protected void drawXAxisScaleValue(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawXAxisScale(Canvas canvas, Paint mPaint) {

    }

    @Override
    protected void drawYAxis(Canvas canvas, Paint mPaint) {
        float start = lineStartX;

        float any = height / 4;

        float textWidth = mTextPaint.measureText("150");
        float Textx = start - textWidth / 2;


        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = mTextBound.centerY() + distance;


        for (int i = 0; i < 5; i++) {
            canvas.drawText(i * topValue / 4 + "", Textx, originalY - any * i + baseline, mPaint);
        }
        //canvas.drawText(150 + "", Textx, distance * 2, mPaint);
    }

    @Override
    protected void drawXAxis(Canvas canvas, Paint mPaint) {

        float start = originalX + lineStartX;
        float any = height / 4;

        Path path = new Path();

        for (int i = 0; i < 5; i++) {
            path.moveTo(start, originalY - any * i);
            path.lineTo(width, originalY - any * i);
        }


        canvas.drawPath(path, mPaint);

    }
}
