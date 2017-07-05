package com.example.b016104b.healthapplication.MainFragments;

import android.Manifest;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.b016104b.healthapplication.Helper.RemoteSQLHandler;
import com.example.b016104b.healthapplication.LoginFragments.RegisterFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.example.b016104b.healthapplication.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;


/**
 * Created by b016104b on 16/06/2017.
 */

public class ActiveFragment extends Fragment{

        MapView mMapView;
        LocationManager locationManager;
        private GoogleMap mMap;
        final int MY_PERMISSIONS_REQUEST_LOCATION = 14;
        private LatLngBounds Demo = new LatLngBounds(new LatLng(49.495091,-10.722460), new LatLng(59.497134,1.843598));
        private ImageView searchb;
        public EditText locationSearch;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

            final View v = inflater.inflate(R.layout.adventures_fragment, container, false);

            mMapView = (MapView) v.findViewById(R.id.mapviewarea);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            this.getContext().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);





            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setLatLngBoundsForCameraTarget(Demo);
                    mMap.setMinZoomPreference(5);

                    //Location LatLng defined here
                    LatLng sydney = new LatLng(-34, 151);
                    LatLng stoke = new LatLng(53.0027, -2.1794);
                    LatLng center = new LatLng(0, 0);

                    //Permanent Markers added and camera zoom on initial startup
                    mMap.addMarker(new MarkerOptions().position(stoke).title("Marker in Sydney").snippet("Test Snippet inserting text").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

                    if (RemoteSQLHandler.totems != null)
                    {
                        MarkerOptions markerOptions = new MarkerOptions();

                        for (int i = 0; i < RemoteSQLHandler.totems.size(); i++)
                        {
                            LatLng temp = new LatLng(RemoteSQLHandler.totems.get(i).latf, RemoteSQLHandler.totems.get(i).lngf);

                            googleMap.addMarker(markerOptions.position(temp));

                        }
                    }

                    checkLocationPermission();

                    // For showing a move to my location button
                    try {
                        mMap.setMyLocationEnabled(true);

                    }
                    catch (SecurityException e) {
                        Log.d(e.toString(), e.getMessage());
                    }

                    // For zooming automatically to the location of the marker
                    //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });

            locationSearch = (EditText) v.findViewById(R.id.gSearch);

            locationSearch.setOnKeyListener(new View.OnKeyListener()
            {
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (event.getAction() == KeyEvent.ACTION_DOWN)
                    {
                        switch (keyCode)
                        {
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:
                                onMapSearch(v);
                                return true;
                            default:
                                break;
                        }
                    }
                    return false;
                }
            });

            searchb = (ImageView) v.findViewById(R.id.search_button);
            // Link to Register Screen
            searchb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    onMapSearch(view);
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
    private boolean checkLocation() {
        if (!isLocationEnabled())
            showOffAlert();
        return isLocationEnabled();
    }

    //Popup to ask to turn GPS on (Not permissions allow or deny box)
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //Checks to see if the user has granted location permissions to the app.
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            mMap.setMyLocationEnabled(true);
            return true;
        }
    }


    //Asks for permission to access GPS and handles the outcome of allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        LatLng stoke = new LatLng(53.0027, -2.1794);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException SE) {
                        Log.e(TAG, "Permissions error: Requires Location Permissions to be enabled manually");
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    }

                } else {
                    Log.e(TAG, "Location permissions denied");
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(stoke));
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    //Creates an alert window to prompt the user to turn their location settings on
    private void showOffAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings are turned off.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    //Search implementation, pins a marker on the location of the user
    public void onMapSearch(View v) {

        Editable input = locationSearch.getText();

        String location = input.toString();

        if (location != null) {
            //String location = locationSearch.getText().toString();
            List<Address> addressList = null;

            if (!location.equals("")) {
                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    addressList = geocoder.getFromLocationName(location, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = addressList.get(0);

                //Marker mSearch = new Marker();
                //mSearch.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.searchbutton));

                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                Marker mSearch = (mMap.addMarker(new MarkerOptions().position(latLng).title("Search query").icon(BitmapDescriptorFactory.fromResource(R.drawable.searchbutton))));
                mSearch.setDraggable(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        }

    }
}


