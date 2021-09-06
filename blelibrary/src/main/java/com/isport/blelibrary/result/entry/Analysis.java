package com.isport.blelibrary.result.entry;

import java.util.Arrays;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class Analysis{
        public Analysis(int averageBreathRate, int averageHeartBeatRate, int fallAlseepAllTime, int
                wakeAndLeaveBedBeforeAllTime, int leaveBedTimes, int trunOverTimes, int bodyMovementTimes, int
                                heartBeatPauseTimes, int breathPauseTimes, int deepSleepPerc, int inSleepPerc, int lightSleepPerc,
                        int wakeSleepPerc, int duration, int wakeTimes, int lightSleepAllTime, int inSleepAllTime,
                        int deepSleepAllTime, int wakeAllTime, int breathPauseAllTime, int heartBeatPauseAllTime, int
                                leaveBedAllTime, int maxHeartBeatRate, int maxBreathRate, int minHeartBeatRate, int
                                minBreathRate, int heartBeatRateFastAllTime, int heartBeatRateSlowAllTime, int
                                breathRateFastAllTime, int breathRateSlowAllTime, int sleepScore, float[]
                                sleepCurveArray, short[] sleepCurveStatusArray, String algorithmVer, int
                                fallsleepTimeStamp, int wakeupTimeStamp, int reportFlag, int[] breathRateStatusAry,
                        int[] heartRateStatusAry, int[] leftBedStatusAry, int[] turnOverStatusAry, short
                                md_body_move_decrease_scale, short md_leave_bed_decrease_scale, short
                                md_wake_cnt_decrease_scale, short md_start_time_decrease_scale, short
                                md_fall_asleep_time_decrease_scale, short md_perc_deep_decrease_scale, short
                                md_sleep_time_decrease_scale, short md_sleep_time_increase_scale, short
                                md_breath_stop_decrease_scale, short md_heart_stop_decrease_scale, short
                                md_heart_low_decrease_scale, short md_heart_high_decrease_scale, short
                                md_breath_low_decrease_scale, short md_breath_high_decrease_scale, short
                                md_perc_effective_sleep_decrease_scale) {
            this.averageBreathRate = averageBreathRate;
            this.averageHeartBeatRate = averageHeartBeatRate;
            this.fallAlseepAllTime = fallAlseepAllTime;
            this.wakeAndLeaveBedBeforeAllTime = wakeAndLeaveBedBeforeAllTime;
            this.leaveBedTimes = leaveBedTimes;
            this.trunOverTimes = trunOverTimes;
            this.bodyMovementTimes = bodyMovementTimes;
            this.heartBeatPauseTimes = heartBeatPauseTimes;
            this.breathPauseTimes = breathPauseTimes;
            this.deepSleepPerc = deepSleepPerc;
            this.inSleepPerc = inSleepPerc;
            this.lightSleepPerc = lightSleepPerc;
            this.wakeSleepPerc = wakeSleepPerc;
            this.duration = duration;
            this.wakeTimes = wakeTimes;
            this.lightSleepAllTime = lightSleepAllTime;
            this.inSleepAllTime = inSleepAllTime;
            this.deepSleepAllTime = deepSleepAllTime;
            this.wakeAllTime = wakeAllTime;
            this.breathPauseAllTime = breathPauseAllTime;
            this.heartBeatPauseAllTime = heartBeatPauseAllTime;
            this.leaveBedAllTime = leaveBedAllTime;
            this.maxHeartBeatRate = maxHeartBeatRate;
            this.maxBreathRate = maxBreathRate;
            this.minHeartBeatRate = minHeartBeatRate;
            this.minBreathRate = minBreathRate;
            this.heartBeatRateFastAllTime = heartBeatRateFastAllTime;
            this.heartBeatRateSlowAllTime = heartBeatRateSlowAllTime;
            this.breathRateFastAllTime = breathRateFastAllTime;
            this.breathRateSlowAllTime = breathRateSlowAllTime;
            this.sleepScore = sleepScore;
            this.sleepCurveArray = sleepCurveArray;
            this.sleepCurveStatusArray = sleepCurveStatusArray;
            this.algorithmVer = algorithmVer;
            this.fallsleepTimeStamp = fallsleepTimeStamp;
            this.wakeupTimeStamp = wakeupTimeStamp;
            this.reportFlag = reportFlag;
            this.breathRateStatusAry = breathRateStatusAry;
            this.heartRateStatusAry = heartRateStatusAry;
            this.leftBedStatusAry = leftBedStatusAry;
            this.turnOverStatusAry = turnOverStatusAry;
            this.md_body_move_decrease_scale = md_body_move_decrease_scale;
            this.md_leave_bed_decrease_scale = md_leave_bed_decrease_scale;
            this.md_wake_cnt_decrease_scale = md_wake_cnt_decrease_scale;
            this.md_start_time_decrease_scale = md_start_time_decrease_scale;
            this.md_fall_asleep_time_decrease_scale = md_fall_asleep_time_decrease_scale;
            this.md_perc_deep_decrease_scale = md_perc_deep_decrease_scale;
            this.md_sleep_time_decrease_scale = md_sleep_time_decrease_scale;
            this.md_sleep_time_increase_scale = md_sleep_time_increase_scale;
            this.md_breath_stop_decrease_scale = md_breath_stop_decrease_scale;
            this.md_heart_stop_decrease_scale = md_heart_stop_decrease_scale;
            this.md_heart_low_decrease_scale = md_heart_low_decrease_scale;
            this.md_heart_high_decrease_scale = md_heart_high_decrease_scale;
            this.md_breath_low_decrease_scale = md_breath_low_decrease_scale;
            this.md_breath_high_decrease_scale = md_breath_high_decrease_scale;
            this.md_perc_effective_sleep_decrease_scale = md_perc_effective_sleep_decrease_scale;
        }

        public int averageBreathRate;
        public int averageHeartBeatRate;
        public int fallAlseepAllTime;
        public int wakeAndLeaveBedBeforeAllTime;
        public int leaveBedTimes;
        public int trunOverTimes;
        public int bodyMovementTimes;
        public int heartBeatPauseTimes;
        public int breathPauseTimes;
        public int deepSleepPerc;
        public int inSleepPerc;
        public int lightSleepPerc;
        public int wakeSleepPerc;
        public int duration;
        public int wakeTimes;
        public int lightSleepAllTime;
        public int inSleepAllTime;
        public int deepSleepAllTime;
        public int wakeAllTime;
        public int breathPauseAllTime;
        public int heartBeatPauseAllTime;
        public int leaveBedAllTime;
        public int maxHeartBeatRate;
        public int maxBreathRate;
        public int minHeartBeatRate;
        public int minBreathRate;
        public int heartBeatRateFastAllTime;
        public int heartBeatRateSlowAllTime;
        public int breathRateFastAllTime;
        public int breathRateSlowAllTime;
        public int sleepScore;
        public float[] sleepCurveArray;
        public short[] sleepCurveStatusArray;
        public String algorithmVer;
        public int fallsleepTimeStamp;
        public int wakeupTimeStamp;
        public int reportFlag;
        public int[] breathRateStatusAry;
        public int[] heartRateStatusAry;
        public int[] leftBedStatusAry;
        public int[] turnOverStatusAry;
        public short md_body_move_decrease_scale;
        public short md_leave_bed_decrease_scale;
        public short md_wake_cnt_decrease_scale;
        public short md_start_time_decrease_scale;
        public short md_fall_asleep_time_decrease_scale;
        public short md_perc_deep_decrease_scale;
        public short md_sleep_time_decrease_scale;
        public short md_sleep_time_increase_scale;
        public short md_breath_stop_decrease_scale;
        public short md_heart_stop_decrease_scale;
        public short md_heart_low_decrease_scale;
        public short md_heart_high_decrease_scale;
        public short md_breath_low_decrease_scale;
        public short md_breath_high_decrease_scale;
        public short md_perc_effective_sleep_decrease_scale;

        @Override
        public String toString() {
            return "Analysis{" +
                    "averageBreathRate=" + averageBreathRate +
                    ", averageHeartBeatRate=" + averageHeartBeatRate +
                    ", fallAlseepAllTime=" + fallAlseepAllTime +
                    ", wakeAndLeaveBedBeforeAllTime=" + wakeAndLeaveBedBeforeAllTime +
                    ", leaveBedTimes=" + leaveBedTimes +
                    ", trunOverTimes=" + trunOverTimes +
                    ", bodyMovementTimes=" + bodyMovementTimes +
                    ", heartBeatPauseTimes=" + heartBeatPauseTimes +
                    ", breathPauseTimes=" + breathPauseTimes +
                    ", deepSleepPerc=" + deepSleepPerc +
                    ", inSleepPerc=" + inSleepPerc +
                    ", lightSleepPerc=" + lightSleepPerc +
                    ", wakeSleepPerc=" + wakeSleepPerc +
                    ", duration=" + duration +
                    ", wakeTimes=" + wakeTimes +
                    ", lightSleepAllTime=" + lightSleepAllTime +
                    ", inSleepAllTime=" + inSleepAllTime +
                    ", deepSleepAllTime=" + deepSleepAllTime +
                    ", wakeAllTime=" + wakeAllTime +
                    ", breathPauseAllTime=" + breathPauseAllTime +
                    ", heartBeatPauseAllTime=" + heartBeatPauseAllTime +
                    ", leaveBedAllTime=" + leaveBedAllTime +
                    ", maxHeartBeatRate=" + maxHeartBeatRate +
                    ", maxBreathRate=" + maxBreathRate +
                    ", minHeartBeatRate=" + minHeartBeatRate +
                    ", minBreathRate=" + minBreathRate +
                    ", heartBeatRateFastAllTime=" + heartBeatRateFastAllTime +
                    ", heartBeatRateSlowAllTime=" + heartBeatRateSlowAllTime +
                    ", breathRateFastAllTime=" + breathRateFastAllTime +
                    ", breathRateSlowAllTime=" + breathRateSlowAllTime +
                    ", sleepScore=" + sleepScore +
                    ", sleepCurveArray=" + Arrays.toString(sleepCurveArray) +
                    ", sleepCurveStatusArray=" + Arrays.toString(sleepCurveStatusArray) +
                    ", algorithmVer='" + algorithmVer + '\'' +
                    ", fallsleepTimeStamp=" + fallsleepTimeStamp +
                    ", wakeupTimeStamp=" + wakeupTimeStamp +
                    ", reportFlag=" + reportFlag +
                    ", breathRateStatusAry=" + Arrays.toString(breathRateStatusAry) +
                    ", heartRateStatusAry=" + Arrays.toString(heartRateStatusAry) +
                    ", leftBedStatusAry=" + Arrays.toString(leftBedStatusAry) +
                    ", turnOverStatusAry=" + Arrays.toString(turnOverStatusAry) +
                    ", md_body_move_decrease_scale=" + md_body_move_decrease_scale +
                    ", md_leave_bed_decrease_scale=" + md_leave_bed_decrease_scale +
                    ", md_wake_cnt_decrease_scale=" + md_wake_cnt_decrease_scale +
                    ", md_start_time_decrease_scale=" + md_start_time_decrease_scale +
                    ", md_fall_asleep_time_decrease_scale=" + md_fall_asleep_time_decrease_scale +
                    ", md_perc_deep_decrease_scale=" + md_perc_deep_decrease_scale +
                    ", md_sleep_time_decrease_scale=" + md_sleep_time_decrease_scale +
                    ", md_sleep_time_increase_scale=" + md_sleep_time_increase_scale +
                    ", md_breath_stop_decrease_scale=" + md_breath_stop_decrease_scale +
                    ", md_heart_stop_decrease_scale=" + md_heart_stop_decrease_scale +
                    ", md_heart_low_decrease_scale=" + md_heart_low_decrease_scale +
                    ", md_heart_high_decrease_scale=" + md_heart_high_decrease_scale +
                    ", md_breath_low_decrease_scale=" + md_breath_low_decrease_scale +
                    ", md_breath_high_decrease_scale=" + md_breath_high_decrease_scale +
                    ", md_perc_effective_sleep_decrease_scale=" + md_perc_effective_sleep_decrease_scale +
                    '}';
        }

}
