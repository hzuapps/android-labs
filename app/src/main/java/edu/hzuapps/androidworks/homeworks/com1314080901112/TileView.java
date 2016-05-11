package com.xmobileapp.Snake;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class TileView extends View {

	protected static int mTileSize;   //每个tile的边长的像素数量 

	protected static int mXTileCount;  //屏幕内能容纳的 X方向上方块的总数量  
	protected static int mYTileCount;

	private static int mXOffset;      //原点坐标，按pixel计。X Y的偏移量  
	private static int mYOffset;

	//存储着不同种类的bitmap图。通过resetTiles，loadTile，将游戏中的方块加载到这个数组。
	private Bitmap[] mTileArray; 
	
	/*存储整个界面内每个tile位置应该绘制的tile。 
    * 可看作是我们直接操作的画布。 
    * 通过setTile、clearTile 进行图形显示的修改操作。
    */
	private int[][] mTileGrid;  //存放图片的数组
	
	//画笔，canvas的图形绘制，需要画笔Paint实现。
	private final Paint mPaint = new Paint();

	public TileView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//使用TypedArray，获取在attrs.xml中为TileView定义的新属性tileSize
		//tileSize属性定义为整型数
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TileView);

		mTileSize = a.getInt(R.styleable.TileView_tileSize, 12);
		//返回先前使用过的风格属性集，使用TypedArray后一定要使用这个
		a.recycle();
	}

	public TileView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TileView);

		mTileSize = a.getInt(R.styleable.TileView_tileSize, 12);

		a.recycle();
	}
	 //重置清零mTileArray，在游戏初始的时候使用。
	public void resetTiles(int tilecount) {
		mTileArray = new Bitmap[tilecount];
	}

	//当改变屏幕大小尺寸时，同时修改tile的相关计数指标。
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mXTileCount = (int) Math.floor(w / mTileSize);//获得X轴能放多少个节
		mYTileCount = (int) Math.floor(h / mTileSize);

		mXOffset = ((w - (mTileSize * mXTileCount)) / 2);//获得X偏移量
		mYOffset = ((h - (mTileSize * mYTileCount)) / 2);
		
		//声明用来存放绘画图像的X，Y轴的位置的数组 ，创建整个网格数组
		mTileGrid = new int[mXTileCount][mYTileCount];
		clearTiles();//清屏
	}

	/*加载具体的砖块图片 到 砖块字典。 
    *即将对应的砖块的图片 对应的加载到 mTileArray数组中 
    */
	public void loadTile(int key, Drawable tile) {
		//这里做了一个 Drawable 到 bitmap 的转换。由于外部程序使用的时候是直接读取资源文件中的图片，  
        //是drawable格式，而我们的数组是bitmap格式，方便最终的绘制。所以，需要进行一次到 bitmap的转换。  
		Bitmap bitmap = Bitmap.createBitmap(mTileSize, mTileSize,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);//创建画布
		tile.setBounds(0, 0, mTileSize, mTileSize);//画图
		tile.draw(canvas);

		mTileArray[key] = bitmap;
	}
    /*
     * 清空图形显示。 
     * 用以更新画面。 
     * 调用了绘图的setTile()。 
     */
	public void clearTiles() {
		for (int x = 0; x < mXTileCount; x++) {
			for (int y = 0; y < mYTileCount; y++) {
				setTile(0, x, y);
			}
		}
	}
	//在相应的坐标位置绘制相应的砖块 
    //mTileGrid其实就是我们直接操作的画布。
	public void setTile(int tileindex, int x, int y) {
		mTileGrid[x][y] = tileindex;
	}

	//将我们直接操作的画布绘制到手机界面上！
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int x = 0; x < mXTileCount; x += 1) {
			for (int y = 0; y < mYTileCount; y += 1) {
				if (mTileGrid[x][y] > 0) {
					canvas.drawBitmap(mTileArray[mTileGrid[x][y]], mXOffset + x
							* mTileSize, mYOffset + y * mTileSize, mPaint);
				}
			}
		}

	}

}
