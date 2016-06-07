package com.example.jsoupdemo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

/**
 * 通过给定链接地址，解析获取的html资源，返回封装好的ArrayList<Blog>对象
 */
public class BlogsFetchr {
	private static final String TAG = "BlogsFetchr";
	// 下载URL指定的资源
	public byte[] getUrlBytes(String urlSpec) throws IOException {
		URL url = new URL(urlSpec);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// 这里强制转换，是因为下面要用到HttpURLConnection.getInputStream
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream in = conn.getInputStream();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				Log.i(TAG, "连接不成功");// 连接不成功
				return null;
			}
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.close();
			return out.toByteArray();
		} finally {
			conn.disconnect();
		}
	}
	// 下载URL指定的资源(即将getUrlBytes方法的返回值byte[]转换成String类型)
	private String getUrl(String urlSpec) {
		String result = null;
		try {
			result = new String(getUrlBytes(urlSpec));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Blog> downloadBlogItems(String urlSpec) {
		ArrayList<Blog> blogs = new ArrayList<Blog>();
		String htmlString = getUrl(urlSpec);
		// 解析htmlString
		parserItems(blogs, htmlString);
		return blogs;
	}

	private void parserItems(ArrayList<Blog> blogs, String htmlString) {

		Document doc = Jsoup.parse(htmlString);
		Elements units = doc.getElementsByClass("blog_list");
		for (int i = 0; i < units.size(); i++) {
			Blog blog = new Blog();
			Element unit_ele = units.get(i);
			Element dl_ele = unit_ele.getElementsByTag("dl").get(0);
			Element dl_dt_ele = dl_ele.getElementsByTag("dt").get(0);
			Element dt_a_ele = dl_dt_ele.child(0);
			String iconUrl = dt_a_ele.child(0).attr("src"); // 博主头像链接
			Log.i(TAG, "文章" + i + "的博主头像链接：" + iconUrl);
			
			Elements fls = unit_ele.getElementsByClass("fl");
			Element fl_ele = fls.get(0);
			Element fl_a1_ele = fl_ele.child(0);
			String bloggerId = fl_a1_ele.text(); // 博主Id
			Log.i(TAG, "文章" + i + "的作者：" + bloggerId);
			
			blog.setBloggerIconUrl(iconUrl);
			blog.setBloggerId(bloggerId);
			blogs.add(blog);
		}
	}

}