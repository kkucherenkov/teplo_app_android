package com.kkucherenkov.teploapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Kirill Kucherenkov on 11/09/16.
 */

public class VisitorDetails {

    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("visitor_id")
    private String visitorId;
    @Expose
    @SerializedName("full_name")
    private String fullName;
    @Expose
    @SerializedName("start_date")
    private Date startDate;
    @Expose
    @SerializedName("end_date")
    private Date endDate;
    @Expose
    @SerializedName("stop_check")
    private int stopCheck;
    @Expose
    @SerializedName("stop_time")
    private int stopTime;

    public VisitorDetails() {
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStopCheck() {
        return stopCheck;
    }

    public void setStopCheck(int stopCheck) {
        this.stopCheck = stopCheck;
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }
}
