package edu.hzuapps.androidworks.homeworks.net1314080903122;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.hzuapps.androidworks.homeworks.net1314080903122.R;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_Festival;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_FestivalLab;
import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_Msg;

public class Net1314080903122_SendMsg extends AppCompatActivity {

    public static final String KEY_FESTIVAL_ID = "festivalId";
    public static final String KEY_MSG_ID = "msgId";
    private int mFstivalId;
    private int msgId;

    private Net1314080903122_Festival mFestival;
    private Net1314080903122_Msg mMsg;

    private EditText mEdMsg;
    private Button mBtnAdd;
    private FloatingActionButton mFabSend;
    private View mLayoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903122__send_msg);

        initDatas();
        initView();


    }

    private void initView() {
        mEdMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAdd = (Button) findViewById(R.id.id_btn_add);
        mFabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        mLayoutLoading = findViewById(R.id.id_layout_loading);


        mLayoutLoading.setVisibility(View.GONE);

        mFabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLayoutLoading.getVisibility() == View.GONE) ;
                {
                    mLayoutLoading.setVisibility(View.VISIBLE);
                }
            }
        });



        if (msgId !=-1)
        {
            mMsg = Net1314080903122_FestivalLab.getInstance().getMsgByMsgId(msgId);
            mEdMsg.setText(mMsg.getContent());
        }

    }

    private void initDatas() {
        mFstivalId = getIntent().getIntExtra(KEY_FESTIVAL_ID,-1);
        msgId = getIntent().getIntExtra(KEY_MSG_ID, -1);
        mFestival = Net1314080903122_FestivalLab.getInstance().getFestivalById(mFstivalId);
        setTitle(mFestival.getName());
    }


    public static void toActivity(Context context,int festivalId,int msgId){
        Intent intent = new Intent(context,Net1314080903122_SendMsg.class);
        intent.putExtra(KEY_FESTIVAL_ID,festivalId);
        intent.putExtra(KEY_MSG_ID,msgId);
        context.startActivity(intent);

    }


}
