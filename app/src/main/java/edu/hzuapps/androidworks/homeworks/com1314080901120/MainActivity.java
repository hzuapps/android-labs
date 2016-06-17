package com.example.bigwheeldemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends Activity implements OnClickListener{
	private Button mBtn_1;
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.com1314080901120_main);
		init();
	}
	
	public void init(){
		mBtn_1 = (Button)findViewById(R.id.btn_bigWheel);

		mBtn_1.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bigWheel:
			BigWheelActivity.startActivity(this);
			break;
		default:
			break;
		}
	}
}
