package bike.gymproject.viewlibray;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
public class DeviceItemView extends LinearLayout {


    private TextView tvValue, tvResult, tvTime, tvResultTips;

    private int valueColor, resultColor, timeColor, resultTipsColor;

    private float valueSize, resultSize, timeSize, resultTipsSize;

    private String strValue, strResult, strTime, strResultTips, strValueUnit, strResultUnit;


    private ImageView ivResult;


    private Drawable remindIcon;

    public DeviceItemView(Context context) {
        this(context, null);
    }

    public DeviceItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeviceItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DeviceItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    Context mContex;

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        mContex = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DeviceItemView,
                defStyleAttr, 0);
        strValue = a.getString(R.styleable.DeviceItemView_deviceValueText);
        strResult = a.getString(R.styleable.DeviceItemView_deviceResultText);
        strTime = a.getString(R.styleable.DeviceItemView_deviceTimeText);
        strResultTips = a.getString(R.styleable.DeviceItemView_deviceResultText);
        strValueUnit = a.getString(R.styleable.DeviceItemView_deviceValueUnitText);
        strResultUnit = a.getString(R.styleable.DeviceItemView_deviceResultUnitText);


        valueColor = a.getColor(R.styleable.DeviceItemView_deviceValueTextColor, context.getResources().getColor(R.color.common_white));
        timeColor = a.getColor(R.styleable.DeviceItemView_deviceTimeTextColor, context.getResources().getColor(R.color.common_white));
        resultColor = a.getColor(R.styleable.DeviceItemView_deviceResultTextColor, context.getResources().getColor(R.color.common_white));
        resultTipsColor = a.getColor(R.styleable.DeviceItemView_deviceResultTipsTextColor, context.getResources().getColor(R.color.common_white));

        valueSize = a.getDimension(R.styleable.DeviceItemView_deviceValueTextSize, 14);
        timeSize = a.getDimension(R.styleable.DeviceItemView_deviceTimeTextSize, 14);
        resultSize = a.getDimension(R.styleable.DeviceItemView_deviceResultTextSize, 14);
        resultTipsSize = a.getDimension(R.styleable.DeviceItemView_deviceResultTipsTextSize, 14);


        remindIcon = a.getDrawable(R.styleable.DeviceItemView_deviceResultIcon);
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
        LayoutInflater.from(getContext()).inflate(R.layout.view_device_item, this, true);

        tvValue = (TextView) findViewById(R.id.tv_value);
        tvResult = (TextView) findViewById(R.id.tv_result);
        tvTime = findViewById(R.id.tv_time);
        ivResult = findViewById(R.id.iv_result);
        tvResultTips = findViewById(R.id.tv_resutl_tips);
        //remindImageView = (RoundImageView) findViewById(R.id.view_car_remind_red);

    }

    private void initData() {
        if (tvValue != null) {
            tvValue.setTextColor(valueColor);
        }
        if (tvResult != null) {
            tvResult.setTextColor(resultColor);
        }
        if (tvTime != null) {
            tvTime.setTextColor(timeColor);
        }
        if (tvResultTips != null) {
            tvResultTips.setTextColor(resultTipsColor);
        }
        // setValueText(strValue, strValueUnit);
        //setTime(strTime);
        //setResult(strResult, strResultUnit, 0);
        //setResultTips(strResultTips);
    }

    private void setListener() {
    }


    public void setValueSleepTime(String hour, String min, int Res, TextView textView) {
        if (textView == null) {
            return;
        }


        // float scale = Float.valueOf(args[1]);
			/*int mins = Integer.valueOf(minutes);
			String hour = String.valueOf(mins / 60);
			String minute = String.valueOf(mins % 60);*/
        String str = mContex.getString(Res, hour, min);
        SpannableString span = new SpannableString(str);
        span.setSpan(new RelativeSizeSpan(0.5f), hour.length(), str.lastIndexOf(min),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(0.5f), str.lastIndexOf(min) + min.length(), str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);

    }

    public void setValueSleepTime(String hour, String min, int Res) {
        if (tvValue == null) {
            return;
        }


        // float scale = Float.valueOf(args[1]);
			/*int mins = Integer.valueOf(minutes);
			String hour = String.valueOf(mins / 60);
			String minute = String.valueOf(mins % 60);*/
        String str = mContex.getString(Res, hour, min);
        SpannableString span = new SpannableString(str);
        span.setSpan(new RelativeSizeSpan(0.5f), hour.length(), str.lastIndexOf(min),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(0.5f), str.lastIndexOf(min) + min.length(), str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvValue.setText(span);

    }

    public void setValueText(String text, int unit) {

        if (tvValue == null) {
            return;
        }
        this.strValue = text;
        if (unit == 0) {
            strValueUnit = "";
        } else {
            this.strValueUnit = mContex.getString(unit);
        }
        if (TextUtils.isEmpty(strValueUnit)) {
            tvValue.setText(TextUtils.isEmpty(text) ? "" : text);
        } else {
            format(tvValue, text, unit, 0);
        }


    }

    public void format(TextView textView, String text, int unit, int resId) {
        String temp = text;
        float scale = 0.45f;
        String str = text + "   " + mContex.getString(unit);
        SpannableString span = new SpannableString(str);
        span.setSpan(new RelativeSizeSpan(scale), str.indexOf(temp) + temp.length(), str.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //span.setSpan(new BackgroundColorSpan(Color.RED), str.indexOf(temp) + temp.length(), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  //红色高亮
        if (resId != 0) {
            //textView.setCompoundDrawables(getDrawable(resId), null, null, null);
        }
        textView.setText(span);
    }


    public void setResult(String hourValue, String valueMin, int unitResId, int resId) {
        if (tvResult == null) {
            return;
        }

        if (TextUtils.isEmpty(hourValue)) {
            format(tvResult, valueMin, unitResId, resId);
        } else {
            setValueSleepTime(hourValue, valueMin, unitResId, tvResult);
        }


        strResult = valueMin;
        strResultUnit = mContex.getString(unitResId);

        ivResult.setImageResource(resId);

    }

    public void setTime(String text) {
        if (tvTime == null) {
            return;
        }
        this.strTime = text;
        tvTime.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setResultTips(String text) {
        if (tvResultTips == null) {

            return;
        }
        this.strResultTips = text;
        tvResultTips.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void setResultTips(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setResultTips(getContext().getString(resId));
    }


    public void setRemindIcon(int resId) {
        //titleTextView.setCompoundDrawables(getDrawable(resId), null, null, null);
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


}