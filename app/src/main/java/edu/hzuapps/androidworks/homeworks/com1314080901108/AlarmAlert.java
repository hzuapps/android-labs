package com.example.czg.com1314080901108;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlarmAlert extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AlertDialog.Builder(AlarmAlert.this)
				.setIcon(R.drawable.clock)
				.setTitle("时间到了")
				.setMessage("快醒醒")
				.setPositiveButton("知道了",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								System.exit(0);
								android.os.Process
										.killProcess(android.os.Process
												.myPid());
							}
						}).show();
	}

}
