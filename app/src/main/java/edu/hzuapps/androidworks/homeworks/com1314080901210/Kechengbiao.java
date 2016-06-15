package com.example.com1314080901210.Kechengbiao;

import czl.Curriculum.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Kechengbiao extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
     
        TextView tv=(TextView)findViewById(R.id.day);
        Date now=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy年MM月dd日");
        
        tv.setText(f.format(now).toString());
        
        ListView lv = (ListView)findViewById(R.id.ListView01);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new String[]{getString(R.string.mon),getString(R.string.tue),getString(R.string.wed),getString(R.string.thu),getString(R.string.fri),getString(R.string.sat),getString(R.string.sun)});
        lv.setAdapter(aa);
        
        lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				Intent i=new Intent(Kechengbiao.this,Day.class);
				i.putExtra("d", arg2);
				startActivity(i);
				
	
			}
        	
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater=getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case R.id.help:
    		new AlertDialog.Builder(this).setTitle(R.string.help).setMessage(R.string.help_text).setIcon(android.R.drawable.ic_menu_help).show();
    		break;    	
    	case R.id.about:
    		new AlertDialog.Builder(this).setTitle(R.string.about).setMessage(R.string.about_text).setIcon(android.R.drawable.ic_menu_info_details).show();
    		break;
    	case R.id.exit:
    	{
    		new AlertDialog.Builder(this).setTitle("是否退出?").setPositiveButton("确定",
    				new DialogInterface.OnClickListener() 
    				{
						
    			@Override
				public void onClick(DialogInterface dialog, int which) {
		
					
					new AlertDialog.Builder(Kechengbiao.this).setMessage(
							"课程表已经退出！！！").create().show();
					finish();
					
				}
			}).setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							new AlertDialog.Builder(Kechengbiao.this).setMessage(
									"你已经选择了取消操作！").create().show();
									
								}
							}).show();
    		}
    	}
    	return false;
    }
}