package com.isport.blelibrary.db.parse;

public class ChallengeItem {
    /**
     * {
     * "challengeItemId": 1,
     * "userId": 317
     * }
     */
    String challengeItemId;
    String userId;

    public String getChallengeItemId() {
        return challengeItemId;
    }

    public void setChallengeItemId(String challengeItemId) {
        this.challengeItemId = challengeItemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
