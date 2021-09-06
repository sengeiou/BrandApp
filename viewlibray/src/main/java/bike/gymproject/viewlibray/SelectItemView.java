package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class SelectItemView extends LinearLayout {

    /**
     * select标签
     */
    TextView tvName;
    String strLabelValue;
    int tvLableColor;
    float tvLableSize;


    TextView tvContent;

    private String strContentValue;
    private int tvContentColor;
    private float tvContentSize;

    private Drawable dContentBg;
    private Drawable dArrowblew;


    public SelectItemView(Context context) {
        this(context, null);
    }

    public SelectItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SelectItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectItemView,
                defStyleAttr, 0);
        strLabelValue = a.getString(R.styleable.SelectItemView_lableValue);
        tvLableSize = a.getDimension(R.styleable.SelectItemView_lableTextSize, 14);
        tvLableColor = a.getColor(R.styleable.SelectItemView_lableColor, 0);

        strContentValue = a.getString(R.styleable.SelectItemView_contenValue);
        tvContentSize = a.getDimension(R.styleable.SelectItemView_contentTextSize, 14);
        tvContentColor = a.getColor(R.styleable.SelectItemView_contentValueColor, 0);


        dArrowblew = a.getDrawable(R.styleable.SelectItemView_arrowIcon);
        dContentBg = a.getDrawable(R.styleable.SelectItemView_contentBgIcon);
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
        LayoutInflater.from(getContext()).inflate(R.layout.view_select_item, this, true);

        /**
         * mText.setTextSize(18);  // 方法1
         mText.setTextSize(getResources().getDimension(R.dimen.font1));  // 方法2
         mText.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.font1));  // 方法3
         mText.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);  // 方法4
         */

        tvContent = findViewById(R.id.tv_value);
        tvName = findViewById(R.id.tv_unit);

        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvContentSize);
        tvContent.setTextColor(tvContentColor);

        if (tvName != null) {
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, tvLableSize);
            tvName.setTextColor(tvLableColor);
        }

    }

    private void initData() {
        if (!TextUtils.isEmpty(strLabelValue) && tvName != null) {
            tvName.setText(strLabelValue);
        }

        if (!TextUtils.isEmpty(strContentValue)) {
            tvContent.setText(strContentValue);
        }

        setRemindIcon(dArrowblew);
        tvContent.setBackgroundDrawable(dContentBg);
    }

    private void setListener() {
    }


    public void setContent(String text) {
        if (null == tvContent) {
            return;
        }
        this.strContentValue = text;
        tvContent.setText(TextUtils.isEmpty(text) ? "" : text);
    }


    public void setRemindIcon(Drawable remindIcon) {
        this.dArrowblew = remindIcon;
        if (null == remindIcon) {
            return;
        }
        this.dArrowblew.setBounds(0, 0, this.dArrowblew.getMinimumWidth(), this.dArrowblew.getMinimumHeight());
        // remindImageView.setImageDrawable(this.remindIcon);
    }


    public interface OnItemViewCheckedChangeListener {
        void onCheckedChanged(int id, boolean isChecked);
    }
}