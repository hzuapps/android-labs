package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
//p4 资料修改
//@// TODO: 2016/6/11 以sp形式存储和加载数据 
public class AccountAlterInfoActivity extends Activity implements
		OnClickListener {

	Button btn_left_header;
	TextView tv_head_title;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_alter_account);
		findViewById();
		initTitle();
	}

	private void initTitle() {
		tv_head_title.setText("资料修改");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left_header:
			finish();
			break;

		default:
			break;
		}

	}

	private void findViewById() {
		btn_left_header = (Button) this.findViewById(R.id.btn_left_header);
		btn_left_header.setOnClickListener(this);
		tv_head_title = (TextView) this.findViewById(R.id.tv_head_title);

	}
}
