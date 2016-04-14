package edu.hzuapps.androidworks.homeworks.com1314080901102;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.util.Log;

import edu.hzuapps.androidworks.homeworks.simple_2048.R;


public class com1314080901102MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "edu.hzuapps.androidworks.homeworks.simple_2048.MESSAGE";
    public final static String EXTRA_PS = "edu.hzuapps.androidworks.homeworks.simple_2048.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com1314080901102_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_com1314080901102_main, menu);
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
    public void sendMessage(View view){
        Intent intent = new Intent(this,HomeActivity.class);
        EditText editText = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        String account = editText.getText().toString();
        String password = editText.getText().toString();

        Log.v("debug", "aaaaaaaaaaaaaaaaaaaaaaaa" + account + ";" + password);
        intent.putExtra(EXTRA_MESSAGE, account);
        intent.putExtra(EXTRA_PS,password);
        startActivity(intent);
    }
}
