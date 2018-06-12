package com.howtekno.lss.lss.template;

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
import com.howtekno.lss.lss.app.MainActivity;
import com.howtekno.lss.lss.helper.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class courseList extends AppCompatActivity {


    public ListView l ;// The list var
    ProgressDialog dialog; // Dialog var
    final static String url = "http://92.222.181.250/json/courseList.php";// Json Url
    public final ArrayList<String> courseList = new ArrayList<String>();
    private String TAG = MainActivity.class.getSimpleName();// Used in logs
    // For the sake of re using the code these should be override when extending
    private Context CON = courseList.this;
    private int CONV = R.layout.activity_course_list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(CONV);
        // Get the list from the xml
        l = (ListView) findViewById(R.id.lv);
        new GetContacts().execute();// Fetching courses from json url using async task



    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            dialog = new ProgressDialog(CON);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    //JSONObject kk = jsonObj.getJSONObject("");
                    // Getting JSON Array node
                    JSONArray jsonCourses = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < jsonCourses.length(); i++) {
                        //JSONObject c = contacts.getJSONObject(i);
                        String id = jsonCourses.getJSONObject(i).getString("name");                        // adding contact to contact list
                        courseList.add(id);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (dialog.isShowing())
                dialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            ArrayAdapter<String> ad = new ArrayAdapter<String>(CON,android.R.layout.simple_list_item_1,courseList);

            l.setAdapter(ad);
        }

    }
}

