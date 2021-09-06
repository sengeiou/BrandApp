package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class ShareItemView extends LinearLayout {

    TextView tvValue;
    TextView tvTitile;
    TextView tvUnit;
    TextView tvBottom;

    View viewAbove;
    View viewBelow;


    String strValue;
    String strTitle;
    String strUnit;
    String strBottom;

    int colorValue;
    int colorTitle;
    int colorUnit;
    int colorBottom;

    float sizeValue;
    float sizeTitle;
    float sizeUnit;
    float sizeBottom;


    public ShareItemView(Context context) {
        this(context, null);
    }

    public ShareItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShareItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShareItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    boolean isShowLine, isShowUnit, isShowValue, isShowTitle;
    private Drawable titleIcon;

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShareItem,
                defStyleAttr, 0);
        strValue = a.getString(R.styleable.ShareItem_sharevaluseText);
        strTitle = a.getString(R.styleable.ShareItem_sharetitleText);
        strUnit = a.getString(R.styleable.ShareItem_shareunitText);
        strBottom = a.getString(R.styleable.ShareItem_sharebottomText);

        colorValue = a.getColor(R.styleable.ShareItem_sharevaluseColor, context.getResources().getColor(R.color.common_white));
        colorTitle = a.getColor(R.styleable.ShareItem_sharetitleColor, context.getResources().getColor(R.color.common_white));
        colorUnit = a.getColor(R.styleable.ShareItem_shareunitColor, context.getResources().getColor(R.color.common_white));
        colorBottom = a.getColor(R.styleable.ShareItem_sharebottomColor, context.getResources().getColor(R.color.common_white));

        titleIcon = a.getDrawable(R.styleable.ShareItem_sharetitleIcon);
        isShowLine = a.getBoolean(R.styleable.ShareItem_shareisShowLine, false);

        sizeTitle = a.getDimension(R.styleable.ShareItem_sharetitleSize, 14);
        sizeValue = a.getDimension(R.styleable.ShareItem_sharevaluseSize, 14);
        sizeUnit = a.getDimension(R.styleable.ShareItem_shareunitSize, 14);
        sizeBottom = a.getDimension(R.styleable.ShareItem_sharebottomSize, 14);

        isShowUnit = a.getBoolean(R.styleable.ShareItem_shareisUnit, false);

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
        LayoutInflater.from(getContext()).inflate(R.layout.view_share_item, this, true);

        tvValue = findViewById(R.id.tv_value);
        tvTitile = findViewById(R.id.tv_title);
        tvUnit = findViewById(R.id.tv_unit);
        tvBottom = findViewById(R.id.tv_bottom);

        viewBelow = findViewById(R.id.line_below);
        viewAbove = findViewById(R.id.line_above);

        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeValue);
        tvTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeTitle);
        tvUnit.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeUnit);
        tvBottom.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeBottom);

        tvBottom.setGravity(Gravity.CENTER);

        tvValue.setTextColor(colorValue);
        tvUnit.setTextColor(colorUnit);
        tvTitile.setTextColor(colorTitle);
        tvBottom.setTextColor(colorBottom);

    }

    public void setTitleSize(float size) {
        tvTitile.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    private void initData() {


        if (!TextUtils.isEmpty(strValue)) {
            tvValue.setText(strValue);
        }

        if (!TextUtils.isEmpty(strUnit)) {
            tvUnit.setText(strUnit);
        }

        if (!TextUtils.isEmpty(strBottom)) {
            tvBottom.setText(strBottom);
        }
        isShowUnit(isShowUnit);
        //isShowValue(isShowValue);
        //isShowTitle(isShowTitle);
        isShowLine(isShowLine);
        if (titleIcon != null) {
            tvTitile.setVisibility(View.VISIBLE);
            titleIcon.setBounds(0, 0, titleIcon.getMinimumWidth(), titleIcon.getMinimumHeight());
            tvTitile.setCompoundDrawables(titleIcon, null, null, null);
            tvTitile.setText("");
        } else {
            tvTitile.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitile.setVisibility(VISIBLE);
            tvTitile.setText(strTitle);
        }
    }

    private void setListener() {
    }

    public void setTitleText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setTitleText(getContext().getString(resId));
    }


    public void setTitleText(String text) {
        if (null == tvTitile) {
            return;
        }
        tvTitile.setVisibility(VISIBLE);
        this.strTitle = text;
        tvTitile.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setValueText(String text) {
        if (null == tvValue) {
            return;
        }
        this.strValue = text;
        tvValue.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setBottomText(String text) {
        if (null == tvBottom) {
            return;
        }
        this.strBottom = text;
        tvBottom.setText(TextUtils.isEmpty(text) ? "" : text);

    }

    public void setBottomText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setBottomText(getContext().getString(resId));
    }

    public void setUnitText(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setUnitText(getContext().getString(resId));
    }

    public void setUnitText(String text) {
        if (null == tvUnit) {
            return;
        }
        this.strUnit = text;
        tvUnit.setVisibility(VISIBLE);
        tvUnit.setText(TextUtils.isEmpty(text) ? "" : text);
    }


    public void setTitleIcon(int resId) {
        if ((resId >>> 24) < 2 || tvTitile == null) {
            return;
        }
        tvTitile.setVisibility(View.VISIBLE);
        tvTitile.setText("");
        tvTitile.setCompoundDrawables(null, null, getDrawable(resId), null);
    }

    public void setTitleIcon() {
        if (tvTitile != null) {
            tvTitile.setVisibility(View.GONE);
        }
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

    public void isShowLine(boolean isShow) {
        if (viewBelow != null) {
            viewBelow.setVisibility(isShow ? VISIBLE : GONE);
        }
        if (viewAbove != null) {
            viewAbove.setVisibility(isShow ? VISIBLE : GONE);
        }
    }

    public void isShowUnit(boolean isShow) {
        tvUnit.setVisibility(isShowUnit ? VISIBLE : GONE);
    }

    public void isShowValue(boolean isShow) {
        tvValue.setVisibility(isShowValue ? VISIBLE : GONE);
    }

    public void isShowTitle(boolean isShow) {
        tvTitile.setVisibility(isShowTitle ? VISIBLE : GONE);
    }
}