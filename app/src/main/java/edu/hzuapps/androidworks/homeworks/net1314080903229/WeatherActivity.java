package com.perkin.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.perkin.weather.R;
import com.perkin.weather.util.HttpCallbackListener;
import com.perkin.weather.util.HttpUtil;
import com.perkin.weather.util.Utility;

/**
 * Created by perkin on 2016/6/14.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {
    private LinearLayout weatherInfoLayout;
    /**
     * 用于显示城市名字
     */
    private TextView cityNameText;
    /**
     * 用于显示发布信息
     */
    private TextView publishText;
    /**
     * 用于显示天气描述信息
     */
    private TextView weatherDespText;

    /**
     * 用于显示气温1
     */
    private TextView temp1Text;

    /**
     * 用于显示气温2
     */
    private TextView temp2Text;

    /**
     * 用于显示当前日期
     */
    private TextView currentDateText;

    private TextView testText;

    /**
     * 切换城市按钮
     */
    private Button switchCity;
    /**
     * 更新天气按钮
     */
    private Button refreshWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        //初始化各控件
        weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView)findViewById(R.id.publish_text);
        weatherDespText = (TextView)findViewById(R.id.weather_desp);
        temp1Text = (TextView)findViewById(R.id.temp1);
        temp2Text = (TextView)findViewById(R.id.temp2);
        currentDateText = (TextView)findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather =(Button) findViewById(R.id.refresh_weather);
        String countyCode = getIntent().getStringExtra("county_code");  //获取上一个活动传递过来的对象，传递过来的是县的代号
        String countyName = getIntent().getStringExtra("county_name");
        cityNameText.setText(countyName);
        if(!TextUtils.isEmpty(countyCode)){
            //县级代号部位空的时候则查询天气
            publishText.setText("同步中......");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            cityNameText.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
            //没有县级代号是就直接显示本地天气
            showWeather();
        }
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
    }

    /**
     * 用于点击事件
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(this,ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中......");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = sharedPreferences.getString("weather_code","");
                if(!TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }
                break;
            default: break;
        }
    }
    /**
     * 查询县级代号所对应的天气代号
     */
    private void queryWeatherCode(String countyCode){
        String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        queryFromServer(address,"countyCode");
    }
    /**
     * 查询代号所对应的天气
     */
    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address, "weatherCode");
    }
    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address,final String type){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        //从服务器返回的数据中解析出天气代号
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    //处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      publishText.setText("同步失败");
                    }
                });
            }
        });
    }
    /**
     * 从SharedPreference 文件中读取存储的天气信息，并显示到界面上
     */
    private void showWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));
        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        publishText.setText("今天"+prefs.getString("publish_time","")+"发布");
        currentDateText.setText(prefs.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }
}
