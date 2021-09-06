package com.isport.brandapp.wu.bean;

import java.util.ArrayList;
import java.util.List;

public class PractiseRecordInfo {

    /**
     * key : 2019-11-20
     * data : [{"wristbandTrainingId":550,"deviceId":"W814-C7AFF1103604","userId":122,"exerciseType":"2","dateStr":"2019-11-20","createTime":"2019-11-20T08:08:09.612+0000","extend1":null,"extend2":null,"extend3":null,"aveRate":"","startTimestamp":1574233416000,"endTimestamp":1574237286000,"vaildTimeLength":"65","totalSteps":"402","totalDistance":"333","totalCalories":"36","heartRateDetailArray":""},{"wristbandTrainingId":538,"deviceId":"W814-C7AFF1103604","userId":122,"exerciseType":"2","dateStr":"2019-11-20","createTime":"2019-11-20T03:37:12.195+0000","extend1":null,"extend2":null,"extend3":null,"aveRate":"","startTimestamp":1574211797000,"endTimestamp":1574211875000,"vaildTimeLength":"2","totalSteps":"52","totalDistance":"43","totalCalories":"5","heartRateDetailArray":""}]
     */

    private String key;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private List<ExerciseInfo> data;
    private List<ExerciseInfo> currentShowList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<ExerciseInfo> getData() {
        return data;
    }

    public List<ExerciseInfo> getCurrentShowList() {
        if (currentShowList == null) {
            currentShowList = new ArrayList<>();
        }
        return currentShowList;
    }

    public void setCurrentShowList(List<ExerciseInfo> currentShowList) {
        this.currentShowList = currentShowList;
    }

    public void setData(List<ExerciseInfo> data) {
        this.data = data;
    }
}
