package com.example.b016104b.healthapplication.app;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by b016104b on 20/06/2017.
 */

public class StepCounterTracker extends Service implements SensorEventListener {

    String count;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("com.example.b016104b", Context.MODE_PRIVATE);
        if(prefs.contains("count"))
        {
            count = prefs.getString("count", "defaultifnothingfound");
        }

        return super.onStartCommand(intent, flags, START_STICKY);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (String.valueOf(sensorEvent.values[0]) != null) {
            SharedPreferences prefs = AppController.getInstance().getSharedPreferences("com.example.b016104b", Context.MODE_PRIVATE);
            count = String.valueOf(sensorEvent.values[0]);
            prefs.edit().putString("count", count).apply();
            prefs.edit().commit();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void onDestroy() {

        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("com.example.b016104b", Context.MODE_PRIVATE);
        prefs.edit().putString("count", count).apply();
        prefs.edit().commit();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
