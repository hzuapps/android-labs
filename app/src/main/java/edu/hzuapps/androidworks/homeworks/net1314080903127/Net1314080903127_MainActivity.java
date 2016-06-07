package edu.hzuapps.androidworks.homeworks.net1314080903127;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.hzuapps.androidworks.net1314080903127.R;
public class Net1314080903127_MainActivity extends Activity {
	
	//菜单选项
	public static final int HELP = Menu.FIRST;
	public static final int EXIT = Menu.FIRST + 1;
	public static final int SCORE = Menu.FIRST + 2; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903127_main);
        setTitle("大学生活管理助手");
        find_and_modify_button();
        Toast.makeText(Net1314080903127_MainActivity.this,  
                "欢迎使用大学生活管理助手", Toast.LENGTH_SHORT).show();
    }

	private void find_and_modify_button() {
		// TODO Auto-generated method stub
		Button btn_course = (Button) findViewById(R.id.btn_course);
		btn_course.setOnClickListener(course_listener);
		Button btn_diary = (Button) findViewById(R.id.btn_diary);
		btn_diary.setOnClickListener(diary_listener);
	}
	private Button.OnClickListener course_listener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Net1314080903127_MainActivity.this, Net1314080903127_course_activity.class);
			startActivity(intent);
		}
	};
	private Button.OnClickListener diary_listener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Net1314080903127_MainActivity.this, Net1314080903127_diary_activity.class);
			startActivity(intent);
		}
	};


	@Override
	/*
	 * menu.findItem(EXIT_ID);找到特定的MenuItem
	 * MenuItem.setIcon.可以设置menu按钮的背景
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, HELP, 0, "帮助").setIcon(R.drawable.net1314080903127_helps);
		menu.add(0, EXIT, 0, "退出").setIcon(R.drawable.net1314080903127_exit);
		menu.add(0, SCORE, 0, "评分").setIcon(R.drawable.net1314080903127_score);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case HELP: 
			 Intent help_intent = new Intent();
			 help_intent.setClass(Net1314080903127_MainActivity.this, Net1314080903127_help_activity.class);
			 startActivity(help_intent);
		break;
		case SCORE:
			 Intent score_intent = new Intent();
			 score_intent.setClass(Net1314080903127_MainActivity.this, Net1314080903127_score_activity.class);
			 startActivity(score_intent);
		break;
		case EXIT: 
			finish();
		 break;

		}
		return super.onOptionsItemSelected(item);
	}

}