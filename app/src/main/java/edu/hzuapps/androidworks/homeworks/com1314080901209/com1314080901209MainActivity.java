package edu.hzuapps.androidworks.homeworks.com1314080901209;
import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import edu.hzuapps.androidworks.homeworks.com1314080901209.Drawl.GestureCallBack;

/**
 * 
 * @author chzayi
 * 
 */
public class com1314080901209MainActivity extends Activity {

	private FrameLayout body_layout;
	private ContentView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.com1314080901209activity_main);//手势锁页面
		body_layout = (FrameLayout) findViewById(R.id.body_layout);


		// 初始化一个显示各个点的viewGroup
		GestureCallBack t =new GestureCallBack(){

			public void checkedSuccess() {
				Toast.makeText(com1314080901209MainActivity.this,"校验成功", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void checkedFail() {
				Toast.makeText(com1314080901209MainActivity.this,"校验失败", Toast.LENGTH_SHORT).show();
			}
		};
		content = new ContentView(this,"14753",t);
		//设置手势解锁显示到哪个布局里面
		content.setParentView(body_layout);
	}

}
