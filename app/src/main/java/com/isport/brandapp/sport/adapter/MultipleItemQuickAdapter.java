package com.isport.brandapp.sport.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.isport.brandapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<QuickMultipleEntity, BaseViewHolder> {

    public MultipleItemQuickAdapter(List<QuickMultipleEntity> data) {
        super(data);
        // 绑定 layout 对应的 type
        addItemType(QuickMultipleEntity.TITLE, R.layout.item_sport_tilte);
        addItemType(QuickMultipleEntity.CONTENT, R.layout.item_sport_history_layout);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, QuickMultipleEntity item) {
        switch (helper.getItemViewType()) {
            case QuickMultipleEntity.TITLE:
                helper.setText(R.id.tv_date, "2019-0203 ");
                break;
            case QuickMultipleEntity.CONTENT:
                switch (helper.getLayoutPosition() % 2) {
                    case 0:
                        //helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
                        break;
                    case 1:
                        //helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
                        break;
                    default:
                        break;
                }
                helper.setText(R.id.tv, "ChayChan " + helper.getAdapterPosition());
                break;
            default:
                break;
        }
    }

}
