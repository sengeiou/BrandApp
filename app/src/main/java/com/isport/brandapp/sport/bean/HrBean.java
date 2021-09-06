package com.isport.brandapp.sport.bean;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class HrBean implements Comparable<HrBean> {
    long time;
    Integer heartRate;
    //[{"time":"33","heartRate":"134"},{"time":"34","heartRate":"135"}]


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    @Override
    public int compareTo(@NonNull HrBean paceBean) {
        /*if(this.pace>paceBean.pace){
            return
        }else{
            return;
        }*/
        int num = paceBean.heartRate-this.heartRate;
        //int num1 = (num == 0 ? this.name.compareTo(s.name) : num);
        return num;
    }

    @Override
    public String toString() {
        //return super.toString();
        Gson gson = new Gson();
        /*Gson gs = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();*/


        //return "{\"pace\":\"" + pace + "\"," + "\"time\":" + time + "}";

        //return "{\"pace\":\"" + pace + "\"+\"time\":" + time + "}";
        return gson.toJson(this);
    }
}
