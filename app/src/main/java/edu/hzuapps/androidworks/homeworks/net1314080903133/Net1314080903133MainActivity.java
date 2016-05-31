package edu.hzuapps.androidworks.homeworks.net1314080903133;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Net1314080903133MainActivity extends Activity {

	private Button enterEmptyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net1314080903133_main);

		enterEmptyButton = (Button) findViewById(R.id.button1open);
		enterEmptyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Net1314080903133MainActivity.this,
						Net1314080903133EmptyClassroom.class);

				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.net1314080903133_main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		System.out.println("onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		System.out.println("onPause");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		System.out.println("onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		System.out.println("onDestroy");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		System.out.println("onRestart");
	}

}
