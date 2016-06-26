package com.example.intelligent_restranant_boss.weather;

/*import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;*/

public class WeatherService {

	/*public static String getWeather(String cityName) {
		String result = null;
		String url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&day=2&city="
				+ cityName + "&dfc=3";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet mothod = new HttpGet(url);
			HttpResponse httpResponse = client.execute(mothod);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity(),
						"gb2312");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}*/

}
