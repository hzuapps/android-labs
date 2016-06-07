package edu.hzuapps.androidworks.homeworks.net1314080903113;

import com.huangcheng.smscut.R;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Net1314080903113_SmsCut extends Activity {
	private EditText sms_num_edit;
	private Button yes_btn;
	private Button no_btn;

	private Net1314080903113_SmsRecevier recevier=null;
	private boolean isregiset = false;

	private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903113_main);
		sms_num_edit = (EditText) findViewById(R.id.sms_number_edit);
		yes_btn = (Button) findViewById(R.id.yes_btn);
		no_btn = (Button) findViewById(R.id.no_btn);
		yes_btn.setEnabled(true);
		no_btn.setEnabled(false);
		yes_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				regiset();
				yes_btn.setEnabled(false);
				no_btn.setEnabled(true);
			}
		});
		no_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				unregiset();
				Net1314080903113_SmsCut.this.finish();
			}
		});
	}
	public void regiset() {
		IntentFilter filter = new IntentFilter(ACTION);
		filter.setPriority(1000);// 设置优先级最大
		recevier = new Net1314080903113_SmsRecevier(sms_num_edit.getText().toString().trim());
		registerReceiver(recevier, filter);
		isregiset = true;
		Toast.makeText(this, "开始拦截", 0).show();
	}
	public void unregiset() {
		if (recevier != null && isregiset) {
			unregisterReceiver(recevier);
			isregiset = false;
			Toast.makeText(this, "停止拦截,关闭程序", 0).show();
		} else
			Toast.makeText(this, "尚未设置，关闭程序", 0).show();
	}
	protected void onDestroy() {
		super.onDestroy();
		if (recevier != null && isregiset) {
			unregisterReceiver(recevier);
			isregiset = false;
			Toast.makeText(this, "停止拦截,关闭程序", 0).show();
		}
	}
}
