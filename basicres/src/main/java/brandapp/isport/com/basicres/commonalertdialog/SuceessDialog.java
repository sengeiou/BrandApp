package brandapp.isport.com.basicres.commonalertdialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.brandapp.basicres.R;


/*
 *
 *
 * @author mhj
 * Create at 2019/3/30 17:27
 */
public class SuceessDialog extends BaseDialog {


    Context context;
    ImageView ivIcon;
    TextView tvMessage;


    public SuceessDialog(@NonNull Context context, int resId, String message) {
        super(context, R.style.SimpleHUD1);
        setContentView(R.layout.dialog_success_layout);
        this.context = context;

        initView();
        initEvent();
        initData(resId, message);

    }

    private void initData(int resId, String message) {
        ivIcon.setImageResource(resId);
        tvMessage.setText(message);
    }


    public void initView() {
        ivIcon = findViewById(R.id.iv_icon);
        tvMessage = findViewById(R.id.tv_message);
    }

    public void initEvent() {

    }


    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT ;
        layoutParams.width = (int) context.getResources().getDimension(R.dimen.dilaog_common_success_width);
        layoutParams.height = (int) context.getResources().getDimension(R.dimen.dilaog_common_success_height);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
        this.setCancelable(false);
    }


}
