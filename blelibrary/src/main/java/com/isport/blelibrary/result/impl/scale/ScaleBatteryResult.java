package com.isport.blelibrary.result.impl.scale;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/17
 * @Fuction
 */

public class ScaleBatteryResult implements IResult,Serializable{
    @Override
    public String toString() {
        return "ScaleBatteryResult{" +
                "quantity=" + quantity +
                '}';
    }

    @Override
    public String getType() {
        return SCALE_BATTERY;
    }

    private int quantity;

    public ScaleBatteryResult(int quantity) {
        this.quantity = quantity;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
