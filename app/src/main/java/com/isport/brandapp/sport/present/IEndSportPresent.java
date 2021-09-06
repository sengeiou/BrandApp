package com.isport.brandapp.sport.present;

import com.isport.brandapp.sport.bean.SportSumData;

public interface IEndSportPresent {
    public void addSportSummerData(SportSumData sumData);

    public void getDbSummerData(long id);

    //POST /wristband/iphoneSport/selectByPrimaryKey
    public void getSportSummarDataByPrimaryKey(String primaryKey);

}
