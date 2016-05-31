package edu.hzuapps.androidworks.homeworks.net1314080903117;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({ "InflateParams", "ClickableViewAccessibility", "SimpleDateFormat" })
public class Net1314080903117ReflashListView extends ListView implements OnScrollListener {
	
	View header;//定义一个全局View，为顶部布局文件；
	View footer;//定义一个全局的View，为底部布局文件
	
	int headerHeight;//顶部布局文件的高度
	int firstVisibleItem;//当前界面第一个可见的item的位置；
	boolean isRemark;//标记，判断是否在ListView最顶端往下拉的；
	int startY;//开始下拉时的Y值
	
	int state;//当前的状态
	final int NONE = 0;//正常状态
	final int PULL = 1; //下拉时候，提示下拉可刷新的状态
	final int RELESE = 2; //下拉到一定高度后，提示松开可以刷新的状态
	final int REFLASHING = 3; // 松开以后，正在刷新中的状态；
	
	int scrollState;//ListView当前的滚动状态

	IReflashListener iReflashListener;
	
	
	int totalItemCount;
	int lastVisibleItem;
	boolean isLoading;
	private TextView loading = null;
	private ProgressBar pBarLoading = null;
	
	

	public Net1314080903117ReflashListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public Net1314080903117ReflashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	public Net1314080903117ReflashListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	/*
	 * 初始化布局文件，添加布局文件到ListView中，并在每个构造方法中调用
	 */
	private void initView(Context context){
		//将顶部布局文件关联,底部文件关联
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.net1314080903117header_layout, null);
		
		
		footer = inflater.inflate(R.layout.net1314080903117footer,null);
		//需要先将底部布局隐藏
		footer.setVisibility(View.GONE);
		
		
		
		measureView(header);
		
