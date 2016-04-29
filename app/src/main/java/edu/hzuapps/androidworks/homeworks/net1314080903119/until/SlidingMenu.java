package edu.hzuapps.androidworks.homeworks.net1314080903119.until;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

import edu.hzuapps.androidworks.homeworks.net1314080903119.R;

public class SlidingMenu extends HorizontalScrollView
{
	private LinearLayout mWapper;
	private ViewGroup left_mMenu;//侧边内容
//	private ViewGroup right_mMenu;//侧边内容
	private ViewGroup mContent;//主内容
	private int mScreenWidth;//屏幕宽度

	public int left_mMenuWidth;//侧边宽度
//	public int right_mMenuWidth;//侧边宽度
	// dp
//	private int mMenuRightPadding = 100;//侧滑时离屏幕右边的距离
	private int mMenuleftPadding = 200;//侧滑时离屏幕右边的距离

	private boolean once;

	private int scrloo1=0;
	private int scrollX;
	private float x1;
	private float x2;
	//	private boolean left_isOpen;
//	private boolean right_isOpen;
//	private boolean left_Opening;
//	private boolean right_Opening;
	private boolean if_Open = false;

	/**
	 * 未使用自定义属性时，调用
	 *
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	/**
	 * 当使用了自定义属性时，会调用此构造方法
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// 获取我们定义的属性
		/***
		 * 1、文件名
		 * 2、属性名
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
//				case R.styleable.SlidingMenu_rightPadding:
//					//如果属性值有进行设置则用设置的值（attr文件中的SlidingMenu_rightPadding），否则用默认的50
//					mMenuleftPadding = a.getDimensionPixelSize(attr,
//							(int) TypedValue.applyDimension(
//									TypedValue.COMPLEX_UNIT_DIP, 50, context
//											.getResources().getDisplayMetrics()));
//					break;
				case R.styleable.SlidingMenu_leftPadding:
					//如果属性值有进行设置则用设置的值（attr文件中的SlidingMenu_rightPadding），否则用默认的50
					mMenuleftPadding = a.getDimensionPixelSize(attr,
							(int) TypedValue.applyDimension(
									TypedValue.COMPLEX_UNIT_DIP, 50, context
											.getResources().getDisplayMetrics()));
					break;
			}
		}
		a.recycle();
		//得到屏幕宽度
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
		//
	}

	public SlidingMenu(Context context)
	{
		this(context, null);
	}

	/**
	 * 设置子View的宽和高 设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		if (!once)
		{
			mWapper = (LinearLayout) getChildAt(0);//得到LinearLayout的第一个元素
			left_mMenu = (ViewGroup) mWapper.getChildAt(0);
//			right_mMenu = (ViewGroup) mWapper.getChildAt(2);
			mContent = (ViewGroup) mWapper.getChildAt(1);//得到LinearLayout的第二个元素
			left_mMenuWidth = left_mMenu.getLayoutParams().width = mScreenWidth - mMenuleftPadding;
//			right_mMenuWidth = right_mMenu.getLayoutParams().width = mScreenWidth
//					- mMenuleftPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 通过设置偏移量，将menu隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			this.scrollTo(left_mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();

		switch (action)
		{
			case  MotionEvent.ACTION_DOWN:
				scrloo1 = getScrollX();
				x1 = ev.getX();
				Log.e("ACTION_DOWN","点击" +scrloo1);
			case MotionEvent.ACTION_UP:{
				x2 = ev.getX();
				// 隐藏在左边的宽度
				scrollX = getScrollX();

				if(if_Open) {
					//向左滑
					if (scrollX < left_mMenuWidth / 3) {
						this.smoothScrollTo(0, 0);
						if_Open = true;
						//				left_isOpen = true;
					} else if (scrollX >= left_mMenuWidth / 3) {
						this.smoothScrollTo(left_mMenuWidth, 0);
						//				left_isOpen = false;
						//				right_isOpen = false;
						if_Open = false;
					}
				}else {
					//向右滑
					if (scrollX < 2*left_mMenuWidth / 3) {
						this.smoothScrollTo(0, 0);
						if_Open = true;
						//				left_isOpen = true;
					} else if (scrollX >= 2*left_mMenuWidth / 3) {
						this.smoothScrollTo(left_mMenuWidth, 0);
						//				left_isOpen = false;
						//				right_isOpen = false;
						if_Open = false;
					}
				}
				return true;
			}

		}
		return super.onTouchEvent(ev);
	}

	public Boolean if_open(){
		return if_Open;

	}
	/**
	 * 打开菜单
	 */
	public void open_left_Menu()
	{
		//if (!left_isOpen)
		if (if_Open)
			return;
		this.smoothScrollTo(0, 0);
//		left_isOpen = true;
		if_Open = true;
	}

