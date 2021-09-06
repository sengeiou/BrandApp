package com.isport.brandapp.Home.bean;

public class SportLastDataBean {
    /**
     * "type": 0,
     "lastTimestamp": 1550569030777,
     "lastDistance": 33.55,
     "allDistance": 33.55
     */

    int type;
    long lastTimestamp;
    String lastDistance;
    String allDistance;

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public String getLastDistance() {
        return lastDistance;
    }

    public void setLastDistance(String lastDistance) {
        this.lastDistance = lastDistance;
    }

    public String getAllDistance() {
        return allDistance;
    }

    public void setAllDistance(String allDistance) {
        this.allDistance = allDistance;
    }

    public int getType() {

        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
