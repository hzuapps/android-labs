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

public class Snake extends Activity implements OnClickListener {

	private final static int PLAY = 1;

	private final static int LEFT = 2;

	private final static int RIGHT = 3;

	private final static int UP = 4;

	private final static int DOWN = 5;
	
	private final static int PAUS =6;//暂停

	private SnakeView mSnakeView;

	private static String ICICLE_KEY = "snake-view";

	private Button play;
	private Button paus;

	private ImageButton left;

	private ImageButton right;

	private ImageButton up;

	private ImageButton down;

	private UpdateStatus updateStatus;

	private Handler handler;

	protected static final int GUINOTIFIER = 0x1234;

	/*
	 * 第一次被创建时调用活动。关掉标题栏，设置内容视图、启动snakeview
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//全屏效果
		
		setContentView(R.layout.snake_layout);

		//将界面的TextView对象注入到snakeview类中
		mSnakeView = (SnakeView) findViewById(R.id.snake);
		mSnakeView.setTextView((TextView) findViewById(R.id.text));
		play = (Button) findViewById(R.id.play);
		play.setId(PLAY);
		play.setOnClickListener(this);
		play.setBackgroundColor(Color.argb(0, 0, 255, 0));//设置背景透明度
		
		
		paus=(Button)findViewById(R.id.paus);
		paus.setId(PAUS);
		paus.setOnClickListener(this);
		paus.setBackgroundColor(Color.argb(0, 0, 255, 0));//设置背景透明度
		
		
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
			// We were just launched -- set up a new game
			mSnakeView.setMode(mSnakeView.READY);
		} else {
			// We are being restored
			Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
			if (map != null) {
				mSnakeView.restoreState(map);
			} else {
				mSnakeView.setMode(SnakeView.PAUSE);
			}
		}

		handler = new Handler() {
			public void handleMessage(Message msg) {

				switch (msg.what) {
				case Snake.GUINOTIFIER:

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

	//@Override
	//protected void onPause() {
		/*super.onPause();
		// Pause the game along with the activity
		mSnakeView.setMode(SnakeView.PAUSE);*/
	//}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		//Store the game state
		outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case PAUS:
			play.setVisibility(View.VISIBLE);
			left.setVisibility(View.GONE);
			right.setVisibility(View.GONE);
			up.setVisibility(View.GONE);
			down.setVisibility(View.GONE);
			//mSnakeView.mMode=mSnakeView.PAUSE;
			mSnakeView.setMode(mSnakeView.PAUSE);
			break;
			

		case PLAY:
			play.setVisibility(View.VISIBLE);
			left.setVisibility(View.VISIBLE);
			right.setVisibility(View.VISIBLE);
			up.setVisibility(View.VISIBLE);
			down.setVisibility(View.VISIBLE);
			if (mSnakeView.mMode == mSnakeView.READY
					| mSnakeView.mMode == mSnakeView.LOSE) {
				/*
				 *  At the beginning of the game, or the end of a previous one,
                 * we should start a new game.
				 */
				mSnakeView.initNewGame();
				mSnakeView.setMode(mSnakeView.RUNNING);
				mSnakeView.update();
				updateStatus = new UpdateStatus();
				updateStatus.start();
				break;
			}

			if (mSnakeView.mMode == mSnakeView.PAUSE) {
				/*If the game is merely paused, we should just continue where
                * we left off.
                */
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

			//mDirection是当前方向；mNextDirection下一次刷新时候的方向
			//加入现在正在向右移动则我们按向左的按钮是无效的，如果不是向右移动则按右改变方向
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
					m.what = Snake.GUINOTIFIER;
					Snake.this.handler.sendMessage(m);

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
