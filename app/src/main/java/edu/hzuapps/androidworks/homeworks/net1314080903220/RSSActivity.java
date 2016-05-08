package com.example.administrator.rssdemo;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSActivity extends ListActivity {
	private static String strUrl = "http://rss.sina.com.cn/ent/hot_roll.xml";

	private static final int Rss_Item1 = Menu.FIRST;
	private static final int Rss_Item2 = Menu.FIRST + 1;

	private String shareinfo = "list_info";
	private SharedPreferences sPreferences;

	protected static final int GUIUPDATEIDENTIFIER = 0x101;
	URL url = null;
	ReadHelper handler;
	RSSFeed rsFeed;
	ProgressDialog pDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		pDialog = ProgressDialog.show(this, "", "数据正在加载中...", true, false);
		new LoadDataThread().start();

	}


	public RSSFeed loadData(String rssUrl) {
		try {
			try {
				url = new URL(rssUrl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SAXParserFactory xFactory = SAXParserFactory.newInstance();
			SAXParser parser = xFactory.newSAXParser();
			XMLReader xReader = parser.getXMLReader();

			handler = new ReadHelper();
			xReader.setContentHandler(handler);
			InputSource iSource = new InputSource(url.openStream());

			xReader.parse(iSource);
			return handler.getFeed();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GUIUPDATEIDENTIFIER:
				pDialog.dismiss();
				updata();
				break;
			default:
				break;
			}
		}
	};

	public class LoadDataThread extends Thread {

		@Override
		public void run() {
			rsFeed = loadData(strUrl);
			System.out.println(strUrl);
			Message msg = new Message();
			msg.what = GUIUPDATEIDENTIFIER;
			RSSActivity.this.myHandler.sendMessage(msg);
		}
	}

	public void updata() {
		if (rsFeed != null) {
			SimpleAdapter sAdapter = new SimpleAdapter(this, rsFeed
					.getItemsForList(), R.layout.list_row, new String[] {
					RSSItem.TITLE, RSSItem.PUBDATE }, new int[] {
					R.id.txt_title, R.id.txt_pubDate });
			setListAdapter(sAdapter);
			this.getListView().setSelection(0);
		} else {
			return;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, Rss_Item1, 1, R.string.item1);
		menu.add(0, Rss_Item2, 2, R.string.item2);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = null;
		switch (item.getItemId()) {
		case Rss_Item1:
			intent = new Intent(RSSActivity.this, RSSList.class);
			startActivityForResult(intent, 0);
			break;
		case Rss_Item2:
			intent = new Intent(RSSActivity.this, About.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		RSSItem rItem = rsFeed.getItem(position);
		Intent intent = new Intent(RSSActivity.this,RSSShowItem.class);
		
		Bundle bundle = new Bundle();
		bundle.putString("title", rItem.getTitle());
		bundle.putString("pubDate", rItem.getPubDate());
		bundle.putString("description", rItem.getDescription());
		bundle.putString("link", rItem.getLink());
		intent.putExtra("com.lq.showitem", bundle);
		
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// 第一次运行时，没有link值，需初始化一个
		sPreferences = getSharedPreferences(shareinfo, 0);
		String link = sPreferences.getString("LIST_LINK", "");
		if ("".equals(link)) {
			sPreferences.edit().putInt("LIST_ID", 0).putLong("LIST_ITEMID", 1)
					.putString("LIST_LINK", RSS_mlist.LINK).commit();
		}
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				strUrl = sPreferences.getString("LIST_LINK", strUrl);
				pDialog = ProgressDialog.show(this, "", "数据正在加载中...", true,
						false);
				new LoadDataThread().start();
			}
		}
	}
}