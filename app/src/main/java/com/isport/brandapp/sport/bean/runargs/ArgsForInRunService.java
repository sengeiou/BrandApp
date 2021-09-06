
package com.isport.brandapp.sport.bean.runargs;

/**
 * ClassName:ArgsForStepService <br/>
 * Function: 用来记录跑步时的状态数据. <br/>
 * Date: 2017年3月9日 下午12:11:12 <br/>
 *
 * @author Administrator
 */
public class ArgsForInRunService {

    // 记录是否为第一次获取数据
    public boolean isFirstGetPhoneData = true;
    public boolean isFirstGetRingData = true;
    public boolean isFirstGetHeartRateData = true;

    public long phoneStartTime = 0; // 手机跑步开始时间
    public long phonePauseTime = 0; // 手机点击暂停的时间
    public long phonePauseTimeTotal = 0; // 手机暂停时间总和

    public long ringStartTime = 0; // 手环跑步开始时间
    public long ringPauseTime = 0; // 手环点击暂停的时间
    public long ringPauseTimeTotal = 0; // 手环暂停时间总和

    public long heartRateStartTime = 0; // 心率带跑步开始时间
    public long heartRatePauseTime = 0; // 心率带点击暂停的时间
    public long heartRatePauseTimeTotal = 0; // 心率带暂停时间总和

    /*
     * 跑步页面是否为锁定状态
     */
    public boolean isViewLock = false;

    /**
     * 以下为手环跑步时的状态以及数据记录
     */
    // 开始时刻，手环——步数，卡路里，距离，心率
    public int ringStepValue = 0;
    public double ringCalorieValue = 0;
    public double ringDistanceValue = 0;

    // 开始时刻，心率带的步数。
    public int heartRateStepValue = 0;

    // 开始时刻，手机的步数。
    public int phoneStepValue = 0;

    // 暂停时，手环的 步数，卡路里，距离变化值，不计入跑步的数值里面。

    /**
     * 1.记下暂停开始那一个时刻的数据。 2.当点击开始的时候，用开始那一个时刻的数据减去暂停那一个时刻的数据，为暂停中的数据，这个数据不计入跑步数值。
     * 3.多次暂停的状况：将每次暂停的数值累加给total。
     */


    public void clearData() {
        isFirstGetPhoneData = true;
        isFirstGetRingData = true;
        isFirstGetHeartRateData = true;

        phoneStartTime = 0; // 手机跑步开始时间
        phonePauseTime = 0; // 手机点击暂停的时间
        phonePauseTimeTotal = 0; // 手机暂停时间总和

        ringStartTime = 0; // 手环跑步开始时间
        ringPauseTime = 0; // 手环点击暂停的时间
        ringPauseTimeTotal = 0; // 手环暂停时间总和

        heartRateStartTime = 0; // 心率带跑步开始时间
        heartRatePauseTime = 0; // 心率带点击暂停的时间
        heartRatePauseTimeTotal = 0; // 心率带暂停时间总和

        isViewLock = false;

        ringStepValue = 0;
        ringCalorieValue = 0;
        ringDistanceValue = 0;

        heartRateStepValue = 0;

        phoneStepValue = 0;


        // 手环
        ringPauseStep = 0;
        ringPauseCalorie = 0;
        ringPauseDistance = 0;

        ringPauseStepTotal = 0;
        ringPauseCalorieTotal = 0;
        ringPauseDistanceTotal = 0;

        // 心率带
        heartRatePauseStep = 0;
        heartRatePauseStepTotal = 0;

        // 手机
        phonePauseStep = 0;
        phonePauseStepTotal = 0;

        // 上次保存距离，心率的时间，和算配速的上次距离

        // 手环
        preRingDistanceTime = 0;// 记录距离变化一个精度，过了多少时间。
        preRingDistance = 0;// 上次的距离

        // 心率带
        preHeartRateDistanceTime = 0;// 记录距离变化一个精度，过了多少时间。
        preHeartRateDistance = 0;// 上次的距离

        // 手机
        prePhoneDistanceTime = 0;// 记录距离变化一个精度，过了多少时间。
        prePhoneDistance = 0;// 上次的距离
    }


    // 手环
    public int ringPauseStep = 0;
    public double ringPauseCalorie = 0;
    public double ringPauseDistance = 0;

    public int ringPauseStepTotal = 0;
    public double ringPauseCalorieTotal = 0;
    public double ringPauseDistanceTotal = 0;

    // 心率带
    public int heartRatePauseStep = 0;
    public int heartRatePauseStepTotal = 0;

    // 手机
    public int phonePauseStep = 0;
    public int phonePauseStepTotal = 0;

    // 上次保存距离，心率的时间，和算配速的上次距离

    // 手环
    public long preRingDistanceTime = 0;// 记录距离变化一个精度，过了多少时间。
    public double preRingDistance = 0;// 上次的距离

    // 心率带
    public long preHeartRateDistanceTime = 0;// 记录距离变化一个精度，过了多少时间。
    public double preHeartRateDistance = 0;// 上次的距离

    // 手机
    public long prePhoneDistanceTime = 0;// 记录距离变化一个精度，过了多少时间。
    public double prePhoneDistance = 0;// 上次的距离

    public ArgsForInRunService setFirstGetPhoneData(boolean isFirstGetPhoneData) {
        this.isFirstGetPhoneData = isFirstGetPhoneData;
        return this;
    }

