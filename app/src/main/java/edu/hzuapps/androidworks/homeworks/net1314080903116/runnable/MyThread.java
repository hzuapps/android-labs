package edu.hzuapps.androidworks.homeworks.net1314080903116.runnable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import edu.hzuapps.androidworks.homeworks.net1314080903116.R;
import java.util.Random;

import edu.hzuapps.androidworks.homeworks.net1314080903116.role.Role;

public class MyThread extends Thread {

	private SurfaceHolder holder;
	private Context context;
	private int w, h;
	private int gameType;

	private boolean isStart = true;
	// 获得图片
	private int[] paths;
	private Bitmap[] bms;

	//
	private int gameStatu = 0;
	// 保存游戏状态
	public static final int RUNNING = 0;
	public static final int STANDOFF = 1;
	public static final int GAMEOVER = 2;
	// 精灵

	private int gameSpan;

	public Role[] roles;
	private Rect[] rects;
	// 画笔
	private Paint paint;

	// 初始化开始时间
	private long beginTime;
	// 上一时间毫秒
	private long preTime = 0;
	// 记录第一次位置
	private float fristLocation = 0;

	public MyThread(Context context, SurfaceHolder holder, int w, int h, int gameType) {

		this.holder = holder;
		this.context = context;
		this.w = w;
		this.h = h;
		this.gameType = gameType;

		paint = new Paint();
		// 初始化数组
		paths = new int[] { R.drawable.jingling1, R.drawable.jingling2 };
		bms = new Bitmap[paths.length];
		for (int i = 0; i < paths.length; i++) {
			// 根据路径加载图片
			bms[i] = BitmapFactory.decodeResource(context.getResources(), paths[i]);
		}
		// 游戏区域
		gameSpan = h * 4 / (5 * gameType);

		roles = new Role[gameType];
		rects = new Rect[gameType];

		// 初始化精灵
		initSpirit();

	}

	private void initSpirit() {
		// 初始化精灵
		for (int i = 0; i < gameType; i++) {
			// 画线 Y坐标
			int lineY = h / 10 + (i + 1) * gameSpan;
			// 创建精灵
			Role role = new Role(bms);
			role.setX(w / 8);
			role.setY(lineY - role.getHeight());
			roles[i] = role;
			// 创建障碍物
			Rect rect = new Rect();
			// 随机障碍物宽高
			int random_w = (int) (role.getWidth() * (Math.random() * 5 + 1) /(3*gameType));
			int random_h = (int) (role.getHeight() * (Math.random() * 5 + 1) /(3*gameType));

			rect.left = 3 * w / 2+random_w;
			rect.right = rect.left + random_w;
			rect.bottom = lineY;
			rect.top = rect.bottom - random_h;
			rects[i] = rect;
		}
	}

	@Override
	public void run() {
		super.run();
		beginTime = System.currentTimeMillis();
		while (isStart) {
			// 获取画布
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					// 开始画
					switch (gameStatu) {
					case RUNNING:
						// 正常运行
						drawRunningView(canvas);
						break;

					default:
						break;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);

				}
			}

		}
	}

	private void drawRunningView(Canvas canvas) {
		// 设置画笔属性
		paint.setStrokeWidth(5);
		canvas.drawColor(Color.WHITE);
		for (int i = 0; i < gameType; i++) {

			// 画底部部text版权
			String text = "李梓聪制造";
			paint.setTextSize(60);
			canvas.drawText(text, w / 2 - (text.length() * 60 / 2), h - h / 20, paint);
			// 画i根线
			int lineY = h / 10 + (i + 1) * gameSpan;
			// 模拟精灵向下速度 待完善

			roles[i].setSpeedY(roles[i].getSpeedY() + h*gameType / 3100f);
			roles[i].setY(roles[i].getY() + roles[i].getSpeedY());

			// 不能掉到线下
			if (roles[i].getY() + roles[i].getHeight() >= lineY) {
				// 此时已经落到线上了
				roles[i].setY(lineY - roles[i].getHeight());
				roles[i].setSpeedY(0);
				// 改变状态(先让小人跳起来写在点击区域中的ontouch事件中)
				roles[i].setJump(false);
			}

			// 让障碍物重右往左移动，改变left和right
			rects[i].left = rects[i].left - (h / 300);
			rects[i].right = rects[i].right - (h / 300);
			// 障碍物移出界面
			if (rects[i].right <= 0) {
				// 重新回到右边生成矩形
				// 随机障碍物宽高
				int random_w = (int) (roles[i].getWidth() * (Math.random() * 5 + 1)/(3*gameType));
				int random_h = (int) (roles[i].getHeight() * (Math.random() * 5 + 1)/(3*gameType));
				int random_start = (int) (w * (new Random().nextFloat() / 5));

				rects[i].left = 3 * w / 2 + random_start;
				rects[i].right = rects[i].left + random_w;
				// bottom不变
				// rects[i].bottom = lineY;
				rects[i].top = rects[i].bottom - random_h;
			}
			// 碰撞检测 待完善
			Rect roleRect = roles[i].getRectFromRole();
			// 添加顶部时间
			long myTime = System.currentTimeMillis() - beginTime;
			String myTimeStr = myTime / 1000 + "." + myTime % 1000;
			String timeText = myTimeStr.substring(0, myTimeStr.length() - 1) + "''";

			if (rects[i].intersect(roleRect)) {
				canvas.drawColor(Color.WHITE,PorterDuff.Mode.CLEAR);
				canvas.drawColor(Color.WHITE);
				canvas.drawText(text, w / 2 - (text.length() * 60 / 2), h - h / 20, paint);
				// 已经发生碰撞
				// 改变游戏状态
				// roleRect.
				paint.setTextSize(50);
				String topText = "你挂了，本次成绩为" + myTimeStr.substring(0, myTimeStr.length() - 1) + "秒!";

				canvas.drawText(topText, w / 2 - (topText.length() * 40 / 2), (float) (h * 0.5 / 5), paint);

				canvas.drawText(" 普   通  ", w / 2 - (topText.length() * 10 / 2), (float) (h / 5), paint);
				canvas.drawText(" 困   难  ", w / 2 - (topText.length() * 10 / 2), (float) (h *1.5/ 5), paint);

				Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), 
						R.drawable.lizicong2);
				canvas.drawBitmap(bitmap, w / 2 - bitmap.getWidth()/ 2, 
						(float) (h*2/ 5), paint);
				isStart = false;
				gameStatu = STANDOFF;
				return;

			} else {
				if (fristLocation <= 0) {
					fristLocation = w / 2 - (timeText.length() * 25 / 2);
				}

				if (myTime - preTime > 10) {
					canvas.drawText(timeText, 0, (float) (h * 0.5 / 5), paint);
					preTime = myTime;
				}
			}

			// 画线
			canvas.drawLine(0, lineY, w, lineY, paint);
			// 画小人
			roles[i].drawSelf(canvas);
			// 画障碍物
			canvas.drawRect(rects[i], paint);

		}
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public int getGameStatu() {
		return gameStatu;
	}

	public void setGameStatu(int gameStatu) {
		this.gameStatu = gameStatu;
	}
}
