package com.example.ranine99.ranine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by ranine99 on 2016/4/26.
 */

   public class Com1314080901131Activity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.first_layout);

            Button button1 = (Button) findViewById(R.id.button_1);
                   button1.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      //Intent intent0 = new Intent("com.example.ranine99.ranine.ACTION_START");
                                                      // intent0.addCategory("com.example.ranine99.ranine.MY_CATEGORY");
                                                      Intent intent = new Intent(Com1314080901131Activity.this, SecondActivity_beijing.class);
                                                       //intent.addCategory("com.example.ranine99.ranine.MY_CATEGORY");

                                                      startActivity(intent);

                                                      //通过Intent来实现“北京”Button跳转Second_layout
                                                  }
                                              });
                           Button button2 = (Button) findViewById(R.id.button_2);
                           button2.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   Intent intent2 = new Intent(Com1314080901131Activity.this, ThirdActivity_London.class);
                                   //intent2.addCategory("com.example.ranine99.ranine.MY_CATEGORY");
                                   startActivity(intent2);
                               }

                           });




        }
}