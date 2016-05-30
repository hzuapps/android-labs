package edu.hzuapps.androidworks.homeworks.net1314080903117;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 该类为ReflashListview的适配器
 * 继承了BaseAdapter
 * 重写BaseAdapter的四个方法
 * @author Charlie
 *
 */
public class Net1314080903117MessageAdapter extends BaseAdapter {
	private List<Net1314080903117Message> mList;//微博消息数据容器
	private Context mContext;//获取上下文对象
	
	
	/**
	 * 传入两个参数的构造方法
	 * @param context 上下文对象
	 * @param list  消息数据
	 */
	public Net1314080903117MessageAdapter(Context context, List<Net1314080903117Message> list) {
		mContext = context;
		mList = list;
	}
	/**
	 * 获取Item 的个数
	 */
	@Override
	public int getCount() {   return mList.size();  }

	/**
	 * 显示当前界面可显示的数据
	 */
	@Override
	public Object getItem(int position) {
		
		return mList.get(position);
	}

	/**
	 * 获取当前位置
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 获取当前的View
	 * 为了防止内存消耗过大，使用MessageViewHolder来实现
	 * 提高资源利用率
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MessageViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MessageViewHolder();
			//获取ListView的Item关联的布局item_listview
			convertView = View.inflate(mContext, R.layout.net1314080903117item_listview, null);
			//通过View 来查找对应的控件
			viewHolder.messageIcon = (ImageView) convertView.findViewById(R.id.id_message_icon);
			viewHolder.messageUser = (TextView) convertView.findViewById(R.id.id_message_User);
			viewHolder.messageTime = (TextView) convertView.findViewById(R.id.id_message_Time);
			viewHolder.messageContent = (TextView) convertView.findViewById(R.id.id_message_content);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (MessageViewHolder) convertView.getTag();
		}
		/**
		 * 为各个控件进行赋值，根据position来获取当前要显示的数据，并显示出来
		 */
	    viewHolder.messageIcon.setImageResource(mList.get(position).getMessageIcon());
		viewHolder.messageUser.setText(mList.get(position).getMessageUser());
		viewHolder.messageTime.setText(mList.get(position).getMessageTime());
		viewHolder.messageContent.setText(mList.get(position).getMessageContent());
		return convertView;
	}
	
	/**
	 * 使用ViewHolder，可以不用每次适配器getView的时候都创建新的对象，
	 * 提高了软件的性能
	 * 并且降低了内存消耗
	 * @author Charlie
	 *
	 */
	class MessageViewHolder{
		/**
		 * ListView 中Item中的控件
		 */
		public ImageView messageIcon;
		public TextView messageUser;
		public TextView messageTime;
		public TextView messageContent;
		
	}

}
