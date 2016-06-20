package com.example.dictionary;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NoteBook extends Activity {
	public static final int MENU_ADD = 1;
	public static final int MENU_TUICHU = MENU_ADD + 1;
	private ArrayList<HashMap<String, String>> arrayList;
	private ListView listView1;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_book);

		listView1 = (ListView) findViewById(R.id.listView1);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getIntent();
		final String word = intent.getStringExtra("word");
		final String explain = intent.getStringExtra("explain");
		arrayList = (ArrayList<HashMap<String, String>>) new ObjectFile()
				.readObjectFile();
		if (null == arrayList) {
			arrayList = new ArrayList<HashMap<String, String>>();
		}
		if (!TextUtils.isEmpty(word) && !TextUtils.isEmpty(explain)) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("word", word);
			map.put("explain", explain);
			arrayList.add(map);
			new ObjectFile().writeObjectFile(arrayList);
		}

		final SimpleAdapter adapter = new SimpleAdapter(this, arrayList,
				android.R.layout.simple_list_item_2, new String[] { "word",
						"explain" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		listView1.setAdapter(adapter);

		// 为listView1视图添加setOnItemClickListener监听
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// 对于选中的项进行处理
				AlertDialog.Builder builder = new Builder(NoteBook.this);
				builder.setIcon(R.drawable.dialog_icon);
				builder.setMessage("确认操作");
				builder.setTitle("提示");
				builder.setPositiveButton("删除", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						arrayList.remove(arg2);
						new ObjectFile().writeObjectFile(arrayList);
						adapter.notifyDataSetChanged();
						dialog.dismiss();

					}
				});

				builder.setNeutralButton("编辑", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
//						Intent intent = new Intent();
//						Bundle bundle = new Bundle();	
//						bundle.putString("word", word);
//						bundle.putString("explain", explain);
//						intent.putExtras(bundle);
//						intent.setClass(NoteBook.this, EditWord.class);
//						startActivity(intent);
//						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", null).show();
				// TODO Auto-generated method stub

			}

		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, MENU_ADD, 0, "添加新单词");
		menu.add(0, MENU_TUICHU, 1, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD: {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("action", "add");
			intent.putExtras(bundle);
			intent.setClass(this, EditWord.class);
			startActivity(intent);
			break;
		}
		case MENU_TUICHU: {
			finish();
		}
		}
		return super.onOptionsItemSelected(item);
	}

}
