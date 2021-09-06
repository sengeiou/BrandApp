package com.isport.brandapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.isport.brandapp.R;


/*
 * 通用定时器控件封装/针对短信发送
 * classes : com.topoto.project.view
 * @author 苗恒聚
 * V 1.0.0
 * Create at 2016/10/9 8:52
 */
public class TimerView extends LinearLayout {

    /**
     * 标题
     */
    private String titleText;

    /**
     * 定时器时长
     */
    private int second;

    /**
     * 当前时长
     */
    private int curSecond;

    private TextView titleTextView;

    private Handler handler = new Handler(Looper.getMainLooper());

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBase(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TimerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initBase(context, attrs, defStyleAttr);
    }

    private void initBase(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Timer,
                defStyleAttr, 0);
        titleText = a.getString(R.styleable.Timer_text);
        second = a.getInteger(R.styleable.Timer_second, 0);

        a.recycle();

        initView();
        setListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.app_view_timer, this, true);
        titleTextView = (TextView) findViewById(R.id.view_timer);
    }

    private void initData() {
        if (!TextUtils.isEmpty(titleText)) {
            titleTextView.setText(titleText);
        }
    }

    private void setListener() {
    }

    public void setTitle(int resId) {
        if ((resId >>> 24) < 2) {
            return;
        }
        setTitle(getContext().getString(resId));
    }

    public void setTitle(String text) {
        if (null == titleTextView) {
            return;
        }
        this.titleText = text;
        titleTextView.setText(TextUtils.isEmpty(text) ? "" : text);
    }

    public void initTimer() {
        long time = (System.currentTimeMillis() - getTime()) / 1000;
        if (time < second) {
            startTimer((int) time);
        }
    }

    public void startTimer() {
        curSecond = second;
        setEnabled(false);
        titleTextView.setEnabled(false);

        titleTextView.setText(curSecond + "s");

        saveTime(System.currentTimeMillis());
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1000);
    }

    public void startTimer(int millisecond) {
        this.second = millisecond;
        startTimer();
    }

    public void stopTimer() {
        handler.removeCallbacks(runnable);
        setEnabled(true);
        titleTextView.setEnabled(true);
        setTitle(titleText);
    }

    private void saveTime(long time) {
        SharedPreferences preferences = getContext().getSharedPreferences(TimerView.class.getSimpleName(),
                0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("time", time);
        editor.commit();
    }

    private long getTime() {
        SharedPreferences preferences = getContext().getSharedPreferences(TimerView.class.getSimpleName(),
                0);
        return preferences.getLong("time", 0);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            curSecond--;
            if (curSecond <= 0) {
                setEnabled(true);
                titleTextView.setEnabled(true);
                setTitle(titleText);
                saveTime(0);
                return;
            }
            titleTextView.setText(curSecond + "s");
            handler.postDelayed(runnable, 1000);
        }
    };
}