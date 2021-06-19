package com.example.wm_android_client.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wm_android_client.R;
import com.example.wm_android_client.databinding.ItemMeasuringPointBinding;
import com.example.wm_android_client.databinding.ItemWaterMeterBinding;
import com.example.wm_android_client.listeners.MPListener;
import com.example.wm_android_client.listeners.WMListener;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;

import java.util.List;

public class WaterMetersAdapter extends RecyclerView.Adapter<WaterMetersAdapter.WaterMeterViewHolder> {

    private List<WaterMeter> waterMeters;
    private LayoutInflater layoutInflater;

    private WMListener wmListener;

    public WaterMetersAdapter(List<WaterMeter> waterMeters, WMListener wmListener) {
        this.waterMeters = waterMeters;
        this.wmListener = wmListener;
    }

    @NonNull
    @Override
    public WaterMeterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemWaterMeterBinding waterMeterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_water_meter, parent, false);

        return new WaterMeterViewHolder(waterMeterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterMeterViewHolder holder, int position) {

        holder.bind(waterMeters.get(position));
    }

    @Override
    public int getItemCount() {
        return waterMeters.size();
    }

    public void setWaterMeters(List<WaterMeter> waterMeters) {
        this.waterMeters = waterMeters;
    }

    public class WaterMeterViewHolder extends RecyclerView.ViewHolder{

        private ItemWaterMeterBinding binding;

        public WaterMeterViewHolder(ItemWaterMeterBinding  b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(final WaterMeter waterMeter) {

            binding.setWatermeter(waterMeter);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> wmListener.onWaterMeterClicked(waterMeter));
        }

    }
}
