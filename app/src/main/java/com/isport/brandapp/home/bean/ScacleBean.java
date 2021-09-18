package com.isport.brandapp.home.bean;

public class ScacleBean {

    private String strDate;
    private Float weight;
    private Float BMI;
    private String color;
    private String stander;


    public ScacleBean() {

    }

    public ScacleBean(String strDate, Float weight, Float BMI) {
        this.strDate = strDate;
        this.weight = weight;
        this.BMI = BMI;
    }


    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getBMI() {
        return BMI;
    }

    public void setBMI(Float BMI) {
        this.BMI = BMI;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStander() {
        return stander;
    }

    public void setStander(String stander) {
        this.stander = stander;
    }
}
