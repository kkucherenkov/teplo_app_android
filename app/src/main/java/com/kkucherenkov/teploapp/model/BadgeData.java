package com.kkucherenkov.teploapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Kirill Kucherenkov on 05/09/16.
 */

public class BadgeData {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    private Date startDate;
    private Date endDate;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
}
