package com.jkcq.train.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import brandapp.isport.com.basicres.base.BaseConstant
import brandapp.isport.com.basicres.commonalertdialog.AlertDialogStateCallBack
import brandapp.isport.com.basicres.commonalertdialog.PublicAlertDialog
import com.jkcq.train.R
import kotlinx.android.synthetic.main.activity_train_video.*

class TrainVideoActivity : AppCompatActivity() {

    var isPlay = false

    var mVideoPath: String? = null
    var mCourseId: String? = null
    var isFullScreen = false;

    companion object {
        val HIDE_TIME = 5000L;
        val HIDE_PROGRESS = 10
    }

    var mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                HIDE_PROGRESS -> {
                    isFullScreen = true
                    ll_play_progress.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_train_video)

        iv_play_stop.setOnClickListener {
            videoview.pause()
            ll_play_progress.visibility = View.GONE
            ll_vedio_pause.visibility = View.VISIBLE
        }
        iv_play_continue.setOnClickListener {
            trainContinue()
        }
        iv_exit.setOnClickListener {
            PublicAlertDialog.getInstance().showDialog(getString(R.string.ensure_exit), getString(R.string.now_exit_can_not_save_data), this, getString(R.string.ensure_exit_train), getString(R.string.continue_train), object : AlertDialogStateCallBack {
                override fun determine() {
                    trainContinue()
                }

                override fun cancel() {
                    mCourseId?.let {
                        if (TextUtils.isEmpty(it)) {
                            return
                        }
                        var intent = Intent(this@TrainVideoActivity, ExitTrainActivity::class.java)
                        intent.putExtra(BaseConstant.EXTRA_COURSE_ID, it)
                        startActivity(intent)
                    }
                    finish()
                }
            }, false)

        }
        initVideoView()
        initData()
    }

    fun initVideoView() {
        mVideoPath = intent.getStringExtra(BaseConstant.EXTRA_VIDEO_URL)
        mCourseId = intent.getStringExtra(BaseConstant.EXTRA_COURSE_ID)
//        val path = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"

        if (TextUtils.isEmpty(mVideoPath)) {
            return;
        }
        if (videoview == null) {
            return;
        }
        videoview.setVideoPath(mVideoPath)
        videoview.requestFocus();
        videoview.setOnPreparedListener {
            videoview.start()
            hideProgress()
        }
        videoview.setOnInfoListener(object : MediaPlayer.OnInfoListener {
            override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                when (what) {
                    MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                        progress.max = videoview.duration
                        getVideoProgress()
                    }
                    MediaPlayer.MEDIA_INFO_BUFFERING_END -> {

                    }
                }
                return false;
            }

        })
        videoview.setOnErrorListener(object : MediaPlayer.OnErrorListener {
            override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                Log.e("TrainVideo", " onError")
                return true;
            }
        })
        videoview.setOnCompletionListener {
            Log.e("TrainVideo", "complete")
            runOnUiThread {
                mCourseId?.let {
                    var intent = Intent(this@TrainVideoActivity, TrainFinishActivity::class.java)
                    intent.putExtra(BaseConstant.EXTRA_COURSE_ID, it)
                    startActivity(intent)
                    finish()
                }
            }

        }
        videoview.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (isFullScreen) {
                            isFullScreen = false
                            ll_play_progress.visibility = View.VISIBLE
                        } else {
                            isFullScreen = true
                            ll_play_progress.visibility = View.GONE
                        }
                        mHandler.removeMessages(HIDE_PROGRESS)
                        hideProgress()
                    }
                }
                return false
            }

        })
    }


    fun trainContinue() {
        videoview.start()
        ll_vedio_pause.visibility = View.GONE
        ll_play_progress.visibility = View.VISIBLE
        hideProgress()
    }

    fun initData() {

    }

    override fun onStop() {
        super.onStop()
        videoview.stopPlayback()
        mHandler.removeMessages(0)
    }

    override fun onResume() {
        super.onResume()
    }

    fun getVideoProgress() {
        mHandler.postDelayed({
            tv_play_time.text = "${getFormatTime(videoview.currentPosition)}/${getFormatTime(videoview.duration)}"
            tv_train_duration.text = getFormatTime(videoview.currentPosition)
            progress.setProgress(videoview.currentPosition)
            Log.e("TrainVideo", " duration=" + videoview.currentPosition + " formatTime=" + getFormatTime(videoview.currentPosition))
            getVideoProgress()
        }, 500)
    }


    fun hideProgress() {
        var msg = Message.obtain()
        msg.what = HIDE_PROGRESS
        mHandler.sendMessageDelayed(msg, HIDE_TIME)
    }

    fun getFormatTime(time: Int): String? {
        var time = time
        time = time / 1000
        val second = time % 60
        val minute = time % 3600 / 60
        val hour = time / 3600
        // 毫秒秒显示两位
        // String strMillisecond = "" + (millisecond / 10);
        // 秒显示两位
        val strSecond = "00$second".substring("00$second".length - 2)
        // 分显示两位
        val strMinute = "00$minute".substring("00$minute".length - 2)
        // 时显示两位
//        val strHour = "00$hour".substring("00$hour".length - 2)
        return "$strMinute:$strSecond"
    }
}
