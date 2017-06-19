package com.example.b016104b.healthapplication.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.b016104b.healthapplication.LoginFragments.LoginFragment;
import com.example.b016104b.healthapplication.R;


public class InitAuth extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_auth);

        LoginFragment loginFragment = new LoginFragment();

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, loginFragment);
                ft.commit();
            }
        }
    }
}