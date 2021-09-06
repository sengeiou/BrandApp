package phone.gym.jkcq.com.socialmodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * created by wq on 2019/6/4
 */
public class MyNestedScrollView extends NestedScrollView {
    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private float mStartX;
    private float mStartY;

    private float mCurrentX;
    private float mCurrentY;

    private boolean isHorizontal = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("TouchInterceptViewGroup","mCurrentX="+
//                        event.getX()+"  mCurrentY="+event.getY());
                if (isHorizontal) {
                    return false;
                }
                mCurrentX = event.getRawX();
                mCurrentY = event.getRawY();
//                LogTest.test("x=" + mCurrentX + " y=" + mCurrentY);
                if (Math.abs(mCurrentX - mStartX) > (Math.abs(mCurrentY - mStartY)+10)) {
                    isHorizontal = true;
                    return false;
                }
//                if (Math.abs(mStartY - event.getY()) >= ViewConfiguration.get(
//                        getContext()).getScaledTouchSlop()) {
//                    return true;
//                }

                break;
            case MotionEvent.ACTION_UP:
                isHorizontal = false;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }
}
