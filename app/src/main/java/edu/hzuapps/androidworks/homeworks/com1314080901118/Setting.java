package com.hzu.lxh.mencard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//设置窗口
public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting1314080901118);
        Button btn_ok = (Button) findViewById(R.id.ok_btn); //确定按钮设置
        btn_ok.setOnClickListener(new Button.OnClickListener() {
                                      public void onClick(View view)  //创建监听
                                      {
                                          Toast toast = Toast.makeText(Setting.this, "设置完成", Toast.LENGTH_LONG);
                                          toast.show();
                                          Setting.this.finish();
                                      }
                                  }
        );
        Button btn_cncel = (Button) findViewById(R.id.cncel_btn);     //取消按钮设置
        btn_cncel.setOnClickListener(new Button.OnClickListener() {
                                       public void onClick(View view) {
                                           Intent in = new Intent();
                                           in.setClassName(getApplicationContext(), "com.hzu.lxh.mencard.Com1314080901118Activity");
                                           startActivity(in);
                                       }
                                   }
        );
    }
}
