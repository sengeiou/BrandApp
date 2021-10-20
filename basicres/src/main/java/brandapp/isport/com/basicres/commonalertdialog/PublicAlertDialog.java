package brandapp.isport.com.basicres.commonalertdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import brandapp.isport.com.basicres.commonutil.LoadImageUtil;

/**
 * Created by huashao on 2017/12/21.
 */

public class PublicAlertDialog {

    public PublicAlertDialog() {
    }

    private static PublicAlertDialog instance;

    public static PublicAlertDialog getInstance() {

        if (null == instance) {

            synchronized (PublicAlertDialog.class) {

                instance = new PublicAlertDialog();
            }
        }
        return instance;
    }

    AlertDialog showDialog;

    public synchronized void clearShowDialog() {

        if (showDialog != null && showDialog.isShowing()) {
            showDialog.dismiss();
            showDialog = null;
        }
        showDialog = null;
        cancelshowDialogWithContentAndTitledialog();
        if (NoCancledialog != null && NoCancledialog.isShowing()) {
            NoCancledialog.dismiss();
            NoCancledialog = null;
        }
        NoCancledialog = null;
    }

    public synchronized void showDialog(String contenxtTitle, String contenxtMessage, Context context, String negativeBtnStr, String positiveBtnStr, final AlertDialogStateCallBack alertDialogState, boolean canCancel) {
        if (LoadImageUtil.isDestroy((Activity) context)) {
            return;
        }

        com.isport.blelibrary.utils.Logger.myLog("showDialog" + showDialog);

        if (showDialog != null && showDialog.isShowing()) {
            return;
        }
        showDialog = new AlertDialog.Builder(context)
                .setMessage(contenxtMessage)
                .setNegativeButton(negativeBtnStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //showDialog.dismiss();
                        showDialog = null;
                        alertDialogState.cancel();


                    }
                })
                .setPositiveButton(positiveBtnStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        showToast("执行删除操作");
                        // showDialog.dismiss();
                        showDialog = null;
                        alertDialogState.determine();



                    }
                })
                .create();
        if (!TextUtils.isEmpty(contenxtTitle)) {
            showDialog.setTitle(contenxtTitle);
        }
        showDialog.setCanceledOnTouchOutside(false);
        showDialog.show();
        // 在dialog执行show之后才能来设置
        TextView tvMsg = (TextView) showDialog.findViewById(android.R.id.message);
        tvMsg.setTextSize(16);

        tvMsg.setTextColor(Color.parseColor("#4E4E4E"));
        showDialog.getButton(showDialog.BUTTON_NEGATIVE).setTextSize(16);
        showDialog.getButton(showDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8C8C8C"));
        showDialog.getButton(showDialog.BUTTON_POSITIVE).setTextSize(16);
        showDialog.getButton(showDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DCE74"));
    }


    public void cancelshowDialogWithContentAndTitledialog() {
        if (showDialogWithContentAndTitledialog != null && showDialogWithContentAndTitledialog.isShowing()) {
            showDialogWithContentAndTitledialog.dismiss();
        }
        showDialogWithContentAndTitledialog = null;
    }

    AlertDialog showDialogWithContentAndTitledialog;

    public synchronized void showDialogWithContentAndTitle(String title, String contenxtMessage, Context context, String subStr, String cancelStr, final AlertDialogStateCallBack alertDialogState) {
        if (LoadImageUtil.isDestroy((Activity) context)) {
            return;
        }

        try {
            showDialogWithContentAndTitledialog = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(contenxtMessage)
                    .setNegativeButton(subStr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialogState.determine();
                            showDialogWithContentAndTitledialog = null;
                        }
                    })
                    .setPositiveButton(cancelStr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        showToast("执行删除操作");
                            alertDialogState.cancel();
                            showDialogWithContentAndTitledialog = null;
                        }
                    })
                    .create();
            showDialogWithContentAndTitledialog.show();
            showDialogWithContentAndTitledialog.setCancelable(false);
            // 在dialog执行show之后才能来设置
            TextView tvMsg = (TextView) showDialogWithContentAndTitledialog.findViewById(android.R.id.message);
            tvMsg.setTextSize(16);
            tvMsg.setTextColor(Color.parseColor("#4E4E4E"));
            showDialogWithContentAndTitledialog.getButton(showDialogWithContentAndTitledialog.BUTTON_NEGATIVE).setTextSize(16);
            showDialogWithContentAndTitledialog.getButton(showDialogWithContentAndTitledialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1DCE74"));
            showDialogWithContentAndTitledialog.getButton(showDialogWithContentAndTitledialog.BUTTON_POSITIVE).setTextSize(16);
            showDialogWithContentAndTitledialog.getButton(showDialogWithContentAndTitledialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#8C8C8C"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    AlertDialog NoCancledialog;

    public synchronized void showDialogNoCancle(boolean isCancel, String contenxtTitle, String contenxtMessage, Context context, String positiveBtnStr, final AlertDialogStateCallBack alertDialogState) {
        if (LoadImageUtil.isDestroy((Activity) context)) {
            return;
        }

        try {
            if (NoCancledialog != null && NoCancledialog.isShowing()) {
                com.isport.blelibrary.utils.Logger.myLog("DFUActivity showDialog" + showDialog + "NoCancledialog.isShowing():" + NoCancledialog.isShowing());
                return;
            }
            com.isport.blelibrary.utils.Logger.myLog("DFUActivity showDialog" + showDialog);

            NoCancledialog = new AlertDialog.Builder(context)
                    .setMessage(contenxtMessage)
                    .setPositiveButton(positiveBtnStr, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        showToast("执行删除操作");
                            alertDialogState.determine();
                            NoCancledialog = null;
                        }
                    })
                    .create();
            if (!TextUtils.isEmpty(contenxtTitle)) {
                NoCancledialog.setTitle(contenxtTitle);
            }

            NoCancledialog.show();
            NoCancledialog.setCancelable(isCancel);
            // 在dialog执行show之后才能来设置
            TextView tvMsg = (TextView) NoCancledialog.findViewById(android.R.id.message);
            tvMsg.setTextSize(16);
            tvMsg.setTextColor(Color.parseColor("#8C8C8C"));
            NoCancledialog.getButton(NoCancledialog.BUTTON_POSITIVE).setTextSize(16);
            NoCancledialog.getButton(NoCancledialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DCE74"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
