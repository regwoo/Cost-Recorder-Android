package com.example.a10911.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Movement_list extends AppCompatActivity {
    ArrayList<content> contentlist=new ArrayList<>();
    content_list_adapter adapter1;
    Databasehelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement_list);
        helper = new Databasehelper(this);
        ini();
      /*  for(int i=0;i<contentlist.size();i++) {
            Toast.makeText(getApplicationContext(), String.valueOf(contentlist.get(i).money), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), contentlist.get(i).time, Toast.LENGTH_SHORT).show();
        }*/
        adapter1=new content_list_adapter(getApplicationContext(),contentlist);
        ListView listview=findViewById(R.id.content_list);
        listview.setAdapter(adapter1);
    }

    public void ini() {
        contentlist.clear();
        if (helper.getall() != null) {
            contentlist=helper.getall();
        }
    }


    private void delete(int position){
        content mcontent =contentlist.get(position);
        int contentId=mcontent.getId();
    }





}
