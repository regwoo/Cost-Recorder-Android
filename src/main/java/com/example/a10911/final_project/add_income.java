package com.example.a10911.final_project;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class add_income extends AppCompatActivity {
    PopupWindow popupWindow;
    PopupWindow popupWindow2;
    CalendarView cal1;
    content add1=new content();
    ArrayList<content> contentlist=new ArrayList<>();
    Databasehelper helper;
    private final int REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        FloatingActionButton scan = (FloatingActionButton) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Scan.class);
                startActivityForResult(intent, REQUEST);
            }

        });


        Button button = (Button)findViewById(R.id.location);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MapsActivity.class);
                startActivityForResult(intent,2);
            }
        });





        helper = new Databasehelper(this);
        ini();
       // Toast.makeText(getApplicationContext(),String.valueOf(contentlist.size()),Toast.LENGTH_SHORT).show();
        ((TextView)findViewById(R.id.catagory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.catagory_pop_layout,null);
                ListView list=customView.findViewById(R.id.list1);
                final ArrayList<String>catagory= new ArrayList<>();
                catagory.add("Loan");
                catagory.add("Sales");
                catagory.add("Salary");
                catagory.add("Others");
                catagory_list_adapter adapter1=new catagory_list_adapter(getApplicationContext(),catagory);
                popupWindow2 = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,true);

                //display the popup window
                popupWindow2.showAtLocation(findViewById(R.id.add_income_activity), Gravity.CENTER, 0, -40);
                list.setAdapter(adapter1);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String cata=catagory.get(position);
                        add1.catagory=cata;
                        popupWindow2.dismiss();
                        ((TextView)findViewById(R.id.catagory)).setText(cata);
                    }
                });
            }
        });
        ((TextView)findViewById(R.id.data)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customView = layoutInflater.inflate(R.layout.calendar_pop_layout,null);
                cal1=customView.findViewById(R.id.calendarView);


                //instantiate popup window
                popupWindow = new PopupWindow(customView, 1200, 2500,true);

                //display the popup window
                popupWindow.showAtLocation(findViewById(R.id.add_income_activity), Gravity.CENTER, 0, 0);
                cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {
                        ((Button)customView.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                              String data_r=String.valueOf(month+1)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
                              add1.time=String.valueOf(year*10000+(month+1)*100+dayOfMonth);
                                ((TextView)findViewById(R.id.data)).setText(data_r);
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
                ((Button)customView.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       popupWindow.dismiss();
                    }
                });

            }
        });      // click textview to get time

        ((Button)findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(((EditText)findViewById(R.id.income)).getText().toString().trim().length()>0
                      &&((EditText)findViewById(R.id.describtion)).getText().toString().trim().length()>0
                      &&((TextView)findViewById(R.id.data)).getText().toString()!="Click to choose the date")
              {
                  add1.money=Double.parseDouble(((EditText)findViewById(R.id.income)).getText().toString());
                  if(contentlist.size()==0)
                  {
                     add1.allmoney=add1.money;
                  }
                  else {
                      add1.allmoney = contentlist.get(contentlist.size() - 1).allmoney + add1.money;
                  }
                  add1.describe=((EditText)findViewById(R.id.describtion)).getText().toString();
                  contentlist.add(add1);
                  helper.insertData(add1.describe,String.valueOf(add1.latitude),String.valueOf(add1.longitude),String.valueOf(add1.allmoney),add1.time,String.valueOf(add1.money),add1.catagory);
                  Toast.makeText(getApplicationContext(),"successful stored",Toast.LENGTH_SHORT).show();// store the new content
                  finish();
              }
              else
              {
                  Toast.makeText(getApplicationContext(),"money,date and describtion can't be null ",Toast.LENGTH_SHORT).show();
              }
            }
        });// click save to store the income
    }
    public void ini() {
       contentlist.clear();
        if (helper.getall() != null) {
            contentlist=helper.getall();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST) {
            // Make sure the request was successful


                    if (resultCode == RESULT_OK) {
                        String str = data.getStringExtra("result");
                        EditText editText = (EditText)findViewById(R.id.income);
                        if(str != null){

                            editText.setText(str);

                            // The user picked a contact.
                            // The Intent's data Uri identifies which contact was selected.

                            // Do something with the contact here (bigger example below)
                    }
            }
        }

        if(requestCode ==2 ){
            if(resultCode ==RESULT_OK){
                add1.latitude=Double.parseDouble(data.getStringExtra("latitude"));
                add1.longitude=Double.parseDouble(data.getStringExtra("longitude"));
            }
        }
    }


}
