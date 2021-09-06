package com.isport.brandapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

public class CustomDialogUtil {
    /** * * @param context 上下文 * @param resource 资源 layout布局 * @param dialogStyle 弹出样式 * @param gravity 方向 * @param width 宽 * @param height 高 * @param animation 动画 */
    public static void customDialog(Context context, int resource,
                             int dialogStyle,
                             int gravity,
                             int width,
                             int height,
                             int animation) {
        View view = View.inflate(context, resource, null);
        final Dialog dialog = new Dialog(context, dialogStyle);
        dialog.setContentView(view);
        WindowManager.LayoutParams layoutParams =
                dialog.getWindow().getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
//        layoutParams.y = 180;//距离顶部的距离
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setGravity(gravity);
        dialog.getWindow().setWindowAnimations(animation);
        dialog.show();
    }
}
