package com.example.bigwheeldemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BigWheelActivity extends Activity{
	private BitWheelView mWheelView;
	private ImageView  mStartBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_bigwheel);
		mWheelView = (BitWheelView) findViewById(R.id.id_luckypan);
		mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

		mStartBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!mWheelView.isStart())
				{
					mStartBtn.setImageResource(R.drawable.stop);
					mWheelView.luckyStart();
				} else
				{
					if (!mWheelView.isShouldEnd())

					{
						mStartBtn.setImageResource(R.drawable.start);
						mWheelView.luckyEnd();
					}
				}
			}
		});
	}
	
	public static void startActivity(Context context){
		Intent intent = new Intent(context, BigWheelActivity.class);
		context.startActivity(intent);
	}
}
