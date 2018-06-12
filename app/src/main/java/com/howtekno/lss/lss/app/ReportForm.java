package com.howtekno.lss.lss.app;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.howtekno.lss.lss.R;

public class ReportForm extends AppCompatActivity {


    EditText title , message ;// Intilizing the var
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_form);

        // Bring all values from the xml
        title = (EditText) findViewById(R.id.title);
        message = (EditText) findViewById(R.id.message);
    }

    public void sendReport(View view) {
        String t = title.getText().toString();
        String m = message.getText().toString();
        if (t.isEmpty() || m.isEmpty()){
            Toast.makeText(this, "Please Fill Title OR/AND Message", Toast.LENGTH_SHORT).show();
        }else {

            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setData(Uri.parse("mailto:")); // To launch email
            mailIntent.setType("text/plain"); // Specify the type
            mailIntent.putExtra(Intent.EXTRA_EMAIL , new String[]{"report@lss.com"});
            mailIntent.putExtra(Intent.EXTRA_SUBJECT , t);
            mailIntent.putExtra(Intent.EXTRA_TEXT , m);

            try {
                startActivity(Intent.createChooser(mailIntent,"Please Select E-mail Client ... "));
                finish();
            }catch (android.content.ActivityNotFoundException e){
                Toast.makeText(ReportForm.this, "No Email Client Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
