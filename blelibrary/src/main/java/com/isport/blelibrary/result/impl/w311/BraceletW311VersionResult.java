package com.isport.blelibrary.result.impl.w311;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

public class BraceletW311VersionResult implements IResult, Serializable {
    @Override
    public String getType() {
        return BRAND_VERSION;
    }

    private String version;

    @Override
    public String toString() {
        return "WatchVersionResult{" +
                "version='" + version + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BraceletW311VersionResult(String version) {

        this.version = version;
    }
}
