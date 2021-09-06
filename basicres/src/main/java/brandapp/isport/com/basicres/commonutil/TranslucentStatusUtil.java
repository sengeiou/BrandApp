package brandapp.isport.com.basicres.commonutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TranslucentStatusUtil {

    private static final String TAG = TranslucentStatusUtil.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentStatus(Activity activity, boolean isOn) {
        if (null == activity || activity.isDestroyed()) {
            return;
        }
        Window win = activity.getWindow();
        if (null == win) {
            return;
        }
        WindowManager.LayoutParams winParams = win.getAttributes();

        // win.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        // WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (isOn) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 只支持MIUI V6
     *
     * @param context
     * @param type    0--只需要状态栏透明 1-状态栏透明且黑色字体 2-清除黑色字体
     */
    public static void setStatusBarTextColor(Activity context, int type) {
        if (!isMiUIV6()) {
            Logger.d("isMiUIV6:" + false);
            return;
        }
        Logger.d("isMiUIV6:" + true);
        Window window = context.getWindow();
        Class clazz = window.getClass();
        try {
            int tranceFlag = 0;
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);
            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (type == 0) {
                extraFlagField.invoke(window, tranceFlag, tranceFlag);//只需要状态栏透明
            } else if (type == 1) {
                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

    private static boolean isMiUIV6() {
        String name = getSystemProperty(KEY_MIUI_VERSION_NAME);
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        if ("V6".equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Logger.e("Unable to read sysprop " + propName);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Logger.e("Exception while closing InputStream");
                }
            }
        }
        return line;
    }

    /**
     * 设置系统状态栏Dark模式
     *
     * @param activity
     * @param darkMode true Dark模式
     * @return
     */
    public static boolean setStatusBarDarkMode(Activity activity, boolean darkMode) {
        if (SystemRomHelper.isMIUI()) {
            return setMiuiStatusBarDarkMode(activity, darkMode);
        } else if (SystemRomHelper.isFlyme()) {
            return setMeizuStatusBarDarkIcon(activity, darkMode);
        }
        return false;
    }

    private static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkMode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean darkMode) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (darkMode) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
}
