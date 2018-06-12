package com.howtekno.lss.lss.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.howtekno.lss.lss.R;
import com.howtekno.lss.lss.helper.SQLiteHandler;
import com.howtekno.lss.lss.helper.SessionManager;

import java.util.HashMap;

public class Forum extends AppCompatActivity {

    WebView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        wb=(WebView)findViewById(R.id.wb_v);
        WebSettings webSettings=wb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb.setWebViewClient(new WebViewClient());
        wb.loadUrl("http://92.222.181.250/form");

    }


}
