package com.isport.brandapp.message;

import brandapp.isport.com.basicres.mvp.BaseView;
import phone.gym.jkcq.com.socialmodule.bean.ListData;

/**
 * Created by BeyondWorlds
 * on 2020/6/30
 */
interface MessageView extends BaseView {
    void addFollowSuccess(int type);

    void unFollowSuccess(int type);

    void getMessageInfoSuccess(ListData<MessageInfo> info);

    void successDel(String messageId);
}
