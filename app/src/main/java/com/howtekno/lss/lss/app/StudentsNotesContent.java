package com.howtekno.lss.lss.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.howtekno.lss.lss.R;

public class StudentsNotesContent extends com.howtekno.lss.lss.template.contentList {


    private Context CON = StudentsNotesContent.this;
    private int CONV = R.layout.activity_students_notes_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dep = "studentsContentList.php";
        super.onCreate(savedInstanceState);


        // Simple Listener
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CON, pdfViewer.class);
                // Send the selected Course and file
                i.putExtra("file",contentList.get(position).toString());
                i.putExtra("course", intent.getStringExtra("course"));
                i.putExtra("location","stu");
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // User chose the "add" item
                Intent i = new Intent(CON,submitNote.class);
                i.putExtra("course",intent.getStringExtra("course"));
                startActivity(i);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
