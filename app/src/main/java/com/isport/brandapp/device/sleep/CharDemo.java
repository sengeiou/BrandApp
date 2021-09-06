package com.isport.brandapp.device.sleep;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.isport.brandapp.R;
import com.isport.brandapp.device.sleep.calendar.Cell;
import com.isport.brandapp.device.sleep.calendar.PopCalendarView;

import android.view.ViewGroup.LayoutParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import brandapp.isport.com.basicres.BaseActivity;

public class CharDemo extends BaseActivity {


    TextView tvDate;


    @Override
    protected int getLayoutId() {
        return R.layout.app_activity_chart;
    }

    @Override
    protected void initView(View view) {

        tvDate = findViewById(R.id.tv_main_date);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDayPop();
                int[] loc = new int[2];
                v.getLocationOnScreen(loc);
                datePop.setTouchable(true);
                datePop.setOutsideTouchable(true);
                datePop.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                datePop.showAtLocation(v, Gravity.BOTTOM, 0, loc[1] + v.getHeight());
            }
        });
    }

    @Override
    protected void initHeader() {

    }


    private PopupWindow datePop;
    private PopCalendarView calendarview;
    private ImageView ivPreDate, ivNextDate;
    private TextView tvDatePopTitle, tvBackToay;

    private void createDayPop() {
        View v = LayoutInflater.from(this).inflate(R.layout.app_activity_chart_dem, null);
        calendarview = (PopCalendarView) v.findViewById(R.id.calendar);
        ivPreDate = (ImageView) v.findViewById(R.id.iv_pre);
        ivNextDate = (ImageView) v.findViewById(R.id.iv_next);
        tvDatePopTitle = (TextView) v.findViewById(R.id.tv_date);
        tvBackToay = (TextView) v.findViewById(R.id.tv_back_today);

        tvBackToay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarview.goCurrentMonth();
                initDatePopMonthTitle();
            }
        });

        ivPreDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendarview.previousMonth();
                initDatePopMonthTitle();
            }
        });

        ivNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                calendarview.nextMonth();
                initDatePopMonthTitle();
            }
        });

      /*  String sn = device == null ? "" : device.getSn();
        calendarview.setDeviceSN(sn);*/
        int time = (int) (System.currentTimeMillis() / 1000);
       /* if (dayAnalysis != null) {
            time = dayAnalysis.getStartTime() - SleepConfig.SLEEP_DIFF * 3600;
        }*/
        calendarview.setActiveC(TimeUtil.second2Millis(time));
        calendarview.setTimeInMillis(TimeUtil.second2Millis(time));

        initDatePopMonthTitle();

        // setPop_showTime(TimeUtill.second2Millis(time), -100);
        datePop = new PopupWindow(v, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        calendarview.setOnCellTouchListener(new PopCalendarView.OnCellTouchListener() {
            @Override
            public void onTouch(Cell cell) {
                /*if (cell.getStartTime() <= 0) {
                    Toast.makeText(BaseApp.getApp(),"没有数据", Toast.LENGTH_SHORT).show();
                    return;
                }
               */
                datePop.dismiss();
                int stime = cell.getStartTime();
                /*dayAnalysis = analysisDao.getAnalysis(device.getSn(), stime);
                LogUtil.log(TAG+" touch cell analysis:" + dayAnalysis);
                initDayReport();*/
            }
        });
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

    private void initDatePopMonthTitle() {
        Calendar calendar = calendarview.getDate();

        // LogUtil.log(TAG + " initDatePopMonthTitle:" + dateFormat.format(calendar.getTime()));
        tvDatePopTitle.setText(dateFormat.format(calendar.getTime()));
    }

}
