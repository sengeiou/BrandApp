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
import android.widget.RelativeLayout;
import android.widget.TextView;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class RecView extends LinearLayout {

    TextView tvCurrentVaule;
    TextView tvDate;
    Context context;

    public RecView(Context context) {
        this(context, null);
    }

    public RecView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RecView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemView,
                defStyleAttr, 0);
        this.context = context;
        a.recycle();

        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_rec, this, true);

        tvCurrentVaule = (TextView) findViewById(R.id.tv_current_result);
        tvDate = findViewById(R.id.tv_date);
    }


    public void isShowDate(boolean flag) {
        if (tvDate == null)
            return;
        tvDate.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    public void setResultTextView(int color, float heigt) {
        if (tvCurrentVaule == null)
            return;
        //tvCurrentVaule.setHeight(500);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) tvCurrentVaule.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20

        linearParams.height = (int) heigt;// 控件的宽强制设成30

        tvCurrentVaule.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        tvCurrentVaule.setBackgroundColor(context.getColor(color));

    }
}