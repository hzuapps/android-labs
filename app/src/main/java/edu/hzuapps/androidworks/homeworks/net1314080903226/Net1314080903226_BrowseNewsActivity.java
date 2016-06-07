package edu.hzuapps.androidworks.homeworks.net1314080903226;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import edu.hzuapps.androidworks.homeworks.net1314080903226.R;

public class Net1314080903226_BrowseNewsActivity extends Activity {
	
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivy_browse_news);
		
		webView = (WebView) findViewById(R.id.webView);
		String pic_url = getIntent().getStringExtra("content_url");
		webView.loadUrl(pic_url);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	}
}
