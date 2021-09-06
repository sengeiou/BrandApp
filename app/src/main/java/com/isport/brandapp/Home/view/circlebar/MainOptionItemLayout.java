package com.isport.brandapp.Home.view.circlebar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.brandapp.R;

public class MainOptionItemLayout extends RelativeLayout {

    TextView tvStatus;
    TextView tvOption;

    String currentType;

    public interface Type {
        String ISDISCONNECT = "ISDISCONNECT";
        String NEEDADD = "NEEDADD";
        String WATCH = "WATCH";
        String SCALE = "SCALE";
        String SLEEP = "SLEEP";
    }

    public MainOptionItemLayout(Context context) {
        this(context, null);
    }

    public MainOptionItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainOptionItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }


    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
       /* TypedArray a = context.obtainStyledAttributes(attrs, bike.gymproject.viewlibray.R.styleable.ItemView,
                defStyleAttr, 0);*/
       /* titleText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_itemText);
        titleColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_itemTextColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));

        contentText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_contentText);
        contentColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_contentTextColor, context.getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
        showRemind = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showRemind, false);
        showArrow = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showArrow, true);
        showCheckModel = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_showCheckModel, false);
        isChecked = a.getBoolean(bike.gymproject.viewlibray.R.styleable.ItemView_isChecked, false);
        remindIcon = a.getDrawable(bike.gymproject.viewlibray.R.styleable.ItemView_remindIcon);*/
        //a.recycle();

        initView();
        setListener();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.app_main_page_item_layout, this, true);

        tvStatus = (TextView) findViewById(R.id.tv_status);
        tvOption = (TextView) findViewById(R.id.tv_option);

        tvOption.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOptionListion(currentType);
                }
            }
        });
    }

    private void setListener() {


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initData() {
       /* if (!TextUtils.isEmpty(titleText)) {
            titleTextView.setText(titleText);
        }

        if (!TextUtils.isEmpty(contentText)) {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(contentText);
        }

        setShowCheckModel(showCheckModel);
        setShowRemind(showRemind);
        setChecked(isChecked);
        setShowArrow(showArrow);
        setRemindIcon(remindIcon);*/
    }

    OnItemOptionListener listener;

    public void setItemOptionListenr(OnItemOptionListener listenr) {
        this.listener = listenr;
    }

    public interface OnItemOptionListener {
        void onOptionListion(String type);
    }

    public void setLeftIcon(int resId, String str, String currentStatus, String tvOptinValue) {
        if ((resId >>> 24) < 2) {
            return;
        }
        currentType = currentStatus;
        //tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(str);
        tvOption.setText(tvOptinValue);
        tvStatus.setCompoundDrawables(getDrawable(resId), null, null, null);
    }


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
