package com.isport.blelibrary.result.entry;

import java.util.Arrays;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class Detail {

    public Detail(int[] breathRate, int[] heartRate, int[] status, int[] statusValue, int[] eHumidity, int[]
            eTemp) {
        this.breathRate = breathRate;
        this.heartRate = heartRate;
        this.status = status;
        this.statusValue = statusValue;
        this.eHumidity = eHumidity;
        this.eTemp = eTemp;
    }

    public int[] breathRate;
    public int[] heartRate;
    public int[] status;
    public int[] statusValue;
    public int[] eHumidity;
    public int[] eTemp;

    @Override
    public String toString() {
        return "Detail{" +
                "breathRate=" + Arrays.toString(breathRate) +
                ", heartRate=" + Arrays.toString(heartRate) +
                ", status=" + Arrays.toString(status) +
                ", statusValue=" + Arrays.toString(statusValue) +
                ", eHumidity=" + Arrays.toString(eHumidity) +
                ", eTemp=" + Arrays.toString(eTemp) +
                '}';
    }
}
