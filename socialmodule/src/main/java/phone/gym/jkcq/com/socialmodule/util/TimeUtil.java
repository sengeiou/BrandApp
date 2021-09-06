package phone.gym.jkcq.com.socialmodule.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.socialmodule.R;

public class TimeUtil {


    //
    public static String getDynmicTime(Long sendTime, String strTime) {
        //  String str = "";

        Long currentTime = System.currentTimeMillis();
        Long subSe = currentTime / 1000 - sendTime / 1000;
        if (subSe < 0) {
            return dataToString(new Date(sendTime), "yyyy-MM-dd HH:mm:ss");
        }
        if (subSe < 60) {
            return UIUtils.getString(R.string.dynamic_sen);
        } else if (subSe < 60 * 60) {
            return String.format(UIUtils.getString(R.string.dynamic_min), subSe / 60);
        } else if (subSe < 60 * 60 * 24) {
            return String.format(UIUtils.getString(R.string.dynamic_hour), subSe / 60 / 60);
        } else if (subSe < 60 * 60 * 24 * 7) {
            return String.format(UIUtils.getString(R.string.dynamic_day), subSe / 60 / 60 / 24);
        } else {
            // if (TextUtils.isEmpty(strTime)) {
            return dataToString(new Date(sendTime), "yyyy-MM-dd HH:mm:ss");
           /* } else {
                return strTime;
            }*/
        }


    }

    public static String dataToString(Date date, String format) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(format);
            return format1.format(date);
        } catch (Exception e) {
            return System.currentTimeMillis() + "";
        }

    }

}
