package com.howtekno.lss.lss.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.howtekno.lss.lss.R;
import com.howtekno.lss.lss.helper.SQLiteHandler;
import com.howtekno.lss.lss.helper.SessionManager;
import com.howtekno.lss.lss.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends Activity {


    private static final String TAG = signupActivity.class.getSimpleName();
    // Initialization Line/s

    private EditText username , password ;
    private TextView status;
    private int presscount = 0 ;

    //
    private SessionManager session;
    private SQLiteHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         username = (EditText)findViewById(R.id.username);
         password = (EditText)findViewById(R.id.password);
         status = (TextView)findViewById(R.id.status);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());


        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(loginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    // Validate the input here
    public void validate(View view) {
        // Validate the input here
        if (username.getText().toString().isEmpty()){ // check empty username
            status.setText("Username should not be empty");
            return;
        }
        if (password.getText().toString().isEmpty()){ // check empty password
            status.setText("Password should not be empty");
            return;
        }
        // Call login function here
        doLogin(username.getText().toString(),password.getText().toString());


    }

    // Login function
    public void doLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                ConnectionConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(loginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errrrr", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);



    }

    // Foarward user to sign up activity
    public void signup(View view) {
        Intent i = new Intent(loginActivity.this, signupActivity.class);
        startActivity(i);
        finish();
    }

    // Back button function
    public void onBackPressed() {
        if (presscount == 0 ){
            Toast t = Toast.makeText(this,"Press Again To Exit",Toast.LENGTH_SHORT);
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
            // terminate
            finish();
            System.exit(0);
        }
    }
}
