package com.isport.brandapp.util;

/*
 *
 *
 * @author mhj
 * Create at 2019/3/26 14:21
 */
public interface onDownloadListener {
    void onStart(float length);
    void onProgress(float progress);
    void onComplete();
    void onFail();
}
