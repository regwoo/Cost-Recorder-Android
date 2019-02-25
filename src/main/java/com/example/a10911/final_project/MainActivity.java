package com.example.a10911.final_project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置api参数
        AVOSCloud.initialize(this,"tuNk5InJkV8hLtVJIgrX0AnK-gzGzoHsz", "XsGmbaggBhNBEfvApY13sfyV");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);


        ((ImageButton)findViewById(R.id.add_income)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), add_income.class);
                startActivityForResult(i, 1);
            }
        });
        ((ImageButton)findViewById(R.id.movement_list)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Movement_list.class);
                startActivityForResult(i, 1);
            }
        });
        ((ImageButton)findViewById(R.id.add_expense)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), add_expense.class);
                startActivityForResult(i, 1);
            }
        });
        ((ImageButton)findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Searchactivity.class);
                startActivityForResult(i, 1);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
