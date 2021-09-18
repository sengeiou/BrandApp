package com.isport.brandapp.home.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.isport.brandapp.App;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.banner.recycleView.holder.CustomHolder;

import brandapp.isport.com.basicres.commonbean.UserInfoBean;

import java.util.List;

import bike.gymproject.viewlibray.ItemView;
import brandapp.isport.com.basicres.commonutil.LoadImageUtil;
import brandapp.isport.com.basicres.commonutil.TokenUtil;
import brandapp.isport.com.basicres.commonutil.ViewMultiClickUtil;
import brandapp.isport.com.basicres.commonview.RoundImageView;
import brandapp.isport.com.basicres.net.userNet.CommonUserAcacheUtil;
import phone.gym.jkcq.com.commonres.commonutil.DisplayUtils;

public class MineHeaderHolder extends CustomHolder<String> {
    ImageView ivSetting, ivEdit;
    TextView tvName;
    RoundImageView ivHead;
    UserInfoBean loginBean;
    ItemView heathItemView;
    RelativeLayout layoutHeath;

    public MineHeaderHolder(View itemView) {
        super(itemView);
    }

    public MineHeaderHolder(List<String> datas, View itemView) {
        super(datas, itemView);
    }


    public void isShowHeathItem(boolean isShow) {
        layoutHeath.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public MineHeaderHolder(Context context, final List<String> lists, int itemID) {
        super(context, lists, itemID);

        ivSetting = itemView.findViewById(R.id.iv_setting);
        heathItemView = itemView.findViewById(R.id.itemview_heath);
        ivEdit = itemView.findViewById(R.id.iv_edit);
        ivHead = itemView.findViewById(R.id.iv_head);
        tvName = itemView.findViewById(R.id.tv_name);
        layoutHeath = itemView.findViewById(R.id.layout_heath);
        itemView.findViewById(R.id.tv_title_name).setVisibility(View.GONE);

        //对于打孔屏设置的marginTop更低
        RelativeLayout.LayoutParams ivSsettingLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivSsettingLP.topMargin = DisplayUtils.dip2px(context, 40);
        RelativeLayout.LayoutParams ivEditLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivEditLP.topMargin = DisplayUtils.dip2px(context, 40);
        ivEditLP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        int paddingValue = DisplayUtils.dip2px(context, 15);
        if (App.isPerforatedPanel()) {
            ivSetting.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
            ivEdit.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
            ivSetting.setLayoutParams(ivSsettingLP);
            ivEdit.setLayoutParams(ivEditLP);
        }
        if (App.appType() == App.httpType) {
            if (AppConfiguration.deviceBeanList != null) {
                if (AppConfiguration.deviceBeanList.size() >= 3) {
                    heathItemView.setVisibility(View.VISIBLE);
                } else {
                    heathItemView.setVisibility(View.GONE);
                }
            } else {
                heathItemView.setVisibility(View.GONE);
            }
        } else {
            heathItemView.setVisibility(View.GONE);
        }


        heathItemView.setBg(R.drawable.common_main_item_selector);
        loginBean = CommonUserAcacheUtil.getUserInfo(TokenUtil.getInstance().getPeopleIdStr(context));
        if (loginBean != null) {
            updateData(loginBean.getNickName(), loginBean.getHeadUrlTiny());
        }
        heathItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 健康指数的跳转
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onHealthItemOnclick();
                }
            }
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onSettingOnclick();
                }
            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onEditOnclick();
                }
            }
        });
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lister != null) {
                    if (ViewMultiClickUtil.onMultiClick(view)) {
                        return;
                    }
                    lister.onHeadOnclick();
                }
            }
        });


    }

    OnHeadOnclickLister lister;

    public void setOnCourseOnclickLister(OnHeadOnclickLister lister) {
        this.lister = lister;
    }

    public interface OnHeadOnclickLister {
        public void onSettingOnclick();

        public void onEditOnclick();

        public void onHeadOnclick();

        public void onHealthItemOnclick();

    }

    public void updateData(String name, String headUrl) {
        tvName.setText(name);
        if (App.appType() == App.httpType) {
            LoadImageUtil.getInstance().loadCirc(context, headUrl, ivHead);
        } else {
            if (!TextUtils.isEmpty(headUrl)) {
                Log.e("CustomRepository  222", headUrl);
                ivHead.setImageBitmap(BitmapFactory.decodeFile(headUrl));
            }
            //LoadImageUtil.getInstance().displayImagePath(context, headUrl, ivHead);
        }
    }

    public void updateData(String headUrl) {
        LoadImageUtil.getInstance().loadCirc(context, headUrl, ivHead);
    }

    public void updateData(UserInfoBean userInfo) {
        updateData(userInfo.getNickName(), userInfo.getHeadUrl());
    }
}
