package com.example.b016104b.healthapplication.app;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;

import com.example.b016104b.healthapplication.Helper.GeofenceStore;
import com.example.b016104b.healthapplication.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.Date;
import java.util.List;

/**
 * Created by b016104b on 28/06/2017.
 */

public class IntentReciever extends IntentService {


    public IntentReciever() {
        super("IntentReciever");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
        if (geoEvent.hasError()) {
            //Log.d(MapsActivityNew.TAG, "Error GeofenceReceiver.onHandleIntent");
        } else {
            //Log.d(MapsActivityNew.TAG, "GeofenceReceiver : Transition -> "+ geoEvent.getGeofenceTransition());

            int transitionType = geoEvent.getGeofenceTransition();

            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER
                    || transitionType == Geofence.GEOFENCE_TRANSITION_DWELL
                    || transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
                List<Geofence> triggerList = geoEvent.getTriggeringGeofences();

                for (Geofence geofence : triggerList) {
                    String name = geofence.getRequestId();

                    String transitionName = "";
                    switch (transitionType) {
                        case Geofence.GEOFENCE_TRANSITION_DWELL:
                            transitionName = "dwell";
                            break;

                        case Geofence.GEOFENCE_TRANSITION_ENTER:
                            transitionName = "enter";
                            break;

                        case Geofence.GEOFENCE_TRANSITION_EXIT:
                            transitionName = "exit";
                            break;
                    }
                    String date = DateFormat.format("yyyy-MM-dd hh:mm:ss",
                            new Date()).toString();


                    String notificationText = "" + name + " : " + transitionName + " : " + date;

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                            getApplicationContext())
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                            .setContentTitle(getApplicationContext().getString(R.string.app_name))
                            .setContentText(notificationText).setAutoCancel(true);

                    Notification notification;

                    notification = notificationBuilder.build();
                    notification.defaults = Notification.DEFAULT_LIGHTS;
                    notification.defaults = Notification.DEFAULT_SOUND;
                    notification.defaults = Notification.DEFAULT_VIBRATE;

                }
            }
        }
    }

}
