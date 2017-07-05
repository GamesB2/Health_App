package com.example.b016104b.healthapplication.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.support.design.widget.BottomNavigationView;

import com.example.b016104b.healthapplication.Helper.BottomNavigationViewHelper;
import com.example.b016104b.healthapplication.Helper.GeofenceStore;
import com.example.b016104b.healthapplication.Helper.My_Points;
import com.example.b016104b.healthapplication.Helper.SQLiteHandler;
import com.example.b016104b.healthapplication.MainFragments.ActiveFragment;
import com.example.b016104b.healthapplication.MainFragments.ProfileFragment;
import com.example.b016104b.healthapplication.MainFragments.QRFragment;
import com.example.b016104b.healthapplication.MainFragments.ShopFragment;
import com.example.b016104b.healthapplication.MainFragments.StatisticFragment;
import com.example.b016104b.healthapplication.R;
import com.example.b016104b.healthapplication.app.AppController;
import com.example.b016104b.healthapplication.app.IntentReciever;
import com.example.b016104b.healthapplication.app.MyBroadcastReceiver;
import com.example.b016104b.healthapplication.app.StepCounterTracker;
import com.google.android.gms.location.Geofence;


import java.lang.reflect.Field;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{

    private StepCounterTracker stepCount;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public SQLiteHandler db;
    private GeofenceStore geoStore;
    public HashMap<String, String> user;

    String name;
    String email;
    String u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create persistent classes needed for whole app
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepCount = new StepCounterTracker();
        mSensorManager.registerListener(stepCount, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        geoStore = new GeofenceStore();

        Intent intent = new Intent(getApplicationContext(), StepCounterTracker.class);
        this.startService(intent);
        Intent intent1 = new Intent(getApplicationContext(), IntentReciever.class);
        this.startService(intent1);
        Intent intent2 = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
        this.startService(intent2);


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        if (db == null) {
            db = AppController.getInstance().getDb();
        }
        if (user == null)
        {
            user = AppController.getInstance().getDb().getUserDetails();
        }

        HashMap<String, String> user = db.getUserDetails();
        name = user.get("name");
        email = user.get("email");
        u_id = user.get("uid");


        //Menu bar at the bottom
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_profile:
                        pushFragment(new ProfileFragment());
                        break;

                    case R.id.ic_statistics:
                        pushFragment(new StatisticFragment());
                        break;

                    case R.id.ic_map:
                        pushFragment(new ActiveFragment());
                        break;

                    case R.id.ic_shop:
                        pushFragment(new ShopFragment());
                        break;
                }
                return false;
            }
        });
    }


    public void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

//        Bundle extras = new Bundle();
//        extras.putSerializable("userMap", db.getUserDetails());
//
//        fragment.setArguments(extras);

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }

    public void popUpMenu(View view)
    {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                String itemID = item.getTitle().toString();
                switch(itemID)
                {
                    case "Settings":
                    {
                        //startSettings();
                        break;
                    }
                }
                return false;
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, popup.getMenu());
        popup.show();
    }

    public void launchQR(View view)
    {
        QRFragment newFrag = new QRFragment();
        pushFragment(newFrag);
    }

    public void testCreateGeofence()
    {
        long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * DateUtils.HOUR_IN_MILLIS;

        geoStore.createGeofence("The Shire",
                Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL,
                53.010054, -2.180195,
                100,
                GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                60000);
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
