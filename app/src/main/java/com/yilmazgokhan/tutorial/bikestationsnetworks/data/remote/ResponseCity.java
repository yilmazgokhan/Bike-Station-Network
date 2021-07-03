package com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCity {

    @SerializedName("networks")
    private List<Network> networks;

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }
}
