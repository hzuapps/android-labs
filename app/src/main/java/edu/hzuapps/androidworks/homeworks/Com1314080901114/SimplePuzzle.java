package edu.hzuapps.androidworks.homeworks.Com1314080901114;

import java.util.Random;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

@SuppressWarnings("deprecation")
public class SimplePuzzle extends Activity {
	
	private static final int MENU_ABOUT = Menu.FIRST;
	private static final int MENU_EXIT = Menu.FIRST + 1;
	
	// 存储图片序列的int数组,最后一位为图片标记,用于换图片时的判断
	// 默认图片
	int[] queue = { R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
			R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8,
			R.drawable.p9, 1 };
	// 图片p系列
	int[] queue1 = { R.drawable.p1, R.drawable.p2, R.drawable.p3,
			R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7,
			R.drawable.p8, R.drawable.p9, 1 };
	// 图片a系列
	int[] queue2 = { R.drawable.a1, R.drawable.a2, R.drawable.a3,
			R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7,
			R.drawable.a8, R.drawable.a9, 2 };

	// ImageView 图片显示区域,按九宫格分为九个区域
	private ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8, iv_9;

	// 图片的位置参数对象，
	// 通过Drawable对象的getLayoutParams()方法取得，可得到图片的位置信息
	// 通过设置LayoutParams方法的x,y坐标，并传给图片，可以改变图片的位置，如：
	// setLayoutParams(paramsIv_1)

	private AbsoluteLayout.LayoutParams paramsIv_1, paramsIv_2, paramsIv_3,
			paramsIv_4, paramsIv_5, paramsIv_6, paramsIv_7, paramsIv_8,
			paramsIv_9;

	// 记录图片的长宽，与左上角一点的坐标
	private int picWidth, picHeight, picX1, picY1, picX2, picY2, picX3, picY3,
			picX4, picY4, picX5, picY5, picX6, picY6, picX7, picY7, picX8,
			picY8, picX9, picY9;

	// 在onTouchEvent方法中设置，标记是否击中图片
	private boolean clickPic1, clickPic2, clickPic3, clickPic4, clickPic5,
			clickPic6, clickPic7, clickPic8, clickPic9;

