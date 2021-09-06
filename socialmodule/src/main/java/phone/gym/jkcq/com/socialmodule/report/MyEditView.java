package phone.gym.jkcq.com.socialmodule.report;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

public class MyEditView extends androidx.appcompat.widget.AppCompatEditText {
    public MyEditView(Context context) {
        super(context);
    }

    public MyEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.i("main_activity", "键盘向下 "+keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1) {
            Log.i("main_activity", "键盘向下 ");
            super.onKeyPreIme(keyCode, event);
            return false;
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
