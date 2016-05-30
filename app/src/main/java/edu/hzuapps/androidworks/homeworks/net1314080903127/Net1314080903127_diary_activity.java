package edu.hzuapps.androidworks.homeworks.net1314080903127;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import edu.hzuapps.androidworks.net1314080903127.R;
public class Net1314080903127_diary_activity extends ListActivity {
	
	//回复的关键字
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	//菜单的选择
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private Net1314080903127_DbAdapter mDbHelper;
	private Cursor mDiaryCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903127_diary_list);
		mDbHelper = new Net1314080903127_DbAdapter(this);
		updateListView();

	}

	//更新当前的listacvitity
	private void updateListView() {
		mDbHelper.open();
		mDiaryCursor = mDbHelper.getAllNotes();
		//交给Activity管理游标
		startManagingCursor(mDiaryCursor);
		String[] from = new String[] { Net1314080903127_DbAdapter.KEY_TITLE,
				Net1314080903127_DbAdapter.KEY_CREATED };
		int[] to = new int[] { R.id.text1, R.id.created };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.net1314080903127_diary_row, mDiaryCursor, from, to);
		setListAdapter(notes);
		mDbHelper.closeclose();
	}

	//创建一个菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0,"新建").setIcon(R.drawable.net1314080903127_new_course);
		menu.add(0, DELETE_ID, 0, "删除").setIcon(R.drawable.net1314080903127_delete);
		return true;
	}

	//菜单选择
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createDiary();
			return true;
		case DELETE_ID:
			mDbHelper.open();
			mDbHelper.deleteDiary(getListView().getSelectedItemId());
			mDbHelper.closeclose();
			updateListView();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void createDiary() {
		Intent i = new Intent(this, Net1314080903127_DiaryEditActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override
	// 需要对position和id进行一个很好的区分
	// position指的是点击的这个ViewItem在当前ListView中的位置
	// 每一个和ViewItem绑定的数据，肯定都有一个id，通过这个id可以找到那条数据。
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		Intent i = new Intent(this, Net1314080903127_DiaryEditActivity.class);
		i.putExtra(Net1314080903127_DbAdapter.KEY_ROWID, id);
		i.putExtra(Net1314080903127_DbAdapter.KEY_TITLE, c.getString(c
				.getColumnIndexOrThrow(Net1314080903127_DbAdapter.KEY_TITLE)));
		i.putExtra(Net1314080903127_DbAdapter.KEY_BODY, c.getString(c
				.getColumnIndexOrThrow(Net1314080903127_DbAdapter.KEY_BODY)));
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		updateListView();
	}
}
