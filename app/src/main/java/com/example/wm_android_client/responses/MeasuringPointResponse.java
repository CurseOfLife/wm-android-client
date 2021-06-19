package com.example.wm_android_client.responses;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.relationships.MeasuringPointWithWaterMeters;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasuringPointResponse {

    @SerializedName("measuringpoints")
    private List<MeasuringPoint> measuringPoints;

    public List<MeasuringPoint> getMeasuringPoints() {
        return measuringPoints;
    }
}
