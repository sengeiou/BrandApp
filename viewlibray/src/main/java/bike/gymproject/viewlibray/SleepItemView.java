package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class SleepItemView extends LinearLayout {

    TextView tvValue;
    TextView tvTitile;
    TextView tvUnit;


    String strValue;
    String strTitle;
    String strUnit;

    int colorValue;
    int colorTitle;
    int colorUnit;

    float sizeValue;
    float sizeTitle;
    float sizeUnit;

    Drawable iconDrawable;

    ImageView leftIcon;

    public SleepItemView(Context context) {
        this(context, null);
    }

    public SleepItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SleepItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SleepItem,
                defStyleAttr, 0);
        strValue = a.getString(R.styleable.SleepItem_valuseText);
        strTitle = a.getString(R.styleable.SleepItem_titleText);
        strUnit = a.getString(R.styleable.SleepItem_unitText);

        colorValue = a.getColor(R.styleable.SleepItem_valuseColor, context.getResources().getColor(R.color.common_white));
        colorTitle = a.getColor(R.styleable.SleepItem_titleColor, context.getResources().getColor(R.color.common_white));
        colorUnit = a.getColor(R.styleable.SleepItem_unitColor, context.getResources().getColor(R.color.common_white));

        sizeTitle = a.getDimension(R.styleable.SleepItem_titleSize, 14);
        sizeValue = a.getDimension(R.styleable.SleepItem_valuseSize, 14);
        sizeUnit = a.getDimension(R.styleable.SleepItem_unitSize, 14);

        iconDrawable = a.getDrawable(R.styleable.SleepItem_leftIcon);

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
        LayoutInflater.from(getContext()).inflate(R.layout.view_sleep_item, this, true);

        tvValue = findViewById(R.id.tv_value);
        tvTitile = findViewById(R.id.tv_title);
        tvUnit = findViewById(R.id.tv_unit);

        leftIcon = findViewById(R.id.iv_icon);

        //tvValue.setTextSize(sizeValue);
        //tvTitile.setTextSize(sizeTitle);
        //tvUnit.setTextSize(sizeUnit);

        tvValue.setTextColor(colorValue);
        tvUnit.setTextColor(colorUnit);
        tvTitile.setTextColor(colorTitle);

        leftIcon.setImageDrawable(iconDrawable);

    }

    private void initData() {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitile.setText(strTitle);
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

    public void setTitleText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setValueText(getContext().getString(resId));
    }

    public void setValueText(String text) {
        if (null == tvValue) {
            return;
        }
        this.strValue = text;
        tvValue.setText(TextUtils.isEmpty(text) ? "" : text);
    }


    /**
     * 根据资源ID获取Drawable/设置边框
     *
     * @param resId
     * @return
     */
    private Drawable getDrawable(int resId) {
        if ((resId >>> 24) < 2) {
            return null;
        }
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