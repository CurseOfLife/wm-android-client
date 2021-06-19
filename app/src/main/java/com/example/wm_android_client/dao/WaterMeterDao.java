package com.example.wm_android_client.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;

import java.util.List;

@Dao
public interface WaterMeterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWaterMeters(List<WaterMeter> waterMeters);

    @Query("SELECT * FROM WaterMeters WHERE id = :mpID")
    LiveData<List<WaterMeter>> getWaterMetersForMeasuringPointID(int mpID);
}
