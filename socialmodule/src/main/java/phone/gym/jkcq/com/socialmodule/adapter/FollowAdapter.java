package phone.gym.jkcq.com.socialmodule.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.FriendInfo;

public class FollowAdapter extends BaseQuickAdapter<FriendInfo, BaseViewHolder> {

    private OnChildClickLisenter mOnChildClickLisenter;
    private OnItemClickLisenter mOnItemClickLisenter;

    public FollowAdapter(List<FriendInfo> data) {
        super(R.layout.friend_item_follow, data);
    }

    public void addOnChildClickListener(OnChildClickLisenter onChildClickLisenter) {
        this.mOnChildClickLisenter = onChildClickLisenter;
    }

    public void addOnItemClickListener(OnItemClickLisenter onItemClickLisenter) {
        this.mOnItemClickLisenter = onItemClickLisenter;
    }

    @Override
    protected void convert(BaseViewHolder holder, FriendInfo info) {
        boolean isMySelf = info.getUserId().equals(TokenUtil.getInstance().getPeopleIdInt(getContext()));
        if (isMySelf) {
            holder.getView(R.id.tv_follow).setVisibility(View.GONE);
            holder.getView(R.id.iv_arrow_right).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_follow).setVisibility(View.VISIBLE);
            holder.getView(R.id.iv_arrow_right).setVisibility(View.GONE);
        }

        switch (info.getType()) {
            case 1:
                holder.setText(R.id.tv_follow, R.string.friend_each_follow);
                break;
            case 0:
            case 2:
                holder.setText(R.id.tv_follow, R.string.friend_to_follow);
                break;
            case 3:
                holder.setText(R.id.tv_follow, R.string.friend_already_follow);

                break;
        }
        holder.setText(R.id.tv_nickname, info.getNickName());
        holder.getView(R.id.tv_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ViewMultiClickUtil.onMultiClick(v)) {
                    mOnChildClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.onClick(v, info, holder.getLayoutPosition());
                }
            }
        });
        LoadImageUtil.getInstance().loadCirc(getContext(), info.getHeadUrl(), holder.getView(R.id.iv_head_photo));
    }

    public interface OnChildClickLisenter {
        void onClick(View view, FriendInfo info, int position);
    }

    public interface OnItemClickLisenter {
        void onClick(View view, FriendInfo info, int position);
    }
}
