package com.howtekno.lss.lss.template;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.howtekno.lss.lss.R;
import com.howtekno.lss.lss.app.MainActivity;
import com.howtekno.lss.lss.helper.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class contentList extends AppCompatActivity {

    public ListView l ;
    public Intent intent ;
    private String TAG = MainActivity.class.getSimpleName();// Used in logs
    static String url = null;// Json Url
    public final ArrayList<String> contentList = new ArrayList<String>();
    ProgressDialog dialog;
    private Context CON = contentList.this;
    private int CONV = R.layout.activity_content_list;
    public String dep = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(CONV);
        intent = getIntent();
        url = "http://92.222.181.250/json/"+ dep +"?c=" + intent.getStringExtra("course");
        l = (ListView) findViewById(R.id.lv);
        new GetContent().execute();
    }

    private class GetContent extends AsyncTask<Void, Void, Void> {

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
            //Get intent extra fetch according to extra and push to list


            // on click listner to view pdf according to where it came from inmtent extra data


            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    //JSONObject kk = jsonObj.getJSONObject("");
                    // Getting JSON Array node
                    JSONArray jsonContent = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < jsonContent.length(); i++) {
                        //JSONObject c = contacts.getJSONObject(i);
                        String id = jsonContent.getJSONObject(i).getString("file");                        // adding contact to contact list
                        contentList.add(id);
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

            ArrayAdapter<String> ad = new ArrayAdapter<String>(CON,android.R.layout.simple_list_item_1,contentList);

            l.setAdapter(ad);
        }

    }


}
