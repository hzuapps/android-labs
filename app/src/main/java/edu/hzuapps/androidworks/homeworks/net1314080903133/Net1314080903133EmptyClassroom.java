package edu.hzuapps.androidworks.homeworks.net1314080903133;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 
 Copyright ? 2016 Authors. All rights reserved.
 *
 * FileName: Net1314080903133EmptyClassroom.java
 * @author : Wu_Being <1040003585@qq.com>
 * Date/Time: 2016-6-6/下午7:30:18
 * Description: 
 */
public class Net1314080903133EmptyClassroom extends Activity {

	private static Button findButton = null;
	private static TextView contentstextView2 = null;

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
				/*
				 * try { // URL hzuPeopleAPPUrl = new
				 * URL("https://www.baidu.com");// // URL hzuPeopleAPPUrl = new
				 * URL( //
				 * "http://1.zooo.vipsinaapp.com/miapp/index.php?aid=ks&uid=olOSijlybNLFRSBN7ub12yQQSkxY&svr=1.wechatlab.vipsinaapp.com"
				 * ); URL hzuPeopleAPPUrl = new URL(
				 * "http://202.192.224.137/xsxx.aspx?xh=1314080903134");// ok
				 * BufferedReader bufferedReader = null; try { // InputStream
				 * inputStream = // hzuPeopleAPPUrl.openStream(); //
				 * InputStreamReader inputStreamReader = new //
				 * InputStreamReader( // inputStream); // bufferedReader = new
				 * // BufferedReader(inputStreamReader);
				 * 
				 * bufferedReader = new BufferedReader( new
				 * InputStreamReader(hzuPeopleAPPUrl .openStream())); String
				 * readline = null; while ((readline =
				 * bufferedReader.readLine()) != null) { /** deal with from this
				 * function
				 * 
				 * // DealWithString(readline); System.out.println(readline);
				 * contentsEditText.setText(readline); } } catch (IOException e)
				 * { // TODO Auto-generated catch block
				 * System.out.println("IOException"); e.printStackTrace(); }
				 * finally { try { System.out
				 * .println("finally bufferedReader.close();"); if
				 * (bufferedReader != null) { bufferedReader.close(); } } catch
				 * (IOException e) { // TODO Auto-generated catch block
				 * System.out.println("finally IOException");
				 * e.printStackTrace(); } }
				 * 
				 * } catch (MalformedURLException e) { // TODO Auto-generated
				 * catch block System.out.println("MalformedURLException");
				 * e.printStackTrace(); }
				 */
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						System.out.println("starting.....");
						int c;
						InputStream inputStream = null;
						try {

							URL url = new URL("http://1.zooo.vipsinaapp.com/miapp/index.php?aid=ks&uid=olOSijlybNLFRSBN7ub12yQQSkxY&svr=1.wechatlab.vipsinaapp.com");
							// URL url = new URL("https://www.baidu.com");
							System.out.println(url);
//							HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//							System.out.println(urlConnection);
//							urlConnection.connect();
							//inputStream = urlConnection.getInputStream();
							inputStream = url.openStream();
							String s="...";
							while ((c = inputStream.read()) != -1) {
								System.out.print((char) c);
								s += (char)c;
							}
							final String string = s ;
							
							runOnUiThread(new Runnable() {
								public void run() {
									//只能在ui线程修改ui
									contentstextView2.setText(string);
								
								}
							});
							
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {

							try {
								if (inputStream != null) {
									inputStream.close();
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();

							}

						}
					}
				}).start();

			}

		});

	}

	public static void DealWithString(String readline) {

		// TODO Auto-generated method stub
		// if (readline.length() > 2) {
		// String subString = readline.substring(0, 2);
		// // System.out.println(subString);
		// if ("kb".equals(subString)) {
		// System.out.println(readline);
		// }
		// }

		if (readline.startsWith("kb")) {
			System.out.println(readline);
		}

	}
}
