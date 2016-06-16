package edu.hzuapps.androidworks.homeworks.net1314080903115.view;


import edu.hzuapps.androidworks.homeworks.net1314080903115.Net1314080903115MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author LDM
 * @fileName MySlidingMenuView.java
 * @date 2012-12-20 下午1:51:49
 * 侧滑分类菜单
 */
public class Net1314080903115_MySlidingMenuView extends ViewGroup {

	private float lTouchX;// touch操作被触发时记录当前X坐标
	private float lLastDownX;//最后一次按下的坐标值
	private float lTouchSlop = 25;//滑动时产生粘性效果
	private int LEFT_VIEW_WIDTH = 0;//菜单宽度
	private boolean LEFT_VIEW_STATE;//菜单状态 true显示

	private int lSlidingMenuState;//菜单状态，平铺式出或覆盖式

	private boolean PULL_DIRECTION;// true向右拉动 false向左拉动
	// 拉动比率 ：当view已经被拉过边缘时，操作没有停止，VIEW的跟随减慢的比率
	private double PULL_RATIO = 0.5;

	private boolean enter = true;// 是否可以进入验证，手指离开屏幕时使用

	/**
	 * 默认平铺样式
	 */
	public static final int SLIDING_MENU_TILE = 0;

	/**
	 * 覆盖式样式
	 */
	public static final int SLIDING_MENU_COVER = 1;

	public Net1314080903115_MySlidingMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public Net1314080903115_MySlidingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Net1314080903115_MySlidingMenuView(Context context) {
		super(context);
		init(context);
	}

	void init(Context context)
	{
		postDelayed(new Runnable() {
			@Override
			public void run() {
				LEFT_VIEW_WIDTH = getChildAt(0).getWidth();
				scrollTo(LEFT_VIEW_WIDTH, 0);
				if(lSlidingMenuState == SLIDING_MENU_COVER){
					/*隐藏并保留菜单空间，如果不隐藏则会执行平滑拉出操作 */
					getChildAt(0).setVisibility(View.INVISIBLE);
				}
			}
		}, 50);
	}

	@Override
	public void scrollTo(int x, int y)
	{
		super.scrollTo(x, y);
		postInvalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final View child1 = getChildAt(0);
		child1.measure(child1.getLayoutParams().width + child1.getLeft() + child1.getRight(), heightMeasureSpec);

		final View child2 = getChildAt(1);
		child2.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		int childLeft = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth, child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	/*
	 * 当屏幕被按下时会调用方法
	 * 参数MotionEvent封装了触屏事件的所有信息
	 * */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction())
		{//触摸的状态
			case MotionEvent.ACTION_UP://屏幕被抬起


				int offux = getScrollX();// 默认不移动

				// 向左拉动超过边缘
				if (getScrollX() < 0) {
					offux = 0;
				}
				// 向右拉动超过边缘
				if (enter && getScrollX() > LEFT_VIEW_WIDTH) {
					offux = LEFT_VIEW_WIDTH;
					enter = !enter;
				}

				// 边缘内左右拉动
				if (enter && PULL_DIRECTION) {
					if ((LEFT_VIEW_WIDTH - getScrollX()) >= LEFT_VIEW_WIDTH / 3) {// 可以展开第一页面
						offux = 0;
					} else {// 拉动距离不够，回到第二界面
						offux = LEFT_VIEW_WIDTH;
					}
					enter = !enter;
				}

				// 边缘内向左拉动
				if (enter && !PULL_DIRECTION) {
					if (getScrollX() >= LEFT_VIEW_WIDTH / 3) {
						offux = LEFT_VIEW_WIDTH;
					} else {
						offux = 0;
					}
				}
				scrollTo(offux, 0);// 根据上面的计算执行得出的最终结果
				if(lSlidingMenuState == SLIDING_MENU_COVER){
					if(offux == 0){
						LEFT_VIEW_STATE = true;
						getChildAt(0).setVisibility(View.VISIBLE);
					}
					if(offux == LEFT_VIEW_WIDTH){
						LEFT_VIEW_STATE = false;
						getChildAt(0).setVisibility(View.INVISIBLE);
					}
				}
				enter = true;// 每次使用完还原状态


				break;
			case MotionEvent.ACTION_DOWN://屏幕被按下
				lTouchX = event.getX();//记录touch操作被触发时记录当前X坐标
				lLastDownX = event.getX();//记录最后一次按下的x坐标值
				break;
			case MotionEvent.ACTION_MOVE://在屏幕中拖动
				if (lLastDownX > lTouchSlop)
				{
					//如果滑动时菜单项为显示状态，将其隐藏
					if(LEFT_VIEW_STATE)
					{
						getChildAt(0).setVisibility(View.INVISIBLE);//可见性设为不可见
					}
					int offx = 0;// 此次移动的偏移量
					// 计算拉动是否超出边缘
					if ((getScrollX() + (int) (lTouchX - event.getX()) < 0)//getScrollX() 就是当前view的左上角相对于母视图的左上角的X轴偏移量
							|| (getScrollX() + (int) (lTouchX - event.getX()) > LEFT_VIEW_WIDTH))
					{// VIEW已经被拉过边缘
						offx = (int) ((lTouchX - event.getX()) * PULL_RATIO);
					}
					else
					{// VIEW处于边缘内
						offx = (int) (lTouchX - event.getX());
						scrollBy(offx, 0);
					}

					// 根据本次的移动距离计算用户的方向,PULL_DIRECTION 取值true向右拉动 false向左拉动
					PULL_DIRECTION = lTouchX == event.getX() ? PULL_DIRECTION : lTouchX < event.getX();



					lTouchX = event.getX();
				}
				break;
			default:
				break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	void enableChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(true);
		}
	}

	void clearChildrenCache() {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View layout = (View) getChildAt(i);
			layout.setDrawingCacheEnabled(false);
		}
	}



	/**
	 * 获取当前菜单使用的样式CODE
	 * @return
	 */
	public int getlSlidingMenuState() {
		return lSlidingMenuState;
	}

	/**
	 * 设置菜单的出现样式。取值范围：MySlidingMenuView.SLIDING_MENU_TILE(default) or MySlidingMenuView.SLIDING_MENU_COVER
	 * @param lSlidingMenuState
	 */
	public void setlSlidingMenuState(int lSlidingMenuState) {
		this.lSlidingMenuState = lSlidingMenuState;
	}

	public void MenuButton()
	{

	}

}
