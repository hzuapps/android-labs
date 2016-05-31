package com.example.ranine99.ranine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ranine99 on 2016/4/27.
 */
public class ListViewActivity extends Activity {
    private TextView tvBeijingTime;
    private List<City> cityList = new ArrayList<City>();
    public static ListView listView;
    public static int i;
    private CityAdapter adapter;
    private String[] times;
    private  static int flag_1 = 0;
    private  static int flag_2 = 0;
    private List<String> mList_time=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_city);
        listView = (ListView) findViewById(R.id.list_view_city);
        initTime();
        initCitys();

        //mList_time没有用到
        for (int i=0;i<3;i++){
            mList_time.add(times[i]);
        }

        adapter = new CityAdapter(ListViewActivity.this, R.layout.city_item, cityList, listView,mList_time);
        listView.setAdapter(adapter);

        Intent intent = this.getIntent();
        if (intent != null) {
            i = getIntent().getIntExtra("position", -1);
            if (i == 0) {
                City london = new City("伦敦 London", times[1]);
                cityList.add(london);
                flag_1++;
            } else if (i == 1) {
                City tokyo = new City("东京 Tokyo",times[2]);
                cityList.add(tokyo);
                flag_2++;
            }
//            adapter.notifyDataSetChanged();
        }
        Toast.makeText(this,"flag_1:"+flag_1+",flag_2:"+flag_2,Toast.LENGTH_SHORT).show();
        if (flag_1 >= 2){
            if(!(cityList.get(1).getName().equals("伦敦 London")/*||cityList.get(2).getName().equals("伦敦 London")*/)){
                addLondon();
            }
        }
        if (flag_2 >= 2){
            if(!(cityList.get(1).getName().equals("东京 Tokyo")/*||cityList.get(2).getName().equals("东京 Tokyo")*/)){
                addTokyo();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initCitys() {
        City beijing = new City("北京 Beijing", times[0]);
        cityList.add(beijing);
    }

    private void addLondon(){
        City london = new City("伦敦 London", times[1]);
        cityList.add(london);
    }
    private void addTokyo(){
        City tokyo = new City("东京 Tokyo", times[2]);
        cityList.add(tokyo);
    }

    private void initTime() {
        times = new String[3];
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String ee = dff.format(new Date());
        times = new String[3];
        times[0] = ee;
        dff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        ee = dff.format(new Date());
        times[1] = ee;
        dff = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        ee = dff.format(new Date());
        times[2] = ee;
    }
    public void change_2(View view) {
        Intent intent = new Intent(this, ListViewActivity_Choice.class);
        startActivity(intent);
        finish();
    }
}
