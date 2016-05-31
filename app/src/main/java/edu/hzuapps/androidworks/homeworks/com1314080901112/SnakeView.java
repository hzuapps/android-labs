package com.xmobileapp.Snake;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class SnakeView extends TileView {

	private static final String TAG = "SnakeView";

	//游戏的四种状态，初始状态为预备开始的状态
	int mMode = READY;
	public static final int PAUSE = 0;//暂停
	public static final int READY = 1;
	public static final int RUNNING = 2;
	public static final int LOSE = 3;

	//蛇体运动的方向标识
	int mDirection = SOUTH;
	int mNextDirection = SOUTH;
	static final int NORTH = 1;
	static final int SOUTH = 2;
	static final int EAST = 3;
	static final int WEST = 4;

	//游戏中三种方块对应的数值
	static final int RED_STAR = 1;
	static final int YELLOW_STAR = 2;
	static final int GREEN_STAR = 3;

	private long mScore = 0;//记录获得的分数
	//每移动一步的延时。初始时设置为600ms，以后每吃一个果子，打个9折  。造成的结果就是蛇速度越来越快
	private long mMoveDelay = 600; 
	
	//记录上次移动的确切时间。 
    //同mMoveDelay一起处理与用户的异步操作的协同问题。 
	private long mLastMove;

	//用来显示游戏状态的TextView 
	private TextView mStatusText;

	/*
	 * 两个链表，分别用来存储 蛇体 和 果子的坐标。 
     * 每次蛇体的运动，蛇体的增长，产生新的苹果，被吃掉苹果，都会在这里记录。 
	 */
	private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
	private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

	//随机数生成器。用来产生随机的苹果。在addRandomApple()中使用。
	private static final Random RNG = new Random();
	
   //用Handler机制实现定时刷新。
	private RefreshHandler mRedrawHandler = new RefreshHandler();

	class RefreshHandler extends Handler {

		//获取消息并处理
		@Override
		public void handleMessage(Message msg) {
			SnakeView.this.update();
			SnakeView.this.invalidate();//刷新view为基类的界面
		}

		 //定时发送消息给UI线程，以此达到更新的效果。  
		public void sleep(long delayMillis) {
			this.removeMessages(0);//清空消息队列，Handler进入对新消息的等待
			sendMessageDelayed(obtainMessage(0), delayMillis);//定时发送新消息,激活handler
		}
	};

	public SnakeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSnakeView();//构造函数中，别忘了，初始化游戏～ 
	}

	public SnakeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSnakeView();
	}
	//初始化SnakeView类，注意，这根初始化游戏是不一样的。  
	private void initSnakeView() {
		setFocusable(true);//设置焦点，由于存在 文字界面 和 游戏界面的跳转。这个focus是不可或缺的。

		//取得资源中的图片，加载到 砖块字典 中。
		Resources r = this.getContext().getResources();

		resetTiles(4);
		loadTile(RED_STAR, r.getDrawable(R.drawable.redstar));
		loadTile(YELLOW_STAR, r.getDrawable(R.drawable.yellowstar));
		loadTile(GREEN_STAR, r.getDrawable(R.drawable.greenstar));

	}

	//如果不是从暂停中回复，就绪要 初始化游戏了。
	void initNewGame() {
		//清空保存蛇体和果子的数据结构
		mSnakeTrail.clear();
		mAppleList.clear();

		 // 设定初始状态的蛇体的位置。Coordinate其实就是蛇体开始时候的小方块，创建了六个小方块
		mSnakeTrail.add(new Coordinate(7, 7));
		mSnakeTrail.add(new Coordinate(6, 7));
		mSnakeTrail.add(new Coordinate(5, 7));
		mSnakeTrail.add(new Coordinate(4, 7));
		mSnakeTrail.add(new Coordinate(3, 7));
		mSnakeTrail.add(new Coordinate(2, 7));
		mNextDirection = SOUTH;//设定了蛇初始运动的方向为SOUTH
		
		 // Two apples to start with  
		addRandomApple();
		addRandomApple();

		mMoveDelay = 600;
		mScore = 0;
	}

	/*
	 * 在游戏暂停时，需要通过Bundle方式保存数据。见saveState()。 
     * Bundle支持简单的数组。 
     * 所以需要将我们的部分数据结构，如蛇体和苹果位置的数组，转换成简单的序列化的int数组。 
	 */
	private int[] coordArrayListToArray(ArrayList<Coordinate> cvec) {
		int count = cvec.size();
		int[] rawArray = new int[count * 2];
		for (int index = 0; index < count; index++) {
			Coordinate c = cvec.get(index);
			rawArray[2 * index] = c.x;
			rawArray[2 * index + 1] = c.y;
		}
		return rawArray;
	}

	//在意外情况下，暂时性保存游戏数据，在下次打开游戏时，可以继续游戏。如来电话了。 
	public Bundle saveState() {
		Bundle map = new Bundle();

		map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
		map.putInt("mDirection", Integer.valueOf(mDirection));
		map.putInt("mNextDirection", Integer.valueOf(mNextDirection));
		map.putLong("mMoveDelay", Long.valueOf(mMoveDelay));
		map.putLong("mScore", Long.valueOf(mScore));
		map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));

		return map;
	}
	
	/*
	 * 是coordArrayListToArray（）的逆过程，用来读取保存在Bundle中的数据。 
     * @param rawArray : [x1,y1,x2,y2,...] 
     * @return a ArrayList of Coordinates 
	 */

	private ArrayList<Coordinate> coordArrayToArrayList(int[] rawArray) {
		ArrayList<Coordinate> coordArrayList = new ArrayList<Coordinate>();

		int coordCount = rawArray.length;
		for (int index = 0; index < coordCount; index += 2) {
			Coordinate c = new Coordinate(rawArray[index], rawArray[index + 1]);
			coordArrayList.add(c);
		}
		return coordArrayList;
	}

	/*
	 * 恢复游戏数据。是saveState()的逆过程 
     * @param icicle a Bundle containing the game state 
	 */
	public void restoreState(Bundle icicle) {
		setMode(PAUSE);

		mAppleList = coordArrayToArrayList(icicle.getIntArray("mAppleList"));
		mDirection = icicle.getInt("mDirection");
		mNextDirection = icicle.getInt("mNextDirection");
		mMoveDelay = icicle.getLong("mMoveDelay");
		mScore = icicle.getLong("mScore");
		mSnakeTrail = coordArrayToArrayList(icicle.getIntArray("mSnakeTrail"));
	}

	/*
	 * 按键的监听。 
     * 现在大多数的android手机都没有按键了。 
     * 在自己的模拟机上才能正常的使用这款小游戏的 - -# 
     * @see android.view.View#onKeyDown(int, android.os.KeyEvent) 
	 * (non-Javadoc)
	 * @see android.view.View#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg) {

		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			if (mMode == READY | mMode == LOSE) {
				initNewGame();
				setMode(RUNNING);
				update();//update()实现了对游戏数据的更新，是整个游戏的推动力。
				return (true);
			}

			/* 
             * If the game is merely paused, we should just continue where 
             * we left off. 
             */  
			if (mMode == PAUSE) {
				setMode(RUNNING);
				update();
				return (true);
			}

			if (mDirection != SOUTH) {//如果按键的方向 跟蛇本身的运动方向完全相反，则无法执行  
				mNextDirection = NORTH;
			}
			return (true);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			if (mDirection != NORTH) {
				mNextDirection = SOUTH;
			}
			return (true);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (mDirection != EAST) {
				mNextDirection = WEST;
			}
			return (true);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (mDirection != WEST) {
				mNextDirection = EAST;
			}
			return (true);
		}

		return super.onKeyDown(keyCode, msg);
	}

	//Snake类会调用到它，来绑定到相应的textview.
	public void setTextView(TextView newView) {
		mStatusText = newView;
	}

	/*
	 * 更新应用程序的当前模式(运行或暂停或类似的) 　　
	 * *以及设置textview能见度的通知　
	 */
	public void setMode(int newMode) {
		int oldMode = mMode;
		mMode = newMode;

		if (newMode == RUNNING & oldMode != RUNNING) {
			mStatusText.setVisibility(View.INVISIBLE);//游戏开始后，将TextView的文字显示设置为不可见
			update();
			return;
		}

		Resources res = getContext().getResources();
		CharSequence str = "";
		if (newMode == PAUSE) {
			str = res.getText(R.string.mode_pause);
		}
		if (newMode == READY) {
			str = res.getText(R.string.mode_ready);
		}
		if (newMode == LOSE) {
			//str = res.getString(R.string.mode_lose_prefix) + mScore
					//+ res.getString(R.string.mode_lose_suffix);
			//str=mScore+"";
			str=res.getString(R.string.mode_lose_prefix)
					+res.getString(R.string.mode_lose_suffix)+mScore;
		}

		mStatusText.setText(str);
		mStatusText.setVisibility(View.VISIBLE);
	}

	/*
	 * 在地图上随机的增加果子
	 * 新产生的果子的坐标会增加到mApplist的数组上。
	 */
	private void addRandomApple() {
		Coordinate newCoord = null;
		boolean found = false;
		while (!found) {
			//注意别产生在边框上的果子
			int newX = 1 + RNG.nextInt(mXTileCount - 2);
			int newY = 1 + RNG.nextInt(mYTileCount + 5);
			//int newX=1;
			//int newY=1;
			newCoord = new Coordinate(newX, newY);

			// Make sure it's not already under the snake 
			boolean collision = false;
			int snakelength = mSnakeTrail.size();
			for (int index = 0; index < snakelength; index++) {
				if (mSnakeTrail.get(index).equals(newCoord)) {
					collision = true;
				}
			}
			found = !collision;
		}
		if (newCoord == null) {
			Log.e(TAG, "Somehow ended up with a null newCoord!");
		}
		mAppleList.add(newCoord);
	}

	//刷新游戏状态。每次游戏画面的更新、游戏数据的更新，都是依靠这个update()来完成的。 
	public void update() {
		if (mMode == RUNNING) {
			long now = System.currentTimeMillis();

			if (now - mLastMove > mMoveDelay) {//这里是对蛇体游戏刚开始时连续的两个移动速率的控制 
				clearTiles();//清空界面画布
				updateWalls();//重新绘制墙壁
				updateSnake();//对蛇的 游戏逻辑 的处理 以及绘制  
				updateApples();//对果子的 游戏逻辑 的处理 以及绘制 
				mLastMove = now;
			}
			mRedrawHandler.sleep(mMoveDelay);//利用Handler进行 定时刷新的控制（调用mMoveDelay控制蛇的移动速度）
		}

	}

	//用setTile绘制墙壁 
	private void updateWalls() {
		for (int x = 0; x < mXTileCount; x++) {//画左右
			setTile(GREEN_STAR, x, 3);
			setTile(GREEN_STAR, x, mYTileCount - 1);
		}
		for (int y = 3; y < mYTileCount - 1; y++) {//画上下
			setTile(GREEN_STAR, 0, y);
			setTile(GREEN_STAR, mXTileCount - 1, y);
		}
	}
   //绘制果子
	private void updateApples() {
		for (Coordinate c : mAppleList) {
			setTile(YELLOW_STAR, c.x, c.y);
		}
	}

	private void updateSnake() {
		boolean growSnake = false;//吃过果子的蛇会长长。这个变量即为它的标记。

		Coordinate head = mSnakeTrail.get(0);//头部很重要，只有头部可能碰到果子
		Coordinate newHead = new Coordinate(1, 1);//蛇下一步一定会前移，也就试newHead。长长只会从尾部增加。

		mDirection = mNextDirection;

		switch (mDirection) {
		case EAST: {
			newHead = new Coordinate(head.x + 1, head.y);
			break;
		}
		case WEST: {
			newHead = new Coordinate(head.x - 1, head.y);
			break;
		}
		case NORTH: {
			newHead = new Coordinate(head.x, head.y - 1);
			break;
		}
		case SOUTH: {
			newHead = new Coordinate(head.x, head.y + 1);
			break;
		}
		}
		//撞墙检测  
		if ((newHead.x < 1) || (newHead.y < 4) || (newHead.x > mXTileCount - 2)
				|| (newHead.y > mYTileCount - 2)) {
			setMode(LOSE);
			return;

		}
		//撞自己检测
		int snakelength = mSnakeTrail.size();
		for (int snakeindex = 0; snakeindex < snakelength; snakeindex++) {
			Coordinate c = mSnakeTrail.get(snakeindex);
			if (c.equals(newHead)) {
				setMode(LOSE);
				return;
			}
		}
		//吃果子检测  
		int applecount = mAppleList.size();
		for (int appleindex = 0; appleindex < applecount; appleindex++) {
			Coordinate c = mAppleList.get(appleindex);
			if (c.equals(newHead)) {
				mAppleList.remove(c);
				addRandomApple();

				mScore++;
				mMoveDelay *= 0.9;

				growSnake = true;
			}
		}
		// push a new head onto the ArrayList and pull off the tail  
        //前进  
		mSnakeTrail.add(0, newHead);
		if (!growSnake) {
			mSnakeTrail.remove(mSnakeTrail.size() - 1);
		}
		//绘制新的蛇体  
		int index = 0;
		for (Coordinate c : mSnakeTrail) {
			if (index == 0) {
				setTile(YELLOW_STAR, c.x, c.y);
			} else {
				setTile(RED_STAR, c.x, c.y);
			}
			index++;
		}

	}

	//这是坐标点的类。很简单的存储XY坐标。 
	private class Coordinate {
		public int x;
		public int y;

		public Coordinate(int newX, int newY) {
			x = newX;
			y = newY;
		}

		public boolean equals(Coordinate other) {
			if (x == other.x && y == other.y) {
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return "Coordinate: [" + x + "," + y + "]";
		}
	}

}
