package edu.hzuapps.androidworks.homeworks3;

import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class Net1314080903124_SplashSpotActivity extends Activity { 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 如果仅仅使用开屏，需要取消注释以下注释，如果使用了开屏和插屏，则不需要。
		SpotManager.getInstance(this).loadSplashSpotAds();

		// 开屏的两种调用方式：请根据使用情况选择其中一种调用方式。
		// 1.可自定义化调用：
		// 此方式能够将开屏适应一些应用的特殊场景进行使用。
		// 传入需要跳转的activity
		SplashView splashView = new SplashView(this, Net1314080903124_MainActivity.class);

		// 开屏也可以作为控件加入到界面中。
		setContentView(splashView.getSplashView());

		SpotManager.getInstance(this).showSplashSpotAds(this, splashView,
				new SpotDialogListener() {

					@Override
					public void onShowSuccess() {
						Log.i("YoumiAdDemo", "开屏展示成功");
					}

					@Override
					public void onShowFailed() {
						Log.i("YoumiAdDemo", "开屏展示失败。");
					}

					@Override
					public void onSpotClosed() {
						Log.i("YoumiAdDemo", "开屏关闭。");
					}
				});

		// 2.简单调用方式
		// 如果没有特殊要求，简单使用此句即可实现插屏的展示
		// SpotManager.getInstance(this).showSplashSpotAds(this,
		// Net1314080903124_MainActivity.class);

	}

	// 请务必加上词句，否则进入网页广告后无法进去原sdk
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 10045) {
			Intent intent = new Intent(Net1314080903124_SplashSpotActivity.this, Net1314080903124_MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		// 取消后退键
	}

	@Override
	protected void onResume() {

		/**
		 * 设置为竖屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// land
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// port
		}
	}

}
