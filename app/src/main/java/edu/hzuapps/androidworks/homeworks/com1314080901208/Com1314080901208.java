package com.example.passwordbox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Com1314080901208 extends Activity {

	private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901208);
        dbHelper=new MyDatabaseHelper(this, "PasswordBox.db", null, 2);
        Button TiJiaoBtn=(Button)findViewById(R.id.TiJiaoBtn);
        TiJiaoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText userNameText=(EditText)findViewById(R.id.userName);
				EditText paddwordText=(EditText)findViewById(R.id.password);
				SQLiteDatabase db=dbHelper.getWritableDatabase();
				// 查询Login表中所有的数据
				Cursor cursor=db.rawQuery("select * from Login where userName='"+userNameText.getText().toString()+"'", null);				
				if(cursor.getCount()==0)
				{
					Toast.makeText(Com1314080901208.this,"用户名不存在",Toast.LENGTH_LONG).show();	
				}
				else
				{
					Toast.makeText(Com1314080901208.this,"登录成功",Toast.LENGTH_LONG).show();
					Intent intent=new Intent(Com1314080901208.this,AcountListView.class);//显示Intent
					startActivity(intent);
				}
				
				//Toast.makeText(Com1314080901208.this,cursor.getCount(),Toast.LENGTH_LONG).show();
//				Intent intent = new Intent("com.example.passwordbox.ACTION_START");//隐式Intent
//			     intent.addCategory("android.intent.category.MY_CATEGORY");//这里指定了Category为MY_CATEGORY，如果没有指定这个时默认为default
//				  //Intent intent=new Intent(Com1314080901208.this,activity2.class);//显示Intent
//				 startActivity(intent);
			}
		});
        
        //点击注册跳转到注册页面
        Button RegisterBtn=(Button)findViewById(R.id.RegisterBtn);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Com1314080901208.this,Register.class);
				 startActivity(intent);
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.com1314080901208, menu);
        return true;
    }
    
}
