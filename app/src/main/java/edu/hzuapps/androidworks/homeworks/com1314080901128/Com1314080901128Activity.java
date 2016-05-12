package com.edu.hzu.liaotian;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.hzu.liaotian.R;

public class MainActivity extends Activity implements OnClickListener{
    EditText et_tony_message;
    Button but_tony_send;

    EditText et_hill_message;
    Button but_hill_send;

    ListView lv_message;
    List<Message> list;
    MessageAdapter adapter;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        et_tony_message=(EditText) findViewById(R.id.et_tony_message);
        but_tony_send=(Button) findViewById(R.id.but_tony_send);
        but_tony_send.setOnClickListener(this);

        et_hill_message=(EditText) findViewById(R.id.et_hill_message);
        but_hill_send=(Button) findViewById(R.id.but_hill_send);
        but_hill_send.setOnClickListener(this);

        list=new ArrayList<Message>();
        lv_message=(ListView) findViewById(R.id.lv_message);
        adapter=new MessageAdapter();
        lv_message.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

        if(v==but_tony_send){

            if(et_tony_message.getText()==null||et_tony_message.getText().toString().equals("")){
                Toast.makeText(this, "",Toast.LENGTH_LONG).show();
                return ;
            }
            Message m=new Message();
            m.setFrom_username("Tony");
            m.setCreate_time(System.currentTimeMillis());
            m.setText(et_tony_message.getText().toString());
            sendMessage(m);
            et_tony_message.setText("");
        }

        if(v==but_hill_send){

            if(et_hill_message.getText()==null||et_hill_message.getText().toString().equals("")){
                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                return ;
            }
            Message m=new Message();
            m.setFrom_username("Hill");
            m.setCreate_time(System.currentTimeMillis());
            m.setText(et_hill_message.getText().toString());
            sendMessage(m);
            et_hill_message.setText("");
        }
    }
    private void sendMessage(Message m) {
        list.add(m);
        adapter.notifyDataSetChanged();
//      lv_message.f
        lv_message.setSelection(list.size()+1);
    }
    class MessageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Message message=list.get(position);
            ViewHolder viewHolder=null;
//      if(convertView==null){
            if("Tony".equalsIgnoreCase(message.getFrom_username())){
                convertView=parent.inflate(MainActivity.this, R.layout.list_message_item_left, null);
            }else{
                convertView=parent.inflate(MainActivity.this, R.layout.list_message_item_right, null);
            }
            viewHolder=new ViewHolder();
            viewHolder.iv_userhead=(ImageView) convertView.findViewById(R.id.iv_userhead);
            viewHolder.tv_chatcontent=(TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.tv_sendtime=(TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tv_username=(TextView) convertView.findViewById(R.id.tv_username);
//          convertView.setTag(viewHolder);
//      }else{
//          viewHolder=(ViewHolder) convertView.getTag();
//      }

            viewHolder.tv_chatcontent.setText(message.getText());
            viewHolder.tv_sendtime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(message.getCreate_time())));
            viewHolder.tv_username.setText(message.getFrom_username());
            return convertView;
        }
        class ViewHolder{
            public ImageView iv_userhead;
            public TextView tv_username;
            public TextView tv_chatcontent;
            public TextView tv_sendtime;
        }
    }
}