package com.example.b016104b.healthapplication.LoginFragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.b016104b.healthapplication.R;

/**
 * Created by w028006g on 08/06/2017.
 */
public class ResetFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.activity_reset, container, false);

        WebView myWebView = (WebView) v.findViewById(R.id.webviewreset);

        myWebView.setWebViewClient(new WebViewClient());

        myWebView.loadUrl("https://concussive-shirt.000webhostapp.com/reset/index.php");

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);



        return v;
    }


}
