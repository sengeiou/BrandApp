package com.isport.brandapp.sport.bean;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class PaceBean implements Comparable<PaceBean> {
    int strPace;
    long time;
    String pace;

    public int getStrPace() {
        return strPace;
    }

    public void setStrPace(int strPace) {
        this.strPace = strPace;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public PaceBean() {

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


    @Override
    public int compareTo(@NonNull PaceBean paceBean) {
        /*if(this.pace>paceBean.pace){
            return
        }else{
            return;
        }*/
        int num = this.strPace - paceBean.strPace;
        //int num1 = (num == 0 ? this.name.compareTo(s.name) : num);
        return num;
    }
}
