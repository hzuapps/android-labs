package com.example.com1314080901203;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Chatroom extends Fragment {
	private ViewPager myViewPager;
	private PagerAdapter myPagerAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组

	
	EditText edit;
	Button send;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatroom, container, false);
        send=(Button)view.findViewById(R.id.btn_send);
        edit=(EditText)view.findViewById(R.id.et_sendmessage);
        return view;

    }
}
