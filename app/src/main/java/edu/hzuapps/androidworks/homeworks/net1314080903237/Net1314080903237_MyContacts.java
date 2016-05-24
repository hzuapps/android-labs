package com.example.mense.net1314080903237;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Net1314080903237_MyContacts extends ListActivity
// 浏览联系人时，如果没有选中一条联系人，那么就不能使用删除、修改、查看等菜单。
// 但是当选中一条数据时，就可以执行这些功能，所以我们要使用动态菜单，根据不同的状态显示不同的菜单。
// 动态菜单要在Android中使用Intent来设置ACTION,然后根据不同的动作来启动不同的界面Activity
{
	private static final String TAG = "Net1314080903237_MyContacts";

	private static final int AddContact_ID = Menu.FIRST;
	private static final int EditContact_ID = Menu.FIRST+1;
	private static final int DELEContact_ID = Menu.FIRST+2;
	private static final int EXITContact_ID = Menu.FIRST+3;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
		
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Net1314080903237_ContactsProvider.CONTENT_URI);
        }

        getListView().setOnCreateContextMenuListener(this);
        getListView().setBackgroundResource(R.drawable.view_bg);

        Cursor cursor = managedQuery(getIntent().getData(), Net1314080903237_ContactColumn.PROJECTION, null, null,null);
//注册每个列表表示形式:姓名+移动电话
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
			android.R.layout.simple_list_item_2,
			cursor,
			new String[] {Net1314080903237_ContactColumn.NAME, Net1314080903237_ContactColumn.MOBILENUM },
			new int[] { android.R.id.text1, android.R.id.text2 });

        setListAdapter(adapter);
	}

    public boolean onCreateOptionsMenu(Menu menu) 
    {//添加动态菜单“编辑”，“查看”的方法
        super.onCreateOptionsMenu(menu);
		//添加联系人
        menu.add(0, AddContact_ID, 0, R.string.add_user)
        	.setShortcut('3', 'a');
        
        Intent intent = new Intent(null, getIntent().getData());
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0,
                new ComponentName(this, Net1314080903237_MyContacts.class), null, intent, 0, null);
		//退出程序
        menu.add(0, EXITContact_ID, 0, R.string.exit)
    		.setShortcut('4', 'd');
        return true;
        
    }

    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case AddContact_ID:
            startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
            return true;
        case EXITContact_ID:
        	this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public boolean onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		final boolean haveItems = getListAdapter().getCount() > 0;

		if (haveItems)
		{
			
			Uri uri = ContentUris.withAppendedId(getIntent().getData(), getSelectedItemId());

			Intent[] specifics = new Intent[2];
			specifics[0] = new Intent(Intent.ACTION_EDIT, uri);
			specifics[1] = new Intent(Intent.ACTION_VIEW, uri);
			MenuItem[] items = new MenuItem[2];
			//添加满足条件的条件
			Intent intent = new Intent(null, uri);
			intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
			menu.addIntentOptions(Menu.CATEGORY_ALTERNATIVE, 0, 0, null, specifics, intent, 0, items);

			if (items[0] != null)
			{
				items[0].setShortcut('1', 'e').setTitle(R.string.editor_user);
				//编辑联系人
			}
			if (items[1] != null)
			{
				items[1].setShortcut('2', 'f').setTitle(R.string.view_user);
				//查看联系人
			}
		}
		else
		{
			menu.removeGroup(Menu.CATEGORY_ALTERNATIVE);
		}
		return true;
	}

    protected void onListItemClick(ListView l, View v, int position, long id)   
    {   //动态菜单处理，点击的默认操作也可以在这里处理
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);   
  
        String action = getIntent().getAction();   
        if ( Intent.ACTION_EDIT.equals(action) )
		{
        	startActivity(new Intent(Intent.ACTION_EDIT, uri));  //编辑联系人
		}  
        else
        {
        	startActivity(new Intent(Intent.ACTION_VIEW, uri)); 	//查看联系人
        }  
    }   

	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
	{// 触发的菜单。这时可以设置能够进行操作的菜单，通过onContextItemSelected方法监听菜单的事件处理
		AdapterView.AdapterContextMenuInfo info;
		try
		{
			info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		}
		catch (ClassCastException e)
		{
			return;
		}
		Cursor cursor = (Cursor) getListAdapter().getItem(info.position);//得到的数据项
		if (cursor == null)
		{
			return;
		}

		menu.setHeaderTitle(cursor.getString(1));
		menu.add(0, DELEContact_ID, 0, R.string.delete_user);//添加删除菜单
	}
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
	{//本例中时会弹出删除该记录的菜单
		AdapterView.AdapterContextMenuInfo info;
		try
		{
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		}
		catch (ClassCastException e)
		{
			return false;
		}

		switch (item.getItemId())
		{
			case DELEContact_ID:
			{
				Uri noteUri = ContentUris.withAppendedId(getIntent().getData(), info.id);
				getContentResolver().delete(noteUri, null, null);
				return true;
			}
		}
		return false;
	}
}
