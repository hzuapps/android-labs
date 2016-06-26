package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
//用户反馈
public class FeedbackActivity extends Activity implements OnClickListener {

	ImageButton main_head_back_button;
	TextView tv_head_title;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more_feedback);
		init();
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_head_back_button:
			finish();
			break;

		default:
			break;
		}

	}

	private void init() {
		main_head_back_button = (ImageButton) this
				.findViewById(R.id.main_head_back_button);
		main_head_back_button.setOnClickListener(this);

	}
}
