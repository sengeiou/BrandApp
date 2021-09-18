package com.isport.brandapp.home.view.circlebar;

import android.animation.ObjectAnimator;
import android.content.Context;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;

import bike.gymproject.viewlibray.BebasNeueTextView;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import com.isport.blelibrary.utils.CommonDateUtil;

public class BandCirclebarAnimatorLayout extends RelativeLayout {
    private Context mContext;
    BandView          view;
    BebasNeueTextView tvValue1;
    TextView          tvUnit, tvTargetGoalRate;


    int   currentType;
    int   progreesVaule = 0;
    float goalValue     = 100;

    public BandCirclebarAnimatorLayout(Context context) {
        this(context, null);
    }

    public BandCirclebarAnimatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BandCirclebarAnimatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void showTargetGoalRate(boolean show) {
        tvTargetGoalRate.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.app_view_band_circlebar, this, true);
        view = findViewById(R.id.objectAnimatorView);
        tvValue1 = findViewById(R.id.tv_value);
        tvUnit = findViewById(R.id.tv_util);
        tvTargetGoalRate = findViewById(R.id.tv_target_goal_rate);

    }

    private void setListener() {
        view.setOnClickListener(new OnClickListener() {
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
        initData();
        startAnimotion();
    }

    private void initData() {

    }

    public void startAnimotion() {
        /**
         * 不同的类型 开始值和目标值的设置
         * 单位的设置
         *
         */
        //progreesVaule=5000;
        if (view != null) {
            view.setCurrentType(currentType);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "progress", view.getProgress(), ParsePreceter.parseProgress
                    (progreesVaule, goalValue, currentType));
            animator.setDuration(1000);
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.start();
        }
    }

    public void updateProgress(int progress) {
        if (view != null) {
            view.setProgress(90);
            view.invalidate();
        }
    }


    public void setGoalValue(int step, int currentType, int targetStep) {
        this.currentType = currentType;
        this.progreesVaule = step;
        this.goalValue = targetStep;

        int rate;
        if (step == 0) {
            rate = 0;
        } else {
            rate = Math.round(step * 1.0f / targetStep * 100);
        }
        if (rate == 0) {
            rate = 1;
        }
        if (rate > 20000) {
            rate = 0;
        }
        if (step == 0) {
            tvValue1.setText("0");
            tvUnit.setText("%");
        } else {
            startAnimotion();
            tvValue1.setText(rate + "");
            tvUnit.setText("%");
        }
    }

    public void setGoalValue(float value, int currentType, String value1) {
        if (currentType == JkConfiguration.DeviceType.WATCH_W516) {
            goalValue = JkConfiguration.WATCH_GOAL;
        } else if (currentType == JkConfiguration.DeviceType.SLEEP) {
            goalValue = JkConfiguration.SLEEP_GOAL;
        } else {
            goalValue = JkConfiguration.BODYFAT_GOAL;
        }
        this.currentType = currentType;
        this.progreesVaule = (int) value;
        this.goalValue = goalValue;
        // tvValue1.setText(CommonDateUtil.formatInterger(value));
        String rate = "80";
        startAnimotion();
        tvValue1.setText(CommonDateUtil.formatInterger(value));
        tvUnit.setText(mContext.getResources().getString(R.string.unit_steps));
        //StringFomateUtil.formatText(StringFomateUtil.FormatType.Alone, mContext, tvValue1, ContextCompat.getColor(mContext, R.color.common_view_color), R.string.app_band_step_unit, CommonDateUtil.formatInterger(value), "0.5");
    }


    ViewClickLister lister;

    public void setViewClickLister(ViewClickLister lister) {
        this.lister = lister;
    }

    public interface ViewClickLister {
        void onViewClickLister(int type);
    }


}
