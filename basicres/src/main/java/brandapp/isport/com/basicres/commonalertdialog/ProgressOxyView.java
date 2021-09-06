package brandapp.isport.com.basicres.commonalertdialog;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.isport.brandapp.basicres.R;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


public class ProgressOxyView extends View {
    float radius;

    float progress = 80;
    RectF arcRectF = new RectF();
    Context mContext;


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint arcCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // ColorInt
    private int startColor;
    // ColorInt
    private int endColor;


    LinearGradient lg;

    public ProgressOxyView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public ProgressOxyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public ProgressOxyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initPaint();
    }

    float centerX;
    float centerY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        radius = (getHeight()) / 2;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    private void initPaint() {


        startColor = mContext.getResources().getColor(R.color.common_view_color);
        endColor = mContext.getResources().getColor(R.color.common_view_color);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(DisplayUtils.dip2px(mContext, 40));
        AssetManager assets = mContext.getAssets();


        //paint.setStrokeWidth(DisplayUtils.dip2px(mContext, 10));
        paint.setColor(Color.parseColor("#334dda64"));
        //paint.setColor(mContext.getResources().getColor(R.color.common_bg));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);

        arcCirclepaint.setColor(mContext.getResources().getColor(R.color.common_view_color));
        arcCirclepaint.setStyle(Paint.Style.FILL);
        arcCirclepaint.setStrokeCap(Paint.Cap.ROUND);

    }

    private float percent = 1f;
    private TimeInterpolator pointInterpolator = new DecelerateInterpolator();
    ValueAnimator mAnimator;

    public void startAnimation(int time) {
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(time * 1000);
        mAnimator.setInterpolator(pointInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                if (percent >= 0.95) {
                    if (onlcik != null) {
                    }
                    percent = 0.95f;
                }
                onlcik.onProgress(percent);
                invalidate();
            }
        });
        mAnimator.start();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        this.percent = 0.5f;
        if (onlcik != null) {
            onlcik.onProgress(1);
        }

        // }
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progress == 80) {
            arcRectF.set(0, centerY - radius, getWidth(), centerY + radius);
            canvas.drawRoundRect(arcRectF, DisplayUtils.dip2px(mContext, 20), DisplayUtils.dip2px(mContext, 20), paint);
            //arcRectF.set(0, centerY - radius, ((progress - 80) / 20 * (getWidth() - DisplayUtils.dip2px(mContext, 25))) + DisplayUtils.dip2px(mContext, 25), centerY + radius);
            //canvas.drawRoundRect(arcRectF, DisplayUtils.dip2px(mContext, 20), DisplayUtils.dip2px(mContext, 20), arcCirclepaint);
        } else {
            arcRectF.set(0, centerY - radius, getWidth(), centerY + radius);
            canvas.drawRoundRect(arcRectF, DisplayUtils.dip2px(mContext, 20), DisplayUtils.dip2px(mContext, 20), paint);
            arcRectF.set(0, centerY - radius, ((progress - 80) / 20 * (getWidth() - DisplayUtils.dip2px(mContext, 25))) + DisplayUtils.dip2px(mContext, 25), centerY + radius);
            canvas.drawRoundRect(arcRectF, DisplayUtils.dip2px(mContext, 20), DisplayUtils.dip2px(mContext, 20), arcCirclepaint);
        }


    }

    ProgressValueOnlcik onlcik;

    public void setProgressListen(ProgressValueOnlcik onlcik) {
        this.onlcik = onlcik;
    }

    public interface ProgressValueOnlcik {
        void onProgress(float progress);
    }

}
