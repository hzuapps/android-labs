package com.saolei.minesweeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

public class Com1314080901110Activity extends AppCompatActivity {
    private Button toPlay;
    private Button setDifficulty;
    public String level="10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_Com131408090110);
//        布置ToPlayGame按钮监听事件
        toPlay=(Button)findViewById(R.id.toPlay);
        toPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity跳转
                Intent intent=new Intent();
                intent.putExtra("level",level);
                intent.setClass(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
        setDifficulty=(Button)findViewById(R.id.setDifficulty);
        setDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(v.getContext());
                new AlertDialog.Builder(v.getContext()).setTitle("请输入游戏难度（2-20）：").setView(editText).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                level= editText.getText().toString().trim();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
