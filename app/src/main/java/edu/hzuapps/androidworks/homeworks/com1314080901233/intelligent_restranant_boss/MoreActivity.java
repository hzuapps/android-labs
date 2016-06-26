package com.example.intelligent_restranant_boss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//标签页5
public class MoreActivity extends PreferenceActivity {

	TextView tv_system_setting;
	SharedPreferences mSharedPreferences;
	Preference sp_account;
	Preference sp_cache;
	Preference sp_feedback;
	Preference sp_update;
	Preference sp_about;
	CheckBoxPreference sp_loadimage;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置显示Preferences
		addPreferencesFromResource(R.xml.preferences);

		// 获得SharedPreferences
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		ListView lv_local = getListView();
		lv_local.setBackgroundColor(0);
		lv_local.setCacheColorHint(0);
		((ViewGroup) lv_local.getParent()).removeView(lv_local);
		ViewGroup localViewGroup = (ViewGroup) getLayoutInflater().inflate(
				R.layout.setting, null);
		((ViewGroup) localViewGroup.findViewById(R.id.setting_content))
				.addView(lv_local, -1, -1);
		setContentView(localViewGroup);

		tv_system_setting = (TextView) findViewById(R.id.systv);
		tv_system_setting.setText(R.string.setting_head_title);
		// 登录、注销
		sp_account = findPreference("account");
		sp_account
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						Intent intent = new Intent(MoreActivity.this,
								LoginDialog.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						return true;
					}
				});

		sp_cache = findPreference("cache");
		sp_cache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				Toast.makeText(getApplicationContext(), "已清除缓存", 1).show();
				sp_cache.setSummary("0KB");
				return true;
			}
		});

		// 加载图片loadimage
		sp_loadimage = (CheckBoxPreference) findPreference("loadimage");
		sp_loadimage.setSummary("页面加载图片 (默认在WIFI网络下加载图片)");
		sp_loadimage
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						Toast.makeText(getApplicationContext(), "正在加载图片", 1)
								.show();
						return true;
					}
				});

		// 意见反馈
		sp_feedback = findPreference("feedback");
		sp_feedback
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						Intent intent = new Intent(getApplicationContext(),
								FeedbackActivity.class);
						startActivity(intent);
						Toast.makeText(getApplicationContext(), "正在加载图片", 1)
						.show();
						return true;
					}
				});

		// 版本更新
		sp_update = findPreference("update");
		sp_update
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					public boolean onPreferenceClick(Preference preference) {
						Toast.makeText(getApplicationContext(), "最新的版本", 1)
								.show();
						return true;
					}
				});

		// 关于我们
		sp_about = findPreference("about");
		sp_about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(getApplicationContext(),
						AboutUsActivity.class);
				startActivity(intent);
				Toast.makeText(getApplicationContext(), "正在加载图片", 1)
				.show();
				return true;
			}
		});/**/
	}

}
