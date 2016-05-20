package edu.hzuapps.androidworks.homeworks.net134080903206;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Net1314080903206Blocks extends FrameLayout {
	
	TextView label;
	private int color;

	public Net1314080903206Blocks(Context context) {
		super(context);
		
		label = new TextView(getContext());
		label.setTextSize(1);
		label.setTextColor(Color.RED);
		label.setBackgroundColor(Color.BLACK);
		label.setTextSize(10);
		//label.setText("w");
		
		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(5, 5, 0, 0);
		addView(label, lp);
		
		
	}

	public void setColor(int i) {
		this.color=i;
		label.setBackgroundColor(i);
	}
}







