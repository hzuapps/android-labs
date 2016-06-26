package com.example.intelligent_restranant_boss.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.R;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Cook;

public class MenuAPIActivity extends Activity implements APICallback,
		OnItemSelectedListener, OnClickListener {
	private Spinner spCategory1;
	private Spinner spCategory2;
	private Spinner spCategory3;

	private ArrayList<HashMap<String, Object>> category1List;
	private ArrayList<String> categoryIds;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		MobAPI.initSDK(this, "ee0efbb5f308");
		spCategory1 = (Spinner) findViewById(R.id.spCategory1);
		spCategory1.setOnItemSelectedListener(this);
		spCategory2 = (Spinner) findViewById(R.id.spCategory2);
		spCategory2.setOnItemSelectedListener(this);
		spCategory3 = (Spinner) findViewById(R.id.spCategory3);
		findViewById(R.id.btnSearch).setOnClickListener(this);

		category1List = new ArrayList<HashMap<String, Object>>();
		categoryIds = new ArrayList<String>();

		// 获取API实例，请求菜单列表
		Cook api = (Cook) MobAPI.getAPI(Cook.NAME);
		api.queryCategory(this);
	}

	@SuppressWarnings("unchecked")
	public void onSuccess(API api, int action, Map<String, Object> result) {
		// 解析数据，显示列表
		result = (Map<String, Object>) result.get("result");
		HashMap<String, Object> categoryInfo = (HashMap<String, Object>) result
				.get("categoryInfo");
		category1List.clear();
		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) result
				.get("childs");
		if (list != null) {
			category1List.addAll(list);
		}

		ArrayList<String> category1 = new ArrayList<String>();
		category1.add(com.mob.tools.utils.R.toString(categoryInfo.get("name")));
		spCategory1.setAdapter(new ArrayAdapter<String>(this, R.layout.menu,
				category1));
	}

	public void onError(API api, int action, Throwable details) {
		details.printStackTrace();
		Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("unchecked")
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.equals(spCategory1)) {
			ArrayList<String> category2 = new ArrayList<String>();
			for (HashMap<String, Object> cat : category1List) {
				HashMap<String, Object> categoryInfo = (HashMap<String, Object>) cat
						.get("categoryInfo");
				category2.add(com.mob.tools.utils.R.toString(categoryInfo
						.get("name")));
			}
			spCategory2.setAdapter(new ArrayAdapter<String>(this,
					R.layout.menu, category2));
		} else if (parent.equals(spCategory2)) {
			ArrayList<HashMap<String, Object>> category2List = (ArrayList<HashMap<String, Object>>) category1List
					.get(position).get("childs");
			ArrayList<String> category3 = new ArrayList<String>();
			categoryIds = new ArrayList<String>();
			for (HashMap<String, Object> cat : category2List) {
				HashMap<String, Object> categoryInfo = (HashMap<String, Object>) cat
						.get("categoryInfo");
				category3.add(com.mob.tools.utils.R.toString(categoryInfo
						.get("name")));
				categoryIds.add(com.mob.tools.utils.R.toString(categoryInfo
						.get("ctgId")));
			}
			spCategory3.setAdapter(new ArrayAdapter<String>(this,
					R.layout.menu, category3));
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {

	}

	public void onClick(View v) {
		// 打开详情页面
		if (categoryIds.size() > 0) {
			String title = (String) spCategory3.getSelectedItem();
			int ind = spCategory3.getSelectedItemPosition();
			String ctgId = categoryIds.get(ind < 0 ? 0 : (ind >= categoryIds
					.size() ? categoryIds.size() : ind));
			Intent i = new Intent(this, MenuListActivity.class);
			i.putExtra("title", title);
			i.putExtra("ctgId", ctgId);
			startActivity(i);
		}
	}

}
