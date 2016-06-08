package edu.hzuapps.androidworks.homeworks3;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_DBManager;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_HisResult;
import edu.hzuapps.androidworks.homeworks2.Net1314080903124_HistoryResultColumns;
import edu.hzuapps.androidworks.homeworks4.Net1314080903124_ViewHolder;

import com.mobile.cetfour.R;


public class Net1314080903124_HisResultActivity extends Activity implements OnClickListener {

	private ArrayList<Net1314080903124_HisResult> list;
	private MyAdapter myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		// 先读取数据库中的缓存, 数据量较多比较耗时，应使用AsyncTask
		new QueryTask().execute();
		setContentView(R.layout.activity_his_result);
		resetTitlebar();
		ListView listView = (ListView) findViewById(R.id.listView1);
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if(list.size() == 0){
				return 0;
			}else{
				return list.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_listview_hisresult, null);
			}
			TextView name = Net1314080903124_ViewHolder.get(convertView, R.id.name);
			TextView curTime = Net1314080903124_ViewHolder.get(convertView, R.id.curTime);
			TextView useTime = Net1314080903124_ViewHolder.get(convertView, R.id.useTime);
			TextView hisResult = Net1314080903124_ViewHolder.get(convertView, R.id.hisResult);
			Net1314080903124_HisResult myData = list.get(position);
			name.setText(myData.getName());
			curTime.setText(myData.getCurTime());
			useTime.setText(myData.getUseTime());
			hisResult.setText(myData.getHisResult());
			return convertView;
		}
		
	}
	
	private class QueryTask extends AsyncTask<Void, Void, ArrayList<Net1314080903124_HisResult>> {

		@Override
		protected ArrayList<Net1314080903124_HisResult> doInBackground(Void... params) {
			list = Net1314080903124_DBManager.getInstance(Net1314080903124_HisResultActivity.this).queryHisResult(Net1314080903124_HistoryResultColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<Net1314080903124_HisResult> list) {
			if (list.size() == 0) {
				return;
			}
			myAdapter.notifyDataSetChanged();
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.view_comm_titlebar);
		final TextView title = (TextView) findViewById(R.id.titlebar_title);
		TextView right = (TextView) findViewById(R.id.titlebar_right_text);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		title.setText("历史成绩");
		right.setText("清空");
		right.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_right_text:
			Net1314080903124_DBManager.getInstance(this).removeAll(Net1314080903124_HistoryResultColumns.TABLE_NAME);
			list.clear();
			myAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}
	
}
