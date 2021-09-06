package com.isport.blelibrary.result.impl.w311;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class BraceletW311SyncComplete implements IResult, Serializable {
    @Override
    public String getType() {
        return BRACELET_W311_COMPTELETY;
    }

    public static int FAILED = 0;
    public static int SYNCING = 1;
    public static int SUCCESS = 2;
    public static int TIMEOUT=3;

    private int success;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public BraceletW311SyncComplete(int success) {
        this.success = success;
    }

    public BraceletW311SyncComplete() {

    }
}
