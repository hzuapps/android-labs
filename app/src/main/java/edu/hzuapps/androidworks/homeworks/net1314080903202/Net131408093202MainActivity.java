package edu.hzuapps.androidworks.homeworks.net1314080903202;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Net131408093202MainActivity extends Activity {
	EditText number = null;
	EditText message = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_net1314080903202);
        Button send = (Button) findViewById(R.id.Button);
        number = (EditText) findViewById(R.id.edit1);
        message = (EditText) findViewById(R.id.edit2);
        Listener lis = new Listener();
        send.setOnClickListener(lis);
    }


    private final class Listener implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String num = number.getText().toString();
			String msg = message.getText().toString();
			SmsManager manager = SmsManager.getDefault();
			ArrayList<String> texts = manager.divideMessage(msg);
			for (String text : texts) {
				manager.sendTextMessage(num, null, text, null, null);
			}
			Toast.makeText(getApplicationContext(), "send success", Toast.LENGTH_LONG).show();
		}
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
