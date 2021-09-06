package phone.gym.jkcq.com.socialmodule.video.cut;

import android.media.MediaMetadataRetriever;

public class CutUtil {
    public static String calBitRa(String mSourceVideoPath){

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mSourceVideoPath);
        String value = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        //String videoTime = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取视频时长
        //int framRate=Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE));

        int videoWidth = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));//获取视频的宽度
        int videoHeight = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));//获取视频的高度
        //Logger.e("VideoTrimmerView", "start=" + start + " duration=" + duration + " mDuration=" + mDuration + ",value=" + value);
        Long rate = Long.parseLong(value);

        long video = rate / 1000;
        if (video >= 20000) {
            video = video / 4;
        } else if (video >= 10000) {
            video = video / 6;
        } else if (video >= 5000) {
            video = video / 4;
        } else if (video >= 4000) {
            video = video / 2;
        }else if(video>=3000){
            video= (long) (video*(2.0f/3));
        }
        String videoRate = video + "k";
        return videoRate;
    }
}
