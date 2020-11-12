package com.thetechnoobs.reactiontest;

public class User {
    private String UserID = "";
    private String Name = "";
    private String Time = "59.999";
    private String AverageTime = "40.888";
    private String FastestReactionTime = "40.888";
    private String FastestAvgReactionTime = "40.888";

    public User(){
    }


    public User(String name, String userID, String time, String fastestReactionTime, String fastestAvgReactionTime) {
        Name = name;
        Time = time;
        UserID = userID;
        FastestReactionTime = fastestReactionTime;
        FastestAvgReactionTime = fastestAvgReactionTime;

    }

    public String getFastestAvgReactionTime() {
        return FastestAvgReactionTime;
    }

    public void setFastestAvgReactionTime(String fastestAvgReactionTime) {
        FastestAvgReactionTime = fastestAvgReactionTime;
    }

    public String getFastestReactionTime() {
        return FastestReactionTime;
    }

    public void setFastestReactionTime(String fastestReactionTime) {
        FastestReactionTime = fastestReactionTime;
    }

    public String getImgRecAverageTime() {
        return AverageTime;
    }

    public void setImgRecAverageTime(String averageTime) {
        AverageTime = averageTime;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgFastestTime() {
        return Time;
    }

    public void setImgFastestTime(String times) {
        Time = times;
    }
}
