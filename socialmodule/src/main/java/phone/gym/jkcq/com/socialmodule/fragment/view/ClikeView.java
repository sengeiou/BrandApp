package phone.gym.jkcq.com.socialmodule.fragment.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

public class ClikeView extends FrameLayout {
    private long firstClickTime;
    private long secondClickTime;
    private long stillTime;
    private boolean isUp = false;
    private boolean isDoubleClick = false;

    public ClikeView(Context context) {
        super(context);
    }

    public ClikeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private float startX, startY, endX, endY;


    boolean isMove;


    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    if (!isUp) {
                        Log.e("ACTION_DOWN", "长按 startX" + startX + "endX:" + endX + "startY:" + startY + "endY" + endY + "Math.abs(startX - endX):" + Math.abs(startX - endX) + "Math.abs(startY - endY):" + Math.abs(startY - endY));
                        if (Math.abs(startX - endX) < 10 && Math.abs(startY - endY) < 10) {
                            isMove = false;
                        }
                        if (onViewOption != null && !isMove) {
                            onViewOption.onViewLongClick();
                        }
                        //Toast.makeText(BaseApp.getApp(), "长按", Toast.LENGTH_SHORT).show();
                        firstClickTime = 0;
                        secondClickTime = 0;
                        isDoubleClick = false;
                    } else {
                        if (!isDoubleClick) {
                            if (onViewOption != null) {
                                onViewOption.onViewClick();
                            }
                            Log.e("ACTION_DOWN", "点击");
                            //  Toast.makeText(BaseApp.getApp(), "点击", Toast.LENGTH_SHORT).show();
                        }
                        isDoubleClick = false;
                        firstClickTime = 0;
                        secondClickTime = 0;
                    }
                    isUp = true;
                    break;
            }

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                endX = event.getX();
                endY = event.getY();
                Log.e("ACTION_DOWN", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_DOWN:
                /*Log.e("ACTION_DOWN", "ACTION_DOWN");
                isUp = false;
                if (firstClickTime == 0 & secondClickTime == 0) {//第一次点击
                    isMove = false;
                    startX = event.getX();
                    startY = event.getY();
                   *//* endX = event.getX();
                    endY = event.getY();*//*
                    firstClickTime = System.currentTimeMillis();
                    mHandler.sendEmptyMessageDelayed(0x01, 1000);

                } else {
                    secondClickTime = System.currentTimeMillis();
                    stillTime = secondClickTime - firstClickTime;
                    if (stillTime < 500) {//两次点击小于0.5秒
                        Log.e("ACTION_DOWN", "双击");
                        if (onViewOption != null) {
                            onViewOption.onViewDoubleCLick();
                        }
                        //  Toast.makeText(BaseApp.getApp(), "双击", Toast.LENGTH_SHORT).show();
                        isDoubleClick = true;
                        firstClickTime = 0;
                        secondClickTime = 0;
                    }
                }*/
                break;
            case MotionEvent.ACTION_UP:
                Log.e("ACTION_DOWN", "ACTION_UP");
                isUp = true;
                break;
        }

        return true;
        // return super.onTouchEvent(event);
    }


    public OnViewOption onViewOption;

    public void setonViewOptin(OnViewOption viewOption) {
        this.onViewOption = viewOption;
    }


    public interface OnViewOption {
        void onViewClick();

        void onViewLongClick();

        void onViewDoubleCLick();
    }
}
