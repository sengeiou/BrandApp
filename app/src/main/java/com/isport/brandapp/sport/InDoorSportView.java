package com.isport.brandapp.sport;

import com.isport.brandapp.sport.bean.SportSumData;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface InDoorSportView extends BaseView{

    public void successSaveData(String publishId);
    public void failSaveData();
}
