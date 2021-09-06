package com.isport.brandapp.bean;

import brandapp.isport.com.basicres.commonbean.BaseBean;

/**
 * Created by peng on 2018/5/23.
 */

public class ShareBean extends BaseBean {

    //String message;
    String inviteCode;
    String imageUrl;
    String shareUrl;
    String title;
    String content;

    /* @Override
     public String getMessage() {
         return message;
     }

     @Override
     public void setMessage(String message) {
         this.message = message;
     }
 */
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
