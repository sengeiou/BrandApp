package brandapp.isport.com.basicres.commonutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import brandapp.isport.com.basicres.BaseApp;


public class SharedPreferencesUtil {
    /**
     * 保存到sp中即为xml
     *
     * @param keyName
     * @param keyValue
     */
    public static SharedPreferences sharedPreferences;

    public static void saveToSharedPreferences(String keyName,
                                               String keyValue) {
        // if (sharedPreferences == null) {
        sharedPreferences = BaseApp.getApp().getSharedPreferences(keyName,
                                                                  Context.MODE_PRIVATE);
        // }
        Editor editor = sharedPreferences.edit();
        editor.putString(keyName, keyValue);
        editor.commit();
    }
    public static void saveToSharedPreferences(String keyName,
                                               boolean keyValue) {
        // if (sharedPreferences == null) {
        sharedPreferences = BaseApp.getApp().getSharedPreferences(keyName,
                Context.MODE_PRIVATE);
        // }
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyName, keyValue);
        editor.commit();
    }

    /**
     * 取出记录的数值
     *
     * @param keyName
     * @return
     */
    public static String getSharedPreferences(String keyName) {
        String dataStr = "";
        // if (sharedPreferences == null) {
        sharedPreferences = BaseApp.getApp().getSharedPreferences(keyName,
                Context.MODE_PRIVATE);
        // }
        dataStr = sharedPreferences.getString(keyName, dataStr);
        return dataStr;
    }
    public static boolean getSharedPreferences(String keyName,boolean isBoolean) {
        boolean dataStr = false;
        // if (sharedPreferences == null) {
        sharedPreferences = BaseApp.getApp().getSharedPreferences(keyName,
                Context.MODE_PRIVATE);
        // }
        dataStr = sharedPreferences.getBoolean(keyName, false);
        return dataStr;
    }

    /**
     * 刪除数据
     */
    public static void deleteSharedPreferences(String keyName) {
        sharedPreferences = BaseApp.getApp().getSharedPreferences(keyName, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}
