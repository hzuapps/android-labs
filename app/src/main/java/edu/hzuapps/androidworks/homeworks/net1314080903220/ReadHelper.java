package edu.hzuapps.androidworks.homeworks.net1314080903220;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadHelper extends DefaultHandler {
	private RSSFeed r_Feed;
	private RSSItem r_item;

	private static final int RSS_TITLE = 1;
	private static final int RSS_LINK = 2;
	private static final int RSS_PUBDATE = 3;
	private static final int RSS_DESCRIPTION = 4;
	int currentStatus = 0;

	public ReadHelper() {
	}

	public RSSFeed getFeed(){
		return r_Feed;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
			if(length<5){
				return;
			}
			else{
				String conString = new String(ch,start,length);//获取字符串
				switch (currentStatus) {
				case RSS_TITLE:
					r_item.setTitle(conString);// 设置完后，重置为开始状态
					currentStatus = 0;
					break;
				case RSS_LINK:
					r_item.setLink(conString);
					currentStatus = 0;
					break;
				case RSS_PUBDATE:
					r_item.setPubDate(conString);
					currentStatus = 0;
					break;
				case RSS_DESCRIPTION:
					r_item.setDescription(conString);
					currentStatus = 0;
					break;
				default:
					break;
				}
			}
	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (localName.equals("item")) {
			// 如果解析一个item节点结束，就将rssItem添加到rssFeed中。
			r_Feed.addItem(r_item	);
			return;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		r_Feed = new RSSFeed();
		r_item = new RSSItem();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if (localName.equals("channel")) {
			currentStatus = 0;
			return;
		}
		if (localName.equals("item")) {
			r_item = new RSSItem();
			return;
		}
		if (localName.equals("title")) {
			currentStatus = RSS_TITLE;
			return;
		}
		if (localName.equals("link")) {
			currentStatus = RSS_LINK;
			return;
		}
		if (localName.equals("pubDate")) {
			currentStatus = RSS_PUBDATE;
			return;
		}
		if (localName.equals("description")) {
			currentStatus = RSS_DESCRIPTION;;
			return;
		}
		currentStatus = 0;
	}

}
