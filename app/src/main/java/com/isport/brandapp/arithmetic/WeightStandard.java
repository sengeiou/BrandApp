package com.isport.brandapp.arithmetic;

import java.util.ArrayList;

public interface WeightStandard {

    /**
     * 身体得分（综合得分）标准的算法
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> compositeScoreStandardWithValue(String valueStr);


    /**
     * bmi标准的算法,体重的结果等于bmi的结果
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> bmiStandardWithValue(double valueStr);


    /**
     * 体脂率标准的算法 ，脂肪重量的结果等于体脂率的结果
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> bfpStandardWithValue(double valueStr,String gender,int age );


    /**
     * 肌肉量标准的算法，肌肉率的结果等于肌肉量
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> muscle_massStandardWithValue(double valueStr, String gender, int height);

    /**
     * 水分率标准的算法，水份的结果等于水分率
     *
     * @param valueStr 入参，注意是水分率
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> bwpStandardWithValue(double valueStr,String gender);


    /**
     * 骨量标准的算法
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> bone_massStandardWithValue(double valueStr, String gender,int weight);

    /**
     * 蛋白质标准的算法
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> proteinStandardWithValue(double valueStr);


    /**
     * 基础代谢标准的算法
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> basal_metabolismStandardWithValue(double valueStr,String gender,int age);

    /**
     * 内脏脂肪等级标准的算法
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> visceral_fatStandardWithValue(double valueStr);


    /**
     * 骨骼肌重量标准的算法，骨骼肌率的结果等于骨骼肌重量的结果
     *
     * @param valueStr 入参
     * @return 返回值为array，index 0 =standard ，index 1=color
     */
    public ArrayList<String> skeleton_muscle_massStandardWithValue(double valueStr,String gender,int height);

}
