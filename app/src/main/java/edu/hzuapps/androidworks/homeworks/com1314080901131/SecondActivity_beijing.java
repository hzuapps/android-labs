package com.example.ranine99.ranine;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by hewq on 2016/4/11.
 */
@SuppressLint("NewApi")
public class SecondActivity_beijing extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout_beijing);


    }

    public void change(View view) {
                //Intent intent = new Intent("com.example.ranine99.ranine.ACTION_START");
                //intent.addCategory("com.example.ranine99.ranine.MY_CATEGORY");
                Intent intent = new Intent(this, ListViewActivity.class);
               // intent.addCategory("com.example.ranine99.ranine.MY_CATEGORY");

                startActivity(intent);




    }
}