package com.isport.brandapp.parm.http;

import com.isport.brandapp.parm.db.BaseDbParms;

/**
 * @创建者 bear
 * @创建时间 2019/3/12 14:33
 * @描述
 */
public class WatchHistoryParms extends BaseDbParms {

    public long time;//time为月份第一天零点时间戳
    public int userId;
    public int dataType;//(0步数，1心率，2睡眠)
    public int dayNum;//天数
    public int pageNum;//页数
    public int pageSize;//每页天数
    public String deviceId;
    public String date;

}
