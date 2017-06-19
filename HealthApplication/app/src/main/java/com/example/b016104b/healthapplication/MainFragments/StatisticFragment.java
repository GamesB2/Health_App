package com.example.b016104b.healthapplication.MainFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.b016104b.healthapplication.R;

/**
 * Created by b016104b on 16/06/2017.
 */

public class StatisticFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.statistic_fragment, container, false);
    }
}
