package brandapp.isport.com.basicres.commonutil;

import android.view.View;

/*
 * 防止短时间内多次重复点击
 *
 * @author mhj
 * Create at 2017/11/14 11:01
 */
public class ViewMultiClickUtil {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long LAST_CLICK_TIME;
    private static long LAST_CLICK_TIME_NOVIEW;

    private static int id = View.NO_ID;

    /**
     * @param v 当前控件
     * @return true 重复点击 过滤
     */
    public static boolean onMultiClick(View v) {
        if (null == v || v.getId() == View.NO_ID) return false;
        if (id != v.getId()) {
            id = v.getId();
            LAST_CLICK_TIME = System.currentTimeMillis();
            return false;
        }

        long curClickTime = System.currentTimeMillis();
        long result = curClickTime - LAST_CLICK_TIME;
        Logger.e("返回结果：", (result) + "");
        if ((curClickTime - LAST_CLICK_TIME) < MIN_CLICK_DELAY_TIME) {
            Logger.e("返回结果：", "成立");
            LAST_CLICK_TIME = curClickTime;
            return true;
        } else {
            LAST_CLICK_TIME = curClickTime;
            return false;
        }


    }

    //点之前清除下时间  xz
    public static void clearTimeNoView() {
        LAST_CLICK_TIME_NOVIEW = 0;
    }

    public static boolean onMultiClick() {

        long curClickTime = System.currentTimeMillis();
        long result = curClickTime - LAST_CLICK_TIME_NOVIEW;
        Logger.e("返回结果：", (result) + "");
        if ((curClickTime - LAST_CLICK_TIME_NOVIEW) < MIN_CLICK_DELAY_TIME) {
            Logger.e("返回结果：", "成立");
            LAST_CLICK_TIME_NOVIEW = curClickTime;
            return true;
        } else {
            LAST_CLICK_TIME_NOVIEW = curClickTime;
            return false;
        }


    }


}
