package com.isport.brandapp.device.sleep;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.view.WindowManager;

import com.isport.brandapp.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SleepUtil {
	private static final String TAG = SleepUtil.class.getSimpleName();
	private static final Random RANDOM = new Random();
	
	public static int getRandomNumber(int min, int max) {
		int randNumber = RANDOM.nextInt(max - min + 1) + min;
		return randNumber;
	}
	

	public static byte getAsleepLevel(int asleepTime) {
		byte level = 0;
		if(asleepTime <= 10) {
			level = 0;
		}else if(asleepTime <= 20){
			level = 1;
		}else if(asleepTime <= 40){
			level = 2;
		}else {
			level = 3;
		}
		return level;
	}
	
	public static byte getTurnOverLevel(int turnOverCount, int sleepDuration) {
		int sleepTimeHour = Math.round(sleepDuration / 60f);
		if(sleepTimeHour > 0) {
			turnOverCount = turnOverCount / sleepTimeHour;
		}
		return getTurnOverLevel(turnOverCount);
	}
	
	public static byte getTurnOverLevel(int turnOverCount) {
		byte level = 0;
		if(turnOverCount >= 6 && turnOverCount <= 10) {
			level = 0;
		}else if((turnOverCount >= 4 && turnOverCount < 6) || (turnOverCount > 10 && turnOverCount <= 13)){
			level = 1;
		}else if((turnOverCount >= 2 && turnOverCount < 4) || (turnOverCount > 13 && turnOverCount <= 15)){
			level = 2;
		}else {
			level = 3;
		}
		return level;
	}
	
	public static byte getLeaveBedLevel(int leaveBedCount) {
		byte level = 0;
		if(leaveBedCount <= 1) {//优
			level = 0;
		}else if(leaveBedCount <= 3){//良
			level = 1;
		}else if(leaveBedCount <= 4){//差
			level = 2;
		}else {//极差
			level = 3;
		}
		return level;
	}
	
	public static byte getBreathPauseCountLevel(int breathPauseCount) {
		byte level = 0;
		if(breathPauseCount <= 0) {
			level = 0;
		}else if(breathPauseCount <= 1){
			level = 1;
		}else if(breathPauseCount <= 2){
			level = 2;
		}else {
			level = 3;
		}
		return level;
	}
	
	public static byte getBreathPauseTimeLevel(int breathPauseTime) {
		byte level = 0;
		if(breathPauseTime <= 0) {
			level = 0;
		}else if(breathPauseTime <= 10){
			level = 1;
		}else if(breathPauseTime <= 30){
			level = 2;
		}else {
			level = 3;
		}
		return level;
	}
	

	/*public static byte getSleepState(short hr, short br, byte status) {
		if (isLeaveBed(hr, br, status)) {
			return SleepState.WAKE;
		} else if (hr > 60 && hr < 80 && br > 12 && br < 16) {
			return SleepState.DEEP;
		} else {
			return SleepState.LIGHT;
		}
	}*/
	


	/**
	 * 描述：获取app版本的语言
	 */
	public static String getLanguage(Context context) {
		Resources res = context.getResources();
		Configuration config = res.getConfiguration();
		String language = config.locale.getLanguage();
		return language;
	}

	public static String getCountry(Context context) {
		Resources res = context.getResources();
		Configuration config = res.getConfiguration();
		return config.locale.getCountry();
	}

	/**
	 * 判断手机是否装载sd卡
	 * @return
	 */
	public static boolean hasSdcard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获得当前app的版本versionCode
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
		}
		return verCode;
	}

	/**
	 * 获得当前app的版本versionName
	 */
	public static String getVerName(Context context) {
		String verName = null;
		try {
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
		}
		return verName;
	}

	public static int getAge(String birthday) {
		int age = 0;
		if (birthday != null && birthday.indexOf("-") > 0) {
			if (birthday.length() > 10) {// 如果格式为：yyyy-MM-dd HH:mm:ss，则去掉后面的时分秒
				birthday = birthday.substring(0, 10);
			}
			String[] str = birthday.split("-");
			int year = Integer.valueOf(str[0]);
			int month = Integer.valueOf(str[1]);
			int day = Integer.valueOf(str[2]);

			Calendar calendar = Calendar.getInstance();
			int curYear = calendar.get(Calendar.YEAR);
			int curMonth = calendar.get(Calendar.MONTH) + 1;
			int curDay = calendar.get(Calendar.DAY_OF_MONTH);

			age = curYear - year;
			if (curMonth - month > 0 || (curMonth == month && curDay - day > 0)) {
				if (age == 0) {
					age++;
				}
			} else {
				if (age > 1) {
					age--;
				}
			}
		}
		return age;
	}


	

	public static boolean isAppInstalled(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		List<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);
	}

	public static boolean isAppInstalled1(Context context, String packageName) {
		if (!TextUtils.isEmpty(packageName)) {
			try {
				/* ApplicationInfo info = */ context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
				return true;
			} catch (NameNotFoundException e) {

			}
		}
		return false;
	}

	public static boolean isAppRunning(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(context.getPackageName()) && info.baseActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
		// return GlobalInfo.userInfo.userId > 0;
	}

	/*
	 * public static boolean isActivityRunning(Context context, String className) {
	 * Intent intent = new Intent(); intent.setClassName(context.getPackageName(),
	 * className); if (context.getPackageManager().resolveActivity(intent, 0) ==
	 * null) { // 说明系统中不存在这个activity return false; } return true; }
	 */

	public static boolean isActivityRunning(Context context, String className) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
		if (cn.getClassName().equals(className)) {
			return true;
		}
		return false;
	}

	public static boolean isServiceRunning(Context mContext, String serviceName) {
		boolean isRunning = false;
		ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(100);
		final int size = myList == null ? 0 : myList.size();
		for (int i = 0; i < size; i++) {
			String mName = myList.get(i).service.getClassName().toString();
			// LogUtil.showMsg(TAG+" isServiceRunning size:"+
			// size+",name:"+serviceName+",cname:"+mName);
			if (mName.equals(serviceName)) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 判断当前应用程序处于前台还是后台
	 */
	public static boolean isAppForeground(final Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (tasks != null && tasks.size() > 0) {
			ComponentName topActivity = tasks.get(0).topActivity;
			// LogUtil.showMsg(TAG+" isAppForeground name:" +
			// topActivity.getShortClassName());
			if (topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public static int getScreenBrightness(Context context) {
		int value = 0;
		ContentResolver cr = context.getContentResolver();
		try {
			value = android.provider.Settings.System.getInt(cr, android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {

		}
		return value;
	}

	public static void setScreenBrightness(Activity activity, int lightness) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.screenBrightness = lightness / 255f;
		activity.getWindow().setAttributes(lp);
	}

	/**
	 * 判断是否当前月
	 * 
	 * @param year
	 * @param month
	 *            [1,12]
	 * @return
	 */
	public static boolean isCurrentMonth(int year, int month) {
		Calendar c = TimeUtil.getCalendar(-100);
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;
		if (year == curYear && month == curMonth) {
			return true;
		}
		return false;
	}

	/**
	 * Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	 * startActivityForResult(enabler, reqCode); 三星Note3
	 * 手机，系统不会弹出提示框，直接就打开蓝牙了，resultCode为0
	 * 
	 * @return
	 */
	public static boolean isSamsungNote3() {
		if (Build.BRAND.toLowerCase().indexOf("samsung") != -1 && Build.MODEL.toUpperCase().startsWith("SM-N9")) {
			return true;
		}
		return false;
	}

	public static boolean isHTC_M8St() {
		if (Build.MODEL.toUpperCase().equals("HTC M8ST")) {
			return true;
		}
		return false;
	}

	public static byte getWeekRepeat(byte[] repeat) {
		StringBuilder sb = new StringBuilder("0");
		for (int i = 6; i >= 0; i--) {
			sb.append(repeat[i]);
		}
		return Byte.valueOf(sb.toString(), 2);
	}

	public static byte[] getWeekRepeat(byte repeat) {
		// LogUtil.showMsg("getWeekRepeat repeat:"+repeat);
		String repeatStr = Integer.toBinaryString(repeat);
		int len = 7 - repeatStr.length();
		if (len > 0) {
			repeatStr = String.format("%0" + len + "d", 0) + repeatStr;
		}
		byte[] weekRepeat = new byte[7];
		for (int i = 0; i < 7; i++) {
			weekRepeat[i] = Byte.valueOf(String.valueOf(repeatStr.charAt(repeatStr.length() - 1 - i)));
		}
		return weekRepeat;
	}

	public static boolean isGoogleNexus6() {
		if (Build.BRAND.equals("google") && Build.MODEL.equals("Nexus 6")) {
			return true;
		}
		return false;
	}

	public static boolean isMeizuM1() {
		if (Build.BRAND.equals("Meizu") && Build.MODEL.equals("m1")) {
			return true;
		}
		return false;
	}

	public static boolean needDisableOthers() {
		if (isGoogleNexus6()/* || isMeizuM1() */) {
			return false;
		}
		return true;
	}

	/**
	 * 当前手机型号是否是N7108，是返回true，不是返回false ,可以不断添加
	 */
	public static boolean isPhone() {
		ArrayList<String> phoneModelArrayList = new ArrayList<String>();
		phoneModelArrayList.add("GT-N7108");
		String phoneModel = Build.MODEL;
		if (phoneModelArrayList.size() == 0) {
			return false;
		}
		for (int i = 0; i < phoneModelArrayList.size(); i++) {
			if (phoneModel.equals(phoneModelArrayList.get(i))) {
				return true;
			}
		}
		return false;

	}

	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		for (RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}

	public static String getId(int id) {
		String idStr = null;
		try {
			Field[] fields = R.id.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.getInt(field.getName()) == id) {
					idStr = field.getName();
					break;
				}
			}
		} catch (Exception e) {
		}
		return idStr;
	}
}
