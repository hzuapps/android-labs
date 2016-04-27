package com.xmobileapp.Snake;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Net1314080903224SnakeActivity extends Activity implements OnClickListener {

	private final static int PLAY = 1;

	private final static int LEFT = 2;

	private final static int RIGHT = 3;

	private final static int UP = 4;

	private final static int DOWN = 5;

	private Net1314080903224SnakeView mSnakeView;

	private static String ICICLE_KEY = "snake-view";

	private Button play;

	private ImageButton left;

	private ImageButton right;

	private ImageButton up;

	private ImageButton down;

	private UpdateStatus updateStatus;

	private Handler handler;

	protected static final int GUINOTIFIER = 0x1234;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.net1314080903224snake_layout);

		mSnakeView = (Net1314080903224SnakeView) findViewById(R.id.snake);
		mSnakeView.setTextView((TextView) findViewById(R.id.text));
		play = (Button) findViewById(R.id.play);
		play.setId(PLAY);
		play.setOnClickListener(this);
		play.setBackgroundColor(Color.argb(0, 0, 255, 0));
		left = (ImageButton) findViewById(R.id.left);
		left.setId(LEFT);
		left.setOnClickListener(this);
		left.setBackgroundColor(Color.argb(1, 1, 255, 1));
		left.setVisibility(View.GONE);

		right = (ImageButton) findViewById(R.id.right);
		right.setId(RIGHT);
		right.setOnClickListener(this);
		right.setBackgroundColor(Color.argb(1, 1, 255, 1));
		right.setVisibility(View.GONE);

		up = (ImageButton) findViewById(R.id.up);
		up.setId(UP);
		up.setOnClickListener(this);
		up.setBackgroundColor(Color.argb(1, 1, 255, 1));
		up.setVisibility(View.GONE);

		down = (ImageButton) findViewById(R.id.down);
		down.setId(DOWN);
		down.setOnClickListener(this);
		down.setBackgroundColor(Color.argb(1, 1, 255, 1));
		down.setVisibility(View.GONE);

		if (savedInstanceState == null) {
			mSnakeView.setMode(mSnakeView.READY);
		} else {
			Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
			if (map != null) {
				mSnakeView.restoreState(map);
			} else {
				mSnakeView.setMode(Net1314080903224SnakeView.PAUSE);
			}
		}

		handler = new Handler() {
			public void handleMessage(Message msg) {

				switch (msg.what) {
				case Net1314080903224SnakeActivity.GUINOTIFIER:

					play.setVisibility(View.VISIBLE);
					left.setVisibility(View.GONE);
					right.setVisibility(View.GONE);
					up.setVisibility(View.GONE);
					down.setVisibility(View.GONE);
					break;
				}
				super.handleMessage(msg);
			}
		};

	}

	@Override
	protected void onPause() {
		super.onPause();
		mSnakeView.setMode(Net1314080903224SnakeView.PAUSE);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case PLAY:
			play.setVisibility(View.GONE);
			left.setVisibility(View.VISIBLE);
			right.setVisibility(View.VISIBLE);
			up.setVisibility(View.VISIBLE);
			down.setVisibility(View.VISIBLE);
			if (mSnakeView.mMode == mSnakeView.READY
					| mSnakeView.mMode == mSnakeView.LOSE) {
				mSnakeView.initNewGame();
				mSnakeView.setMode(mSnakeView.RUNNING);
				mSnakeView.update();
				updateStatus = new UpdateStatus();
				updateStatus.start();
				break;
			}

			if (mSnakeView.mMode == mSnakeView.PAUSE) {
				mSnakeView.setMode(mSnakeView.RUNNING);
				mSnakeView.update();

				break;
			}

			if (mSnakeView.mDirection != mSnakeView.SOUTH) {
				mSnakeView.mNextDirection = mSnakeView.NORTH;

				break;
			}

			break;

		case LEFT:

			if (mSnakeView.mDirection != mSnakeView.EAST) {
				mSnakeView.mNextDirection = mSnakeView.WEST;
			}
			break;

		case RIGHT:

			if (mSnakeView.mDirection != mSnakeView.WEST) {
				mSnakeView.mNextDirection = mSnakeView.EAST;
			}
			break;
		case UP:

			if (mSnakeView.mDirection != mSnakeView.SOUTH) {
				mSnakeView.mNextDirection = mSnakeView.NORTH;
			}
			break;

		case DOWN:

			if (mSnakeView.mDirection != mSnakeView.NORTH) {
				mSnakeView.mNextDirection = mSnakeView.SOUTH;
			}
			break;

		default:

			break;

		}
	}

	class UpdateStatus extends Thread {
		@Override
		public void run() {

			super.run();

			while (true) {

				if (mSnakeView.mMode == mSnakeView.LOSE) {

					Message m = new Message();
					m.what = Net1314080903224SnakeActivity.GUINOTIFIER;
					Net1314080903224SnakeActivity.this.handler.sendMessage(m);

					break;

				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

	}

}
