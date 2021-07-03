package com.yilmazgokhan.tutorial.bikestationsnetworks.util;

import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.CityItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.StationItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote.Network;
import com.yilmazgokhan.tutorial.bikestationsnetworks.data.remote.Station;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private DateFormatter dateFormatter;

    public Mapper() {
        this.dateFormatter = new DateFormatter();
    }

    public List<CityItem> mapNetwork(List<Network> networks) {
        List<CityItem> cityItems = new ArrayList<>();
        for (Network network : networks) {
            CityItem city = new CityItem();
            city.setId(network.getId());
            city.setCity(network.getLocation().getCity());
            city.setLat(network.getLocation().getLatitude());
            city.setLng(network.getLocation().getLongitude());
            city.setName(network.getName());
            cityItems.add(city);
        }
        return cityItems;
    }

    public List<StationItem> mapCity(List<Station> stationList) {
        List<StationItem> stationItems = new ArrayList<>();
        for (Station station : stationList) {
            StationItem stationItem = new StationItem();
            stationItem.setEmptySlots(station.getEmptySlots());
            stationItem.setFreeBikes(station.getFreeBikes());
            stationItem.setLatitude(station.getLatitude());
            stationItem.setLongitude(station.getLongitude());
            stationItem.setName(station.getName());
            stationItem.setTotalSlots(station.getExtra().getSlots());
            stationItem.setUpdateTime(dateFormatter.getFormattedDate(station.getTimestamp()));
            stationItems.add(stationItem);
        }
        return stationItems;
    }
}
