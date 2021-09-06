package com.isport.brandapp.device.scale.bean;

public class ScaleCharBean {
    String starDate;
    float weight;

    @Override
    public String toString() {
        return "ScaleCharBean{" +
                "starDate='" + starDate + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
