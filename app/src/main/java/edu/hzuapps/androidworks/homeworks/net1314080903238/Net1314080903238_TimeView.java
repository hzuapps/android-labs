package edu.hzuapps.androidworks.homeworks;

import java.util.Calendar;

import com.jikexueyuan.jkxyclock.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Net1314080903238_TimeView extends LinearLayout {

	public Net1314080903238_TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Net1314080903238_TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Net1314080903238_TimeView(Context context) {
		super(context);
	}

	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setText("Hello");
		
		timerHandler.sendEmptyMessage(0);
	}
	
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		
		if (visibility==View.VISIBLE) {
			timerHandler.sendEmptyMessage(0);
		}else{
			timerHandler.removeMessages(0);
		}
	}
	
	
	private void refreshTime(){
		Calendar c = Calendar.getInstance();
		
		tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND)));
	}
	
	private Handler timerHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			refreshTime();
			
			if (getVisibility()==View.VISIBLE) {
				timerHandler.sendEmptyMessageDelayed(0, 1000);
			}
		};
	};
	
	private TextView tvTime;
}
