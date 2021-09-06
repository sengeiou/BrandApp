package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;
import com.sleepace.sdk.core.heartbreath.domain.OriginalData;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction
 */

public class SleepOriginalDataResult implements IResult,Serializable{
    @Override
    public String getType() {
        return SLEEP_ORIGINAL;
    }

    private OriginalData mOriginalData;

    @Override
    public String toString() {
        return "SleepOriginalDataResult{" +
                "mOriginalData=" + mOriginalData +
                '}';
    }

    public OriginalData getOriginalData() {
        return mOriginalData;
    }

    public void setOriginalData(OriginalData originalData) {
        mOriginalData = originalData;
    }

    public SleepOriginalDataResult(OriginalData originalData) {

        mOriginalData = originalData;
    }
}
