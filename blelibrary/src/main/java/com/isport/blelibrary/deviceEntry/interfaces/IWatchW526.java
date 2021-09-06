package com.isport.blelibrary.deviceEntry.interfaces;

public interface IWatchW526 {


    public void findWatch();

    public void setWatchbacklight(int leve, int time);


    public void senddisturb(boolean enable, int starHour, int starMin, int endHour, int endMin);


    public void set_w526_raise_hand(int type, int startHour, int startMin, int endHour, int endMin);

    public void sendW526Messge(String comming_phone, String name,int type);


    public void sendRealHrSwitch(boolean isOpen);


    public void setTargetStep(int step);

    public void meassureOxy(boolean isStart);

    public void meassureBlood(boolean isStart);

    public void sendphoto();

    public void meassureOneHr(boolean isStart);
}
