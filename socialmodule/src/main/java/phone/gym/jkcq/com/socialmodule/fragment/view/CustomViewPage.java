package phone.gym.jkcq.com.socialmodule.fragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPage extends ViewPager {
    public CustomViewPage(@NonNull Context context) {
        super(context);
    }

    public CustomViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private int startX, startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
       /* switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
        startX = (int) ev.getX();
        startY = (int) ev.getY();
        getParent().requestDisallowInterceptTouchEvent(true);
        break;
        case MotionEvent.ACTION_MOVE:
        int endX = (int) ev.getX();
        int endY = (int) ev.getY();
        int disX = Math.abs(endX - startX);
        int disY = Math.abs(endY - startY);
        if(disX > disY){
            getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(startX -endX));
        }else {
            getParent().requestDisallowInterceptTouchEvent(canScrollVertically(startY -endY));
        }
        break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
        getParent().requestDisallowInterceptTouchEvent(false);
        break;
    }*/
        return super.dispatchTouchEvent(ev);
    }
}
