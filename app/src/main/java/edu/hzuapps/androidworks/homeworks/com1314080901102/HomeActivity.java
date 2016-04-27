package edu.hzuapps.androidworks.homeworks.com1314080901102;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.hzuapps.androidworks.homeworks.simple_2048.R;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        String account = intent.getStringExtra(Com1314080901102MainActivity.EXTRA_MESSAGE);
        String password = intent.getStringExtra(Com1314080901102MainActivity.EXTRA_PS);
        Log.v("debug","sdasd:" + account + ";" + password);
//        TextView textView = (TextView)findViewById(R.id.message);
        TextView textView = new TextView(this);
        textView.setText("登陆信息"+account+";"+password);

        setContentView(textView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
