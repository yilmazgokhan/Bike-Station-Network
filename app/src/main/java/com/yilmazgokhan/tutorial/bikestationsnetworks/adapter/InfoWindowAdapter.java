package com.yilmazgokhan.tutorial.bikestationsnetworks.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Marker;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.StationItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.databinding.CustomMarkerInfoBinding;

public class InfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        try {
            CustomMarkerInfoBinding binding = CustomMarkerInfoBinding.inflate(LayoutInflater.from(context));
            StationItem stationItem = (StationItem) marker.getTag();
            binding.txtName.setText(stationItem.getName() != null ? stationItem.getName() : "N/A");
            binding.txtEmptySlots.setText(stationItem.getEmptySlots() != null ? String.format("Empty Slots: %s", stationItem.getEmptySlots()) : "N/A");
            binding.txtTotalSlot.setText(stationItem.getTotalSlots() != null ? String.format("Total Slots: %s", stationItem.getTotalSlots()) : "N/A");
            binding.txtFreeBikes.setText(stationItem.getFreeBikes() != null ? String.format("Free Bikes: %s", stationItem.getFreeBikes()) : "N/A");
            binding.txtUpdated.setText(stationItem.getUpdateTime() != null ? String.format("Updated: %s", stationItem.getUpdateTime()) : "N/A");
            return binding.getRoot();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}