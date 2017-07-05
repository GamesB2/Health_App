package com.example.b016104b.healthapplication.MainFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b016104b.healthapplication.Helper.SQLiteHandler;
import com.example.b016104b.healthapplication.Helper.MyRecyclerViewAdapter;
import com.example.b016104b.healthapplication.Helper.My_Points;
import com.example.b016104b.healthapplication.Helper.SessionManager;
import com.example.b016104b.healthapplication.Helper.Ticket;
import com.example.b016104b.healthapplication.R;
import com.example.b016104b.healthapplication.app.AppController;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.b016104b.healthapplication.app.AppConfig.CONNECTION_TIMEOUT;
import static com.example.b016104b.healthapplication.app.AppConfig.READ_TIMEOUT;

/**
 * Created by b016104b on 16/06/2017.
 */

public class ShopFragment extends Fragment{

    private SessionManager session;
    private CardView btnCard1;
    //public ArrayList<Ticket> t = DatabaseRetrieval.ticketsAl;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private Person p;
    private String points="";
    private ArrayList<Ticket> pointsList = new ArrayList<>();
    private HashMap<String, String> user;
    private View v;
    private ImageView cards;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.shop_fragment, container, false);

        if (user == null)
        {
            user = AppController.getInstance().getDb().getUserDetails();
        }
        cards = (ImageView) v.findViewById(R.id.card1);

        cards.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Tickets_View.class);
                startActivity(intent);
                }
            });

        new ShopFragment.AsyncLogin().execute(user.get("email"));

        return v;
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

    private class AsyncLogin extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(DatabaseQ.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://concussive-shirt.000webhostapp.com/get_details_user_points.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0]);
                //.appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String response) {
            //this method will be running on UI thread
            // pdLoading.dismiss();

            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getInt("success") == 1 ? false : true;
                System.out.println(response);

                // Check for error node in json
                if (!error) {
                    JSONArray contacts = jObj.getJSONArray("points");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        points = c.getString("points");
                    }


                    //Now Sort

                    System.out.println(points);
                    TextView pointstext = (TextView) v.findViewById(R.id.points2);
                    pointstext.setText(points);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setMessage("You Have: " + points + " in your account! :-)\nUse these points?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"You clicked yes",Toast.LENGTH_LONG).show();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();



                    // Launch main activity

                } else {
                    // Error in login. Get the error message
                    String errorMsg = jObj.getString("error_msg");
                    //Toast.makeText(getApplicationContext(),
                    //errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }

}

