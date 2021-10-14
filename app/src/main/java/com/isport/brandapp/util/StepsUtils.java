package com.isport.brandapp.util;

import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.sport.bean.PaceBean;
import com.isport.brandapp.sport.service.InDoorService;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class StepsUtils {

    private static final String TAG = "StepsUtils";
    
    public static double countStepDis(String gender, float bodyHeight) {
        if(gender == null)
            gender = "Male";
        if(bodyHeight==0.0){
            bodyHeight = 175f;
        }
        double stepDistance = 0;
        if (gender.equals("Male")) {
            stepDistance = bodyHeight / 100.00 * 0.415;//步距，单位m
        } else {
            stepDistance = bodyHeight / 100.00 * 0.413;//步距，单位m
        }
        return stepDistance;
    }

    public static int countDistToStep(double dis, String gender, float bodyHeight) {
        int step = 0;
        step = (int) (dis * 1000 / countStepDis(gender, bodyHeight));
        return step;
    }

    public static double countStepDis(String gender, float bodyHeight,int normal) {
        double stepDistance = 0;
        if (gender.equals("Male")) {
            stepDistance = bodyHeight / 100.00 * 0.415;//步距，单位m
        } else {
            stepDistance = bodyHeight / 100.00 * 0.413;//步距，单位m
        }
        return stepDistance;
    }




    public static double countDistanceUseStep(long step, String gender, float bodyHeight) {
        double distance = 0;
        distance = step * countStepDis(gender, bodyHeight);
        return distance;
       /* if (step % 2 == 0) {
            distance = (step / 2) * 3 * 70 * 0.01;
        } else {
            distance = ((step / 2) * 3 + 1) * 70 * 0.01;
        }
        return distance;*/
    }



    public static PaceBean calPace(float distance, long dTime, long currentSeced) {

        Logger.myLog(TAG,"----distance="+distance+"\n"+dTime+"\n"+currentSeced);

        Logger.myLog(TAG,"----当前瞬时速度="+(divi(distance,5,2)));

        if(distance <1){
            return new PaceBean(0,currentSeced,"--");
        }
        
        //分钟 25.6 25分钟6*60秒
        PaceBean paceBean = new PaceBean();

        //根据距离和时间算出当前瞬时速度 单位米/s
        double momentSpeed = divi(distance,5,2);
        //得到km/小时
        double tmpMomentSpeed = mul(momentSpeed,3.6);

        //60 / km/s得到配速
        double currPace = divi(60,tmpMomentSpeed,2);

        Logger.myLog(TAG,"----转换配速="+currPace);

        //转换成0'0''格式
        //小数点后的
        String afterStr = StringUtils.substringAfter(currPace+"",".");
        //小数点前的
        String beforeStr = StringUtils.substringBefore(currPace+"",".");

        int tmpAfter = Integer.parseInt(afterStr);
        int tmpBefore = Integer.parseInt(beforeStr);

        if(tmpAfter ==0 && tmpBefore == 0)
            return new PaceBean(0,currentSeced,"--");
        return new PaceBean(tmpBefore*60+tmpBefore,currentSeced,anyPace(tmpBefore,tmpAfter));





//        double pace;
//
//        if (distance == 0) {
//            pace = 0;
//            paceBean.setPace("00\'00\"");
//            paceBean.setStrPace(0);
//        } else {
//            pace = 1000.0 / (distance / dTime * 60);
//
//            int intpace = (int) (pace * 10);
//            int second = intpace % 10;
//            int min = intpace / 10;
//
//            int resultSecod = 0;
//            int resultMin = 0;
//
//            //String strPace = CommonDateUtil.formatOnePoint((float)pace);
//            //  String[] strsPace = strPace.split(".");
//            resultMin = min * 60;
//            resultSecod = second * 60 / 10;
//            paceBean.setPace(min + "\'" + CommonDateUtil.formatTwoStr(resultSecod) + "\"");
//            paceBean.setStrPace(resultMin + resultSecod);
//            paceBean.setTime(currentSeced);
//
//           /* String strPace = CommonDateUtil.formatOnePoint(pace);
//            String[] strsPace = strPace.split(".");
//            int min = Integer.parseInt(strsPace[0]) * 60;
//            int second = Integer.parseInt(strsPace[1]) * 60 / 10;
//            paceBean.setPace(min + "\'" + CommonDateUtil.formatTwoStr(second) + "\"");
//            paceBean.setStrPace(min + second);*/
//        }
//
//        paceBean.setTime(currentSeced);
//        return paceBean;

    }

    public static PaceBean calPaceBean(double pace, long currentSeced) {


        if (pace == 0) {
            return null;
        } else {
            int intpace = (int) (pace * 10);
            int second = intpace % 10;
            int min = intpace / 10;

            int resultSecod = 0;
            int resultMin = 0;

            PaceBean paceBean = new PaceBean();
            //String strPace = CommonDateUtil.formatOnePoint((float)pace);
            //  String[] strsPace = strPace.split(".");
            resultMin = min * 60;
            resultSecod = second * 60 / 10;
            paceBean.setPace(min + "\'" + CommonDateUtil.formatTwoStr(resultSecod) + "\"");
            paceBean.setStrPace(resultMin + resultSecod);
            paceBean.setTime(currentSeced);
            return paceBean;
        }

    }

    public static PaceBean calPaceBean(double pace) {


        if (pace == 0) {
            return new PaceBean();
        } else {
            int intpace = (int) (pace * 10);
            int second = intpace % 10;
            int min = intpace / 10;

            int resultSecod = 0;
            int resultMin = 0;

            PaceBean paceBean = new PaceBean();
            //String strPace = CommonDateUtil.formatOnePoint((float)pace);
            //  String[] strsPace = strPace.split(".");
            resultMin = min * 60;
            resultSecod = second * 60 / 10;
            paceBean.setPace(min + "\'" + CommonDateUtil.formatTwoStr(resultSecod) + "\"");
            paceBean.setStrPace(resultMin + resultSecod);
            return paceBean;
        }

    }


    public static double calCalorie(float weight, double distance, int sportType) {
        //distance 公里

        float[] k = new float[]{1.036f, 1.036f, 0.6142f, 0.8217f};
        double calorie = weight * (distance / 1000) * k[sportType];
        return calorie;
    }

    public static synchronized double getPhoneRunVelocity(double distance, long timer) {
        double velocity = 0;

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

        return velocity;
    }

    /**
     * 两个double相除，保留小数
     * @param d1 被除数
     * @param d2 除数
     * @param point 保留几位小数
     * @return
     */
    public static double divi(double d1,double d2,int point){
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,point, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个double相乘
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Double mul(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.multiply(b2).doubleValue();
    }


    private static String anyPace(int beforeV, int afterV){
        String tmpBefore;
        tmpBefore = beforeV<=9? ("0"+beforeV+"'"):beforeV+"'";
        String tmpAfter;
        tmpAfter = afterV<=9?("0"+afterV+"'"):afterV+"''";
        return tmpBefore+tmpAfter;
    }
}
