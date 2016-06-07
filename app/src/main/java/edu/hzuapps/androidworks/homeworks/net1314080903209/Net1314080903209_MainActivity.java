package edu.hzuapps.androiworks.homeworks.net1314080903209.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class Net1314080903209_MainActivity extends AppCompatActivity {

    public static final int MSG_RECEIVES_CODE = 1;

    private EditText et_MyApplication = null;

    private Net1314080903209_SmsObserver mObserver;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_RECEIVES_CODE) {
                String code = (String) msg.obj;
                //更新UI
                et_MyApplication.setText(code);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903209_activity_main);

        et_MyApplication = (EditText)findViewById(R.id.et_myapplication);

        mObserver = new Net1314080903209_SmsObserver(Net1314080903209_MainActivity.this, mHandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri,true,mObserver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
