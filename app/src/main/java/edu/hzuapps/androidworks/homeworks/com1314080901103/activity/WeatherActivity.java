package edu.hzuapps.androidworks.homeworks.com1314080901103.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidworks.homeworks.com1314080901103.R;
import edu.hzuapps.androidworks.homeworks.com1314080901103.adapter.ViewPageAdapter;
import edu.hzuapps.androidworks.homeworks.com1314080901103.db.WeatherDB;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.County;
import edu.hzuapps.androidworks.homeworks.com1314080901103.model.CountyWeather;
import edu.hzuapps.androidworks.homeworks.com1314080901103.util.HttpCallbackListener;
import edu.hzuapps.androidworks.homeworks.com1314080901103.util.HttpUtil;
import edu.hzuapps.androidworks.homeworks.com1314080901103.util.Utility;

public class WeatherActivity extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 所用到的类的声明
     */
    private RelativeLayout relativeLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    public WeatherDB weatherDB;
    private ViewPager listPager;
    private List<View> listView;
    private ViewPageAdapter viewPageAdapter;
    /**
     * 用于显示城市名
     */
    private TextView cityNameText;
    /**
     * 用于显示发布时间
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
    /**
     * 切换城市按钮
     */
    private Button switchCity;
    /**
     * 更新天气按钮
     */
    private Button refreshWeather;
    /**
     * 保存已查询过的天气代号
     */
    private String weatherCode;
    /**
     * 保存当前定位到的城市
     */
    public String cityName;
    /**
     * 保存从上一活动传递过来的城市代码
     */
    public String countyCode;
    /**
     * 保存从上一活动传递过来的城市名
     */
    public String countyName;
    /**
     * 保存当前显示的页数
     */
    public int currentPosition;
    /**
     * 判断是否来自ChooseAreaActivity
     */
    private Boolean isFromChooseAreaActivity;
    /**
     * 判断是否来自DeleteAreaActivity
     */
    private int formDeleteAreaActivityPosition;
    /**
     * 声明AMapLocationClient类对象
     */
    public AMapLocationClient mLocationClient = null;
    /**
     * 声明定位回调监听器
     */
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    cityName = aMapLocation.getCity();  //获得当前城市信息
                    SharedPreferences prefs = PreferenceManager.
                            getDefaultSharedPreferences(WeatherActivity.this);
                    String defaultLocation = prefs.getString("city_name_juhe", "") + "市";
                    if (defaultLocation.equals(cityName)) {
                        showWeatherFromJuHe();
                    } else if (!TextUtils.isEmpty(cityName)) {
                        queryWeatherInfoFromJuHe(cityName);
                    }
                    if (mLocationClient != null) {
                        mLocationClient.stopLocation();//停止定位
                        mLocationClient.onDestroy();//销毁定位客户端
                    }
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    /**
     * 声明mLocationOption对象
     */
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_com1314080901103_pager_adapter);
        initView();
        countyCode = getIntent().getStringExtra("county_code");
        countyName = getIntent().getStringExtra("county_name");
        isFromChooseAreaActivity = getIntent().getBooleanExtra("is_form_choose_area", false);
        formDeleteAreaActivityPosition = getIntent().getIntExtra("to_weather_activity_position", -1);
        weatherDB = WeatherDB.getInstance(this);
        judgeDBToAdd();
        initViewPage();
        initSwipeRefreshLayout();
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        //设置下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(this);
        ActivityCollector.addActivity(this);
    }

    /**
     * 初始化所有控件
     */
    private void initView() {
        relativeLayout = (RelativeLayout) this.findViewById(R.id.weather_main);
        cityNameText = (TextView) this.findViewById(R.id.city_name);
        switchCity = (Button) this.findViewById(R.id.switch_city);
        refreshWeather = (Button) this.findViewById(R.id.refresh_weather);
    }

    /**
     * 初始化页面fragment
     */
    private void initViewPage() {
        listPager = (ViewPager) findViewById(R.id.list_pager);
        listView = new ArrayList<>();
        //这里只设置了4.因为在实现应用中，我们在页面加载的时候，你会根据数据的多少，而知道这个页面的数量
        //一般情况下，我们会根据list<>或是string[]这样的数组的数量来判断要有多少页
        int count = weatherDB.selectAddCountySize();
        for (int i = 0; i <= count; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_com1314080901103_fragment_main, null);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.weather_main);
            publishText = (TextView) view.findViewById(R.id.publish_text);
            weatherDespText = (TextView) view.findViewById(R.id.weather_desp);
            temp1Text = (TextView) view.findViewById(R.id.temp1);
            temp2Text = (TextView) view.findViewById(R.id.temp2);
            currentDateText = (TextView) view.findViewById(R.id.current_date);
            if (i == 0) {
                SharedPreferences prefs = PreferenceManager.
                        getDefaultSharedPreferences(this);
                if (TextUtils.isEmpty(prefs.getString("city_name_juhe", ""))) {
                    initMap();
                }
                temp1Text.setText(prefs.getString("temp1_juhe", ""));
                temp2Text.setText(prefs.getString("temp2_juhe", ""));
                weatherDespText.setText(prefs.getString("weather_juhe", ""));
                selectPictureByWeather(prefs.getString("weather_juhe", ""));
                publishText.setText("今天" + prefs.getString("update_time_juhe", "") + "更新");
                currentDateText.setText(prefs.getString("current_date_juhe", ""));
            } else {
                County county = weatherDB.loadAddCounties(i);
                Log.d("WeatherActivity", "xyz county.getCountyName() is " + county.getCountyName());
                CountyWeather countyWeather = weatherDB.loadAddCountiesWeatherByCountyName(county.getCountyName());
                temp1Text.setText(countyWeather.getTemp1());
                temp2Text.setText(countyWeather.getTemp2());
                weatherDespText.setText(countyWeather.getWeatherDesp());
                if (!TextUtils.isEmpty(countyWeather.getWeatherDesp())) {
                    selectPictureByWeather(countyWeather.getWeatherDesp());
                }
                publishText.setText("今天" + countyWeather.getUpdateTime() + "更新");
                currentDateText.setText(countyWeather.getCurrentDate());
            }
            listView.add(view);
        }
        viewPageAdapter = new ViewPageAdapter(listView);
        listPager.setAdapter(viewPageAdapter);
        if (isFromChooseAreaActivity) {
            listPager.setCurrentItem(listView.size());
            County county = weatherDB.loadAddCounties(listView.size());
            cityNameText.setText(county.getCountyName());
        } else if (formDeleteAreaActivityPosition != -1) {
            listPager.setCurrentItem(formDeleteAreaActivityPosition);
            if (formDeleteAreaActivityPosition == 0) {
                SharedPreferences prefs = PreferenceManager.
                        getDefaultSharedPreferences(WeatherActivity.this);
                cityNameText.setText(prefs.getString("city_name_juhe", ""));
            } else {
                County county = weatherDB.loadAddCounties(formDeleteAreaActivityPosition);
                cityNameText.setText(county.getCountyName());
            }
        } else {
            listPager.setCurrentItem(0);
            SharedPreferences prefs = PreferenceManager.
                    getDefaultSharedPreferences(WeatherActivity.this);
            cityNameText.setText(prefs.getString("city_name_juhe", ""));
        }
        listPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    SharedPreferences prefs = PreferenceManager.
                            getDefaultSharedPreferences(WeatherActivity.this);
                    cityNameText.setText(prefs.getString("city_name_juhe", ""));
                } else {
                    County county = weatherDB.loadAddCounties(position);
                    cityNameText.setText(county.getCountyName());
                }
                currentPosition = position;
                Log.d("WeatherActivity", "xyz 当前是第" + position + "页");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 设置下拉刷新的相关属性
     */
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        // 设定下拉圆圈的背景
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
    }

    /**
     * 判断数据库是否已经存在此城市,若不存在则将其存入数据库
     */
    private void judgeDBToAdd() {
        List<County> countyList = weatherDB.loadAddCounties();
        if (!TextUtils.isEmpty(countyCode) && !TextUtils.isEmpty(countyName) && !countyCode.equals("location")) {
            if (countyList.size() >= 0) {
                Boolean isExist = false;
                for (County county1 : countyList) {
                    if (countyCode.equals(county1.getCountyCode())) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    County county = new County();
                    county.setCountyCode(countyCode);
                    county.setCountyName(countyName);
                    weatherDB.saveAddCounty(county);
                    queryWeatherCode(countyCode);
                }
            }
        }
    }

    /**
     * 高德地图定位初始化
     */
    public void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 对标题栏的图标进行点击事件注册,并对其进行相应处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                Intent intentDelete = new Intent(this, DeleteAreaActivity.class);
                intentDelete.putExtra("to_delete_area_position", currentPosition);
                startActivity(intentDelete);
                finish();
                break;
            default:
                break;
        }
    }

    /*
    * 根据当前位置获得页面对应的view
    * */
    public void getView() {
        View view = listView.get(currentPosition);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.weather_main);
        publishText = (TextView) view.findViewById(R.id.publish_text);
        weatherDespText = (TextView) view.findViewById(R.id.weather_desp);
        temp1Text = (TextView) view.findViewById(R.id.temp1);
        temp2Text = (TextView) view.findViewById(R.id.temp2);
        currentDateText = (TextView) view.findViewById(R.id.current_date);
    }

    /*
     * 监听器SwipeRefreshLayout.OnRefreshListener中的方法，当下拉刷新后触发
     */
    public void onRefresh() {
        if (currentPosition > 0) {
            County county = weatherDB.loadAddCounties(currentPosition);
            queryWeatherCode(county.getCountyCode());
            viewPageAdapter.notifyDataSetChanged();
            getView();
            showWeather(county.getCountyName());
            swipeRefreshLayout.setRefreshing(false);
            Log.d("WeatherActivity", "xyz REFRESH_COUNTY has finished");
        } else {
            SharedPreferences prefs = PreferenceManager.
                    getDefaultSharedPreferences(WeatherActivity.this);
            String defaultLocation = prefs.getString("city_name_juhe", "");
            queryWeatherInfoFromJuHe(defaultLocation);
            viewPageAdapter.notifyDataSetChanged();
            getView();
            showWeatherFromJuHe();
            swipeRefreshLayout.setRefreshing(false);
            Log.d("WeatherActivity", "xyz REFRESH_LOCATION has finished");
        }
    }

    /**
     * 查询县级代号所对应的天气代号
     *
     * @param countyCode
     */
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" +
                countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    /**
     * 查询天气代号所对应的天气
     *
     * @param weatherCode
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" +
                weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    /**
     * 通过聚合数据的接口查询城市天气
     *
     * @param cityName
     */
    private void queryWeatherInfoFromJuHe(String cityName) {
        //  组装反向地理编码的接口地址
        StringBuilder url = new StringBuilder();
        url.append("http://v.juhe.cn/weather/index?cityname=");
        url.append(cityName);
        url.append("&dtype=&format=&key=38a866e9c3aa7066994f99a0a2998a31");
        String address = url.toString();
        queryFromServer(address, "location");
    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息
     *
     * @param address
     * @param type
     */
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if ("location".equals(type)) {
                    Utility.handleWeatherResponseFromJuHe(WeatherActivity.this, response);
                    // 通过runOnUiThread()方法回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeatherFromJuHe();
                        }
                    });
                } else if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        // 从服务器返回的数据中解析出天气代号
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    // 处理服务器返回的天气信息
                    final String returnCountyName = Utility.handleWeatherResponse(WeatherActivity.this,
                            response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather(returnCountyName);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d("WeatherActivity", "xyz 548 Exception is " + e.toString());
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
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上
     *
     * @param countyName
     */
    private void showWeather(String countyName) {
        CountyWeather countyWeather = weatherDB.loadAddCountiesWeatherByCountyName(countyName);
        cityNameText.setText(countyWeather.getCountyName());
        temp1Text.setText(countyWeather.getTemp1());
        temp2Text.setText(countyWeather.getTemp2());
        weatherDespText.setText(countyWeather.getWeatherDesp());
        selectPictureByWeather(countyWeather.getWeatherDesp());
        publishText.setText("今天" + countyWeather.getUpdateTime() + "更新");
        currentDateText.setText(countyWeather.getCurrentDate());
    }

    /**
     * 从SharedPreferences文件中读取存储的JuHe天气信息，并显示到界面上
     */
    private void showWeatherFromJuHe() {
        SharedPreferences prefs = PreferenceManager.
                getDefaultSharedPreferences(this);
        Log.d("WeatherActivity", "xyz 512 city_name is " + prefs.getString("city_name_juhe", ""));
        cityNameText.setText(prefs.getString("city_name_juhe", ""));
        temp1Text.setText(prefs.getString("temp1_juhe", ""));
        temp2Text.setText(prefs.getString("temp2_juhe", ""));
        weatherDespText.setText(prefs.getString("weather_juhe", ""));
        selectPictureByWeather(prefs.getString("weather_juhe", ""));
        publishText.setText("今天" + prefs.getString("update_time_juhe", "") + "更新");
        currentDateText.setText(prefs.getString("current_date_juhe", ""));
    }

    /**
     * 根据天气选择不同的背景图
     *
     * @param weatherDesp
     */
    private void selectPictureByWeather(String weatherDesp) {
        if (weatherDesp.contains("晴")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_sunny);
        } else if (weatherDesp.contains("多云")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_cloud);
        } else if (weatherDesp.contains("阴")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_night);
        } else if (weatherDesp.contains("雷阵雨")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_thunder_shower);
        } else if (weatherDesp.contains("阵雨")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_shower);
        } else if (weatherDesp.contains("小雨")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_light_rain);
        } else if (weatherDesp.contains("中雨")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_mid_rain);
        } else if (weatherDesp.contains("雨")) {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_rain);
        } else {
            relativeLayout.setBackgroundResource(R.drawable.com1314080901103_default_weather);
        }
    }

    /**
     * 销毁全部活动，退出APP
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        Log.d("WeatherActivity", "xyz onDestroy() is finished");
    }

    /**
     * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表、省列表、还是直接退出
     */
    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();
    }
}