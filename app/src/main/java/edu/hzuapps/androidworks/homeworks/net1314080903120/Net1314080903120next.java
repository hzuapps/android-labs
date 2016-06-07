package edu.hzuapps.androidworks.homeworks.net1314080903120;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Net1314080903120next extends Activity {


	/////////////////////////////////////////////////////////////////////////定义基本控件

	//Spinner sp;
	ArrayAdapter aa;
	ProgressBar pb;
	Spinner showpriority;
	Button choosedate,choosetime,finish,back;
	EditText showtopic,showtime,showdate,showdetail;
	Calendar c;
	/////////////////////////////////////////////////////////////////////////定义中间传递变量
	String getperiod="",gettopic="",getdate="",gettime="",getpriority="",getdetail="";
	/////////////////////////////////////////////////////////////////////////定义数据库
	Net1314080903120dataBase data;
	Cursor cursor;

	Dialog dialog=null;
	private final static int DATE_DIALOG = 0;
	private final static int TIME_DIALOG = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903120next);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
		setTitle("填写计划内容");
		data=new Net1314080903120dataBase(this);
		cursor=data.select();
		cursor.moveToFirst();
		/////////////////////////////////////////////////////////////////////////选择日期 		
		choosedate=(Button)findViewById(R.id.choosedate);
		choosedate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);
			}
		});


		/////////////////////////////////////////////////////////////////////////选择时间
		choosetime=(Button)findViewById(R.id.choosetime);
		choosetime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Calendar c=Calendar.getInstance();  //show the time
//				String st1=c.getTime().toString();
//				showtime.setText(st1);
				showDialog(TIME_DIALOG);

			}
		});

		/////////////////////////////////////////////////////////////////////////下拉菜单触发事件
		showpriority=(Spinner)findViewById(R.id.spinner1);
		aa=ArrayAdapter.createFromResource(Net1314080903120next.this,R.array.setlevel,android.R.layout.simple_dropdown_item_1line); //将可选内容与ArrayAdapter连接起来
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //设置下拉列表的风格
		showpriority.setAdapter(aa);
		showpriority.setVisibility(View.VISIBLE); //设置默认值 
		showpriority.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				getpriority=showpriority.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		/////////////////////////////////////////////////////////////////////////获取控件上的内容 准备储存

		showtopic=(EditText)findViewById(R.id.edittopic);

		showtime=(EditText)findViewById(R.id.edittime);
//		
		showdate=(EditText)findViewById(R.id.editdate);
//		
		showdetail=(EditText)findViewById(R.id.xiangxi);

		back=(Button)findViewById(R.id.cancel);
		/////////////////////////////////////////////////////////////////////////取消存储事件
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder cancelConfirm=new AlertDialog.Builder(Net1314080903120next.this);
				cancelConfirm.setTitle("确定回去么？还没保存呢");
				cancelConfirm.setPositiveButton("回去吧", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Intent turnback=new Intent(Net1314080903120next.this,Net1314080903120newproject.class);
						startActivity(turnback);

					}
				});
				cancelConfirm.setNegativeButton("继续填写", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						pb.setVisibility(View.INVISIBLE);

					}
				});
				cancelConfirm.show();




			}
		});
		/////////////////////////////////////////////////////////////////////////存入数据库
		finish=(Button)findViewById(R.id.finish);
		finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pb.setVisibility(View.VISIBLE);
				Bundle getperiodbundle=getIntent().getExtras();                                //阶段
				Log.d("check", getperiodbundle.getString("period"));
				getperiod=getperiodbundle.getString("period");
				gettopic=showtopic.getText().toString();                                   //主题	
				getdate=showdate.getText().toString();                                    //日期
				gettime=showtime.getText().toString();                                        //时间
				getdetail=showdetail.getText().toString();                                  //备注
				String state="未完成";

				add(getperiod, gettopic, getdate, gettime, getpriority, getdetail, state);

				Toast.makeText(getApplicationContext(),"新计划已建立，加油完成吧！",Toast.LENGTH_SHORT).show();


				Intent toboss=new Intent(Net1314080903120next.this,Net1314080903120Activity.class);   //准备返回首页
				startActivity(toboss);



			}
		});


	}

	public void add(String period,String topic,String date,String time,
					String priority,String detail,String state)
	{
		if(period.equals("")||topic.equals("")||date.equals("")||time.equals("")||priority.equals(""))
		{
			return;
		}
		else
		{
			data.insert(period, topic, date, time, priority, detail, state);
			cursor.requery();
		}
	}

	/////////////////////////////////////////////////////////////////////////获取日期 时间的方法 重写消息框

	@Override
	protected Dialog onCreateDialog(int id) {                 //overwrite the oncreate method
		Dialog dialog = null;
		c = Calendar.getInstance();
		switch (id) {
			case DATE_DIALOG:

				dialog = new DatePickerDialog(
						this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(DatePicker dp, int year,int month, int dayOfMonth) {
								showdate.setText( year + "年" + (month+1) + "月" + dayOfMonth + "日");
							}
						},
						c.get(Calendar.YEAR), // 传入年份
						c.get(Calendar.MONTH), // 传入月份
						c.get(Calendar.DAY_OF_MONTH) // 传入天数
				);
				dialog.setTitle(c.get(Calendar.YEAR)+","+c.get(Calendar.MONTH)+","+c.get(Calendar.DAY_OF_MONTH));
				break;
			case TIME_DIALOG:
				dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

						showtime.setText(hourOfDay+":"+minute);
					}
				}, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), true);

				break;


		}
		return dialog;
	}



}
