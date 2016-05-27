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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Net1314080903120editPage extends Activity {

	/////////////////////////////////////////////////////////////////////////定义基本控件
	RadioButton longperiod,middleperiod,shortperiod;
	RadioGroup periodpanel;
	EditText showtopic,showtime,showdate,showdetail;
	Spinner showpriority;
	ArrayAdapter aa;
	Button choosedate,choosetime,save,cancel;
	ProgressBar pb;
	Calendar c;
	/////////////////////////////////////////////////////////////////////////定义传递中间量
	String getperiod="",gettopic="",getdate="",gettime="",getpriority="",getdetail="";
	String itemID;
	String showperiod="short period";


	/////////////////////////////////////////////////////////////////////////定义数据库
	Net1314080903120dataBase data;
	Cursor cursor;


	private final static int DATE_DIALOG = 0;
	private final static int TIME_DIALOG = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903120editpage);
		pb=(ProgressBar)findViewById(R.id.progressBar1);
		setTitle("修改计划");
		Bundle getid=getIntent().getExtras();

		itemID=getid.getString("ID");                           //获得ID
/////////////////////////////////////////////////////////////////////////测试一下的
		Log.d("whether ID has got",itemID);
		data=new Net1314080903120dataBase(this);
		cursor=data.select(itemID);
		cursor.moveToFirst();


		showEditContent();                               //展示布局


	}
	////////////////////////////////////////////////////////////////////////////////初始化
	public void showEditContent()
	{
		longperiod=(RadioButton)findViewById(R.id.radioButton2);
		middleperiod=(RadioButton)findViewById(R.id.radioButton3);
		shortperiod=(RadioButton)findViewById(R.id.radioButton1);
		periodpanel=(RadioGroup)findViewById(R.id.radiogroup);

		showtopic=(EditText)findViewById(R.id.edittopic);
		showtime=(EditText)findViewById(R.id.edittime);
		showdate=(EditText)findViewById(R.id.editdate);
		showpriority=(Spinner)findViewById(R.id.spinner1);
		showdetail=(EditText)findViewById(R.id.xiangxi);
		choosedate=(Button)findViewById(R.id.choosedate);
		choosetime=(Button)findViewById(R.id.choosetime);
		save=(Button)findViewById(R.id.edit);

		if(cursor.getString(1).equals("长期计划"))
		{

			periodpanel.check(R.id.radioButton2);
			Log.d("show","long period");
		}
		if(cursor.getString(1).equals("中期计划"))
		{

			periodpanel.check(R.id.radioButton3);

			Log.d("show","middle period");
		}
		if(cursor.getString(1).equals("短期计划"))
		{

			periodpanel.check(R.id.radioButton1);


			Log.d("show","short period");


		}
		showperiod=cursor.getString(1);


		////////////////////////////////////////////////////////////////////////////////开始获取内容展示在布局上   
		periodpanel.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				//String result="";
				if(R.id.radioButton2==checkedId)
					showperiod=longperiod.getText().toString();
				else if(R.id.radioButton3==checkedId)
					showperiod=middleperiod.getText().toString();
				else if(R.id.radioButton1==checkedId)
					showperiod=shortperiod.getText().toString();

				// TODO Auto-generated method stub
				Log.d("show",showperiod);

			}
		});

		showtopic.setText(cursor.getString(2));
		showdate.setText(cursor.getString(3));
		choosedate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG);
			}
		});
		showtime.setText(cursor.getString(4));
		choosetime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG);

			}
		});
		showdetail.setText(cursor.getString(5));
		aa=ArrayAdapter.createFromResource(Net1314080903120editPage.this,R.array.setlevel,android.R.layout.simple_gallery_item); //将可选内容与ArrayAdapter连接起来
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //设置下拉列表的风格
		showpriority.setAdapter(aa);

		aa.notifyDataSetChanged();                      //通知spinner刷新数据      

		if(cursor.getString(6).equals("紧急"))      //设置某个选中项
			showpriority.setSelection(0);
		else if(cursor.getString(6).equals("重要"))
			showpriority.setSelection(1);
		else
		{
			Log.d("showpriority",cursor.getString(6));
			showpriority.setSelection(2);
		}

		showpriority.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				getpriority=showpriority.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		////////////////////////////////////////////////////////////////////////////////取消修改事件

		cancel=(Button)findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				AlertDialog.Builder cancelConfirm=new AlertDialog.Builder(Net1314080903120editPage.this);
				cancelConfirm.setTitle("还改么？还没保存呢");
				cancelConfirm.setPositiveButton("不改了", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent toboss=new Intent(Net1314080903120editPage.this,Net1314080903120Activity.class);
						startActivity(toboss);

					}
				});
				cancelConfirm.setNegativeButton("修改吧", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						pb.setVisibility(View.INVISIBLE);

					}
				});
				cancelConfirm.show();


			}
		});
		////////////////////////////////////////////////////////////////////////////////保存修改事件	
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				getperiod=showperiod;
				Log.d("getperiod",getperiod);
				gettopic=showtopic.getText().toString();
				getdate=showdate.getText().toString();                                    //date
				gettime=showtime.getText().toString();                                        //time
				getdetail=showdetail.getText().toString();                                  //detail
				data.update(itemID, getperiod, gettopic, getdate, gettime, getpriority, getdetail);
				Toast.makeText(getApplicationContext(),"已保存修改！",Toast.LENGTH_SHORT).show();
				Intent toboss=new Intent(Net1314080903120editPage.this,Net1314080903120Activity.class);
				startActivity(toboss);

			}
		});






	}


	////////////////////////////////////////////////////////////////////////////////重写消息框以获取日期时间的方法
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

	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
