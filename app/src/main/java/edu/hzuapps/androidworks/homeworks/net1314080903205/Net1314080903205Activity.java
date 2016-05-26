package edu.hzuapps.androidworks.homeworks.net1314080903205;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Net1314080903205Activity extends AppCompatActivity  implements View.OnClickListener {
    private EditText et;
    private Button send_btn;
    private Button error_check;
    private TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903205_main);
        et= (EditText) findViewById(R.id.editText);
        send_btn = (Button) findViewById(R.id.button);
        tx =(TextView)findViewById(R.id.textview4);
        error_check=(Button)findViewById(R.id.button2);
        send_btn.setOnClickListener(this);
        error_check.setOnClickListener(this);
    }

    public  void jiexi(String shuju){


        try {
            JSONObject jh = new JSONObject(shuju);
            if(jh.getInt("error_code")==0){

                ;
                String RZ=jh.getString("result");
                JSONObject jx = new JSONObject(RZ);
                String  model = jx.getString("phone_model");
                String  id = jx.getString("serial_number");
                String  IMEI = jx.getString("imei_number");
                String  act = jx.getString("active");
                String  tel_support = jx.getString("tele_support");
                String  tel_s_s = jx.getString("tele_support_status");
                String  war = jx.getString("warranty");
                String  was = jx.getString("warranty_status");
                String  made_a = jx.getString("made_area");
                String  start_d = jx.getString("start_date");
                String  color = jx.getString("color");
                String  size = jx.getString("size");
                String RD ="型号："+model+"\n"+"序列号："+id+"\n"+"IMEI："+IMEI+"\n"+war+"保修状态："+war+"\n"+"生产工厂："+made_a+"\n"+"生产日期："+start_d+"\n"+ "颜色："+color+"\n"+"容量："+size;
                tx.setText(RD);

            }else{

                String R =jh.getString("error_code")+":"+jh.getString("reason");
                tx.setText(R);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void go(){
        String b=et.getText().toString();
        final String a ="http://apis.juhe.cn/appleinfo/index?sn="+b+"&key=adfb120cdf079cf95264627f730b167c";
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {

                StringBuffer sb = new StringBuffer();
                String line = null;
                BufferedReader buffer = null;
                try{
                    URL url = new URL(a);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestMethod("GET");
                    buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    while ((line = buffer.readLine()) != null) {
                        sb.append(line);}
                    buffer.close();
                    return sb.toString();

                }catch (Exception e){}



                return null;
            }

            @Override
            protected void onPostExecute(String s) {


                jiexi(s);

                super.onPostExecute(s);
            }
        }.execute();
    }
    public void check(){
        String error_tips;
        error_tips="203701:序列号不能为空\n203702:网络错误\n203704:您输入的IMEI/MEID/序列号已经被苹果更换\n203705:苹果官方系统维护，暂时无法查询\n";
        tx.setText(error_tips);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:

                go();
            case R.id.button2:
                check();
        }

    }
}

