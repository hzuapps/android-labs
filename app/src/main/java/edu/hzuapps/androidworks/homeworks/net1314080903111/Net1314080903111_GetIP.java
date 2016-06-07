package edu.hzuapps.androidworks.homeworks.net1314080903111;

import edu.hzuapps.androidworks.homeworks.net1314080903111.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TableLayout;

public class Net1314080903111_GetIP extends Activity {
	String ipname = null;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
     	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);        
      
      	final Builder builder = new AlertDialog.Builder(this);   	      			
		builder.setTitle("登录服务器对话框");
		TableLayout loginForm = (TableLayout)getLayoutInflater().inflate( R.layout.login, null);		
		final EditText iptext = (EditText)loginForm.findViewById(R.id.ipedittext);				
		builder.setView(loginForm);                              
		builder.setPositiveButton("登录"
			, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ipname = iptext.getText().toString().trim();
					Bundle data = new Bundle();
					data.putString("ipname",ipname);					
					Intent intent = new Intent(Net1314080903111_GetIP.this,Net1314080903111_CameraTest.class);
					intent.putExtras(data);
					startActivity(intent);
				}
			});
		builder.setNegativeButton("取消"
			,  new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					System.exit(1);
				}
			});
		builder.create().show();
	}
}