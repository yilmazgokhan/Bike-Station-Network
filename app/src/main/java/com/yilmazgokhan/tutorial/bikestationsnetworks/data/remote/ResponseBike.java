package com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote;

import com.google.gson.annotations.SerializedName;

public class ResponseBike {

    @SerializedName("network")
    private Network network;

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
