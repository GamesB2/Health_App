package com.example.b016104b.healthapplication.MainFragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.maps.GoogleMap;

import com.example.b016104b.healthapplication.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by b016104b on 16/06/2017.
 */

public class ActiveFragment extends Fragment{

        MapView mMapView;
        private GoogleMap googleMap;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

            View v = inflater.inflate(R.layout.adventures_fragment, container, false);

            mMapView = (MapView) v.findViewById(R.id.mapviewarea);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    // For showing a move to my location button
                    try {
                        googleMap.setMyLocationEnabled(true);
                    }
                    catch (SecurityException e) {
                        Log.d(e.toString(), e.getMessage());
                    }

                    // For zooming automatically to the location of the marker

                    //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });

            return v;
        }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}

