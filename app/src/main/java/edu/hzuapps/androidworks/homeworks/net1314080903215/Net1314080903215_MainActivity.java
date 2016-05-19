package edu.hzuapps.androidworks.homeworks.net1314080903215;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Net1314080903215_MainActivity extends AppCompatActivity{

    //----------------------------- UI变量 ---------------------------
    private TextView tvIP;

    //----------------------------------------------------------------
    private Handler handler;
    private Socket client;
    private String ip;
    private DataInputStream dis;
    private DataOutputStream dos;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903215__main);

        tvIP = (TextView) findViewById(R.id.tvIP);

        //接收扫描IP线程返回的可用IP地址
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1000:
                        ip = (String) msg.obj;  //获取扫描的IP地址
                        tvIP.setText(ip);   //在界面中显示
                        Toast.makeText(Net1314080903215_MainActivity.this, "找到服务端，可连接服务端了", Toast.LENGTH_SHORT).show();
                        break;
                    case 2000:
                        Toast.makeText(Net1314080903215_MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };

        //添加五个按钮的点击事件
        //扫描IP
        findViewById(R.id.btnScan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
        //连接PC
        findViewById(R.id.btnConnect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
        //关机
        findViewById(R.id.btnShutdown).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                shutdown();
            }
        });
        //重启
        findViewById(R.id.btnRestart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
        //取消
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }



    //----------------------------- 操作实现方法 -----------------------------------------------
    public void scan(){
        Net1314080903215_MyWifi net1314080903215MyWifi = new Net1314080903215_MyWifi(Net1314080903215_MainActivity.this);
        new ScanIP(net1314080903215MyWifi).start();
    }

    public void connect(){
        Toast.makeText(Net1314080903215_MainActivity.this, "尝试连接服务端", Toast.LENGTH_SHORT).show();
        AsyncTask<String, String, Socket> connect_server = new AsyncTask<String, String, Socket>() {
            @Override
            protected Socket doInBackground(String... params) {
                Socket socket = null;
                try {
                    socket = new Socket(params[0], 30000);
                    publishProgress("@success");
                } catch (IOException e) {
                    //Toast.makeText(Net1314080903215_MainActivity.this, "无法建立连接", Toast.LENGTH_SHORT).show();
                    System.out.println("连接服务端失败");
                }
                return socket;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if(values[0].equals("@success")){
                    Toast.makeText(Net1314080903215_MainActivity.this, "连接服务端成功！", Toast.LENGTH_SHORT).show();
                }
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Socket socket) {
                client = socket;
                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dis = new DataInputStream(socket.getInputStream());
                    //启动接收服务端发来消息的线程
                    new Receive(dis).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.onPostExecute(socket);
            }
        };
        connect_server.execute(ip); //启动
    }

    public void shutdown(){
        send("shutdown");
    }

    public void restart(){
        send("restart");
    }

    public void cancel(){
        send("cancel");
    }

    //------------------------------- 通过Socket发送消息 -----------------------------------------------
    public void send(String msg){
        if(dos!=null){
            try {
                dos.writeUTF(msg);
                dos.flush();
//                if(dis!=null){
//                    String str = dis.readUTF();
////                    Toast.makeText(Net1314080903215_MainActivity.this, str, Toast.LENGTH_SHORT).show();
//                    System.out.println(str);
//                }else{
//                    System.out.println("dis is null");
//                }
            } catch (IOException e) {
                Toast.makeText(Net1314080903215_MainActivity.this, msg+" 失败！", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(Net1314080903215_MainActivity.this, "未连接服务端", Toast.LENGTH_SHORT).show();
        }
    }

    //------------------------------- 扫描IP的线程 ----------------------------------------------------
    class ScanIP extends Thread{
        String findIP;
        Net1314080903215_MyWifi net1314080903215MyWifi;
        ScanIP(Net1314080903215_MyWifi net1314080903215MyWifi){
            this.net1314080903215MyWifi = net1314080903215MyWifi;
        }
        @Override
        public void run() {
            String serverIP = net1314080903215MyWifi.getServerIp();
            int t = serverIP.lastIndexOf(".") + 1;
            String resultIP = serverIP.substring(0, t);
            for(int i=1; i<255; i++){
                Socket socket = new Socket();
                InetSocketAddress s = new InetSocketAddress(resultIP + i, 30000);
                try {
                    socket.connect(s, 50);
                    socket.close();

                    //把IP发给主UI
                    Message msg = Message.obtain();
                    msg.what = 1000;
                    msg.obj = resultIP + i;
                    handler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                findIP = resultIP + i;
                break;
            }
            super.run();
        }
    }

    //---------------------------------- 侦听发送过来消息的线程 ---------------------------------------
    class Receive extends Thread{
        private DataInputStream dis;
        Receive(DataInputStream dis){
            this.dis = dis;
        }

        @Override
        public void run() {
            String str;
            if(dis!=null){
                while(true){
                    try {
                        str = dis.readUTF();
                        Message msg = Message.obtain();
                        msg.what = 2000;
                        msg.obj = str;
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                System.out.println("dis is null");
            }
        }
    }

}
