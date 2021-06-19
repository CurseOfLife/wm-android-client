package com.example.wm_android_client.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;


@Entity (tableName = "WaterMeters",
        foreignKeys = {
                @ForeignKey(entity = MeasuringPoint.class,
                        parentColumns = "id",
                        childColumns = "measuringPointId",
                        onDelete = CASCADE  //vidi koji treba
                )},
        indices = {@Index("measuringPointId")})
public class WaterMeter implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    public int id;

    @SerializedName("code")
    public int code;

    //add average value

    @SerializedName("measuringPointId")
    public int measuringPointId;

    //@SerializedName("WaterMeters")
    //public List<Measurements> measurements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public int getMeasuringPointId() {
        return measuringPointId;
    }

    public void setMeasuringPointId(int measuringPointId) {
        this.measuringPointId = measuringPointId;
    }
}
