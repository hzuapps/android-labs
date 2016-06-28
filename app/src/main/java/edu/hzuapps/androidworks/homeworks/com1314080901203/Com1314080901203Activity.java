package com.example.com1314080901203;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Com1314080901203Activity extends FragmentActivity implements OnClickListener{
	
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;
	private List<Fragment> mFragments;
	private LinearLayout mTabCont;
	private LinearLayout mTabFire;
	private LinearLayout mTabSetTimer;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
	//*********************************
	Button send;
	EditText edit;
	
	TextView tv_chat,tv_cont,tv_send;
	ImageButton contactimg,chatroomimg,senderimg;
	RelativeLayout rl_chatroom;
	FrameLayout fl_contact;
	LinearLayout ll_sender;
	//*********************************
	TextView[] textview={tv_chat,tv_cont,tv_send};
	int currIndex  = 0;  
	private final static int COUNT = 12;// 初始化数组总数
    private Resources resources;
	
	String objString;
	String dateString;
	String timeString;
	String msgString;
	Button confirm;
	Sender sender;
	
    //**************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("www","first");
		super.onCreate(savedInstanceState);
		Log.i("www","second");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  
		Log.i("www","third");
		super.setContentView(R.layout.activity_com1314080901203);
		Log.i("www","fourth");
		resources = getResources();
		Log.i("www","fifth");
		initView();//初始化界面
		Log.i("www","sixth");
        InitViewPager();//初始化滑动界面
        Log.i("www","seven");
       // initData();//初始化聊天室聊天纪录数据
        //初始化朋友数据
        Log.i("www","eighth");
        initEvent();
        setSelect(0);
        Log.i("www","ninth");
       
	}
	 private void initView() {//初始化所有的view
		
		 mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		 //tabs
		 rl_chatroom=(RelativeLayout)findViewById(R.id.layout_chatroom);
		 fl_contact=(FrameLayout)findViewById(R.id.layout_contact);
		 ll_sender=(LinearLayout)findViewById(R.id.layout_sender);
		 //Imagebutton
		 contactimg=(ImageButton)findViewById(R.id.id_tab_contact_img);
		 chatroomimg=(ImageButton)findViewById(R.id.id_tab_chatroom_img);
		 senderimg=(ImageButton)findViewById(R.id.id_Messagesender_img);
		//通过LayoutInflater引入布局，并将布局转化为view
		// LayoutInflater mInflater = LayoutInflater.from(this);//创建一个LayoutInflater对象
		 //mInflater.inflate(R.layout.chatroom, null);
	 }
	 private void initEvent()
		{
		 contactimg.setOnClickListener(this);
		 chatroomimg.setOnClickListener(this);
		 senderimg.setOnClickListener(this);
		}
	
		@SuppressWarnings("deprecation")
		private void InitViewPager() {
			// TODO Auto-generated method stub
	    	mViewPager = (ViewPager)findViewById(R.id.id_viewpager);
	    	mTabFire = (LinearLayout) findViewById(R.id.id_tab_firechat);
	    	mTabCont = (LinearLayout) findViewById(R.id.id_tab_contact);
			mTabSetTimer = (LinearLayout) findViewById(R.id.id_tab_setTimer);
	        
	        mFragments = new ArrayList<Fragment>();
	        Fragment senderfragment = new Sender();
	        Fragment Contactframent=new Contact();
	        Fragment chatFragment=new Chatroom();

	        mFragments.add(chatFragment);
	      
	        mFragments.add(Contactframent);
	        mFragments.add(senderfragment);
	        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
			{
	        	// TODO Auto-generated method stub
				@Override
				public int getCount()
				{// TODO Auto-generated method stub
					return mFragments.size();
				}

				@Override
				public Fragment getItem(int arg0)
				{// TODO Auto-generated method stub
					return mFragments.get(arg0);
				}
			};
			mViewPager.setAdapter(mPagerAdapter);
			
			mViewPager.setOnPageChangeListener(new OnPageChangeListener()
			{
				// TODO Auto-generated method stub
				@Override
				public void onPageSelected(int arg0)
				{// TODO Auto-generated method stub
					int currentItem = mViewPager.getCurrentItem();
					setTab(currentItem);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0)
				{
					// TODO Auto-generated method stub
					
				}
				
			});
	    }
	    @Override
		public void onClick(View v)
		{// TODO Auto-generated method stub
			switch (v.getId())
			{// TODO Auto-generated method stub
			case R.id.id_tab_chatroom_img:
				setSelect(0);
				break;
			case R.id.id_tab_contact_img:
				setSelect(1);
				break;
			case  R.id.id_Messagesender_img:
			
				setSelect(2);
				break;
			default:
				break;
			}
		}

		private void setSelect(int i)
		{// TODO Auto-generated method stub
			setTab(i);
			mViewPager.setCurrentItem(i);
		}

		private void setTab(int i)
		{// TODO Auto-generated method stub
			resetImgs();
		/*
		 * */
			switch (i)
			{
			case 0:
				chatroomimg.setImageResource(R.drawable.tab_weixin_pressed);
				break;
			case 1:
				contactimg.setImageResource(R.drawable.tab_find_frd_pressed);
				break;
			case 2:senderimg.setImageResource(R.drawable.tab_settings_pressed);
				break;
			}
		}

		/**
		 * �л�ͼƬ����ɫ
		 */
		private void resetImgs()
		{// TODO Auto-generated method stub
			chatroomimg.setImageResource(R.drawable.tab_weixin_normal);
			contactimg.setImageResource(R.drawable.tab_find_frd_normal);
			senderimg.setImageResource(R.drawable.tab_settings_normal);
		}
		
}
