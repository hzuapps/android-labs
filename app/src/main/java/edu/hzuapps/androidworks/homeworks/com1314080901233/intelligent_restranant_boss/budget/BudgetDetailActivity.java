package com.example.intelligent_restranant_boss.budget;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intelligent_restranant_boss.R;

public class BudgetDetailActivity extends Activity {
	private String year;
	private String month;
	private String day;
	private EditText ed_remarks, ed_meal, ed_snacks, ed_clothes, ed_life,
			ed_others, ed_total;
	private Button btn_commit;
	String remarks;
	String meal;
	String snacks;
	String clothes;
	String life;
	String others;
	String total;
	private boolean hasData;
	private DBOperation dbOperation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_budget_detail);

		init();

		year = getIntent().getStringExtra("year");
		month = getIntent().getStringExtra("month");
		day = getIntent().getStringExtra("day");

		dbOperation = new DBOperation(getApplicationContext());
		dbOperation.openOrCreateDatebase();
		Cursor cursor = dbOperation.query(DBOperation.TABLE_NAME, null,
				DBOperation.YEAR + " like ? AND " + DBOperation.MONTH
						+ " like ? AND " + DBOperation.DAY + " like ? ",
				new String[] { year, month, day });
		hasData = cursor.moveToFirst();
		if (hasData) {
			int remarkIndex = cursor.getColumnIndex(DBOperation.REMARKS);
			int mealIndex = cursor.getColumnIndex(DBOperation.MEAL);
			int snackskIndex = cursor.getColumnIndex(DBOperation.SNACKS);
			int clothesIndex = cursor.getColumnIndex(DBOperation.CLOTHES);
			int lifeIndex = cursor.getColumnIndex(DBOperation.LIFE);
			int othersIndex = cursor.getColumnIndex(DBOperation.OTHERS);
			int totalIndex = cursor.getColumnIndex(DBOperation.TOTAL);

			remarks = cursor.getString(remarkIndex);
			meal = cursor.getString(mealIndex);
			snacks = cursor.getString(snackskIndex);
			clothes = cursor.getString(clothesIndex);
			life = cursor.getString(lifeIndex);
			others = cursor.getString(othersIndex);
			total = cursor.getString(totalIndex);

			fillData();
		}
		btn_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				remarks = ed_remarks.getText().toString().equals("") ? "无"
						: ed_remarks.getText().toString();
				meal = ed_meal.getText().toString().equals("") ? "0" : ed_meal
						.getText().toString();
				snacks = ed_snacks.getText().toString().equals("") ? "0"
						: ed_snacks.getText().toString();
				clothes = ed_clothes.getText().toString().equals("") ? "0"
						: ed_clothes.getText().toString();
				life = ed_life.getText().toString().equals("") ? "0" : ed_life
						.getText().toString();
				others = ed_others.getText().toString().equals("") ? "0"
						: ed_others.getText().toString();
				int a = Integer.valueOf(meal) + Integer.valueOf(snacks)
						+ Integer.valueOf(clothes) + Integer.valueOf(life)
						+ Integer.valueOf(others);
				total = a + "";
				if (!hasData) {
					dbOperation.insert(year, month, day, meal, snacks, clothes,
							life, others, remarks, total);
					Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT)
							.show();
				} else {
					int i = dbOperation.update(new String[] {
							DBOperation.REMARKS, DBOperation.MEAL,
							DBOperation.SNACKS, DBOperation.CLOTHES,
							DBOperation.LIFE, DBOperation.OTHERS,
							DBOperation.TOTAL }, new String[] { remarks, meal,
							snacks, clothes, life, others, total },
							DBOperation.YEAR + " =? AND " + DBOperation.MONTH
									+ " =? AND " + DBOperation.DAY + " =?",
							new String[] { year, month, day });
					Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_SHORT)
							.show();
				}
				ed_total.setText(total);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			dbOperation.close();
			finish();
		}
		return true;
	}

	private void fillData() {
		ed_others.setText(others);
		ed_clothes.setText(clothes);
		ed_life.setText(life);
		ed_meal.setText(meal);
		ed_snacks.setText(snacks);
		ed_total.setText(total);
		ed_remarks.setText(remarks);

	}

	private void init() {
		btn_commit = (Button) this.findViewById(R.id.btn_commit);
		ed_clothes = (EditText) this.findViewById(R.id.ed_clothes);
		ed_life = (EditText) this.findViewById(R.id.ed_life);
		ed_meal = (EditText) this.findViewById(R.id.ed_meal);
		ed_others = (EditText) this.findViewById(R.id.ed_others);
		ed_snacks = (EditText) this.findViewById(R.id.ed_snacks);
		ed_total = (EditText) this.findViewById(R.id.ed_total);
		ed_remarks = (EditText) this.findViewById(R.id.ed_remarks);

	}

}
