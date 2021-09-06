
package com.isport.brandapp.sport.service.persenter;

import com.amap.api.maps.model.LatLng;
import com.isport.brandapp.sport.bean.IndoorRunDatas;
import com.isport.brandapp.sport.service.IndoorRunningServiceView;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BasePresenter;

/**
 * ClassName:StepServicePersenter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2017年3月6日 下午5:00:22 <br/>
 *
 * @author Administrator
 */
public class IndoorRunningServicePersenter extends BasePresenter<IndoorRunningServiceView> {


    public IndoorRunningServicePersenter() {
        super();
    }


    public double getaveragePace(float distance, long timer) {
        double averageVelocity = (distance * 1000 * 3.6) / timer; // 计算平均速度
        double avgSpace = 60.0 / averageVelocity;
        return avgSpace;
    }

    public double getRealTimePace() {
        return 0.0;
    }








    /**
     * 如果距离有变化，则记录距离变化数值和时间变化数值，加入队列计算速度。
     * 如果距离没有变化，且时间超过5秒钟。则将5秒以及0distance加入队列，计算速度。 当经过4个5秒钟，速度减为0.
     */


    // /**
    // *
    // * DESC : 初始化计步器的步数，以及服务开启了多长时间。 . <br/>
    // *
    // * @param stepSharedPreferences 存储步数和时间的sp
    // * @return 返回服务累计的运行时间。
    // */
    // public long initStep(SharedPreferences stepSharedPreferences) {
    //
    // long mRunTime;
    // String step =
    // stepSharedPreferences.getString(AllocationApi.SpStringTag.STEP_NUMBER,
    // "");
    // Logger.e("tag", "step : " + step);
    // String phoneTime =
    // stepSharedPreferences.getString(AllocationApi.SpStringTag.PHONE_TIME,
    // "");
    // String nowPhoneTime = PhoneMessageUtil.getPhoneTime();
    // long serviceRunTime =
    // stepSharedPreferences.getLong(AllocationApi.SpStringTag.SERVICE_RUN_TIME,
    // 0);
    // if (step.equals("") || !phoneTime.equals(nowPhoneTime)) {
    // StepDetectorMain.mCurrentStepNumber = 0;
    // } else {
    // StepDetectorMain.mCurrentStepNumber = Integer.parseInt(step);
    // }
    //
    // if (0 == serviceRunTime || !phoneTime.equals(nowPhoneTime)) {
    // mRunTime = 0;
    // } else {
    // mRunTime = serviceRunTime;
    // }
    // return mRunTime;
    //
    // }