	// 图片锁，每次只能对一个图片进行操作。flag为真时表明已经ACTION_DOWN一张图片
	private boolean flag = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// 全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 加载layout
		setContentView(R.layout.com1314080901114main);
		// 按照queue的顺序显示图片
		findImageView(queue);
		//按钮
		Button button01 = (Button) findViewById(R.id.button01);
		Button button02 = (Button) findViewById(R.id.button02);
		button01.setOnClickListener(button01Listener);
		button02.setOnClickListener(button02Listener);
	}

	/**
	 * 创建菜单选项
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//关于选项
		menu.add(0, MENU_ABOUT, 0, R.string.menu_about);
		//退出选项
		menu.add(0, MENU_EXIT, 0, R.string.menu_exit);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ABOUT:
			Toast
					.makeText(
							this,
							"App coded by ZL at 2010.9.17\nhttp://blog.csdn.net/zzzrlhzl",
							Toast.LENGTH_SHORT).show();
			break;
		case MENU_EXIT:
			finish();
		}

		return true;
	}

	/**
	 * 打乱数组顺序
	 * 
	 * @param intArray
	 * @return 打乱后的int数组
	 */
	public static int[] shuffle(int[] intArray) {
		Random ran = new Random();
		int num;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				num = ran.nextInt(9);
				swap(intArray, j, num);
			}
		}
		return intArray;
	}

	/**
	 * 用于交换intArray中下标分别为index1和index2的值
	 */
	public static void swap(int[] intArray, int index1, int index2) {
		int temp = 0;
		temp = intArray[index1];
		intArray[index1] = intArray[index2];
		intArray[index2] = temp;
	}

	/**
	 * 打乱数组
	 */
	private OnClickListener button01Listener = new OnClickListener() {
		public void onClick(View v) {
			queue = shuffle(queue);
			findImageView(queue);
		}
	};

	/**
	 * 换图片
	 */
	private OnClickListener button02Listener = new OnClickListener() {
		public void onClick(View v) {
			//判断数组最后一位的标记,只有两幅图,随便做一个简单判断
			if (queue[9] == 1)
				//数组复制,如果用queue==queue2的话,会让queue指向queue2的地址,
				//修改queue的同时也会修改queue2
				System.arraycopy(queue2, 0, queue, 0, queue2.length);
			else
				System.arraycopy(queue1, 0, queue, 0, queue1.length);
			findImageView(queue);
		}
	};

	/**
	 * 按照int数组queue的顺序显示图片
	 * 
	 * @param queue
	 */
	@SuppressWarnings("deprecation")
	public void findImageView(int[] queue) {
		//每幅分图的大小
		picHeight = 106;
		picWidth = 70;

		Drawable pic1 = getResources().getDrawable(queue[0]);
		iv_1 = (ImageView) this.findViewById(R.id.ImageView01);
		iv_1.setImageDrawable(pic1);
		// CENTER_INSIDE 将图片的内容完整居中显示，通过按比例缩小原来的size使得图片长/宽等于或小于View的长/宽
		iv_1.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_1 = (LayoutParams) iv_1.getLayoutParams();
		picX1 = paramsIv_1.x;
		picY1 = paramsIv_1.y;

		Drawable pic2 = getResources().getDrawable(queue[1]);
		iv_2 = (ImageView) this.findViewById(R.id.ImageView02);
		iv_2.setImageDrawable(pic2);
		iv_2.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_2 = (LayoutParams) iv_2.getLayoutParams();
		picX2 = paramsIv_2.x;
		picY2 = paramsIv_2.y;

		Drawable pic3 = getResources().getDrawable(queue[2]);
		iv_3 = (ImageView) this.findViewById(R.id.ImageView03);
		iv_3.setImageDrawable(pic3);
		iv_3.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_3 = (LayoutParams) iv_3.getLayoutParams();
		picX3 = paramsIv_3.x;
		picY3 = paramsIv_3.y;

		Drawable pic4 = getResources().getDrawable(queue[3]);
		iv_4 = (ImageView) this.findViewById(R.id.ImageView04);
		iv_4.setImageDrawable(pic4);
		iv_4.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_4 = (LayoutParams) iv_4.getLayoutParams();
		picX4 = paramsIv_4.x;
		picY4 = paramsIv_4.y;

		Drawable pic5 = getResources().getDrawable(queue[4]);
		iv_5 = (ImageView) this.findViewById(R.id.ImageView05);
		iv_5.setImageDrawable(pic5);
		iv_5.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_5 = (LayoutParams) iv_5.getLayoutParams();
		picX5 = paramsIv_5.x;
		picY5 = paramsIv_5.y;

		Drawable pic6 = getResources().getDrawable(queue[5]);
		iv_6 = (ImageView) this.findViewById(R.id.ImageView06);
		iv_6.setImageDrawable(pic6);
		iv_6.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_6 = (LayoutParams) iv_6.getLayoutParams();
		picX6 = paramsIv_6.x;
		picY6 = paramsIv_6.y;

		Drawable pic7 = getResources().getDrawable(queue[6]);
		iv_7 = (ImageView) this.findViewById(R.id.ImageView07);
		iv_7.setImageDrawable(pic7);
		iv_7.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_7 = (LayoutParams) iv_7.getLayoutParams();
		picX7 = paramsIv_7.x;
		picY7 = paramsIv_7.y;

		Drawable pic8 = getResources().getDrawable(queue[7]);
		iv_8 = (ImageView) this.findViewById(R.id.ImageView08);
		iv_8.setImageDrawable(pic8);
		iv_8.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_8 = (LayoutParams) iv_8.getLayoutParams();
		picX8 = paramsIv_8.x;
		picY8 = paramsIv_8.y;

		Drawable pic9 = getResources().getDrawable(queue[8]);
		iv_9 = (ImageView) this.findViewById(R.id.ImageView09);
		iv_9.setImageDrawable(pic9);
		iv_9.setScaleType(ScaleType.CENTER_INSIDE);
		paramsIv_9 = (LayoutParams) iv_9.getLayoutParams();
		picX9 = paramsIv_9.x;
		picY9 = paramsIv_9.y;
	}

	int temp;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();

		float y = event.getY();

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			// flag为TRUE时代表已经按下一张图片，跳过此部
			if (flag) {
				break;
			} else {
				flag = true;

				// if语句 判断当鼠标点下时，是否击中图片

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					clickPic1 = true;
				} else {
					clickPic1 = false;
				}

				if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					clickPic2 = true;
				} else {
					clickPic2 = false;
				}

				if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					clickPic3 = true;
				} else {
					clickPic3 = false;
				}

				if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					clickPic4 = true;
				} else {
					clickPic4 = false;
				}

				if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					clickPic5 = true;
				} else {
					clickPic5 = false;
				}

				if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					clickPic6 = true;
				} else {
					clickPic6 = false;
				}

				if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					clickPic7 = true;
				} else {
					clickPic7 = false;
				}

				if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					clickPic8 = true;
				} else {
					clickPic8 = false;
				}

				if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					clickPic9 = true;
				} else {
					clickPic9 = false;
				}

				break;
			}

		case MotionEvent.ACTION_UP:
			// 保持ImageView的位置不变
			paramsIv_1.x = picX1;
			paramsIv_1.y = picY1;
			iv_1.setLayoutParams(paramsIv_1);

			paramsIv_2.x = picX2;
			paramsIv_2.y = picY2;
			iv_2.setLayoutParams(paramsIv_2);

			paramsIv_3.x = picX3;
			paramsIv_3.y = picY3;
			iv_3.setLayoutParams(paramsIv_3);

			paramsIv_4.x = picX4;
			paramsIv_4.y = picY4;
			iv_4.setLayoutParams(paramsIv_4);

			paramsIv_5.x = picX5;
			paramsIv_5.y = picY5;
			iv_5.setLayoutParams(paramsIv_5);

			paramsIv_6.x = picX6;
			paramsIv_6.y = picY6;
			iv_6.setLayoutParams(paramsIv_6);

			paramsIv_7.x = picX7;
			paramsIv_7.y = picY7;
			iv_7.setLayoutParams(paramsIv_7);

			paramsIv_8.x = picX8;
			paramsIv_8.y = picY8;
			iv_8.setLayoutParams(paramsIv_8);

			paramsIv_9.x = picX9;
			paramsIv_9.y = picY9;
			iv_9.setLayoutParams(paramsIv_9);

			flag = false;
			if (clickPic1) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					// 交换全局int数组queue的元素顺序，达到交换图片位置的目的
					temp = queue[0];
					queue[0] = queue[1];
					queue[1] = temp;
					// 显示交换顺序后的图片
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[0];
					queue[0] = queue[2];
					queue[2] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[0];
					queue[0] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[0];
					queue[0] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[0];
					queue[0] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[0];
					queue[0] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[0];
					queue[0] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[0];
					queue[0] = queue[8];
					queue[8] = temp;
					findImageView(queue);
				} else {

				}
			}

			if (clickPic2) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[1];
					queue[1] = queue[2];
					queue[2] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[1];
					queue[1] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[1];
					queue[1] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[1];
					queue[1] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[1];
					queue[1] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[1];
					queue[1] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[1];
					queue[1] = queue[8];
					queue[8] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic3) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[2];
					queue[2] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[2];
					queue[2] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[2];
					queue[2] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[2];
					queue[2] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[2];
					queue[2] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[2];
					queue[2] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[2];
					queue[2] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[2];
					queue[2] = queue[8];
					queue[8] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic4) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[3];
					queue[3] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[2];
					queue[2] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[4];
					queue[4] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[5];
					queue[5] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[6];
					queue[6] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[7];
					queue[7] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[8];
					queue[8] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic5) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[4];
					queue[4] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[2];
					queue[2] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[4];
					queue[4] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[5];
					queue[5] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[6];
					queue[6] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[7];
					queue[7] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[8];
					queue[8] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic6) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[5];
					queue[5] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[2];
					queue[2] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[5];
					queue[5] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[5];
					queue[5] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[6];
					queue[6] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[7];
					queue[7] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[8];
					queue[8] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic7) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[6];
					queue[6] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[2];
					queue[2] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[6];
					queue[6] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[6];
					queue[6] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[6];
					queue[6] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[7];
					queue[7] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[8];
					queue[8] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic8) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[7];
					queue[7] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[2];
					queue[2] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[7];
					queue[7] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[7];
					queue[7] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[7];
					queue[7] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[7];
					queue[7] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX9 && x <= picX9 + picWidth && y >= picY9
						&& y <= picY9 + picHeight) {
					temp = queue[8];
					queue[8] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else {

				}

			}

			if (clickPic9) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				if (x >= picX1 && x <= picX1 + picWidth && y >= picY1
						&& y <= picY1 + picHeight) {
					temp = queue[0];
					queue[0] = queue[8];
					queue[8] = temp;
					findImageView(queue);
				} else if (x >= picX2 && x <= picX2 + picWidth && y >= picY2
						&& y <= picY2 + picHeight) {
					temp = queue[8];
					queue[8] = queue[1];
					queue[1] = temp;
					findImageView(queue);
				} else if (x >= picX3 && x <= picX3 + picWidth && y >= picY3
						&& y <= picY3 + picHeight) {
					temp = queue[2];
					queue[2] = queue[8];
					queue[8] = temp;
					findImageView(queue);
				} else if (x >= picX4 && x <= picX4 + picWidth && y >= picY4
						&& y <= picY4 + picHeight) {
					temp = queue[8];
					queue[8] = queue[3];
					queue[3] = temp;
					findImageView(queue);
				} else if (x >= picX5 && x <= picX5 + picWidth && y >= picY5
						&& y <= picY5 + picHeight) {
					temp = queue[8];
					queue[8] = queue[4];
					queue[4] = temp;
					findImageView(queue);
				} else if (x >= picX6 && x <= picX6 + picWidth && y >= picY6
						&& y <= picY6 + picHeight) {
					temp = queue[8];
					queue[8] = queue[5];
					queue[5] = temp;
					findImageView(queue);
				} else if (x >= picX7 && x <= picX7 + picWidth && y >= picY7
						&& y <= picY7 + picHeight) {
					temp = queue[8];
					queue[8] = queue[6];
					queue[6] = temp;
					findImageView(queue);
				} else if (x >= picX8 && x <= picX8 + picWidth && y >= picY8
						&& y <= picY8 + picHeight) {
					temp = queue[8];
					queue[8] = queue[7];
					queue[7] = temp;
					findImageView(queue);
				} else {

				}

			}
			break;

		case MotionEvent.ACTION_MOVE:

			// 当Action_Down击中图片时，才执行move的动作

			// 否则跳过

			if (clickPic1) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_1.x = (int) (x - picWidth / 2);

				paramsIv_1.y = (int) (y - picHeight / 2);
				//通过setLayoutParams达到图片跟随手指移动的目的
				iv_1.setLayoutParams(paramsIv_1);

			}

			if (clickPic2) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_2.x = (int) (x - picWidth / 2);

				paramsIv_2.y = (int) (y - picHeight / 2);

				iv_2.setLayoutParams(paramsIv_2);

			}

			if (clickPic3) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_3.x = (int) (x - picWidth / 2);

				paramsIv_3.y = (int) (y - picHeight / 2);

				iv_3.setLayoutParams(paramsIv_3);

			}

			if (clickPic4) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_4.x = (int) (x - picWidth / 2);

				paramsIv_4.y = (int) (y - picHeight / 2);

				iv_4.setLayoutParams(paramsIv_4);
			}

			if (clickPic5) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_5.x = (int) (x - picWidth / 2);

				paramsIv_5.y = (int) (y - picHeight / 2);

				iv_5.setLayoutParams(paramsIv_5);
			}

			if (clickPic6) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_6.x = (int) (x - picWidth / 2);

				paramsIv_6.y = (int) (y - picHeight / 2);

				iv_6.setLayoutParams(paramsIv_6);
			}

			if (clickPic7) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_7.x = (int) (x - picWidth / 2);

				paramsIv_7.y = (int) (y - picHeight / 2);

				iv_7.setLayoutParams(paramsIv_7);
			}

			if (clickPic8) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_8.x = (int) (x - picWidth / 2);

				paramsIv_8.y = (int) (y - picHeight / 2);

				iv_8.setLayoutParams(paramsIv_8);
			}

			if (clickPic9) {

				// picWidth与picHeight分别除以2，定位击中点为图片的中心

				paramsIv_9.x = (int) (x - picWidth / 2);

				paramsIv_9.y = (int) (y - picHeight / 2);

				iv_9.setLayoutParams(paramsIv_9);
			}

			break;

		}

		return super.onTouchEvent(event);

	}

}