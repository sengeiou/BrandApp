package com.isport.brandapp.login;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class TouchEventWebView extends WebView {
    public TouchEventWebView(Context context) {
        super(context);
    }

    public TouchEventWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchEventWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* public TouchEventWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
       // super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    private float mStartX;
    private float mStartY;

    private float mCurrentX;
    private float mCurrentY;

    private boolean isHorizontal = false;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
//                if (isHorizontal) {
//                    return true;
//                }
                mCurrentX = event.getRawX();
                mCurrentY = event.getRawY();
//                LogTest.test("x=" + mCurrentX + " y=" + mCurrentY);
                if (Math.abs(mCurrentX - mStartX) > Math.abs(mCurrentY - mStartY)) {
//                    isHorizontal = true;
                    return super.dispatchTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                isHorizontal = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

}