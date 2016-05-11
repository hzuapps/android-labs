package edu.hzuapps.androidworks.homeworks.net1314080903117;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.hzuapps.androidworks.homeworks.net1314080903117.Net1314080903117ReflashListView.IReflashListener;

/**
 * 该界面为首页界面，实现了自定义ListView中的下拉刷新的接口
 * @author Charlie
 *
 */
@SuppressLint("SimpleDateFormat")
public class Net1314080903117HomeFragment extends Fragment implements IReflashListener {

	private List<Net1314080903117Message> mList;//List容器，用于微博消息数据的添加
	private Net1314080903117MessageAdapter mAdapter;//ListView的Adapter
	private Net1314080903117ReflashListView mReflashListView;//自定义的ListView
	/**
	 * 用于获取当前时间和时间格式的转换
	 */
	private Date date ;
	private SimpleDateFormat format;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/**
		 * 根据资源ID 绑定当前界面的布局文件
		 */
		View view = inflater.inflate(R.layout.net1314080903117fragment_home, container,false);
		/*
		 * 自定义ListView控件的查找
		 */
		mReflashListView = (Net1314080903117ReflashListView) view.findViewById(R.id.id_reflash_Listview);
		/**
		 * 当前系统时间的初始化
		 * format时间转换为：yyyy-MM-dd HH:mm:ss格式
		 */
		date = new Date();
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//用于初始化微博消息数据
		initData();
		/**
		 * mAdapter 为ListView的适配器，这里进行初始化，传入当前上下文，和装有微博消息数据的List容器
		 */
		mAdapter = new Net1314080903117MessageAdapter(getContext(), mList);
		//为ListView设置适配器和设置下拉刷新和加载更多的监听
		mReflashListView.setAdapter(mAdapter);
		mReflashListView.setReflashListener(this);
		return view;
	}

	/**
	 * 用于初始化微博首页微博消息的显示数据
	 */
	private void initData() {
		mList = new ArrayList<>();
		Net1314080903117Message msg = null ;
		for (int i = 0; i < 15; i++) {
			msg = new Net1314080903117Message();
			/**
			 * 微博消息头像调用getRandomIcon，来随机产生一个头像
			 */
			msg.setMessageIcon(getRandomIcon());
			msg.setMessageUser("Charlie");
			msg.setMessageContent("我是微博的内容，编号是"+i);
			msg.setMessageTime(format.format(date));
			mList.add(msg);//将微博消息添加到List容器中
		}
		
		
	}
	/**
	 * 产生0-6的随机数，
	 * 根据产生的随机数来返回一个头像资源Id
	 * @return
	 */
	private int getRandomIcon(){
		Random rand = new Random();
		int randNum = rand.nextInt(6);
		if (randNum==0) {
			return R.drawable.icon1;
		}
		if (randNum==1) {
			return R.drawable.icon2;
		}
		if (randNum==2) {
			return R.drawable.icon3;
		}
		if (randNum==3) {
			return R.drawable.icon4;
		}
		if (randNum==4) {
			return R.drawable.icon5;
		}
		if (randNum==5) {
			return R.drawable.icon6;
		}
		return R.drawable.icon3;
	}

	/**
	 * 实现下拉刷新后的方法
	 */
	@Override
	public void onReflash() {
		/*
		 * 首先获取最新数据
		 * 两秒后执行该方法，添加最新数据
		 * 然后通知界面显示
		 * 最后ListView刷新数据完毕
		 */
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//调用该方法添加刷新数据
				mList = addNewData(mList);
				//刷新完成
				mReflashListView.reflashComplete();
				//更新适配器
				mAdapter.notifyDataSetChanged();
			}
		}, 2000);
	}

	/**
	 * 上拉加载数据
	 */
	@Override
	public void onLoad() {
		/*
		 * 首先获取最新数据
		 * 两秒后执行该方法，添加最新数据
		 * 然后通知界面显示
		 * 最后ListView刷新数据完毕
		 */
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//调用加载数据的方法
				mList = addLoadData(mList);
				mReflashListView.reflashComplete();
				mAdapter.notifyDataSetChanged();
			}
		}, 2000);
	}
	
	/**
	 * 用于添加最新的数据到List容器中
	 * 
	 * @param list 原先的数据容器，在这基础上继续添加
	 * @return
	 */
	private List<Net1314080903117Message> addNewData(List<Net1314080903117Message> list){
		Net1314080903117Message msg= null;
		for (int i = 0; i < 3; i++) {
			msg = new Net1314080903117Message();
			msg.setMessageIcon(R.drawable.icon1);
			msg.setMessageUser("Charlie刷新");
			msg.setMessageTime(format.format(date));
			msg.setMessageContent("我是下拉刷新出来的新内容"+i);
			list.add(0,msg);//从0开始添加，就可以将数据添加到顶部显示
		}
		return list;
		
	}
	/**
	 * 用于加载上拉的数据，添加到原先容器中
	 * @param list
	 * @return
	 */
	private List<Net1314080903117Message> addLoadData(List<Net1314080903117Message> list){
		Net1314080903117Message msg= null;
		for (int i = 0; i < 3; i++) {
			msg = new Net1314080903117Message();
			msg.setMessageIcon(R.drawable.iconload);
			msg.setMessageUser("廖Charlie");
			msg.setMessageTime(format.format(date));
			msg.setMessageContent("我是加载的新内容"+i);
			list.add(msg);
		}
		return list;
	}

}
