package com.isport.brandapp.sport.view;

import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.sport.bean.IphoneSportListVo;
import com.isport.brandapp.sport.bean.IphoneSportWeekVo;
import com.isport.brandapp.sport.bean.MoreTypeBean;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface SportHistoryWeekOrMonthView extends BaseView {

    void successSummarData(Integer times,double dis,ArrayList<String> datalist,ArrayList<Float> disList);

    void successDetailData(ArrayList<MoreTypeBean> scaleHistoryBeanLists);



}
