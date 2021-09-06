package com.isport.blelibrary.deviceEntry.interfaces;

/**
 * @Author
 * @Date 2019/2/19
 * @Fuction
 */

public interface IWatch516 {

    void read_status();

    void set_general(boolean open24HeartRate, boolean isHeart);

    void set_general(boolean open24HeartRate, boolean isCall, boolean isMessage);

    void get_general();

    void set_user(int year, int month, int day, int sex, int weight, int height, int maxHeartRate, int
            minHeartRate);

    void get_user();

    void switch_mode(boolean inMode);

    void adjust(int hour, int min);

    void set_calender();

    void get_calender();

    void set_alarm(boolean enable,int day, int hour, int min, int index);

    void get_alarm(int index);

    void set_sleep_setting(boolean isAutoSleep, boolean hasNotif, boolean disturb, int
            testStartTimeH, int testStartTimeM, int
                                   testEndTimeH, int testEndTimeM,
                           int
                                   disturbStartTimeH, int disturbStartTimeM, int
                                   disturbEndTimeH, int disturbEndTimeM);

    void get_sleep_setting();

    void set_sedentary_time(int time, int startHour, int startMin, int endHour, int endMin);

    void get_sedentary_time();

    void getTestData();

    void send_notification(int type);

    void send_notificationN(String type);

    void set_handle(boolean enable);

    void get_daily_record(int day);

    void clear_daily_record();

    void get_exercise_data();

    void clear_exercise_data();

    void set_default();

    void set_sn_factory();

    void set_sn_normalmode(int SN);

    void get_sn_data();

    void set_beltname();

    void test_reset();

    void test_motorled();

    void stop_test_motorled();

    void test_handle();

    void test_display();

    void test_ohr();

    void device_to_phone();
}
