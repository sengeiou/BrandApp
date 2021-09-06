package phone.gym.jkcq.com.socialmodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * created by wq on 2019/6/4
 */
public class MoveControlView extends RelativeLayout {
    public MoveControlView(@NonNull Context context) {
        super(context);
    }

    public MoveControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                Log.e("TouchInterceptViewGroup","mCurrentX="+ event.getRawX()+"  mCurrentY="+event.getRawY());
                if (isHorizontal) {
                    return true;
                }
                mCurrentX = event.getRawX();
                mCurrentY = event.getRawY();

                if (Math.abs(mCurrentX - mStartX) < (Math.abs(mCurrentY - mStartY)+10)) {
                    isHorizontal = true;
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                isHorizontal = false;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }
}
