package edu.hzuapps.androidworks.homeworks.net1314080903220;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RSSShowItem extends Activity{
	private TextView txtContent;
	private Button button;
	
	private String title,pubDate,description,link;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_showitem);
		
		txtContent = (TextView)findViewById(R.id.txt_content);
		button = (Button)findViewById(R.id.btn_back);
		button.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});

		Intent intent = getIntent();
		if(intent != null){
			Bundle bundle = intent.getBundleExtra("com.lq.showitem");
			if(bundle != null){
				title = bundle.getString("title");
				pubDate = bundle.getString("pubDate");
				description = bundle.getString("description");
				link = bundle.getString("link");
			}
		}
		txtContent.setText(title+"\n\n"+pubDate+"\n\n"+description+"\n\n"+link);
	}
	
}
