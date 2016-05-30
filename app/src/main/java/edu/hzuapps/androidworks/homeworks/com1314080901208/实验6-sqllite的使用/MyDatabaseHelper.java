package com.example.passwordbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper{

	public static final String CREATE_Login="create table Login ("
            + "id integer primary key autoincrement, "
            + "userName text, "
            + "password text) ";
	public static final String CREATE_Acount="create table Acount ("
            + "id integer primary key autoincrement, "
			+ "acountLabel text, "
            + "userName text, "
            + "password text," 
            + "notes text) ";
	private Context myContext;
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		myContext=context;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_Login);
		db.execSQL(CREATE_Acount);
		Toast.makeText(myContext, "Create Login And Acount table Succeed", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	

}
