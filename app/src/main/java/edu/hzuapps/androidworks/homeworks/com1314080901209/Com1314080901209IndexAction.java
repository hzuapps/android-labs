package com.example.administrator.chzayi;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class Com1314080901209IndexAction extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		/*
		1.	编写一个Activity；
		2.	将标题设置为自己的学号+选题题目；
		3.	运行后截图存入实验报告；
		4.	Activity内容根据自己选择的题目编写。
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        TextView t=new TextView(this);
        t.setText("这是主页");
        setContentView(t);
    }

}
