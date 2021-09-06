package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction     chargingState	int	是否正在充电，0：未充电状态 1: 正在充电状态
                quantity	int	电量百分比 (充电时, 不建议使用该值
 */

public class SleepBatteryResult implements IResult,Serializable{
    @Override
    public String getType() {
        return SLEEP_BATTERY;
    }

    private int chargingState;
    private int quantity;

    public SleepBatteryResult(int chargingState, int quantity) {
        this.chargingState = chargingState;
        this.quantity = quantity;
    }

    public int getChargingState() {
        return chargingState;
    }

    public void setChargingState(int chargingState) {
        this.chargingState = chargingState;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SleepBatteryResult{" +
                "chargingState=" + chargingState +
                ", quantity=" + quantity +
                '}';
    }
}
