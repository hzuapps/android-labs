package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home);

			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(1000);
						shiftActivityToHome();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).start();
			
		}
		
		private void shiftActivityToHome(){
			Intent intent=new Intent(HomeActivity.this,MainActivity.class);
			startActivity(intent);
			//把当前也关闭
			HomeActivity.this.finish();
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

}
