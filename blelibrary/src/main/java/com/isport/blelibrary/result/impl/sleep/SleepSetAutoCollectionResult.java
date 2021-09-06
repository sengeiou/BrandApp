package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/30
 * @Fuction
 */

public class SleepSetAutoCollectionResult implements IResult,Serializable {
    @Override
    public String getType() {
        return SLEEP_SETAUTOCOLLECTION;
    }

    private boolean isEnable;

    @Override
    public String toString() {
        return "SleepSetAutoCollectionResult{" +
                "isEnable=" + isEnable +
                '}';
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public SleepSetAutoCollectionResult(boolean isEnable) {

        this.isEnable = isEnable;
    }
}
