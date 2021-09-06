package com.isport.blelibrary.result.impl.scale;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/17
 * @Fuction
 */

public class ScaleVersionResult implements IResult,Serializable {
    @Override
    public String getType() {
        return SCALE_VERSION;
    }

    private String version;

    @Override
    public String toString() {
        return "ScaleVersionResult{" +
                "version='" + version + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ScaleVersionResult(String version) {

        this.version = version;
    }
}
