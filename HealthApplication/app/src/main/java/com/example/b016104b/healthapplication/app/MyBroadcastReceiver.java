package com.example.b016104b.healthapplication.app;

/**
 * Created by b016104b on 26/06/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class MyBroadcastReceiver extends BroadcastReceiver
{

    Intent broadcastIntent = new Intent();

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
            case "ACTION_RECEIVE_GEOFENCE":
                GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
                if (geoEvent.hasError())
                {
                    Toast.makeText(context, geoEvent.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Successfuly recieved GeoFence", Toast.LENGTH_SHORT).show();

                    int transitionType = geoEvent.getGeofenceTransition();
                    if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER)
                    {

                    }
                }
        }
    }
}