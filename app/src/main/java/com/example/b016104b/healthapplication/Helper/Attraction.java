package com.example.b016104b.healthapplication.Helper;

/**
 * Created by a025178g on 15/06/2017.
 */

public class Attraction extends POI
{
    private int icon = 0;
    public Attraction(String sName,String sLat, String sLong)
    {
        super.setName(sName);
        super.setLat(sLat);
        super.setLong(sLong);
    }

    public void setIcon(String code)
    {
        icon = Integer.parseInt(code);
    }

    public int getIcon()
    {
        return icon;
    }
}