		//将顶部布局的高度的负值先传入topPadding（）
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);//传入负值，即表示隐藏了顶部的刷新布局
		
		//将关联的布局文件添加到ListView中，通过addHeaderView()的方法
		this.addHeaderView(header);
		this.addFooterView(footer);
		
		//设定ListView的滚动监听事件
		this.setOnScrollListener(this);
		
		loading = (TextView) footer.findViewById(R.id.loading);
		pBarLoading = (ProgressBar) footer.findViewById(R.id.progressbar);
		
	}
	
	/**
	 * 通知父布局ListView，顶部文件所需要的宽度和高度
	 * @param view
	 */
	private void measureView(View view){
		
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		/*
		 * 定义一个宽度，获取子布局的宽度
		 * spec 为当前header的左右边距
		 * padding 为当前header的内边距
		 * childDimension 为子布局的宽度
		 */
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if (tempHeight > 0) {
			/*
			 * mode 模式为MeasureSpec.EXACTLY
			 * 高度不为空的时候，填充布局
			 */
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
			
		}else {
			//高度为0时，不填充内容
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
		
	}
	
	/**
	 * 定义的方法，传入一个高度
	 * 即设置顶部布局的上边距
	 * 左边距、右边距、底边距不变，上边距设为传入的高度
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		// TODO Auto-generated method stub
		header.setPadding(header.getPaddingLeft(), topPadding, 
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
		
		
	}
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.scrollState = scrollState;
		if (totalItemCount == lastVisibleItem && scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading = true;
				footer.setVisibility(View.VISIBLE);
				loading.setText("加载更多");
				iReflashListener.onLoad();
			}
		}
		
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		this.totalItemCount = totalItemCount;
		this.lastVisibleItem = firstVisibleItem+visibleItemCount;
		
	}
	
	/**
	 * 设置监听手势触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//判断当前界面是否在界面最顶端
			if (firstVisibleItem == 0) {
				isRemark = true;
				//记录下拉时候开始的Y值
				startY = (int) ev.getY();
				
			}
			
			break;

		case MotionEvent.ACTION_UP:
			//判断当前状态
			if (state == RELESE) {
				state = REFLASHING;
				//加载最新数据
				reflashViewByState();
				//调用接口的刷新方法
				iReflashListener.onReflash();
			}else if(state == PULL){
				//不是刷新的状态的话，就不加载数据，将状态变为正常的状态
				state =NONE;
				isRemark = false;
				reflashViewByState();
			}
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			
			onMove(ev);
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 判断手势移动过程中的操作
	 */
	private void onMove(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (!isRemark) {
			return ;
		}
		//获取当前下拉的位置
		int tempY =(int) ev.getY();
		//获取下拉开始移动了的具体
		int space = tempY - startY;
		//设定在移动过程顶部布局的topPadding的值，来，慢慢显示顶部布局
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			if (space > 0) {
				state =PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);
			//下拉距离大于一定高度的时候，并且滚动状态为正在滚动中
			if (space > headerHeight+30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELESE;
				reflashViewByState();
			}
			break;
		case RELESE:
			topPadding(topPadding);
			//如果小于一定的高度，状态变回下拉可以刷新状态
			if (space < headerHeight+30) {
				state = PULL;
				reflashViewByState();
			}else if (space <=0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		
	}
	/**
	 * 根据当前的状态来刷新显示界面
	 */
	private void reflashViewByState(){
		
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progressBar = (ProgressBar) header.findViewById(R.id.progressbar);
		
		/**
		 * 给箭头添加动画效果
		 * 一开始从0--180度变化
		 */
		RotateAnimation rotateAnimation = new RotateAnimation(0,180,
				RotateAnimation.RELATIVE_TO_SELF,0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5F);
		//设定动画之间的时间间隔
		rotateAnimation.setDuration(800);
		rotateAnimation.setFillAfter(true);
		
		//方向反转效果
		RotateAnimation rotateAnimation1 = new RotateAnimation(180,0,
				RotateAnimation.RELATIVE_TO_SELF,0.5f,
				RotateAnimation.RELATIVE_TO_SELF,0.5F);
		//设定动画之间的时间间隔
		rotateAnimation1.setDuration(800);
		rotateAnimation1.setFillAfter(true);
		
		switch (state) {
		case NONE:
			//正常状态不显示，设置topPadding的值为负值即可
			topPadding(-headerHeight);
			arrow.clearAnimation();
			break;
		case PULL:
			//下拉状态，提示箭头为下拉状态，进度条为隐藏状态
			arrow.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tip.setText("=￣ω￣=下拉刷新啦");
			//先清除动画效果，再设置动画效果，箭头从180到0度变化
			arrow.clearAnimation();
			arrow.setAnimation(rotateAnimation1);
			break;
		case RELESE:
			//松开可以刷新的状态，提示显示的箭头处于下拉状态，进度条隐藏
			arrow.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tip.setText("敢放开，我就变身啦");
			arrow.clearAnimation();
			arrow.setAnimation(rotateAnimation);
			break;

		case REFLASHING:
			//正在刷新的状态，在固定的高度显示，不会消失，提示箭头隐藏，进度条为显示状态
			topPadding(30);
			arrow.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			tip.setText("变身中...");
			arrow.clearAnimation();
			break;
		}
		
		
	}
	
	//数据刷新完成以后调用这个方法
	public void reflashComplete(){
		state = NONE;
		isRemark = false;
		//刷新当前界面
		reflashViewByState();
		//设定上次更新时间
		TextView lastupdatatime = (TextView) header.findViewById(R.id.lastupdate_time);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = format.format(date);
		lastupdatatime.setText("上次更新："+time);
	}
	
	public void allDataReflashComplete(){
		isRemark =false;
		TextView tip = (TextView) header.findViewById(R.id.tip);
		tip.setText("木有新数据咯，等等哈");
		
	}
	
	public void GoneHeader() {
		topPadding(-headerHeight);
		header.setVisibility(View.GONE);
	}
	
	public void loadingComplete(){
		isLoading = false;
		footer.setVisibility(View.GONE);
		
	}
	public void addDataLoadingComplete(){
		isLoading =false;
		loading.setText("全部数据已加载完成");
		pBarLoading.setVisibility(View.GONE);
	}
	
	
	
	
	
	//设定一个方法，将接口引入进来
	public void setReflashListener(IReflashListener iReflashListener){
		this.iReflashListener =iReflashListener;
	}
	
	
	/*
	 * 定义了一个刷新数据的接口
	 * 需要在MainActivity中去实现这个接口
	 */
	public interface IReflashListener{
		/*
		 * 接口中定义两个方法
		 * onReflash（）；下拉刷新的方法
		 * onLoad（）；上拉自动加载更多的方法
		 */
		public void onReflash();
		public void onLoad();
		
	}

}
