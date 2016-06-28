package com.example.com1314080901203;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class Sender extends Fragment  {
	EditText editObj,editDate,editTime,editMsg;
	Button comfirm;
	String objString;
	String dateString;
	String timeString;
	String msgString;
	Button confirm;
	private MydatabaseHelper dbHelper;
	
	@Override
	  public  void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);
	        confirm=(Button)getActivity().findViewById(R.id.confirm);
	        Log.i("www","fourteen");
	        dbHelper=new MydatabaseHelper(getActivity(), "Setting.db", null, 1);
	        confirm.setOnClickListener(new OnClickListener(){
	    		// TODO Auto-generated method stub
	    		   public void onClick(View v) {
	    			   editSetTimer();//按confirm键确认，存储数据
	    		   }
	    	});
	        Log.i("www","eighteen");
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sender, container, false);
        editObj=(EditText)view.findViewById(R.id.editObj);
        Log.i("www","tenth");
        editDate=(EditText)view.findViewById(R.id.editDate);
        Log.i("www","eleven");
       editTime=(EditText)view.findViewById(R.id.editTime);
        Log.i("www","twelve");
        editMsg=(EditText)view.findViewById(R.id.editMsg);
        Log.i("www","thirteen");
        return view;
    }
	
	/*存储数据
	 * */
	private void editSetTimer(){
		// TODO Auto-generated method stub
		Log.i("www","这里1");
		objString =  editObj.getText().toString();
		dateString =  editDate.getText().toString();
		timeString =  editTime.getText().toString();
		msgString =  editMsg.getText().toString();
		Log.i("www","这里2");
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("objString", objString);
		values.put("dateString", dateString);
		values.put("timeString", timeString);
		values.put("msgString", msgString);
		db.insert("time", null, values);
		/* 清空编辑框数据
		 * */
		editObj.setText("");
		editDate.setText("");
		editTime.setText("");
		editMsg.setText("");
	}
}
