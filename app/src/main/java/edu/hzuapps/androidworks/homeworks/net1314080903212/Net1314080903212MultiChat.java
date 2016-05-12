package edu.hzuapps.androidworks.homeworks.net1314080903212;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import java.util.logging.Handler;
import android.os.Handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2016/4/12.
 */
public class Net1314080903212MultiChat extends Activity {
    //定义界面的两个文本框
    EditText input;
    TextView show;
    //定义一个界面上的按钮
    Button send;
    Handler handler;
    //定义与服务器通信的子线程
    Net1314080903212ClientThread clientThread;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        input = (EditText) findViewById(R.id.input);
        send = (Button) findViewById(R.id.send);
        show = (TextView) findViewById(R.id.show);
        show.setMovementMethod(new ScrollingMovementMethod());

        //读取历史记录
        Net1314080903212RH read_history = new Net1314080903212RH();
        read_history.file_read(show);

        handler = new Handler()                         //????不懂！！！
        {
            @Override
            public void handleMessage(Message msg)
            {
                //如果消息来自于子线程
                if (msg.what == 0x123)                  //????不懂！！！
                {
                    //将读取的内容追加显示到文本框
                    Date date = new Date();
                    DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = date_format.format(date);
                    show.append("\n" + time);
                    show.append("\n" + msg.obj.toString());
                    //并写到文件！                                    //!!!!
                }
            }
        };
        clientThread = new Net1314080903212ClientThread(handler);
        //客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
        new Thread(clientThread).start();

        send.setOnClickListener(new View.OnClickListener()              ///!!!
        {
            @Override
            public void onClick(View v)
            {

                try {
                    //当用户按下发送按钮后， 将用户输入的数据封装成Message
                    //然后发送给子线程的Handler
                    Message msg = new Message();

                    msg.what = 0x345;                   ///???????不懂!!
                    msg.obj = input.getText().toString();
                    clientThread.revHandler.sendMessage(msg);
                    System.out.println("send msg = " + input.getText() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //清空input文本框
                    input.setText("");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });                                                                   ///note!!!!不懂!!!
    }

}
