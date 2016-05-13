package com.example.ranine99.ranine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ranine99 on 2016/4/28.
 */
public class CityAdapter extends ArrayAdapter<City> {
    private int resourceId;
    private ListView listView;
    private String[] times;
    private City city;
    private List<City> mList;
    private List<String> mList_time = new ArrayList<String>();

    public CityAdapter(Context context, int textViewResourceId, List<City> objects, ListView listView, List<String> mList_time) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.mList_time = mList_time;
        this.listView = listView;
        mList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        city = mList.get(position);
        View view = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else
            view = convertView;
        TextView cityName = (TextView) view.findViewById(R.id.city_name);
        cityName.setText(city.getName());

        TextView cityTime = (TextView) view.findViewById(R.id.id_city_time);
        cityTime.setText(city.getTime());

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public City getItem(int position) {
        return super.getItem(position);
    }

}


