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
public class TextieImageView extends LinearLayout {

    TextView tvValue;
    ImageView ivLog;

    BebasNeueTextView tvNumber;

    TextView tvTitle;

    int textColor;
    float textSize;

    int titleColor;
    float titleSize;


    int numberColor;
    float numberSize;


    boolean isShowIcon;
    boolean isShowTitle;
    boolean isShowNumber;


    private Drawable icon;
    private String strValue, strNumber, strTitle;

    public TextieImageView(Context context) {
        this(context, null);
    }

    public TextieImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextieImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextieImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewImageView,
                defStyleAttr, 0);

        icon = a.getDrawable(R.styleable.TextViewImageView_text_image_view_text_icon);
        strValue = a.getString(R.styleable.TextViewImageView_text_image_view_text_value);
        strNumber = a.getString(R.styleable.TextViewImageView_text_image_view_text_number_value);
        strTitle = a.getString(R.styleable.TextViewImageView_text_image_view_text_title_value);

        isShowIcon = a.getBoolean(R.styleable.TextViewImageView_text_image_view_text_show_icon, true);
        isShowNumber = a.getBoolean(R.styleable.TextViewImageView_text_image_view_text_show_number_value, false);
        isShowTitle = a.getBoolean(R.styleable.TextViewImageView_text_image_view_text_show_title_value, false);

        textColor = a.getColor(R.styleable.TextViewImageView_text_image_view_text_color, Color.BLACK);
        numberColor = a.getColor(R.styleable.TextViewImageView_text_image_view_text_number_color, Color.BLACK);
        titleColor = a.getColor(R.styleable.TextViewImageView_text_image_view_text_title_color, Color.BLACK);

        textSize = a.getDimension(R.styleable.TextViewImageView_text_image_view_text_size, 14);
        titleSize = a.getDimension(R.styleable.TextViewImageView_text_image_view_text_title_size, 14);
        numberSize = a.getDimension(R.styleable.TextViewImageView_text_image_view_text_number_size, 14);


        a.recycle();

        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_text_image_item, this, true);

        ivLog = findViewById(R.id.iv_log);
        tvValue = findViewById(R.id.tv_value);
        tvNumber = findViewById(R.id.tv_number);
        tvTitle = findViewById(R.id.tv_title);

        if (tvValue != null) {
            setText(tvValue, strValue, textColor, textSize, true);
        }
        if (tvNumber != null) {
            setText(tvNumber, strNumber, textColor, numberSize, isShowNumber);
        }
        if (tvTitle != null) {
            setText(tvTitle, strTitle, titleColor, titleSize, isShowTitle);
        }
        ivLog.setImageDrawable(icon);

        ivLog.setVisibility(isShowIcon ? VISIBLE : GONE);


    }

    public void setText(TextView text, String value, int color, float size, boolean isShow) {
        text.setTextColor(color);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        text.setText(TextUtils.isEmpty(value) ? "" : value);
        text.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


}