package phone.gym.jkcq.com.socialmodule.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import brandapp.isport.com.basicres.commonutil.MessageEvent;

public class NetworkChang extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        EventBus.getDefault().post(new MessageEvent(MessageEvent.newwork_change));
       /* if (NetworkUtils.isAvailable()) {
            Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
        }*/
    }
}
