package edu.hzuapps.androidworks.homeworks3;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.spot.SpotManager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_AnswerColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_BaseColumns;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_CauseInfo;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_DBManager;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_TimeUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobile.cetfour.R;

public class Net1314080903124_MainActivity extends Activity implements OnClickListener {

	private boolean hasPressedBack;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Net1314080903124_TimeUtils.isNetworkAvailable(this)) {
			Net1314080903124_DBManager.getInstance(this).removeAll(Net1314080903124_AnswerColumns.TABLE_NAME);
		}
		setContentView(R.layout.activity_main);
		showBanner();
		TextView order = (TextView) findViewById(R.id.order);
		TextView simulate = (TextView) findViewById(R.id.simulate);
		TextView recommend = (TextView) findViewById(R.id.recommend);
		LinearLayout favorite = (LinearLayout) findViewById(R.id.favorite);
		LinearLayout wrong = (LinearLayout) findViewById(R.id.wrong);
		LinearLayout history = (LinearLayout) findViewById(R.id.history);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		asynctaskInstance();
		order.setOnClickListener(this);
		simulate.setOnClickListener(this);
		recommend.setOnClickListener(this);
		favorite.setOnClickListener(this);
		wrong.setOnClickListener(this);
		history.setOnClickListener(this);
	}

	private void asynctaskInstance() {
		progressBar.setVisibility(View.VISIBLE);
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(Net1314080903124_Contans.PATH_HOME, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String result = new String(arg2);
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("code");
					if (code == 1) {
						String content = jsonObject.getString("content");
						JSONArray array = new JSONArray(content);
						for (int i = 0; i < content.length(); i++) {
							JSONObject object = array.getJSONObject(i);
							String timu_title = new JSONObject(object.getString("timu")).getString("title");
							String timu_one = new JSONObject(object.getString("timu")).getString("one");
							String timu_tow = new JSONObject(object.getString("timu")).getString("tow");
							String timu_three = new JSONObject(object.getString("timu")).getString("three");
							String timu_four = new JSONObject(object.getString("timu")).getString("four");
							String daan_one = new JSONObject(object.getString("daan")).getString("daan_one");
							String daan_tow = new JSONObject(object.getString("daan")).getString("daan_tow");
							String daan_three = new JSONObject(object.getString("daan")).getString("daan_three");
							String daan_four = new JSONObject(object.getString("daan")).getString("daan_four");
							String types = new JSONObject(object.getString("types")).getString("types");
							String detail = new JSONObject(object.getString("detail")).getString("detail");
							int reply = Net1314080903124_BaseColumns.NULL;
							Net1314080903124_CauseInfo myData = new Net1314080903124_CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
									daan_three, daan_four, detail, types, reply);
							Net1314080903124_DBManager.getInstance(Net1314080903124_MainActivity.this).insert(Net1314080903124_AnswerColumns.TABLE_NAME, myData);
						}
					} else {
						Toast.makeText(Net1314080903124_MainActivity.this, "数据解析出现异常，请联系管理员", Toast.LENGTH_SHORT).show();
					}
					progressBar.setVisibility(View.GONE);
				} catch (JSONException e) {
					progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				progressBar.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.recommend:
			// 调用方式一：直接打开全屏积分墙
			 OffersManager.getInstance(this).showOffersWall();
			break;

		case R.id.order:
			startActivity(new Intent(this, Net1314080903124_OrderActivity.class));
			break;

		case R.id.simulate:
			View layout = getLayoutInflater().inflate(R.layout.enter_simulate, null);
			final Dialog dialog = new Dialog(this);
			dialog.setTitle("温馨提示");
			dialog.show();
			dialog.getWindow().setContentView(layout);
			final EditText et_name = (EditText) layout.findViewById(R.id.et_name);
			TextView confirm = (TextView) layout.findViewById(R.id.confirm);
			TextView cancel = (TextView) layout.findViewById(R.id.cancel);
			confirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
						Toast.makeText(Net1314080903124_MainActivity.this, "请先输入考试姓名", Toast.LENGTH_SHORT).show();
					} else {
						Net1314080903124_ExamActivity.intentToExamActivity(Net1314080903124_MainActivity.this, et_name.getText().toString().trim());
						Toast.makeText(Net1314080903124_MainActivity.this, "考试开始", Toast.LENGTH_SHORT).show();
					}
					dialog.dismiss();
				}
			});

			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			break;

		case R.id.favorite:
			startActivity(new Intent(this, Net1314080903124_CollectActivity.class));
			break;

		case R.id.wrong:
			startActivity(new Intent(this, Net1314080903124_ErrorActivity.class));
			break;

		case R.id.history:
			startActivity(new Intent(this, Net1314080903124_HisResultActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (hasPressedBack) {
				finish();
				return true;
			}
			hasPressedBack = true;
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					hasPressedBack = false;
				}
			}, 3000);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showBanner() {

		// 广告条接口调用（适用于应用）
		// 将广告条adView添加到需要展示的layout控件中
		// LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
		// AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// adLayout.addView(adView);

		// 广告条接口调用（适用于游戏）

		// 实例化LayoutParams(重要)
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数

		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
		this.addContentView(adView, layoutParams);
	}

	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(this).onStop();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		SpotManager.getInstance(this).onDestroy();
		super.onDestroy();
	}

}
