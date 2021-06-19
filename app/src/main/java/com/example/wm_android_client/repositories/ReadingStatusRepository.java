package com.example.wm_android_client.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.wm_android_client.database.WaterMeterDb;
import com.example.wm_android_client.models.WaterMeter;
import com.example.wm_android_client.network.ApiClient;
import com.example.wm_android_client.network.ApiService;

import java.util.List;

public class ReadingStatusRepository {

    private WaterMeterDb db;
    private ApiService apiService;


    public ReadingStatusRepository(Application application) {
        db = WaterMeterDb.getInstance(application);
        apiService = ApiClient.getRetrofit(application).create(ApiService.class);
    }

    public LiveData<List<WaterMeter>> getWaterMetersForMeasuringPointID(int mpID) {
        return  db.waterMeterDao().getWaterMetersForMeasuringPointID(mpID);
    }
}
