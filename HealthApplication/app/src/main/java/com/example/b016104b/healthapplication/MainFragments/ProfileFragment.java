package com.example.b016104b.healthapplication.MainFragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b016104b.healthapplication.Helper.SQLiteHandler;
import com.example.b016104b.healthapplication.Helper.SessionManager;
import com.example.b016104b.healthapplication.R;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by b016104b on 16/06/2017.
 */

public class ProfileFragment extends Fragment{

    private SessionManager session;
    private SQLiteHandler db;
    private HashMap<String, String> user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // move to activity
        if (user == null)
        {
            db = new SQLiteHandler(getActivity().getApplicationContext());

            // Session manager
            session = new SessionManager(getActivity().getApplicationContext());

            user = db.getUserDetails();
//            name = user.get("name");
//            email = user.get("email");
//            u_id = user.get("uid");
//            userDetails.setName(name);
//            userDetails.setEmail(email);
//            userDetails.setU_id(u_id);
        }

        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        TextView x = (TextView)v.findViewById(R.id.txtName);
        x.setText(user.get("name"));
        TextView y = (TextView)v.findViewById(R.id.userProfileName);
        y.setText(user.get("email"));

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
}

