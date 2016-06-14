package edu.hzuapps.androidworks.homeworks.com1314080901106;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Com1314080901106Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901106);

        findViewById(R.id.btnSendMsg).setOnClickListener(this);
        findViewById(R.id.btnReg).setOnClickListener(this);
        findViewById(R.id.btnunReg).setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case  R.id.btnSendMsg:
                //Intent i =new Intent(this,MyReceiver.class);
                Intent i =new Intent(Com1314080901106Receiver.ACTION);
                i.putExtra("data","HzuUniversity");
                sendBroadcast(i);
                break;
            case  R.id.btnReg:
                if(receiver==null) {
                    receiver=new Com1314080901106Receiver();
                    registerReceiver(receiver,new IntentFilter(Com1314080901106Receiver.ACTION));
                }
                break;
            case  R.id.btnunReg:
                if(receiver!=null) {
                    unregisterReceiver(receiver);
                    receiver =null;
                }
                break;

        }

    }

    private Com1314080901106Receiver receiver = null;
}
