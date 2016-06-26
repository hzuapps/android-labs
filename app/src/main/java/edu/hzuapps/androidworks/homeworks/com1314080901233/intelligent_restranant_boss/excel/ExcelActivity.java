package com.example.intelligent_restranant_boss.excel;

import java.util.ArrayList;
import java.util.List;

import com.example.intelligent_restranant_boss.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

//表格的Activity,很多个跳转到这里
/**
 * 实现功能: 1.使用TableLayout动态布局展示,可动态添加. 2.可实现横向的乘法运算和纵向的加法运算，只要表格由变化就会实时更新
 * 3.重置时到初始化状态.
 */

@SuppressLint("ResourceAsColor")
public class ExcelActivity extends Activity {

	private ImageView add, clear, save, back;
	private DisplayMetrics displayMetrics;
	private int entity_id = 0;
	private DictDaoImpl dao = null;
	private TableLayout table = null;
	private int id = 0; // 用户记录最大的orders值
	private TextView finalnumber, finaltotle;
	private int numberber = 0;
	private float totlele = 0;
	private Handler mhandler = null;
	private List<Dict> templist;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_excel_dict_item);
		displayMetrics = new DisplayMetrics();
		(ExcelActivity.this).getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		// 实例化按钮并设置监听器.
		findViewById();

		entity_id = 1;
		table = (TableLayout) findViewById(R.id.dictTable);
		initDictItemTable();
		getfoottable();

	}

	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add:
				appendNewRow(table);
				break;
			case R.id.clear:
				initDictItemTable();
				getfoottable();
				Toast.makeText(ExcelActivity.this, "重置成功", Toast.LENGTH_SHORT)
						.show();
				break;
			case R.id.save:
				Toast.makeText(ExcelActivity.this, "保存成功", Toast.LENGTH_SHORT)
				.show();
				break;
			case R.id.back:
				finish();
				break;
			default:
				break;
			}

		}
	};

	private void appendNewRow(final TableLayout table) {
		TableRow row = new TableRow(this);
		row.setId(0);
		id++;
		final Dict dict = new Dict();
		final EditText projectadd = new EditText(this);
		final EditText numberadd = new EditText(this);
		final EditText priceadd = new EditText(this);
		final EditText totleadd = new EditText(this);

		projectadd.setWidth(displayMetrics.widthPixels / 4);
		// projectadd.setLayoutParams(new
		// LayoutParams(displayMetrics.widthPixels / 4,
		// LayoutParams.WRAP_CONTENT));
		projectadd.setGravity(Gravity.LEFT);
		projectadd.setSingleLine(true);
		projectadd.setBackgroundResource(R.drawable.excel_bg_city_search_normal);

		totleadd.setWidth(displayMetrics.widthPixels / 4);
		// totleadd.setLayoutParams(new LayoutParams(displayMetrics.widthPixels
		// / 4, LayoutParams.WRAP_CONTENT));
		totleadd.setGravity(Gravity.LEFT);
		totleadd.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
		totleadd.setSingleLine(true);
		totleadd.setInputType(InputType.TYPE_NULL);

		priceadd.setWidth(displayMetrics.widthPixels / 4);
		priceadd.setGravity(Gravity.LEFT);
		priceadd.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
		priceadd.setSingleLine(true);
		priceadd.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
				| InputType.TYPE_CLASS_NUMBER);
		priceadd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!"".equals(priceadd.getText() + "")
						&& !"".equals(numberadd.getText() + "")) {
					totleadd.setText(Float.parseFloat(arg0 + "")
							* Integer.parseInt(numberadd.getText() + "") + "");
					dict.setTotle(Float.parseFloat(totleadd.getText() + ""));
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list;
					mhandler.sendMessage(msg);
				} else {
					totleadd.setText("");
					dict.setTotle(0);
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list;
					mhandler.sendMessage(msg);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		numberadd.setWidth(displayMetrics.widthPixels / 4);
		// numberadd.setLayoutParams(new LayoutParams(displayMetrics.widthPixels
		// / 4, LayoutParams.WRAP_CONTENT));
		numberadd.setGravity(Gravity.LEFT);
		numberadd.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
		numberadd.setSingleLine(true);
		numberadd.setInputType(InputType.TYPE_CLASS_NUMBER);
		numberadd
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
		numberadd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!"".equals(priceadd.getText() + "")
						&& !"".equals(numberadd.getText() + "")) {
					totleadd.setText(Float.parseFloat(priceadd.getText() + "")
							* Integer.parseInt(arg0 + "") + "");
					dict.setTotle(Float.parseFloat(priceadd.getText() + "")
							* Integer.parseInt(arg0 + ""));
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list;
					mhandler.sendMessage(msg);
				} else {
					totleadd.setText("");
					dict.setTotle(0);
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list;
					mhandler.sendMessage(msg);
				}

				if (!"".equals(numberadd.getText() + "")) {
					dict.setNumber(Integer.parseInt(arg0 + ""));
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list;
					mhandler.sendMessage(msg);

				} else {
					dict.setNumber(0);
					Message msg = new Message();
					msg.what = 1;
					msg.obj = list;
					mhandler.sendMessage(msg);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		list.add(dict);
		row.addView(projectadd);
		row.addView(priceadd);
		row.addView(numberadd);
		row.addView(totleadd);
		table.addView(row, id);
	}

	List<Dict> list;

	@SuppressLint("ResourceAsColor")
	public void initDictItemTable() {
		if (dao == null) {
			dao = new DictDaoImpl(this);
		}

		list = new ArrayList<Dict>();
		if (entity_id != 0) {
			list = dao.getDictItem(String.valueOf(entity_id));
		}
		table.removeAllViews();

		TableRow row1 = new TableRow(this);

		TextView project1 = new TextView(this);
		project1.setText("项目");
		project1.setWidth(displayMetrics.widthPixels / 4);
		project1.setGravity(Gravity.CENTER);
		project1.setTextSize(18);
		project1.setTextColor(ContextCompat.getColor(this, R.color.white));
		project1.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

		TextView price1 = new TextView(this);
		price1.setText("单价");
		price1.setWidth(displayMetrics.widthPixels / 4);
		price1.setGravity(Gravity.CENTER);
		price1.setTextSize(18);
		price1.setTextColor(ContextCompat.getColor(this,R.color.white));
		price1.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

		TextView number1 = new TextView(this);
		number1.setText("数量");
		number1.setWidth(displayMetrics.widthPixels / 4);
		number1.setGravity(Gravity.CENTER);
		number1.setTextSize(18);
		number1.setTextColor(ContextCompat.getColor(this,R.color.white));
		number1.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

		final TextView totle1 = new TextView(this);
		totle1.setText("金额");
		totle1.setWidth(displayMetrics.widthPixels / 4);
		totle1.setGravity(Gravity.CENTER);
		totle1.setTextSize(18);
		totle1.setTextColor(ContextCompat.getColor(this,R.color.white));
		totle1.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

		// row1.addView(dictItemId1);
		row1.addView(project1);
		row1.addView(price1);
		row1.addView(number1);
		row1.addView(totle1);
		table.addView(row1);
		id = 0;
		for (final Dict dict : list) {
			id = Math.max(dict.getId(), id);// 取较大的作为新orders.
			TableRow row = new TableRow(this);
			row.setId(dict.getId());
			EditText project = new EditText(this);
			final EditText price = new EditText(this);
			final EditText number = new EditText(this);
			final EditText totle = new EditText(this);

			project.setWidth(displayMetrics.widthPixels / 4);
			project.setGravity(Gravity.LEFT);
			project.setSingleLine(true);
			project.setBackgroundResource(R.drawable.excel_bg_city_search_normal);

			price.setWidth(displayMetrics.widthPixels / 4);
			price.setGravity(Gravity.LEFT);
			price.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
			price.setSingleLine(true);
			price.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL
					| InputType.TYPE_CLASS_NUMBER);
			price.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					if (!"".equals(price.getText() + "")
							&& !"".equals(number.getText() + "")) {
						totle.setText(Float.parseFloat(arg0 + "")
								* Integer.parseInt(number.getText() + "") + "");
						dict.setTotle(Float.parseFloat(arg0 + "")
								* Integer.parseInt(number.getText() + ""));
						Message msg = new Message();
						msg.what = 1;
						msg.obj = list;
						mhandler.sendMessage(msg);
					} else {
						totle.setText("");
						dict.setTotle(0);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = list;
						mhandler.sendMessage(msg);
					}

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {

				}

				@Override
				public void afterTextChanged(Editable arg0) {
				}

			});

			number.setWidth(displayMetrics.widthPixels / 4);
			number.setGravity(Gravity.LEFT);
			number.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
			number.setSingleLine(true);
			number.setInputType(InputType.TYPE_CLASS_NUMBER);
			number.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					9) });
			number.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int start,
						int before, int count) {

					if (!"".equals(price.getText() + "")
							&& !"".equals(number.getText() + "")) {
						totle.setText(Float.parseFloat(price.getText() + "")
								* Integer.parseInt(arg0 + "") + "");
						dict.setTotle(Float.parseFloat(totle.getText() + ""));
						Message msg = new Message();
						msg.what = 1;
						msg.obj = list;
						mhandler.sendMessage(msg);

					} else {
						totle.setText("");
						dict.setTotle(0);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = list;
						mhandler.sendMessage(msg);
					}
					Log.w("111", start + "----" + before + "----" + count);
					if (!"".equals(number.getText() + "")) {
						dict.setNumber(Integer.parseInt(arg0 + ""));
						Message msg = new Message();
						msg.what = 1;
						msg.obj = list;
						mhandler.sendMessage(msg);
					} else {
						dict.setNumber(0);
						Message msg = new Message();
						msg.what = 1;
						msg.obj = list;
						mhandler.sendMessage(msg);
					}

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {

				}

				@Override
				public void afterTextChanged(Editable arg0) {

				}
			});

			totle.setWidth(displayMetrics.widthPixels / 4);
			totle.setGravity(Gravity.LEFT);
			totle.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
			totle.setSingleLine(true);
			totle.setInputType(InputType.TYPE_NULL);

			row.addView(project);
			row.addView(price);
			row.addView(number);
			row.addView(totle);
			table.addView(row, new TableLayout.LayoutParams());
		}

	}

	private void getfoottable() {

		TableRow footrow = new TableRow(this);
		TextView finalname = new TextView(this);
		finaltotle = new TextView(this);
		finalnumber = new TextView(this);

		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1) {
					numberber = 0;
					totlele = 0;
					templist = (List<Dict>) msg.obj;
					for (int i = 0; i < templist.size(); i++) {
						numberber = numberber + templist.get(i).getNumber();
						totlele = totlele + templist.get(i).getTotle();
					}
					finalnumber.setText(numberber + "");
					finaltotle.setText(totlele + "");
				}
			}
		};

		finalname.setText("合计");
		finalname.setWidth(displayMetrics.widthPixels / 4);
		finalname.setGravity(Gravity.CENTER);
		finalname.setTextSize(18);
		finalname.setTextColor(ContextCompat.getColor(this,R.color.white));
		finalname.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

		TextView finalname1 = new TextView(this);
		finalname1.setWidth(displayMetrics.widthPixels / 4);
		finalname1.setTextSize(18);
		finalname1.setTextColor(ContextCompat.getColor(this,R.color.white));
		finalname1.setBackgroundColor(ContextCompat.getColor(this,R.color.black));

		finalnumber.setWidth(displayMetrics.widthPixels / 4);
		finalnumber.setGravity(Gravity.LEFT);
		finalnumber.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
		finalnumber.setSingleLine(true);
		finalnumber.setTextSize(18);

		finaltotle.setWidth(displayMetrics.widthPixels / 4);
		finaltotle.setGravity(Gravity.LEFT);
		finaltotle.setTextSize(18);
		finaltotle.setBackgroundResource(R.drawable.excel_bg_city_search_normal);
		finaltotle.setSingleLine(true);

		footrow.addView(finalname);
		footrow.addView(finalname1);
		footrow.addView(finalnumber);
		footrow.addView(finaltotle);

		table.addView(footrow, id + 1, new TableLayout.LayoutParams());
	}

	private void findViewById() {
		add = (ImageView) findViewById(R.id.add);
		clear = (ImageView) findViewById(R.id.clear);
		save = (ImageView) findViewById(R.id.save);
		back = (ImageView) findViewById(R.id.back);
		add.setOnClickListener(listener);
		clear.setOnClickListener(listener);
		save.setOnClickListener(listener);
		back.setOnClickListener(listener);
	}

}
