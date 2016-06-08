package models;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import db.DBHelper_Net1314080903221;

public class MyPackage_Net1314080903221 {
	float income_sum;
	float consume_sum;
	private DBHelper_Net1314080903221 dbhelper;
	private SQLiteDatabase db;
	private Context context;
	//public 
	public MyPackage_Net1314080903221(Context context){
		this.context=context;
		dbhelper=new DBHelper_Net1314080903221(context);
	}
	public float getincome(){
		return income_sum;
	} 
	
	public float getconsume(){
		return consume_sum;
	}
	public List<TradeClass_Net1314080903221> getAlltrade(){
		List<TradeClass_Net1314080903221> TradeList=new ArrayList<TradeClass_Net1314080903221>();
		db=dbhelper.getReadableDatabase();
		Cursor cu=db.rawQuery("select * from tb_outaccount", null);
		while(cu.moveToNext()){
			TradeList.add(new consumeClass_Net1314080903221(cu.getInt(cu.getColumnIndex("_id")),cu.getFloat(cu.getColumnIndex("money")),
					cu.getString(cu.getColumnIndex("addTime")),cu.getString(cu.getColumnIndex("mark")),cu.getString(cu.getColumnIndex("pocketType")),
					context));	
		}
		cu=db.rawQuery("select * from tb_inaccount", null);
		while(cu.moveToNext()){
			TradeList.add(new incomeClass_Net1314080903221(cu.getInt(cu.getColumnIndex("_id")),cu.getFloat(cu.getColumnIndex("money")),
					cu.getString(cu.getColumnIndex("addTime")),cu.getString(cu.getColumnIndex("mark")),cu.getString(cu.getColumnIndex("pocketType")),
					context));
		}
		return TradeList;
	}
}
