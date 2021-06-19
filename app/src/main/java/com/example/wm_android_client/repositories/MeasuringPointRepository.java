package com.example.wm_android_client.repositories;


import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wm_android_client.dao.MeasuringPointDao;
import com.example.wm_android_client.database.WaterMeterDb;
import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;
import com.example.wm_android_client.models.relationships.MeasuringPointWithWaterMeters;
import com.example.wm_android_client.network.ApiClient;
import com.example.wm_android_client.network.ApiService;
import com.example.wm_android_client.responses.MeasuringPointResponse;
import com.example.wm_android_client.utilities.SessionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasuringPointRepository {

    private ApiService apiService;

    //database connection
    private WaterMeterDb db;
   // private LiveData<List<MeasuringPoint>> measuringPoints;
    private SessionManager sessionManager;


    public MeasuringPointRepository(Application application) {
        apiService = ApiClient.getRetrofit(application).create(ApiService.class);
        db = WaterMeterDb.getInstance(application);
        sessionManager = new SessionManager(application);
    }

    //Db
    public LiveData<List<MeasuringPoint>> getMeasuringPointsFromDb() {
        return db.measuringPointDao().getMeasuringPointsFromDb();
    }

    /*
    public Completable insertMeasuringPoint(MeasuringPoint measuringPoint) {
        return db.measuringPointDao().insertMeasuringPoint(measuringPoint);
    }*/
/*
    public Flowable<MeasuringPoint> search(String mpId){
        return  db.measuringPointDao().search(mpId);
    }*/

    //API call
    //change so it caches data also
    public LiveData<String> getMeasuringPointsFromWeb() {
        MeasuringPointResponse data = new MeasuringPointResponse();

        final MutableLiveData<String> code = new MutableLiveData<>();

        apiService.getMeasuringPoints(sessionManager.getUsername()).enqueue(new Callback<MeasuringPointResponse>() {
            @Override
            public void onResponse(Call<MeasuringPointResponse> call, Response<MeasuringPointResponse> response) {


                if (response.isSuccessful() && response.body() != null) {

                    //KAZE DA JE BODY OD GET MEASURING POINTS 0

                    for (MeasuringPoint mp : response.body().getMeasuringPoints()) {
                        insertMeasuringPointsWithWaterMeters(mp);
                    }
                }

                code.postValue(String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<MeasuringPointResponse> call, Throwable t) {
                code.postValue(t.getMessage());
            }
        });

        return code;
    }

    private void insertMeasuringPointsWithWaterMeters(MeasuringPoint mp) {

        new InsertAsync(db).doInBackground(mp);
    }

    private static class InsertAsync {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        private WaterMeterDb dbAsync;

        InsertAsync(WaterMeterDb db) {
            dbAsync = db;
        }


        protected void doInBackground(MeasuringPoint mp) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                     dbAsync.measuringPointDao().insertMp(mp);

                    int identifier = mp.getId();

                    for (WaterMeter waterMeter : mp.waterMeters) {
                        waterMeter.setMeasuringPointId(identifier);
                    }
                    dbAsync.waterMeterDao().insertWaterMeters(mp.waterMeters);

                }
            });
        }
    }
}


