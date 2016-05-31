package edu.hzuapps.androidworks.homeworks.com1314080901103.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidworks.homeworks.com1314080901103.R;
import edu.hzuapps.androidworks.homeworks.com1314080901103.db.WeatherDB;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.County;

public class DeleteAreaActivity extends Activity {

    private ImageView imageView;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<County> countyList = new ArrayList<>();
    private List<String> countyNameList = new ArrayList<>();
    private WeatherDB weatherDB;
    private String selectedCountyName;
    private int toWeatherActivityPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_com1314080901103_show_area);

        toWeatherActivityPosition = getIntent().getIntExtra("to_delete_area_position", -1);

        weatherDB = WeatherDB.getInstance(this);
        countyList = weatherDB.loadAddCounties();
        if (countyList.size() > 0) {
            countyNameList.clear();
            for (County county : countyList) {
                countyNameList.add(county.getCountyName());
            }
        }

        imageView = (ImageView) findViewById(R.id.back);
        titleText = (TextView) findViewById(R.id.title_text);
        titleText.setText("删除城市");
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countyNameList);
        listView.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCountyName = countyNameList.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder
                        (DeleteAreaActivity.this);
                dialog.setTitle("友情提示");
                dialog.setMessage("确认删除" + selectedCountyName + "么？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确认", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weatherDB.deleteFromAddCountiesByCountyName(selectedCountyName);
                        weatherDB.deleteFromAddCountiesWeatherByCountyName(selectedCountyName);
                        Toast.makeText(DeleteAreaActivity.this, selectedCountyName + " 删除成功", Toast.LENGTH_SHORT).show();
                        countyList = weatherDB.loadAddCounties();
                        weatherDB.deleteAddCounties();
                        weatherDB.createAddCounties();
                        if (countyList.size() > 0) {
                            countyNameList.clear();
                            for (County county : countyList) {
                                countyNameList.add(county.getCountyName());
                                weatherDB.saveAddCounty(county);
                            }
                        } else {
                            countyNameList.clear();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });
        ActivityCollector.addActivity(this);
    }

    /**
     * 捕获Back按键，回到WeatherActivity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeleteAreaActivity.this, WeatherActivity.class);
        intent.putExtra("to_weather_activity_position", toWeatherActivityPosition);
        startActivity(intent);
        finish();
        Log.d("DeleteAreaActivity", "xyz onBackPressed has finished");
    }
}