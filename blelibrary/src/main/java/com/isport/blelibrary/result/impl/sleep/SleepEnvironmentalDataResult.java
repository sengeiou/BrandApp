package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction temperature	int	温度,单位：摄氏度
            humidity	int	温度
 */

public class SleepEnvironmentalDataResult implements IResult,Serializable {
    @Override
    public String getType() {
        return SLEEP_ENVIRONMENT;
    }

    private int temperature;
    private int humidity;

    @Override
    public String toString() {
        return "SleepEnvironmentalDataResult{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public SleepEnvironmentalDataResult(int temperature, int humidity) {

        this.temperature = temperature;
        this.humidity = humidity;
    }
}
