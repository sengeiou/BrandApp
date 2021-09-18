package com.isport.brandapp.home.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.home.bean.ScacleBean;
import com.isport.brandapp.home.customview.BezierView;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;
import com.isport.brandapp.util.BmiUtil;
import com.isport.brandapp.util.DeviceTypeUtil;

import java.util.ArrayList;
import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.AppUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

/**
 * @创建者 bear
 * @创建时间 2019/3/28 19:06
 * @描述
 */
public class DataScaleHolder extends CustomHolder<String> {


    TextView tv_sport_time, tv_weight_value, tv_bmi_value, tv_bmi_standard;
    BezierView mBezierView;
    ImageView iv_empty;
    TextView tv_option;
    boolean isloadSuccess;


    ArrayList<ScacleBean> scacleBeans = new ArrayList<>();
    List<Float> pointList = new ArrayList<>();

    public DataScaleHolder(View itemView) {
        super(itemView);
    }

    public DataScaleHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }

    public DataScaleHolder(Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);
        isloadSuccess = false;
        Logger.myLog("getConectScale DataScaleHolder");
        mBezierView = itemView.findViewById(R.id.bezier);
        tv_sport_time = itemView.findViewById(R.id.tv_sport_time);
        tv_weight_value = itemView.findViewById(R.id.tv_weight_value);
        tv_bmi_value = itemView.findViewById(R.id.tv_bmi_value);
        tv_bmi_standard = itemView.findViewById(R.id.tv_bmi_standard);
        iv_empty = itemView.findViewById(R.id.iv_empty);
        tv_option = itemView.findViewById(R.id.tv_option);
        mBezierView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Logger.myLog("mBezierView 加载完成 mBezierView.getWidth" + mBezierView.getWidth() + "mBezierView.getHeight():" + mBezierView.getHeight());
                if (onScaleOnclickListenter != null) {
                    isloadSuccess = true;
                    onScaleOnclickListenter.onScaleViewSuccess();
                }

                mBezierView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        tv_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                if (onScaleOnclickListenter != null) {
                    onScaleOnclickListenter.onScaleStateListenter();
                }
            }
        });
        iv_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                if (onScaleItemClickListener != null) {
                    onScaleItemClickListener.onAddScaleItemClick();
                }
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewMultiClickUtil.onMultiClick(v)) {
                    return;
                }
                if (onScaleItemClickListener != null) {
                    onScaleItemClickListener.onScaleItemClick();
                }
            }
        });
        mBezierView.setOnItemBarClickListener(new BezierView.OnItemBarClickListener() {
            @Override
            public void onClick(int position) {
                if (scacleBeans != null && position >= 0 && scacleBeans.size() > position) {
                    ScacleBean scacleBean = scacleBeans.get(position);
                    if (tv_sport_time != null) {
                        tv_sport_time.setText(scacleBean.getStrDate());
                        tv_weight_value.setText(CommonDateUtil.formatOnePoint(scacleBean.getWeight()));
                        tv_bmi_value.setText(CommonDateUtil.formatTwoPoint(scacleBean.getBMI()));
                        // tv_bmi_standard.setText(Scale);

                        tv_bmi_standard.setVisibility(View.VISIBLE);
                        String strStander = scacleBean.getStander();
                        if (TextUtils.isEmpty(strStander)) {
                            strStander = "";
                        }
                        if (strStander.length() > 8) {
                            tv_bmi_standard.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                        } else {
                            tv_bmi_standard.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                        }
                        tv_bmi_standard.setText(strStander);
                        tv_bmi_standard.setBackgroundColor(Color.parseColor(scacleBean.getColor()));
                        calBmiStandard(scacleBean.getBMI());
                    }
                }
            }
        });
        enableOption();
        if (AppConfiguration.scacleBeansList.size() > 0) {
            ArrayList<ScacleBean> tempList = new ArrayList<>();
            tempList.clear();
            tempList.addAll(AppConfiguration.scacleBeansList);
            updateUI(tempList, 500);
        } else {
            setDef();
        }

    }


    public void calBmiStandard(float bmi) {
        int color = BmiUtil.getBmiCorrespondingColor(bmi);
        if (tv_bmi_standard != null) {
            tv_bmi_standard.setBackgroundColor(color);
        }
    }

    /**
     * 没有绑定的情况下
     **/
    public void setDef() {


        if (AppUtil.isZh(BaseApp.getApp())) {
            //LoadImageUtil.getInstance().load(context,R.drawable.bg_scale_emty_zh,iv_empty,0);
            iv_empty.setImageResource(R.drawable.bg_scale_emty_zh);
        } else {
            //LoadImageUtil.getInstance().load(context,R.drawable.bg_scale_emty_en,iv_empty,0);
            iv_empty.setImageResource(R.drawable.bg_scale_emty_en);
        }
        iv_empty.setVisibility(View.VISIBLE);
        mBezierView.setVisibility(View.INVISIBLE);
        tv_bmi_standard.setVisibility(View.INVISIBLE);
        tv_sport_time.setVisibility(View.INVISIBLE);
        tv_bmi_value.setText(UIUtils.getString(R.string.no_data));
        tv_weight_value.setText(UIUtils.getString(R.string.no_data));
    }


    public void setValue() {
        tv_option.setEnabled(true);
        tv_sport_time.setVisibility(View.VISIBLE);
        //tv_bmi_standard.setVisibility(View.VISIBLE);
        iv_empty.setVisibility(View.INVISIBLE);
        mBezierView.setVisibility(View.VISIBLE);
    }

    public synchronized void updateUI(ArrayList<ScacleBean> lists, int dedaly) {
        if (DeviceTypeUtil.isContainBody()) {
            tv_option.setEnabled(true);
        } else {
            tv_option.setEnabled(false);
        }
        if (lists == null || lists.size() == 0) {
            setDef();
            //显示初始状态
            return;
        }
        scacleBeans.clear();
        scacleBeans.addAll(lists);

        if (!DeviceTypeUtil.isContainBody()) {
            setDef();
            //显示初始状态
            return;
        }
        setValue();
        pointList.clear();
        int len = scacleBeans.size();
        for (int i = 0; i < len; i++) {
            pointList.add(scacleBeans.get(i).getWeight());
        }

        if (isloadSuccess) {
        //    mBezierView.setPointListFloat(pointList);
        }
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBezierView.setPointListFloat(pointList);
            }
        }, dedaly);*/




       /* itemViews.setShowArrow(R.drawable.icon_green_left_arrow);
        itemViews.setRemindIcon(R.drawable.icon_scale);
        itemViews.setTitleText(R.string.body_weight);
        itemViews.setContentText(App.isScale() && AppConfiguration.isConnected ? R.string.weigh : R.string.unconnected_device);
        String compareWeightStr;
        boolean isUp;
        if (scaleMainData == null) {
            isUp = true;
            compareWeightStr = UIUtils.getString(R.string.no_data);
        } else {
            if (scaleMainData.getCompareWeight() == null) {
                isUp = true;
                compareWeightStr = UIUtils.getString(R.string.no_data);
            } else {
                if (scaleMainData.getCompareWeight().contains("-")) {
                    compareWeightStr = scaleMainData.getCompareWeight().replace("-", "");
                    isUp = false;
                } else {
                    isUp = true;
                    compareWeightStr = scaleMainData.getCompareWeight();
                }
            }
        }
        deviceItemView.setResult("", StringUtil.isBlank(compareWeightStr) ? UIUtils.getString(R.string.no_data) : compareWeightStr, R.string.kg, isUp ? R.drawable.icon_up : R.drawable.icon_down);
        deviceItemView.setValueText(StringUtil.isBlank(scaleMainData.getWeight()) ? UIUtils.getString(R.string.no_data) : scaleMainData.getWeight(), R.string.kg);
        deviceItemView.setTime(scaleMainData.getLastSyncTime() == 0 ? "" : TimeUtils.getTimeByYYMMDDHHMM
                (scaleMainData.getLastSyncTime()));*/
    }

    public void enableOption() {

        if (tv_option != null) {
            if (AppConfiguration.deviceBeanList != null && AppConfiguration.deviceMainBeanList.containsKey(JkConfiguration.DeviceType.BODYFAT) && AppUtil.isOpenBle()) {
                tv_option.setEnabled(true);
            } else {
                tv_option.setEnabled(false);
            }
        }

       /* Logger.myLog("enableOption:" + isOption);
        tv_option.setEnabled(isOption);*/
    }

    OnScaleItemClickListener onScaleItemClickListener;
    OnScaleOnclickListenter onScaleOnclickListenter;

    public void setScaleItemClickListener(OnScaleItemClickListener onSportItemClickListener, OnScaleOnclickListenter onSportOnclickListenter) {
        this.onScaleItemClickListener = onSportItemClickListener;
        this.onScaleOnclickListenter = onSportOnclickListenter;
    }


    public interface OnScaleItemClickListener {
        void onScaleItemClick();
        void onAddScaleItemClick();
    }


    public interface OnScaleOnclickListenter {
        void onScaleStateListenter();

        void onScaleViewSuccess();
    }

}
