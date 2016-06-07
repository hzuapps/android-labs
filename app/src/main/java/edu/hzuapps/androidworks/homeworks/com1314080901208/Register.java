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

public class Register extends Activity{
	private MyDatabaseHelper dbHelper;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbHelper = new MyDatabaseHelper(this, "PasswordBox.db", null, 2);
		Button registerBtn = (Button) findViewById(R.id.login);
		registerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

		EditText userName = (EditText) findViewById(R.id.userName);
		EditText password = (EditText) findViewById(R.id.password);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("userName", password.getText().toString());
				values.put("password", userName.getText().toString());
				db.insert("Login", null, values);
				Toast.makeText(Register.this, "×¢²á³É¹¦", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(Register.this,Com1314080901208.class);
				 startActivity(intent);
			}
		});
	}
	
}
