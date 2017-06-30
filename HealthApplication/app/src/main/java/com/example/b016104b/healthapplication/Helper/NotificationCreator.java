package com.example.b016104b.healthapplication.Helper;

/**
 * Created by b016104b on 27/06/2017.
 */

public final class NotificationCreator {

    private static NotificationCreator instance;

    public static synchronized NotificationCreator getInstance()
    {
        if (instance == null)
        {
            instance = new NotificationCreator();
        }
        return instance;
    }


}
