package com.jkcq.platform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * created by wq on 2019/5/20
 */
public class MapUtil {

    public static void jumpToMap(Activity mContext, String userLat, String userLng, String titleAddress) {
        //腾讯地图判断部分
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);
        intent2.addCategory(Intent.CATEGORY_DEFAULT);
        //将功能Scheme以URI的方式传入data
        Uri uri2 = Uri.parse("qqmap://map/routeplan?type=drive&to=tvShopName&tocoord=" + userLat + "," + userLng);
        intent2.setData(uri2);
        //跳转百度地图 lng目的地纬度   lat 目的地精度    tvshopName 目的地名称
        if (isAvilible(mContext, "com.baidu.BaiduMap")) {// 传入指定应用包名

            try {
                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                        + userLat + ","
                        + userLng + "|name:" + titleAddress + // 终点
                        "&mode=driving&" + // 导航路线方式
                        "region=武汉" + //
                        "&src=东风标致#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                mContext.startActivity(intent); // 启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        }
        //跳转高德地图
        else if (isAvilible(mContext, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=新疆和田&poiname=" + titleAddress + "&lat="
                        + userLat
                        + "&lon="
                        + userLng + "&dev=0");
                mContext.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (intent2.resolveActivity(mContext.getPackageManager()) != null) {
            //启动该页面即可     腾讯地图
            mContext.startActivity(intent2);
        } else {

            Toast.makeText(mContext, "您尚未安装地图", Toast.LENGTH_LONG);
            //跳转到应用商店去下载高德地图app
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    //判断手机上是否安装了指定的百度地图，高德地图等软件
    private static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
