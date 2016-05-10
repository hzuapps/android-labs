package edu.hzuapps.androidworks.homeworks.net1314080903133;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Net1314080903133EmptyClassroom extends Activity {

	private Button findButton;
	private EditText contentsEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.net1314080903133_empty_classroom);

		findButton = (Button) findViewById(R.id.button1Find);
		contentsEditText = (EditText) findViewById(R.id.editText1Contents);

		findButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content = "";
				for (int i = 0; i < 10; i++) {
					content += "待实现获取空教室：" + i + "\n";
				}
				contentsEditText.setText(content);

			}
		});

	}
}
