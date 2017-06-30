package com.example.b016104b.healthapplication.MainFragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.b016104b.healthapplication.Activities.MainActivity;
import com.example.b016104b.healthapplication.Helper.SQLiteHandler;
import com.example.b016104b.healthapplication.Helper.SessionManager;
import com.example.b016104b.healthapplication.R;
import com.example.b016104b.healthapplication.app.AppController;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by b016104b on 16/06/2017.
 */

public class ProfileFragment extends Fragment{

    private SessionManager session;
    private HashMap<String, String> user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (user == null)
        {
            user = AppController.getInstance().getDb().getUserDetails();
        }

        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        TextView x = (TextView)v.findViewById(R.id.txtName);
        x.setText(user.get("name"));
        TextView y = (TextView)v.findViewById(R.id.userProfileName);
        y.setText(user.get("email"));

        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("com.example.b016104b", Context.MODE_PRIVATE);
        if(prefs.contains("count"))
        {
            TextView z = (TextView)v.findViewById(R.id.profilefluff);
            String count = prefs.getString("count", "deafaultifnothing");
            z.append(count);
        }
        else
        {
            TextView z = (TextView)v.findViewById(R.id.profilefluff);
            z.append("zero");
        }

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public void DisplayOnUI(float currentcount)
    {
        String count = Float.toString(currentcount);
    }


}

