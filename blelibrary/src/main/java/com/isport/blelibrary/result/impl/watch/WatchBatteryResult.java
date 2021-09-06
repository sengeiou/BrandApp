package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/11/5
 * @Fuction
 */

public class WatchBatteryResult implements IResult,Serializable {

    private int quantity;

    @Override
    public String toString() {
        return "WatchBatteryResult{" +
                "quantity=" + quantity +
                '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public WatchBatteryResult(int quantity) {

        this.quantity = quantity;
    }

    @Override
    public String getType() {
        return WATCH_BATTERY;
    }
}