	public void close_left_Menu()
	{
		//if (!left_isOpen)
		if (!if_Open)
			return;
		this.smoothScrollTo(left_mMenuWidth, 0);
//		left_isOpen = false;
		if_Open = false;
	}

	/**
	 * 切换菜单
	 */
	public Boolean left_toggle()
	{
//		mMenu = left_mMenu;
		//if (left_isOpen)
		if (if_Open)
		{
			close_left_Menu();
			if_Open = false;
			return false;
		} else
		{
			open_left_Menu();
			if_Open = true;
			return true;
		}
	}
	/**
	 * 打开菜单
	 */
//	public void open_right_Menu()
//	{
////		if (right_isOpen)
//		if (if_Open)
//			return;
//		this.smoothScrollTo(left_mMenuWidth+right_mMenuWidth, 0);
////		right_isOpen = true;
//		if_Open = true;
//	}
//
//	public void close_right_Menu()
//	{
////		if (!right_isOpen)
//		if (!if_Open)
//			return;
//		this.smoothScrollTo(left_mMenuWidth, 0);
////		right_isOpen = false;
//		if_Open = false;
//	}

	/**
	 * 切换菜单
	 */
//	public Boolean right_toggle()
//	{
////		mMenu = right_mMenu;
////		if (right_isOpen)
//		if (if_Open)
//		{
//			close_right_Menu();
//			if_Open = false;
//			return false;
//		} else
//		{
//			open_right_Menu();
//			if_Open = true;
//			return true;
//		}
//	}
	/**
	 * 滚动发生时
	 */

	@Override					//l相当于getScrollX()
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);

		left_mMenu = (ViewGroup) mWapper.getChildAt(0);
//		mMenu2 = (ViewGroup) mWapper.getChildAt(2);
//		if(right_Opening)
//		{mMenu = right_mMenu;}
//		int scrollX = getScrollX();

//		if(scrollX < left_mMenuWidth / 2)
//		{
//			left_mMenuWidth + right_mMenuWidth +mScreenWidth - mScreenWidth -（ left_mMenuWidth-l）
//		}
		float scale = 0;
		if(l <= left_mMenuWidth)
		{scale = l * 1.0f / left_mMenuWidth;} // 1 ~ 0
//		float scale2 = 0.5f * (2 * left_mMenuWidth - l)/left_mMenuWidth;// 1 ~ 0
		/**
		 * 区别1：内容区域1.0~0.7 缩放的效果 scale : 1.0~0.0 0.7 + 0.3 * scale
		 *
		 * 区别2：菜单的偏移量需要修改
		 *
		 * 区别3：菜单的显示时有缩放以及透明度变化 缩放：0.7 ~1.0 1.0 - scale * 0.3 透明度 0.6 ~ 1.0
		 * 0.6+ 0.4 * (1- scale) ;
		 *
		 */
//		float rightScale = 0.7f + 0.3f * scale;
		float leftScale = 1.0f - scale * 0.3f;//缩放的倍数
		float leftAlpha = 0.6f + 0.4f * (1 - scale);//透明度

		// 调用属性动画，设置TranslationX	设置mMenu的偏移量
		ViewHelper.setTranslationX(left_mMenu, left_mMenuWidth * scale * 0.8f);
//		ViewHelper.setTranslationX(mMenu2, -scale2 * left_mMenuWidth);
		ViewHelper.setScaleX(left_mMenu, leftScale);
		ViewHelper.setScaleY(left_mMenu, leftScale);
		ViewHelper.setAlpha(left_mMenu, leftAlpha);

		 //设置content的缩放的中心点
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);

//		ViewHelper.setScaleX(mContent, rightScale);//设置X轴缩放
//		ViewHelper.setScaleY(mContent, rightScale);//设置Y轴缩放

	}

}