    /* public IndoorRunDatas getTheMomentRunData(int weight, int step_length) {
     *//**
     * 1.先判断蓝牙是否连接 a.蓝牙未连接，选择使用手机传感器数据。 b.如果蓝牙连接，判断是否为手环
     * b1.是手环，使用手环数据。b2.是心率带，使用手机传感器数据。
     *//*
        String kitType = getKitType();
        boolean[] dataTypes = getDataTypes(kitType);//很重要，判断当前的设备类型。
        boolean isNeedRing = dataTypes[0]; // 记录是否用手环数据.true为使用手环数据。
        boolean isHeartRateStep = dataTypes[1];// 是否采用心率带的步数。
        boolean isHeartRate = dataTypes[2];// 记录是否有心率数据

        // 热量：卡路里
        Double calories = 0.0;
        // 速度：米每秒
        Double velocity = 0.0;

        long timer = 0;
        String strTime = "0"; // 运动时常

        Double distance = 0.0;// 运动距离
        int totalStep = 0;// 总步数

        double averageVelocity = 0.0;// 平均速度
        double instantVelocity = 0.0;// 即时速度。

        String heartRate = "0";
        ArrayList<Object> dataList;

        // 1.先判断是不是手环。
        // A.是手环。步数，卡路里，距离采用手机数据。
        // a1.支持心率，记录心率值
        // a2.不支持心率，不记录心率值。
        // B.不是手环，分三种情况。
        // b1.是心率带，且支持计步功能。-----使用心率带计步，心率带心率。
        // b2.采用手机数据。
        // b21.用手机计步，采用心率带心率。
        // b22.用手机计步，不收集心率。

        // 1.先判断是不是手环。
        if (isNeedRing) {

            // A.是手环。步数，卡路里，距离采用手机数据。

            *//**
     * 如果蓝牙连接，则显示蓝牙数据。
     * 1.记录首次获取的步数，卡路里，距离，心率。(在服务里面设置一个boolean值来确保是第一次获取的数据)
     * 2.然后后面每次进来的数据，都减去第一次获取的数据，进而得到这段时间里面的数据。
     * 3.实时配速为距离每增加一个精度，然后取出精度内的时间。进行计算
     *//*
            dataList = getTheMomentBleRunData();
            timer = (long) dataList.get(0);
            totalStep = (int) dataList.get(1);// 记录手环获取的运动步数
            calories = (double) dataList.get(2);// 记录手环获取的运动卡路里
            distance = (double) dataList.get(3);// 记录距离

            *//**
     * 如果距离有变化，则记录距离变化数值和时间变化数值，加入队列计算速度。
     * 如果距离没有变化，且时间超过5秒钟。则将5秒以及0distance加入队列，计算速度。 当经过4个5秒钟，速度减为0.
     *//*
            instantVelocity = getRingRunVelocity(distance, timer);
            averageVelocity = (distance * 1000 * 3.6) / timer; // 计算平均速度

        } else {

            // 不是手环，分三种情况。
            if (isHeartRate && isHeartRateStep) {
                // A.是心率带，且支持计步功能。-----使用心率带计步，心率带心率。
                dataList = getTheMomentHeartRateKitData(weight, step_length);
                timer = (long) dataList.get(0);
                totalStep = (int) dataList.get(1);// 记录手环获取的运动步数
                calories = (double) dataList.get(2);// 记录手环获取的运动卡路里
                distance = (double) dataList.get(3);// 记录距离

                *//**
     * 如果距离有变化，则记录距离变化数值和时间变化数值，加入队列计算速度。
     * 如果距离没有变化，且时间超过5秒钟。则将5秒以及0distance加入队列，计算速度。 当经过4个5秒钟，速度减为0.
     *//*
                instantVelocity = getHeartRateRunVelocity(distance, timer);
                averageVelocity = (distance * 1000 * 3.6) / timer; // 计算平均速度

            } else {
                // B.采用手机数据。
                dataList = getTheMomentPhoneData(weight, step_length);
                timer = (long) dataList.get(0);
                totalStep = (int) dataList.get(1);// 记录手环获取的运动步数
                calories = (double) dataList.get(2);// 记录手环获取的运动卡路里
                distance = (double) dataList.get(3);// 记录距离

                */

    /**
     * 如果距离有变化，则记录距离变化数值和时间变化数值，加入队列计算速度。
     * 如果距离没有变化，且时间超过5秒钟。则将5秒以及0distance加入队列，计算速度。 当经过4个5秒钟，速度减为0.
     *//*
                instantVelocity = getPhoneRunVelocity(distance, timer);
                averageVelocity = (distance * 1000 * 3.6) / timer; // 计算平均速度
            }
        }

        // 计算 距离，步数，时间，卡路里，时速，--，配速。
        strTime = DateUtils.getFormatTime(timer * 1000);
        heartRate = getHeartRate(isHeartRate);// 获取设备的心率数据。

        IndoorRunDatas indoorRunDatas = new IndoorRunDatas();
        indoorRunDatas.setTime(strTime).setTimer(timer).setDistance(distance).setTotalStep(totalStep)
                .setCalories(calories).setVelocity(velocity).setAverageVelocity(averageVelocity)
                .setInstantVelocity(instantVelocity).setHeartRate(heartRate).setKitType(kitType);
        return indoorRunDatas;
    }*/
    public boolean[] getDataTypes(String kitType) {

        boolean isNeedRing = false; // 记录是否用手环数据.true为使用手环数据。
        boolean isHeartRateStep = false;// 是否采用心率带的步数。
        boolean isHeartRate = false;// 记录是否有心率数据

        switch (kitType) {
            case IndoorRunDatas.JUST_PHONE:
                isNeedRing = false;
                isHeartRate = false;
                isHeartRateStep = false;
                break;
            case IndoorRunDatas.RING_WITH_HEART_RATE:
                isNeedRing = true;
                isHeartRate = true;
                isHeartRateStep = false;
                break;
            case IndoorRunDatas.RING_WITHOUT_HEART_RATE:
                isNeedRing = true;
                isHeartRate = false;
                isHeartRateStep = false;
                break;
            case IndoorRunDatas.HEART_RATE_WITH_STEP:
                isNeedRing = false;
                isHeartRate = true;
                isHeartRateStep = true;
                break;
            case IndoorRunDatas.HEART_RATE_WITHOUT_STEP:
                isNeedRing = false;
                isHeartRate = true;
                isHeartRateStep = false;
                break;
            default:
                break;
        }

        return new boolean[]{isNeedRing, isHeartRateStep, isHeartRate};
    }

