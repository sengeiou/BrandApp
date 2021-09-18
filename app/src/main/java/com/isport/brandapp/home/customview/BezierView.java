package com.isport.brandapp.home.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.R;
import com.isport.brandapp.device.scale.bean.ScaleCharBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

/**
 * Created by zouxiang on 2016/9/22.
 */

public class BezierView extends View {

    public String TAG = "BezierView";

    private Context mContext;
    private float lineSmoothness = 0.2f;
    private List<Point> mPointList = new ArrayList<>();
    private Path mPath;
    private Path mAssistPath;
    private float drawScale = 1f;
    private PathMeasure mPathMeasure;
    private int mTopMargin;
    private float defYAxisBottom, defYAxisTop;
    private float defXAxis = 10f;
    private int mTotalWidth, mTotalHeight, maxHeight;
    private float viewHeight;
    private float maxValue, minValue, middleValue;
    private Path dst;
    int len;

    /**
     * 是否绘制点击效果
     */
    private boolean isDrawBorder;


    private float scrollx = 0;
    /**
     * 点击的地方
     */
    private int mClickPosition = -1;


    //距离顶部的多少 用来显示顶部的文字
    private int topMargin;
    /**
     * 每一个bar的宽度
     */
    private float barWidth;

    public BezierView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    Paint paintBg, linePaint, selectPaint, trianglePaint, greenLine, outCirclePaint, mPaintText, mPaintValue;
    Rect rectBg;
    RectF recTime = new RectF();
    Paint thumbStartPaint;
    Bitmap progressStartMark;

    public void initPaint() {

        thumbStartPaint = new Paint();
        thumbStartPaint.setAntiAlias(true);
        progressStartMark = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_move);
        mGestureListener = new GestureDetector(mContext, new RangeBarOnGestureListener());
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        topMargin = DisplayUtils.dip2px(mContext, 30);
        if (paintBg == null) {
            paintBg = new Paint();
            paintBg.setColor(mContext.getResources().getColor(R.color.scale_bg));
            paintBg.setStyle(Paint.Style.FILL);

        }

        if (linePaint == null) {
            linePaint = new Paint();
            linePaint.setColor(mContext.getResources().getColor(R.color.white));
            linePaint.setStyle(Paint.Style.STROKE);
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            linePaint.setStrokeWidth(2);
            linePaint.setPathEffect(new DashPathEffect(new float[]{15, 2}, 0));
            linePaint.setDither(true);
            linePaint.setAntiAlias(true);
        }
        if (selectPaint == null) {
            selectPaint = new Paint();
            selectPaint.setColor(mContext.getResources().getColor(R.color.common_view_color));
            selectPaint.setStyle(Paint.Style.STROKE);
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            selectPaint.setStrokeWidth(1);
            //selectPaint.setPathEffect(new DashPathEffect(new float[]{15, 2}, 0));
            //selectPaint.setDither(true);
            selectPaint.setAntiAlias(true);
        }

        if (trianglePaint == null) {
            trianglePaint = new Paint();
            trianglePaint.setColor(mContext.getResources().getColor(R.color.common_view_color));
            trianglePaint.setStyle(Paint.Style.FILL);
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            trianglePaint.setStrokeWidth(3);
            trianglePaint.setDither(true);
            trianglePaint.setAntiAlias(true);
        }
        if (greenLine == null) {
            greenLine = new Paint();
            greenLine.setColor(mContext.getResources().getColor(R.color.common_view_color));
            greenLine.setStyle(Paint.Style.STROKE);
            // mPaint.setShadowLayer(3, 0, 0, ContextCompat.getColor(mContex, R.color.common_text_color));
            greenLine.setStrokeWidth(2);
            greenLine.setDither(true);
            greenLine.setAntiAlias(true);
        }

