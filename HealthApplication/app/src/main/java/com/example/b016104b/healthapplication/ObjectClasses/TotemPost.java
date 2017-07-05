package com.example.b016104b.healthapplication.ObjectClasses;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by b016104b on 04/07/2017.
 */

public class TotemPost {

    String id;
    String name;
    String website;
    public float latf;
    public float lngf;
    String txt;
    String video;
    String summary;
    String qr;

    public TotemPost(String id,String name,String website,String lat,String lng,String txt,String video,String summary,String qr)
    {
        this.id = id;
        this.name = name;
        this.website = website;

        latf = Float.parseFloat(lat);
        lngf = Float.parseFloat(lng);

        this.txt =txt;

        this.qr = qr;
    }
}
