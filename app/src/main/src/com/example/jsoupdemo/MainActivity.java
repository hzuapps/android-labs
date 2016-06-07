package com.example.jsoupdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private ListView mListView;
	private ArrayList<Blog> mBlogs; // 博客列表
	private String testUrl = "http://blog.csdn.net/column.html"; // 访问的链接，这里测试的CSDN博客专栏的首页
	private BlogsFetchr fetchr; // 下载html页面和解析它的工具对象
	private MyAdapter mAdapter;
	private ThumbnailDownloader<ImageView> mThumbnailDownloader; // 图片下载器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fetchr = new BlogsFetchr();
		mBlogs = new ArrayList<Blog>();
		Log.i(TAG, "mBlogs.size:" + mBlogs.size());
		Blog blog = new Blog();
		blog.setBloggerId("hello");
		mBlogs.add(blog);

		update(testUrl);
		// 开启响应下载图片消息的线程
		mThumbnailDownloader = new ThumbnailDownloader<ImageView>(new Handler());
		mThumbnailDownloader
				.setListener(new ThumbnailDownloader.Listener<ImageView>() {
					@Override
					public void onThumbnailDownloaded(ImageView imageView,
							Bitmap thumbnail) {
						// 更新UI，上图
						imageView.setImageBitmap(thumbnail);
					}
				});
		mThumbnailDownloader.start();
		mThumbnailDownloader.getLooper(); // 必须要在start之后
	}

	private void update(final String testUrl) {
		new AsyncTask<Void, Void, Void>() {
			protected Void doInBackground(Void... params) {
				mBlogs = fetchr.downloadBlogItems(testUrl); // 下载博客列表
				return null;
			};

			protected void onPostExecute(Void result) {
				// 更新ListView
				mListView = (ListView) findViewById(R.id.lv);
				mAdapter = new MyAdapter(mBlogs);
				mListView.setAdapter(mAdapter);
			}
		}.execute();

	}

	private class MyAdapter extends ArrayAdapter<Blog> {
		public MyAdapter(ArrayList<Blog> blogs) {
			super(MainActivity.this, 0, blogs);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item, null);
			}
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
			TextView tv = (TextView) convertView.findViewById(R.id.tv);

			tv.setText(getItem(position).getBloggerId());
			String imageUrl = getItem(position).getBloggerIconUrl();
			String a = "[^\\w]";
			String imageTag = imageUrl.replaceAll(a, "");
			iv.setTag(imageTag);
			// 去掉字符串中非（字母、数字、下划线）
			// 给imageView设置一个标签，用于存取于Cache
			Bitmap bitmap = null;
			if ((bitmap = mThumbnailDownloader.getCacheImage(imageTag)) != null) {
				// 如果在缓存中存在
				iv.setImageBitmap(bitmap);
			} else {
				// 发送下载图片消息
				mThumbnailDownloader.queueThumbnail(iv, imageUrl);
			}

			return convertView;
		}
	}
}