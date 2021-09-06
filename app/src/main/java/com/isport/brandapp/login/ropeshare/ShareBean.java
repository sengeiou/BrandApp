package com.isport.brandapp.login.ropeshare;

import android.graphics.Bitmap;

import java.io.File;

public class ShareBean {
    boolean isSelect;
    String url;
    File shareFile;
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ShareBean() {

    }

    public ShareBean(boolean isSelect, File shareFile) {
        this.isSelect = isSelect;
        this.shareFile = shareFile;
    }

    public ShareBean(boolean isSelect, Bitmap shareFile) {
        this.isSelect = isSelect;
        this.bitmap = bitmap;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getShareFile() {
        return shareFile;
    }

    public void setShareFile(File shareFile) {
        this.shareFile = shareFile;
    }
}
