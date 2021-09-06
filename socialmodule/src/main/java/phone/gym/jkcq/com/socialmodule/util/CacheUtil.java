package phone.gym.jkcq.com.socialmodule.util;

import android.util.Log;

import com.blankj.utilcode.util.FileUtils;

import phone.gym.jkcq.com.socialmodule.video.cut.StorageUtil;

public class CacheUtil {
    public static final String TAG=CacheUtil.class.getSimpleName();
    public static final String VIDEO_CACHE="video_cache";

    /**
     * 获取视频临时缓存
     * @return
     */
    public static String getVideoCache(){
        String path=StorageUtil.getCacheDir()+"/"+VIDEO_CACHE;
        FileUtils.createOrExistsDir(path);
        Log.e(TAG,"path="+path);
       return path ;
    }

    /**
     *删除视频缓存
     */
    public static void deleteVideoCache(){
        String path=StorageUtil.getCacheDir()+"/"+VIDEO_CACHE;
        FileUtils.createOrExistsDir(path);
        FileUtils.delete(path);

        String pat1=StorageUtil.getCacheDir()+"/"+"video-cache";
        FileUtils.createOrExistsDir(pat1);
        FileUtils.delete(pat1);
    }
}
