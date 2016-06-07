package com.example.passwordbox;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AcountListView extends Activity{
	private List<AcountData> acountList=new ArrayList<AcountData>();
	private MyDatabaseHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acount_listview);
		initAcount();
		AcountAdapter adapter=new AcountAdapter(AcountListView.this,R.layout.acount_item, acountList);
		ListView listView=(ListView)findViewById(R.id.list_view_acount);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				AcountData acountData = acountList.get(position);
				Intent intent=new Intent(AcountListView.this,AcountDetails.class);//显示Intent
				intent.putExtra("acountId", String.valueOf(acountData.getAcountId()));
				startActivity(intent);
			}
		});
		Button addAcountBtn=(Button)findViewById(R.id.addAcountBtn);
		addAcountBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AcountListView.this,AddAcount.class);//显示Intent
				startActivity(intent);
			}
		});
	}
	private void initAcount()
	{
		dbHelper=new MyDatabaseHelper(this, "PasswordBox.db", null, 2);
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		// 查询Acount表中所有的数据
		Cursor cursor=db.rawQuery("select * from Acount", null);
		if(cursor.moveToFirst())
		{
			do{
				int id=cursor.getInt(cursor.getColumnIndex("id"));
				String acountLabel=cursor.getString(cursor.getColumnIndex("acountLabel"));
				AcountData acountData=new AcountData(id,acountLabel);
				acountList.add(acountData);
			}
			while(cursor.moveToNext());
		}
		cursor.close();
	}
}
