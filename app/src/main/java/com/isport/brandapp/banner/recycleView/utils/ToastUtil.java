package com.isport.brandapp.banner.recycleView.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import brandapp.isport.com.basicres.commonutil.StringUtil;


public class ToastUtil {
    private static Toast toast = null;
    private static Context context;

    public static void init(Context cnt) {
        context = cnt;
    }

    public static void showTextToast(String msg) {
        if(context == null)
            return;
        if (StringUtil.isBlank(msg) || msg.contains("没有访问权限！")) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }




    public static void showTextToastById(int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getResources().getString(msg), Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showTextToast(Context context, String msg) {
        if (StringUtil.isBlank(msg) || msg.contains("没有访问权限！")) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        //	toast.show();
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showTextToastById(Context context, int msg) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getResources().getString(msg), Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
