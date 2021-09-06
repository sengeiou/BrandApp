package com.isport.blelibrary.utils;

import com.sleepace.sdk.core.heartbreath.domain.HistoryData;

import java.util.Comparator;

public class HistoryDataComparator implements Comparator<HistoryData> {

	@Override
	public int compare(HistoryData lhs, HistoryData rhs) {
		// TODO Auto-generated method stub
		if(lhs.getSummary().getStartTime() < rhs.getSummary().getStartTime()){
			return 1;
		}else if(lhs.getSummary().getStartTime() > rhs.getSummary().getStartTime()){
			return -1;
		}
		return 0;
	}

}
