package bike.gymproject.viewlibray.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.List;


/**
 * Created by 李超凡 on 2019/2/28.
 */

public class CircleView extends View {

    private int color = 0xFF000000;
    //圆环的画笔
    private Paint cyclePaint;
    //View自身的宽和高
    private int mHeight;
    private int mWidth;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCycle(canvas);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCycle(Canvas canvas) {
        cyclePaint.setColor(color);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, cyclePaint);
    }
}

