package com.example.com1314080901203;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ContactDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME ="contact.db";
	
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	
	private SQLiteDatabase db;
	
	private static ContactDB contactdb;
	
	/**
	 * 将构造方法私有化
	 */
	private ContactDB(Context context) {
		Contactbase dbHelper = new Contactbase(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取ContactDB的实例。
	 */
	public synchronized static ContactDB getInstance(Context context) {
		if (contactdb == null) {
			contactdb = new ContactDB(context);
		}
		return contactdb;
	}
	
	/**
	 * 将ContactList实例存储到数据库。
	 */
	public void saveContactList(ContactList contactList){
		if(contactList!=null){
			ContentValues Values=new ContentValues();
			Values.put("id", contactList.getID());
			Values.put("objectnName", contactList.getName());
			Values.put("nickName", contactList.getNickname());
			
		}
	}
	
	/**
	 * 从数据库读取所有的联系人信息。
	 */
	public List<ContactList> loadContactList() {
		List<ContactList> list = new ArrayList<ContactList>();
		Cursor cursor = db
				.query("Contact", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				ContactList contactlist = new ContactList();
				contactlist.setID(cursor.getInt(cursor.getColumnIndex("id")));
				contactlist.setName(cursor.getString(cursor
						.getColumnIndex("objectnName")));
				contactlist.setNickname(cursor.getString(cursor
						.getColumnIndex("nickName")));
				list.add(contactlist);
			} while (cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}
}
