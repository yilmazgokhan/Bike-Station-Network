package com.yilmazgokhan.tutorial.bikestationsnetworks.ui;

import android.os.Bundle;

import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.CityItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.StationItem;
import com.huawei.hms.maps.model.LatLng;

import java.util.List;

public interface MainContract {

    interface View {

        void initHuaweiMap(Bundle bundle);

        void initCityRecycler();

        void initClicks();

        void prepareCityRecycler(List<CityItem> items);

        void prepareInfo(String city, String country, int count);

        void clearHuaweiMap();

        void prepareMarkers(List<StationItem> stationItems);

        void preparePolyline(List<StationItem> stationItems);

        void moveCamera(LatLng latLng);

        void bottomSheetSetCollapsed();

        void showMessage(String message);

        void makeInfoViewVisible();

        void makeInfoViewGone();
    }

    interface Presenter {

        void mapReady();

        void getAllNetworks();

        void cityClicked(CityItem cityItem);

        void getSelectedCity(String city);

        void drawOnMapClicked();
    }
}
