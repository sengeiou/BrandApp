package phone.gym.jkcq.com.commonres.commonutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DisplayUtils {

    private static final String STATUSBAR_CLASS_NAME = "com.android.internal.R$dimen";
    private static final String STATUSBAR_FIELD_HEIGHT = "status_bar_height";

    /**
     * Convert the given dip to px.
     * See also {@link #px2dip(Context, float)}
     *
     * @param context The context of Android operation system (OS).
     * @param dpValue The dip to be converted
     * @return The px value
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Convert the given px to dip
     * See also {@link #dip2px(Context, float)}
     *
     * @param context The context of Android operation system (OS).
     * @param pxValue The px to be converted
     * @return The dip value.
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Get the width and height of the screen in orientation portrait (width < height).
     * See also {@link #getScreenHeight(Context)}, {@link #getScreenWidth(Context)}.
     *
     * @param context    The context of Android operation system (OS).
     * @param outMetrics An int array: metrics[0]: the width of the screen
     *                   metrics[1]: the height of the screen
     */

    public static void getScreenSizeMetrics(Context context, int outMetrics[]) {
        if (null == outMetrics) {
            outMetrics = new int[2];
        }

        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        if (screenHeight < screenWidth) {
            int temp = screenWidth;
            screenWidth = screenHeight;
            screenHeight = temp;
        }

        outMetrics[0] = screenWidth;
        outMetrics[1] = screenHeight;
    }

    /**
     * Get the width of the screen.
     * See also {@link #getDisplayMetrics(Context)}, {@link #getScreenHeight(Context)}.
     *
     * @param context The context of Android operation system (OS).
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /**
     * Get the height of the screen.
     * See also {@link #getDisplayMetrics(Context)}, {@link #getScreenWidth(Context)}
     *
     * @param context The context of Android operation system (OS).
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /**
     * Get the height of status bar
     *
     * @param context The context of Android operation system (OS).
     * @return The height of status bar.
     * <br>0, if failed.
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int sbar = 0;
        try {
            c = Class.forName(STATUSBAR_CLASS_NAME);
            obj = c.newInstance();
            field = c.getField(STATUSBAR_FIELD_HEIGHT);
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 字体高度
     *
     * @param fontSize
     * @return
     */
    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public static int getVirtualBarHeigh(Activity activity) {
        int titleHeight = 0;
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusHeight = frame.top;
        titleHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop() - statusHeight;
        return titleHeight;
    }
}
