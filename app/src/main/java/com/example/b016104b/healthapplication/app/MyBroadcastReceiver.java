package com.example.b016104b.healthapplication.app;

/**
 * Created by b016104b on 26/06/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class MyBroadcastReceiver extends BroadcastReceiver
{

    Intent broadcastIntent = new Intent();
    protected Context context;

    protected NotificationManager notificationManager;
    protected Notification notification;



    @Override
    public void onReceive(Context context, Intent intent)
    {

        String action = intent.getAction();

        switch (action)
        {
            case "BOOT_COMPLETED":
                Toast.makeText(context, "Recieved boot complete broadcast", Toast.LENGTH_SHORT).show();
                Intent startServiceIntent = new Intent(context, StepCounterTracker.class);
                context.startService(startServiceIntent);
                break;
        }
    }
}