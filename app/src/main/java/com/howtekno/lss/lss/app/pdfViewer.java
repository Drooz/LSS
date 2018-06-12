package com.howtekno.lss.lss.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.howtekno.lss.lss.R;

public class pdfViewer extends AppCompatActivity {


    WebView webview;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        Intent intent = getIntent();// Get the Selected File and the course
        // We get the needed info to create the URL for the PDF file
        String course = intent.getStringExtra("course");// Course name
        String file = intent.getStringExtra("file");// File name
        String location = intent.getStringExtra("location");// STU or Teacher
        webview = (WebView)findViewById(R.id.webview);// Get the web view
        progressbar = (ProgressBar) findViewById(R.id.progressbar);// Progressbar
        webview.getSettings().setJavaScriptEnabled(true);
        final String filename ="http://92.222.181.250/content/"+ course + "/" + location + "/" + file;// Creating the URL
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filename);// Start the webview using google api and our custom URL the has
        // been created according to the activity Intent info

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // After we load the page we remove the progressbar
                progressbar.setVisibility(View.GONE);
            }
        });

    }
}
