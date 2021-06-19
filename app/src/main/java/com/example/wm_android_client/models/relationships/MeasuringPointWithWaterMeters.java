package com.example.wm_android_client.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;

import java.util.List;

public class MeasuringPointWithWaterMeters {

    @Embedded
    public MeasuringPoint measuringPoint;

    @Relation(
            entity = WaterMeter.class,
            parentColumn = "id",
            entityColumn = "measuringPointId"
    )
    public List<WaterMeter> waterMeters;

    public MeasuringPointWithWaterMeters(MeasuringPoint measuringPoint, List<WaterMeter> waterMeters) {
        this.measuringPoint = measuringPoint;
        this.waterMeters = waterMeters;
    }


}
