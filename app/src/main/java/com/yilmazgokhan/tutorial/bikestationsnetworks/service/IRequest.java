package com.yilmazgokhan.tutorial.bikestationsnetworks.service;

import com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote.ResponseBike;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote.ResponseCity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRequest {

    @GET("networks")
    Call<ResponseCity> getAllNetworks();

    @GET("networks/{stationCode}")
    Call<ResponseBike> getNetwork(@Path("stationCode") String countryCode);
}
