package edu.hzuapps.androidworks.homeworks.net1314080903114;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Net1314080903114_GetIPActivity extends Activity {
    String ipname = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_net1314080903114__main);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);   //定义一个AlertDialog.Builder对象
        builder.setTitle("登录服务器对话框");                          // 设置对话框的标题
        //装载/res/layout/login.xml界面布局
        LinearLayout loginForm = (LinearLayout)getLayoutInflater().inflate( R.layout.net1314080903114_login, null);
        final EditText iptext = (EditText)loginForm.findViewById(R.id.et_ip);
        builder.setView(loginForm);                              // 设置对话框显示的View对象
        // 为对话框设置一个“登录”按钮
        builder.setPositiveButton("登录"
                // 为按钮设置监听器
                , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //此处可执行登录处理
                ipname = iptext.getText().toString().trim();
                Bundle data = new Bundle();
                data.putString("ipname",ipname);
                Intent intent = new Intent(Net1314080903114_GetIPActivity.this,Net1314080903114_CameraActivity.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
        // 为对话框设置一个“取消”按钮
        builder.setNegativeButton("取消"
                ,  new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //取消登录，不做任何事情
                System.exit(1);
            }
        });
        //创建、并显示对话框
        builder.create().show();
    }


}
