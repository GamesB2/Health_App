package com.example.b016104b.healthapplication.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.b016104b.healthapplication.Helper.SQLiteHandler;
import com.example.b016104b.healthapplication.MainFragments.ActiveFragment;
import com.example.b016104b.healthapplication.MainFragments.ProfileFragment;
import com.example.b016104b.healthapplication.MainFragments.StatisticFragment;
import com.example.b016104b.healthapplication.R;
import com.example.b016104b.healthapplication.app.AppController;
import com.example.b016104b.healthapplication.app.StepCounterTracker;

public class MainActivity extends AppCompatActivity
{

    private StepCounterTracker stepCount;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        stepCount = new StepCounterTracker();

        mSensorManager.registerListener(stepCount, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Intent intent = new Intent(getApplicationContext(),StepCounterTracker.class);

        this.startService(intent);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        if (db == null)
        {
            db = AppController.getInstance().getDb();
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if (bottomNavigationView != null)
        {
            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item)
                {
                    selectFragment(item);
                    return false;
                }
            });
        }

        pushFragment(new ProfileFragment());
    }


    protected void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.action_profile:
                // Action to perform when Home Menu item is selected.
                pushFragment(new ProfileFragment());
                break;
            case R.id.action_adventures:
                // Action to perform when Bag Menu item is selected.
                pushFragment(new ActiveFragment());
                break;
            case R.id.action_statistics:
                // Action to perform when Account Menu item is selected.
                pushFragment(new StatisticFragment());
                break;
        }
    }


    protected void pushFragment(Fragment fragment) {
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
}
