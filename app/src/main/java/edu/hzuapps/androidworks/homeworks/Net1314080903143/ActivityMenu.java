package edu.hzuapps.androidworks.homeworks.Net1314080903143;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.KeyEvent;

import edu.hzuapps.androidworks.homeworks.com1314080903143.R;


public class ActivityMenu extends Activity {
	private static final int ACTIVITY_CREATE = 0;
	private Context mcontext=this;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary_welcome);
		Button allButton = (Button) findViewById(R.id.all);
		Button newButton = (Button) findViewById(R.id.newinsert);
		Button exitButton = (Button) findViewById(R.id.exitp);

		allButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(mcontext, Net1314080903143MainActivity.class);
				startActivityForResult(i, ACTIVITY_CREATE);
			}

		});
		
		newButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(mcontext, ActivityDiaryEdit.class);
				startActivityForResult(i, ACTIVITY_CREATE);
			}

		});
		
		exitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
				builder.setTitle("提示");
				builder.setMessage("确定要退出程序?");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.dismiss();
						System.exit(0);
						finish();
					}
				});
				builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.show();
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK ){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("确定要退出程序?");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					finish();
				}
			});
			builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.show();
			return true;
		}else{
		
			return super.onKeyDown(keyCode, event);
		}
	}
}
