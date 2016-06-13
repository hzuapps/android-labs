package com.example.lzf.dianzi_clock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lzf on 2016/4/28.
 */
public class Main_2_com1314080901219 extends Activity{
    private Button fulfillbutton,gettext;
    private EditText hourtext,minutetext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901219_2);


        hourtext=(EditText)findViewById(R.id.hour);
        minutetext=(EditText)findViewById(R.id.minute);
        fulfillbutton=(Button)findViewById(R.id.fulfill);
        fulfillbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                String hourString=hourtext.getText().toString();
                String minuteString=minutetext.getText().toString();
                if (!hourString.equals("")&&!minuteString.equals("")) {
                    int hourtiem = Integer.parseInt(hourString);
                    int minutetiem = Integer.parseInt(minuteString);
                    if(hourtiem>=0&&hourtiem<24&&minutetiem>=0&&minutetiem<60) {
                        editor.putInt("hour",hourtiem);
                        editor.putInt("minute",minutetiem);
                        editor.commit();
                        Toast.makeText(Main_2_com1314080901219.this,"设置完成",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Main_2_com1314080901219.this,"时间设置有误",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Main_2_com1314080901219.this,"取值不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
