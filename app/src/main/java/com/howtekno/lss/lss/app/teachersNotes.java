package com.howtekno.lss.lss.app;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.howtekno.lss.lss.R;
import com.howtekno.lss.lss.helper.HttpHandler;
import com.howtekno.lss.lss.template.courseList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class teachersNotes extends com.howtekno.lss.lss.template.courseList {


    private Context CON = teachersNotes.this;
    private int CONV = R.layout.activity_teachers_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Simple Listener
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CON, TeachersNotesContent.class);
                // Send the selected Course. Its being handled in the main class "Content"
                intent.putExtra("course",courseList.get(position).toLowerCase().toString());
                startActivity(intent);
            }
        });
    }


}

