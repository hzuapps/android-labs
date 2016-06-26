package com.example.intelligent_restranant_boss.budget;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.intelligent_restranant_boss.R;

public class MonthBudgets extends Activity {

	private EditText ed_meal, ed_snacks, ed_clothes, ed_life, ed_others,
			ed_total;
	private Button btn_commit;
	private String year, month;
	private int meal = 0, snacks = 0, clothes = 0, life = 0, others = 0,
			total = 0;
	private DBOperation db;
	private boolean isYearBugets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_budget_detail);

		year = getIntent().getStringExtra("year");
		month = getIntent().getStringExtra("month");
		System.out.println(year + "-" + month);
		init();

		db = new DBOperation(getApplicationContext());
		db.openOrCreateDatebase();
		monthBudgets();

		fillData();

		btn_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isYearBugets) {
					yearBugets();
					btn_commit.setText("当前年账单（点击切换）");
				} else {
					monthBudgets();
					btn_commit.setText("当前月账单（点击切换）");
				}
				fillData();
				isYearBugets = !isYearBugets;

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			db.close();
			finish();
		}
		return true;
	}

	private void fillData() {
		ed_meal.setText(meal + "");
		ed_clothes.setText(clothes + "");
		ed_life.setText(life + "");
		ed_others.setText(others + "");
		ed_snacks.setText(snacks + "");
		ed_total.setText(total + "");
	}

	private void clear() {
		meal = 0;
		snacks = 0;
		clothes = 0;
		life = 0;
		others = 0;
		total = 0;

	}

	private void monthBudgets() {
		clear();
		Cursor cs = db.query(DBOperation.TABLE_NAME, null, DBOperation.YEAR
				+ " like ? AND " + DBOperation.MONTH + " like ? ",
				new String[] { year, month });

		boolean isFirstLine = true;
		while (cs.moveToNext()) {
			if (isFirstLine) {
				cs.moveToFirst();
				isFirstLine = false;
			}
			int mealIndex = cs.getColumnIndex("meal");
			int snacksIndex = cs.getColumnIndex("snacks");
			int clothesIndex = cs.getColumnIndex("clothes");
			int lifeIndex = cs.getColumnIndex("life");
			int othersIndex = cs.getColumnIndex("others");
			int totalIndex = cs.getColumnIndex("total");
			meal = meal + Integer.valueOf(cs.getString(mealIndex));
			System.out.println("monthBudgets" + meal);
			snacks = snacks + Integer.valueOf(cs.getString(snacksIndex));
			others = others + Integer.valueOf(cs.getString(othersIndex));
			life = life + Integer.valueOf(cs.getString(lifeIndex));
			total = total + Integer.valueOf(cs.getString(totalIndex));
			clothes = clothes + Integer.valueOf(cs.getString(clothesIndex));
			System.out.println(clothes);
		}
	}

	private void yearBugets() {
		clear();
		Cursor cs = db.query(DBOperation.TABLE_NAME, null, DBOperation.YEAR
				+ " like ?  ", new String[] { year });

		boolean isFirstLine = true;
		for (cs.moveToFirst(); cs.moveToNext();) {
			if (isFirstLine) {
				cs.moveToFirst();
				isFirstLine = false;
			}
			int mealIndex = cs.getColumnIndex("meal");
			int snacksIndex = cs.getColumnIndex("snacks");
			int clothesIndex = cs.getColumnIndex("clothes");
			int lifeIndex = cs.getColumnIndex("life");
			int othersIndex = cs.getColumnIndex("others");
			int totalIndex = cs.getColumnIndex("total");
			meal = meal + Integer.valueOf(cs.getString(mealIndex));
			snacks = snacks + Integer.valueOf(cs.getString(snacksIndex));
			others = others + Integer.valueOf(cs.getString(othersIndex));
			life = life + Integer.valueOf(cs.getString(lifeIndex));
			total = total + Integer.valueOf(cs.getString(totalIndex));
			clothes = clothes + Integer.valueOf(cs.getString(clothesIndex));
		}
	}

	private void init() {
		btn_commit = (Button) this.findViewById(R.id.btn_commit);
		btn_commit.setText("当前月账单（点击切换）");
		ed_clothes = (EditText) this.findViewById(R.id.ed_clothes);
		ed_life = (EditText) this.findViewById(R.id.ed_life);
		ed_meal = (EditText) this.findViewById(R.id.ed_meal);
		ed_others = (EditText) this.findViewById(R.id.ed_others);
		ed_snacks = (EditText) this.findViewById(R.id.ed_snacks);
		ed_total = (EditText) this.findViewById(R.id.ed_total);

	}
}
