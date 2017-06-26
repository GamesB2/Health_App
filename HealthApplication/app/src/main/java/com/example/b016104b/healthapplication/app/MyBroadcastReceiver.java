package com.example.b016104b.healthapplication.app;

/**
 * Created by b016104b on 26/06/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, StepCounterTracker.class);
        context.startService(startServiceIntent);
    }
}