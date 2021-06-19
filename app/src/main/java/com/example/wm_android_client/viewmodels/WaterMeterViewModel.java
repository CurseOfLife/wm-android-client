package com.example.wm_android_client.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;
import com.example.wm_android_client.repositories.WaterMeterRepository;

import java.util.List;

public class WaterMeterViewModel extends AndroidViewModel {

    //get List<WM> (mmID)

    private WaterMeterRepository waterMeterRepository;

    public WaterMeterViewModel(@NonNull Application application) {
        super(application);
        this.waterMeterRepository = new WaterMeterRepository(application);
    }


    //DB
    //getting all the water meters for the specific measuring point
    public LiveData<List<WaterMeter>> getWaterMetersForMeasuringPointID (int mpID)
    {
        return waterMeterRepository.getWaterMetersForMeasuringPointID(mpID);
    }

}