    public ArgsForInRunService setFirstGetRingData(boolean isFirstGetRingData) {
        this.isFirstGetRingData = isFirstGetRingData;
        return this;
    }

    public ArgsForInRunService setFirstGetHeartRateData(boolean isFirstGetHeartRateData) {
        this.isFirstGetHeartRateData = isFirstGetHeartRateData;
        return this;
    }

    public ArgsForInRunService setPhoneStartTime(long phoneStartTime) {
        this.phoneStartTime = phoneStartTime;
        return this;
    }

    public ArgsForInRunService setPhonePauseTime(long phonePauseTime) {
        this.phonePauseTime = phonePauseTime;
        return this;
    }

    public ArgsForInRunService setPhonePauseTimeTotal(long phonePauseTimeTotal) {
        this.phonePauseTimeTotal = phonePauseTimeTotal;
        return this;
    }

    public ArgsForInRunService setRingStartTime(long ringStartTime) {
        this.ringStartTime = ringStartTime;
        return this;
    }

    public ArgsForInRunService setRingPauseTime(long ringPauseTime) {
        this.ringPauseTime = ringPauseTime;
        return this;
    }

    public ArgsForInRunService setRingPauseTimeTotal(long ringPauseTimeTotal) {
        this.ringPauseTimeTotal = ringPauseTimeTotal;
        return this;
    }

    public ArgsForInRunService setHeartRateStartTime(long heartRateStartTime) {
        this.heartRateStartTime = heartRateStartTime;
        return this;
    }

    public ArgsForInRunService setHeartRatePauseTime(long heartRatePauseTime) {
        this.heartRatePauseTime = heartRatePauseTime;
        return this;
    }

    public ArgsForInRunService setHeartRatePauseTimeTotal(long heartRatePauseTimeTotal) {
        this.heartRatePauseTimeTotal = heartRatePauseTimeTotal;
        return this;
    }

    public ArgsForInRunService setViewLock(boolean isViewLock) {
        this.isViewLock = isViewLock;
        return this;
    }

    public ArgsForInRunService setRingStepValue(int ringStepValue) {
        this.ringStepValue = ringStepValue;
        return this;
    }

    public ArgsForInRunService setRingCalorieValue(double ringCalorieValue) {
        this.ringCalorieValue = ringCalorieValue;
        return this;
    }

    public ArgsForInRunService setRingDistanceValue(double ringDistanceValue) {
        this.ringDistanceValue = ringDistanceValue;
        return this;
    }

    public ArgsForInRunService setHeartRateStepValue(int heartRateStepValue) {
        this.heartRateStepValue = heartRateStepValue;
        return this;
    }

    public ArgsForInRunService setPhoneStepValue(int phoneStepValue) {
        this.phoneStepValue = phoneStepValue;
        return this;
    }

    public ArgsForInRunService setRingPauseStep(int ringPauseStep) {
        this.ringPauseStep = ringPauseStep;
        return this;
    }

    public ArgsForInRunService setRingPauseCalorie(double ringPauseCalorie) {
        this.ringPauseCalorie = ringPauseCalorie;
        return this;
    }

    public ArgsForInRunService setRingPauseDistance(double ringPauseDistance) {
        this.ringPauseDistance = ringPauseDistance;
        return this;
    }

    public ArgsForInRunService setRingPauseStepTotal(int ringPauseStepTotal) {
        this.ringPauseStepTotal = ringPauseStepTotal;
        return this;
    }

    public ArgsForInRunService setRingPauseCalorieTotal(double ringPauseCalorieTotal) {
        this.ringPauseCalorieTotal = ringPauseCalorieTotal;
        return this;
    }

    public ArgsForInRunService setRingPauseDistanceTotal(double ringPauseDistanceTotal) {
        this.ringPauseDistanceTotal = ringPauseDistanceTotal;
        return this;
    }

    public ArgsForInRunService setHeartRatePauseStep(int heartRatePauseStep) {
        this.heartRatePauseStep = heartRatePauseStep;
        return this;
    }

    public ArgsForInRunService setHeartRatePauseStepTotal(int heartRatePauseStepTotal) {
        this.heartRatePauseStepTotal = heartRatePauseStepTotal;
        return this;
    }

    public ArgsForInRunService setPhonePauseStep(int phonePauseStep) {
        this.phonePauseStep = phonePauseStep;
        return this;
    }

    public ArgsForInRunService setPhonePauseStepTotal(int phonePauseStepTotal) {
        this.phonePauseStepTotal = phonePauseStepTotal;
        return this;
    }

    public ArgsForInRunService setPreRingDistanceTime(long preRingDistanceTime) {
        this.preRingDistanceTime = preRingDistanceTime;
        return this;
    }

    public ArgsForInRunService setPreRingDistance(double preRingDistance) {
        this.preRingDistance = preRingDistance;
        return this;
    }

    public ArgsForInRunService setPreHeartRateDistanceTime(long preHeartRateDistanceTime) {
        this.preHeartRateDistanceTime = preHeartRateDistanceTime;
        return this;
    }

    public ArgsForInRunService setPreHeartRateDistance(double preHeartRateDistance) {
        this.preHeartRateDistance = preHeartRateDistance;
        return this;
    }

    public ArgsForInRunService setPrePhoneDistanceTime(long prePhoneDistanceTime) {
        this.prePhoneDistanceTime = prePhoneDistanceTime;
        return this;
    }

    public ArgsForInRunService setPrePhoneDistance(double prePhoneDistance) {
        this.prePhoneDistance = prePhoneDistance;
        return this;
    }

}
