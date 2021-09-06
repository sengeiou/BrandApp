package brandapp.isport.com.basicres.commonalertdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import com.isport.brandapp.basicres.R;

import com.isport.blelibrary.utils.CommonDateUtil;

import brandapp.isport.com.basicres.commonutil.UIUtils;


/**
 * 自定义加载进度条，有旋转进度条+TextView
 */
public class SyncDataProgressDialog extends ProgressDialog implements ProgressView.ProgressValueOnlcik {

    private TextView tips_loading_msg, tv_prog, tv_sum_times;
    private ProgressView progressView;

    private String message = null;

    public SyncDataProgressDialog(Context context) {
        super(context, R.style.theme_customer_progress_dialog);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.common_sync_dialog_load);
        tips_loading_msg = findViewById(R.id.tips_loading_msg);
        tv_prog = findViewById(R.id.tv_prog);
        progressView = findViewById(R.id.progressBar);
        tv_sum_times = findViewById(R.id.tv_sum_times);
        tips_loading_msg.setText(this.message);
        progressView.setProgressListen(this);
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

            this.show();
            this.setCanceledOnTouchOutside(false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public void showProgrss100() {
        progressView.setProgress(100);
    }


    public void showProgress(int times) {
        // this.progress = progress;
        if (progressView != null) {
            progressView.startAnimation(times);
        }
        if (tv_sum_times != null) {
            tv_sum_times.setText(String.format(UIUtils.getString(R.string.comm_sync_data), times));
        }
        this.setMessage(getContext().getResources().getString(R.string.common_sync));

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

    @Override
    public void onProgress(float progress) {
        tv_prog.setText(CommonDateUtil.formatInterger(progress * 100) + "%");
    }
}
