/**
 * 
 */
package edu.hzuapps.androidworks.homeworks.net1314080903127;

import java.sql.Date;
import java.util.Calendar;

import edu.hzuapps.androidworks.homeworks.net1314080903127.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class Net1314080903127_course_activity extends ListActivity {
	
	//菜单选项
	public static final int NEW = Menu.FIRST;
	public static final int SET = Menu.FIRST + 1;
	public static final int DELETE = Menu.FIRST + 2;
	public static final int HELP = Menu.FIRST + 3;
	
	//传值确认的关键字
	private static final int REQUEST_SET = 0;
	private static final int REQUEST_NEW = 1;
	
	//默认的第一周开始时间
	int first_year = 2010;
	int first_month = 9;
	int first_day = 1;	
	Date start_date = new Date(first_year,first_month,first_day);
	
	//新建的课程信息
	String course_name = "";
	String week_start  = "";
	String week_end  = "";
	String course_index1  = "";
	String course_place  = "";
	String week_index  = "";
	
	//当前日期
	Calendar c = Calendar.getInstance();
	int now_year = c.get(Calendar.YEAR);
	int now_month = c.get(Calendar.MONTH);
	int now_day = c.get(Calendar.DAY_OF_MONTH);
	Date now_date = new Date(now_year,now_month,now_day);
	
	//现在是第几周
	int interval_weeks = 1;
	
	//数据库操作
	private Net1314080903127_DbAdapter mDbHelper;
	private Cursor mCourseCursor;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("本周课程信息");
		setContentView(R.layout.net1314080903127_course_activity);
		
		mDbHelper = new Net1314080903127_DbAdapter(this);
		updateCourseView();
	}
	/**
	 * 更新listactivity的数据
	 */
	private void updateCourseView() {
		// TODO Auto-generated method stub
		Log.e("done", "getcourse");
		mDbHelper.open();
		mCourseCursor = mDbHelper.getAllCourses(interval_weeks);
        Toast.makeText(Net1314080903127_course_activity.this,  
                "当前是第"+interval_weeks +"周,点击menu设置", Toast.LENGTH_SHORT).show();
		Log.e("weeks"," " +interval_weeks);
		Log.e("done", "donegetcourse");
		setTitle("第"+interval_weeks +"周 "+"课程信息");
		startManagingCursor(mCourseCursor);
		
		String[] from = new String[] { Net1314080903127_DbAdapter.KEY_NAME, Net1314080903127_DbAdapter.KEY_PLACE, Net1314080903127_DbAdapter.KEY_INDEX, Net1314080903127_DbAdapter.KEY_WEEK_INDEX};
		int[] to = new int[] { R.id.item_name, R.id.item_place, R.id.item_index, R.id.item_week_index };
		SimpleCursorAdapter courses = new SimpleCursorAdapter(this,
				R.layout.net1314080903127_course_list_item, mCourseCursor, from, to);
		setListAdapter(courses);
		mDbHelper.closeclose();
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 menu.add(0, NEW, 0, "新建").setIcon(R.drawable.net1314080903127_new_course);
		menu.add(0, SET, 0, "设置").setIcon(R.drawable.net1314080903127_setting);
		menu.add(0, DELETE, 0, "删除").setIcon(R.drawable.net1314080903127_delete);
		menu.add(0, HELP, 0, "帮助").setIcon(R.drawable.net1314080903127_helps);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case HELP: 
			 Intent help_intent = new Intent();
			 help_intent.setClass(Net1314080903127_course_activity.this, Net1314080903127_help_activity.class);
			 startActivity(help_intent);
			 return true;
		case NEW:
			 Intent new_course_intent = new Intent();
			 new_course_intent.setClass(Net1314080903127_course_activity.this, Net1314080903127_course_new_activity.class);
			 startActivityForResult(new_course_intent, REQUEST_NEW);
			 return true;
		case SET: 
			Intent set_intent = new Intent();
			set_intent.setClass(Net1314080903127_course_activity.this, Net1314080903127_course_set_activity.class);
			startActivityForResult(set_intent, REQUEST_SET);
			 return true;
		case DELETE:
			mDbHelper.open();
			mDbHelper.deleteCourse(getListView().getSelectedItemId());
			mDbHelper.closeclose();
			updateCourseView();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//得到现在是第几周
	private int get_interval_weeks(Date ds, Date de)
	{
		   long total = (de.getTime()-ds.getTime())/(24*60*60*1000);
			Log.e("total"," " +total);
		   return ((int)(total/7.0) + 1);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		//设置第一周的回复
		if(requestCode == REQUEST_SET)
		{
			if(resultCode == RESULT_OK)
			{
				Bundle extras = data.getExtras();
				if(extras != null)
				{
					first_year = extras.getInt("year");
					first_month = extras.getInt("month");
					first_day = extras.getInt("day");
					start_date = new Date(first_year,first_month,first_day);
					Log.e("now_month"," " +now_month);
					Log.e("now_day"," " +now_day);
					interval_weeks = get_interval_weeks(start_date, now_date);
			        Toast.makeText(Net1314080903127_course_activity.this,  
			                "当前是第"+interval_weeks +"周", Toast.LENGTH_LONG).show();
					setTitle("第"+interval_weeks +"周 "+"课程信息");
					Log.e("weeks"," " +interval_weeks);
				}
				
			}
		}
		
		//添加新课程信息的回复
		else if(requestCode == REQUEST_NEW)
		{
			if(resultCode == RESULT_OK)
			{
				updateCourseView();
			}
		}
	}
}





