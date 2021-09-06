package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction
 */

public class SleepCollectionStatusResult implements IResult,Serializable {
    @Override
    public String getType() {
        return SLEEP_COLLECTIONSTATUS;
    }

    private byte mByte;

    @Override
    public String toString() {
        return "SleepCollectionStatusResult{" +
                "mByte=" + mByte +
                '}';
    }

    public byte getByte() {
        return mByte;
    }

    public void setByte(byte aByte) {
        mByte = aByte;
    }

    public SleepCollectionStatusResult(byte aByte) {

        mByte = aByte;
    }
}
