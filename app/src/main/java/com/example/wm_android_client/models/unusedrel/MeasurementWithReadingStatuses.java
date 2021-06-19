package com.example.wm_android_client.models.unusedrel;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.wm_android_client.models.Measurements;
import com.example.wm_android_client.models.MeasuringStatus;

import java.util.List;

public class MeasurementWithReadingStatuses {

    @Embedded public Measurements measurement;
    @Relation(
            parentColumn = "id",
            entityColumn = "measurementId"
    )
    public List<MeasuringStatus> measuringStatuses;
}
