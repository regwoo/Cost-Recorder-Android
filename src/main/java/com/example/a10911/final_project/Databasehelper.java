package com.example.a10911.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;

public class Databasehelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase";    // Database Name
    private static final String TABLE_NAME = "myTable";   // Table Name
    private static final int DATABASE_Version = 1;    // Database Version
    private static final String UID="_id";     // Column I (Primary Key)
    private static final String Describe = "describe";    //Column II
    private static final String Latitude="latitude";
    private static final String Longitude="longitude";
    private static final String Allmoney="allmoney";
    private static final String Time="Time";
    private static final String Catagory="catagory";
    //private static final String Expense="expence";
    private static final String Money="money";
    private static final String ObjectId="objectid";



    private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+
            " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Describe+" VARCHAR(255) ,"+Longitude+" VARCHAR(255) ,"+Time+" VARCHAR(255) ,"+Allmoney+" VARCHAR(255) ,"+ Latitude+" VARCHAR(225) ,"+Catagory+" VARCHAR(225) ,"+Money+" VARCHAR(225),"+ObjectId+" VARCHAR(225));";

    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    public Databasehelper databasehelper;
    private Context context;

    public Databasehelper(Context context)
    {
        super(context,TABLE_NAME,null,1);
        this.context=context;
    }
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
        }


    }
    public void insertData(String describe, String latitude,String longitude,String allmoney,String time,String money,String catagory)
    {
        SQLiteDatabase dbb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databasehelper.Describe, describe);
        contentValues.put(databasehelper.Latitude,latitude);
        contentValues.put(databasehelper.Longitude,longitude);
        contentValues.put(databasehelper.Allmoney,allmoney);
        contentValues.put(databasehelper.Time,time);
        contentValues.put(databasehelper.Money,money);
        contentValues.put(databasehelper.Catagory,catagory);

        final AVObject content = new AVObject("CONTENT");
        content.put("Describe",describe);
        content.put("Latitude",latitude);
        content.put("Longitude",longitude);
        content.put("Allmoney",allmoney);
        content.put("Time",time);
        content.put("Money",money);
        content.put("Catagory",catagory);
        content.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    Toast.makeText(context,"ok,id:"+content.getObjectId(),Toast.LENGTH_LONG).show();
                    content.put("ObjectId",content.getObjectId());
                }else{
                    Toast.makeText(context,"fail",Toast.LENGTH_LONG).show();

                }
            }
        });




        long id = dbb.insert(databasehelper.TABLE_NAME, null , contentValues);
    }

    public ArrayList<content> getall()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from myTable",null);
        cursor.moveToFirst();
        ArrayList<content> list=new ArrayList<>();
        while (cursor.isAfterLast()==false)
        {
            content rem=new content();
            String describe =cursor.getString(cursor.getColumnIndex(databasehelper.Describe));
            String money =cursor.getString(cursor.getColumnIndex(databasehelper.Money));
            String allmoney =cursor.getString(cursor.getColumnIndex(databasehelper.Allmoney));
            String time =cursor.getString(cursor.getColumnIndex(databasehelper.Time));
            String latitude =cursor.getString(cursor.getColumnIndex(databasehelper.Latitude));
            String longitude =cursor.getString(cursor.getColumnIndex(databasehelper.Longitude));
            String catagory =cursor.getString(cursor.getColumnIndex(databasehelper.Catagory));
            rem.describe=describe;
            rem.money=Double.parseDouble(money);
            rem.allmoney=Double.parseDouble(allmoney);
            rem.time=time;
            rem.latitude=Double.parseDouble(latitude);
            rem.longitude=Double.parseDouble(longitude);
            rem.catagory=catagory;
            list.add(rem);
            //rem=new content(describe,Double.parseDouble(money),Double.parseDouble(allmoney),time,Double.parseDouble(latitude),Double.parseDouble(longitude),catagory);
            cursor.moveToNext();
        }
        return list;
    }





}
