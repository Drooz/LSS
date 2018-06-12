package com.howtekno.lss.lss.app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.howtekno.lss.lss.R;
import com.howtekno.lss.lss.helper.SQLiteHandler;
import com.howtekno.lss.lss.helper.SessionManager;

public class MainActivity extends AppCompatActivity {


    private SQLiteHandler db;
    private SessionManager session;


    private int presscount = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
    }


    @Override
    public void onBackPressed() {
        if (presscount == 0 ){
        Toast t = Toast.makeText(this,"Press Again To Logout",Toast.LENGTH_SHORT);
        t.show();
        presscount++;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    presscount = 0;
                }
            }, 5000);
        }else {
            // Clear the session
            session.setLogin(false);
            db.deleteUsers();
            // Launching the login activity
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void trigger1(View view) {// For launching Activity for offical lecture notes
        Intent i = new Intent(MainActivity.this , teachersNotes.class);
        startActivity(i);
    }

    public void trigger2(View view) {// For launching Activity for Studnet submitted lectures
        Intent i = new Intent(MainActivity.this , studentsNotes.class);
        startActivity(i);
    }

    public void trigger3(View view) {// For launching Activity for forum activity
        Intent i = new Intent(MainActivity.this , Forum.class);
        startActivity(i);
    }

    public void trigger4(View view) {// For launching Tools Activity
        Intent i = new Intent(MainActivity.this , Tools.class);
        startActivity(i);
    }

    public void trigger5(View view) {// For lanuching report and bugges
        Intent i = new Intent(MainActivity.this , ReportForm.class);
        startActivity(i);
    }



    private void logoutUser() {
        // Set Session Flag To False
        session.setLogin(false);
        // Delete User Data From The Session
        db.deleteUsers();
        // Launching the login activity
        Intent i = new Intent(MainActivity.this, loginActivity.class);
        startActivity(i);
        finish();
    }
}
