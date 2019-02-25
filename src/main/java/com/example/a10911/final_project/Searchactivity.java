package com.example.a10911.final_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Searchactivity extends AppCompatActivity {
    double time1;
    String timeshow1;
    double time2;
    String timeshow2;
    double totalmoney = 0 ;
    CalendarView cal1;
    PopupWindow popupWindow;
    Databasehelper helper = new Databasehelper(this);
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final content_list_adapter listadapter ;
        final ArrayList<content> thiscontent = new ArrayList<content>();
        listView = (ListView)findViewById(R.id.relsit) ;
        listadapter = new content_list_adapter(this,thiscontent);


        listView.setAdapter(listadapter);


        final TextView begin = (TextView)findViewById(R.id.beginday);
        final TextView end   = (TextView)findViewById(R.id.untild);
        final TextView result=(TextView)findViewById(R.id.result);
        final Button confirm = (Button)findViewById(R.id.cbtn);

        /*Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Searchactivity.this  ,MapsActivity.class);
                startActivity(intent);
            }
        });*/


//choose begin date
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//instantiate popup window
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.popsearchdate,null);
                cal1=customView.findViewById(R.id.sc);
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,true);
//display the popup window
                popupWindow.showAtLocation(findViewById(R.id.search), Gravity.CENTER, 0, 0);
                cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {
                        time1 =dayOfMonth+(month+1)*100+year*10000;
                        timeshow1 =String.valueOf(month+1)+ "/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
                        popupWindow.dismiss();
                        begin.setText(timeshow1);
                    }
                });
            }
        });
//choose end date
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//instantiate popup window
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.popsearchdate,null);
                cal1=customView.findViewById(R.id.sc);
                popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,true);
//display the popup window
                popupWindow.showAtLocation(findViewById(R.id.search), Gravity.CENTER, 0, 0);

                cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {
                        time2 =dayOfMonth+(month+1)*100+year*10000;
                        timeshow2 =String.valueOf(month+1)+ "/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
                        popupWindow.dismiss();
                        end.setText(timeshow2);
                    }
                });

            }
        });
        final int[] a = new int[1];
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalmoney = 0 ;
                thiscontent.clear();
                for(int i = 0 ;i<helper.getall().size();i++){
                    if(Double.parseDouble(helper.getall().get(i).time) >= time1 && Double.parseDouble(helper.getall().get(i).time) <=time2){
                        totalmoney = totalmoney + helper.getall().get(i).money;
                        content newnoe = new content();
                        newnoe = helper.getall().get(i);
                        thiscontent.add(newnoe);
                    }
                    a[0]=i+1;
                }
                listadapter.notifyDataSetChanged();
               // Toast.makeText(getApplicationContext(),String.valueOf(a[0]),Toast.LENGTH_LONG).show();
                result.setText(String.valueOf(totalmoney));
            }
        });





    }
}