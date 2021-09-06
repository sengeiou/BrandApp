package com.isport.brandapp.wu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.isport.blelibrary.utils.TimeUtils;
import com.isport.brandapp.wu.bean.TempInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class TempTrendView extends View {

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
    private Paint mPaintLine, mPaintText;
    // private List<OxyInfo> mDatas;
    private List<TempInfo> strmDatas;
    private int currentType;
    private float max = 100;
    private int clickPotion = -1;
    private GestureDetector mGestureListener;
    private List<Point> points = new ArrayList<>();


    private String tempUnitl = "0";

    public TempTrendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TempTrendView(Context context, AttributeSet attrs, int defStyleAttr) {
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
            if (position != INVALID_POSITION && strmDatas.size() > 0) {
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
        if (strmDatas != null) {
            for (int i = 0; i < strmDatas.size(); i++) {
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

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.parseColor("#E7E8EB"));
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

    RectF re = new RectF();
    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        if (strmDatas == null || strmDatas.size() == 0) {
            return;
        }


        try {
            //canvas.drawLine(0, mYLineAxis, mViewWidth, mYLineAxis, mPaintLine);
            int size = strmDatas.size();
            TempInfo info;
            float currentC = 0.f;
            String strCurerntC;
            points.clear();
            for (int i = 0; i < size; i++) {
                info = strmDatas.get(i);
                strCurerntC = info.getCentigrade();
                if (TextUtils.isEmpty(strCurerntC)) {
                    strCurerntC = "0";
                }
                currentC = Float.parseFloat(strCurerntC);
                //画圆柱
                int left = i * (mValueWidth + mItemMargin) + mLeftMargin;
                int right = left + mValueWidth;
                int bottom = mYLineAxis;
                int top = (int) (mYLineAxis - (mYLineAxis - mTopMargin) * (currentC / max) + mRoundHeight);
                mPaintValue.setColor(info.getColor());

                points.add(new Point(left, right));

                int radius = mValueWidth / 2;
                re.set(left, DisplayUtils.dip2px(mContext, 30), right, bottom);
                canvas.drawRoundRect(re, (float) radius, (float) radius, mPaintLine);
                if (clickPotion != -1 && clickPotion == i) {
                    drawTringle(canvas, re.centerX(), re.top);
                    drawTimeText(canvas, re.centerX(), re.top - DisplayUtils.dip2px(mContext, 15), TimeUtils.getTimeByyyyyMMddhhmmss(info.getTimestamp()));
                }
                re.set(left, top, right, bottom);
                canvas.drawRoundRect(re, radius, radius, mPaintValue);


                //
                // canvas.drawCircle(left + radius, top, radius, mPaintValue);

                //画值
                mPaintValue.setTextSize(DisplayUtils.dip2px(mContext, 12));
                String textValue = "";
                if (tempUnitl.equals("0")) {
                    textValue = info.getCentigrade();
                } else {
                    textValue = info.getFahrenheit();
                }
                if (TextUtils.isEmpty(textValue)) {
                    textValue = "0";
                }
                int textWidth = (int) mPaintValue.measureText(textValue);
                int textCenter = right - mValueWidth / 2;
                mPaintValue.setColor(Color.parseColor("#2F2F33"));
                canvas.drawText(textValue, textCenter - textWidth / 2, mViewHeight, mPaintValue);
            }
        } catch (Exception e) {


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

    public void setCurrentTempUnitl(String tempUnitl) {
        this.tempUnitl = tempUnitl;
    }

    public void setData(List<TempInfo> data, String tempUnitl) {
        this.strmDatas = new ArrayList<>();
        this.strmDatas.addAll(data);
        Collections.reverse(strmDatas);
        this.tempUnitl = tempUnitl;
        calMax();
        invalidate();
    }

    public void calMax() {
        max = 0;
        String strCurrentC = "";
        float currentC = 0;
        for (int i = 0; i < strmDatas.size(); i++) {
            strCurrentC = strmDatas.get(i).getCentigrade();
            if (TextUtils.isEmpty(strCurrentC)) {
                strCurrentC = "0";
            }
            currentC = Float.parseFloat(strCurrentC);
            if (currentC > max) {
                max = currentC;
            }
        }
        max += 10;
    }

    public void setLocalData(TempInfo info, String tempUnitl) {
        this.tempUnitl = tempUnitl;
        if (strmDatas == null) {
            strmDatas = new ArrayList<>();
        }
        if (strmDatas.size() == 7) {
            strmDatas.remove(0);
        }
        strmDatas.add(new TempInfo());
        strmDatas.add(strmDatas.size() - 1, info);
        strmDatas.remove(strmDatas.size() - 1);
        calMax();
        invalidate();
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

}
