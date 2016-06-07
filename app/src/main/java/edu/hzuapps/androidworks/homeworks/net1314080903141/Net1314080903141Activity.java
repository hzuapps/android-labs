package edu.hzuapps.androidworks.homeworks.net1314080903141;


import edu.hzuapps.androidworks.homeworks.net131240809031413.R;
import edu.hzuapps.androidworks.homeworks.net13140809031412.Net1314080903141runActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Net1314080903141Activity extends Activity {

	// 麦克风开关图片Button
	private ImageButton state;
	Net1314080903141runActivity run_Metned = new Net1314080903141runActivity();
	Thread thread = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net1314080903141);

		// 绑定控件
		this.state = (ImageButton) findViewById(R.id.state);
		this.state.setOnClickListener(new ocl());
	}

	// 记录麦克风状态的布尔值
	private boolean is = false;

	// 监听类
	class ocl implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.state && is == true) {
				// 切换图片按钮的背景图【开】——————【关】
				state.setImageDrawable(getResources().getDrawable(
						R.drawable.net1314080903141close));
				run_Metned.no();

				// 改变状态
				is = false;
			} else if (v.getId() == R.id.state && is == false) {
				// 切换图片按钮的背景图【关】——————【开】
				state.setImageDrawable(getResources().getDrawable(
						R.drawable.net1314080903141open));
				// 改变状态
				try {
					Thread thread = new Thread(run_Metned);

					thread.start();
				} catch (Exception e) {

				}
				is = true;
			}
		}
	}

	int i = 0;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			i++;
			if (i == 3) {
				finish();
			}

		}
		return true;
	}
}
