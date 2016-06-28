package com.example.com1314080901203;

import java.util.List;

import com.example.com1314080901203.ChatMsgViewAdapter.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactListAdapter extends ArrayAdapter<ContactList>{
	
	private  int resourseId;
	//private static int resourseId=R.id.contact_itemlist;
	private LayoutInflater mInflater;
	private static int imageId=R.drawable.mini_avatar_shadow;
	
	public ContactListAdapter(Context context,int textViewResourceId, List<ContactList>clist) {
		// TODO Auto-generated constructor stub
		super(context,textViewResourceId, clist);
		resourseId=textViewResourceId;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		ViewHolder viewHolder=null;
		ContactList contact=getItem(position);//获取当前项的contact实例
		View view;
		if(convertView==null){
		 view =LayoutInflater.from(getContext()).inflate(resourseId, null);
		 convertView=view.inflate(getContext(), R.layout.contace_item, null);
	     viewHolder=new ViewHolder();
	     viewHolder.contactImage=(ImageView)convertView.findViewById(R.id.contact_image);
		
		//contactImage=(ImageView)contactitemlist.findViewById(R.id.contact_image);
		 viewHolder.contactID=(TextView)convertView.findViewById(R.id.contact_num);
		 viewHolder.contactname=(TextView)convertView.findViewById(R.id.contact_name);
		 viewHolder.contactnickname=(TextView)convertView.findViewById(R.id.contact_nickname);
		 convertView.setTag(viewHolder);
		}else{
			
			viewHolder=(ViewHolder)convertView.getTag();//重新获取viewHolder
		}
		 viewHolder.contactImage.setImageResource(contact.getImageID());
		 viewHolder.contactID.setText(contact.getID());
		viewHolder.contactname.setText(contact.getName());
		viewHolder.contactnickname.setText(contact.getNickname());
		return convertView;
		 //LinearLayout contactitemlist=new LinearLayout(getContext()); 
		 //String inflater = Context.LAYOUT_INFLATER_SERVICE;   
	     //LayoutInflater view = (LayoutInflater)getContext().getSystemService(inflater);   
	     //view.inflate(resourseId, contactitemlist,true);
		 /*convertView=view.inflate(getContext(), R.layout.contace_item, null);
	     viewHolder=new ViewHolder();
	     viewHolder.contactImage=(ImageView)view.findViewById(R.id.contact_image);
		
		//contactImage=(ImageView)contactitemlist.findViewById(R.id.contact_image);
		 viewHolder.contactID=(TextView)view.findViewById(R.id.contact_num);
		 viewHolder.contactname=(TextView)view.findViewById(R.id.contact_name);
		 viewHolder.contactnickname=(TextView)view.findViewById(R.id.contact_nickname);
		 view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();//重新获取viewHolder
		}
		 viewHolder.contactImage.setImageResource(contact.getImageID());
		 viewHolder.contactID.setText(contact.getID());
		viewHolder.contactname.setText(contact.getName());
		viewHolder.contactnickname.setText(contact.getNickname());
		return view;*/
		}
}
