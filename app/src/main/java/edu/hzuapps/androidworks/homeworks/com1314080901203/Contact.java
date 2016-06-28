package com.example.com1314080901203;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Contact extends Fragment {
	private ListView contactListView;
	private Contactbase dbHelper;
	private ContactDB contactdb;
	Context context;
	//联系人列表
	private List<ContactList>contactlist;
	private List<ContactList>datalist=new ArrayList<ContactList>();
	private ContactList Contact;
	private ContactListAdapter adapter;
	
	
	private final static int count = 12;// 初始化数组总数
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact, container, false);

        return v;

    }
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
	}
	@Override
	  public  void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);
	        //setRetainInstance(true);
	        contactListView=(ListView)getActivity().findViewById(R.id.contactlistview);
	        initData();
	}
	
	private int[] num =  { 1, 2, 3, 4, 5,6};

	private String[] dataArray = new String[] { "赵","钱", "孙","李", "陈","吕" };
	private String[] n=new String[]{"爆","冷","车技","掉渣天","老司机","隔壁老王","修为"};
	public void initData() {
		//不知道为什么这里不注释就无法打开
		/*for (int i = 0; i < 6; i++) {
			ContactList CL=new ContactList();
			CL.setID(num[i]);
			CL.setImageID(R.drawable.mini_avatar_shadow);
			CL.setName(dataArray[i]);
			CL.setNickname(n[i]);
			datalist.add(CL);
		}*/
		 Log.i("www","adapter");
	    adapter=new ContactListAdapter(getContext(),android.R.layout.activity_list_item,datalist);
	    Log.i("www","adapter2");
        contactListView.setAdapter(adapter);
        Log.i("www","adapter3");
        contactdb=ContactDB.getInstance(getContext());
        Log.i("www","adapter4");
        queryContact();
        Log.i("www","adapter1");
	}
	private void queryContact(){
		 Log.i("www","contact");
		contactlist=contactdb.loadContactList();
		 Log.i("www","contact1");
		if(contactlist.size()>0){
			 Log.i("www","contact2");
			datalist.clear();
			 Log.i("www","contact3");
			for(ContactList contact:contactlist ){
				 Log.i("www","contact4");
				datalist.add(contact);
				 Log.i("www","contact5");
				datalist.add(contact);
				 Log.i("www","contact6");
				datalist.add(contact);
				 Log.i("www","contact7");
			}
			adapter.notifyDataSetChanged();
			 Log.i("www","contact8");
			contactListView.setSelection(0);
			 Log.i("www","contact9");
			
		}
	}

	
}
