package com.isport.brandapp.sport.bean;

import java.util.List;

public class MoreTypeBean {
    public int type;
    public int pic;
    public SportSumData sportSumData;
    public String moth;
    public List<Float> countList;
    public List<String> dateList;
    public List<IphoneSportWeekVo> weekVos;
    public ResultHistorySportSummarizingData resultHistorySportSummarizingData;

    public MoreTypeBean(){

    }
    public MoreTypeBean(int type){
        this.type=type;
        sportSumData=new SportSumData();
    }
}
