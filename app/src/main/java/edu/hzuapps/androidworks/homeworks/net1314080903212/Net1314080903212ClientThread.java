package edu.hzuapps.androidworks.homeworks.net1314080903212;

import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import android.os.Handler;                  ////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//import java.util.logging.Handler;


/**
 * Created by dell on 2016/4/12.
 */
public class Net1314080903212ClientThread implements Runnable {
    private Socket      s;
    private Handler     handler;        //定义向UI发送消息的Handler对象
    public Handler     revHandler;     //定义接收UI线程消息的Handler对象
    BufferedReader       br =null;        //输入流
    OutputStream         os = null;       //输出流

    public Net1314080903212ClientThread(Handler handler)
    {
        this.handler = handler;
    }


    public void run()
    {
        try {
            System.out.println("Socket before!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            s = new Socket("192.168.240.22", 9402);
            System.out.println("Socket behind!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            br = new BufferedReader((new InputStreamReader(s.getInputStream())));
            os = s.getOutputStream();       //为什么br不是类似这样？

            //启动一条子线程来读取服务器响应的数据
            new Thread()
            {
                @Override
                public void run()
                {
                    String content = null;
                    Net1314080903212WH write_history = new Net1314080903212WH();
                    //不断读取Socket输入流中的内容
                    try
                    {
                        while((content = br.readLine()) != null)
                        {
                            //读到来自服务器的数据后， 发送消息通知程序
                            //界面显示该数据
                            System.out.println("content =" + content + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                            Message msg = new Message();
                            msg.what = 0x123;                               //?????
                            msg.obj = content; //s.getLocalAddress() + ":" + content;                              //????            ////!!!
                            handler.sendMessage(msg);                      //???
                            //把数据写入文件
                            write_history.write_file(content);

                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }.start();                                                     //????

            //当前线程初始化
            Looper.prepare();                                             //????
            revHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    //接收到UI线程中用户输入的数据
                    if (msg.what == 0x345)
                    {
                        //将用户在文本框输入的内容写入网络
                        try
                        {
                            os.write((s.getLocalAddress() + ": " + msg.obj.toString() + "\r\n").getBytes("utf-8"));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //启动Looper
            Looper.loop();                  //????
        }
        catch (SocketTimeoutException e1)
        {
            System.out.println("网络连接超时!!!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
