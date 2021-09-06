package brandapp.isport.com.basicres.commonalertdialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.isport.brandapp.basicres.R;


/**
 * 自定义加载进度条，有旋转进度条+TextView
 */
public class LoadProgressDialog extends ProgressDialog {

    private TextView tips_loading_msg;

    private String message = null;

    public LoadProgressDialog(Context context) {
        super(context, R.style.theme_customer_progress_dialog);
        /*dialog = new Dialog(context, R.style.dialog_translucent);
        dialog.setContentView(R.layout.dialog_send_daynmic);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();// 最最重要的步骤
        layoutParams.x = 0;
        layoutParams.y = 0;
        window.setAttributes(layoutParams);*/

        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.common_dialog_load);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

    public void setText(String message) {
        this.message = message;
        if (tips_loading_msg != null) {
            tips_loading_msg.setText(this.message);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        setText(message);
    }

    /**
     * 显示默认消息对话框
     */
    public void showDialog() {
        try {
            this.setMessage(getContext().getResources().getString(R.string.common_please_wait));
            this.show();
            this.setCanceledOnTouchOutside(false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showDownDialog(boolean enforce) {
        try {
            this.setMessage(getContext().getResources().getString(R.string.common_please_wait));
            this.show();
            this.setCancelable(enforce);
            this.setCanceledOnTouchOutside(enforce);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示带字符串对话
     */
    public void showDialog(int resId) {
        this.message = getContext().getResources().getString(resId);
        this.setMessage(message);
        try {
            this.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(String message) {
        this.setMessage(message);
        try {
            this.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示是否可按返回键取消对话框
     *
     * @param isback 是否允许返回键响应
     */
    public void showDialog(boolean isback) {
        this.setCancelable(!isback);
        this.show();
    }

    /**
     * 显示帶字符串并設置是否可按返回鍵取消对话�?
     *
     * @param resId  资源id
     * @param isback true是不相应back�?
     */
    public void showDialog(int resId, boolean isback) {
        this.message = getContext().getResources().getString(resId);
        this.setMessage(message);
        this.setCancelable(!isback);
        this.show();
    }

    public void updateMessage(String message) {
        this.setMessage(message);
    }

    /**
     * 关闭对话
     */
    public void cancelDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
