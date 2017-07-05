package com.example.b016104b.healthapplication.MainFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b016104b.healthapplication.R;
import com.example.b016104b.healthapplication.LoginFragments.LoginFragment;
import com.example.b016104b.healthapplication.app.AppController;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets_View extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_view);

        Transition ts = new Explode();  //Slide(); //Explode();
        ts.setDuration(300);
        getWindow().setEnterTransition(ts);
        getWindow().setExitTransition(ts);



        }

}
