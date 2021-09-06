package com.isport.brandapp.sport.bean;

import com.isport.brandapp.util.StepsUtils;

public class ArrayThree {
    double distances[] = new double[5];
    long times[] = new long[5];

    // 将当前时间，和距离加入的数组中。
    public double addValueAndGetVelocity(long thisTime, double distance) {
        // 首先排序数组，将所有值前移动一位，最后一个值空出来；
        moveValue();
        times[4] = thisTime;
        distances[4] = distance;

        double velocity;
        // 计算瞬时速度。
        double gapLength1 = distances[4] - distances[3];// 距离差值。
        double gapLength2 = distances[3] - distances[2];// 距离差值。
        double gapLength3 = distances[2] - distances[1];// 距离差值。
        double gapLength4 = distances[1] - distances[0];// 距离差值。

        long gapTime1 = times[4] - times[3];// 距离差值。
        long gapTime2 = times[3] - times[2];// 距离差值。
        long gapTime3 = times[2] - times[1];// 距离差值。
        long gapTime4 = times[1] - times[0];// 距离差值。

        velocity = (gapTime1 + gapTime2 + gapTime3 + gapTime4)
                / ((gapLength1 + gapLength2 + gapLength3 + gapLength4) * 60);
        return velocity;
    }

    public double clearValue(long thisTime, double distance) {
        times[4] = thisTime;
        times[3] = thisTime;
        times[2] = thisTime;
        times[1] = thisTime;
        times[0] = thisTime;
        distances[4] = distance;
        distances[3] = distance;
        distances[2] = distance;
        distances[1] = distance;

        return Double.POSITIVE_INFINITY;
    }

    public void cleanAtRunFinish() {
        for (int i = 0; i < times.length; i++) {
            times[i] = 0;
        }

        for (int i = 0; i < distances.length; i++) {
            times[i] = 0;
        }
    }

    private void moveValue() {
        // 将所有值前移动一位，最后一个值空出来;
        distances[0] = distances[1];
        distances[1] = distances[2];
        distances[2] = distances[3];
        distances[3] = distances[4];
        distances[4] = 0;

        times[0] = times[1];
        times[1] = times[2];
        times[2] = times[3];
        times[3] = times[4];
        times[4] = 0;

    }

}
