package com.yilmazgokhan.tutorial.bikestationsnetworks.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.huawei.hms.maps.model.PolygonOptions;
import com.yilmazgokhan.tutorial.bikestationsnetworks.R;
import com.yilmazgokhan.tutorial.bikestationsnetworks.adapter.CityAdapter;
import com.yilmazgokhan.tutorial.bikestationsnetworks.adapter.InfoWindowAdapter;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.CityItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.StationItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.databinding.ActivityMainBinding;
import com.yilmazgokhan.tutorial.bikestationsnetworks.util.Constant;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnMapReadyCallback {

    private ActivityMainBinding binding;
    private MainPresenter presenter;
    private HuaweiMap huaweiMap;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MainPresenter(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constant.MAP_BUNDLE);
        }

        this.initHuaweiMap(mapViewBundle);
        this.initCityRecycler();
        this.initClicks();
        this.bottomSheetSetCollapsed();
        this.makeInfoViewGone();
    }

    @Override
    public void initHuaweiMap(Bundle bundle) {
        Log.w(Constant.TAG, "initHuaweiMap");
        MapsInitializer.setApiKey(Constant.MAP_KEY);
        binding.mapView.onCreate(bundle);
        binding.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.w(Constant.TAG, "onMapReady ");
        this.huaweiMap = huaweiMap;
        presenter.mapReady();
        huaweiMap.setInfoWindowAdapter(new InfoWindowAdapter(this));
    }

    @Override
    public void initCityRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvCity.setLayoutManager(layoutManager);
        binding.rvCity.setItemAnimator(new DefaultItemAnimator());
        cityAdapter = new CityAdapter();
        binding.rvCity.setAdapter(cityAdapter);
        binding.rvCity.addItemDecoration(new DividerItemDecoration(binding.rvCity.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void initClicks() {
        cityAdapter.setItemClickListener(cityItem -> presenter.cityClicked(cityItem));
        binding.btnDrawOnMap.setOnClickListener(v -> presenter.drawOnMapClicked());
    }

    @Override
    public void prepareCityRecycler(List<CityItem> items) {
        this.cityAdapter.prepareCityList(items);
    }

    @Override
    public void prepareInfo(String city, String country, int count) {
        binding.txtCityInfo.setText(city);
        binding.txtCountryInfo.setText(country);
        binding.txtStationCountInfo.setText(String.valueOf(count));

        this.makeInfoViewVisible();
    }

    @Override
    public void clearHuaweiMap() {
        this.huaweiMap.clear();
    }

    @Override
    public void prepareMarkers(List<StationItem> stationItems) {
        this.clearHuaweiMap();
        for (StationItem station : stationItems) {
            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .title(station.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .clusterable(true)
                    .alpha(0.9f);
            Marker marker = huaweiMap.addMarker(options);
            marker.setTag(station);
        }
        huaweiMap.setMarkersClustering(true);
    }

    @Override
    public void preparePolyline(List<StationItem> stationItems) {
        List<LatLng> stationLocationList = new ArrayList<>();
        for (StationItem station : stationItems)
            stationLocationList.add(new LatLng(station.getLatitude(), station.getLongitude()));
        huaweiMap.addPolygon(new PolygonOptions()
                .addAll(stationLocationList)
                .fillColor(Color.RED));
    }

    @Override
    public void moveCamera(LatLng latLng) {
        huaweiMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        this.bottomSheetSetCollapsed();
    }

    @Override
    public void bottomSheetSetCollapsed() {
        BottomSheetBehavior.from(binding.bottomSheet).setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeInfoViewVisible() {
        binding.cvInfo.setVisibility(View.VISIBLE);
        binding.btnDrawOnMap.setVisibility(View.VISIBLE);
    }

    @Override
    public void makeInfoViewGone() {
        binding.cvInfo.setVisibility(View.GONE);
        binding.btnDrawOnMap.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        binding.mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }
}