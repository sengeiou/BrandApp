package com.isport.brandapp.wu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.wu.bean.BPInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


public class BPTrendView extends View {

    private Context mContext;
    private int mViewWidth;
    private int mViewHeight;
    //高低压两个圆柱的间距
    private int mValueMargin;
    //圆柱宽
    private int mValueWidth;
    //两个Itme的间距
    private int mItemMargin;
    //线的Y坐标
    private int mYLineAxis;
    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    //预留圆柱顶部高度
    private int mRoundHeight;
    private Paint mPaintHigh, mPaintText, mPaintValue;
    private Paint mPaintLow;
    private Paint mPaintLine;
    private List<BPInfo> mDatas;


    private int clickPotion = -1;


    private GestureDetector mGestureListener;

    private List<Point> points = new ArrayList<>();


    public BPTrendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BPTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }


    /**
     * 点击
     */
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
            int position = identifyWhichItemClick(e.getX(), e.getY());

            //如果是清醒状态是不能点
            if (position != INVALID_POSITION && mDatas.size() > 0) {
                clickPotion = position;
                invalidate();

            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
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

    /* 用户点击到了无效位置 */
    public static final int INVALID_POSITION = -1;
    /**
     * 根据点击的手势位置识别是第几个柱图被点击
     *
     * @param x
     * @param y
     * @return -1时表示点击的是无效位置
     */
    private float centerX = 0;

    private int identifyWhichItemClick(float x, float y) {
        float leftx = 0;
        float rightx = 0;
        if (mDatas != null) {
            for (int i = 0; i < mDatas.size(); i++) {
                leftx = points.get(i).x;
                rightx = points.get(i).y;
                if (x < leftx) {
                    break;
                }
                if (leftx <= x && x <= rightx) {
                    centerX = (leftx + rightx) / 2;
                    return i;
                }
            }
        }

        return INVALID_POSITION;
    }

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
////                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
////                invalidate();
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


    private void init() {
        mGestureListener = new GestureDetector(mContext, new RangeBarOnGestureListener());
        mPaintHigh = new Paint();
        mPaintHigh.setAntiAlias(true);
        mPaintHigh.setColor(Color.parseColor("#FD3C30"));
        mPaintHigh.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintLow = new Paint();
        mPaintLow.setAntiAlias(true);
        mPaintLow.setColor(Color.parseColor("#127AFF"));
        mPaintLow.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setColor(Color.parseColor("#E7E8EB"));
        mPaintLine.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintText.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));
        mPaintValue = new Paint();
        mPaintValue.setAntiAlias(true);
        mPaintValue.setColor(Color.parseColor("#4DDA64"));
        mPaintValue.setStyle(Paint.Style.FILL);
        mPaintValue.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintValue.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintValue.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewWidth = getWidth();
        mViewHeight = getHeight();
        mLeftMargin = DisplayUtils.dip2px(mContext, 15);
        mRightMargin = mLeftMargin;
        mValueWidth = DisplayUtils.dip2px(mContext, 10);
        mTopMargin = DisplayUtils.dip2px(mContext, 30);
        mRoundHeight = mValueWidth / 2;
        mValueMargin = DisplayUtils.dip2px(mContext, 2);
        mItemMargin = (mViewWidth - 2 * 7 * mValueWidth - 7 * mValueMargin - mLeftMargin - mRightMargin) / 6;

        mYLineAxis = mViewHeight - DisplayUtils.dip2px(mContext, 40);
        Log.e("test", "mViewWidth=" + mViewWidth + " mViewHeight=" + mViewHeight + " mYLineAxis=" + mYLineAxis
                + " mLeftMargin=" + mLeftMargin + " mValueWidth=" + mValueWidth + " mItemMargin=" + mItemMargin);
    }

    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDatas == null || mDatas.size() == 0) {
            return;
        }
        points.clear();
        //canvas.drawLine(0, mYLineAxis, mViewWidth, mYLineAxis, mPaintLine);

        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            BPInfo info = mDatas.get(i);
            //画高压圆柱
            int left = i * (mValueWidth * 2 + mValueMargin + mItemMargin) + mLeftMargin;
            int right = left + mValueWidth;
            int bottom = mYLineAxis;


            int top = mYLineAxis - (mYLineAxis - mTopMargin) * info.getSpValue() / 200 + mRoundHeight;

            int radius = mValueWidth / 2;


            rectF.set(left, DisplayUtils.dip2px(mContext, 30), right, bottom);
            canvas.drawRoundRect(rectF, (float) radius, (float) radius, mPaintLine);
            Point point = new Point();

            point.x = left;

            rectF.set(left, top, right, bottom);
            canvas.drawRoundRect(rectF, radius, radius, mPaintHigh);


            //canvas.drawCircle(left + radius, top, radius, mPaintHigh);
            //画低压圆柱
            int leftLow = left + mValueWidth + mValueMargin;
            int rightLow = leftLow + mValueWidth;
            int bottomLow = mYLineAxis;
            int topLow = mYLineAxis - (mYLineAxis - mTopMargin) * info.getDpValue() / 200 + mRoundHeight;

            rectF.set(leftLow, DisplayUtils.dip2px(mContext, 30), rightLow, bottom);
            canvas.drawRoundRect(rectF, (float) radius, (float) radius, mPaintLine);
            if (clickPotion != -1 && clickPotion == i) {
                drawTringle(canvas, rectF.left, rectF.top);
                drawTimeText(canvas, rectF.left, rectF.top - DisplayUtils.dip2px(mContext, 15), TimeUtils.getTimeByyyyyMMddhhmmss(info.getTimestamp()));
            }
            rectF.set(leftLow, topLow, rightLow, bottom);
            canvas.drawRoundRect(rectF, radius, radius, mPaintLow);
            point.y = rightLow;

            points.add(point);
            //canvas.drawCircle(leftLow + radius, topLow, radius, mPaintLow);

            //画高压值
            int textSize = (int) mPaintHigh.measureText("" + info.getSpValue());
            int textCenter = rightLow - (rightLow - left) / 2;
            canvas.drawText("" + info.getSpValue(), textCenter - textSize / 2, mYLineAxis + DisplayUtils.dip2px(mContext, 20), mPaintHigh);
            //画低压值
            int textSizeLow = (int) mPaintLow.measureText("" + info.getDpValue());
            canvas.drawText("" + info.getDpValue(), textCenter - textSizeLow / 2, mYLineAxis + DisplayUtils.dip2px(mContext, 40), mPaintLow);
        }
    }

    private void drawTringle(Canvas canvas, float centerX, float topy) {

        int len = DisplayUtils.dip2px(mContext, 8);
        int left = (int) (centerX - len);
        int right = (int) (centerX + len);
        Path path2 = new Path();
        path2.moveTo(left, topy - DisplayUtils.dip2px(mContext, 5));
        path2.lineTo(right, topy - DisplayUtils.dip2px(mContext, 5));
        path2.lineTo(centerX, topy);
        path2.close();
        canvas.drawPath(path2, mPaintValue);

    }

    RectF recTime = new RectF();

    private void drawTimeText(Canvas canvas, float x, float y, String text) {


        //计算矩形显示的位置
        //画一个矩形
        Log.e("drawTimeText", "text=" + text);
        // text = "2020-09-09";
        float len = mPaintText.measureText(text);

        //    canvas.draw
        float left = x - ((len / 2) + DisplayUtils.dip2px(mContext, 10));
        float right = x + ((len / 2));
        if ((x - len / 2) < 0) {
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


    /**
     * 设置数据
     */
    public void setData(List<BPInfo> data) {
        this.mDatas = new ArrayList<>();
        mDatas.addAll(data);
        Collections.reverse(mDatas);
        invalidate();
    }

    public void setLocalData(BPInfo info) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        if (mDatas.size() == 7) {
            mDatas.remove(0);
        }
        mDatas.add(new BPInfo());
        mDatas.add(mDatas.size() - 1, info);
        mDatas.remove(mDatas.size() - 1);
        invalidate();
    }
}
