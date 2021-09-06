package phone.gym.jkcq.com.socialmodule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;
import phone.gym.jkcq.com.socialmodule.R;
import phone.gym.jkcq.com.socialmodule.bean.response.DynamBean;

public class LikeAdapter extends BaseQuickAdapter<DynamBean, BaseViewHolder> {

    public LikeAdapter(List<DynamBean> data) {
        super(R.layout.item_action_production, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamBean data) {
        holder.setText(R.id.tv_like_num, "" + data.getPraiseNums());
        holder.setVisible(R.id.iv_delete, false);
        LoadImageUtil.getInstance().loadCircs(getContext(), data.getCoverUrl(), holder.getView(R.id.iv_video), DisplayUtils.dip2px(getContext(), 20));
    }

}
