package brandapp.isport.com.basicres.service.daemon.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

// 走步检测器，用于检测走步并计数

/**
 * 具体算法不太清楚，本算法是从谷歌计步器：Pedometer上截取的部分计步算法
 * 
 */
public class StepDetectorMain implements SensorEventListener {

    // private static final String TAG = StepDetectorMain.class.getSimpleName();
    //
    // public static int mCurrentStepNumber = 0;
    //
    // public static float SENSITIVITY = 0; // SENSITIVITY灵敏度
    //
    // private float mLastValues[] = new float[3 * 2];
    // private float mScale[] = new float[2];
    // private float mYOffset;
    // private static long end = 0;
    // private static long start = 0;
    //
    // //保存今天的时间Str，每次获取到步数的时候，则判断有没有过一天，过了就把步数清零，时间重新赋值。
    // public static String nowPhoneTime = "";
    //
    // /**
    // * 最后加速度方向
    // */
    // private float mLastDirections[] = new float[3 * 2];
    // private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
    // private float mLastDiff[] = new float[3 * 2];
    // private int mLastMatch = -1;
    //
    // private SharedPreferences stepSharedPreferences;
    //
    // /**
    // * 传入上下文的构造函数
    // *
    // * @param context
    // */
    // public StepDetectorMain(Context context) {
    // // TODO Auto-generated constructor stub
    // super();
    // int h = 480;
    // mYOffset = h * 0.5f;
    // mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
    // mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    // stepSharedPreferences = context.getSharedPreferences(
    // ActivityStepSetting.SETP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    // SENSITIVITY = stepSharedPreferences.getInt(ActivityStepSetting.SENSITIVITY_VALUE, 13);//
    // 这里设置了手机灵敏度,来调整步数
    // }
    //
    // // public void setSensitivity(float sensitivity) {
    // // SENSITIVITY = sensitivity; // 1.97 2.96 4.44 6.66 10.00 15.00 22.50
    // // // 33.75
    // // // 50.62
    // // }
    //
    // // public void onSensorChanged(int sensor, float[] values) {
    // @Override
    // public void onSensorChanged(SensorEvent event) {
    //
    //
    //
    // // Log.i(Constant.STEP_SERVER, "StepDetector");
    // Sensor sensor = event.sensor;
    // // Log.i(Constant.STEP_DETECTOR, "onSensorChanged");
    // synchronized (this) {
    // if (sensor.getType() == Sensor.TYPE_ORIENTATION) {
    // } else {
    // int j = (sensor.getType() == Sensor.TYPE_ACCELEROMETER) ? 1 : 0;
    // if (j == 1) {
    // float vSum = 0;
    // for (int i = 0; i < 3; i++) {
    // final float v = mYOffset + event.values[i] * mScale[j];
    // vSum += v;
    // }
    // int k = 0;
    // float v = vSum / 3;
    //
    // float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
    // if (direction == -mLastDirections[k]) {
    // // Direction changed
    // int extType = (direction > 0 ? 0 : 1); // minumum or
    // // maximum?
    // mLastExtremes[extType][k] = mLastValues[k];
    // float diff =
    // Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);
    //
    // if (diff > SENSITIVITY) {
    // boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
    // boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
    // boolean isNotContra = (mLastMatch != 1 - extType);
    //
    // if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra) {
    // end = System.currentTimeMillis();
    //
    // logevent(event);
    //
    // if (end - start > 500) {// 此时判断为走了一步
    //
    // // Log.i("StepDetector", "CURRENT_SETP:"+ CURRENT_SETP);
    // mCurrentStepNumber++;
    // mLastMatch = extType;
    // start = end;
    // }
    // } else {
    // mLastMatch = -1;
    // }
    // }
    // mLastDiff[k] = diff;
    // }
    // mLastDirections[k] = direction;
    // mLastValues[k] = v;
    // }
    // }
    // }
    // }
    //
    // private void logevent(SensorEvent event) {
    // //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
    // float[] values = event.values;
    //
    // float x = values[0];
    // float y = values[1];
    // float z = values[2];
    //
    //// Logger.i(TAG, "x:" + x + "y:" + y + "z:" + z);
    //// Logger.i(TAG, "Math.abs(x):" + Math.abs(x) + "Math.abs(y):" +Math.abs(y) + "Math.abs(z):" +
    // Math.abs(z));
    // }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private int count = 0;
    public static int mCurrentStepNumber = 0;
    public static String nowPhoneTime = "";
    // 计步传感器类型 0-counter 1-detector 2-accelerometer
    public static int stepSensor = -1;

    // 存放三轴数据
    float[] oriValues = new float[3];
    final int ValueNum = 4;
    // 用于存放计算阈值的波峰波谷差值
    float[] tempValue = new float[ValueNum];
    int tempCount = 0;
    // 是否上升的标志位
    boolean isDirectionUp = false;
    // 持续上升次数
    int continueUpCount = 0;
    // 上一点的持续上升的次数，为了记录波峰的上升次数
    int continueUpFormerCount = 0;
    // 上一点的状态，上升还是下降
    boolean lastStatus = false;
    // 波峰值
    float peakOfWave = 0;
    // 波谷值
    float valleyOfWave = 0;
    // 此次波峰的时间
    long timeOfThisPeak = 0;
    // 上次波峰的时间
    long timeOfLastPeak = 0;
    // 当前的时间
    long timeOfNow = 0;
    // 当前传感器的值
    float gravityNew = 0;
    // 上次传感器的值
    float gravityOld = 0;
    // 动态阈值需要动态的数据，这个值用于这些动态数据的阈值
    final float InitialValue = (float) 1.3;
    // 初始阈值
    float ThreadValue = (float) 2.0;
    // 波峰波谷时间差
    int TimeInterval = 250;

