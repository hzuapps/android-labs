package edu.hzuapps.androidworks.homeworks.net1314080903145;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class LiveWallpaper extends WallpaperService {
	public static final String SHARED_PREFS_NAME = "edu.hzuapps.androidworks.homeworks.livewallpaper";
	 DisplayMetrics dm;
	 static int w1;
	 static int h1;
	 static int i=0;
	 static int row=0;
	 static int col=0;
	 static int textCount;
	 int leaf1Count=1;
	 int flower1Count=2;
	 int flower2Count=2;
	 ArrayList<leafInfo> leaf1list=new ArrayList<leafInfo>();
	 ArrayList<leafInfo> flower1list=new ArrayList<leafInfo>();
	 ArrayList<leafInfo> flower2list=new ArrayList<leafInfo>();
	 ArrayList<info> list=new ArrayList<info>();
	 Paint paint= new Paint();
	 static String loveText="冰雪林中著此身 不同桃李混芳尘  忽然一夜清香发 散作乾坤万里春  ";
	 static int wordCount=9;
	@Override
	public Engine onCreateEngine() {
		// TODO Auto-generated method stub
		 return new WallpaperEngine(getResources());
	}
public class LiveWallpaper extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener{
		 private final Handler handler=new Handler();        
	     private Bitmap image; //Image
	     Bitmap image2;
	     private Bitmap flower2; //Image01 for fire01.PNG
	        private Bitmap flower1; //Image02 for fire02.PNG
	        private Bitmap leaf;
	        private boolean visible; //显示状态
	        private int     width;   //长
	        private int     height;  //高
	        //描绘程序
	     Canvas c;
	     SharedPreferences prefs;
	     private final Runnable drawThread=new Runnable() {
	            public void run() {
	            	if(i==LiveWallpaper.loveText.length()-1){
	            		i=0;
	            		row=0;
	            		col=0;
	            		list.clear();
	            	}
	            	else{
	            		if(textCount==20){
	            	i++;
	            	col++;
	            	textCount=0;
	            		}
	            	textCount++;
	            	}
	            	leafInfo();
	            	flower1Info();
	            	flower2Info();
	                drawFrame();
	            }
	        };
	public WallpaperEngine(Resources r) {
		 prefs = LiveWallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
         prefs.registerOnSharedPreferenceChangeListener(this);
         onSharedPreferenceChanged(prefs, null);
		WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
	           image=BitmapFactory.decodeResource(r,R.drawable.background);
	            dm=new DisplayMetrics();
	           wm.getDefaultDisplay().getMetrics(dm);
	 	       w1=dm.widthPixels;
	 	       h1=dm.heightPixels;
	 	      flower2=BitmapFactory.decodeResource(r,R.drawable.floewr2);
	            flower1=BitmapFactory.decodeResource(r,R.drawable.flower4);
	            leaf=BitmapFactory.decodeResource(r,R.drawable.leaf1);
	        }
    //创建引擎通过SurfaceHolder来描描绘壁纸在Canvas上
    @Override
    public void onCreate(SurfaceHolder surfaceHolder) {
        super.onCreate(surfaceHolder);
    }
    //销毁引擎
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(drawThread);
    }
    //变更Surface
    @Override
    public void onSurfaceChanged(SurfaceHolder holder,
        int format,int width,int height) {
        super.onSurfaceChanged(holder,format,width,height);
        this.width =width;
        this.height=height;
       // drawFrame();
    }
    //创建Surface
    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
        super.onSurfaceCreated(holder);
    }
    //销毁Surface
    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {
        super.onSurfaceDestroyed(holder);
        visible=false;
        handler.removeCallbacks(drawThread);
    }
    //变更可见/不可见状态
    @Override
    public void onVisibilityChanged(boolean visible) {
        this.visible=visible;
        if (visible) {
            drawFrame();
        } else {
            handler.removeCallbacks(drawThread);
        }
    }
    //变更壁纸位置
    @Override
    public void onOffsetsChanged(float xOffset,float yOffset,
        float xStep,float yStep,int xPixels,int yPixels) {
       // drawFrame();
    }
    //Frame描绘
    private void drawFrame() {
    	//加锁画布
        SurfaceHolder holder=getSurfaceHolder();
        Canvas c=holder.lockCanvas();
        //描绘
       
	//	paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setTextSize(16);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));


	/*	 Context context = getBaseContext();
		  // 字体
		  Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/ss.ttf");
		  paint.setTypeface(typeface); // 设置Paint的字体
		  paint.setAntiAlias(true);*/

			image2=Bitmap.createScaledBitmap(image,w1,h1, false);
        c.drawBitmap(image2, 0,0, null);
        drawText1 text=new drawText1(i,w1,h1);
        Paint p1=new Paint();
        p1.setTextSize(16);
        p1.setColor(Color.BLACK);
        p1.setStrokeWidth(4);
		p1.setAntiAlias(true);
		p1.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        drawPast(c,p1);
        drawImage(c);
        paint.setColor(Color.argb(51/4*textCount, 0, 0, 0));
        c.drawText(text.getText(),text.getX(),text.getY(), paint);
    if(textCount==19)
        list.add(new info(text.getText(),text.getX(),text.getY()));
        //解锁画布
        holder.unlockCanvasAndPost(c);
        //再描
        handler.removeCallbacks(drawThread);
        if (visible) handler.postDelayed(drawThread,10);
    }
    public void drawPast(Canvas c,Paint p){
    	if(list.size()>0){
    	for(int j=0;j<list.size();j++){
    		info in=list.get(j);
    		c.drawText(in.getText(), in.getRealwidth(), in.getRealheight(), p);
    	}
    	}
    }
    public void drawImage(Canvas c){
    	if(leaf1list.size()>0){
    	for(int a=0;a<leaf1Count;a++){
    		leafInfo leaf1=leaf1list.get(a);
    		c.drawBitmap(leaf, leaf1.getWidth(),3*leaf1.getHeight(),null);
    	}
    	}
    	if(flower1list.size()>0){
        	for(int a=0;a<flower1Count;a++){
        		leafInfo leaf1=flower1list.get(a);
        		c.drawBitmap(flower1, leaf1.getWidth(),3*leaf1.getHeight(),null);
        	}
        	}
    	if(flower2list.size()>0){
        	for(int a=0;a<flower2Count;a++){
        		leafInfo leaf1=flower2list.get(a);
        		c.drawBitmap(flower2, leaf1.getWidth(),3*leaf1.getHeight(),null);
        	}
        	}
    }
    
    public void leafInfo(){
    	if(leaf1list.size()>0){
    		for(int leaf1=0;leaf1<leaf1Count;leaf1++){
           leafInfo l1= leaf1list.get(leaf1);
         int leaf1temp=l1.getHeight();
         leaf1temp++;
         if(3*leaf1temp>h1){
        	 l1.setHeight(0);
        	 l1.setWidth(new Random().nextFloat()*w1);
         }
         else{
         l1.setHeight(leaf1temp);
         }
         leaf1list.set(leaf1, l1);
            		}
    	}
    	else{
    		for(int leaf1=0;leaf1<leaf1Count;leaf1++){
    	leafInfo l1=new leafInfo(new Random().nextFloat()*w1,new Random().nextInt(200));
    	leaf1list.add(l1);
    		}
    	}
    }
    
    public void flower1Info(){
    	if(flower1list.size()>0){
    		for(int flower1=0;flower1<flower1Count;flower1++){
           leafInfo f1= flower1list.get(flower1);
         int leaf1temp=f1.getHeight();
         leaf1temp++;
         if(3*leaf1temp>h1){
        	 f1.setHeight(0);
        	 f1.setWidth(new Random().nextFloat()*w1);
         }
         else{
         f1.setHeight(leaf1temp);
         }
         flower1list.set(flower1, f1);
            		}
    	}
    	else{
    		for(int leaf1=0;leaf1<flower1Count;leaf1++){
    	leafInfo l1=new leafInfo(new Random().nextFloat()*w1,new Random().nextInt(200));
    	flower1list.add(l1);
    		}
    	}
    }
    
    public void flower2Info(){
    	if(flower2list.size()>0){
    		for(int flower1=0;flower1<flower2Count;flower1++){
           leafInfo f1= flower2list.get(flower1);
         int leaf1temp=f1.getHeight();
         leaf1temp++;
         if(3*leaf1temp>h1){
        	 f1.setHeight(0);
        	 f1.setWidth(new Random().nextFloat()*w1);
         }
         else{
         f1.setHeight(leaf1temp);
         }
         flower2list.set(flower1, f1);
            		}
    	}
    	else{
    		for(int leaf1=0;leaf1<flower2Count;leaf1++){
    	leafInfo l1=new leafInfo(new Random().nextFloat()*w1,new Random().nextInt(200));
    	flower2list.add(l1);
    		}
    	}
    }
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String key) {
		// TODO Auto-generated method stub
		if(key!=null){
		if(key.equals("leaf1Count")){
		leaf1Count = Integer.parseInt(prefs.getString(getResources().getString(R.string.leaf1Count), "1"));
		leaf1list.clear();
		}
		if(key.equals("flower1Count")){
			flower1Count = Integer.parseInt(prefs.getString(getResources().getString(R.string.flower1Count), "2"));
			flower1list.clear();
			}
		if(key.equals("flower2Count")){
			flower2Count = Integer.parseInt(prefs.getString(getResources().getString(R.string.flower2Count), "2"));
			flower2list.clear();
			}
		if(key.equals("inputText")){
			loveText = prefs.getString(getResources().getString(R.string.inputText),loveText);
			loveText=loveText+"   ";      //在末尾加几个空格的目的就是文字全部显示后，让它停顿几秒，再重头开始写文字
			i=0;
    		row=0;
    		col=0;
    		list.clear();
			}
		if(key.equals("wordCount")){
			wordCount = Integer.parseInt(prefs.getString(getResources().getString(R.string.wordCount), "9"));
			i=0;                                       //如果字符数被改变，初始化各项变量，重新开始绘制字符
    		row=0;
    		col=0;
    		list.clear();
			}
		if(key.equals("reset")){
				loveText="冰雪林中著此身，不同桃李混芳尘  忽然一夜清香发，散作乾坤万里春 ";
				 wordCount=9;
				 i=0;                                       //如果恢复默认设置，初始化各项变量，重新开始绘制字符
		    	row=0;
		    	col=0;
		    	list.clear();
		    	
			}
		}
	}
}
}
