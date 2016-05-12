package com.hzu.lxh.mencard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Com1314080901118Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901118);
        Button btn_st = (Button) findViewById(R.id.btn_start); //开始按钮设置
        btn_st.setOnClickListener(new Button.OnClickListener() {
                                      public void onClick(View view)  //创建监听
                                      {
                                          Intent in = new Intent();
                                          in.setClassName(getApplicationContext(), "com.hzu.lxh.mencard.Game");
                                          startActivity(in);
                                      }
                                  }
        );
        Button btn_set = (Button) findViewById(R.id.btn_set);     //设置按钮设置
        btn_set.setOnClickListener(new Button.OnClickListener() {
                                       public void onClick(View view) {
                                           Intent intent = new Intent();
                                           intent.setClassName(getApplicationContext(), "com.hzu.lxh.mencard.Setting");
                                           startActivity(intent);
                                       }
                                   }
        );
        Button btn_ex = (Button) findViewById(R.id.btn_exit);      //退出按钮设置
        btn_ex.setOnClickListener(new Button.OnClickListener() {
                                      public void onClick(View view) {
                                          dialog();
                                          /*
                                          Toast toast = Toast.makeText(Com1314080901118Activity.this, "提示信息", Toast.LENGTH_LONG);
                                          toast.show();
                                          */
                                      }
                                  }
        );
    }

  protected void dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Com1314080901118Activity.this);
        builder.setMessage("是否退出游戏？");
        builder.setTitle("提示");
        builder.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();                                      //关闭提示框
                        Com1314080901118Activity.this.finish();                 //退出
                    }
                });
        builder.setNegativeButton("否",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)  //设置返回键
    {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            dialog();
        }
        return false;
    }
}
