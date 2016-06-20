package com.example.dictionary;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class EditWord extends Activity implements OnClickListener {
	private String action;
	private EditText zhushi;
	private EditText meanning;
	private Button confirm;
	private Button cancel;
	int index = -1;
	private Bundle bundle;
	private ArrayList<HashMap<String, String>> arrayList;

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置隐藏标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);
		Bundle bundle = this.getIntent().getExtras();
		action = bundle.getString("action");

		initWidgets();
	}

	private void initWidgets() {
		// spelling是单词，meanning是解释，confirm是确定，cancel是取消
		zhushi = (EditText) findViewById(R.id.zhushi);
		meanning = (EditText) findViewById(R.id.meanning);
		confirm = (Button) findViewById(R.id.button1);
		cancel = (Button) findViewById(R.id.button2);
		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (action.equals("edit")) {
			zhushi.setText(bundle.getString("word"));
			meanning.setText(bundle.getString("explain"));
			index = bundle.getInt("index");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String word = meanning.getText().toString().trim();
		String explain = zhushi.getText().toString().trim();
		if (v == cancel) {
			finish();
		}
		if (v == confirm) {
			if (meanning.getText().toString().equals("")
					|| zhushi.getText().toString().equals("")) {
				Toast.makeText(EditWord.this, "信息不能为空", Toast.LENGTH_SHORT)
						.show();
			}

			else {
				Intent intent = getIntent();
				arrayList = (ArrayList<HashMap<String, String>>) new ObjectFile()
						.readObjectFile();
				if (null == arrayList) {
					arrayList = new ArrayList<HashMap<String, String>>();
				}
				if (!TextUtils.isEmpty(word) && !TextUtils.isEmpty(explain)) {
					if (index == -1) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("word", word);
						map.put("explain", explain);
						arrayList.add(map);
						
					}else{
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("word", word);
						map.put("explain", explain);
						arrayList.set(index, map);
					}
					new ObjectFile().writeObjectFile(arrayList);
				}

				SimpleAdapter adapter = new SimpleAdapter(this, arrayList,
						android.R.layout.simple_list_item_2, new String[] {
								"word", "explain" }, new int[] {
								android.R.id.text1, android.R.id.text2 });
				Intent intent1 = new Intent(this, MainActivity.class);
				intent.putExtra("word", word);
				intent.putExtra("explain", explain);
				startActivity(intent1);
				EditWord.this.finish();
			}
		}
	}
}
