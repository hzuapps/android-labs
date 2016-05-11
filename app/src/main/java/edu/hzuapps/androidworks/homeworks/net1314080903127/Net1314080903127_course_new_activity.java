/**
 * 
 */
package edu.hzuapps.androidworks.homeworks.net1314080903127;

import edu.hzuapps.androidworks.homeworks.net1314080903127;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Net1314080903127_course_new_activity extends Activity {
	
	//数据库操作
	private Net1314080903127_DbAdapter mDbHelper;
	
	//课程信息的默认声明
	String course_name = "default";;
	String week_start = "1";
	String week_end = "15";
	String course_index = "1";
	String week_index = "周一";
	CharSequence course_place = "教学楼";
	
	//TextWatcher place_watcher;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mDbHelper = new Net1314080903127_DbAdapter(this);
		
		setContentView(R.layout.net1314080903127_course_new);
		setTitle("新建一个课程信息");
		
		//课程名自动完成输入框
		AutoCompleteTextView course_name_autocompelete = (AutoCompleteTextView) findViewById(R.id.course_input_autocompelte);
		course_name_autocompelete.setThreshold(1);//设置输入一个字符有提示
		String[] course_name_array = getResources().getStringArray(R.array.course_name_array);
		final ArrayAdapter<String> name_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, course_name_array);
		course_name_autocompelete.setAdapter(name_adapter);
		
		//得到课程内容
		
		course_name_autocompelete.setOnItemClickListener(new OnItemClickListener() {
			
		    @Override 
		    public void onItemClick(AdapterView parent, View view, int position, long id) { 	 
		    	//course_name = (String) adapter.getItemAtPosition(arg2).toString();
		    	course_name = name_adapter.getItem(position); 
		    };
		});
		
		//course_name = "software";
		//确认按钮
		Button btn_course_new_confirm = (Button) findViewById(R.id.btn_course_new_confirm);
		btn_course_new_confirm.setOnClickListener(btn_confirm_listener);
		
		//周次选择  “周”适配器
		final ArrayAdapter<CharSequence> week_adapter = ArrayAdapter.createFromResource(
				this, R.array.week_array, android.R.layout.simple_spinner_dropdown_item);
		week_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//开始周
		Spinner course_time_start_spinner = (Spinner) findViewById(R.id.course_time_start_spinner);		
		course_time_start_spinner.setAdapter(week_adapter);
		
		
		OnItemSelectedListener start_listener = new  OnItemSelectedListener(){  
		    	//week_start = week_adapter.getItemAtPosition(arg2).toString(); 
		    	
		    	 public void onItemSelected(AdapterView<?> adapter,View v,   
		                 int pos, long id) {   
		    		 week_start = week_adapter.getItem(pos).toString();
		         }   

		         public void onNothingSelected(AdapterView<?> arg0) {   
		             // TODO Auto-generated method stub   
		               week_start = "1";
		         }   
		};
		 course_time_start_spinner.setOnItemSelectedListener(start_listener);
		
		
				
		//结束周
		Spinner course_time_end_spinner = (Spinner) findViewById(R.id.course_time_end_spinner);
		course_time_end_spinner.setAdapter(week_adapter);
		
		OnItemSelectedListener end_listener = new  OnItemSelectedListener(){  
	    	//week_start = week_adapter.getItemAtPosition(arg2).toString(); 
	    	
	    	 public void onItemSelected(AdapterView<?> adapter,View v,   
	                 int pos, long id) {   
	    		 week_end =  week_adapter.getItem(pos).toString();
	         }   

	         public void onNothingSelected(AdapterView<?> arg0) {   
	             // TODO Auto-generated method stub   
	        	 week_end= "1";
	         }   
		};
		course_time_end_spinner.setOnItemSelectedListener(end_listener);
		
		//课程节次选择
		Spinner course_index_spinner = (Spinner) findViewById(R.id.course_index_spinner);
		course_index_spinner.setAdapter(week_adapter);
		
		OnItemSelectedListener index_listener = new  OnItemSelectedListener(){  
	    	//week_start = week_adapter.getItemAtPosition(arg2).toString(); 
	    	
	    	 public void onItemSelected(AdapterView<?> adapter,View v,   
	                 int pos, long id) {   
	    		 course_index =  "第" + week_adapter.getItem(pos).toString() + "节";
	         }   

	         public void onNothingSelected(AdapterView<?> arg0) {   
	             // TODO Auto-generated method stub   
	        	 course_index= "1";
	         }   
		};
		course_index_spinner.setOnItemSelectedListener(index_listener);
		
		//课程周次
		final ArrayAdapter<CharSequence> week_index_adapter = ArrayAdapter.createFromResource(
				this, R.array.week_index_array, android.R.layout.simple_spinner_dropdown_item);
		week_index_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner week_index_spinner = (Spinner) findViewById(R.id.week_index_spinner);
		week_index_spinner.setAdapter(week_index_adapter);
		
		OnItemSelectedListener week_index_spinner_listener = new  OnItemSelectedListener(){  
	    	//week_start = week_adapter.getItemAtPosition(arg2).toString(); 
	    	
	    	 public void onItemSelected(AdapterView<?> adapter,View v,   
	                 int pos, long id) {   
	    		 week_index =  week_index_adapter.getItem(pos).toString();
	         }   

	         public void onNothingSelected(AdapterView<?> arg0) {   
	             // TODO Auto-generated method stub   
	        	 week_index= "周一";
	         }   
		};
		week_index_spinner.setOnItemSelectedListener(week_index_spinner_listener);
		
		
		//place   课程地点
		EditText course_place_edittext = (EditText)findViewById(R.id.course_place_edittext);
		course_place = course_place_edittext.getText();
		
		Log.e("place", course_place.toString());
	}

	//按钮监听函数
	private Button.OnClickListener btn_confirm_listener = new OnClickListener()
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setTitle(course_name + "\\" + course_place+ "\\"  +week_start+ "\\"+week_end + "\\"+ course_index);
			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			Log.e("name", ""+course_name);
			Log.e("start", week_start);
			Log.e("end", week_end);
			Log.e("index", course_index);
			Log.e("place", course_place.toString());
			//将课程信息添加到数据库
			mDbHelper.open();
			mDbHelper.createCourse(course_name, Integer.parseInt(week_start), Integer.parseInt(week_end), course_index, course_place.toString(),week_index);
			mDbHelper.closeclose();
			finish();
		}
	};
}



