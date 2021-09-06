package chipsea.bias.v235;

/**
 * Created by liangyc
 * Time :2018/11/26
 * Des:
 */

/*
测试数据
身高， 体重，阻抗，性别， 年龄
{170,    500,      4080,         1,        30},
{170,    1000,    4280,    1,    30},
{170,    1500,    4480,    1,    30},
{170,    500,    4080,    0,    30},
{170,    1000,    4280,    0,    30},
{170,    1500,    4480,    0,    30},
{170,    509,    4547,    1,    28},
{164,    790,    3797,    1,    28},
{174,    669,    4221,    1,    24},
{158,    603,    4388,    0,    21},
{163,    474,    4759,    0,    22},
{160,    553,    4549,    1,    26},
*/
/*
测试结果
BFP		SLM		BWP		BMC		VFR		PP		SMM		BMR		BMI		SBW		MC		WC		FC		age	score
5.0		45.3	62.9	2.2		1.0		27.7	26.0	1324	17.3	62.3	4.2		12.3	8.1		15	60
33.0	64.8	50.2	2.3		20.5	14.5	34.8	1981	34.6	62.3	- 15.3	- 37.7	- 22.4	56	50
42.7	84.2	46.0	1.7		41.5	10.1	43.5	2639	51.9	62.3	- 34.2	- 87.7	- 53.5	80	50
15.3	40.4	55.6	1.9		1.0		25.3	23.6	1239	17.3	60.4	3.0		10.4	7.4		15	66
45.0	51.0	42.6	4.0		13.5	8.4		32.3	1737	34.6	60.4	- 9.7	- 39.6	- 29.9	80	49
45.0	78.5	38.3	4.0		26.5	14.0	41.1	2235	51.9	60.4	- 37.2	- 89.5	- 52.4	80	50
6.1		44.7	61.4	3.1		1		26.5	24.9	1318	17.6	62.3	3.9		11.4	7.5		15	63
27		55.7	53.5	1.9		12		17.1	30.7	1692	29.4	58		- 9.5	- 21	- 11.5	37	56
16.6	53.1	57		2.7		5		22.4	29.9	1598	22.1	65.3	- 1.6	- 1.6	0		21	86
32.4	38.2	48.8	2.6		4.5		14.5	21.7	1269	24.2	52.2	- 1.6	- 8.1	- 6.5	33	68
18.4	36.3	54		2.3		1		22.6	19.5	1153	17.8	55.6	3		8.2		5.2		15	70
*/

public class CSBiasAPI {

    public static final int CSBIAS_OK = 0;
    public static final int CSBIAS_ERR_WEIGTH = -2;
    public static final int CSBIAS_ERR_HEIGHT = -3;
    public static final int CSBIAS_ERR_AGE = -4;
    public static final int CSBIAS_ERR_SEX = -5;
    public static final int CSBIAS_ERR_IMPEDANCE = -6;
    public static final int CSBIAS_ERR_MODE = -7;
    public static final int CSBIAS_ERR_VCODE = -8;

    static {
        System.loadLibrary("chipsea_bias_v235");
    }

    /*
    人体成分算法
    - mode 模式，默认为0
    - sex 性别， 1男， 0女
    - age 年龄，取值在18 ~ 99岁之间
    - height 身高, 取值在90 ~ 220之间，表示90 ~ 220厘米.
    - weight 体重，取值在200 ~ 1500之间，表示20公斤~150公斤.
    - impedance 阻抗值, 取值在2000 ~ 15000.
    - vkeyCode 指纹验证中的验证码
    */
    public static native CSBiasV235Resp cs_bias_v235(int mode, int sex, int age, int height, int weight, int impedance, int vkeyCode);

    /**
     * Created by liangyc
     * Time :2018/11/26
     * Des:
     */
    public static class CSBiasDataV235 {
        //体脂率
        public double BFP;
        //肌肉量
        public double SLM;
        //骨盐量
        public double BMC;
        //体水分率
        public double BWP;
        //蛋白质率
        public double PP;
        //骨骼肌量
        public double SMM;
        //内脏脂肪等级
        public double VFR;
        //身体质量指数
        public double BMI;
        //标准体重
        public double SBW;
        //肌肉控制
        public double MC;
        //体重控制
        public double WC;
        //脂肪控制
        public double FC;
        //基础代谢率
        public int BMR;
        //身体年龄
        public int MA;
        //身体得分
        public int SBC;

    }

    /**
     * Created by liangyc
     * Time :2018/11/26
     * Des:
     */
    public static class CSBiasV235Resp {
        public int result;

        public CSBiasDataV235 data;


    }
}
