package bike.gymproject.viewlibray;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;


/**
 * Created by Admin
 * Date 2020/3/4
 */
public class CusScheduleView extends View {

    private static final String TAG = "CusScheduleView";

    //总的进度画笔
    private Paint allSchedulePaint;
    //当前进度画笔
    private Paint currSchedulePaint;

    //总的进度颜色
    private int allShceduleColor;
    //当前进度颜色
    private int currShceduleColor;



    //宽度
    private float mWidth,mHeight;

    //所有进度值
    private float allScheduleValue = 0f;
    //当前进度值
    private float currScheduleValue = 0;

    private float animatCurrScheduleValue= 0f;

    private ValueAnimator objectAnimator;


    public CusScheduleView(Context context) {
        super(context);
    }

    public CusScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttar(context,attrs);
    }

    public CusScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttar(context,attrs);
    }

    private void initAttar(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CusScheduleView);
        allShceduleColor = typedArray.getColor(R.styleable.CusScheduleView_cus_all_schedule_color,Color.BLUE);
        currShceduleColor = typedArray.getColor(R.styleable.CusScheduleView_cus_curr_schedule_color,Color.BLUE);
        typedArray.recycle();


        initPaint();
    }



    private void initPaint() {
        allSchedulePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        allSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        allSchedulePaint.setAntiAlias(true);
        allSchedulePaint.setColor(allShceduleColor);
        allSchedulePaint.setStrokeCap(Paint.Cap.ROUND);
        allSchedulePaint.setTextSize(1f);

        currSchedulePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currSchedulePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        currSchedulePaint.setColor(currShceduleColor);
        currSchedulePaint.setTextSize(1f);
        currSchedulePaint.setStrokeCap(Paint.Cap.ROUND);
        currSchedulePaint.setAntiAlias(true);



    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0,mHeight);
        canvas.save();
        drawSchedule(canvas);
    }

    private void drawSchedule(Canvas canvas) {
        float currV = currScheduleValue >= allScheduleValue ? mWidth : currScheduleValue / allScheduleValue * mWidth;
        RectF rectF = new RectF(0,-mHeight,mWidth,0);
        canvas.drawRoundRect(rectF,15f,15f,allSchedulePaint);

        RectF currRectf = new RectF(0,-mHeight,currV,0);
//        canvas.drawRect(currRectf,currSchedulePaint);
        canvas.drawRoundRect(currRectf,15f,15f,currSchedulePaint);

    }


    public float getAllScheduleValue() {
        return allScheduleValue;
    }

    public void setAllScheduleValue(float allScheduleValue) {
        this.allScheduleValue = allScheduleValue;
        invalidate();
    }

    public float getCurrScheduleValue() {
        return currScheduleValue;
    }

    public void setCurrScheduleValue(float currScheduleValue) {
        this.currScheduleValue = currScheduleValue;
        invalidate();
    }



    public void setCurrScheduleValue(float currScheduleValues, final long time){
        float currV = currScheduleValue >= allScheduleValue ? getMeasuredWidth() : currScheduleValue / allScheduleValue * getMeasuredWidth();
        objectAnimator = ObjectAnimator.ofFloat(0,currV);//new TranslateAnimation(0,currV, Animation.ABSOLUTE,Animation.ABSOLUTE);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float tmpV = (float) animation.getAnimatedValue();

               // currScheduleValue = (float) animation.getAnimatedValue();
               // postInvalidate();
            }
        });
        objectAnimator.setStartDelay(500);
        objectAnimator.setDuration(time);
        objectAnimator.setRepeatCount(1);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

    }


}
