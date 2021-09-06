package com.isport.brandapp.ropeskipping;

import com.isport.blelibrary.db.table.s002.DailyBrief;
import com.isport.blelibrary.db.table.s002.Summary;
import com.isport.brandapp.ropeskipping.history.bean.HistoryDateBean;
import com.isport.brandapp.ropeskipping.response.ResponseDailySummaries;

import java.util.List;

import brandapp.isport.com.basicres.mvp.BaseView;

/**
 * Created by BeyondWorlds
 * on 2020/6/30
 */
public interface RopeSkippingView extends BaseView {
    void successgetSummaryData(Summary summary);

    void sucessSummaries(List<ResponseDailySummaries> summary);

    void sucessDailyBrief(List<DailyBrief> summary);

    void sucessDaysInMonth(List<String> summary);

    void sucessWeekStr(List<HistoryDateBean> summary);

    void sucessMonthStr(List<HistoryDateBean> summary);


}
