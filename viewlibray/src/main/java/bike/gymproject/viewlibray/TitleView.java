package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
public class TitleView extends LinearLayout {

    TextView tvValue;
    ImageView ivLog;
    RelativeLayout bgLayout;

    boolean isShowIcon;

    int[] intsColor = new int[2];
    float[] radios = new float[8];

    int startColor, endColor;
    float leftTop, leftBottom, rightTop, rightBottom;

    private Drawable icon;
    private String strValue;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView,
                defStyleAttr, 0);

        leftTop = a.getDimension(R.styleable.TitleView_title_radio_left_top, 0);
        leftBottom = a.getDimension(R.styleable.TitleView_title_radio_left_bottom, 0);
        rightTop = a.getDimension(R.styleable.TitleView_title_radio_right_top, 0);
        rightBottom = a.getDimension(R.styleable.TitleView_title_radio_right_bottom, 0);
        startColor = a.getColor(R.styleable.TitleView_title_bg_start_color, Color.WHITE);
        endColor = a.getColor(R.styleable.TitleView_title_bg_end_color, Color.WHITE);
        icon = a.getDrawable(R.styleable.TitleView_title_icon);
        strValue = a.getString(R.styleable.TitleView_title_value);
        isShowIcon = a.getBoolean(R.styleable.TitleView_title_show_icon, true);

        a.recycle();

        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title_item, this, true);

        ivLog = findViewById(R.id.iv_log);
        tvValue = findViewById(R.id.tv_value);
        bgLayout = findViewById(R.id.layout_bg);

        if (tvValue != null) {
            tvValue.setText(TextUtils.isEmpty(strValue) ? "" : strValue);
        }

        intsColor[0] = endColor;
        intsColor[1] = startColor;

        //// 设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
        radios[0] = leftTop;
        radios[1] = leftTop;
        radios[2] = rightTop;
        radios[3] = rightTop;
        radios[4] = rightBottom;
        radios[5] = rightBottom;
        radios[6] = leftBottom;
        radios[7] = leftBottom;


        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(radios);

        gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gradientDrawable.setColors(intsColor);
        if (bgLayout != null) {
            bgLayout.setBackground(gradientDrawable);
        }
        ivLog.setImageDrawable(icon);

        ivLog.setVisibility(isShowIcon ? VISIBLE : GONE);


    }


}