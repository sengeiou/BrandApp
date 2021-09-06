package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class ItemInditionView extends LinearLayout {

    ImageView iv_indition;

    public ItemInditionView(Context context) {
        this(context, null);
    }

    public ItemInditionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemInditionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemInditionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemView,
                defStyleAttr, 0);


        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_indition_item, this, true);

        iv_indition = (ImageView) findViewById(R.id.iv_indition);

    }

    private void initData() {

    }


    public void setImageviewValue(int res) {
        iv_indition.setImageResource(res);
    }


    public interface OnItemViewCheckedChangeListener {
        void onCheckedChanged(int id, boolean isChecked);
    }

    public interface OnItemViewModeCheckedChangeListener {
        void onModeCheckedChanged(int id, boolean isChecked);
    }

    public interface OnItemViewRadioCheckedChangeListener {
        void onRadioCheckedChanged(int id, boolean isActive);
    }

    public interface OnContentClickListener {
        void onContentClick();
    }


}