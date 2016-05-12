package edu.hzuapps.androidworks.homeworks.com1314080901134;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class com1314080901134 extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_com1314080901134);


    }
    public void onClick_Event(View view){
        
        TextView textView = (TextView)findViewById(R.id.Button01);
        textView.setText("你点击了个性推荐按钮");
    }
}