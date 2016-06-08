package edu.hzuapps.androidworks.homeworks.net1314080903133;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: .java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-8/下午3:33:00
 * Description:
 */
public class Net1314080903133EmptyClassroom extends Activity {

	private static Button findButton = null; // 查询按键
	private static TextView contentstextView2 = null; // 空教室信息show
	private static String allemptyclassinfo = null; // 空教室信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903133_empty_classroom);

		findButton = (Button) findViewById(R.id.button1Find);
		contentstextView2 = (TextView) findViewById(R.id.textView2);

		findButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// 不能在安卓主线程进程网络流操作，要在创建的新线程中执行
				new Thread(new Runnable() {

					@Override
					public void run() {

						// System.out.println("starting.....");
						InputStream inputStream = null;
						DataInputStream dataInputStream = null;
						try {
							// 空教室信息来源URL
							URL url = new URL(
									"http://1.zooo.vipsinaapp.com/miapp/index.php?aid=ks&uid=olOSijlybNLFRSBN7ub12yQQSkxY&svr=1.wechatlab.vipsinaapp.com");
							// URL url = new URL("https://www.baidu.com");
							// System.out.println(url);

							inputStream = url.openStream();
							dataInputStream = new DataInputStream(inputStream);

							String line = ""; // 读取网络流的一行数据
							String info = ""; // 处理网络后的一行数据，存空教室信息
							while ((line = dataInputStream.readLine()) != null) {
								// System.out.println(line);
								info += DealWithString(line);
							}

							// UI线程中用到变量allemptyclassinfo，要设为final
							allemptyclassinfo = info;

						} catch (MalformedURLException e) {
							e.printStackTrace();
							// 异常处理
							allemptyclassinfo = "\n\n\n   1、URL协议、格式或者路径错误\n   2、jar问题 ";
							allemptyclassinfo += "\n\n\n  好好检查下你程序中的代码   "; 
						} catch (IOException e) {
							e.printStackTrace();
							// 异常处理
							allemptyclassinfo = "\n\n\n   西湖论坛的新浪服务器异常！！！\n\n   请过几天再试试看。";
						} finally {
							// 修改UI只能在UI线程中
							runOnUiThread(new Runnable() {
								public void run() {
									contentstextView2
											.setText(allemptyclassinfo);
								}
							});
							// 关闭网络流连接
							try {
								if (dataInputStream != null) {
									dataInputStream.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();// Thread()

			}

		});// setOnClickListener

	}

	/**
	 * 这个方法待改进！！！！！！！！
	 * 
	 * @param readline
	 *            待处理的字符串
	 * @return 空教室信息
	 */
	public static String DealWithString(String readline) {

		// html流字符中以"kb"开头的都包含空教室信息
		if (readline.startsWith("kb")) {
			System.out.println(readline);
			return readline;
		}
		// 如果html流字符没有以"kb"开头,则返回空字符串拼接
		return "test! ";

	}
}
