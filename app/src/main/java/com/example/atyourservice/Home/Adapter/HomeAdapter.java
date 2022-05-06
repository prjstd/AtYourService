package com.example.atyourservice.Home.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.atyourservice.R;
import com.example.atyourservice.ViewHolder;


import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {

    ArrayList<String> menu;
    Activity activity;
    ArrayList<Integer> pics;

    public HomeAdapter(Activity activity, ArrayList<String> menu, ArrayList<Integer> pics) {
        this.activity = activity;
        this.menu = menu;
        this.pics = pics; }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.row_home, parent, false);

        ViewHolder.txt1 = convertView.findViewById(R.id.textView);
        ViewHolder.im = convertView.findViewById(R.id.im);

        ViewHolder.txt1.setText(menu.get(position));
        ViewHolder.im.setImageResource(pics.get(position));

        return convertView; }
}