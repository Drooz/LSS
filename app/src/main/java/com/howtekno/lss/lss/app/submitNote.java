package com.howtekno.lss.lss.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.howtekno.lss.lss.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class submitNote extends AppCompatActivity {

    Uri path;
    String course , fileName;
    private static final String TAG = submitNote.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_note);
        Intent intent = getIntent();
        course = intent.getStringExtra("course");
        //

        Button browse = (Button) findViewById(R.id.browse);
        Button upload = (Button) findViewById(R.id.upload);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = path.getLastPathSegment();
                (new Upload(submitNote.this, path)).execute();
                doSubmit(course,fileName+".pdf");

            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                path = result.getData();
            }
        }
    }


    private void doSubmit(final String course, final String file) {

        // Tag used to cancel the request
        String tag_string_sub = "req_submitFile";


        StringRequest strSub = new StringRequest(Request.Method.POST,
                ConnectionConfig.URL_FILESUBMIT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Submit Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // File successfully stored in MySQL

                        Toast.makeText(getApplicationContext(), "File Submitted. will be reviewed before approval", Toast.LENGTH_LONG).show();

                        // Launch content activity
                        Intent intent = new Intent(
                                submitNote.this,
                                StudentsNotesContent.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in Submit. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Submit Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to submit url
                Map<String, String> params = new HashMap<String, String>();
                params.put("course", course);
                params.put("file", file);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strSub, tag_string_sub);
    }




    class Upload extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pd;
        private Context c;
        private Uri path;

        public Upload(Context c, Uri path) {
            this.c = c;
            this.path = path;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(c, "Uploading", "Please Wait");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url_path = "http://92.222.181.250/android_login_api/upload.php"+ "?file=" + fileName+ ".pdf" +"&course=" + course;
            HttpURLConnection conn = null;

            int maxBufferSize = 1024;
            try {
                URL url = new URL(url_path);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(1024);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data");

                OutputStream outputStream = conn.getOutputStream();
                InputStream inputStream = c.getContentResolver().openInputStream(path);

                int bytesAvailable = inputStream.available();
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer, 0, bufferSize)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
                inputStream.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i("result", line);
                }
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }

    }
}