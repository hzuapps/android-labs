package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.intelligent_restranant_boss.budget.MyBudgetActivity;
//标签页4
public class MyActivity extends Activity implements OnClickListener {

	LinearLayout ll_alter_info, ll_alter_pwd, ll_my_add_mode,
			ll_check_my_budget;
	RelativeLayout rl_my_budget, rl_my_info, rl_worker_info;
	LinearLayout ll_my_weather;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my);
		findViewById();
	}

	private void findViewById() {
		ll_alter_info = (LinearLayout) this.findViewById(R.id.ll_my_alter_login);
		ll_alter_pwd = (LinearLayout) this.findViewById(R.id.ll_my_alter_pwd);
		ll_my_add_mode = (LinearLayout) this.findViewById(R.id.ll_my_add_mode);
		ll_check_my_budget = (LinearLayout) this
				.findViewById(R.id.ll_check_my_budget);
		ll_my_weather = (LinearLayout) this.findViewById(R.id.ll_my_weather);
		rl_my_budget = (RelativeLayout) this.findViewById(R.id.rl_my_budget);
		rl_my_info = (RelativeLayout) this.findViewById(R.id.rl_my_info);
		rl_worker_info = (RelativeLayout) this
				.findViewById(R.id.rl_worker_info);

		ll_alter_info.setOnClickListener(this);
		ll_alter_pwd.setOnClickListener(this);
		ll_my_add_mode.setOnClickListener(this);	//今日签到 命名?
		rl_my_budget.setOnClickListener(this);
		rl_my_info.setOnClickListener(this);
		rl_worker_info.setOnClickListener(this);
		ll_check_my_budget.setOnClickListener(this);
		ll_my_weather.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_my_alter_login:
			Intent intent = new Intent(MyActivity.this,
					LoginDialog.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		case R.id.ll_my_alter_pwd:
			Intent intentAlterPwd = new Intent(MyActivity.this,
					AccountAlterPwdActivity.class);
			startActivity(intentAlterPwd);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.ll_my_add_mode:
			Intent intent_add_mode = new Intent(MyActivity.this,
					MyMoodActivity.class);
			startActivity(intent_add_mode);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_my_budget:
			Intent intent_my_budget = new Intent(MyActivity.this,
					MyPaymentActivity.class);
			startActivity(intent_my_budget);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_my_info:
			Intent intent_my_info = new Intent(MyActivity.this,
					MyInfoActivity.class);
			startActivity(intent_my_info);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_worker_info:	//员工信息
			Intent intent_worker_info = new Intent(MyActivity.this,
					CommonListActivity.class);
			intent_worker_info.putExtra(CommonListActivity.LIST_TYPE_KEY,"employee");
			startActivity(intent_worker_info);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.ll_check_my_budget:
			Intent intent_check_my_budget = new Intent(MyActivity.this,
					MyBudgetActivity.class);
			startActivity(intent_check_my_budget);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.ll_my_weather:
			/*Intent intent_my_weather = new Intent(MyActivity.this,
					WeatherActivity.class);
			startActivity(intent_my_weather);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);*/
			break;
		default:
			break;
		}

	}

}
