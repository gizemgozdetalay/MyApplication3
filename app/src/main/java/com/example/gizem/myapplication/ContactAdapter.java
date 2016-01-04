package com.example.gizem.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gizem on 03.01.2016.
 */
public class ContactAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Contact> mContactList;
    public ContactAdapter(Activity activity, List<Contact> contacts){

        mInflater=(LayoutInflater)activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
        mContactList=contacts;

    }
    public List<Contact> getData()
    {
        return mContactList;
    }
    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView=mInflater.inflate(R.layout.row_layout, null);
        TextView textView =
                (TextView) rowView.findViewById(R.id.textView);
        TextView textView1 =
                (TextView) rowView.findViewById(R.id.textView1);
        ImageView imageView =
                (ImageView) rowView.findViewById(R.id.imageView);
        Contact contact=mContactList.get(position);
        textView.setText(contact.getName());
        textView1.setText(contact.getNumber());
        //imageView.setImageResource(R.drawable.can);


        return rowView;
    }
}
