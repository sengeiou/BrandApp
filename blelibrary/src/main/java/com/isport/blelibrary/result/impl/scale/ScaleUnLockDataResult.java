package com.isport.blelibrary.result.impl.scale;

import com.isport.blelibrary.result.IResult;

/**
 * @Author
 * @Date 2018/10/10
 * @Fuction
 */

public class ScaleUnLockDataResult implements IResult {
    @Override
    public String getType() {
        return SCALE_UN_LOCK_DATA;
    }
    private float weight;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ScaleUnLockDataResult{" +
                "weight=" + weight +
                '}';
    }

    public ScaleUnLockDataResult(float weight) {

        this.weight = weight;
    }
}
