package com.example.com1314080901203;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class Com1314080901203Activity extends Activity {
	
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	int[] imgRes = {R.id.id_tab_chatroom_img,R.id.id_tab_contact_img,R.id.id_Messagesender_img};
	Button send;
	ImageButton contact,chatroom,sender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  
		setContentView(R.layout.activity_com1314080901203);
		contact=(ImageButton)findViewById(R.id.id_tab_contact_img);
		chatroom=(ImageButton)findViewById(R.id.id_tab_chatroom_img);
		send=(Button)findViewById(R.id.SEND);
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        //设置Page间间距
		mViewPager.setPageMargin(20);
        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mPagerAdapter = new PagerAdapter()
        {
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
            	ImageButton view = new ImageButton(Com1314080901203Activity.this);
                view.setImageResource(imgRes[position]);
                container.addView(view);
                return view;
            }

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				 return imgRes.length;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
            
			 @Override
	            public void destroyItem(ViewGroup container, int position, Object object)
	            {
	                container.removeView((View) object);
	            }

         });
	}
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
		 mViewPager.setAdapter(mPagerAdapter);
		 return true;
	    }
	
}