    private boolean hasRecord = false;
    private int hasStepCount = 0;
    private int prviousStepCount = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (stepSensor == 0) {
            int tempStep = (int) event.values[0];
            if (!hasRecord) {
                hasRecord = true;
                hasStepCount = tempStep;
            } else {
                if (tempStep < hasStepCount) {
                    hasStepCount = tempStep; 
                }
                int thisStepCount = tempStep - hasStepCount;
                mCurrentStepNumber += (thisStepCount - prviousStepCount);
                prviousStepCount = thisStepCount;
            }

        } else if (stepSensor == 1) {
            if (event.values[0] == 1.0) {
                mCurrentStepNumber++;
            }
        } else {
            for (int i = 0; i < 3; i++) {
                oriValues[i] = event.values[i];
            }
            gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0] + oriValues[1] * oriValues[1]
                    + oriValues[2] * oriValues[2]);
            detectorNewStep(gravityNew);
        }
    }

    /*
     * 检测步子，并开始计步 1.传入sersor中的数据 2.如果检测到了波峰，并且符合时间差以及阈值的条件，则判定为1步
     * 3.符合时间差条件，波峰波谷差值大于initialValue，则将该差值纳入阈值的计算中
     */
    public void detectorNewStep(float values) {
        if (gravityOld == 0) {
            gravityOld = values;
        } else {
            if (detectorPeak(values, gravityOld)) {
                timeOfLastPeak = timeOfThisPeak;
                timeOfNow = System.currentTimeMillis();
                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= ThreadValue)) {
                    timeOfThisPeak = timeOfNow;
                    /*
                     * 更新界面的处理，不涉及到算法 一般在通知更新界面之前，增加下面处理，为了处理无效运动： 1.连续记录10才开始计步
                     * 2.例如记录的9步用户停住超过3秒，则前面的记录失效，下次从头开始 3.连续记录了9步用户还在运动，之前的数据才有效
                     */
                    if (this.timeOfThisPeak - this.timeOfLastPeak <= 3000L) {
                        if (this.count < 9) {
                            this.count++;
                        } else if (this.count == 9) {
                            this.count++;
                            this.mCurrentStepNumber += this.count;
                        } else {
                            this.mCurrentStepNumber++;
                        }
                    } else {// 超时
                        this.count = 1;// 为1,不是0
                    }
                }
                
                if (timeOfNow - timeOfLastPeak >= TimeInterval
                        && (peakOfWave - valleyOfWave >= InitialValue)) {
                    timeOfThisPeak = timeOfNow;
                    ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
                }
            }
        }
        gravityOld = values;
    }

    /*
     * 检测波峰 以下四个条件判断为波峰： 1.目前点为下降的趋势：isDirectionUp为false 2.之前的点为上升的趋势：lastStatus为true
     * 3.到波峰为止，持续上升大于等于2次 4.波峰值大于20 记录波谷值 1.观察波形图，可以发现在出现步子的地方，波谷的下一个就是波峰，有比较明显的特征以及差值
     * 2.所以要记录每次的波谷值，为了和下次的波峰做对比
     */
    public boolean detectorPeak(float newValue, float oldValue) {
        lastStatus = isDirectionUp;
        if (newValue >= oldValue) {
            isDirectionUp = true;
            continueUpCount++;
        } else {
            continueUpFormerCount = continueUpCount;
            continueUpCount = 0;
            isDirectionUp = false;
        }

        if (!isDirectionUp && lastStatus && (continueUpFormerCount >= 2 || oldValue >= 20)) {
            peakOfWave = oldValue;
            return true;
        } else if (!lastStatus && isDirectionUp) {
            valleyOfWave = oldValue;
            return false;
        } else {
            return false;
        }
    }

    /*
     * 阈值的计算 1.通过波峰波谷的差值计算阈值 2.记录4个值，存入tempValue[]数组中 3.在将数组传入函数averageValue中计算阈值
     */
    public float peakValleyThread(float value) {
        float tempThread = ThreadValue;
        if (tempCount < ValueNum) {
            tempValue[tempCount] = value;
            tempCount++;
        } else {
            tempThread = averageValue(tempValue, ValueNum);
            for (int i = 1; i < ValueNum; i++) {
                tempValue[i - 1] = tempValue[i];
            }
            tempValue[ValueNum - 1] = value;
        }
        return tempThread;
    }

    /*
     * 梯度化阈值 1.计算数组的均值 2.通过均值将阈值梯度化在一个范围里
     */
    public float averageValue(float value[], int n) {
        float ave = 0;
        for (int i = 0; i < n; i++) {
            ave += value[i];
        }
        ave = ave / ValueNum;
        if (ave >= 8)
            ave = (float) 4.3;
        else if (ave >= 7 && ave < 8)
            ave = (float) 3.3;
        else if (ave >= 4 && ave < 7)
            ave = (float) 2.3;
        else if (ave >= 3 && ave < 4)
            ave = (float) 2.0;
        else {
            ave = (float) 1.3;
        }
        return ave;
    }

}
