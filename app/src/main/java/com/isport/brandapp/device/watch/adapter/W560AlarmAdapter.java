package com.isport.brandapp.device.watch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.isport.blelibrary.db.table.watch_w516.Watch_W560_AlarmModel;
import com.isport.brandapp.R;
import com.isport.brandapp.device.bracelet.Utils.RepeatUtils;

import java.util.List;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class W560AlarmAdapter extends ArrayAdapter<Watch_W560_AlarmModel> {

    private int resourceId;
    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public W560AlarmAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Watch_W560_AlarmModel> objects) {
        super(context, resource, textViewResourceId, objects);

        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Watch_W560_AlarmModel alarmModel = getItem(position);

        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        } else {
            view = convertView;
        }
        if (alarmModel != null) {
            TextView textViewTime = view.findViewById(R.id.tv_time);
            TextView textViewRepeat = view.findViewById(R.id.tv_repeat);
            TextView textViewName = view.findViewById(R.id.tv_name);
            ImageView imageViewStatus = view.findViewById(R.id.view_car_remind_radio);

            textViewTime.setText(alarmModel.getTimeString());
            int repeatCount = alarmModel.getRepeatCount();
            if (repeatCount == 128) {
                repeatCount = 0;
            }
            textViewRepeat.setText(RepeatUtils.setRepeat(JkConfiguration.DeviceType.Watch_W560, repeatCount));
            textViewName.setText(alarmModel.getName());
            imageViewStatus.setImageResource(alarmModel.getIsEnable() ? R.drawable.icon_open : R.drawable.icon_close);
            imageViewStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemEnableListener.onEnableAlarm(position, !alarmModel.getIsEnable());
                }
            });
        }

        return view;
    }

    /**
     * 开关按钮监听接口
     */
    public interface OnItemEnableListener {
        void onEnableAlarm(int index, boolean enabled);
    }

    private OnItemEnableListener onItemEnableListener;

    public void setOnItemEnableListener(OnItemEnableListener onItemEnableListener) {
        this.onItemEnableListener = onItemEnableListener;
    }
}

