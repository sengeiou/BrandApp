package com.isport.blelibrary.result.impl.watch;

import com.isport.blelibrary.result.IResult;

import java.io.Serializable;
import java.util.List;

/**
 * @Author
 * @Date 2018/11/6
 * @Fuction
 */

public class WatchSportDataListResult implements IResult,Serializable {

    private List<WatchSportData> mSportDataList;

    @Override
    public String toString() {
        return "WatchSportDataListResult{" +
                "mSportDataList=" + mSportDataList +
                '}';
    }

    public List<WatchSportData> getSportDataList() {
        return mSportDataList;
    }

    public void setSportDataList(List<WatchSportData> sportDataList) {
        mSportDataList = sportDataList;
    }

    public WatchSportDataListResult(List<WatchSportData> sportDataList) {

        mSportDataList = sportDataList;
    }

    @Override
    public String getType() {
        return WATCH_SPORTDATALIST;
    }
}
