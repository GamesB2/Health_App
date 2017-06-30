package com.example.b016104b.healthapplication.Helper;

import android.util.Log;

import com.google.android.gms.location.Geofence;

import java.util.HashMap;

/**
 * Created by b016104b on 27/06/2017.
 */

public class GeofenceStore
{

    protected static HashMap<String, Geofence> geofences = new HashMap<String, Geofence>();

    public boolean createGeofence(String geofenceId, int transition, double latitude, double longitude,
                                  float radius, long expiration, int delay)
    {

        try {

            Geofence g = new Geofence.Builder().setRequestId(geofenceId)
                    .setTransitionTypes(transition)
                    .setCircularRegion(latitude, longitude, radius)
                    .setExpirationDuration(expiration)
                    .setLoiteringDelay(delay).build();

            geofences.put(geofenceId, g);
        }
        catch (Exception e)
        {
            Log.d("TAG", e.getMessage());
            return false;
        }

        return true;

    }

    public HashMap<String, Geofence> returnHashMap()
    {
        return geofences;
    }
}
