package edu.hzuapps.androidworks.homeworks.net1314080903114;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Net1314080903114_HomeActivity extends Activity {
    Button startBtn,endBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903114__home);
        // 初始化控件 的方法
        initView();
        // 为按钮设置监听的方法
        setListener();
    }

    private void setListener() {
        //监控按钮点击监听事件
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Net1314080903114_HomeActivity.this, Net1314080903114_GetIPActivity.class);
                startActivity(intent);
            }
        });
        //取消按钮点击事件
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        startBtn= (Button) findViewById(R.id.jk_btn);
        endBtn= (Button) findViewById(R.id.qx_Btn);
    }


}
