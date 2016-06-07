package com.example.passwordbox;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAcount extends Activity{
	private MyDatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_acount);
		dbHelper=new MyDatabaseHelper(this, "PasswordBox.db", null, 2);
		Button addCountBtn=(Button)findViewById(R.id.addCount);
		addCountBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				EditText userName = (EditText) findViewById(R.id.userName);
				EditText password = (EditText) findViewById(R.id.password);
				EditText notes = (EditText) findViewById(R.id.notes);
				EditText acountLabel = (EditText) findViewById(R.id.acountLabel);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("acountLabel", acountLabel.getText().toString());
				values.put("userName", password.getText().toString());
				values.put("password", userName.getText().toString());
				values.put("notes", notes.getText().toString());
				db.insert("Acount", null, values);
				Toast.makeText(AddAcount.this,"添加账号成功",Toast.LENGTH_LONG).show();
				Intent intent=new Intent(AddAcount.this,AcountListView.class);//显示Intent
				startActivity(intent);
			}
		});
	}
}
