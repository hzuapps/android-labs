package edu.hzuapps.androidworks.homeworks.net1314080903117;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 这里是程序的MainActivity
 * 继承了FragmentActivity
 * 实现了点击监听事件
 * @author Charlie
 *
 */
public class Net1314080903117Activity extends FragmentActivity implements OnClickListener {
	//将底部布局加入
	private LinearLayout mTabHome;
	private LinearLayout mTabMessage;
	private LinearLayout mTabSendMsg;
	private LinearLayout mTabSearch;
	private LinearLayout mTabMy;
	
	//底部Tab按钮的图标按钮
	private ImageButton mImgHome;
	private ImageButton mImgMessage;
	private ImageButton mImgSendMsg;
	private ImageButton mImgSearch;
	private ImageButton mImgMy;

	//底部Tab按钮的文字控件
	private TextView mTextHome;
	private TextView mTextMessage;
	private TextView mTextSearch;
	private TextView mTextMy;

	//各个界面的Fragment
	private Fragment mFrgHome;
	private Fragment mFrgMessage;
	private Fragment mFrgSendMsg;
	private Fragment mFrgSearch;
	private Fragment mFrgMy;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_net1314080903117);
	
		//实例化控件
		initView();
		//用于初始化事件
		initEvent();
		//当进入软件后，默认加载Home首页的界面
		setSelect(0);
		
	}


	/**
	 * 这个方法主要用于控件的初始化，包括布局，和各个按钮
	 */
	private void initView() {
		/**
		 * Tab LinearLayout布局查找
		 */
		mTabHome = (LinearLayout) findViewById(R.id.tab_home);
		mTabMessage = (LinearLayout) findViewById(R.id.tab_message);
		mTabSendMsg = (LinearLayout) findViewById(R.id.tab_sendmessage);
		mTabSearch = (LinearLayout) findViewById(R.id.tab_search);
		mTabMy = (LinearLayout) findViewById(R.id.tab_myself);
		
		/**
		 * Tab 按钮中的图标Button查找
		 */
		mImgHome = (ImageButton) findViewById(R.id.tab_home_img);
		mImgMessage = (ImageButton) findViewById(R.id.tab_message_img);
		mImgSendMsg = (ImageButton) findViewById(R.id.tab_sendmessage_img);
		mImgSearch = (ImageButton) findViewById(R.id.tab_search_img);
		mImgMy = (ImageButton) findViewById(R.id.tab_myself_img);

		/**
		 * Tab 按钮中底部的文字TextView查找
		 */
		mTextHome = (TextView) findViewById(R.id.tab_home_tv);
		mTextMessage = (TextView) findViewById(R.id.tab_message_tv);
		mTextSearch = (TextView) findViewById(R.id.tab_search_tv);
		mTextMy = (TextView) findViewById(R.id.tab_myself_tv);


	}
	
	/**
	 * 控件监听事件的注册
	 */
	private void initEvent() {
		mTabHome.setOnClickListener(this);
		mTabMessage.setOnClickListener(this);
		mTabSendMsg.setOnClickListener(this);
		mTabSearch.setOnClickListener(this);
		mTabMy.setOnClickListener(this);
		
	}
	
	/**
	 * 选择当前界面
	 * @param i
	 */
	private void setSelect(int i){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		/**
		 * 在现实新的界面的时候，先隐藏之前的界面
		 * 通过Switch判断
		 */
		hideFragment(transaction);
		
		switch (i) {
		case 0:
			/**
			 * 先判断当前布局是否存在，再进行界面绘制，防止内存浪费
			 * 1.当前视图不存在，就创建
			 * 2.视图存在，则直接显示
			 * 其他case 也一样
			 */
			if (mFrgHome == null) {
				mFrgHome = new Net1314080903117HomeFragment();

				
				transaction.add(R.id.id_content, mFrgHome);
			}else {
				transaction.show(mFrgHome);
			}
			//选择了该界面，则对应的图标换成有颜色的
			mImgHome.setImageResource(R.mipmap.tabbar_home_highlighted);
			//选择了该界面，则对应的文字颜色也改变
			mTextHome.setTextColor(Color.parseColor("#FF8E29"));
			break;
		case 1:
			if (mFrgMessage == null) {
				mFrgMessage = new Net1314080903117MessageFragment();
				transaction.add(R.id.id_content, mFrgMessage);
				
			}else {
				transaction.show(mFrgMessage);
			}
			mImgMessage.setImageResource(R.mipmap.tabbar_message_center_highlighted);
			mTextMessage.setTextColor(Color.parseColor("#FF8E29"));
			break;
			
		case 2:
			if (mFrgSendMsg == null) {
				mFrgSendMsg = new Net1314080903117SendMsgFragment();
				transaction.add(R.id.id_content, mFrgSendMsg);
			}else {
				transaction.show(mFrgSendMsg);
			}
			mImgSendMsg.setImageResource(R.mipmap.navigationbar_subsribe_manage_highlighted);
			break;
			
		case 3:
			if (mFrgSearch == null) {
				mFrgSearch = new Net1314080903117SearchFragment();
				transaction.add(R.id.id_content, mFrgSearch);
				
			}else {
				transaction.show(mFrgSearch);
			}
			mImgSearch.setImageResource(R.mipmap.tabbar_discover_highlighted);
			mTextSearch.setTextColor(Color.parseColor("#FF8E29"));
			break;
		case 4:
			if (mFrgMy == null) {
				mFrgMy = new Net1314080903117MyFragment();
				transaction.add(R.id.id_content, mFrgMy);
			}else {
				transaction.show(mFrgMy);
			}
			mImgMy.setImageResource(R.mipmap.tabbar_profile_highlighted);
			mTextMy.setTextColor(Color.parseColor("#FF8E29"));
			break;
		

		default:
			break;
		}
		//提交事务
		transaction.commit();
	}
	
	/**
	 * 拥有隐藏Fragment，防止界面重叠
	 * @param transaction
	 */
	private void hideFragment(FragmentTransaction transaction)
	{
		/**
		 * 判断当前显示界面是否为空，不为空就需要隐藏，空表示该界面并未显示，不需隐藏操作
		 */
		if (mFrgHome != null)
		{
			transaction.hide(mFrgHome);
		}
		if (mFrgMessage != null)
		{
			transaction.hide(mFrgMessage);
		}
		if (mFrgSendMsg != null)
		{
			transaction.hide(mFrgSendMsg);
		}
		if (mFrgSearch != null)
		{
			transaction.hide(mFrgSearch);
		}
		if (mFrgMy != null)
		{
			transaction.hide(mFrgMy);
		}
	}
	
	/**
	 * 每次选定当前界面的时候，需要调用该方法，用于界面底部Tab各个tab的图标初始化，恢复成无颜色状态
	 */
	private void resetImgs(){
		mImgHome.setImageResource(R.mipmap.tabbar_home);
		mImgMessage.setImageResource(R.mipmap.tabbar_message_center);
		mImgSendMsg.setImageResource(R.mipmap.navigationbar_subsribe_manage);
		mImgSearch.setImageResource(R.mipmap.tabbar_discover);
		mImgMy.setImageResource(R.mipmap.tabbar_profile);

		/**
		 * 设定底部的颜色为黑色
		 */
		mTextHome.setTextColor(Color.BLACK);
		mTextMessage.setTextColor(Color.BLACK);
		mTextSearch.setTextColor(Color.BLACK);
		mTextMy.setTextColor(Color.BLACK);
	}


/**
 * 监听事件的处理，根据点击的按钮，来选定要显示的界面
 */
	@Override
	public void onClick(View v) {
		resetImgs();//初始化各个图标的颜色，先将各个图标设置为无颜色图标
		switch (v.getId()) {
		case R.id.tab_home:
			setSelect(0);
			break;
		case R.id.tab_message:
			setSelect(1);
			break;
		case R.id.tab_sendmessage:
			setSelect(2);
			break;
		case R.id.tab_search:
			setSelect(3);
			break;
		case R.id.tab_myself:
			setSelect(4);
			break;

		default:
			break;
		}
		
	}

	
}
