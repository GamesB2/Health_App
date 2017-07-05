package com.example.b016104b.healthapplication.Helper;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import android.widget.Toast;

import com.example.b016104b.healthapplication.MainFragments.ActiveFragment;
import com.example.b016104b.healthapplication.ObjectClasses.TotemPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoteSQLHandler extends Service
{
    private String TAG = RemoteSQLHandler.class.getSimpleName();
    ArrayList<HashMap<String, String>> dataList;
    public static ArrayList<TotemPost> totems = new ArrayList();
    public static TotemPost totemPost;
    private static DownloadServerData download;

    private static RemoteSQLHandler mInstance;

    public RemoteSQLHandler()
    {
        if(mInstance == null)
        {
            mInstance = this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

     @Override
    public int onStartCommand(Intent intent, int flags, int startId)
     {
    // Let it continue running until it is stopped.
    Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
         download = new DownloadServerData();
         download.execute();

    return START_STICKY;
    }

    public RemoteSQLHandler getInstance()
    {
        if (mInstance == null) {
            mInstance = this;
        }

        return mInstance;
    }

    public void downloadServerData()
    {
        if (download == null)
        {
            download = new DownloadServerData();
        }
        download.doInBackground();
    }

    private class DownloadServerData extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            HTTPHandler requestPosts = new HTTPHandler();
            HTTPHandler requestRoutes = new HTTPHandler();


            // Making a request to url and getting response

            String postsUrl = "https://concussive-shirt.000webhostapp.com/get_details_posts.php";
            String routesUrl = "https://concussive-shirt.000webhostapp.com/get_details_landmarks.php";


            String jsonStrA = requestPosts.makeServiceCall(postsUrl);
//            String jsonStrL = requestRoutes.makeServiceCall(routesUrl);
//

            Log.e(TAG, "Response from url: " + jsonStrA);
//            Log.e(TAG, "Response from url: " + jsonStrL);
//
            if (jsonStrA != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrA);

                    // Getting JSON Array node
                    JSONArray dataRp = jsonObj.getJSONArray("posts");

                    // looping through All Attractions
                    for (int i = 0; i < dataRp.length(); i++) {
                        JSONObject c = dataRp.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String website = c.getString("website");
                        String lat = c.getString("lat");
                        String lng = c.getString("lng");
                        String txt = c.getString("txt");
                        String video = c.getString("video");
                        String summary = c.getString("summary");
                        String qr = c.getString("qr");

                        totemPost = new TotemPost(id , name, website, lat,lng,txt,video,summary,qr);

                        totems.add(totemPost);

                        Log.e(TAG, "Posts Added OK!: ");

                    }
                } catch (final JSONException eA) {
                    Log.i(TAG, "Json parsing error: " + eA.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });*/

                }
            }

//            if (jsonStrL != null)
//            {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStrL);
//
//                    // Getting JSON Array node
//                    JSONArray dataRe = jsonObj.getJSONArray("events");
//
//                    // looping through All Attractions
//                    for (int i = 0; i < dataRe.length(); i++) {
//                        JSONObject c = dataRe.getJSONObject(i);
//                        String id = c.getString("autoNumber");
//                        String name = c.getString("name");
//                        String dateStart = c.getString("dateStart");
//                        String desc = c.getString("description");
//                        String cName = c.getString("contactName");
//                        String cNumber = c.getString("contactNumber");
//                        String website = c.getString("website");
//                        String price = c.getString("price");
//                        String time = c.getString("time");
//                        String dateEnd = c.getString("dateEnd");
//                        String cat = c.getString("cat");
//                        String add = c.getString("address");
//                        String pcode = c.getString("postcode");
//                        String lat = c.getString("lat");
//                        String lng = c.getString("lng");
//
//                        Log.e(TAG, "Events Added OK!: ");
//
//                    }
//                } catch (final JSONException eA) {
//                    Log.i(TAG, "Json parsing error: " + eA.getMessage());
//                    /*runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });*/
//
//                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

        }
    }


}