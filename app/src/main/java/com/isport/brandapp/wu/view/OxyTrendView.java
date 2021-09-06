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

import com.isport.brandapp.wu.bean.DrawRecDataBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class OxyTrendView extends View {

    private Context mContext;
    private int mViewWidth;
    private int mViewHeight;
    private int mLeftMargin;
    private int mRightMargin;
    private int mTopMargin;
    //圆柱宽
    private int mValueWidth;
    //两个Itme的间距
    private int mItemMargin;
    //线的Y坐标
    private int mYLineAxis;
    //预留圆柱顶部高度
    private int mRoundHeight;
    private Paint mPaintValue;
    private Paint mPaintText;
    private Paint mPaintBgValue;
    private Paint mPaintLine;
    private List<Integer> strmDatas;
    private List<DrawRecDataBean> list;
    private int currentType;
    private int max = 100;
    private int clickPotion = -1;


    private GestureDetector mGestureListener;

    private List<Point> points = new ArrayList<>();

    public OxyTrendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OxyTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
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
            if (position != INVALID_POSITION && list.size() > 0) {
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
        if (points != null) {
            for (int i = 0; i < points.size(); i++) {
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

        mPaintValue = new Paint();
        mPaintValue.setAntiAlias(true);
//        mPaintValue.setStyle(Paint.Style.FILL);
        mPaintValue.setColor(Color.parseColor("#1DCE74"));
/*
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
//        mPaintValue.setStyle(Paint.Style.FILL);
        mPaintText.setColor(mContext.getResources().getColor(R.color.white));
        //  mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintText.setTextSize(DisplayUtils.dip2px(mContext, 12));*/

        mPaintBgValue = new Paint();
        mPaintBgValue.setAntiAlias(true);
//        mPaintValue.setStyle(Paint.Style.FILL);
        mPaintBgValue.setColor(Color.parseColor("#F7F7F9"));

        mPaintLine = new Paint();
        mPaintLine.setAntiAlias(true);
        mPaintLine.setColor(Color.parseColor("#2F2F33"));
        mPaintLine.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintLine.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(DisplayUtils.dip2px(mContext, 12));
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintText.setStrokeWidth(DisplayUtils.dip2px(mContext, 1));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mViewWidth = getWidth();
        mViewHeight = getHeight();

        mLeftMargin = DisplayUtils.dip2px(mContext, 15);
        mRightMargin = mLeftMargin;
        mValueWidth = DisplayUtils.dip2px(mContext, 22);
        mItemMargin = (mViewWidth - 7 * mValueWidth - mLeftMargin - mRightMargin) / 6;
        mRoundHeight = mValueWidth / 2;
        mTopMargin = DisplayUtils.dip2px(mContext, 30);
        mYLineAxis = mViewHeight - DisplayUtils.dip2px(mContext, 20);
        Log.e("test", "mViewWidth=" + mViewWidth + " mViewHeight=" + mViewHeight + " mYLineAxis=" + mYLineAxis
                + " mLeftMargin=" + mLeftMargin + " mValueWidth=" + mValueWidth + " mItemMargin=" + mItemMargin);
    }

    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        if (list == null || list.size() == 0) {
            return;
        }
        points.clear();
        //canvas.drawLine(0, mYLineAxis, mViewWidth, mYLineAxis, mPaintLine);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            // Integer value = list.get(size - 1 - i).getValue();
            Integer value = list.get(i).getValue();
            //画圆柱
            int left = i * (mValueWidth + mItemMargin) + mLeftMargin;
            int right = left + mValueWidth;
            int bottom = mYLineAxis;
            int top = mYLineAxis - (mYLineAxis - mTopMargin) * value / max + mRoundHeight;
            int topMax = mYLineAxis - (mYLineAxis - mTopMargin) * max / max + mRoundHeight;
            int radius = mValueWidth / 2;
            points.add(new Point(left, right));
            rectF.set(left, DisplayUtils.dip2px(mContext, 30), right, bottom);
            canvas.drawRoundRect(rectF, (float) radius, (float) radius, mPaintBgValue);
            try {
                mPaintValue.setColor(list.get(i).getColors());
            } catch (Exception e) {

                Log.e("onDraw excepiton", "" + list.get(i) + "-------" + clickPotion + "clickPotion == i" + (clickPotion == i));
            }
            if (clickPotion != -1 && clickPotion == i) {
                drawTringle(canvas, rectF.centerX(), rectF.top);
                drawTimeText(canvas, rectF.centerX(), rectF.top - DisplayUtils.dip2px(mContext, 15), list.get(i).getStrdate());
            }
            /**
             * 计算出颜色
             */
            Log.e("onDraw", "" + list.get(i) + "-------" + clickPotion);
            rectF.set(left, top, right, bottom);
            canvas.drawRoundRect(rectF, (float) radius, (float) radius, mPaintValue);
            //canvas.drawCircle(left + radius, top, radius, mPaintValue);

            //画值


            String textValue = "";
            if (currentType == JkConfiguration.BODY_ONCE_HR) {
                textValue = "" + value;
            } else {
                textValue = "" + value + "%";
            }
            int textWidth = (int) mPaintLine.measureText(textValue);
            int textCenter = right - mValueWidth / 2;
            Log.e("test", "value=" + value);
            canvas.drawText(textValue, textCenter - textWidth / 2, mViewHeight, mPaintLine);
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
        canvas.drawRoundRect(recTime, (recTime.right - recTime.left) / 2, (recTime.right - recTime.left) / 2, mPaintValue);
        canvas.drawText(text, left + DisplayUtils.dip2px(mContext, 5), y, mPaintText);
    }

    public void setDeviceType(int currentType) {
        this.currentType = currentType;
    }

    public void setdata(List<DrawRecDataBean> beans, int currentType) {
        this.currentType = currentType;
        this.list = new ArrayList<>();
        this.list.addAll(beans);
        Collections.reverse(this.list);
        strmDatas = new ArrayList<>();
        for (int i = 0; i < this.list.size(); i++) {
            strmDatas.add(list.get(i).getValue());
        }
        calMax(currentType);
        invalidate();
    }


    public void calMax(int type) {
        if (currentType == JkConfiguration.BODY_ONCE_HR) {
            if (strmDatas.size() > 0) {
                max = Collections.max(strmDatas) + 10;
            } else {
                max = 240;
            }
        } else {
            max = 100;
        }
    }


    public void setLocalData(DrawRecDataBean bean) {

        if (strmDatas == null) {
            strmDatas = new ArrayList<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.size() == 7) {
            strmDatas.remove(0);
            list.remove(0);

        }
        strmDatas.add(0);
        list.add(new DrawRecDataBean());

        strmDatas.add(strmDatas.size() - 1, bean.getValue());
        list.add(list.size() - 1, bean);

        strmDatas.remove(strmDatas.size() - 1);
        list.remove(list.size() - 1);


        calMax(currentType);
        invalidate();
    }
}
