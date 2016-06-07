package com.example.passwordbox;

import android.app.Activity;
import android.os.Bundle;

public class CreateDb extends Activity{
	
	private MyDatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901208);
		//创建数据库
		dbHelper=new MyDatabaseHelper(this, "PassWordBox.db", null, 1);
		dbHelper.getWritableDatabase();
	}
}
