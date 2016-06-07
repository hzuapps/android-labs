package com.example.passwordbox;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AcountDetails extends Activity {
	private MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acount_details);
		Intent intent = getIntent();
		String acountId = intent.getStringExtra("acountId");
		// 从数据库获取acountId的Acount数据
		dbHelper = new MyDatabaseHelper(this, "PasswordBox.db", null, 2);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// 查询Acount表中所有的数据
		Cursor cursor = db.rawQuery(
				"select * from Acount where id="+acountId, null);
		if (cursor.moveToFirst()) {
			TextView acountLabel2 = (TextView) findViewById(R.id.acountLabel2);
			TextView userName2 = (TextView) findViewById(R.id.userName2);
			TextView password2 = (TextView) findViewById(R.id.password2);
			TextView notes2 = (TextView) findViewById(R.id.notes2);
			acountLabel2.setText(cursor.getString(cursor.getColumnIndex("acountLabel")));
			userName2.setText(cursor.getString(cursor.getColumnIndex("userName")));
			password2.setText(cursor.getString(cursor.getColumnIndex("password")));
			notes2.setText(cursor.getString(cursor.getColumnIndex("notes")));

		}
		cursor.close();

	}
}
