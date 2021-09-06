package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction
 */

public class SleepVersionResult implements IResult,Serializable{
    @Override
    public String getType() {
        return SLEEP_VERSION;
    }

    private String version;

    @Override
    public String toString() {
        return "SleepVersionResult{" +
                "version='" + version + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SleepVersionResult(String version) {

        this.version = version;
    }
}
