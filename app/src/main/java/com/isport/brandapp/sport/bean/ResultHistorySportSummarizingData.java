package com.isport.brandapp.sport.bean;

import java.math.BigDecimal;

public class ResultHistorySportSummarizingData {

    private Integer times;
    private double allDistance;

    public double getAllDistance() {
        return allDistance;
    }

    public void setAllDistance(double allDistance) {
        this.allDistance = allDistance;
    }

    public Integer getTimes() {

        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
