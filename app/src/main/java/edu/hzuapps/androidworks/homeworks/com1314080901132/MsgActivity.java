package edu.hzuapps.androidworks.homeworks.com1314080901132;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9 0009.
 */
public class MsgActivity extends AppCompatActivity implements OnClickListener {

    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    EditText et_receiver;
    EditText et_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_activity_main);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        et_receiver = (EditText) findViewById(R.id.et_receiver);
        et_msg = (EditText) findViewById(R.id.et_msg);

        et_receiver.setText(MainActivity.buffer.toString().trim());

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton1:
                clickImageButton1();
                break;
            case R.id.imageButton2:
                clickImageButton2();
                break;
            case R.id.imageButton3:
                clickImageButton3();
                break;
            case R.id.imageButton4:
                clickImageButton4();
                break;
        }

    }

    private void clickImageButton1(){
        Intent intent = new Intent(this,ImageButton_1_Activity.class);
        startActivity(intent);
    }
    private void clickImageButton2(){
        Intent intent = new Intent(this,ImageButton_2_Activity.class);
        startActivity(intent);
    }
    private void clickImageButton3(){
        Intent intent = new Intent(this,ImageButton_3_Activity.class);
        startActivity(intent);
    }
    private void clickImageButton4(){
        String phone = et_receiver.getText().toString();
        String context = et_msg.getText().toString();
        SmsManager manager = SmsManager.getDefault();//获得默认的消息管理器
        ArrayList<String> list = manager.divideMessage(context);  //因为一条短信有字数限制，因此要将长短信拆分
        for(String text:list){
            manager.sendTextMessage(phone, null, text, null, null);//发送短信
        }
        Toast.makeText(getApplicationContext(), "发送完毕", Toast.LENGTH_SHORT).show();
    }
}
