package com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Extra {

    @SerializedName("bike_uids")
    private List<String> bikeUids = null;
    @SerializedName("number")
    private String number;
    @SerializedName("slots")
    private Integer slots;
    @SerializedName("uid")
    private String uid;

    public List<String> getBikeUids() {
        return bikeUids;
    }

    public void setBikeUids(List<String> bikeUids) {
        this.bikeUids = bikeUids;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getSlots() {
        return slots;
    }

    public void setSlots(Integer slots) {
        this.slots = slots;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
