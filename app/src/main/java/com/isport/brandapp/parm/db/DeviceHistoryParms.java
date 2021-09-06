package com.isport.brandapp.parm.db;

/**
 * @Author
 * @Date 2019/1/14
 * @Fuction
 */

public class DeviceHistoryParms extends BaseDbParms {
    public String deviceId;//设备id
    public int pageSize;//距离当月多远,没有用了，只是判断是否是最新,体脂称查询
    public String currentMonth;//体脂称的查询
    public long timeTamp;//最后一条数据时间戳，用以查询是否有跟多数据
}
