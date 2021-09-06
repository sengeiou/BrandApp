package com.isport.brandapp.ropeskipping.speakutil;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.App;

import java.util.concurrent.ConcurrentHashMap;

public class SpeakUtil {

    String TAG = "SpeakUtil";
    volatile private static SpeakUtil instance = null;
    // 语音合成对象
    public static volatile SpeechSynthesizer mTts;

    private Context context;


    public static SpeakUtil getInstance() {
        try {
            if (instance != null) {//懒汉式

            } else {
                //创建实例之前可能会有一些准备性的耗时工作
                Thread.sleep(300);
                synchronized (SpeakUtil.class) {
                    if (instance == null) {//二次检查
                        instance = new SpeakUtil();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }

    ConcurrentHashMap<Integer, String> readList = new ConcurrentHashMap<>();
    // 默认发音人
    private String voicer = "catherine";

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;


    public void initSpeak(Context context) {
        this.context = context;
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
    }

    public void startSpeaking(String value, boolean addquery) {

        if (mTts.isSpeaking()) {
            if (addquery) {
                readList.put(1, value);
            }
        } else {
            int codePause = mTts.startSpeaking(value, mTtsListener);
        }
    }


    /**
     * 合成回调监听。
     */
    protected SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Logger.myLog("mTtsListener 开始播放");
            //showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            Logger.myLog("mTtsListener 暂停播放");
            // showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Logger.myLog("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
               /* // 合成进度
                mPercentForBuffering = percent;
                showTip(String.format(getString(R.string.tts_toast_format),
                        mPercentForBuffering, mPercentForPlaying));*/
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
             /*   mPercentForPlaying = percent;
                showTip(String.format(getString(R.string.tts_toast_format),
                        mPercentForBuffering, mPercentForPlaying));

                SpannableStringBuilder style = new SpannableStringBuilder(texts);
                Log.e(TAG, "beginPos = " + beginPos + "  endPos = " + endPos);
                style.setSpan(new BackgroundColorSpan(Color.RED), beginPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((EditText) findViewById(R.id.tts_text)).setText(style);*/
        }

        @Override
        public void onCompleted(SpeechError error) {
            Logger.myLog("mTtsListener onCompleted");
            if (error == null) {
                Logger.myLog("播放完成");
                if (readList.size() > 0 && !mTts.isSpeaking()) {
                    if (readList.containsKey(1)) {
                        mTts.startSpeaking(readList.get(1), mTtsListener);
                        readList.remove(1);
                    }
                }
            } else if (error != null) {
                Logger.myLog(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}

            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
                Log.e("MscSpeechLog", "buf is =" + buf);
            }

        }
    };


    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Logger.myLog(TAG + " mTtsInitListener InitListener init() code = " + code);
            // Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                // showTip("初始化失败,错误码：" + code);
                Logger.myLog(TAG + " mTtsInitListener InitListener init()初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里

                if (App.isZh(context)) {
                    setParam();
                } else {
                    setParamEn();
                }

            }
        }
    };

    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);

        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    /**
     * 参数设置 是英文模式
     *
     * @return
     */
    private void setParamEn() {


        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);

        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //mTts.setParameter(SpeechConstant.ACCENT,)
            mTts.setParameter(SpeechConstant.ACCENT, null);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            mTts.setParameter(SpeechConstant.LANGUAGE, "en_us");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }


}
