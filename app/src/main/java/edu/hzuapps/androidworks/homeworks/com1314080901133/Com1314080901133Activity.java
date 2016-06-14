package com.whd.imageviewpager;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.imageviewpager.R;
import com.whd.imageviewpager.Com1314080901133_ImageCycleView.ImageCycleViewListener;

public class Com1314080901133Activity extends Activity {

	private Com1314080901133_ImageCycleView mAdView;
	private ArrayList<String> mImageUrl = null;
	
	private ArrayList<String> mImageTitle = null;
	private String imageUrl1 = "http://www.yueqikan.com/uploads/141010/3425-14101011103Q57.jpg";
	private String imageUrl2 = "http://chinapic.people.com.cn/data/attachment/forum/201503/25/113355cxziyi1ud12vijad.jpg";
	private String imageUrl3 = "http://wiki.mdaxue.com/uploads/201210/1350886571YE7hD7NI.jpg";
	
	private String imageTitle1 = "惠州学院";
	private String imageTitle2 = "惠州学院夜景";
	private String imageTitle3 = "惠州学院计算机系";

	public int stype=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901133_main);
		mImageUrl = new ArrayList<String>();
		mImageUrl.add(imageUrl1);
		mImageUrl.add(imageUrl2);
		mImageUrl.add(imageUrl3);
		
		
		mImageTitle = new ArrayList<String>();
		mImageTitle.add(imageTitle1);
		mImageTitle.add(imageTitle2);
		mImageTitle.add(imageTitle3);
		mAdView = (Com1314080901133_ImageCycleView) findViewById(R.id.ad_view);
		mAdView.setImageResources(mImageUrl ,mImageTitle, mAdCycleViewListener,stype);
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {
		@Override
		public void onImageClick(int position, View imageView) {
			
			
			Toast.makeText(Com1314080901133Activity.this, mImageUrl.get(position)+position, 1).show();
		}
	};
}
