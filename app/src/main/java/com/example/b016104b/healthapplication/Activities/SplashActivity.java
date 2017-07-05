package com.example.b016104b.healthapplication.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.b016104b.healthapplication.LoginFragments.LoginFragment;
import com.example.b016104b.healthapplication.R;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by a025178g on 08/06/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(300);
                    Intent intent = new Intent(getApplicationContext(),InitAuth.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();

    }
}
