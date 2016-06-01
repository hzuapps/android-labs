package edu.hzuapps.androidworks.homeworks.com1314080901123;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Com1314080901123_Activity extends AppCompatActivity {
    private Button insert;
    private Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901123);

        //        布置insert按钮监听事件
        insert=(Button)findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity跳转
                Intent intent=new Intent();
                intent.setClass(Com1314080901123_Activity.this,Com1314080901123_InsertActivity.class);
                startActivity(intent);
            }
        });
        //        布置qurey按钮监听事件
        query=(Button)findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity跳转

                Intent intent=new Intent();
                intent.setClass(Com1314080901123_Activity.this,Com1314080901123_QueryActivity.class);
                startActivity(intent);
            }
        });
    }
}
