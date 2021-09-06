package com.isport.brandapp.device.scale;

public enum ColorStandard {
	OBESITYDEGREE1(-Float.MAX_VALUE,-20.0f, "#84D5FD", "消瘦"),
	OBESITYDEGREE2(-20.0f,-10.0f, "#4BC4FF", "偏瘦"),
	OBESITYDEGREE3(-10.0f,10.0f, "#50E3C2", "标准"),
	OBESITYDEGREE4(10.0f,20.0f, "#FFD100", "超重"),
	OBESITYDEGREE5(20.0f,30.0f, "#FD944A", "轻度"),
	OBESITYDEGREE6(30.0f,50.0f, "#FA5F9B", "中度"),
	OBESITYDEGREE7(50.0f,Float.MAX_VALUE, "#FA5F5F", "重度"),

	WEIGHT1(-Float.MAX_VALUE,57.96f, "#4BC4FF", "偏瘦"),
	WEIGHT2(57.96f,75.19f, "#50E3C2", "标准"),
	WEIGHT3(75.19f,87.72f, "#FFD100", "偏胖"),
	WEIGHT4(87.72f,93.99f, "#FD944A", "肥胖"),
	WEIGHT5(93.99f,Float.MAX_VALUE, "#FA5F5F", "重度"),

	BMI1(-Float.MAX_VALUE,18.5f, "#4BC4FF", "偏瘦"),
	BMI2(18.5f,24.0f, "#50E3C2", "标准"),
	BMI3(24.0f,28.0f, "#FFD100", "偏胖"),
	BMI4(28.0f,30.0f, "#FD944A", "肥胖"),
	BMI5(30.0f,Float.MAX_VALUE, "#FA5F5F", "重度"),
	
	PERCENTAGEFAT1(-Float.MAX_VALUE,11.0f, "#4BC4FF", "偏瘦"),
	PERCENTAGEFAT2(11.0f,17.0f, "#50E3C2", "标准"),
	PERCENTAGEFAT3(17.0f,27.0f, "#FFD100", "偏胖"),
	PERCENTAGEFAT4(27.0f,Float.MAX_VALUE, "#FA5F5F", "肥胖"),

	SKELETALMUSCLE1(-Float.MAX_VALUE,31.0f, "#FA5F5F", "不足"),
	SKELETALMUSCLE2(31.0f,39.0f, "#50E3C2", "标准"),
	SKELETALMUSCLE3(39.0f,Float.MAX_VALUE, "#50E386", "优"),

	MUSCLEWEIGHT1(-Float.MAX_VALUE,49.40f, "#FA5F5F", "不足"),
	MUSCLEWEIGHT2(49.40f,59.40f, "#50E3C2", "标准"),
	MUSCLEWEIGHT3(59.40f,Float.MAX_VALUE, "#50E386", "优"),

	VISCERALFATLEVEL1(-Float.MAX_VALUE,9.0f, "#50E3C2", "标准"),
	VISCERALFATLEVEL2(9.0f,14.0f, "#FFD100", "超标"),
	VISCERALFATLEVEL3(14.0f,Float.MAX_VALUE, "#FA5F5F", "过多"),

	PERCENTWATERCONTENT1(-Float.MAX_VALUE,53.6f, "#FA5F5F", "不足"),
	PERCENTWATERCONTENT2(53.6f,57.0f, "#50E3C2", "标准"),
	PERCENTWATERCONTENT3(57.0f,Float.MAX_VALUE, "#50E386", "优"),

	BASALMETABOLISM1(-Float.MAX_VALUE,1559.2f, "#FA5F5F", "未达标"),
	BASALMETABOLISM2(1559.2f,Float.MAX_VALUE, "#50E3C2", "达标"),

	INORGANICSALT1(-Float.MAX_VALUE,2.40f, "#FA5F5F", "偏低"),
	INORGANICSALT2(2.40f,3.10f, "#50E3C2", "标准"),
	INORGANICSALT3(3.10f,Float.MAX_VALUE, "#50E386", "优"),

	PROTEIN1(-Float.MAX_VALUE,16.0f, "#4BC4FF", "偏低"),
	PROTEIN2(16.0f,20.0f, "#50E3C2", "标准"),
	PROTEIN3(20.0f,Float.MAX_VALUE, "#FFD100", "偏高");

	private float minNum;
	private float maxNum;
	private String color;
	private String standard;

	private
	ColorStandard(float minNum, float maxNum, String color,String standard) {
		this.minNum = minNum;
		this.maxNum = maxNum;
		this.color = color;
		this.standard = standard;

	}
	public float getMinNum() {
		return minNum;
	}
	public float getMaxNum() {
		return maxNum;
	}
	public String getColor() {
		return color;
	}
	public String getStandard() {
		return standard;
	}

	
}
