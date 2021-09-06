package com.isport.brandapp.Home.view.circlebar;

import android.animation.ObjectAnimator;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;
import com.isport.brandapp.util.StringFomateUtil;

import bike.gymproject.viewlibray.BebasNeueTextView;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import com.isport.blelibrary.utils.CommonDateUtil;

public class SleepCircleLayout extends RelativeLayout {
    private Context mContext;
    SleepTimeView sleepVIew;
    DeepSleepTimeView viewDeepSleepView;
    BebasNeueTextView tvTime;
    private ImageView ivBg;

    String strTime;

    int currentType;
    int deepProgreesVaule = 80;
    int sleepProgreesVaule = 80;
    float goalValue = 100;

    int sumTime;
    int sleepTime;
    int deepSleepTime;

    public SleepCircleLayout(Context context) {
        this(context, null);
    }

    public SleepCircleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SleepCircleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initBase(context, attrs, defStyleAttr);
    }

    public void updateViewValue(DeviceBean deviceBean) {
        switch (deviceBean.currentType) {

        }
    }


    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
       /* TypedArray a = context.obtainStyledAttributes(attrs, bike.gymproject.viewlibray.R.styleable.ItemView,
                defStyleAttr, 0);*/
       /* titleText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_itemText);
        titleColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_itemTextColor, context.getResources()
        .getColor(bike.gymproject.viewlibray.R.color.common_white));

        contentText = a.getString(bike.gymproject.viewlibray.R.styleable.ItemView_contentText);
        contentColor = a.getColor(bike.gymproject.viewlibray.R.styleable.ItemView_contentTextColor, context
        .getResources().getColor(bike.gymproject.viewlibray.R.color.common_white));
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
        LayoutInflater.from(getContext()).inflate(R.layout.app_view_sleep_circlebar, this, true);
        sleepVIew = findViewById(R.id.objectAnimatorView);
        viewDeepSleepView = findViewById(R.id.view_sleep);
        tvTime = findViewById(R.id.tv_time);
        ivBg = findViewById(R.id.iv_bg);
    }

    private void setListener() {
        sleepVIew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lister != null) {
                    lister.onViewClickLister(currentType);
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        initData("--", "--");
    }

    public void setData(int deepTime, int duration, boolean is12H) {

        if (is12H) {
            goalValue = 12 * 60;
        } else {
            goalValue = 24 * 60;
        }
        deepProgreesVaule = deepTime;
        sleepProgreesVaule = duration;
        int hour;
        int minute;
        if (duration == 0) {
            hour = 0;
            minute = 0;

        } else {

            hour = duration / 60;
            minute = duration % 60;
        }
        //updateProgress(progress);
        initData(CommonDateUtil.formatTwoStr(hour), CommonDateUtil.formatTwoStr(minute));
        startAnimotion(is12H);
        if (is12H) {
            ivBg.setImageResource(R.drawable.bg_watch_12);
        } else {
            ivBg.setImageResource(R.drawable.bg_watch);
        }
    }


    private void initData(String hour, String min) {
        StringFomateUtil.formatText(StringFomateUtil.FormatType.Time, mContext, tvTime, ContextCompat.getColor
                (mContext, R.color.common_white), R.string.app_time_util, hour, min);
    }

    public void startAnimotion(boolean is12H) {
        /**
         * 不同的类型 开始值和目标值的设置
         * 单位的设置
         *
         */
        if (sleepVIew != null) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(sleepVIew, "progress", 0, ParsePreceter.parseProgress
                    (sleepProgreesVaule, goalValue, JkConfiguration.DeviceType.SLEEP));
            animator.setDuration(1000);
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.start();
        }
        if (viewDeepSleepView != null) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(viewDeepSleepView, "progress", 0, ParsePreceter
                    .parseProgress
                            (deepProgreesVaule, goalValue, currentType));
            animator.setDuration(1000);
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.start();
        }
    }

    public void updateProgress(int progress) {
        if (sleepVIew != null) {
            sleepVIew.setProgress(progress);
            sleepVIew.invalidate();
        }
    }


    ViewClickLister lister;

    public void setViewClickLister(ViewClickLister lister) {
        this.lister = lister;
    }

    public interface ViewClickLister {
        void onViewClickLister(int type);
    }


}
