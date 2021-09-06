package com.isport.blelibrary.result.entry;

/**
 * @Author
 * @Date 2018/10/29
 * @Fuction
 */

public class Summary{

        public Summary(int recordCount, int startTime, int stopMode) {
            this.recordCount = recordCount;
            this.startTime = startTime;
            this.stopMode = stopMode;
        }

        public int recordCount;
        public int startTime;
        public int stopMode;

        @Override
        public String toString() {
            return "Summary{" +
                    "recordCount=" + recordCount +
                    ", startTime=" + startTime +
                    ", stopMode=" + stopMode +
                    '}';
        }

}
