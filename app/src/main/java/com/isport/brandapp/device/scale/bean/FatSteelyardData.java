package com.isport.brandapp.device.scale.bean;


/**
 * @Author
 * @Date 2018/10/24
 * @Fuction 体脂称上传测试数据
 */

public class FatSteelyardData {
    private String weight;
    private String extracellularFluid;
    private String intracellularFluid;
    private String totalWaterWeight;
    private String percentWaterContent;
    private String loseFatWeight;
    private String muscleWeight;
    private String protein;
    private String fatWeight;
    private String percentageFat;
    private String edemaTest;
    private String obesityDegree;
    private String muscleControl;
    private String weightControl;
    private String basalMetabolism;
    private String inorganicSalt;
    private String visceralFatLevel;
    private String individualScore;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getExtracellularFluid() {
        return extracellularFluid;
    }

    public void setExtracellularFluid(String extracellularFluid) {
        this.extracellularFluid = extracellularFluid;
    }

    public String getIntracellularFluid() {
        return intracellularFluid;
    }

    public void setIntracellularFluid(String intracellularFluid) {
        this.intracellularFluid = intracellularFluid;
    }

    public String getTotalWaterWeight() {
        return totalWaterWeight;
    }

    public void setTotalWaterWeight(String totalWaterWeight) {
        this.totalWaterWeight = totalWaterWeight;
    }

    public String getPercentWaterContent() {
        return percentWaterContent;
    }

    public void setPercentWaterContent(String percentWaterContent) {
        this.percentWaterContent = percentWaterContent;
    }

    public String getLoseFatWeight() {
        return loseFatWeight;
    }

    public void setLoseFatWeight(String loseFatWeight) {
        this.loseFatWeight = loseFatWeight;
    }

    public String getMuscleWeight() {
        return muscleWeight;
    }

    public void setMuscleWeight(String muscleWeight) {
        this.muscleWeight = muscleWeight;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFatWeight() {
        return fatWeight;
    }

    public void setFatWeight(String fatWeight) {
        this.fatWeight = fatWeight;
    }

    public String getPercentageFat() {
        return percentageFat;
    }

    public void setPercentageFat(String percentageFat) {
        this.percentageFat = percentageFat;
    }

    public String getEdemaTest() {
        return edemaTest;
    }

    public void setEdemaTest(String edemaTest) {
        this.edemaTest = edemaTest;
    }

    public String getObesityDegree() {
        return obesityDegree;
    }

    public void setObesityDegree(String obesityDegree) {
        this.obesityDegree = obesityDegree;
    }

    public String getMuscleControl() {
        return muscleControl;
    }

    public void setMuscleControl(String muscleControl) {
        this.muscleControl = muscleControl;
    }

    public String getWeightControl() {
        return weightControl;
    }

    public void setWeightControl(String weightControl) {
        this.weightControl = weightControl;
    }

    public String getBasalMetabolism() {
        return basalMetabolism;
    }

    public void setBasalMetabolism(String basalMetabolism) {
        this.basalMetabolism = basalMetabolism;
    }

    public String getInorganicSalt() {
        return inorganicSalt;
    }

    public void setInorganicSalt(String inorganicSalt) {
        this.inorganicSalt = inorganicSalt;
    }

    public String getVisceralFatLevel() {
        return visceralFatLevel;
    }

    public void setVisceralFatLevel(String visceralFatLevel) {
        this.visceralFatLevel = visceralFatLevel;
    }

    public String getIndividualScore() {
        return individualScore;
    }

    public void setIndividualScore(String individualScore) {
        this.individualScore = individualScore;
    }

    public FatSteelyardData() {
    }

    public FatSteelyardData(String weight, String extracellularFluid, String intracellularFluid, String
            totalWaterWeight, String percentWaterContent, String loseFatWeight, String muscleWeight, String protein,
                            String fatWeight, String percentageFat, String edemaTest, String obesityDegree, String
                                    muscleControl, String weightControl, String basalMetabolism, String
                                    inorganicSalt, String visceralFatLevel, String individualScore) {
        this.weight = weight;
        this.extracellularFluid = extracellularFluid;
        this.intracellularFluid = intracellularFluid;
        this.totalWaterWeight = totalWaterWeight;
        this.percentWaterContent = percentWaterContent;
        this.loseFatWeight = loseFatWeight;
        this.muscleWeight = muscleWeight;
        this.protein = protein;
        this.fatWeight = fatWeight;
        this.percentageFat = percentageFat;
        this.edemaTest = edemaTest;
        this.obesityDegree = obesityDegree;
        this.muscleControl = muscleControl;
        this.weightControl = weightControl;
        this.basalMetabolism = basalMetabolism;
        this.inorganicSalt = inorganicSalt;
        this.visceralFatLevel = visceralFatLevel;
        this.individualScore = individualScore;
    }

    @Override
    public String toString() {
        return "FatSteelyardData{" +
                "weight='" + weight + '\'' +
                ", extracellularFluid='" + extracellularFluid + '\'' +
                ", intracellularFluid='" + intracellularFluid + '\'' +
                ", totalWaterWeight='" + totalWaterWeight + '\'' +
                ", percentWaterContent='" + percentWaterContent + '\'' +
                ", loseFatWeight='" + loseFatWeight + '\'' +
                ", muscleWeight='" + muscleWeight + '\'' +
                ", protein='" + protein + '\'' +
                ", fatWeight='" + fatWeight + '\'' +
                ", percentageFat='" + percentageFat + '\'' +
                ", edemaTest='" + edemaTest + '\'' +
                ", obesityDegree='" + obesityDegree + '\'' +
                ", muscleControl='" + muscleControl + '\'' +
                ", weightControl='" + weightControl + '\'' +
                ", basalMetabolism='" + basalMetabolism + '\'' +
                ", inorganicSalt='" + inorganicSalt + '\'' +
                ", visceralFatLevel='" + visceralFatLevel + '\'' +
                ", individualScore='" + individualScore + '\'' +
                '}';
    }

//    public JSONObject createJson() {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("weight", weight + "_kg");
//            obj.put("extracellularFluid", extracellularFluid + "_");
//            obj.put("intracellularFluid", intracellularFluid + "_");
//            obj.put("totalWaterWeight", totalWaterWeight + "_");
//            obj.put("percentWaterContent", percentWaterContent + "_");
//            obj.put("loseFatWeight", loseFatWeight + "_");
//            obj.put("muscleWeight", muscleWeight + "_");
//            obj.put("protein", protein + "_");
//            obj.put("fatWeight", fatWeight + "_");
//            obj.put("percentageFat", percentageFat + "_");
//            obj.put("edemaTest", edemaTest + "_");
//            obj.put("obesityDegree", obesityDegree + "_");
//            obj.put("muscleControl", muscleControl + "_");
//            obj.put("weightControl", weightControl + "_");
//            obj.put("basalMetabolism", basalMetabolism + "_");
//            obj.put("inorganicSalt", inorganicSalt + "_");
//            obj.put("visceralFatLevel", visceralFatLevel + "_");
//            obj.put("individualScore", individualScore + "_");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //调用toString()方法可直接将其内容打印出来
//        return obj;
//    }
}
