package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 李超凡
 * V 1.0.0
 * Create at 2019/2/25
 */
public class WatchStepDataView extends LinearLayout {
    ImageView ivIcon;
    TextView  tvValue;
    TextView  tvType;
    TextView  tvUnit;

    String strValue;
    String strType;
    String strUnit;

    int colorValue;
    int colorType;
    int colorUnit;

    float sizeValue;
    float sizeType;
    float sizeUnit;

    private Drawable drawableIcon;

    public WatchStepDataView(Context context) {
        this(context, null);
    }

    public WatchStepDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchStepDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WatchStepDataView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WatchTypeDataView,
                defStyleAttr, 0);
        strValue = a.getString(R.styleable.WatchTypeDataView_valueText);
        strType = a.getString(R.styleable.WatchTypeDataView_typeText);
        strUnit = a.getString(R.styleable.WatchTypeDataView_uniteText);

        colorValue = a.getColor(R.styleable.WatchTypeDataView_valueColor, context.getResources().getColor(R.color.common_white));
        colorType = a.getColor(R.styleable.WatchTypeDataView_typeColor, context.getResources().getColor(R.color.common_white));
        colorUnit = a.getColor(R.styleable.WatchTypeDataView_uniteColor, context.getResources().getColor(R.color.common_white));

        sizeType = a.getDimension(R.styleable.WatchTypeDataView_typeSize, 14);
        sizeValue = a.getDimension(R.styleable.WatchTypeDataView_valueSize, 14);
        sizeUnit = a.getDimension(R.styleable.WatchTypeDataView_uniteSize, 14);

        drawableIcon = a.getDrawable(R.styleable.WatchTypeDataView_bg);

        a.recycle();

        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_watch_step_item, this, true);

        ivIcon = findViewById(R.id.iv_icon);
        tvValue = findViewById(R.id.tv_value);
        tvType = findViewById(R.id.tv_type);
        tvUnit = findViewById(R.id.tv_unit);

//        tvValue.setTextSize(sizeValue);
//        tvType.setTextSize(sizeType);
//        tvUnit.setTextSize(sizeUnit);

        tvValue.setTextColor(colorValue);
        tvUnit.setTextColor(colorUnit);
        tvType.setTextColor(colorType);
        ivIcon.setImageDrawable(drawableIcon);

    }

    private void initData() {
        if (!TextUtils.isEmpty(strType)) {
            tvType.setText(strType);
        }

        if (!TextUtils.isEmpty(strValue)) {
            tvValue.setText(strValue);
        }

        if (!TextUtils.isEmpty(strUnit)) {
            tvUnit.setText(strUnit);
        }

    }

    private void setListener() {
    }

    public WatchStepDataView setValueIdText(int resId) {
        if (checkResId((resId >>> 24) < 2)) return this;
        return setValueText(getContext().getString(resId));
    }

    public WatchStepDataView setValueText(String text) {
        if (checkResId(null == tvValue)) return this;
        this.strValue = text;
        tvValue.setText(TextUtils.isEmpty(text) ? "" : text);
        return this;
    }

    public String getValueText() {
        return (String) tvValue.getText();
    }

    public WatchStepDataView setTypeIdText(int resId) {
        if (checkResId((resId >>> 24) < 2)) return this;
        return setTypeText(getContext().getString(resId));
    }

    public WatchStepDataView setTypeText(String text) {
        if (checkResId(null == strType)) return this;
        this.strType = text;
        tvType.setText(TextUtils.isEmpty(text) ? "" : text);
        return this;
    }

    public WatchStepDataView setUniteIdText(int resId) {
        if (checkResId((resId >>> 24) < 2)) return this;
        return setUniteText(getContext().getString(resId));
    }


    public WatchStepDataView setUniteText(String text) {
        if (checkResId(null == tvUnit)) return this;
        this.strUnit = text;
        tvUnit.setText(TextUtils.isEmpty(text) ? "" : text);
        return this;
    }

    private boolean checkResId(boolean b) {
        if (b) {
            return true;
        }
        return false;
    }

    /**
     * 根据资源ID获取Drawable/设置边框
     *
     * @param resId
     * @return
     */
    private Drawable getDrawable(int resId) {
        if (checkResId((resId >>> 24) < 2)) return null;
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getContext().getDrawable(resId);
        } else {
            drawable = getContext().getResources().getDrawable(resId);
        }

        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }


    /*public RoundImageView getRemindImageView() {
        return remindImageView;
    }*/


    public interface OnItemViewCheckedChangeListener {
        void onCheckedChanged(int id, boolean isChecked);
    }
}