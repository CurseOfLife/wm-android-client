package com.example.wm_android_client.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

//onDelete()
@Entity (tableName = "Measurements",
        foreignKeys = {
        @ForeignKey(entity = WaterMeter.class, parentColumns = "id", childColumns = "waterMeterId" )},
        indices = {@Index("waterMeterId")})
public class Measurements {

    @PrimaryKey
    public int id;

    public String comment;
    public int value;
    public Date date; //type conv za date??

    //FK
    public int waterMeterId;

}
