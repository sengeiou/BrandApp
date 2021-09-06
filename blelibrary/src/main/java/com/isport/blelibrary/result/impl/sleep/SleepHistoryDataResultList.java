package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.IResult;

import java.util.ArrayList;

/**
 * @Author
 * @Date 2018/10/31
 * @Fuction
 */

public class SleepHistoryDataResultList implements IResult {

    @Override
    public String getType() {
        return SLEEP_HISTORYDATA;
    }

    private ArrayList<SleepHistoryDataResult> sleepHistoryDataResults;

    @Override
    public String toString() {
        return "SleepHistoryDataResultList{" +
                "sleepHistoryDataResults=" + sleepHistoryDataResults +
                '}';
    }

    public ArrayList<SleepHistoryDataResult> getSleepHistoryDataResults() {
        return sleepHistoryDataResults;
    }

    public void setSleepHistoryDataResults(ArrayList<SleepHistoryDataResult> sleepHistoryDataResults) {
        this.sleepHistoryDataResults = sleepHistoryDataResults;
    }

    public SleepHistoryDataResultList(ArrayList<SleepHistoryDataResult> sleepHistoryDataResults) {

        this.sleepHistoryDataResults = sleepHistoryDataResults;
    }
}
