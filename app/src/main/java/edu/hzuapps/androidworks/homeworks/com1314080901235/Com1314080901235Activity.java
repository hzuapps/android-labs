package com.example.okhttp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MainActivity extends Activity {
	protected static final int MSG_WHERE = 0;
	protected static final int MSG_WHAT = 1;
	TextView tv_show, tv_show_return_code;

	String htmlStr, htmlStr_return_code;
	String realStrToken;
	Button bt_get_token, bt_return_code;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();

		// 获取token
		bt_get_token.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					public void run() {
						try {
							getToken();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}).start();
			}
		});

		// 获取返回码
		bt_return_code.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					public void run() {
						try {
							push_my_content();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}).start();
			}
		});

	}

	// 初始化
	private void findViewById() {
		tv_show = (TextView) findViewById(R.id.tv_show_token);
		tv_show_return_code = (TextView) findViewById(R.id.tv_return_code);
		bt_get_token = (Button) this.findViewById(R.id.bt);
		bt_return_code = (Button) this.findViewById(R.id.bt_getdata_next);

	}

	private void push_my_content() throws IOException {
		//测试得了数据
		/*final MediaType MEDIA_TYPE_MARKDOWN = MediaType
				.parse("text/x-markdown; charset=utf-8");

		final OkHttpClient client = new OkHttpClient();

		String postBody = "{\"filter\":{\"is_to_all\":true,\"group_id\":\"\"},\"text\":{\"content\":\"cON\"},\"msgtype\":\"text\"}";

		String myurl="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+realStrToken;
		Request request = new Request.Builder()
				.url(myurl)
				.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
				.build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);

		System.out.println(response.body().string());
*/
		
		URL url1=new URL("https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+realStrToken);
		HttpURLConnection connection1=(HttpURLConnection)url1.openConnection();			
		connection1.setDoOutput(true);
		connection1.setDoInput(true);
		connection1.setConnectTimeout(5000);
		connection1.setReadTimeout(5000);
		connection1.setRequestMethod("POST");
		
		String s2="{\"filter\":{\"is_to_all\":true,\"group_id\":\"\"},\"text\":{\"content\":\"cON\"},\"msgtype\":\"text\"}";
		System.out.println(s2);
		OutputStream out=connection1.getOutputStream();
		BufferedWriter sender=new BufferedWriter(new OutputStreamWriter(out));
		sender.write(s2);
		sender.flush();
		sender.close();
		
		InputStream in1=connection1.getInputStream();
		BufferedReader reader1=new BufferedReader(new InputStreamReader(in1));
		String s3=reader1.readLine();
		System.out.println(s3);
		
		connection1.disconnect();
	}

	private void getToken() {
		// 创建okHttpClient对象
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 构造Request对象，参数至少有url，通过Request.Builder设置更多的参数比如：header、method等。
		final Request mRequest = new Request.Builder()
				.url(
				// "http://169.254.176.143:8080/listview_item.xml").build();
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx1a1531ba312f34d0&secret=cb12c83dcef984692f8c771535eb9132")
				.build();

		// 通过request对象去构造得到Call对象，=将请求封装成任务，调用execute()和cancel()等方法。
		Call mCall = mOkHttpClient.newCall(mRequest);
		// 以异步的方式去执行请求，调用call.enqueue，将call请求加入调度队列，等待任务执行完成，在Callback中得到结果。
		mCall.enqueue(new Callback() {
			public void onFailure(Request request, IOException e) {
			}

			public void onResponse(final Response response) throws IOException {
				htmlStr = response.body().string();
				// Log.d("123", htmlStr);
				mHandler.sendEmptyMessage(MSG_WHAT);
			}
		});
	}

	private String getRealStringFromJSON(String htmlStr) {
		JSONObject json;
		String tmp = null;
		try {
			json = new JSONObject(htmlStr);
			tmp = json.get("access_token").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tmp;
	};

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT:
				tv_show.setText(htmlStr);
				realStrToken = getRealStringFromJSON(htmlStr);
				break;
			case MSG_WHERE:
				tv_show_return_code.setText(htmlStr_return_code);
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

}
