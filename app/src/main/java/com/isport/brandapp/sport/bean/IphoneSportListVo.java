package com.isport.brandapp.sport.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by deep on 6/17/2019.
 */
public class IphoneSportListVo {
    private Integer times;
    private double allDistance;
    private List<IphoneSportWeekVo> list;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public double getAllDistance() {
        return allDistance;
    }

    public void setAllDistance(double allDistance) {
        this.allDistance = allDistance;
    }

    public List<IphoneSportWeekVo> getList() {
        return list;
    }

    public void setList(List<IphoneSportWeekVo> list) {
        this.list = list;
    }
}
