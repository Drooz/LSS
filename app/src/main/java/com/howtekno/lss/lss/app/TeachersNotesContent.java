package com.howtekno.lss.lss.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.howtekno.lss.lss.R;

public class TeachersNotesContent extends com.howtekno.lss.lss.template.contentList {


    private Context CON = TeachersNotesContent.this;
    private int CONV = R.layout.activity_teachers_notes_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dep = "teachersContentList.php";
        super.onCreate(savedInstanceState);

        // Simple Listener
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CON, pdfViewer.class);
                // Send the selected Course and file
                i.putExtra("file",contentList.get(position).toString());
                i.putExtra("course", intent.getStringExtra("course"));
                i.putExtra("location","teacher");
                startActivity(i);
            }
        });

    }
}
