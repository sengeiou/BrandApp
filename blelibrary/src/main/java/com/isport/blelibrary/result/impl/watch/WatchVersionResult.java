package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchVersionResult implements IResult, Serializable {
    @Override
    public String getType() {
        return DEVICE_WATCH_VERSION;
    }

    private String version;
    private String mac;

    public WatchVersionResult(String version, String mac) {
        this.mac = mac;
        this.version = version;
    }

    public WatchVersionResult(String version) {
        this.mac = "";
        this.version = version;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
