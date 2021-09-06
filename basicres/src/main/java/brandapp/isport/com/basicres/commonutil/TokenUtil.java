package brandapp.isport.com.basicres.commonutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Calendar;

import phone.gym.jkcq.com.commonres.common.AllocationApi;


/*
 * 保存Token信息
 *
 * @author mhj
 * Create at 2017/11/1 14:38
 */
public class TokenUtil {

    private static final String TAG = TokenUtil.class.getSimpleName();

    private static final String TAG_TOKEN = "token";
    private static final String APP_First = "app_first";
    private static final String APP_First_COMMUNITY = "app_first_community";
    private static final String APP_First_IsRegidit = "app_first_IsRegidit";
    private static final String TAG_PEOPLE_ID = AllocationApi.BaseUrl + "peopleId";
    private static final String TAG_SPORT_TYPE = AllocationApi.BaseUrl + "sporttype";
    private static final String TAG_PHONE_NUM = "phoneNumber";
    private static final String TAG_SLEEP_TIME = AllocationApi.BaseUrl + "sleepTime";
    private static final String TAG_HEAD_URL = "loadpicurl";

    public static final String ROPE_DETAIL_FIRST = "rope_detail_first";
    public static final String DEVICE_SYNC_FIRST = "device_sync_first";
    public static final String DEVICE_DETAIL_FIRST = "device_Detail_first";
    public static final String SPORT_DETAIL_FIRST = "sport_Detail_first";

    private static TokenUtil instance;

    public synchronized static TokenUtil getInstance() {
        if (null == instance) {
            synchronized (TokenUtil.class) {
                instance = new TokenUtil();
            }
        }
        return instance;
    }

    /**
     * 更新保存的Token
     *
     * @param context
     * @param token
     * @return
     */
    public void updateToken(Context context, String token) {
        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.commit();
    }

    public void updateIsRegidit(Context context, String isRegidit) {
        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(APP_First_IsRegidit, isRegidit);
        editor.commit();
    }

    public boolean getAppFirstUseCommunity(Context context) {
        if (null == context) {
            return false;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);

        String value = preferences.getString(APP_First_COMMUNITY, "");
        if (TextUtils.isEmpty(value)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getAppFirst(Context context) {
        if (null == context) {
            return false;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);

        String value = preferences.getString(APP_First, "");
        if (TextUtils.isEmpty(value)) {
            return false;
        } else {
            return true;
        }
    }


    public void updateAPPfirst(Context context, String token) {
        if (null == context) {
            return;
        }

        //Logger.myLog("getAppFirst：updateAPPfirst" + context + "token:" + token);
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(APP_First, token);
        editor.commit();
    }

    public boolean getKeyValue(Context context, String key) {
        if (null == context) {
            return false;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);

        String value = preferences.getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return false;
        } else {
            return true;
        }
    }

    public void updateKeyValue(Context context, String value, String key) {

        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean getIsRegidit(Context context) {
        if (null == context) {
            return false;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);

        String value = preferences.getString(APP_First_IsRegidit, "");
        if (TextUtils.isEmpty(value)) {
            return false;
        } else {
            return true;
        }
    }

    public void updateAPPfirstCommunity(Context context, String token) {
        if (null == context) {
            return;
        }

        //Logger.myLog("getAppFirst：updateAPPfirst" + context + "token:" + token);
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(APP_First_COMMUNITY, token);
        editor.commit();
    }

    /**
     * 获取Token
     *
     * @param context
     * @return
     */
    public String getToken(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);

        return preferences.getString(TAG_TOKEN, "");
    }


    public String getHeadURl(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);

        return preferences.getString(TAG_HEAD_URL, "");
    }


    public void updatePepoleHeadUrl(Context context, String url) {
        if (null == context) {
            return;
        }
        //JPushUtils.getInstance().setAlias(context, peopleId);
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TAG_HEAD_URL, url);
        editor.commit();
    }

    public void updatePeopleId(Context context, String peopleId) {
        if (null == context) {
            return;
        }
        //JPushUtils.getInstance().setAlias(context, peopleId);
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TAG_PEOPLE_ID, peopleId);
        editor.commit();
    }

    public void savePeopleId(Context context, String personId) {
        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TAG_PEOPLE_ID, personId);
        editor.commit();
    }

    public void savePhone(Context context, String phoneNum) {
        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AllocationApi.BaseUrl + TAG_PHONE_NUM, phoneNum);
        editor.commit();
    }

    public long getSleepTime(Context context) {
        if (null == context) {
            return 0;
        }


        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        return preferences.getLong(TAG_SLEEP_TIME, 0);
    }

    public void saveSleepTime(Context context, String time) {
        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        if (TextUtils.isEmpty(time)) {
            editor.putLong(TAG_SLEEP_TIME, 0);
            editor.commit();
            return;
        }
        String[] split = time.split(":");
        int hourOfDay = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH), hourOfDay, minute, 0);

        editor.putLong(TAG_SLEEP_TIME, calendar.getTimeInMillis());
        editor.commit();
    }

    public String getPeopleIdStr(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        return preferences.getString(TAG_PEOPLE_ID, "0");
    }

    public String getPeopleIdInt(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        return preferences.getString(TAG_PEOPLE_ID, "0");
    }

    public int getCurrentSportType(Context context) {
        if (null == context) {
            return -1;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        return preferences.getInt(TAG_SPORT_TYPE, 0);
    }

    public void saveCurrentSportType(Context context, int sportType) {
        if (null == context) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TAG_SPORT_TYPE, sportType);
        editor.commit();
    }

    public String getPhone(Context context) {
        if (null == context) {
            return "";
        }
        SharedPreferences preferences = context.getSharedPreferences(TAG,
                0);
        return preferences.getString(TAG_PHONE_NUM, "");
    }

    public void clear(Context context) {
        if (null == context) {
            return;
        }
        //JPushUtils.getInstance().setAlias(context, "");
        SharedPreferences preferences = context.getSharedPreferences(TAG, 0);
        boolean isFirst = getAppFirst(context);
        boolean isFirstCommnitiy = getAppFirstUseCommunity(context);
        // preferences.edit().clear().commit();
        preferences.edit().remove(APP_First_IsRegidit).commit();
        preferences.edit().remove(TAG_TOKEN).commit();
        preferences.edit().remove(TAG_PEOPLE_ID).commit();
        preferences.edit().remove(TAG_PHONE_NUM).commit();
        preferences.edit().remove(TAG_SLEEP_TIME).commit();
        updateAPPfirst(context, isFirst + "");
        updateAPPfirstCommunity(context, isFirstCommnitiy + "");


    }
}
