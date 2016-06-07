package com.example.jsoupdemo;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class ThumbnailDownloader<Token> extends HandlerThread {
	// Token表示泛型，"类名<泛型>"以保证在类内可以使用Token，就像Token已经是定义好的类一样

	private static final String TAG = "ThumbnailDownloader";
	private static final int MESSAGE_DOWNLOAD = 0;
	private Handler mHandler; // 发送下载图片的指令，和处理下载图片的指令的使者
	private Handler mResponseHandler; // 来自主线程的Handler,更新UI
	private Listener<Token> mListener;
	private Map<Token, String> requestMap = Collections
			.synchronizedMap(new HashMap<Token, String>());
	// 保存ImageView和URL的键值对，并是线程安全的
	private LruCache<String, Bitmap> mMemoryCache;

	// 缓存图片的类，当存储图片的大小大于LruCache设定的值，系统自动释放内存

	public ThumbnailDownloader(Handler handler) {
		super(TAG);
		mResponseHandler = handler;
		// 创建一个名为TAG的HandlerThread,是拥有自己Looper的独立线程
		// super(TAG) 相当于new HandlerThread(TAG)
		int maxMemory = (int) Runtime.getRuntime().maxMemory(); // 系统最大运行内存
		int mCacheSize = maxMemory / 8; // 分配给缓存的内存大小
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {
			// 必须重写此方法，来测量Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};

	}

	public interface Listener<Token> { // 回调方法，在主线程中实现
		void onThumbnailDownloaded(Token token, Bitmap thumbnail);
	}

	public void setListener(Listener<Token> listener) {
		mListener = listener;
	}

	@Override
	public void onLooperPrepared() {
		// 在此线程的Looper启动循环准备时段运行的方法
		mHandler = new Handler() { // 在当前线程新建的Handler,只会在当前线程运行
			@Override
			public void handleMessage(Message message) {
				// 处理发送过来的图片下载消息，下载图片并更新UI
				if (message.what == MESSAGE_DOWNLOAD) {
					Token token = (Token) message.obj;
					try {
						handleRequest(token);
						// 处理消息
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		};
	}

	private void handleRequest(final Token token) throws IOException {

		final String url = requestMap.get(token);
		if (url == null)
			return;

		byte[] bitmapBytes = new BlogsFetchr().getUrlBytes(url);
		// 下载图片
		final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0,
				bitmapBytes.length);
		String key = (String) ((ImageView) token).getTag();
		Log.i(TAG, "imageView的TAG是：" + key);
		mMemoryCache.put(key, bitmap); // 存入缓存

		mResponseHandler.post(new Runnable() {

			@Override
			public void run() {
				// 更新UI
				if (requestMap.get(token) != url)
					return;
				requestMap.remove(token);
				mListener.onThumbnailDownloaded(token, bitmap);// 更新UI
			}
		});

	}

	public void clearQueue() {
		mHandler.removeMessages(MESSAGE_DOWNLOAD);
		requestMap.clear();
	}

	public void queueThumbnail(Token token, String url) {
		// 将下载图片命令加入"ThumbnailDownloader"消息队列，
		// 在PhotoGalleryFragment中被调用

		requestMap.put(token, url);

		Message message = mHandler.obtainMessage(MESSAGE_DOWNLOAD, token);
		// 获取Message,并且自动与mHandler绑定在一起
		// 参数一: what，int型，用于描述消息
		// 参数二: obj，随消息发送的指定对象
		// 参数三: target，处理消息的Handler，这里由于使用自动和mHandler绑定，故缺省
		message.sendToTarget(); // 发送消息给目标Handler

	}

	public Bitmap getCacheImage(String key) {
		// 获取缓存中的图片
		Bitmap bitmap = mMemoryCache.get(key);
		return bitmap;
	}

}