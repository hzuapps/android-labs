package com.example.pictureviewing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class Com1314080901224Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901224);
		// 通过findViewById找到资源
		Gallery mygallery = (Gallery) findViewById(R.id.mygallery);
		// 添加一个adapter对象给Gallery
		mygallery.setAdapter(new ImageAdapter(this,getFromSd()));
		// 点击图片时显示图片的标号
		mygallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(Com1314080901224Activity.this,
						"这是图片" + arg2 + "号", Toast.LENGTH_SHORT).show();
			}

		});
	}

	private List<String> getFromSd() {
		// 设置路径
		List<String> it = new ArrayList<String>();
		File f = new File("/mnt/sdcard");
		//返回f文件下的所有元素
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (getImageFile(file.getPath())) {
				it.add(file.getPath());
			}
		}
		return it;
		
	}
	
	//判断图片的类型
	private boolean getImageFile(String fName){
		boolean re;
		//substring(from,to)：从start到end
		//lastIndexOf：取fName的最后一个.的字符
		//toLowerCase：转换为小写字符
		String end=fName.substring(fName.lastIndexOf(".")+1,
                 fName.length()).toLowerCase(); 
		if(end.equals("jpg")||end.equals("gif")||end.equals("png")
	            ||end.equals("jpeg")||end.equals("bmp"))
	    {
	      re=true;
	    }
	    else
	    {
	      re=false;
	    }
	    return re; 
	}

	// 改写BaseAdapter
	public class ImageAdapter extends BaseAdapter {

		private int mGalleryItemBackground;
		private Context mContext;
		private List<String> list;
		// 构造函数
		public ImageAdapter(Context c,List<String> li) {
			mContext = c;
			list=li;
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery);
			// 获取Gallery属性的id
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery_android_galleryItemBackground, 0);
			// 使得对象styleable属性能够反复使用
			a.recycle();
		}

		// 返回图片数目
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ImageView iv = new ImageView(mContext);
			// 设置图片
			//iv.setImageResource(myImageIds[arg0]);
			Bitmap bm=BitmapFactory.decodeFile(list.get(arg0).toString());
			iv.setImageBitmap(bm);
			// 设置图片宽高
			iv.setScaleType(ScaleType.FIT_XY);
			// 设置Layout宽高
			iv.setLayoutParams(new Gallery.LayoutParams(300, 250));
			// 设置背景图
			iv.setBackgroundResource(mGalleryItemBackground);
			return iv;
		}

//		private Integer[] myImageIds = { 
//				R.drawable.q1, 
//				R.drawable.q2,
//				R.drawable.q3, 
//				R.drawable.q4, 
//				R.drawable.q5, 
//				R.drawable.q6,
//				R.drawable.q7, 
//				R.drawable.q8,
//		};

	}
}
