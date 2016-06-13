package com.example.lzf.dianzi_clock;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity_com1314080901219 extends Activity {
    private Button button1;
    private Button button2;
    private TimeChangeBroadcastReceiver_com1314080901219 timechange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_com1314080901219);
        timechange=new TimeChangeBroadcastReceiver_com1314080901219();
        registerReceiver(timechange, new IntentFilter(Intent.ACTION_TIME_TICK));

        button1=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0=new Intent(MainActivity_com1314080901219.this,Main_1_com1314080901219.class);
                startActivity(intent0);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity_com1314080901219.this,Main_2_com1314080901219.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timechange);
    }
}
