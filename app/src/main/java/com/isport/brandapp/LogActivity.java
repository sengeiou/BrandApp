package com.isport.brandapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.websocket.WsManager;
import com.isport.blelibrary.bluetooth.callbacks.BaseGattCallback;
import com.isport.blelibrary.utils.TimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * @创建者 bear
 * @创建时间 2019/3/21 17:37
 * @描述
 */
public class LogActivity extends Activity implements View.OnClickListener {


    TextView tvLog;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        tvLog = (TextView) findViewById(R.id.log_tv);
       if (BaseGattCallback.logBuilder == null) {
            BaseGattCallback.logBuilder = new StringBuilder();
        }
        tvLog.setText(BaseGattCallback.logBuilder.toString());
       /* if (WsManager.logBuilder == null) {
            WsManager.logBuilder = new StringBuilder();
        }
        tvLog.setText(WsManager.logBuilder.toString());*/
        scrollView = (ScrollView) findViewById(R.id.log_scrollview);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void verifyPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                finish();
                break;
            case R.id.tv_clear:

                BaseGattCallback.logBuilder = new StringBuilder();
                WsManager.logBuilder = new StringBuilder();
                //  tvLog.setText(BaseGattCallback.logBuilder.toString());
                tvLog.setText(WsManager.logBuilder.toString());

                break;
            case R.id.btn_save_log:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "you do not access to write extenal storage", Toast.LENGTH_LONG).show();
                } else {
                    saveLog();
                }
                break;
        }
    }

    private void saveLog() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            FileOutputStream outputStream = null;
            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/isport/" + TimeUtils.date2String(Calendar.getInstance().getTime(), "yyyyMMddHHmmss") + ".txt";
                File file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                outputStream = new FileOutputStream(file);
                if (BaseGattCallback.logBuilder == null) {

                } else {
                    byte[] bs = BaseGattCallback.logBuilder.toString().getBytes();
                    outputStream.write(bs);
                    outputStream.flush();

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            Toast.makeText(this, "extenal storage not mounted", Toast.LENGTH_LONG).show();
        }
    }

}
