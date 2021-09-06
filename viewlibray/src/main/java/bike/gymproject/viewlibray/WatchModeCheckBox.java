package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 李超凡
 * V 1.0.0
 * Create at 2019/2/25
 */
public class WatchModeCheckBox extends LinearLayout {

    TextView tvModeTitle;
    CheckBox tvModeRadio;

    private boolean modeChecked;


    public WatchModeCheckBox(Context context) {
        this(context, null);
    }

    public WatchModeCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchModeCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WatchModeCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WatchModeCheckBox,
                defStyleAttr, 0);
        modeChecked = a.getBoolean(R.styleable.WatchModeCheckBox_modeChecked, false);

        a.recycle();
        initBase(context, attrs, defStyleAttr);
    }

    Context mContex;

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        mContex = context;
        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_mode_check_item, this, true);

        tvModeTitle = findViewById(R.id.mode_title);
        tvModeRadio = findViewById(R.id.mode_radio);

    }

    private void initData() {
        setChecked(modeChecked);
    }

    public void setChecked(boolean checked) {
        tvModeTitle.setText(checked ? mContex.getString(R.string.active_mode) : mContex.getString(R.string.auto_mode));
        tvModeRadio.setChecked(checked);
    }

    public boolean isChecked() {
        return tvModeRadio.isChecked();
    }

    private void setListener() {

    }

    public void setOnModeCheckBoxChangeListener(final OnModeCheckBoxChangeListener listener) {
        tvModeRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onModeCheckBoxChanged(getId(), tvModeRadio.isChecked());
                    setChecked(tvModeRadio.isChecked());
                }
            }
        });
    }

    private OnModeCheckBoxChangeListener listener;

    public interface OnModeCheckBoxChangeListener {
        void onModeCheckBoxChanged(int id, boolean isChecked);
    }
}