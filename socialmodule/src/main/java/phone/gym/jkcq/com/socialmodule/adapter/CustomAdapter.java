package phone.gym.jkcq.com.socialmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context mContext;
    private List<DynamBean> mdatas;
    private OnItemClickListener mLister;

    public CustomAdapter(Context context, List<DynamBean> data) {
        this.mdatas = data;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mLister = listener;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_action_production, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        DynamBean data = mdatas.get(position);
        holder.tv_like_num.setText("" + data.getPraiseNums());

        LoadImageUtil.getInstance().loadCircs(mContext, data.getCoverUrl(), holder.iv_video, DisplayUtils.dip2px(mContext, 20));

        // LoadImageUtil.getInstance().loadCirc(mContext, data.getCoverUrl(), holder.iv_video, 20);
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLister != null) {
                    mLister.onChildClick(v, position);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLister.onItemClick(v, position);
            }
        });

        if (data.getUserId().equals(TokenUtil.getInstance().getPeopleIdInt(mContext))) {
            holder.iv_delete.setVisibility(View.VISIBLE);
        } else {
            holder.iv_delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_video;
        private ImageView iv_delete;
        private TextView tv_like_num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_video = itemView.findViewById(R.id.iv_video);
            tv_like_num = itemView.findViewById(R.id.tv_like_num);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }

    public interface OnItemClickListener {
        void onChildClick(View view, int position);

        void onItemClick(View view, int position);
    }
}
