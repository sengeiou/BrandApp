package com.isport.blelibrary.result.impl.scale;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction
 */

public class ScaleLockDataResult implements IResult,Serializable {
    @Override
    public String getType() {
        return SCALE_LOCK_DATA;
    }

    public float getRES() {
        return RES;
    }


    public float getWeight() {
        return weight;
    }


    private float RES;
    private float weight;

    @Override
    public String toString() {
        return "ScaleLockDataResult{" +
                "RES=" + RES +
                ", weight=" + weight +
                '}';
    }

    public void setRES(float RES) {
        this.RES = RES;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public ScaleLockDataResult(float RES, float weight) {
        this.RES = RES;
        this.weight = weight;
    }
}
