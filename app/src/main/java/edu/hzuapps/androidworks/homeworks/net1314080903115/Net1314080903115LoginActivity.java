package edu.hzuapps.androidworks.homeworks.net1314080903115;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hzuapps.androidworks.R;


public class Net1314080903115LoginActivity extends Activity implements View.OnClickListener {

    private EditText login_Phone, login_PassWord;
    private Button login_Login, login_SignUp;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903115login);

        login_Phone = (EditText) findViewById(R.id.login_Phone);
        login_PassWord = (EditText) findViewById(R.id.login_PassWord);
        login_Login = (Button) findViewById(R.id.login_Login);
        login_Login.setOnClickListener(this);
        login_SignUp = (Button) findViewById(R.id.login_SignUp);
        login_SignUp.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onClick(View v) {

        if (v.getId() == R.id.login_Login) {

            final String phont = login_Phone.getText().toString();
            final String password = login_PassWord.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpGet httpGet = new HttpGet("http://lyongsb.cn:81/CloudNotes.asmx/Login?Phone=" + phont + "&PassWord=" + password);//编者按：与HttpPost区别所在，这里是将参数在地址中传递
                        HttpResponse response = new DefaultHttpClient().execute(httpGet);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity entity = response.getEntity();
                            String json3 = EntityUtils.toString(entity);//这里取得的是带xml头的字符串
                            String json2 = json3.substring(json3.indexOf("{"), json3.indexOf("}") + 1);//去掉头和尾取得中间的json字符串
                            Message msg = Message.obtain();
                            msg.obj =  json2;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                    }
                }
            }).start();

        }

        if (v.getId() == R.id.login_Login) {

        }

    }

    public JSONObject getJSON(String json) {
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
        }catch (JSONException e){

        }
        return jsonObject;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = (String) msg.obj;
            JSONObject jsonObject = getJSON(json);

            String state = "0";
            String NoteTable="";
            try {
                state = jsonObject.getString("state");
                NoteTable = jsonObject.getString("content");
                if (state.equals("1")) {
                    Net1314080903115MainActivity.Account.State = 2;
                    Net1314080903115MainActivity.Account.UserName = "黎振锋";
                    Intent intent = new Intent(Net1314080903115LoginActivity.this, Net1314080903115MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Net1314080903115LoginActivity.this, NoteTable, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}