package com.example.b016104b.healthapplication.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.b016104b.healthapplication.Helper.SQLiteHandler;
import com.example.b016104b.healthapplication.Helper.SessionManager;
import com.example.b016104b.healthapplication.MainFragments.AdventuresFragment;
import com.example.b016104b.healthapplication.MainFragments.ProfileFragment;
import com.example.b016104b.healthapplication.MainFragments.StatisticFragment;
import com.example.b016104b.healthapplication.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        SQLiteDatabase mydatabase = openOrCreateDatabase("android_api",MODE_PRIVATE,null);

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
                pushFragment(new AdventuresFragment());
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

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }


}
