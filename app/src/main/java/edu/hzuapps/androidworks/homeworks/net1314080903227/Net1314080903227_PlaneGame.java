package edu.hzuapps.androidworks.homeworks;

import org.crazyit.event.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Net1314080903227_PlaneGame extends Activity
{
	// 定义飞机的移动速度
	private int speed = 10;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 去掉窗口标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 创建PlaneView组件
		final Net1314080903227_PlaneView planeView = new Net1314080903227_PlaneView(this);
		setContentView(planeView);
		planeView.setBackgroundResource(R.drawable.back);
		// 获取窗口管理器
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		// 获得屏幕宽和高
		display.getMetrics(metrics);
		// 设置飞机的初始位置
		planeView.currentX = metrics.widthPixels / 2;
		planeView.currentY = metrics.heightPixels - 40;
		// 为draw组件键盘事件绑定监听器
		planeView.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View source, int keyCode, KeyEvent event)
			{
				// 获取由哪个键触发的事件
				switch (event.getKeyCode())
				{
					// 控制飞机下移
					case KeyEvent.KEYCODE_S:
						planeView.currentY += speed;
						break;
					// 控制飞机上移
					case KeyEvent.KEYCODE_W:
						planeView.currentY -= speed;
						break;
					// 控制飞机左移
					case KeyEvent.KEYCODE_A:
						planeView.currentX -= speed;
						break;
					// 控制飞机右移
					case KeyEvent.KEYCODE_D:
						planeView.currentX += speed;
						break;
				}
				// 通知planeView组件重绘
				planeView.invalidate();
				return true;
			}
		});
	}
}