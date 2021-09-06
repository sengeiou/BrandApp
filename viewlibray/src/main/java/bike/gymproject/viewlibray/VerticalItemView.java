package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class VerticalItemView extends LinearLayout {

    /**
     * item的icon
     */
    ImageView ivTopHead;
    /**
     * item中间的内容
     */
    BebasNeueTextView tvCertenValue;

    int tvCertenValueColor;

    String certenValue;

    float tvCertenValueSize;


    TextView tvCertenUnit;

    int tvCertenUnitColor;

    String certenUnit;

    float tvCertenUnitSize;


    /**
     * item最后一行的内容
     */
    TextView tvBottomValue;

    int tvBottomValueColor;

    String bottemValue;

    float tvBottomValueSize;

    TextView tvBottomEnValue;

    int tvBottomEnValueColor;

    String bottemEnValue;

    float tvBottomEnValueSize;

    private Drawable remindIcon;

    private RelativeLayout rLayout;

    boolean isShowRlayout;

    boolean isShowCertenUnit;

    public VerticalItemView(Context context) {
        this(context, null);
    }

    public VerticalItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VerticalItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerticalItemView,
                defStyleAttr, 0);
        tvCertenValueColor = a.getColor(R.styleable.VerticalItemView_certenValueColor, 0);
        tvCertenValueSize = a.getDimension(R.styleable.VerticalItemView_certenValueTextSize, 14);
        certenValue = a.getString(R.styleable.VerticalItemView_certenValue);

        tvCertenUnitColor = a.getColor(R.styleable.VerticalItemView_certenUnitColor, 0);
        tvCertenUnitSize = a.getDimension(R.styleable.VerticalItemView_certenUnitTextSize, 14);
        certenUnit = a.getString(R.styleable.VerticalItemView_certenUnitValue);

        bottemValue = a.getString(R.styleable.VerticalItemView_unitValue);
        tvBottomValueColor = a.getColor(R.styleable.VerticalItemView_unitValueColor, 0);
        tvBottomValueSize = a.getDimension(R.styleable.VerticalItemView_unitValueTextSize, 14);

        bottemEnValue = a.getString(R.styleable.VerticalItemView_unitEnValue);
        tvBottomEnValueColor = a.getColor(R.styleable.VerticalItemView_unitEnValueColor, 0);
        tvBottomEnValueSize = a.getDimension(R.styleable.VerticalItemView_unitEnValueTextSize, 14);

        remindIcon = a.getDrawable(R.styleable.VerticalItemView_itemIcon);

        isShowRlayout = a.getBoolean(R.styleable.VerticalItemView_showLayout, true);

        isShowCertenUnit = a.getBoolean(R.styleable.VerticalItemView_showCertenUnitl, false);

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
        LayoutInflater.from(getContext()).inflate(R.layout.view_home_item, this, true);

        /**
         * mText.setTextSize(18);  // 方法1
         mText.setTextSize(getResources().getDimension(R.dimen.font1));  // 方法2
         mText.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.font1));  // 方法3
         mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);  // 方法4
         */
        ivTopHead = findViewById(R.id.iv_head);
        tvCertenValue = findViewById(R.id.tv_value);
        tvBottomValue = findViewById(R.id.tv_unit);
        tvBottomEnValue = findViewById(R.id.tv_en_unit);
        tvCertenUnit = findViewById(R.id.tv_certen_unit);
        rLayout = findViewById(R.id.r_layout);

        tvCertenValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvCertenValueSize);
        tvCertenValue.setTextColor(tvCertenValueColor);


        tvCertenUnit.setVisibility(isShowCertenUnit ? VISIBLE : GONE);
        if (isShowCertenUnit) {
            tvCertenUnit.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvCertenUnitSize);
            tvCertenUnit.setTextColor(tvCertenUnitColor);
        }
        rLayout.setVisibility(isShowRlayout ? VISIBLE : GONE);
        if (isShowRlayout) {
            tvBottomValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvBottomValueSize);
            tvBottomValue.setTextColor(tvBottomValueColor);

            tvBottomEnValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvBottomEnValueSize);
            tvBottomEnValue.setTextColor(tvBottomEnValueColor);
        }
    }

    private void initData() {
        if (!TextUtils.isEmpty(certenValue)) {
            tvCertenValue.setText(certenValue);
        }

        if (!TextUtils.isEmpty(bottemValue)) {
            tvBottomValue.setText(bottemValue);
        }

        if (!TextUtils.isEmpty(bottemEnValue)) {
            tvBottomEnValue.setText(bottemEnValue);
        }

        if (!TextUtils.isEmpty(certenUnit)) {
            tvCertenUnit.setText(certenUnit);
        }

        ivTopHead.setImageDrawable(remindIcon);
    }

    private void setListener() {
    }


    public void setCertenText(String text) {
        if (null == tvCertenValue) {
            return;
        }
        this.certenValue = text;
        tvCertenValue.setText(TextUtils.isEmpty(text) ? "" : text);
    }


  /*  public void setRemindIcon(Drawable remindIcon) {
        this.remindIcon = remindIcon;
        if (null == remindIcon) {
            return;
        }
        this.remindIcon.setBounds(0, 0, this.remindIcon.getMinimumWidth(), this.remindIcon.getMinimumHeight());
        // remindImageView.setImageDrawable(this.remindIcon);
    }*/


    public void setCertenValue(String value) {
        if (tvCertenValue != null) {
            tvCertenValue.setText(value);
        }
    }

    public interface OnItemViewCheckedChangeListener {
        void onCheckedChanged(int id, boolean isChecked);
    }
}