    /**
     * DESC :获取蓝牙中存储的数据已经时间 . <br/>
     * <p>
     * 如果蓝牙连接，则显示蓝牙数据。 1.记录首次获取的步数，卡路里，距离，心率。(在服务里面设置一个boolean值来确保是第一次获取的数据)
     * 2.然后后面每次进来的数据，都减去第一次获取的数据，进而得到这段时间里面的数据。 3.实时配速为距离每增加一个精度，然后取出精度内的时间。进行计算
     *
     * @return 将结果用list集合存起来，定义成object类。按照顺序，存特性类型：long，int,double,double,String。在使用时取出数据强转就可以。
     */
    public ArrayList<TimeLineWithDistance> mBleRunRecord = new ArrayList<TimeLineWithDistance>();
    private int mBleDistance = -1;


    /**
     * DESC : 获取这个时刻，心率带记录的跑步数据 . <br/>
     *
     * @param step_length
     * @param weight
     * @return
     */
    public ArrayList<TimeLineWithDistance> mHeartRateRunRecord = new ArrayList<TimeLineWithDistance>();
    private int mHeartRateDistance = -1;


    /**
     * DESC : 获取这个时刻，手机记录的心率数据 . <br/>
     *
     * @param weight
     * @param step_length
     * @return
     */
    public ArrayList<TimeLineWithDistance> mPhoneRunRecord = new ArrayList<TimeLineWithDistance>();
    private int mPhoneDistance = -1;


    /**
     * 用步数计算距离。
     */
    private double countDistanceUseStep(int step, int step_length) {
        double distance;
        if (step % 2 == 0) {
            distance = (step / 2) * 3 * step_length * 0.01;
        } else {
            distance = ((step / 2) * 3 + 1) * step_length * 0.01;
        }
        return distance;
    }

    public class TimeLineWithDistance {

        public long timeLine;
        public double distanceLine;

        public TimeLineWithDistance(long timeLine, double distanceLine) {
            super();
            this.timeLine = timeLine;
            this.distanceLine = distanceLine;
        }

    }

    /**
     * 如果距离有变化，则记录距离变化数值和时间变化数值，加入队列计算速度。
     * 如果距离没有变化，且时间超过5秒钟。则将5秒以及0distance加入队列，计算速度。 当经过4个5秒钟，速度减为0.
     */
   /* public double getRingRunVelocity(double distance, long timer) {
        double velocity = 0;
        // 如果距离有变化，则将时间和距离存入数组。
        if (distance - IndoorRunningService.argsForInRunService.preRingDistance != 0) {
            velocity = IndoorRunningService.arrayThreeRingVelocityTool.addValueAndGetVelocity(timer, distance);
            IndoorRunningService.argsForInRunService.preRingDistanceTime = timer;
            IndoorRunningService.argsForInRunService.preRingDistance = distance;
        } else if (distance - IndoorRunningService.argsForInRunService.preRingDistance == 0
                && timer - IndoorRunningService.argsForInRunService.preRingDistanceTime > 5) {
            velocity = IndoorRunningService.arrayThreeRingVelocityTool.clearValue(timer, distance);
            IndoorRunningService.argsForInRunService.preRingDistanceTime = timer;
        }
        return velocity;
    }*/

