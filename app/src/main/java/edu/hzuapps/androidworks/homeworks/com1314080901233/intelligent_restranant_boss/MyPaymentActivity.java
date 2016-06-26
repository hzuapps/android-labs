package com.example.intelligent_restranant_boss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
//p4 查看资产
public class MyPaymentActivity extends Activity implements OnClickListener {

    Button btn_left_header;
    TextView tv_head_title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_payment);
        findViewById();
        initTitle();
    }

    private void initTitle() {
        tv_head_title.setText("财务预算");
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
