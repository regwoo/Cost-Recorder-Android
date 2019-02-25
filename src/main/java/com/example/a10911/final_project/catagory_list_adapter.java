package com.example.a10911.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class catagory_list_adapter extends ArrayAdapter {
    List<String> contentlist = new ArrayList<String>();
    Context mContext;
    public catagory_list_adapter(Context context, ArrayList<String> list1) {
        super(context,R.layout.catagory_layout,list1);
        this.mContext = context;
        this.contentlist = list1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemview = convertView;
        if (itemview == null)
            itemview = LayoutInflater.from(mContext).inflate(R.layout.catagory_layout, parent, false);
       String currentposition  = contentlist.get(position);
        ((TextView)itemview.findViewById(R.id.catagory)).setText(currentposition);


        return itemview;
    }
}
