package brandapp.isport.com.basicres.commonutil;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;

import java.util.Locale;

public class AppUtil {
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.contains("zh"))
            return true;
        else
            return false;
    }

    static BluetoothAdapter bleAdapter = null;

    public static boolean isOpenBle() {
        if (bleAdapter == null) {
            bleAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (bleAdapter == null || !bleAdapter.isEnabled()) {
            return false;
        } else {
            return true;
        }
    }


    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

}
