package com.example.dictionary;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置隐藏标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		/*
		 * this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		// 显示的布局是dier
		setContentView(R.layout.dier);
		// 取得TabHost对象
		mTabHost = getTabHost();
		// 将类OfflineQuery赋给intent，以便连接到这个页面上
		Intent intent = new Intent().setClass(this, OfflineQuery.class);
		// 新建一个newTabSpec(newTabSpec)
		// 设置其标签和图标(setIndicator)
		// 设置内容(setContent)
		mTabHost.addTab(mTabHost.newTabSpec("tab1")
				.setIndicator("", getResources().getDrawable(R.drawable.img1))
				.setContent(intent));
		Intent intent1 = new Intent().setClass(this, OnlineQuery.class);
		mTabHost.addTab(mTabHost.newTabSpec("tab2")
				.setIndicator("", getResources().getDrawable(R.drawable.img2))
				.setContent(intent1));
		Intent intent2 = new Intent().setClass(this, NoteBook.class);
		mTabHost.addTab(mTabHost.newTabSpec("tab3")
				.setIndicator("", getResources().getDrawable(R.drawable.img3))
				.setContent(intent2));
		mTabHost.setCurrentTab(0);

	}
}
