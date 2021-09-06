package com.isport.brandapp.Home.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.brandapp.R;

public class MainHeadLayout extends RelativeLayout {

    ImageView iv_device_icon_type;
    TextView tv_option, tv_devcie_state;
    ProgressBar pro_deviceing;
    RelativeLayout layout_devcie_type;
    ImageView iv_back;


    public static final String TAG_OPENBLE = "openBle", TAG_CONNECT = "connectDevice", TAG_ADD = "addDevcie";

    public MainHeadLayout(Context context) {
        this(context, null);
    }

    public MainHeadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainHeadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBase(context, attrs, defStyleAttr);
    }


    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        initView();
        setListener();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.app_main_head, this, true);
        tv_option = findViewById(R.id.tv_option);
        iv_back = findViewById(R.id.iv_back);
        tv_devcie_state = findViewById(R.id.tv_devcie_state);
        pro_deviceing = findViewById(R.id.pro_deviceing);
        layout_devcie_type = findViewById(R.id.layout_devcie_type);
        iv_device_icon_type = findViewById(R.id.iv_device_icon_type);
    }


    /**
     * 未打开蓝牙
     * 未连接设备
     * 没有添加设备显示添加设备
     */
    public void showOptionButton(boolean isShow, String showContent, String tage, String strDevcieState) {
        if (tv_option != null) {
            tv_option.setVisibility(isShow ? VISIBLE : GONE);
            tv_option.setText(showContent);
            tv_option.setTag(tage);
        }
        if (tv_devcie_state != null) {
            tv_devcie_state.setText(strDevcieState);
        }
    }

    /**
     * 绑定两个以上的设备可以显示设备切换的按钮
     */


    public void showOptionButton(boolean isShow, String strDevcieState) {
        if (tv_option != null) {
            tv_option.setVisibility(isShow ? VISIBLE : GONE);
        }
        if (tv_devcie_state != null) {
            tv_devcie_state.setText(strDevcieState);
        }
    }


    /**
     * @param bgRes 设置当前连接的设备类型
     */
    public void setIconDeviceIcon(int bgRes) {

        if ((bgRes >>> 24) < 2) {
            return;
        }
        if (iv_device_icon_type != null) {
            iv_device_icon_type.setImageResource(bgRes);
        }

    }

    /**
     * @param alPha 1 or 0.5
     */

    public void setIconDeviceAlp(float alPha) {
        if (iv_device_icon_type != null) {
            iv_device_icon_type.setAlpha(alPha);
        }
    }

    /**
     * @param isShowProgressBar 连接中和同步中的时候显示progress
     */
    public void showProgressBar(boolean isShowProgressBar) {

        if (isShowProgressBar) {
            layout_devcie_type.setBackgroundResource(R.drawable.shape_main_head_gray_bg);
        } else {
            layout_devcie_type.setBackgroundResource(R.drawable.shape_main_head_white_bg);
        }
        pro_deviceing.setVisibility(isShowProgressBar ? VISIBLE : GONE);
    }

    private void setListener() {
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lister != null) {
                    lister.onMainBack();
                }
            }
        });
        tv_option.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lister != null) {
                    String strTag = tv_option.getTag().toString();
                    lister.onViewOptionClikelister(strTag);
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
        //startAni();
    }


    private void initData() {
    }


    ViewMainHeadClickLister lister;

    public void setViewClickLister(ViewMainHeadClickLister lister) {
        this.lister = lister;
    }

    public interface ViewMainHeadClickLister {

        void onViewOptionClikelister(String type);

        void onMainBack();
    }


}
