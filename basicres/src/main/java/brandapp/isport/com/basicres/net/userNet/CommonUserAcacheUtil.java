package brandapp.isport.com.basicres.net.userNet;

import android.text.TextUtils;

import com.google.gson.Gson;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonbean.UserInfoBean;
import brandapp.isport.com.basicres.commonutil.ACache;
import phone.gym.jkcq.com.commonres.common.AllocationApi;

public class CommonUserAcacheUtil {
    /**
     * 缓存user
     */
    public static UserInfoBean getUserInfo(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        try {
            ACache aCache = ACache.get(BaseApp.getApp());
            Gson mGson = new Gson();
            UserInfoBean userInfo = mGson.fromJson(aCache.getAsString(AllocationApi.BaseUrl + key), UserInfoBean.class);
            if (userInfo != null) {
                // userInfo.setHeadUrl(TokenUtil.getInstance().getHeadURl(BaseApp.getApp()));
                // userInfo.setHeadUrlTiny(TokenUtil.getInstance().getHeadURl(BaseApp.getApp()));
            }
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void removeUserInfo(String key) {

        if (TextUtils.isEmpty(key)) {
            return;
        }
        ACache aCache = ACache.get(BaseApp.getApp());
        aCache.remove(AllocationApi.BaseUrl + key);
        aCache.clear();
    }


    public static void saveUsrInfo(String key, UserInfoBean info) {
        try {
            if (!TextUtils.isEmpty(key)) {
                ACache aCache = ACache.get(BaseApp.getApp());
                Gson mGson = new Gson();
                aCache.put(AllocationApi.BaseUrl + key, mGson.toJson(info));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
