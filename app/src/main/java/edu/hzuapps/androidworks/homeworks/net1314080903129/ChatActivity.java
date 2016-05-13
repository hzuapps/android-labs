package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dudon on 2016/1/15.
 */
public class ChatActivity extends Activity {
    private List<Message> chatList;
    private Net1314080903129MessageAdapter adapter;
    private Net1314080903129ChatLayout net1314080903129ChatLayoutNet1314080903129;
    private ListView listView;
    private EditText editText;
    private Button button;
    private Net1314080903129User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903129activity_chat);
        //Intent内容传递
        Intent intent = getIntent();
        user = (Net1314080903129User) intent.getSerializableExtra("user");
        if (user.getChats() == null) {
            chatList = new ArrayList<Message>();
            user.setChats(chatList);
        } else {
            chatList = user.getChats();
        }
        //加载内容
        TextView chatTitle = (TextView) findViewById(R.id.user_center);
        chatTitle.setText(user.getName());
        //适配内容
        loadContent();
        listView = (ListView) findViewById(R.id.chat_list);
        adapter = new Net1314080903129MessageAdapter(ChatActivity.this, R.layout.net1314080903129chat_item, chatList);
        adapter.setUserImage(user.getImage());
        listView.setAdapter(adapter);
        //点击弹出内容
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView white = (TextView) view.findViewById(R.id.white_message);
                TextView green = (TextView) view.findViewById(R.id.green_message);
                int type = chatList.get(position).getType();
                final String content = chatList.get(position).getContent();
                //点击气泡弹出消息
                if (type == Message.TYPE_RECEIVED) {
                    white.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(ChatActivity.this, content, Toast.LENGTH_SHORT).show();
                            chatList.remove(position);
                            adapter.notifyDataSetChanged();
                            listView.setSelection(chatList.size());
                        }
                    });
                } else if (type == Message.TYPE_SENT) {
                    green.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(ChatActivity.this, content, Toast.LENGTH_SHORT).show();
                            chatList.remove(position);
                            adapter.notifyDataSetChanged();
                            listView.setSelection(chatList.size());
                        }
                    });
                }

            }
        });
        //发送信息
        button = (Button) findViewById(R.id.send_message);
        editText = (EditText) findViewById(R.id.edit_message);
        net1314080903129ChatLayoutNet1314080903129 = (Net1314080903129ChatLayout) findViewById(R.id.chat_layout);

        net1314080903129ChatLayoutNet1314080903129.setUserName(user.getName());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (!TextUtils.isEmpty(content)) {

                    if (net1314080903129ChatLayoutNet1314080903129.getSendState() == Message.TYPE_RECEIVED) {
                        Message message = new Message(content, Message.TYPE_RECEIVED);
                        chatList.add(message);
                    } else if (net1314080903129ChatLayoutNet1314080903129.getSendState() == Message.TYPE_SENT) {
                        Message message = new Message(content, Message.TYPE_SENT);
                        chatList.add(message);
                    }
                    //当有新消息时，刷新ListView中的显示
                    adapter.notifyDataSetChanged();
                    //将ListView定位到最后一行
                    listView.setSelection(chatList.size());
                    //清空输入框中的内容
                    editText.setText("");
                }
            }
        });

    }

    //手动初始化内容
    private void loadContent() {
//        Message Message1 = new Message("How are you?", Message.TYPE_SENT);
//        Message Message2 = new Message("I am fine ,and you?", Message.TYPE_RECEIVED);
//        Message Message3 = new Message("I am fine too.", Message.TYPE_RECEIVED);
//        chatList.add(Message1);
//        chatList.add(Message2);
//        chatList.add(Message3);
    }
}

