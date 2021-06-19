package com.example.wm_android_client.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.relationships.MeasuringPointWithWaterMeters;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface MeasuringPointDao {

    @Query("SELECT * FROM MeasuringPoints")
    LiveData<List<MeasuringPoint>> getMeasuringPointsFromDb();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMp(MeasuringPoint measuringPoint);


    /*
    @Query("SELECT * FROM measuringPoints WHERE id = :mpId")
    Flowable<MeasuringPoint> search(String mpId);*/

}
