package com.example.wm_android_client.models.unusedrel;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;
import com.example.wm_android_client.models.unusedrel.MeasurementWithReadingStatuses;

import java.util.List;

public class CompleteMeasuringPointData {
    @Embedded
    public MeasuringPoint measuringPoint;

    @Relation(
            entity = WaterMeter.class,
            parentColumn = "id",
            entityColumn = "waterMeterId"
    )
    public List<MeasurementWithReadingStatuses> measurementWithReadingStatuses;
}
