package com.yilmazgokhan.tutorial.bikestationsnetworks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yilmazgokhan.tutorial.bikestationsnetworks.data.local.CityItem;
import com.yilmazgokhan.tutorial.bikestationsnetworks.databinding.ItemCityBinding;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<CityItem> cityItems;
    private ItemClickListener itemClickListener;

    public CityAdapter() {
        this.cityItems = new ArrayList<>();
    }

    public void prepareCityList(List<CityItem> cityItems) {
        this.cityItems.addAll(cityItems);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCityBinding binding = ItemCityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CityAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtCity.setText(cityItems.get(position).getCity());
        holder.binding.txtName.setText(cityItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return cityItems.size();
    }

    public CityItem getItem(int position) {
        return cityItems.get(position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {

        void onClick(CityItem cityItem);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemCityBinding binding;

        public ViewHolder(ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onClick(getItem(getAdapterPosition()));
        }
    }

}
