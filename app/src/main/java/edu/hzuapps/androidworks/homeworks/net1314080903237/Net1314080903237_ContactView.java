package edu.hzuapps.androidworks.homeworks.net1314080903237;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Net1314080903237_ContactView extends Activity
{
	private TextView mTextViewName;
	private TextView mTextViewMobile;
	private TextView mTextViewHome;
	private TextView mTextViewAddress;
	private TextView mTextViewEmail;
	private TextView mTextViewBlog;
	
    private Cursor mCursor;
    private Uri mUri;
    
    private static final int REVERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    private static final int EDITOR_ID = Menu.FIRST + 2;
    private static final int CALL_ID = Menu.FIRST + 3;
    private static final int SENDSMS_ID = Menu.FIRST + 4;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mUri = getIntent().getData();
		
		this.setContentView(R.layout.viewuser);
		
		mTextViewName = (TextView) findViewById(R.id.TextView_Name);
		mTextViewMobile = (TextView) findViewById(R.id.TextView_Mobile);
		mTextViewHome = (TextView) findViewById(R.id.TextView_Home);
		mTextViewAddress = (TextView) findViewById(R.id.TextView_Address);
		mTextViewEmail = (TextView) findViewById(R.id.TextView_Email);
		mTextViewBlog = (TextView) findViewById(R.id.TextView_Blog);
		
	    //获得并保存原始联系人信息
        mCursor = managedQuery(mUri, Net1314080903237_ContactColumn.PROJECTION, null, null, null);
        mCursor.moveToFirst();
	}
	
    protected void onResume()
	{
		super.onResume();
		if (mCursor != null)
		{
			//读取并显示联系人信息
			mCursor.moveToFirst();
			
			mTextViewName.setText(mCursor.getString(Net1314080903237_ContactColumn.NAME_COLUMN));
			mTextViewMobile.setText(mCursor.getString(Net1314080903237_ContactColumn.MOBILENUM_COLUMN));
			mTextViewHome.setText(mCursor.getString(Net1314080903237_ContactColumn.HOMENUM_COLUMN));
			mTextViewAddress.setText(mCursor.getString(Net1314080903237_ContactColumn.ADDRESS_COLUMN));
			mTextViewEmail.setText(mCursor.getString(Net1314080903237_ContactColumn.EMAIL_COLUMN));
			mTextViewBlog.setText(mCursor.getString(Net1314080903237_ContactColumn.BLOG_COLUMN));
		}
		else
		{
			setTitle("错误信息");
		}
	}
    
    public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		//添加菜单
		menu.add(0, REVERT_ID, 0, R.string.revert).setShortcut('0', 'r');
		menu.add(0, DELETE_ID, 0, R.string.delete_user).setShortcut('0', 'd');
		menu.add(0, EDITOR_ID, 0, R.string.editor_user).setShortcut('0', 'd');
		return true;
	}
    
    public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			//删除
			case DELETE_ID:
				deleteContact();
				finish();
				break;
			//返回列表
			case REVERT_ID:
				setResult(RESULT_CANCELED);
				finish();
				break;
			case EDITOR_ID:
			//编辑联系人
				startActivity(new Intent(Intent.ACTION_EDIT, mUri)); 
				break;

		}
		return super.onOptionsItemSelected(item);
	}


	//删除联系人信息
	private void deleteContact()
	{
		if (mCursor != null)
		{
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);
			setResult(RESULT_CANCELED);
		}
	}
}

