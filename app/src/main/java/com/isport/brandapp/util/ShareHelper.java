package com.isport.brandapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.isport.brandapp.bean.ShareBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

/**
 * @author mhj
 * Create at 2017/10/18 16:00
 */
public class ShareHelper {

    public static void share(Activity activity, SHARE_MEDIA platform, String title, String content, String url, int thumbResId, UMShareListener shareListener) {

        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setThumb(new UMImage(activity, thumbResId));
        umWeb.setDescription(content);

        new ShareAction(activity).setPlatform(platform)
                .withMedia(umWeb)
                .withText(content)
                .setCallback(shareListener)
                .share();
    }

    public static void shareFile(Activity activity, SHARE_MEDIA platform, String title, String content, String url, File file, UMShareListener shareListener) {

        UMWeb umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setThumb(new UMImage(activity, file));
        umWeb.setDescription(content);

        new ShareAction(activity).setPlatform(platform)
                .withMedia(umWeb)
                .withText(content)
                .setCallback(shareListener)
                .share();
    }


    public static UMImage getUMWeb(Context context, File file) {
        UMImage umWeb = new UMImage(context, file);
//        umWeb.setTitle(getResources().getString(R.string.share_title));
        umWeb.setThumb(new UMImage(context, file));
//        umWeb.setDescription("我在iSport Plan获得了勋章，快来围观吧！");
        return umWeb;
    }
    public static UMImage getUMWeb(Context context, Bitmap file) {
        UMImage umWeb = new UMImage(context, file);
//        umWeb.setTitle(getResources().getString(R.string.share_title));
        umWeb.setThumb(new UMImage(context, file));
//        umWeb.setDescription("我在iSport Plan获得了勋章，快来围观吧！");
        return umWeb;
    }

    public static UMWeb getUMweb(Context context, ShareBean bean) {
        String head = bean.getShareUrl().substring(0, 4);
        UMWeb umWeb;
        if (head.equals("http")) {
            umWeb = new UMWeb(bean.getShareUrl());
        } else {
            umWeb = new UMWeb("http://" + bean.getShareUrl());
        }
        umWeb.setTitle(bean.getTitle());
        umWeb.setThumb(new UMImage(context, bean.getImageUrl()));
        umWeb.setDescription(bean.getContent());
        return umWeb;
    }
}
