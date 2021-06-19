package com.example.wm_android_client.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.wm_android_client.database.WaterMeterDb;
import com.example.wm_android_client.models.WaterMeter;

import java.util.List;

public class WaterMeterRepository {

    private WaterMeterDb db;

    public WaterMeterRepository(Application application) {
        db = WaterMeterDb.getInstance(application);
    }

    public LiveData<List<WaterMeter>> getWaterMetersForMeasuringPointID(int mpID) {
        return  db.waterMeterDao().getWaterMetersForMeasuringPointID(mpID);
    }
}
