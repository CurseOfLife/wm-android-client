package com.example.wm_android_client.adapters;

import android.icu.util.MeasureUnit;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wm_android_client.R;
import com.example.wm_android_client.databinding.ItemMeasuringPointBinding;
import com.example.wm_android_client.listeners.MPListener;
import com.example.wm_android_client.models.MeasuringPoint;

import java.util.List;

public class MeasuringPointsAdapter extends RecyclerView.Adapter<MeasuringPointsAdapter.MeasuringPointsViewHolder> {

    private List<MeasuringPoint> measuringPoints;
    private LayoutInflater layoutInflater;

    private MPListener mpListener;

    public MeasuringPointsAdapter(List<MeasuringPoint> measuringPoints, MPListener mpListener) {
        this.measuringPoints = measuringPoints;
        this.mpListener = mpListener;
    }

    @NonNull
    @Override
    public MeasuringPointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemMeasuringPointBinding measuringPointBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_measuring_point, parent, false);

        return new MeasuringPointsViewHolder(measuringPointBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasuringPointsViewHolder holder, int position) {
        holder.bind(measuringPoints.get(position));
    }

    @Override
    public int getItemCount() {
        return measuringPoints.size();
    }


    public void setMeasuringPoints(List<MeasuringPoint> measuringPoints) {
        this.measuringPoints = measuringPoints;
    }

    public class MeasuringPointsViewHolder extends RecyclerView.ViewHolder {

        private ItemMeasuringPointBinding itemMeasuringPointBinding;

        public MeasuringPointsViewHolder(ItemMeasuringPointBinding itemMeasuringPointBinding) {
            super(itemMeasuringPointBinding.getRoot());
            this.itemMeasuringPointBinding = itemMeasuringPointBinding;
        }

        public void bind(final MeasuringPoint measuringPoint) {

            itemMeasuringPointBinding.setMeasuringPoint(measuringPoint);
            itemMeasuringPointBinding.executePendingBindings();

            itemMeasuringPointBinding.getRoot().setOnClickListener(v -> mpListener.onMeasuringPointClicked(measuringPoint));
        }
    }
}
