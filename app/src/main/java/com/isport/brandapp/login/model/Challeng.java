package com.isport.brandapp.login.model;

import java.io.Serializable;

public class Challeng implements Serializable {
    String challengeItemId;
    String name;
    int challengeType;
    int achieveSecond;
    int achieveNum;

    public String getChallengeItemId() {
        return challengeItemId;
    }

    public void setChallengeItemId(String challengeItemId) {
        this.challengeItemId = challengeItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(int challengeType) {
        this.challengeType = challengeType;
    }

    public int getAchieveSecond() {
        return achieveSecond;
    }

    public void setAchieveSecond(int achieveSecond) {
        this.achieveSecond = achieveSecond;
    }


    public int getAchieveNum() {
        return achieveNum;
    }

    public void setAchieveNum(int achieveNum) {
        this.achieveNum = achieveNum;
    }

    @Override
    public String toString() {
        return "Challeng{" +
                "challengeItemId='" + challengeItemId + '\'' +
                ", name='" + name + '\'' +
                ", challengeType=" + challengeType +
                ", achieveSecond=" + achieveSecond +
                ", achieveNum=" + achieveNum +
                '}';
    }
}
