package com.example.b016104b.healthapplication.Helper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by b016104b on 27/06/2017.
 */

public class ManipulateMap {

    public static ArrayList<Object> poiArrayList = new ArrayList<>();
    public static ArrayList<Marker> markerArrayList = new ArrayList<>();
    public static GoogleMap mMap;
    private static boolean[] filter;
    private static int maxRange = 50000; //Should be the same as MAX constant in FilterActivity
    private static LatLng userLatLng;

    final static int HEALTHYFOOD= 0;
    final static int WALKNGROUTES= 1;
    final static int CYCLEROUTES=2;

    public ManipulateMap(GoogleMap map, ArrayList<Object> arrayList)
    {
        poiArrayList = arrayList;
        mMap = map;
        filter = new boolean[25];
    }

}
