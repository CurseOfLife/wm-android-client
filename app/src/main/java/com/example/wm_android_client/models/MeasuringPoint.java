package com.example.wm_android_client.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.wm_android_client.utilities.converters.DateConverter;
import com.example.wm_android_client.utilities.converters.WaterMeterListConverter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "MeasuringPoints")
public class MeasuringPoint  implements Serializable {

    //Serializable
    //marker interface your classes must implement if they are to be serialized and deserialized.

    // @ColumnInfo(name = "")
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    public int id;

    @SerializedName("street")
    public String street;

    @SerializedName("number")
    public String number;

    @SerializedName("place")
    public String place;

    @SerializedName("owner")
    public String owner;

    @SerializedName("description")
    public String description;

    @SerializedName("watermeters")
    @TypeConverters(WaterMeterListConverter.class)
    public List<WaterMeter> waterMeters;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WaterMeter> getWaterMeters() {
        return waterMeters;
    }

    public void setWaterMeters(List<WaterMeter> waterMeters) {
        this.waterMeters = waterMeters;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public MeasuringPoint(int id, String street, String number, String place, String description) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.place = place;
        this.description = description;
    }
}
