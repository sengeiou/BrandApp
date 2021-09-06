package com.isport.brandapp.parm.http;

import com.isport.brandapp.parm.db.BaseDbParms;

/**
 * @Author
 * @Date 2019/1/18
 * @Fuction
 */

public class ScaleParms extends BaseDbParms {

    public int userId;
    public int pageNum;
    public int pageSize;
    public boolean show;

    @Override
    public String toString() {
        return "ScaleParms{" +
                "userId=" + userId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