   /* public double getHeartRateRunVelocity(double distance, long timer) {
        double velocity = 0;
        // 如果距离有变化，则将时间和距离存入数组。
        if (distance - IndoorRunningService.argsForInRunService.preHeartRateDistance != 0) {
            velocity = IndoorRunningService.arrayThreeHeartRateVelocityTool.addValueAndGetVelocity(timer, distance);
            IndoorRunningService.argsForInRunService.preHeartRateDistanceTime = timer;
            IndoorRunningService.argsForInRunService.preHeartRateDistance = distance;
        } else if (distance - IndoorRunningService.argsForInRunService.preHeartRateDistance == 0
                && timer - IndoorRunningService.argsForInRunService.preHeartRateDistanceTime > 5) {
            velocity = IndoorRunningService.arrayThreeHeartRateVelocityTool.clearValue(timer, distance);
            IndoorRunningService.argsForInRunService.preHeartRateDistanceTime = timer;
        }
        return velocity;
    }*/

   /* public double getPhoneRunVelocity(double distance, long timer) {
        double velocity = 0;
        synchronized (IndoorRunningServicePersenter.class) {
            // 如果距离有变化，则将时间和距离存入数组。
            if (distance - IndoorRunningService.argsForInRunService.prePhoneDistance != 0 && IndoorRunningService.arrayThreePhoneVelocityTool != null) {
                velocity = IndoorRunningService.arrayThreePhoneVelocityTool.addValueAndGetVelocity(timer, distance);
                IndoorRunningService.argsForInRunService.prePhoneDistanceTime = timer;
                IndoorRunningService.argsForInRunService.prePhoneDistance = distance;
            } else if (distance - IndoorRunningService.argsForInRunService.prePhoneDistance == 0
                    && timer - IndoorRunningService.argsForInRunService.prePhoneDistanceTime > 5 && IndoorRunningService.arrayThreePhoneVelocityTool != null) {
                velocity = IndoorRunningService.arrayThreePhoneVelocityTool.clearValue(timer, distance);
                IndoorRunningService.argsForInRunService.prePhoneDistanceTime = timer;
            }
        }
        return velocity;
    }
*/


    public static class OutSportPase {
        //double distances[] = new double[2];
        ArrayList<LatLng> latLngs =new ArrayList<>(2);

        // 将当前时间，和距离加入的数组中。
        public double addValueAndGetVelocity(long thisTime, double distance) {
            // 首先排序数组，将所有值前移动一位，最后一个值空出来；
            moveValue();
            //distances[4] = distance;

            double velocity=0;
            // 计算瞬时速度。
            //double gapLength1 = distances[4] - distances[3];// 距离差值。
            //double gapLength2 = distances[3] - distances[2];// 距离差值。
            //double gapLength3 = distances[2] - distances[1];// 距离差值。
            //double gapLength4 = distances[1] - distances[0];// 距离差值。


            return velocity;
        }

        public double clearValue(long thisTime, double distance) {

            //distances[3] = distance;
            //distances[2] = distance;
            //distances[1] = distance;
            //distances[0] = distance;

            return Double.POSITIVE_INFINITY;
        }

        public void cleanAtRunFinish() {
            /*for (int i = 0; i < times.length; i++) {
                times[i] = 0;
            }

            for (int i = 0; i < distances.length; i++) {
                times[i] = 0;
            }*/
        }

        private void moveValue() {
            // 将所有值前移动一位，最后一个值空出来;
           /* distances[0] = distances[1];
            distances[1] = distances[2];
            distances[2] = distances[3];
            distances[3] = distances[4];
            distances[4] = 0;*/


        }

    }


   /* public double getPhoneRunVelocity(double distance, long timer) {
        double velocity = 0;
        synchronized (IndoorRunningServicePersenter.class) {
            // 如果距离有变化，则将时间和距离存入数组。
            if (distance - InDoorService.argsForInRunService.prePhoneDistance != 0 && InDoorService.arrayThreePhoneVelocityTool != null) {
                velocity = InDoorService.arrayThreePhoneVelocityTool.addValueAndGetVelocity(timer, distance);
                InDoorService.argsForInRunService.prePhoneDistanceTime = timer;
                InDoorService.argsForInRunService.prePhoneDistance = distance;
            } else if (distance - InDoorService.argsForInRunService.prePhoneDistance == 0
                    && timer - InDoorService.argsForInRunService.prePhoneDistanceTime > 5 && InDoorService.arrayThreePhoneVelocityTool != null) {
                velocity = InDoorService.arrayThreePhoneVelocityTool.clearValue(timer, distance);
                InDoorService.argsForInRunService.prePhoneDistanceTime = timer;
            }
        }
        return velocity;
    }*/


}
