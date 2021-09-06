package com.isport.blelibrary.result.impl.w311;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class BraceletW311SyncStart implements IResult, Serializable {
    @Override
    public String getType() {
        return BRACELET_W311_START_SYNC_DATA;
    }

}
