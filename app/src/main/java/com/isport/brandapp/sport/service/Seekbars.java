package com.isport.brandapp.sport.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.device.sleep.TimeUtil;
import com.isport.brandapp.sport.bean.SportSettingBean;

import bike.gymproject.viewlibray.DptopxUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;


/*
 * 通用Item控件封装 包含提示点/复选框等
 * classes : view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class Seekbars extends LinearLayout {


    int type;//是心率还是配速

    SportSettingBean settingBean;

    SeekBar seekBar;
    //ImageView ivPro;
    TextView tvValue;
    TextView tvTips;
    TextView tv_hr_remid, tv_pace_remind;
    TextView tvHrUnit, tvPaceUnit, tvRange;

    private String contentText;


    Context context;

    public Seekbars(Context context) {
        this(context, null);
    }

    public Seekbars(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Seekbars(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Seekbars(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.seekbars,
                defStyleAttr, 0);
        type = a.getInt(R.styleable.seekbars_seekbar_type, 0);
        strTips = a.getString(R.styleable.seekbars_seekbar_tips);
        strUnit = a.getString(R.styleable.seekbars_seekbar_unit);
        a.recycle();

        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    int width = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (left == 0) {
            left = (tvValue.getWidth() / 2);
        }
        tvValue.layout(left - tvValue.getWidth() / 2, tvValue.getTop(), left + tvValue.getWidth() / 2, tvValue.getBottom());

    }

    String strTips, strUnit;

    private void initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.view_seek_bar, this, true);
        seekBar = findViewById(R.id.seekbar_hr);
        tvValue = findViewById(R.id.tv_value);
        //ivPro = findViewById(R.id.iv_pro_hr);
        tvTips = findViewById(R.id.tv_tips);
        tvHrUnit = findViewById(R.id.tv_hr_unit);
        tv_hr_remid = findViewById(R.id.tv_hr_remid);
        tv_pace_remind = findViewById(R.id.tv_pace_remind);
        tvPaceUnit = findViewById(R.id.tv_pace_unit);
        tvRange = findViewById(R.id.tv_range);

    }

    int currentValue;
    float viewHrWidth;
    int left, right, top, bottom;

    private void initData() {


        if (type == 0) {
            tvHrUnit.setVisibility(GONE);
            tvPaceUnit.setVisibility(VISIBLE);
            tv_pace_remind.setVisibility(VISIBLE);
            tv_hr_remid.setVisibility(INVISIBLE);
            tvRange.setVisibility(INVISIBLE);
        } else {
            tv_pace_remind.setVisibility(INVISIBLE);
            tv_hr_remid.setVisibility(VISIBLE);
            tvHrUnit.setVisibility(VISIBLE);
            tvPaceUnit.setVisibility(GONE);
            tvRange.setVisibility(VISIBLE);
        }

        if (tvTips != null && !TextUtils.isEmpty(strTips)) {
            tvTips.setText(strTips);
        }
        if (tvHrUnit != null && !TextUtils.isEmpty(strUnit)) {
            tvHrUnit.setText(strUnit);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {


                if (type == 0) {
                    currentValue = (int) ((settingBean.paceMaxValue - settingBean.paceMinValue) / 100.0 * progress);
                    currentValue = currentValue + settingBean.paceMinValue;
                } else {
                    currentValue = (int) ((settingBean.hrMaxValue - settingBean.hrMinValue) / 100.0 * progress);
                    currentValue = currentValue + settingBean.hrMinValue;
                }
                if (viewHrWidth == 0) {
                    width = getWidth() - DptopxUtil.dp2px(30, context);
                    viewHrWidth = width * 1.0f / 100;
                }

                if (isFirst) {
                    if (type == 0) {
                        if (sportType == JkConfiguration.SportType.sportBike) {
                            tvValue.setText(currentValue + "");
                        } else {
                            tvValue.setText(TimeUtil.getTimerFormatedStringsPace(currentValue));
                        }
                        //tvValue.setText(TimeUtil.getTimerFormatedStringsPace(settingBean.currentPaceValue));
                    } else {
                        tvValue.setText(settingBean.currentHrValue + "");
                    }
                } else {
                    if (type == 0) {
                        if (sportType == JkConfiguration.SportType.sportBike) {
                            tvValue.setText(currentValue + "");
                        } else {
                            tvValue.setText(TimeUtil.getTimerFormatedStringsPace(currentValue));
                        }
                    } else {
                        tvValue.setText(currentValue + "");
                    }
                }


                //进度是从-50~50的,但是seekbar.getmin()有限制,所以这里用0~100 -50;
                int text = progress - 50;
                //设置文本显示
                //  textView.setText(String.valueOf(text));

                //获取文本宽度
                float textWidth = tvValue.getWidth();

                //获取seekbar最左端的x位置
                float left = seekBar.getLeft();

                //进度条的刻度值
                float max = Math.abs(seekBar.getMax());

                //这不叫thumb的宽度,叫seekbar距左边宽度,实验了一下，seekbar 不是顶格的，两头都存在一定空间，所以xml 需要用paddingStart 和 paddingEnd 来确定具体空了多少值,我这里设置15dp;
                float thumb = DisplayUtils.dip2px(context, 15f);

                //每移动1个单位，text应该变化的距离 = (seekBar的宽度 - 两头空的空间) / 总的progress长度
                float average = (((float) seekBar.getWidth()) - 2 * thumb) / max;

                //int to float
                float currentProgress = progress;

                //textview 应该所处的位置 = seekbar最左端 + seekbar左端空的空间 + 当前progress应该加的长度 - textview宽度的一半(保持居中作用)
                float pox = left - textWidth / 2 + thumb + average * currentProgress;
                tvValue.setX(pox);

                //  left = (int) (viewHrWidth * (i)) - ivPro.getWidth() / 2 + (int) (seekBar.getThumb().getIntrinsicWidth() / 2.5f);

                // left = (int) viewHrWidth * (progress);
                /*if (left < tvValue.getWidth() / 2) {
                    left = (int) (tvValue.getWidth() / 2);
                }
                if (left + tvValue.getWidth() > width) {
                    left = (int) (left - (left + tvValue.getWidth() - width) + tvValue.getWidth() / 2);
                }*/


                Log.e("setOnSeekBar", "left=" + left + ",currentValue=" + currentValue + "tvValue" + tvValue.getText().toString());

                if (onlistenCurrentValue != null && !isFirst) {
                    onlistenCurrentValue.backeCurrentValue(currentValue, type);
                }
                if (isFirst) {
                    isFirst = false;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void setListener() {

    }

    OnListenCurrentValue onlistenCurrentValue;

    public void setCurrentValue(OnListenCurrentValue onlistenCurrentValue) {
        this.onlistenCurrentValue = onlistenCurrentValue;
    }

    public interface OnListenCurrentValue {
        void backeCurrentValue(Integer value, int currenttype);
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


    private int sportType;

    boolean isFirst = true;

    public void setProgess(int progess) {
        seekBar.setProgress(progess);

    }

    public void setTips(String tips) {
        if (tvRange != null) {
            tvRange.setVisibility(VISIBLE);
            tvRange.setText(tips);
        }
    }

    public void setTips() {
        if (tvTips != null) {
            tvTips.setText(UIUtils.getString(R.string.speed_notice));
        }
    }


    public void setSettingBean(SportSettingBean settingBean, int sportType) {
        this.settingBean = settingBean;
        this.sportType = sportType;

       /* //当前值设置progress的值
        int prgess = settingBean.currentHrValue / (int) ((settingBean.paceMaxValue - settingBean.paceMinValue) / 100.0);

        seekBar.setProgress(prgess);*/

        if (type == 0) {
            if (tvPaceUnit != null) {
                switch (sportType) {
                    case JkConfiguration.SportType.sportIndoor:
                        tvPaceUnit.setText(UIUtils.getString(R.string.nute_per_km));
                        break;
                    case JkConfiguration.SportType.sportOutRuning:
                        tvPaceUnit.setText(UIUtils.getString(R.string.nute_per_km));
                        break;
                    case JkConfiguration.SportType.sportBike:
                        tvPaceUnit.setText(UIUtils.getString(R.string.unit_speed));
                        break;
                    case JkConfiguration.SportType.sportWalk:
                        tvPaceUnit.setText(UIUtils.getString(R.string.nute_per_km));
                        break;
                }
            }
        } else {
            if (tvRange != null) {
                tvRange.setText(settingBean.tips);
            }
        }


        //设置当前的显示值

    }

    public void setNorClick(boolean isClick) {
        if (isClick) {
            // ivPro.setImageResource(R.drawable.icon_progress_tips);
            Rect bounds = seekBar.getProgressDrawable().getBounds();
            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.po_seekbar));
            seekBar.getProgressDrawable().setBounds(bounds);
            /*seekBar.getThumb().setColorFilter(MyApplication.getContext().getResources().getColor(R.color.red),
                    PorterDuff.Mode.SRC_ATOP);*/

        } else {
            // ivPro.setImageResource(R.drawable.icon_progres_tips_nor);
            Rect bounds = seekBar.getProgressDrawable().getBounds();
            seekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.po_seekbar_nor));
            seekBar.getProgressDrawable().setBounds(bounds);
        }
    }

}