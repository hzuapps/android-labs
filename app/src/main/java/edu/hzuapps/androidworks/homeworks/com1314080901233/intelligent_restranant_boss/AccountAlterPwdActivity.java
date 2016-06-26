package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
//密码修改界面
public class AccountAlterPwdActivity extends Activity implements
        OnClickListener {
    ImageButton login_close_button;
    Button btn_login_reg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_alter_pwd);
        init();
    }

    private void init(){
        login_close_button = (ImageButton) this
                .findViewById(R.id.login_close_button);
        btn_login_reg=(Button)findViewById(R.id.login_btn_reg);
        login_close_button.setOnClickListener(this);
        btn_login_reg.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_close_button:
            case R.id.login_btn_reg:
                finish();
                break;

            default:
                break;
        }

    }
}
