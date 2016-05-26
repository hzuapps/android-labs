package com.example.drawingboard;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Com1314080901225GetJsonActivity extends Activity {

	public RequestQueue requestQueue;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901225_get_json);
		requestQueue = Volley.newRequestQueue(this);
		tv = (TextView) findViewById(R.id.tv);
	}

	public void click(View v) {
		String path = "https://github.com/hzuapps/android-labs/blob/master/app/src/main/java/edu/hzuapps/androidworks/homeworks/com1314080901225/city.html";
		// String path="http://192.168.1.146:8080/city.html"

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(path, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						// System.out.println(response.toString());
						tv.setText(response.toString());
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "获取数据失败", 0)
								.show();
					}
				})
		// 返回的json数据是默认使用 ISO-8859-1编码模式，有乱码
		// 需要重写parseNetworkResponse这个方法解决乱码问题
		{
			protected Response<JSONObject> parseNetworkResponse(
					NetworkResponse response) {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(new String(response.data,
							"UTF-8"));
					return Response.success(jsonObject,
							HttpHeaderParser.parseCacheHeaders(response));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				} catch (JSONException e) {
					e.printStackTrace();
					return Response.error(new ParseError(e));
				}
			}

		};

		requestQueue.add(jsonObjectRequest);
	}

}
