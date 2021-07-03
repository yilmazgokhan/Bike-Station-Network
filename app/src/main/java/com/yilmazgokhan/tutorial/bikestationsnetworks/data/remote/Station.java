package com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote;

import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("empty_slots")
    private Integer emptySlots;
    @SerializedName("extra")
    private Extra extra;
    @SerializedName("free_bikes")
    private Integer freeBikes;
    @SerializedName("id")
    private String id;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("name")
    private String name;
    @SerializedName("timestamp")
    private String timestamp;

    public Integer getEmptySlots() {
        return emptySlots;
    }

    public void setEmptySlots(Integer emptySlots) {
        this.emptySlots = emptySlots;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public Integer getFreeBikes() {
        return freeBikes;
    }

    public void setFreeBikes(Integer freeBikes) {
        this.freeBikes = freeBikes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
