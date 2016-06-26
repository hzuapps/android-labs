package com.example.intelligent_restranant_boss;

//import org.apache.http.message.BasicNameValuePair;
//登录的Dialog
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
public class LoginDialog extends Activity implements OnClickListener {

	private ImageButton login_close_button;
	private Button login_btn, login_btn_reg;
	private AutoCompleteTextView actv_login_account;
	private EditText et_login_password;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置为true点击区域外消失
		setFinishOnTouchOutside(true);
		setContentView(R.layout.login_dialog);

		actv_login_account = (AutoCompleteTextView) findViewById(R.id.actv_login_account);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		login_btn = (Button) findViewById(R.id.login_btn_login);
		login_btn.setOnClickListener(this);
		login_btn_reg = (Button) findViewById(R.id.login_btn_reg);
		login_btn_reg.setOnClickListener(this);

		login_close_button = (ImageButton) this
				.findViewById(R.id.login_close_button);
		login_close_button.setOnClickListener(this);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_close_button:
		case R.id.login_btn_reg:
			this.finish();
			break;
		case R.id.login_btn_login:

			break;
		default:
			break;
		}
	}

}
