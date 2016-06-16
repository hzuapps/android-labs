package edu.hzuapps.androidworks.homeworks.net1314080903115;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import edu.hzuapps.androidworks.homeworks.net1314080903115.db.Net1314080903115_Account;
import edu.hzuapps.androidworks.homeworks.net1314080903115.db.Net1314080903115_Constant;
import edu.hzuapps.androidworks.homeworks.net1314080903115.db.Net1314080903115_MySQLiteOpenHelper;
import edu.hzuapps.androidworks.homeworks.net1314080903115.view.Net1314080903115_MySlidingMenuView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Net1314080903115MainActivity extends Activity implements View.OnClickListener {

    private Net1314080903115_MySlidingMenuView slidingMenu;
    private ImageButton main_portrait;
    private Net1314080903115_MySQLiteOpenHelper helper;
    public static Net1314080903115_Account Account=new Net1314080903115_Account();
    private ImageView imageview;
    private Button zhaoxiang;

    private  Uri OutImageUri = Uri.parse("file://"
            + Environment.getExternalStorageDirectory() + "/lzf.jpg");
    //public static Activity THIS2;
    private TextView name_text1, name_text2;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String json=(String)msg.obj;
            List<JSONObject> listjsonObject = getJSON(json);
            JSONObject j = listjsonObject.get(0);
            String state = null;
            String NoteTable;
            try {
                state = j.getString("state");
                NoteTable = j.getString("content");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(state.equals("1"))
            {//登录成功
                Account.State=2;
                name_text1.setText(Account.UserName);
                name_text2.setText(Account.UserName);
            }else{//登录失败
                Account.State=0;
                name_text1.setText("请登录");
                name_text2.setText("请登录");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.net1314080903115main);


        slidingMenu = (Net1314080903115_MySlidingMenuView) findViewById(R.id.sliding_menu);
        slidingMenu.setlSlidingMenuState(Net1314080903115_MySlidingMenuView.SLIDING_MENU_COVER);

        main_portrait = (ImageButton) findViewById(R.id.main_portrait);
        main_portrait.setOnClickListener(this);

        //http = new Net1314080903115_HTTP();

        name_text1 = (TextView) findViewById(R.id.name_text1);
        name_text2 = (TextView) findViewById(R.id.name_text2);
        imageview = (ImageView) findViewById(R.id.imageview);
        zhaoxiang = (Button)findViewById(R.id.zhaoxiang);
        zhaoxiang.setOnClickListener(this);

        helper = new Net1314080903115_MySQLiteOpenHelper(this);
        Login();
        if (Account.State == 0) {
            name_text1.setText("请登录");
            name_text2.setText("请登录");
        } else if (Account.State == 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpGet httpGet = new HttpGet("http://lyongsb.cn:81/CloudNotes.asmx/Login?Phone=" + Account.Phone + "&PassWord=" + Account.PassWord);//编者按：与HttpPost区别所在，这里是将参数在地址中传递
                        HttpResponse response = new DefaultHttpClient().execute(httpGet);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity entity = response.getEntity();
                            String json3 = EntityUtils.toString(entity);//这里取得的是带xml头的字符串
                            String json2 = json3.substring(json3.indexOf("{"), json3.indexOf("}") + 1);//去掉头和尾取得中间的json字符串
                            Message msg = Message.obtain();
                            msg.obj = json2;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                    }
                }
            }).start();
        } else if (Account.State == 2) {
            name_text1.setText(Account.UserName);
            name_text2.setText(Account.UserName);
        }
    }

    public void onClick(View v) {


        if (v.getId() == R.id.zhaoxiang) {

            Uri OutImageUri = Uri.parse("file://"
                    + Environment.getExternalStorageDirectory() +"/lzf.jpg");
            Intent intent;
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra("return-data", false);// 是否返回数据
            // 部分手机，不返回数据才能直接输出文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, OutImageUri);// 直接输出文件
            startActivityForResult(intent, 0);

        }


        if (v.getId() == R.id.main_portrait) {
            if(Account.State == 2)
            {
                //跳转账户资料界面
            }else{
                Intent intent = new Intent(Net1314080903115MainActivity.this, Net1314080903115LoginActivity.class);
                startActivity(intent);
            }

        }
    }

    private void Login() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Net1314080903115_Constant.TABLE_Account
                + " where ID=?", new String[]{"1"});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Account.ID = cursor.getInt(cursor
                        .getColumnIndex(Net1314080903115_Constant.Account_ID));
                Account.Phone = cursor.getString(cursor
                        .getColumnIndex(Net1314080903115_Constant.Account_Phone));
                Account.PassWord = cursor.getString(cursor
                        .getColumnIndex(Net1314080903115_Constant.Account_PassWord));
                Account.UserName = cursor.getString(cursor
                        .getColumnIndex(Net1314080903115_Constant.Account_UserName));
                Account.HeadPortrait = cursor.getString(cursor
                        .getColumnIndex(Net1314080903115_Constant.Account_HeadPortrait));
                Account.NoteTable = cursor.getString(cursor
                        .getColumnIndex(Net1314080903115_Constant.Account_NoteTable));
            } else {
                //Toast.makeText(this, "没有用户数据", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }
        db.close();
    }

    public List<JSONObject> getJSON(String json) {
        List<JSONObject> listjsonObject = new ArrayList<JSONObject>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
                listjsonObject.add(jsonObject);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return listjsonObject;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case 0:// 拍照完回调这里

                    String fileName =
                            Environment.getExternalStorageDirectory() + "/temp.jpg";
                    Bitmap bm = BitmapFactory.decodeFile(fileName);
                    imageview.setImageBitmap(bm);
                    break;
                default:
                    break;
            }
        }
    }
}
