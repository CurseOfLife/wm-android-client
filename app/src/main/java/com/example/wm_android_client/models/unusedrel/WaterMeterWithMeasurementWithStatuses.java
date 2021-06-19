package com.example.wm_android_client.models.unusedrel;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.wm_android_client.models.Measurements;
import com.example.wm_android_client.models.WaterMeter;

import java.util.List;

public class WaterMeterWithMeasurementWithStatuses {

    @Embedded public WaterMeter waterMeter;

    @Relation(
            entity = Measurements.class,
            parentColumn = "id",
            entityColumn = "waterMeterId"
    )
    public List<MeasurementWithReadingStatuses> measurementWithReadingStatuses;
}
