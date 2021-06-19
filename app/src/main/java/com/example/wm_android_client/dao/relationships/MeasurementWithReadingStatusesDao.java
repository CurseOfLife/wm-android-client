package com.example.wm_android_client.dao.relationships;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.wm_android_client.models.unusedrel.MeasurementWithReadingStatuses;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MeasurementWithReadingStatusesDao {

    //change to RxJava
    @Transaction
    @Query("SELECT * FROM Measurements")
    Flowable<List<MeasurementWithReadingStatuses>> getMeasurementsWithStatuses();
}
