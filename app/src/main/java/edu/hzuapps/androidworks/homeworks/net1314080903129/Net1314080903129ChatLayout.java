package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by dudon on 2016/1/15.
 */
public class Net1314080903129ChatLayout extends LinearLayout {
    private boolean isMe = true;
    private String userName;
    private int sendState = Message.TYPE_SENT;

    public Net1314080903129ChatLayout(final Context context, AttributeSet attr) {
        super(context, attr);
        //动态加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.net1314080903129user_bar, this);
        Button btnBack = (Button) findViewById(R.id.text_back);
        Button btnEdit = (Button) findViewById(R.id.user_change);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束当前Activity
                ((Activity) getContext()).finish();
                //Context是Activity的父类
            }
        });
        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isMe) {
                    Toast.makeText(getContext(), "切换到 " + userName, Toast.LENGTH_SHORT).show();
                    sendState = Message.TYPE_RECEIVED;
                    isMe = false;
                } else {
                    Toast.makeText(getContext(), "切换至本地", Toast.LENGTH_SHORT).show();
                    isMe = true;
                    sendState = Message.TYPE_SENT;
                }

            }
        });
    }

    public int getSendState() {
        return sendState;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
