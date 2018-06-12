package com.howtekno.lss.lss.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.howtekno.lss.lss.R;

public class Tools extends AppCompatActivity {

    ListView l ;
    String tools[] = {"GPA Calculator","Metric Converter"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);


        l = (ListView) findViewById(R.id.lv);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tools);
        l.setAdapter(ad);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                if (item.equals("GPA Calculator")){
                    Intent i = new Intent(Tools.this , GPA_Calculator.class);
                    startActivity(i);
                }else if (item.equals("Metric Converter")){
                    Intent i = new Intent(Tools.this , Metric_Calculator.class);
                    startActivity(i);
                }
            }
        });
    }
}
