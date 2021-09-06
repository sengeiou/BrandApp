package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import bike.gymproject.viewlibray.chart.CircleView;
import bike.gymproject.viewlibray.chart.HourMinuteData;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 李超凡
 * V 1.0.0
 * Create at 2019/2/25
 */
public class WatchHourMinuteView extends LinearLayout {

    TextView tvValue;
    TextView tvHour;
    TextView tvMin;
    TextView tv_hour_unitl;
    TextView tv_min_unitl;
    CircleView icon;


    int iconColor, titleColor, minColor, minUnitColor;
    String strValue;

    float valueSize;

    public WatchHourMinuteView(Context context) {
        this(context, null);
    }

    public WatchHourMinuteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchHourMinuteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WatchHourMinuteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WatchHourMinuteView,
                defStyleAttr, 0);

        iconColor = a.getColor(R.styleable.WatchHourMinuteView_iconColor, context.getResources().getColor(R.color.common_white));
        titleColor = a.getColor(R.styleable.WatchHourMinuteView_Watchtexttitlecolor, context.getResources().getColor(R.color.common_white));
        minColor = a.getColor(R.styleable.WatchHourMinuteView_WatchtextMincolor, context.getResources().getColor(R.color.common_white));
        minUnitColor = a.getColor(R.styleable.WatchHourMinuteView_WatchtextMinUnitlcolor, context.getResources().getColor(R.color.common_white));
        strValue = a.getString(R.styleable.WatchHourMinuteView_Watchtextvalue);
        valueSize = a.getDimensionPixelSize(R.styleable.WatchHourMinuteView_WatchTitleSize, 15);
        a.recycle();

        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_watch_hour_min_item, this, true);

        icon = findViewById(R.id.iv_hour_min);
        tvValue = findViewById(R.id.tv_value);
        tvHour = findViewById(R.id.tv_hour_value);
        tvMin = findViewById(R.id.tv_min_value);
        tv_hour_unitl = findViewById(R.id.tv_hour_unitl);
        tv_min_unitl = findViewById(R.id.tv_min_unitl);

        tvValue.setTextColor(titleColor);
        tvMin.setTextColor(minColor);
        tv_min_unitl.setTextColor(minUnitColor);
        if (!TextUtils.isEmpty(strValue)) {
            tvValue.setText(strValue);
        }

    }

    private void initData() {
        icon.setColor(iconColor);
    }


    public void setdata(String value1, String value2) {
        tvHour.setVisibility(GONE);
        tv_hour_unitl.setVisibility(GONE);
        tvMin.setVisibility(VISIBLE);
        tvMin.setText(value1);
        tv_min_unitl.setText(value2);
        tvMin.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        tv_min_unitl.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
    }

    public void setData(String value) {
        tvHour.setVisibility(GONE);
        tv_hour_unitl.setVisibility(GONE);
        tv_min_unitl.setVisibility(GONE);
        tvMin.setVisibility(GONE);
        tvValue.setText(value);
    }

    public void setData(HourMinuteData hourMinuteData) {
        icon.setColor(hourMinuteData.color);
        tvValue.setText(hourMinuteData.value);
        tvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueSize);
        if (TextUtils.isEmpty(hourMinuteData.hourVal) ? "".equals("- -") : hourMinuteData.hourVal.equals("- -")) {
            tvHour.setVisibility(GONE);
            tv_hour_unitl.setVisibility(GONE);
        } else {
            tvHour.setVisibility(VISIBLE);
            tv_hour_unitl.setVisibility(VISIBLE);
            tvHour.setText(hourMinuteData.hourVal);
        }
        if (!TextUtils.isEmpty(hourMinuteData.minUnitl)) {
            tv_min_unitl.setVisibility(GONE);
            tvMin.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            tv_min_unitl.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        tvMin.setText(hourMinuteData.minVal);
    }


    private boolean checkResId(boolean b) {
        if (b) {
            return true;
        }
        return false;
    }

}