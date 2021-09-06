package com.isport.brandapp.device.sleep;

public class SleepConfig {

	/**
	 * 早上6点前，算昨天的数据
	 */
	public static final byte SLEEP_DIFF = 6;

	/**
	 * 睡眠评级
	 */
	public static final class SleepLevel {
		/**
		 * 极好
		 */
		public static final byte SUPPER = 1;
		/**
		 * 良好
		 */
		public static final byte GOOD = 2;
		/**
		 * 较差
		 */
		public static final byte POOR = 3;
		/**
		 * 很差
		 */
		public static final byte BAD = 4;
	}

	/**
	 * 深浅睡眠状态
	 */
	public static final class SleepState {
		/**
		 * 描述：清醒
		 */
		public static final byte WAKE = 0;
		/**
		 * 描述：浅睡
		 */
		public static final byte LIGHT = 1;
		/**
		 * 描述：中睡
		 */
		public static final byte MID = 2;
		/**
		 * 描述：深睡
		 */
		public static final byte DEEP = 3;
	}

	/**
	 * 特殊睡眠状态
	 */
	public static final class SleepStatus {
		/**
		 * 心跳暂停
		 */
		public static final byte HEART_PAUSE = -1;
		/**
		 * 呼吸暂停
		 */
		public static final byte BREATH_PAUSE = 2;
		/**
		 * 上床
		 */
		public static final byte GO_TO_BED = 3;
		/**
		 * 下床
		 */
		public static final byte OUT_OF_BED = 4;
		/**
		 * 打鼾
		 */
		public static final byte SNORE = 5;
		/**
		 * 翻身
		 */
		public static final byte TURN_OVER = 6;
	}
	
	/**
	 * 睡眠指标
	 */
	public static final class SleepQuota {
		/**
		 * 睡眠时长
		 */
		public static final byte SLEEP_TIME = 1;
		/**
		 * 睡得太晚
		 */
		public static final byte SLEEP_LATE = 2;
		/**
		 * 呼吸过速
		 */
		public static final byte BREATH_FAST = 3;
		/**
		 * 呼吸过缓
		 */
		public static final byte BREATH_SLOW = 4;
		/**
		 * 打鼾次数
		 */
		public static final byte SNORE_COUNT = 5;
		/**
		 * 翻身次数
		 */
		public static final byte TURN_OVER = 6;
		/**
		 * 离床次数
		 */
		public static final byte LEAVE_BED = 7;
		/**
		 * 深睡时长
		 */
		public static final byte DEEP_SLEEP = 8;
		/**
		 * 入睡时长
		 */
		public static final byte ASLEEP_TIME = 9;
		/**
		 * 呼吸暂停次数
		 */
		public static final byte BREATH_PAUSE = 10;
	}

	public static final byte HEART_MIN = 0;
	public static final short HEART_MAX = 160;
	public static final byte HEART_NORMAL_MIN = 60;
	public static final short HEART_NORMAL_MAX = 100;

	public static final byte BREATH_MAX = 30;
	public static final byte BREATH_MIN = 0;
	public static final byte BREATH_NORMAL_MIN = 12;
	public static final byte BREATH_NORMAL_MAX = 24;

	public static final String CONFIG_FILE = "config";

}
