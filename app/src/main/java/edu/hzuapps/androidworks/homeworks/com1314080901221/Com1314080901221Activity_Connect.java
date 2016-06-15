package com.example.lwj_pc.my_classwork;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by LWJ-PC on 2016/6/14.
 */
public class Com1314080901221Activity_Connect extends Activity {
    private final int SUCCESS=1;
    private final int FAILURE=0;
    private TextView citytext,datetext,weathertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901221_connection);
        citytext=(TextView)findViewById(R.id.connect_city);
        datetext=(TextView)findViewById(R.id.connect_date);
        weathertext=(TextView)findViewById(R.id.connect_weather);
        connect();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    String[] strings=getJson(msg.obj.toString());
                    citytext.setText(strings[0]);
                    datetext.setText(strings[1]);
                    weathertext.setText(strings[2]);
                    break;
                case FAILURE:
                    break;
                default:
                    break;
            }
        }
    };

    public String[] getJson(String string){
        JSONObject jObject= null;
        try {
            jObject =new JSONObject(string);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建JsonObject失败");
        }
        //解析JSON数据
        JSONObject jsonObject = jObject.optJSONObject("weatherinfo");
        String[] temp=new String[3];
        temp[0]=jObject.optString("city");
        temp[1]=jObject.optString("date");
        temp[2]=jObject.optString("weather");
        Log.d("json读出信息为：",temp[0]+"-"+temp[1]+"-"+temp[2]);
        String weatherResult="城市："+temp[0]+"\n日期："+temp[1]+"\n天气："+temp[2];
        return temp;
    }

    private void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/liwenjie0/android-labs/master/app/src/main/java/edu/hzuapps/androidworks/homeworks/com1314080901221/Jasontext.txt");
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    //获取返回码
                    int responecode=httpURLConnection.getResponseCode();
                    Log.d("返回码为：","="+responecode);
                    //判断是否请求成功，200则请求成功
                    if(responecode==200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                        String responecontent=readInput(reader);
                        Log.d("信息读取完毕","!!!!!!!!");
                        //Handler message
                        Message message=new Message();
                        message.what=SUCCESS;
                        message.obj=responecontent;
                        handler.sendMessage(message);
                    }else {
                        Message message=new Message();
                        message.what=FAILURE;
                        handler.sendMessage(message);
                        
                    }
                    httpURLConnection.disconnect();
                }catch (MalformedURLException e){

                }catch (IOException e){

                }catch (Exception e){

                }
            }
        }).start();
    }
    //读取字符缓冲区的内容
    private String readInput(BufferedReader in ){
        String temp=null;
        try {
         temp = in.readLine();

        }catch (Exception e){

        }
        Log.d("接受为：","="+temp);
        return temp;
    }

}
