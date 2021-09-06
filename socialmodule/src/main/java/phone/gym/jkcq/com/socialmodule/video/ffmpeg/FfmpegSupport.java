package phone.gym.jkcq.com.socialmodule.video.ffmpeg;

import brandapp.isport.com.basicres.commonutil.Logger;
import io.microshow.rxffmpeg.RxFFmpegCommandList;

public class FfmpegSupport {

    /**
     * 压缩视频
     *
     * @param inputPath
     * @param outputPath
     * @return
     */
    public static String[] getCompressCommand(String inputPath, String outputPath) {
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(inputPath);
//      cmdlist.append("-vf");
//       cmdlist.append("boxblur=5:1");
//        cmdlist.append("boxblur=10:1");
        cmdlist.append("-b");
        cmdlist.append("8000k");
//        cmdlist.append("-r");
//        cmdlist.append("90");
//        cmdlist.append("30");
//        cmdlist.append("-vcodec");
//        cmdlist.append("libx264");
        cmdlist.append("-preset");
        cmdlist.append("superfast");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }

    public static String[] getCompressCommand(String inputPath, String outputPath, String videorate) {
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(inputPath);
//        cmdlist.append("-vf");
//       cmdlist.append("boxblur=5:1");
//        cmdlist.append("boxblur=10:1");
        cmdlist.append("-b");
        cmdlist.append(videorate);

        cmdlist.append("-r");
        cmdlist.append("20");

        cmdlist.append("-vcodec");
        cmdlist.append("h264");

        cmdlist.append("-preset");
        cmdlist.append("superfast");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }

    /**
     * 剪切视频
     * ffmpeg -ss START -t DURATION -i INPUT -codec copy -avoid_negative_ts 1 OUTPUT
     *
     * @param inputPath
     * @param outputPath
     * @return
     */

