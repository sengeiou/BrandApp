package phone.gym.jkcq.com.socialmodule.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import brandapp.isport.com.basicres.BaseApp;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.UIUtils;
import phone.gym.jkcq.com.socialmodule.FriendConstant;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.RankInfo;

public class SportRankAdapter extends BaseQuickAdapter<RankInfo, BaseViewHolder> {

    public SportRankAdapter(List<RankInfo> data) {
        super(R.layout.item_sport_rank, data);
        addChildClickViewIds(R.id.ll_like);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, RankInfo rankInfo) {

        if (TokenUtil.getInstance().getPeopleIdInt(BaseApp.instance).equals(rankInfo.getUserId())) {
            holder.setText(R.id.tv_nickname, rankInfo.getNickName() + UIUtils.getString(R.string.rope_ranking_me));
        } else {
            holder.setText(R.id.tv_nickname, rankInfo.getNickName());
        }
        //  holder.setText(R.id.tv_nickname, rankInfo.getNickName());
        holder.setText(R.id.tv_like_num, "" + rankInfo.getPraiseNums());
        switch (rankInfo.getMotionType()) {
            case FriendConstant.RANK_STEPS:
                holder.setText(R.id.tv_count, "" + rankInfo.getTotalStep());
                holder.setText(R.id.tv_unit, getContext().getString(R.string.unit_step));
                break;
            case FriendConstant.RANK_WALK:
                holder.setText(R.id.tv_count, "" + rankInfo.getTotalDistance());
                holder.setText(R.id.tv_unit, getContext().getString(R.string.unit_distance));
                break;
            case FriendConstant.RANK_RUN_OUTSIDE:
                holder.setText(R.id.tv_count, "" + rankInfo.getTotalDistance());
                holder.setText(R.id.tv_unit, getContext().getString(R.string.unit_distance));
                break;
            case FriendConstant.RANK_RUN_INSIDE:
                holder.setText(R.id.tv_count, "" + rankInfo.getTotalDistance());
                holder.setText(R.id.tv_unit, getContext().getString(R.string.unit_distance));
                break;
            case FriendConstant.RANK_RIDE:
                holder.setText(R.id.tv_count, "" + rankInfo.getTotalDistance());
                holder.setText(R.id.tv_unit, getContext().getString(R.string.unit_distance));
                break;
            case FriendConstant.RANK_CAL:
                holder.setText(R.id.tv_count, "" + (int) (rankInfo.getTotalCalories() / 1));
                holder.setText(R.id.tv_unit, getContext().getString(R.string.unit_kcal));
                break;
        }
        if (rankInfo.isWhetherPraise()) {
            holder.setImageResource(R.id.iv_like, R.drawable.icon_rope_like_unselected);
        } else {
            holder.setImageResource(R.id.iv_like, R.drawable.icon_rope_unlike_unselected);
        }
        int position = holder.getLayoutPosition();
        String rankIndex = rankInfo.getIndex();
        if (TextUtils.isEmpty(rankIndex)) {
            holder.setText(R.id.tv_rank, "--");
        } else {
            holder.setText(R.id.tv_rank, rankIndex);
        }


        switch (rankIndex) {
            case "1":
                holder.setBackgroundResource(R.id.tv_rank, R.drawable.shape_rope_ranking_one);
                break;
            case "2":
                holder.setBackgroundResource(R.id.tv_rank, R.drawable.shape_rope_ranking_two);
                break;
            case "3":
                holder.setBackgroundResource(R.id.tv_rank, R.drawable.shape_rope_ranking_three);
                break;
            default:
                holder.setBackgroundResource(R.id.tv_rank, R.drawable.shape_rope_ranking_other);
                break;
        }


        LoadImageUtil.getInstance().loadCirc(getContext(), rankInfo.getHeadUrl(), holder.getView(R.id.circle_view_head));

    }
}
