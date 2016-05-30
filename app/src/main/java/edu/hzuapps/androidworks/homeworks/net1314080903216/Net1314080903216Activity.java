package edu.hzuapps.androidworks.homeworks.net1314080903216;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Net1314080903216Activity extends AppCompatActivity {

    EditText ip;
    EditText send;
    TextView rectext;
    String IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903216);
        ip = (EditText) findViewById(R.id.ip);
        send = (EditText) findViewById(R.id.edit);
        rectext = (TextView) findViewById(R.id.text);
        rectext.setMovementMethod(ScrollingMovementMethod.getInstance());
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                IP=ip.getText().toString();
                connect();
            }
        });

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                send();
            }
        });
    }

    Socket socket = null;
    BufferedWriter writer = null;
    BufferedReader reader = null;

    public void connect() {
        AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>() {

            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    socket = new Socket(IP, 3666);
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    publishProgress("连接成功");
                } catch (UnknownHostException e1) {
                    Toast.makeText(Net1314080903216Activity.this, "无法建立连接", Toast.LENGTH_SHORT).show();
                } catch (IOException e1) {
                    Toast.makeText(Net1314080903216Activity.this, "无法建立连接", Toast.LENGTH_SHORT).show();
                }
                try {
                    String line;
                    while ((line = reader.readLine())!= null) {
                        publishProgress(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                if (values[0].equals("连接成功")) {
                    Toast.makeText(Net1314080903216Activity.this, "连接成功！", Toast.LENGTH_SHORT).show();

                }
                appendtext("服务器："+values[0]+"\n");
                super.onProgressUpdate(values);
            }
        };
        read.execute();
    }

    public void send() {
        try {
           appendtext("本端："+send.getText().toString()+"\n");
            writer.write(send.getText().toString()+"\n");
            writer.flush();
            send.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void appendtext(String msg){
        rectext.append(msg);
        int offset=rectext.getLineCount()*rectext.getLineHeight();
        if(offset>rectext.getHeight()){
            rectext.scrollTo(0,offset-rectext.getHeight());
        }
    }
    }