    public static String[] getCutVideoCommand(String inputPath, String outputPath, String startTime, String endTime) {
        Logger.e("VideoTrimmerView", "startTime=" + startTime + " endTime=" + endTime);
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-ss");
//        cmdlist.append("00:00:00");
        cmdlist.append(startTime);
        cmdlist.append("-t");
//        cmdlist.append("00:00:10");//    cmdlist.append("15");需要使用长度，使用时间格式会无效果，直接是startTime截取到最后
        cmdlist.append(endTime);
        cmdlist.append("-i");
        cmdlist.append(inputPath);
        cmdlist.append("-vcodec");
//        cmdlist.append("-codec");
//        cmdlist.append("-c");
//        cmdlist.append("copy");
        // cmdlist.append("mpeg4");
        cmdlist.append("mpeg4");
      cmdlist.append("-b:v");
//        cmdlist.append("-b");
//        cmdlist.append("2097152");
        cmdlist.append("10000K");

//        cmdlist.append("-r");
//        cmdlist.append("100");
//        cmdlist.append("-b:a");
//        cmdlist.append("48000");
//        cmdlist.append("-ac");
//        cmdlist.append("2");
//        cmdlist.append("-ar");
//        cmdlist.append("22050");
//       cmdlist.append("-avoid_negative_ts");
//        cmdlist.append("1");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }

    /**
     * 常用参数说明：
     * <p>
     * 主要参数：
     * -i 设定输入流
     * -f 设定输出格式
     * -ss 开始时间
     * 视频参数：
     * -b 设定视频流量，默认为200Kbit/s
     * -r 设定帧速率，默认为25
     * -s 设定画面的宽与高
     * -aspect 设定画面的比例
     * -vn 不处理视频
     * -vcodec 设定视频编解码器，未设定时则使用与输入流相同的编解码器
     * 音频参数：
     * -ar 设定采样率
     * -ac 设定声音的Channel数
     * -acodec 设定声音编解码器，未设定时则使用与输入流相同的编解码器
     * -an 不处理音频
     *
     * @param inputPath
     * @param outputPath
     * @param startTime
     * @param endTime
     * @param videorate
     * @return
     */
    public static String[] getCutVideoCommandH(String inputPath, String outputPath, String startTime, String endTime, String videorate,int videoWidth,int videoHeight ) {
        Logger.e("VideoTrimmerView", "startTime=" + startTime + " endTime=" + endTime + ",videorate=" + videorate);
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-ss");
        //ffmpeg -i %s -s 1080x720 %s
//        cmdlist.append("00:00:00");
        cmdlist.append(startTime);
        cmdlist.append("-t");
//        cmdlist.append("00:00:10");//    cmdlist.append("15");需要使用长度，使用时间格式会无效果，直接是startTime截取到最后
        cmdlist.append(endTime);
        cmdlist.append("-i");
        cmdlist.append(inputPath);
        cmdlist.append("-crf");
        cmdlist.append("20");
        cmdlist.append("-vcodec");
//        cmdlist.append("-codec");
//        cmdlist.append("-c");
//        cmdlist.append("copy");
        // cmdlist.append("mpeg4");
        // cmdlist.append("mpeg4");
        cmdlist.append("h264");
        cmdlist.append("-b:v");
//        cmdlist.append("2097152");
        cmdlist.append(videorate);
       /* cmdlist.append("-s");
        cmdlist.append("1080*720");
        cmdlist.append("-aspect");
        cmdlist.append("16:9");*/
        //-aspect 16:9
//        cmdlist.append("-r");
//        cmdlist.append("100");
//        cmdlist.append("-b:a");
//        cmdlist.append("48000");
//        cmdlist.append("-ac");
//        cmdlist.append("2");
//        cmdlist.append("-ar");
//        cmdlist.append("22050");
//       cmdlist.append("-avoid_negative_ts");
//        cmdlist.append("1");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }
    public static String[] getCutVideoCommandV(String inputPath, String outputPath, String startTime, String endTime, String videorate) {
        Logger.e("VideoTrimmerView", "startTime=" + startTime + " endTime=" + endTime + ",videorate=" + videorate);
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-ss");
//        cmdlist.append("00:00:00");
        cmdlist.append(startTime);
        cmdlist.append("-t");
//        cmdlist.append("00:00:10");//    cmdlist.append("15");需要使用长度，使用时间格式会无效果，直接是startTime截取到最后
        cmdlist.append(endTime);
        cmdlist.append("-i");
        cmdlist.append(inputPath);
        cmdlist.append("-vcodec");
//        cmdlist.append("-codec");
//        cmdlist.append("-c");
//        cmdlist.append("copy");
        // cmdlist.append("mpeg4");
        cmdlist.append("h264");
        cmdlist.append("-b:v");
//        cmdlist.append("2097152");
        cmdlist.append(videorate);
//        cmdlist.append("-r");
//        cmdlist.append("100");
//        cmdlist.append("-b:a");
//        cmdlist.append("48000");
//        cmdlist.append("-ac");
//        cmdlist.append("2");
//        cmdlist.append("-ar");
//        cmdlist.append("22050");
//       cmdlist.append("-avoid_negative_ts");
//        cmdlist.append("1");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }

    /**
     * 转换成特定分辨率视频
     *
     * @param inputPath
     * @param outputPath
     * @return
     */
    public static String[] getTransformCommand(String inputPath, String outputPath) {
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(inputPath);
        cmdlist.append("-s");
        cmdlist.append("1080*720");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }


    /**
     * 剪切音频
     *
     * @param inputPath
     * @param outputPath
     * @return
     */
    public static String[] getCutAudioCommand(String inputPath, String outputPath) {
        RxFFmpegCommandList cmdlist = new RxFFmpegCommandList();
        cmdlist.append("-i");
        cmdlist.append(inputPath);
        cmdlist.append("-vn");
        cmdlist.append("-acodec");
        cmdlist.append("copy");
        cmdlist.append("-ss");
        cmdlist.append("00:00:00");
        cmdlist.append("-t");
        cmdlist.append("00:00:10");
        cmdlist.append(outputPath);
        return cmdlist.build();
    }
}
