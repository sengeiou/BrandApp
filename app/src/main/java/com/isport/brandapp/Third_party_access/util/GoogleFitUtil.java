package com.isport.brandapp.Third_party_access.util;

import android.content.Context;

import com.google.android.gms.fitness.data.DataSet;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.isport.blelibrary.utils.StepArithmeticUtil;
import com.isport.brandapp.Home.bean.http.WatchSleepDayData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import phone.gym.jkcq.com.commonres.common.JkConfiguration;

import static com.google.android.gms.fitness.data.DataSource.TYPE_RAW;

public class GoogleFitUtil {

    static String TAG = "GoogleFitUtil";

    /**
     * Creates and returns a {@link DataSet} of step count data for insertion using the History API.
     * 按照小时来进行数据的分类
     */

    public static DataSet inserSleepData(Context context, Calendar startCalendar, long dayEndTime, int[] sleepList, int currentType) {

        long startTime, endTime;

        DataSource dataSourceSleep =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_ACTIVITY_SEGMENT)
                        .setStreamName(TAG + " - segment")
                        .setType(TYPE_RAW)
                        .build();
        DataSet dataSteSleep = DataSet.create(dataSourceSleep);
        if (currentType == JkConfiguration.DeviceType.BRAND_W311) {
            for (int i = 0; i < sleepList.length; i++) {
                startTime = startCalendar.getTimeInMillis();
                startCalendar.add(Calendar.MINUTE, 1);
                endTime = startCalendar.getTimeInMillis();

                if (endTime > dayEndTime) {
                    break;
                }

                if (sleepList[i] != 250 && sleepList[i] != 253 && sleepList[i] != 251 && sleepList[i] != 252) {
                    continue;
                }
                if (sleepList[i] == 250) {
                    DataPoint dataSLEEP_Deep =
                            dataSteSleep.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                    dataSLEEP_Deep.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.SLEEP_AWAKE);
                    dataSteSleep.add(dataSLEEP_Deep);
                    Log.i("insertFitnessData", "Creating a new data insert SLEEP_DEEP.");

                } else if (sleepList[i] == 253) {
                    DataPoint dataSLEEP_LIGHT =
                            dataSteSleep.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                    dataSLEEP_LIGHT.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.SLEEP_DEEP);
                    dataSteSleep.add(dataSLEEP_LIGHT);
                    Log.i("insertFitnessData", "Creating a new data insert SLEEP_LIGHT.");
                } else if (sleepList[i] == 252 || sleepList[i] == 251) {
                    DataPoint dataSLEEP_LIGHT =
                            dataSteSleep.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                    dataSLEEP_LIGHT.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.SLEEP_LIGHT);
                    dataSteSleep.add(dataSLEEP_LIGHT);
                    Log.i("insertFitnessData", "Creating a new data insert SLEEP_LIGHT.");
                }
            }

        } else if (currentType == JkConfiguration.DeviceType.WATCH_W516) {
            for (int i = 0; i < sleepList.length; i++) {
                startTime = startCalendar.getTimeInMillis();
                startCalendar.add(Calendar.MINUTE, 1);
                endTime = startCalendar.getTimeInMillis();

                if (sleepList[i] != 250 && sleepList[i] != 253 && sleepList[i] != 251 && sleepList[i] != 252) {
                    continue;
                }
                if (sleepList[i] == 253) {
                    DataPoint dataSLEEP_Deep =
                            dataSteSleep.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                    dataSLEEP_Deep.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.SLEEP_AWAKE);
                    dataSteSleep.add(dataSLEEP_Deep);
                    Log.i("insertFitnessData", "Creating a new data insert SLEEP_DEEP.");

                } else if (sleepList[i] == 250) {
                    DataPoint dataSLEEP_LIGHT =
                            dataSteSleep.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                    dataSLEEP_LIGHT.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.SLEEP_DEEP);
                    dataSteSleep.add(dataSLEEP_LIGHT);
                    Log.i("insertFitnessData", "Creating a new data insert SLEEP_LIGHT.");
                } else if (sleepList[i] == 252 || sleepList[i] == 251) {
                    DataPoint dataSLEEP_LIGHT =
                            dataSteSleep.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
                    dataSLEEP_LIGHT.getValue(Field.FIELD_ACTIVITY).setActivity(FitnessActivities.SLEEP_LIGHT);
                    dataSteSleep.add(dataSLEEP_LIGHT);
                    Log.i("insertFitnessData", "Creating a new data insert SLEEP_LIGHT.");
                }
            }
        }
        return dataSteSleep;

    }

    /**
     * @param context
     * @param startTime
     * @param endTime
     * @param hrList
     * @param startCalendar
     * @param min           间隔多少分钟
     * @return
     */
    public static DataSet inserHrListData(Context context, long startTime,
                                          long endTime, ArrayList<Integer> hrList, Calendar startCalendar, int min) {
        DataSource dataSourceSleep =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_HEART_RATE_BPM)
                        .setStreamName(TAG + " - step count")
                        .setType(TYPE_RAW)
                        .build();

        long hrStartTime, hrEndTime;
        DataSet dataSetHR = DataSet.create(dataSourceSleep);
        for (int i = 0; i < hrList.size(); i++) {
            hrStartTime = startCalendar.getTimeInMillis();
            startCalendar.add(Calendar.MINUTE, min);
            hrEndTime = startCalendar.getTimeInMillis();
            if (hrEndTime > endTime) {
                break;
            }
            if (hrList.get(i) <= 30) {
                continue;
            }
            DataPoint dataHrPoint =
                    dataSetHR.createDataPoint().setTimeInterval(hrStartTime, hrStartTime, TimeUnit.MILLISECONDS);
            dataHrPoint.getValue(Field.FIELD_BPM).setFloat(new Float(hrList.get(i)));
            dataSetHR.add(dataHrPoint);
            Log.i("insertFitnessData", "Creating a new data insert dataSetHR.");
        }
        return dataSetHR;
    }


    public static ArrayList<DataSet> insertFitnessData(Context context, long startTime,
                                                       long endTime, ArrayList<Integer> steps, Calendar startCalendar, float
                                                               weight, int heigh, String sex) {
        Log.i("insertFitnessData", "Creating a new data insert request.");


        long starEndTime;

        ArrayList<DataSet> dataSets = new ArrayList<>();

        DataSource dataSource =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .setStreamName(TAG + " - step count")
                        .setType(TYPE_RAW)
                        .build();
        DataSource dataSourceDis =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_DISTANCE_DELTA)
                        .setStreamName(TAG + " - dis count")
                        .setType(TYPE_RAW)
                        .build();
        DataSource dataSourceCal =
                new DataSource.Builder()
                        .setAppPackageName(context)
                        .setDataType(DataType.TYPE_CALORIES_EXPENDED)
                        .setStreamName(TAG + " - cal count")
                        .setType(TYPE_RAW)
                        .build();


        DataSet dataSetStep = DataSet.create(dataSource);
        DataSet dataSetDis = DataSet.create(dataSourceDis);
        DataSet dataSetCal = DataSet.create(dataSourceCal);
        for (int i = 0; i < steps.size(); i++) {
            startTime = startCalendar.getTimeInMillis();
            startCalendar.add(Calendar.HOUR_OF_DAY, 1);
            //大于这个数据就把这个数据给去掉

            starEndTime = startCalendar.getTimeInMillis();
            if (steps.get(i) == 0) {
                continue;
            }
            if (starEndTime > endTime) {
                break;
            }
            DataPoint dataPointStep =
                    dataSetStep.createDataPoint().setTimeInterval(startTime, starEndTime, TimeUnit.MILLISECONDS);
            dataPointStep.getValue(Field.FIELD_STEPS).setInt(steps.get(i));

            Log.i("insertFitnessData", "Creating a new data dataPointStep");


            DataPoint dataPointStepCal =
                    dataSetCal.createDataPoint().setTimeInterval(startTime, starEndTime, TimeUnit.MILLISECONDS);
            dataPointStepCal.getValue(Field.FIELD_CALORIES).setFloat(StepArithmeticUtil.stepsConversionCaloriesFloat(weight, steps.get(i)));

            Log.i("insertFitnessData", "Creating a new data dataPointStepCal");
            DataPoint dataPointStepDis =
                    dataSetDis.createDataPoint().setTimeInterval(startTime, starEndTime, TimeUnit.MILLISECONDS);
            dataPointStepDis.getValue(Field.FIELD_DISTANCE).setFloat(StepArithmeticUtil.stepsConversionDistanceFloat(heigh, sex, steps.get(i)));
            Log.i("insertFitnessData", "Creating a new data dataPointStepDis");
            dataSetStep.add(dataPointStep);
            dataSetCal.add(dataPointStepCal);
            dataSetDis.add(dataPointStepDis);
        }
        dataSets.add(dataSetStep);
        dataSets.add(dataSetCal);
        dataSets.add(dataSetDis);
        // dataSets.add(dataSetHeight);


        return dataSets;
    }


    //插入身高
    //插入体重

    public static void inserHeightData() {

    }


    //插入心率数据
    public static void inserHrData(Context context, ArrayList<Integer> hrLists, int min) {
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();

        // calendar.add(Calendar.MINUTE, hrLists.size() * min);
        //calendar.add(Calendar.MINUTE, -(hrLists.size() * min));
        ArrayList<Integer> hrList = new ArrayList<>();
        hrList.addAll(hrLists);
        DataSet dataSethr = inserHrListData(context, startTime, endTime, hrList, calendar, min);
        DataUpdateRequest requestHr = new DataUpdateRequest.Builder()
                .setDataSet(dataSethr)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestHr)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was requestHr successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestHr the dataset.", task.getException());
                                }
                            }
                        });
    }

    //24小时的数据
    public static void inserSportData(Context context, ArrayList<Integer> stepList,
                                      float weight, float height, String sex) {
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();
        ArrayList<Integer> steps = new ArrayList<>();
        steps.addAll(stepList);

        ArrayList<DataSet> dataSet = insertFitnessData(context, startTime, endTime, steps, calendar, weight, (int) (height), sex);

        DataUpdateRequest requestStep = new DataUpdateRequest.Builder()
                .setDataSet(dataSet.get(0))
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        DataUpdateRequest requestCal = new DataUpdateRequest.Builder()
                .setDataSet(dataSet.get(1))
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        DataUpdateRequest requestDis = new DataUpdateRequest.Builder()
                .setDataSet(dataSet.get(2))
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestStep)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert requestStep was successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestStep the dataset.", task.getException());
                                }
                            }
                        });

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestCal)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was requestCal successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestCal the dataset.", task.getException());
                                }
                            }
                        });


        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestDis)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert requestDis was successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting the dataset.", task.getException());
                                }
                                // Then, invoke the History API to insert the data.
                                Log.i(TAG, "Inserting the dataset in the History API.");
                            }
                        });

    }


    //插入身高
    public static void inserHeightAndWeight() {

    }


    public static void inserSleepData(Context context, WatchSleepDayData watchSleepDayData, int currentType) {


        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();
        // Create the sleep session
        Session session = new Session.Builder()
                .setName("bonlalansleep")
                .setIdentifier("sleep" + startTime)
                .setDescription("bonlalasleep")
                .setStartTime(startTime, TimeUnit.MILLISECONDS)
                .setEndTime(endTime, TimeUnit.MILLISECONDS)
                .setActivity(FitnessActivities.SLEEP)
                .build();


        //前一天的20点 到当前点

        DataSet dataSet = inserSleepData(context, calendar, endTime, watchSleepDayData.getSleepArry(), currentType);
       /* SessionInsertRequest request = new SessionInsertRequest.Builder()
                .setSession(session)
                .addDataSet(dataSet)
                .build();*/

        DataUpdateRequest requestStep = new DataUpdateRequest.Builder()
                .setDataSet(dataSet)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

// Insert the session into Fit platform
        Log.i(TAG, "Inserting the session in the Sessions API");
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestStep)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert sleep was successful!");
                                } else {
                                    Log.e(TAG, "There was a sleep inserting the dataset.", task.getException());
                                }
                                // Then, invoke the History API to insert the data.
                                Log.i(TAG, "Inserting the sleep in the History API.");
                            }
                        });

    }


    /**
     * Creates a {@link DataSet} and inserts it into user's Google Fit history.
     */
    public static void insertData(Context context) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long startTime = calendar.getTimeInMillis();

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();

        ArrayList<Integer> steps = new ArrayList<>();
        ArrayList<Integer> hrList = new ArrayList<>();
        ArrayList<Integer> sleepList = new ArrayList<>();

        for (int i = 0; i < cal.get(Calendar.HOUR_OF_DAY); i++) {
            steps.add(20);
            hrList.add(80);
        }

        for (int i = 0; i < cal.get(Calendar.HOUR_OF_DAY); i++) {
            if (i % 2 == 0) {
                sleepList.add(250);
            } else {
                sleepList.add(253);
            }
        }

        // ArrayList<DataSet> dataSet = insertFitnessData(context, startTime, endTime, steps, calendar);

        DataSet dataSethr = inserHrListData(context, startTime, endTime, hrList, calendar, 1);

        //DataSet dataSetSleep = inserSleepData(context, calendar, endTime, sleepList, 516);


      /*  DataDeleteRequest derequestDis = new DataDeleteRequest.Builder()
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .addDataType(DataType.TYPE_DISTANCE_DELTA)
                .build();*/

        DataUpdateRequest requestHr = new DataUpdateRequest.Builder()
                .setDataSet(dataSethr)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

     /*   Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestStep)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert requestStep was successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestStep the dataset.", task.getException());
                                }
                            }
                        });

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestCal)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was requestCal successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestCal the dataset.", task.getException());
                                }
                            }
                        });


        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestDis)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert requestDis was successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting the dataset.", task.getException());
                                }
                                // Then, invoke the History API to insert the data.
                                Log.i(TAG, "Inserting the dataset in the History API.");
                            }
                        });

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestHeight)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was requestHeight successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestCal the dataset.", task.getException());
                                }
                            }
                        });*/
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .updateData(requestHr)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // At this point, the data has been inserted and can be read.
                                    Log.i(TAG, "Data insert was requestHr successful!");
                                } else {
                                    Log.e(TAG, "There was a problem inserting requestHr the dataset.", task.getException());
                                }
                            }
                        });
    }

}