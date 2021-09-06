package com.isport.brandapp.sport.present;

import com.isport.brandapp.sport.bean.SportSumData;

public interface ISportHistoryPresent {
    public void getSportSummerData();

    public void getFisrtSportHistory(String userid, String offset);

    public void getLoadMoreHistory(String userid, String offset);




}
