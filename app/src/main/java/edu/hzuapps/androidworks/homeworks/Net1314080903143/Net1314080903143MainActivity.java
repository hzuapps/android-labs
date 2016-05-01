package edu.hzuapps.androidworks.homeworks.Net1314080903143;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import edu.hzuapps.androidworks.homeworks.com1314080903143.R;

/**
 * @author jinyan
 * @download http://www.codefans.net
 */
public class Net1314080903143MainActivity extends ListActivity {
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;

	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DiaryDbAdapter mDbHelper;
	private Cursor mDiaryCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary_list);
		mDbHelper = new DiaryDbAdapter(this);
		mDbHelper.open();
		renderListView();

	}

	private void renderListView() {
		mDiaryCursor = mDbHelper.getAllNotes();
		startManagingCursor(mDiaryCursor);
		String[] from = new String[] { DiaryDbAdapter.KEY_TITLE,
				DiaryDbAdapter.KEY_CREATED };
		int[] to = new int[] { R.id.text1, R.id.created };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.diary_row, mDiaryCursor, from, to);
		setListAdapter(notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			createDiary();
			return true;
		case DELETE_ID:
			mDbHelper.deleteDiary(getListView().getSelectedItemId());
			renderListView();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void createDiary() {
		Intent i = new Intent(this, ActivityDiaryEdit.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override
	// ��Ҫ��position��id����һ���ܺõ�����
	// positionָ���ǵ�������ViewItem�ڵ�ǰListView�е�λ��
	// ÿһ����ViewItem�󶨵����ݣ��϶�����һ��id��ͨ�����id�����ҵ��������ݡ�
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		Intent i = new Intent(this, ActivityDiaryEdit.class);
		i.putExtra(DiaryDbAdapter.KEY_ROWID, id);
		i.putExtra(DiaryDbAdapter.KEY_TITLE, c.getString(c
				.getColumnIndexOrThrow(DiaryDbAdapter.KEY_TITLE)));
		i.putExtra(DiaryDbAdapter.KEY_BODY, c.getString(c
				.getColumnIndexOrThrow(DiaryDbAdapter.KEY_BODY)));
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		renderListView();
	}
}
