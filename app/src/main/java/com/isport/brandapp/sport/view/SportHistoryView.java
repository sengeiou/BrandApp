package com.isport.brandapp.sport.view;

import com.isport.brandapp.device.scale.bean.HistoryBeanList;
import com.isport.brandapp.sport.bean.ResultHistorySportSummarizingData;

import java.util.ArrayList;

import brandapp.isport.com.basicres.mvp.BaseView;

public interface SportHistoryView extends BaseView {

    void successLoadSummarData(ResultHistorySportSummarizingData resultHistorySportSummarizingData);

    void successFisrtHistory(ArrayList<HistoryBeanList> scaleHistoryBeanLists);

    void successLoadMoreHistory(ArrayList<HistoryBeanList> scaleHistoryBeanLists);


}
