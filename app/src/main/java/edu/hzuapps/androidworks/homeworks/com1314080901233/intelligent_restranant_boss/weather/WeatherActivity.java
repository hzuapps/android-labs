package com.example.intelligent_restranant_boss.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.intelligent_restranant_boss.MyActivity;
import com.example.intelligent_restranant_boss.R;

public class WeatherActivity extends Activity implements OnClickListener {

	private Button btn_left_header;
	private TextView tv_head_title;
	private String weather_wd, weather_tq;
	private TextView city;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = null;
	private ProgressDialog progressDialog;
	private String cityName;
	private View ws2_ll_yes;
	private ImageView td_iv_image, tor_iv_image, aftor_iv_image;
	private Button btn_submit_other;
	private TextView td_tv_attr1, td_tv_attr2, td_tv_attr3, td_city,
			tor_tv_attr1, tor_tv_attr2, tor_tv_attr3, aftor_tv_attr1,
			aftor_tv_attr2, aftor_tv_attr3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather_main);
		findViewById();
		initTitle();
		initView();
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		initListener();
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		initLocation();
		mLocationClient.start();
		/*progressDialog = ProgressDialog
				.show(this, null, "城市定位中...", true, true);
		Intent intent = new Intent("SEND_INFOR");*/

	}

	private void initView() {
		ws2_ll_yes = this.findViewById(R.id.ws2_ll_yes);
		td_iv_image = (ImageView) this.findViewById(R.id.td_iv_image);
		tor_iv_image = (ImageView) this.findViewById(R.id.tor_iv_image);
		aftor_iv_image = (ImageView) this.findViewById(R.id.aftor_iv_image);
		td_tv_attr1 = (TextView) this.findViewById(R.id.td_tv_attr1);
		td_tv_attr2 = (TextView) this.findViewById(R.id.td_tv_attr2);
		td_tv_attr3 = (TextView) this.findViewById(R.id.td_tv_attr3);
		tor_tv_attr1 = (TextView) this.findViewById(R.id.tor_tv_attr1);
		tor_tv_attr2 = (TextView) this.findViewById(R.id.tor_tv_attr2);
		tor_tv_attr3 = (TextView) this.findViewById(R.id.tor_tv_attr3);
		aftor_tv_attr1 = (TextView) this.findViewById(R.id.aftor_tv_attr1);
		aftor_tv_attr2 = (TextView) this.findViewById(R.id.aftor_tv_attr2);
		aftor_tv_attr3 = (TextView) this.findViewById(R.id.aftor_tv_attr3);
		td_city = (TextView) this.findViewById(R.id.td_city);
	}

	@Override
	protected void onDestroy() {

		mLocationClient.stop();
		super.onDestroy();
	}

	private void initListener() {
		myListener = new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				String citys = location.getCity();
				cityName = citys.substring(0, citys.length() - 1);
				if (cityName != null) {
					QueryAsyncTask asyncTask = new QueryAsyncTask();
					asyncTask.execute();
				} else {
					Toast.makeText(WeatherActivity.this, "定位不到当前城市，无法查询天气",
							Toast.LENGTH_SHORT).show();
				}
			}
		};

	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	// 使用异步通信AsynTask对天气进行查询
	private class QueryAsyncTask extends AsyncTask {
		@Override
		protected void onPostExecute(Object result) {
			progressDialog.dismiss();
			if (result != null) {
				String weatherResult = (String) result;
				if (weatherResult.split(";").length > 1) {
					String a = weatherResult.split(";")[1];
					if (a.split("=").length > 1) {
						String b = a.split("=")[1];
						String c = b.substring(1, b.length() - 1);
						String[] resultArr = c.split("\\}");
						if (resultArr.length > 0) {
							todayParse(resultArr[0]);
							tomorrowParse(resultArr[1]);
							afterTorrowParse(resultArr[2]);
							td_city.setText(cityName);
							ws2_ll_yes.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		}

		@Override
		protected Object doInBackground(Object... params) {
			// 查询天气
		//	return WeatherService.getWeather(cityName);
			return null;
		}

	}

	public void todayParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";
		String tq = "";
		String fx = "";
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			td_tv_attr1.setText("气温：" + wd);
			td_tv_attr2.setText("天气情况：" + tq);
			td_tv_attr3.setText("风向：" + fx);
			td_iv_image.setImageResource(Utils.imageResoId(tq));
			Intent intent = new Intent("SEND_INFOR");
			intent.putExtra("city", cityName);
			intent.putExtra("wd", wd);
			intent.putExtra("tq", tq);
			WeatherActivity.this.sendBroadcast(intent);

		}

	}

	public void tomorrowParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";
		String tq = "";
		String fx = "";
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			tor_tv_attr1.setText("气温：" + wd);
			tor_tv_attr2.setText("天气情况：" + tq);
			tor_tv_attr3.setText("风向：" + fx);
			tor_iv_image.setImageResource(Utils.imageResoId(tq));
		}

	}

	public void afterTorrowParse(String weather) {
		String temp = weather.replace("'", "");
		String[] tempArr = temp.split(",");
		String wd = "";
		String tq = "";
		String fx = "";
		if (tempArr.length > 0) {
			for (int i = 0; i < tempArr.length; i++) {
				if (tempArr[i].indexOf("t1:") != -1) {
					wd = tempArr[i].substring(3, tempArr[i].length()) + "℃";
				} else if (tempArr[i].indexOf("t2:") != -1) {
					wd = wd + "~"
							+ tempArr[i].substring(3, tempArr[i].length())
							+ "℃";
				} else if (tempArr[i].indexOf("d1:") != -1) {
					fx = tempArr[i].substring(3, tempArr[i].length());
				} else if (tempArr[i].indexOf("s1:") != -1) {
					tq = tempArr[i].substring(4, tempArr[i].length());
				}
			}

			aftor_tv_attr1.setText("气温：" + wd);
			aftor_tv_attr2.setText("天气情况：" + tq);
			aftor_tv_attr3.setText("风向：" + fx);
			aftor_iv_image.setImageResource(Utils.imageResoId(tq));
		}
	}

	private void initTitle() {
		tv_head_title.setText("天气预告");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left_header:
			Intent intent_back = new Intent(WeatherActivity.this,
					MyActivity.class);
			startActivity(intent_back);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;

		default:
			break;
		}

	}

	private void findViewById() {
		btn_left_header = (Button) this.findViewById(R.id.btn_left_header);
		btn_left_header.setOnClickListener(this);
		tv_head_title = (TextView) this.findViewById(R.id.tv_head_title);

	}
}
