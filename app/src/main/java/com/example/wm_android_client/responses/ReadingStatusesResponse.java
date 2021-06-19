package com.example.wm_android_client.responses;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.MeasuringStatus;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadingStatusesResponse {

    @SerializedName("readingstatuses")
    private List<MeasuringStatus> measuringStatuses;


}
