package com.example.a10911.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class content_list_adapter extends ArrayAdapter {
    List<content> contentlist = new ArrayList<content>();
    Context mContext;
    public content_list_adapter(Context context, ArrayList<content> list1) {
        super(context,R.layout.content_layout,list1);
        this.mContext = context;
        this.contentlist = list1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemview = convertView;
        if (itemview == null)
            itemview = LayoutInflater.from(mContext).inflate(R.layout.content_layout, parent, false);
        content currentcontent  = contentlist.get(position);
        //Toast.makeText(getContext(),"adapter "+currentcontent.catagory,Toast.LENGTH_SHORT).show();
        ((TextView)itemview.findViewById(R.id.catagory3)).setText("Catagory: "+currentcontent.catagory);
        ((TextView)itemview.findViewById(R.id.money3)).setText("Money: "+String.valueOf(currentcontent.money));
        ((TextView)itemview.findViewById(R.id.describtion3)).setText("Description: "+currentcontent.describe);
        ((TextView)itemview.findViewById(R.id.date3)).setText("Date: "+currentcontent.time);

        return itemview;
    }
}
