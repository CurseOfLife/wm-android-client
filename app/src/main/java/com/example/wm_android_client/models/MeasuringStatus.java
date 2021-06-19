package com.example.wm_android_client.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity (tableName = "MeasuringStatuses",
        foreignKeys = {
                @ForeignKey(entity = Measurements.class, parentColumns = "id", childColumns = "measurementId" )})
public class MeasuringStatus {

    @PrimaryKey
    @SerializedName("id")
    public int measuringStatusId;

    @SerializedName("media")
    public int value;
    //FK
    public int measurementId;
}
