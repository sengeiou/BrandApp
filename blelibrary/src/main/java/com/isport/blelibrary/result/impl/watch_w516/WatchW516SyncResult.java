package com.isport.blelibrary.result.impl.watch_w516;

import com.isport.blelibrary.result.IResult;

/**
 * @创建者 bear
 * @创建时间 2019/3/4 16:39
 * @描述
 */
public class WatchW516SyncResult implements IResult {
    @Override
    public String getType() {
        return WATCH_W516_SYNC;
    }

    public static int FAILED  = 0;
    public static int SYNCING = 1;
    public static int SUCCESS = 2;
    public static int TIMEOUT=3;

    private int success;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "WatchW516SyncResult{" +
                "success=" + success +
                '}';
    }

    public WatchW516SyncResult(int success) {
        this.success = success;
    }
}
