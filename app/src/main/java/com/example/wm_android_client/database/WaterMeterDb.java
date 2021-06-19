package com.example.wm_android_client.database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.wm_android_client.dao.MeasuringPointDao;
import com.example.wm_android_client.dao.WaterMeterDao;
import com.example.wm_android_client.models.Measurements;
import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.MeasuringStatus;
import com.example.wm_android_client.models.WaterMeter;
import com.example.wm_android_client.utilities.converters.DateConverter;
import com.example.wm_android_client.utilities.converters.WaterMeterListConverter;

import java.util.ArrayList;
import java.util.List;

//Measurements.class,
//DateConverter.class,
@Database(entities = {MeasuringPoint.class, WaterMeter.class}, version = 1, exportSchema = true)
@TypeConverters({WaterMeterListConverter.class})
public abstract class WaterMeterDb extends RoomDatabase {

    //check local cashing kotlin yt

    //SINGLETON
    private static WaterMeterDb instance;

    public abstract MeasuringPointDao measuringPointDao();
    public abstract WaterMeterDao waterMeterDao();
    public abstract MeasuringStatusDao measuringStatusDao();

    public static synchronized WaterMeterDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, WaterMeterDb.class, "master_wm_db.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
    }

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private MeasuringPointDao measuringPointDao;

        private PopulateDatabaseAsyncTask(WaterMeterDb database) {
            measuringPointDao = database.measuringPointDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
          //  measuringPointDao.insertMp(new MeasuringPoint(100, "Address Name One", "Owner One", "Note one", "description"));
         //  measuringPointDao.insertMp(new MeasuringPoint(102, "Address Name One", "Owner One", "Note one", "description"));
           // measuringPointDao.insertMp(new MeasuringPoint(103, "Address Name One", "Owner One", "Note one", "description"));

            return null;
        }
    }
}