        mPaintValue = new Paint();
        mPaintValue.setAntiAlias(true);
        mPaintValue.setColor(Color.parseColor("#4DDA64"));
        mPaintValue.setStyle(Paint.Style.FILL);
        mPaintValue.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintValue.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintValue.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintText.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));

        if (outCirclePaint == null) {
            outCirclePaint = new Paint();
            outCirclePaint.setColor(Color.WHITE);
            outCirclePaint.setStrokeWidth(3);
            outCirclePaint.setStyle(Paint.Style.FILL);
        }
        if (dst == null) {
            dst = new Path();
        }
        rectBg = new Rect();
        rectBg.left = 0;
        rectBg.top = 0;


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    ArrayList<Float> userWeights = new ArrayList<>();
    ArrayList<ScaleCharBean> list = new ArrayList<>();

    boolean isSame;

    public synchronized void setPointListFloat(List<ScaleCharBean> weights) {
        //view还没加载成功
        isSame = false;
        list.clear();
        list.addAll(weights);
        //数据不同
       /* if (userWeights.size() != weights.size()) {
            isSame = false;

        } else {
            for (int i = 0; i < weights.size(); i++) {
                if (weights.get(i) != userWeights.get(i)) {
                    isSame = false;
                    break;
                    //不同就去重新弄
                }
            }
        }*/


        Logger.myLog("viewHeight" + viewHeight + "isSame：" + isSame);

        // if (!isSame) {
        //重复的数据不需要去画
        userWeights.clear();
        for (int i = 0; i < weights.size(); i++) {
            userWeights.add(weights.get(i).getWeight());
        }
        mPointList.clear();
        for (int i = 0; i < userWeights.size(); i++) {
            mPointList.add(new Point(0, 0));
        }
        calMaxMinMinValue();
        // Logger.myLog("viewHeight" + viewHeight + "isSame：" + isSame);
        if (isSame) {

            // userWeights.clear();
            measurePath();
            isDrawBorder = true;

            //  if (mClickPosition < 0 || mClickPosition >= userWeights.size()) {
            mClickPosition = userWeights.size() - 1;
            if (mOnItemBarClickListener != null) {
                mOnItemBarClickListener.onClick(mClickPosition);
            }
            // Logger.myLog("viewHeight" + viewHeight + "mClickPosition：" + mClickPosition);
        } else {
            if (mOnItemBarClickListener != null) {
                mOnItemBarClickListener.onClick(mClickPosition);
            }
        }
/*
        } else {
            userWeights.clear();
        }*/
        // Logger.myLog("setPointListFloat");
        //}
        postInvalidate();
    }


    public void calMaxMinMinValue() {
        defXAxis = DisplayUtils.dip2px(mContext, 10);
        len = userWeights.size();
        if (len < 7) {
            barWidth = DisplayUtils.dip2px(mContext, 30);
        } else {
            barWidth = (mTotalWidth - 2 * defXAxis - DisplayUtils.dip2px(mContext, 5)) / (float) (len - 1);
        }
        maxValue = Integer.MIN_VALUE;
        minValue = Integer.MAX_VALUE;

        maxValue = Collections.max(userWeights);
        minValue = Collections.min(userWeights);
        middleValue = (maxValue - minValue) / 2 + minValue;
        viewHeight = (defYAxisBottom - defYAxisTop) / (maxValue - minValue);//多少份


        for (int i = 0; i < len; i++) {
            if (maxValue == minValue) {
                mPointList.get(i).y = (int) ((defYAxisBottom - defYAxisTop) / 2 + defYAxisTop);
            } else {
                mPointList.get(i).y = (int) (defYAxisBottom - (int) ((userWeights.get(i) - minValue) * viewHeight));
            }
            mPointList.get(i).x = (int) (defXAxis + (barWidth * i));


            //Logger.myLog("mPointList.get(i).y:" + mPointList.get(i).y + "mPointList.get(i).x:" + mPointList.get(i).x);

            isSame = true;
            if (mPointList.get(i).y == 0) {
                isSame = false;
                return;
            }
            // Logger.myLog("viewHeight mPointList.get(i).y" + mPointList.get(i).y + "isSame：" + isSame);
        }


        //  Logger.myLog(TAG + "mTotalWidth:" + mTotalWidth + ",minValue:" + minValue + ",maxValue:" + maxValue + ",middleValue:" + middleValue + ",viewHeight" + viewHeight + "defYAxisBottom:" + defYAxisBottom);

    }

    private void drawTimeText(Canvas canvas, float x, float y, String text) {


        //计算矩形显示的位置
        //画一个矩形
        Log.e("drawTimeText", "text=" + text);
        // text = "2020-09-09";
        float len = mPaintText.measureText(text);

        //    canvas.draw
        float left = x - ((len / 2) + DisplayUtils.dip2px(mContext, 10));
        float right = x + ((len / 2));
        if ((x - len / 2) < defXAxis) {
            left = 0;
            right = left + len + DisplayUtils.dip2px(mContext, 10);
        } else if (right > getWidth()) {
            right = getWidth();
            left = getWidth() - len - DisplayUtils.dip2px(mContext, 10);
        }
        recTime.set(left, y - DisplayUtils.dip2px(mContext, 15), right, y + DisplayUtils.dip2px(mContext, 10));
        canvas.drawRoundRect(recTime, 10, 10, mPaintValue);
        canvas.drawText(text, left + DisplayUtils.dip2px(mContext, 5), y, mPaintText);
    }

    private GestureDetector mGestureListener;

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

        if (event.getAction() == MotionEvent.ACTION_UP) {
            getParent().requestDisallowInterceptTouchEvent(false);
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (mGestureListener != null) {
            mGestureListener.onTouchEvent(event);
        }
        return true;
    }

    public void setLineSmoothness(float lineSmoothness) {
        if (lineSmoothness != this.lineSmoothness) {
            this.lineSmoothness = lineSmoothness;
            measurePath();
            postInvalidate();
        }
    }

    public void setDrawScale(float drawScale) {
        this.drawScale = drawScale;
        postInvalidate();
    }

    public void startAnimation(long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "drawScale", 0f, 1f);
        animator.setDuration(duration);
        animator.start();
    }


    public void drawLine(Canvas canvas) {


        canvas.drawLine(0, defYAxisBottom, mTotalWidth, defYAxisBottom, linePaint);
        canvas.drawLine(0, defYAxisTop, mTotalWidth, defYAxisTop, linePaint);
        canvas.drawLine(0, (defYAxisBottom - defYAxisTop) / 2 + defYAxisTop, mTotalWidth, (defYAxisBottom - defYAxisTop) / 2 + defYAxisTop, linePaint);

    }

    float distance;
    LinearGradient lg;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPointList == null || mPathMeasure == null)
            return;
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        rectBg.left = 0;
        rectBg.top = (int) defYAxisTop;
        rectBg.right = mTotalWidth;
        rectBg.bottom = (int) defYAxisBottom;
        canvas.drawRect(rectBg, paintBg);
        drawLine(canvas);
        if (scrollx < defXAxis) {
            scrollx = defXAxis;
        }
        if (scrollx > mTotalWidth - defXAxis) {
            scrollx = mTotalWidth - defXAxis;
        }

        dst.reset();
        dst.rLineTo(0, 0);
        if (mPathMeasure != null) {
            distance = mPathMeasure.getLength() * drawScale;

            if (userWeights.size() == 1) {

                //canvas.drawLine(0, (defYAxisBottom - defYAxisTop) / 2 + defYAxisTop, mTotalWidth, (defYAxisBottom - defYAxisTop) / 2 + defYAxisTop, linePaint);

                if (mPointList.get(0).y == 0) {
                    return;
                }
                float[] pos = new float[]{
                        (float) mPointList.get(0).x, (float) mPointList.get(0).y
                };
                drawPoint(canvas, pos);

            } else {
                if (mPathMeasure.getSegment(0, distance, dst, true)) {
                    //绘制线
                    canvas.drawPath(dst, greenLine);
                    float[] pos = new float[2];
                    mPathMeasure.getPosTan(distance, pos, null);
                    //绘制阴影
                    // drawShadowArea(canvas, dst, pos);
                    //绘制点

                    drawPoint(canvas, pos);
                }
            }


            //if (isDrawBorder ) {

            lg = new LinearGradient(scrollx, 0, scrollx, defYAxisBottom, mContext.getResources().getColor(R.color.white), mContext.getResources().getColor(R.color.common_view_color), Shader.TileMode.MIRROR);
            selectPaint.setShader(lg);
            canvas.drawLine(scrollx, 0, scrollx, defYAxisBottom, selectPaint);

            if (isDrawBorder && mClickPosition >= 0 && mPointList.get(mClickPosition).y != 0) {
                drawTimeText(canvas, centerX, defYAxisTop - DisplayUtils.dip2px(mContext, 15), list.get(mClickPosition).getStarDate());
                canvas.drawCircle(centerX, mPointList.get(mClickPosition).y, DisplayUtils.dip2px(mContext, 3), trianglePaint);
            }

            //  Path path = new Path();


            canvas.drawBitmap(progressStartMark, scrollx - progressStartMark.getWidth() / 2, mTotalHeight - progressStartMark.getHeight() + DisplayUtils.dip2px(mContext, 3), thumbStartPaint);

            /*mPath.reset();
            mPath.moveTo(scrollx, mTotalHeight - DisplayUtils.dip2px(mContext, 12));
            mPath.lineTo(scrollx - DisplayUtils.dip2px(mContext, 11), mTotalHeight);
            mPath.lineTo(scrollx + DisplayUtils.dip2px(mContext, 11), mTotalHeight);
            mPath.close();
            canvas.drawPath(mPath, trianglePaint);*/


            // }
        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        //   barWidth = (mTotalWidth - (2 * DisplayUtils.dip2px(mContext, 19))) / 6.0f;
        topMargin = DisplayUtils.dip2px(mContext, 30);
        mTotalHeight = h;
        defYAxisBottom = mTotalHeight - topMargin;

        defYAxisTop = topMargin;

        Logger.myLog(TAG + "barWidth:mTotalWidth," + mTotalWidth + ",defYAxisBottom:" + defYAxisBottom + ",defYAxisTop:" + defYAxisTop);
        maxHeight = h - getPaddingTop() - getPaddingBottom() - topMargin;

        if (rectBg == null) {
            rectBg = new Rect();
        }
        rectBg.left = 0;
        rectBg.top = mTopMargin;
        rectBg.right = mTotalWidth;
        rectBg.bottom = mTotalHeight;

    }

    /**
     * 绘制阴影
     *
     * @param canvas
     * @param path
     * @param pos
     */
    private void drawShadowArea(Canvas canvas, Path path, float[] pos) {
        path.lineTo(pos[0], defYAxisBottom);
        path.lineTo(defXAxis, defYAxisBottom);
        path.close();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x88CCCCCC);
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制点
     *
     * @param canvas
     * @param pos
     */
    private void drawPoint(Canvas canvas, final float[] pos) {

        mBarLeftXPoints.clear();
        mBarRightXPoints.clear();
        for (Point point : mPointList) {

            // Logger.myLog("drawPoint:" + point.x + "pos[0]:" + pos[0]);
            mBarLeftXPoints.add(point.x - DisplayUtils.dip2px(mContext, 2));
            mBarRightXPoints.add(point.x + DisplayUtils.dip2px(mContext, 2));
          /*  if (point.x > pos[0]) {
                break;
            }*/
            //Logger.myLog("drawPoint:" + point.x + "point.y:" + point.y);
            //  canvas.drawCircle(point.x, point.y, DisplayUtils.dip2px(mContext, 6), outCirclePaint);
            //  canvas.drawCircle(point.x, point.y, DisplayUtils.dip2px(mContext, 3), trianglePaint);


            //Logger.myLog(TAG + "mBarLeftXPoints:" + (point.x - DisplayUtils.dip2px(mContext, 6)) + ",mBarRightXPoints:" + (point.x + DisplayUtils.dip2px(mContext, 6)));

        }

        if (mClickPosition == mBarLeftXPoints.size() - 1) {
            centerX = (mBarLeftXPoints.get(mBarLeftXPoints.size() - 1) + mBarRightXPoints.get(mBarRightXPoints.size() - 1)) / 2;
            scrollx = centerX;
        }
    }

    private PathEffect getPathEffect(float length) {
        return new DashPathEffect(new float[]{length * drawScale, length}, 0);
    }

    private void measurePath() {
        mPath = new Path();
        mAssistPath = new Path();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;

        final int lineSize = mPointList.size();


        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                Point point = mPointList.get(valueIndex);
                currentPointX = point.x;
                currentPointY = point.y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    Point point = mPointList.get(valueIndex - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    Point point = mPointList.get(valueIndex - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                Point point = mPointList.get(valueIndex + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                mPath.moveTo(currentPointX, currentPointY);
                mAssistPath.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (lineSmoothness * firstDiffX);
                final float firstControlPointY = previousPointY + (lineSmoothness * firstDiffY);
                final float secondControlPointX = currentPointX - (lineSmoothness * secondDiffX);
                final float secondControlPointY = currentPointY - (lineSmoothness * secondDiffY);
                //画出曲线
                mPath.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
                //将控制点保存到辅助路径上
                mAssistPath.lineTo(firstControlPointX, firstControlPointY);
                mAssistPath.lineTo(secondControlPointX, secondControlPointY);
                mAssistPath.lineTo(currentPointX, currentPointY);

                //   Logger.myLog(TAG+"firstControlPointX：" + firstControlPointX + "firstControlPointY:" + firstControlPointY);
            }

            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        mPathMeasure = new PathMeasure(mPath, false);
    }
    /**
     * 点击
     */
    /**
     * 柱形图左边的x轴坐标 和右边的x轴坐标
     */
    private List<Integer> mBarLeftXPoints = new ArrayList<>();
    private List<Integer> mBarRightXPoints = new ArrayList<>();
    private float centerX = 0;
    public static final int INVALID_POSITION = -1;
    private OnItemBarClickListener mOnItemBarClickListener;

    private class RangeBarOnGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Logger.myLog(TAG + "onSingleTapUp:" + ",e.getX():" + e.getX() + ",e.getY()：" + e.getY());
            int position = identifyWhichItemClick(e.getX(), e.getY());
            if (position != INVALID_POSITION && mOnItemBarClickListener != null) {
                mOnItemBarClickListener.onClick(position);
                setClicked(position);
                invalidate();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //if (e2.getX() > leftRec && e2.getX() < rightRec) {
            int position = identifyWhichItemClick(e2.getX(), e2.getX());
            scrollx = e2.getX();
            if (position != INVALID_POSITION && mOnItemBarClickListener != null) {
                mOnItemBarClickListener.onClick(position);
                setClicked(position);
            }
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

    /**
     * 设置选中的位置
     *
     * @param position
     */
    public void setClicked(int position) {
        isDrawBorder = true;
        mClickPosition = position;
    }

    /**
     * 根据点击的手势位置识别是第几个柱图被点击
     *
     * @param x
     * @param y
     * @return -1时表示点击的是无效位置
     */
    private int identifyWhichItemClick(float x, float y) {
        float leftx = 0;
        float rightx = 0;
        if (mPointList != null && mBarLeftXPoints.size() == mPointList.size() && mBarRightXPoints.size() == mBarLeftXPoints.size()) {
            for (int i = 0; i < mPointList.size(); i++) {
                leftx = mBarLeftXPoints.get(i);
                rightx = mBarRightXPoints.get(i);
                if (x < leftx) {
                    break;
                }
                if (leftx <= x && x <= rightx) {
                    centerX = (leftx + rightx) / 2;
                    scrollx = centerX;
                    return i;
                }
            }
        }
        return INVALID_POSITION;
    }

    public interface OnItemBarClickListener {
        void onClick(int position);

    }

    public void setOnItemBarClickListener(OnItemBarClickListener onRangeBarClickListener) {
        this.mOnItemBarClickListener = onRangeBarClickListener;
    }

}
