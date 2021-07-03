package com.yilmazgokhan.tutorial.bikestationsnetworks.ui;

import android.util.Log;

import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.CityItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.StationItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote.ResponseBike;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote.ResponseCity;
import com.yilmazgokhan.tutorial.bikestationsnetworks.service.Client;
import com.yilmazgokhan.tutorial.bikestationsnetworks.service.IRequest;
import com.yilmazgokhan.tutorial.bikestationsnetworks.util.Constant;
import com.yilmazgokhan.tutorial.bikestationsnetworks.util.Mapper;
import com.huawei.hms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private IRequest request;
    private List<StationItem> lastSearch;
    private Mapper mapper;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.request = Client.getApiClient().create(IRequest.class);
        this.lastSearch = new ArrayList<>();
        this.mapper = new Mapper();
    }

    @Override
    public void mapReady() {
        this.getAllNetworks();
    }

    /**
     * Get all city info which have bike stations & network
     */
    @Override
    public void getAllNetworks() {
        Call<ResponseCity> call = request.getAllNetworks();
        call.enqueue(new Callback<ResponseCity>() {
            @Override
            public void onResponse(Call<ResponseCity> call, Response<ResponseCity> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Collections.sort(response.body().getNetworks(), (lhs, rhs) -> (lhs.getName().compareTo(rhs.getName())));
                    view.prepareCityRecycler(mapper.mapNetwork(response.body().getNetworks()));
                } else
                    view.showMessage("Something went wrong.");
            }

            @Override
            public void onFailure(Call<ResponseCity> call, Throwable t) {
                Log.d(Constant.TAG, "onFailure: " + t.getMessage());
                view.showMessage("Something went wrong.");
            }
        });
    }

    @Override
    public void cityClicked(CityItem cityItem) {
        if (!cityItem.getId().equals("")) {
            this.getSelectedCity(cityItem.getId());
            view.moveCamera(new LatLng(cityItem.getLat(), cityItem.getLng()));
        }
    }

    @Override
    public void getSelectedCity(String city) {
        Call<ResponseBike> call = request.getNetwork(city);
        call.enqueue(new Callback<ResponseBike>() {
            @Override
            public void onResponse(Call<ResponseBike> call, Response<ResponseBike> response) {
                if (response.isSuccessful()) {
                    try {
                        Collections.sort(response.body().getNetwork().getStations(), (lhs, rhs) -> (lhs.getExtra().getUid().compareTo(rhs.getExtra().getUid())));
                    } catch (Exception e) {
                        Log.d(Constant.TAG, "onResponse: " + e.getMessage());
                    } finally {
                        lastSearch.clear();
                        lastSearch.addAll(mapper.mapCity(response.body().getNetwork().getStations()));
                        view.prepareInfo(response.body().getNetwork().getLocation().getCity(), response.body().getNetwork().getLocation().getCountry(),
                                response.body().getNetwork().getStations().size());
                        view.moveCamera(new LatLng(response.body().getNetwork().getLocation().getLatitude(), response.body().getNetwork().getLocation().getLongitude()));
                        view.prepareMarkers(lastSearch);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBike> call, Throwable t) {
                Log.d(Constant.TAG, "onFailure: " + t.getMessage());
                view.showMessage("Something went wrong.");
                view.makeInfoViewGone();
            }
        });
    }

    @Override
    public void drawOnMapClicked() {
        if (this.lastSearch.size() > 0)
            view.preparePolyline(this.lastSearch);
        else view.showMessage("Something went wrong.");
    }
}