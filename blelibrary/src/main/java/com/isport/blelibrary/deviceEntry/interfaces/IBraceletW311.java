package com.isport.blelibrary.deviceEntry.interfaces;

import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.entry.NotificationMsg;
import com.isport.blelibrary.entry.SedentaryRemind;

import java.util.ArrayList;
import java.util.List;


public interface IBraceletW311 {

    public void getRealHrSwitch();

    public void set_userinfo();

    public void set_wear(boolean wristMode);


    public void set_alar();

    public void set_disPlay(DisplaySet displaySet);

    public void get_display();

    public void set_not_disturb(boolean open, int startHour, int startMin, int endHour, int endMin);

    public void get_not_disturb();

    public void get_sedentary_reminder();

    public void set_sendentary_reminder(List<SedentaryRemind> list);

    public void set_hr();

    public void find_bracelet();

    public void lost_to_remind(boolean isOpen);

    public void set_lift_wrist_to_view_info();

    public void play_bracelet();

    public void set_is_open_raise_hand(boolean isOpen);
    public void set_raise_hand(int type, int startHour, int startMin, int endHour, int endMin);

    public void set_defult();

    public void set_hr_setting(boolean isOpen);

    public void set_hr_setting(boolean isOpen,int time);

    public void sync_data();


    public void w311_send_phone(String comming_phone,String name);

    public void w311_send_msge(NotificationMsg msg);

    public void setAlarmList(ArrayList<AlarmEntry> list);
    public void getAlarmList();



}
