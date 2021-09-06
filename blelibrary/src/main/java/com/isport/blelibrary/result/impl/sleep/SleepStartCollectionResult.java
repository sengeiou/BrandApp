package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/26
 * @Fuction
 */

public class SleepStartCollectionResult implements IResult,Serializable {

    @Override
    public String getType() {
        return SLEEP_STARTCOLLECTION;
    }

    private boolean isEnable;

    public SleepStartCollectionResult(boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "SleepStartCollectionResult{" +
                "isEnable=" + isEnable +
                '}';
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
