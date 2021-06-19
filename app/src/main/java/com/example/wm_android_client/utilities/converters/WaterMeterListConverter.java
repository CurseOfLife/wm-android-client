package com.example.wm_android_client.utilities.converters;

import androidx.room.TypeConverter;

import com.example.wm_android_client.models.WaterMeter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class WaterMeterListConverter {

    Gson gson = new Gson();

    @TypeConverter
    public List<WaterMeter> storedStringToWaterMeters(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<WaterMeter>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String waterMetersToStoredStrings(List<WaterMeter> waterMeters) {
        return gson.toJson(waterMeters);
    }

}
