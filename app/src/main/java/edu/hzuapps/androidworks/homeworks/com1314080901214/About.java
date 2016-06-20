package com.example.dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
	private TextView text;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.about);
	    text = (TextView) findViewById(R.id.text);
	}
}
