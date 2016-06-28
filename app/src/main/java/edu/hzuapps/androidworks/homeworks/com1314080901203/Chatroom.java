package com.example.com1314080901203;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Chatroom extends Fragment {
	private ViewPager myViewPager;
	private PagerAdapter myPagerAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
	private MydatabaseHelper dbHelper;
	ChatMsgEntity entity ;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private ListView mListView;
	
	/*时间匹配变量
	 * */
	public static final int MATCH_TIME=1;
	public Handler handler = null;
	private static int delay = 60000; // 1min
	private static int period = 60000; // 1min
	private Timer mTimer;
	private TimerTask mTimerTask;
	
	private final static int COUNT = 6;// 初始化数组总数
	EditText edit;
	Button send;
	/*时间变量
	 * 
	 * */
	int year ;
	 int month;
	 int day;
	 int minute;
	 int hour;
	 Calendar cd;
	 String nowTime;
	 String nowDate;
	 
	 /*
	  * 预设时间变量
	  * */
	 int reYear;
	 int reMonth;
	 int reDay;
	 int reHour;
	 int reMin;
	 String reTime;
	 String reDate;
	 
	 String remsg;
	 String reObj;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	@Override
	  public  void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);
	        mListView=(ListView)getActivity().findViewById(R.id.listview);
	        initData();
	        mListView.setSelection(mAdapter.getCount() - 1);
	      //开线程进行与预设时间匹配
	      //*********************************
	       /*不知道怎么回事，调试过很多次，不知道问题出现在哪里，当滑动返回或者点击返回上一个界面，都会崩溃
	        * */ 
			startTimer();
			handler=new Handler(){
				public void handlerMessage(Message msg){
					super.handleMessage(msg);
					switch(msg.what){
					case MATCH_TIME:
						matchTime();
						break;
					default:
						break;
					}
				}
			};
			send=(Button)getActivity().findViewById(R.id.btn_send);
	        edit=(EditText)getActivity().findViewById(R.id.et_sendmessage);
			send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					send();
				}
			});
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatroom, container, false);
        return view;

    }
	
	/*开始匹配
	 * */
	private void startTimer() {
		if (mTimer == null) {
			mTimer = new Timer();
		}

		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				public void run() {
					sendMessage(MATCH_TIME);
					while (true) {
						try {
							Thread.sleep(1000);//1s
						} catch (InterruptedException e) {
						}
					}
				}
			};
		}

		if (mTimer != null && mTimerTask != null)
			mTimer.schedule(mTimerTask, delay, period);
	}
	
	public void sendMessage(int id) {
		if (handler != null) {
			Message message = Message.obtain(handler, id);
			handler.sendMessage(message);
		}
	}
	
	/*匹配时间
	 * */
	public  void matchTime() {
		cd = Calendar.getInstance();
		Log.i("www","cd");
		year = cd.get(Calendar.YEAR);
		Log.i("www",year+"year");
		month=cd.get(Calendar.MONTH);
		Log.i("www",month+"month");
		
		day=cd.get(Calendar.DATE);
		Log.i("www",day+"day");
		minute=cd.get(Calendar.MINUTE);
		Log.i("www",minute+"minute");
		hour=cd.get(Calendar.HOUR);
		Log.i("www",hour+"hour");
		nowTime=year+"-"+month+"-"+day;
		Log.i("www",nowTime);
		nowDate=hour+":"+minute;
		Log.i("www",nowDate);
		SQLiteDatabase SQLDB=dbHelper.getReadableDatabase();
		ContentValues values=new ContentValues();
		reObj=values.getAsString("objString");
		reDate= values.getAsString("dateString");
		reTime=values.getAsString("timeString");
		remsg=values.getAsString("msgString");
		if(nowTime==reTime &&nowDate==reDate ){
			entity.setMessage(remsg);
			entity.setMsgType(false);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变
			mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
		}
	}
	
	/**
	 * 发送消息时，获取当前事件
	 * 
	 * @return 当前时间
	 */
	public static String getDate() {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}
	private String[] msgArray = new String[] { "who are you ?", "You guess it", "I wound't guess it", 
			 "e....", "I'm you classmate ！！","soga!"};

	private String[] dataArray = new String[] { "2016-09-28 18:00:02",
			"2016-09-28 18:10:22", "2016-09-28 18:11:24",
			"2016-09-28 18:20:23", "2016-09-28 18:30:31",
			"2016-09-28 18:35:37" };
	
	/**
	 * 模拟加载消息历史，实际开发可以从数据库中读出
	 */
	public void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[i]);
			if (i % 2 == 0) {
				entity.setName("john");
				entity.setMsgType(true);// 收到的消息
			} else {
				entity.setName("moore");
				entity.setMsgType(false);// 自己发送的消息
			}
			entity.setMessage(msgArray[i]);
			mDataArrays.add(entity);
		}

		mAdapter = new ChatMsgViewAdapter(getContext(), mDataArrays);
		Log.i("www","one");
		mListView.setAdapter(new ChatMsgViewAdapter(getContext(), mDataArrays));
		Log.i("www","two");
	}
	/**
	 * 发送消息
	 */
	private void send() {
		// TODO Auto-generated method stub
		String contString =  edit.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("必败");
			entity.setDate(getDate());
			entity.setMessage(contString);
			entity.setMsgType(false);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

			edit.setText("");// 清空编辑框数据

			mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
		}
	}

}
