package com.perkin.weather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.perkin.weather.R;
import com.perkin.weather.model.City;
import com.perkin.weather.model.County;
import com.perkin.weather.model.Province;
import com.perkin.weather.service.WeatherDB;
import com.perkin.weather.util.HttpCallbackListener;
import com.perkin.weather.util.HttpUtil;
import com.perkin.weather.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by perkin on 2016/6/13.
 */
public class ChooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE =0;
    public static final int LEVEL_CITY =1;
    public static final int LEVEL_COUNTY =2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private WeatherDB weatherDB;
    private List<String> dataList = new ArrayList<String>();
    /**
     * 省列表
     */
    private List<Province> provinceList;
    /**
     *市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private  List<County> countyList;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 当前选中的级别
     */
    private int currentLevel;

    /**
     *是否从WeatherActivity中跳转过来
     */
    private boolean isFromWeatherActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);
        //创建用于共享数据的SharedPreference进行传递到下一个Activity
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //已经选择了城市而且不是从WeatherActivity跳转过来，才会直接跳转到WeatherActivity
        if(prefs.getBoolean("city_selected",false)&&!isFromWeatherActivity){
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView)findViewById(R.id.list_view);
        titleText = (TextView)findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        weatherDB =WeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince = provinceList.get(index);
                    queryCities();
                }
                else if(currentLevel==LEVEL_CITY){
                    selectedCity = cityList.get(index);
                    queryCounties();
                }
                //接下来是选定了某个县以后的步骤
                else if(currentLevel==LEVEL_COUNTY){
                    String countyCode = countyList.get(index).getCountyCode();
                    String countyName = countyList.get(index).getCountyName();
                    Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                    intent.putExtra("county_code",countyCode);      //利用putExtra()方法进行添加要向下一个Activity传递的对象
                    intent.putExtra("county_name",countyName);
                    startActivity(intent);
                    finish();
                }

            }
        });
        queryProvinces();   //加载省级数据
    }
    /**
     * 查询所有的省份，优先从数据库查询，如果美欧查询到再去服务器上查询，。
     */
    private void queryProvinces(){
        provinceList = weatherDB.loadProvince();
        if(provinceList.size()>0){
            dataList.clear();
            for(Province province:provinceList){
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }
        else{
            queryFromServer(null,"province");
        }
    }
    /**
     * 查询选中的省内所有的市，优先从数据库中查询，如果没有查询到再去服务器上查询。
     */
    private void queryCities(){
        cityList = weatherDB.loadCities(selectedProvince.getId());
        if(cityList.size()>0){
            dataList.clear();
            for(City city:cityList){
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel=LEVEL_CITY;
        }
        else{
            queryFromServer(selectedProvince.getProvinceCode(),"city");
        }
    }
    /**
     * 查询选中市内所有的县，优先从数据库中查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties(){
        countyList = weatherDB.loadCounties(selectedCity.getId());
        if(countyList.size()>0){
            dataList.clear();
            for(County county:countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        }else{
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }
    /**
     * 根据传入的代码和类型从服务器上查询省市县数据
     */
    private void queryFromServer(final String code,final String type){
        String address;
        if(!TextUtils.isEmpty(code)){
            address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
        }else{
            address ="http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(weatherDB, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(weatherDB, response, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountiesResponse(weatherDB, response, selectedCity.getId());
                }
                if (result) {
                    //通过runOnUiThread()方法回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }


            @Override
            public void onError(Exception e) {
                //通过runOnUiThread()方法回到线程处理逻辑
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("正在加载……");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    /**
     * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表，省列表，还是直接退出。
     */
    @Override
    public void onBackPressed() {
        if(currentLevel==LEVEL_COUNTY){
            queryCities();
        }else if(currentLevel==LEVEL_CITY){
            queryProvinces();
        }else{
            finish();
        }
    }
}
