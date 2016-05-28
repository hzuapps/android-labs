package edu.hzuapps.androidworks.homeworks.net1314080903220;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class RSSList extends ListActivity {
	private static final int Menu_item1 = Menu.FIRST;
	private static final int Menu_item2 = Menu_item1 + 1;
	private static final int Menu_item3 = Menu_item2 + 1;

	private int selected = -1;
	private int curID = 0;
	private String link = null;

	private SharedPreferences sPreferences;
	private RSS_listManager rManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_list);
		init();

		sPreferences = getSharedPreferences("list_info", 0);
		selected = sPreferences.getInt("LIST_ID", -1);
		curID = selected;
		System.out.println("onCreate :" + curID);
		UpdateList();
	}

	public void init() {
		rManager = new RSS_listManager(this);
		rManager.Open();
	}


	public void UpdateList() {
		Cursor cursor = rManager.selectDate();
		SimpleCursorAdapter sAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_single_choice, cursor,
				new String[] { edu.hzuapps.androidworks.homeworks.net1314080903220.RSS_mlist.title },
				new int[] { android.R.id.text1 });
		setListAdapter(sAdapter);
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		this.getListView().setItemChecked(selected, true);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// sPreferences = getSharedPreferences("list_info", 0);
		// sPreferences.edit().putInt("LIST_ID",
		// selected).putLong("LIST_ITEMID",
		// curItemID).putString("LIST_LINK", link).commit();
		rManager.Close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Menu_item1, 0, R.string.item3);
		menu.add(0, Menu_item2, 0, R.string.item4);
		menu.add(0, Menu_item3, 0, R.string.item5);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		Cursor cursor = null;
		sPreferences = getSharedPreferences("list_info", 0);
		Long id = sPreferences.getLong("LIST_ITEMID", 1);
		switch (item.getItemId()) {
			case Menu_item1:

				intent = new Intent(edu.hzuapps.androidworks.homeworks.net1314080903220.RSSList.this, RSS_listItem.class);
				intent.setAction("com.lq.addlistitem");
				startActivity(intent);
				break;
			case Menu_item2:

				cursor = rManager.selectDate(id);
				intent = new Intent(edu.hzuapps.androidworks.homeworks.net1314080903220.RSSList.this, RSS_listItem.class);
				Bundle bundle = new Bundle();
				bundle.putInt(edu.hzuapps.androidworks.homeworks.net1314080903220.RSS_mlist._ID, cursor.getInt(0));
				bundle.putString(edu.hzuapps.androidworks.homeworks.net1314080903220.RSS_mlist.title, cursor.getString(1));
				bundle.putString(RSS_mlist.link, cursor.getString(2));
				intent.putExtra("com.lq.editlistitem", bundle);
				startActivity(intent);
				break;
			case Menu_item3:

				rManager.deleteDate(id);
				UpdateList();

				selected = selected-1;
				this.getListView().setItemChecked(selected, true);

				cursor = rManager.selectDateDesc();
				sPreferences.edit().putInt("LIST_ID", selected).putLong(
						"LIST_ITEMID", cursor.getLong(0)).putString("LIST_LINK",
						cursor.getString(2)).commit();
				break;

			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

		@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (selected != -1) {
			View view = this.getListView().getChildAt(position);
			((CheckedTextView) view).setChecked(false);
		}
		((CheckedTextView) v).setChecked(true);
		selected = position;
		Cursor cursor = rManager.selectDate(id);
		link = cursor.getString(2);

		sPreferences = getSharedPreferences("list_info", 0);
		sPreferences.edit().putInt("LIST_ID", position).putLong("LIST_ITEMID",
				id).putString("LIST_LINK", link).commit();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		sPreferences = getSharedPreferences("list_info", 0);
		selected = sPreferences.getInt("LIST_ID", -1);
		curID = selected;
		System.out.println(selected);
		UpdateList();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (curID != selected) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
			}
			this.finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
