package com.isport.brandapp.Home.adpter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;
import brandapp.isport.com.basicres.commonrecyclerview.adapter.BaseCommonRefreshRecyclerAdapter;
import brandapp.isport.com.basicres.commonutil.UIUtils;

/**
 * Created by huashao on 2017/11/13.
 */
public class AdapterMineDeviceList extends BaseCommonRefreshRecyclerAdapter<DeviceBean, AdapterMineDeviceList
        .ViewHolder> {
    public AdapterMineDeviceList(Context context) {
        super(context);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.app_fragment_mine_item;
    }

    @Override
    protected ViewHolder bindBaseViewHolder(View contentView) {
        return new ViewHolder(contentView);
    }

    @Override
    protected void initData(ViewHolder viewHolder, int position, DeviceBean item) {

    }


    @Override
    protected void initEvent(ViewHolder viewHolder, int position, DeviceBean item) {

        //DeviceBaseBean bean = DeviceDataParseUtil.getDeviceTypeBean(item.currentType, item);

        if (getData().size() > 0 && getData().size() - 1 == position) {
            viewHolder.linebottem.setVisibility(View.GONE);
        } else {
            viewHolder.linebottem.setVisibility(View.VISIBLE);
        }
        int type = item.currentType;
        boolean connectState = item.connectState;
        int battery = item.battery;
        if (type == JkConfiguration.DeviceType.SLEEP) {
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_sleep);
            if (connectState) {
                viewHolder.ivTypeLog.setAlpha(1.0f);
            } else {
                viewHolder.ivTypeLog.setAlpha(0.5f);
            }
            viewHolder.tvTypeName.setText(context.getString(R.string.user_sleep_belt, item.deviceName));
//            viewHolder.tvTypeName.setText(item.deviceName);
        } else if (type == JkConfiguration.DeviceType.WATCH_W516) {
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_516);
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            setIconAla(connectState, viewHolder.ivTypeLog);
//            viewHolder.tvTypeName.setText(item.deviceName);
        } else if (type == JkConfiguration.DeviceType.BODYFAT) {
            viewHolder.tvBattery.setVisibility(View.GONE);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_mz);
            setIconAla(connectState, viewHolder.ivTypeLog);
//            viewHolder.tvTypeName.setText(item.deviceName);
        } else if (type == JkConfiguration.DeviceType.BRAND_W311) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_311);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Brand_W520) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_520);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.BRAND_W307J) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_307j);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Brand_W811) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w811);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Brand_W814) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w814);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W812) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w812);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W813) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w813);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W819) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w819);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W910) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w910);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W556) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w526);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W817) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w817);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W557) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w557);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W812B) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w812b);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W560) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w560);
            setIconAla(connectState, viewHolder.ivTypeLog);
        } else if (type == JkConfiguration.DeviceType.Watch_W560B) {
            setWatchBattery(connectState, battery, viewHolder.tvBattery);
            viewHolder.ivTypeLog.setImageResource(R.drawable.icon_mine_w560);
            setIconAla(connectState, viewHolder.ivTypeLog);
        }
        viewHolder.tvTypeName.setText(item.deviceName);
        viewHolder.tvTypeUnit.setText(connectState ? UIUtils.getString(R.string.connected) : UIUtils.getString(R.string.disConnect));
        viewHolder.tvTypeName.setTextColor(connectState ? context.getResources().getColor(R.color.common_white) :
                context.getResources().getColor(R.color.common_tips_color));
       /* viewHolder.ivLeftArrow.setImageResource(connectState ? R.drawable.icon_arrow_left : R.drawable
                .icon_gray_arrow_left);*/
    }


    public void setIconAla(boolean isConnect, ImageView imageView) {
        if (isConnect) {
            imageView.setAlpha(1.0f);
        } else {
            imageView.setAlpha(0.5f);
        }

    }


    private void setWatchBattery(boolean isConn, int watchBattery, TextView tvWatchBetteryCount) {
        if (isConn) {
            tvWatchBetteryCount.setVisibility(View.VISIBLE);
        } else {
            tvWatchBetteryCount.setVisibility(View.GONE);
            return;
        }
        if (watchBattery == -1) {
            tvWatchBetteryCount.setText(UIUtils.getString(R.string.no_data));
        } else {
            tvWatchBetteryCount.setText(watchBattery + "%");
        }
        if (watchBattery < 10) {
            tvWatchBetteryCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mine_batter0, 0, 0, 0);
        } else if (watchBattery >= 10 && watchBattery <= 20) {
            tvWatchBetteryCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mine_batter20, 0, 0, 0);
        } else if (watchBattery > 20 && watchBattery <= 50) {
            tvWatchBetteryCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mine_batter40, 0, 0, 0);
        } else if (watchBattery > 50 && watchBattery <= 70) {
            tvWatchBetteryCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mine_batter60, 0, 0, 0);
        } else if (watchBattery > 70 && watchBattery <= 90) {
            tvWatchBetteryCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mine_batter80, 0, 0, 0);
        } else {
            tvWatchBetteryCount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mine_batter100, 0, 0, 0);
        }


        tvWatchBetteryCount.setCompoundDrawablePadding(10);
    }


    class ViewHolder extends BaseCommonRefreshRecyclerAdapter.BaseViewHolder {

        private TextView tvTypeName;
        private TextView tvTypeUnit;
        private ImageView ivTypeLog;
        private TextView tvBattery;
        private View linebottem;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTypeLog = (ImageView) itemView.findViewById(R.id.iv_icon);
            // ivLeftArrow = (ImageView) itemView.findViewById(R.id.iv_arrow_left);
            tvTypeUnit = itemView.findViewById(R.id.device_property);
            tvTypeName = itemView.findViewById(R.id.tv_name);
            linebottem = itemView.findViewById(R.id.view_bottom);
            tvBattery = itemView.findViewById(R.id.tv_battery);
        }
    }
}
