package com.isport.blelibrary.bluetooth.callbacks;

public class DataBean {

    private byte[] data;
    private int timeout;
    private boolean isShow;


    public DataBean() {

    }

    public DataBean(byte[] cmd, int timeout, boolean isShow) {
        this.data = cmd;
        this.timeout = timeout;
        this.isShow = isShow;

    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
