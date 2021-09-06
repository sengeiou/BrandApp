package com.isport.brandapp.sport.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.brandapp.AppConfiguration;
import com.isport.brandapp.R;
import com.isport.brandapp.bean.DeviceBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import brandapp.isport.com.basicres.commonutil.MessageEvent;
import phone.gym.jkcq.com.commonres.common.JkConfiguration;

public class FragmentList extends Fragment {

    public static final int TYPE_DAY = 1;
    public static final int TYPE_WEEK = 2;
    public static final int TYPE_MONTH = 3;
    private int type;

    private ViewPager viewPager;

    private FragmentStatePagerAdapter pagerAdapter;
    private DeviceBean deviceBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceBean = (DeviceBean) getArguments().getSerializable(JkConfiguration.DEVICE);
        type = getArguments().getInt("type", TYPE_DAY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, null);

        viewPager = (ViewPager) view;
        EventBus.getDefault().register(this);
        pagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (type) {
                    case TYPE_DAY:
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, -(getCount() - (position + 1)));
                        int date = (int) (cal.getTimeInMillis() / 1000);
                        Fragment fragment = new DayFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("date", date);
                        bundle.putSerializable(JkConfiguration.DEVICE, deviceBean);
                        fragment.setArguments(bundle);
                        return fragment;
                    case TYPE_WEEK:
                        Calendar weekcal = null;
                        if (TextUtils.isEmpty(AppConfiguration.hasSportDate)) {
                            weekcal = getCurretWeek(getCount() - (position + 1));
                        } else {
                            weekcal = getCurretWeek(getCount() - (position + 1), AppConfiguration.hasSportDate);
                        }
                        Date firstDayWeek = getFirstDayOfWeek(weekcal.getTime());
                        String startWeek = DateUtil.dataToString(firstDayWeek, "yyyy-MM-dd");
                        String endWeek = DateUtil.dataToString(getLastDayOfWeek(weekcal.getTime()), "yyyy-MM-dd");
                        //  int weekdate = (int) (weekcal.getTimeInMillis() / 1000);
                        Fragment weekfragment = new WeekFragment();
                        Bundle weekbundle = new Bundle();
                        weekbundle.putBoolean("isLast", (getCount() - (position + 1) == 0) ? true : false);
                        weekbundle.putInt("date", (int) (firstDayWeek.getTime() / 1000));
                        weekbundle.putSerializable(JkConfiguration.DEVICE, deviceBean);
                        weekbundle.putString("startdate", startWeek);
                        weekbundle.putString("enddate", endWeek);
                        weekfragment.setArguments(weekbundle);
                        return weekfragment;
                    case TYPE_MONTH:
                        Calendar monthcal = null;
                        if (TextUtils.isEmpty(AppConfiguration.hasSportDate)) {
                            monthcal = Calendar.getInstance();
                        } else {
                            monthcal = getCurrentMonth(AppConfiguration.hasSportDate);
                        }
                        monthcal.add(Calendar.MONTH, -(getCount() - (position + 1)));
                        String month = DateUtil.dataToString(monthcal.getTime(), "yyyy-MM");
                        int monthdate = (int) (monthcal.getTimeInMillis() / 1000);
                        Fragment monthfragment = new MonthFragment();
                        Bundle monthbundle = new Bundle();
                        monthbundle.putSerializable(JkConfiguration.DEVICE, deviceBean);
                        monthbundle.putInt("date", monthdate);
                        monthbundle.putBoolean("isLast", (getCount() - (position + 1) == 0) ? true : false);
                        monthbundle.putString("month", month);
                        monthfragment.setArguments(monthbundle);
                        return monthfragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 100;
            }
        }

        ;

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(99);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        switch (messageEvent.getMsg()) {
            case MessageEvent.DayAdd:
                int currentCount = viewPager.getCurrentItem();
                Logger.myLog("currentCount=" + currentCount);
                if (currentCount - 1 < 0) {
                    currentCount = 0;
                }
                viewPager.setCurrentItem(currentCount - 1);
                break;
            case MessageEvent.DaySub:
                int currentSubCount = viewPager.getCurrentItem();
                if (viewPager.getAdapter().getCount() < currentSubCount) {
                    currentSubCount = currentSubCount - 1;
                }
                Logger.myLog("currentCount=" + currentSubCount);
                viewPager.setCurrentItem(currentSubCount + 1);
                break;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }


    public int getStartWeek() {

        return 0;
    }

    public Calendar getCurrentMonth(String startTime) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = ft.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar weekcal = Calendar.getInstance();
        if (date != null) {
            weekcal.setTime(date);
        }
        return weekcal;
    }

    public Calendar getCurretWeek(int positon, String startTime) {


        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = ft.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar weekcal = Calendar.getInstance();
        if (date != null) {
            weekcal.setTime(date);
        }

        //代码编写时间：2015年11月17日14:40:12
        Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 每周从周一开始
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.setTime(new Date());
        weekcal.add(Calendar.WEEK_OF_YEAR, -positon);
        int weeks = cal.get(Calendar.WEEK_OF_YEAR);

        System.out.println(weeks);
        return weekcal;
    }

    public Calendar getCurretWeek(int positon) {


        Calendar weekcal = Calendar.getInstance();


        //代码编写时间：2015年11月17日14:40:12
        Calendar cal = Calendar.getInstance();//这一句必须要设置，否则美国认为第一天是周日，而我国认为是周一，对计算当期日期是第几周会有错误
        cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 每周从周一开始
        cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
        cal.setTime(new Date());
        weekcal.add(Calendar.WEEK_OF_YEAR, -positon);
        int weeks = cal.get(Calendar.WEEK_OF_YEAR);

        System.out.println(weeks);
        return weekcal;
    }

    public static String getWeek(Calendar calendar) {
        String Week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }


    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }


}
