package com.howtekno.lss.lss.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.howtekno.lss.lss.R;

import java.util.ArrayList;

public class studentsNotes extends teachersNotes  {


    private Context CON = studentsNotes.this;
    private int CONV = R.layout.activity_students_notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Simple Listener
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CON, StudentsNotesContent.class);
                intent.putExtra("course",courseList.get(position).toLowerCase().toString());
                startActivity(intent);
            }
        });


    }
}
