package edu.hzuapps.androidworks.homeworks.net1314080903132;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class Net1314080903132LocationActivity3 extends Activity{
    private LocationClient mLocationClient;
    private TextView LocationResult,ModeInfor;
    private Button startLocation;
    private RadioGroup selectMode,selectCoordinates;
    private EditText frequence;
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor="gcj02";
    private CheckBox checkGeoLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_net1314080903132);

        mLocationClient = ((Net1314080903132LocationApplication)getApplication()).mLocationClient;

        LocationResult = (TextView)findViewById(R.id.textView1);                //显示结果
        LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        ModeInfor= (TextView)findViewById(R.id.modeinfor);                       //选择提示
        ModeInfor.setText(getString(R.string.hight_accuracy_desc));
        ((Net1314080903132LocationApplication)getApplication()).mLocationResult = LocationResult;
        frequence = (EditText)findViewById(R.id.frequence);                       //定位时间间隔
        checkGeoLocation = (CheckBox)findViewById(R.id.geolocation);               //反地理编码
        startLocation = (Button)findViewById(R.id.addfence);                       //开启定位按钮
        startLocation.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                initLocation();

                if(startLocation.getText().equals(getString(R.string.startlocation))){
                    mLocationClient.start();//定位SDK start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
                    startLocation.setText(getString(R.string.stoplocation));
                }else{
                    mLocationClient.stop();
                    startLocation.setText(getString(R.string.startlocation));
                }


            }
        });
        selectMode = (RadioGroup)findViewById(R.id.selectMode);                 //单选按钮组  选择定位精度
        selectCoordinates= (RadioGroup)findViewById(R.id.selectCoordinates);     //单选按钮组  选择定位类型
        selectMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {   //监听单选按钮

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                String ModeInformation = null;
                switch (checkedId) {
                    case R.id.radio_hight:
                        tempMode = LocationMode.Hight_Accuracy;
                        ModeInformation = getString(R.string.hight_accuracy_desc);
                        break;
                    case R.id.radio_low:
                        tempMode = LocationMode.Battery_Saving;
                        ModeInformation = getString(R.string.saving_battery_desc);
                        break;
                    case R.id.radio_device:
                        tempMode = LocationMode.Device_Sensors;
                        ModeInformation = getString(R.string.device_sensor_desc);
                        break;
                    default:
                        break;
                }
                ModeInfor.setText(ModeInformation);
            }
        });
        selectCoordinates.setOnCheckedChangeListener(new OnCheckedChangeListener() {//监听单选按钮

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.radio_gcj02:
                        tempcoor="gcj02";//国家测绘局标准
                        break;
                    case R.id.radio_bd09ll:
                        tempcoor="bd09ll";//百度经纬度标准
                        break;
                    case R.id.radio_bd09:
                        tempcoor="bd09";//百度墨卡托标准
                        break;
                    default:
                        break;
                }
            }
        });
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        mLocationClient.stop();
        super.onStop();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        int span=1000;
        try {
            span = Integer.valueOf(frequence.getText().toString());
        } catch (Exception e) {
            // TODO: handle exception
        }
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(checkGeoLocation.isChecked());//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }
}
