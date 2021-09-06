package com.isport.blelibrary.result.impl.sleep;

import com.isport.blelibrary.result.entry.Analysis;
import com.isport.blelibrary.result.entry.Detail;
import com.isport.blelibrary.result.entry.Summary;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class SleepHistoryDataResult{

    public Analysis analy;
    public Detail detail;
    public Summary summary;

    @Override
    public String toString() {
        return "SleepHistoryDataResult{" +
                "analy=" + analy +
                ", detail=" + detail +
                ", summary=" + summary +
                '}';
    }

    public Analysis getAnaly() {
        return analy;
    }

    public void setAnaly(Analysis analy) {
        this.analy = analy;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public SleepHistoryDataResult(Analysis analy, Detail detail, Summary summary) {
        this.analy = analy;
        this.detail = detail;
        this.summary = summary;
    }

}
