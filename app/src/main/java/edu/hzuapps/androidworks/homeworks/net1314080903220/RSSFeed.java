package edu.hzuapps.androidworks.homeworks.net1314080903220;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class RSSFeed {
	private List<RSSItem> listview;
	private int itemCount = 0;
	private String title, pubDate;

	public RSSFeed() {
		listview = new Vector(0);
	}

	//// 添加RssItem条目,返回列表长度
	public int addItem(RSSItem item) {
		listview.add(item);
		itemCount++;
		return itemCount;
	}

	public List getList() {
		return listview;
	}

	int getItemsCount() {
		return itemCount;
	}

	// 根据下标获取RssItem
	public RSSItem getItem(int location) {
		return listview.get(location);
	}

	public List getItemsForList() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int SIZE = listview.size();
		for (int i = 0; i < SIZE; i++) {
			Map<String, Object> mdMap = new HashMap<String, Object>();
			mdMap.put(RSSItem.TITLE, listview.get(i).getTitle());
			mdMap.put(RSSItem.PUBDATE, listview.get(i).getPubDate());
			data.add(mdMap);
		}
		return data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
}