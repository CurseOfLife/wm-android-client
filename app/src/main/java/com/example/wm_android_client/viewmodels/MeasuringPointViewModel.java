package com.example.wm_android_client.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.repositories.MeasuringPointRepository;
import com.example.wm_android_client.responses.MeasuringPointResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class MeasuringPointViewModel extends AndroidViewModel {

    private MeasuringPointRepository mpRepository;

    public MeasuringPointViewModel(@NonNull Application application)
    {
        super(application);
        mpRepository = new MeasuringPointRepository(application);
    }

    //FROM DB
    public LiveData<List<MeasuringPoint>> getMeasuringPointsFromDb ()
    {
        return mpRepository.getMeasuringPointsFromDb();
    }
/*
    public Completable insertMeasuringPoint(MeasuringPoint measuringPoint){
        return mpRepository.insertMeasuringPoint(measuringPoint);
    }*/

    /*public Flowable<MeasuringPoint> search(String mpId){
        return  mpRepository.search(mpId);
    }*/

    //FROM API
    public LiveData<String> getMeasuringPointsFromWeb(){
        return mpRepository.getMeasuringPointsFromWeb();
    }
}
