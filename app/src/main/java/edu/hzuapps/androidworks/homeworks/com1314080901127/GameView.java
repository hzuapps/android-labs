package edu.hzuapps.androidworks.homeworks.com1314080901127;


import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class GameView extends View{
	public int width;
	public int height;
	Bitmap[]baoshi=new Bitmap[7];
	Scroller scroll;
	int[]gameMatrix=new int[64];

	int danwei;

	private int controll=0;
	private int animation1=0;//选择的动画 1 左右交换 2 上下交换 3 消除后上方落下填补的水晶
	private boolean animation2=false;//消除的动画
	private boolean animation1Controll=false;//对回滚的控制位
	private float pointX;
	private float pointY;

	private int choose=100;//选择框
	//链表 存放要消除的水晶
	private int []xiaochu=new int[64];//要消除的数组
	private boolean []tianbu=new boolean[64];//每个水晶需要落下的格子数

	private int []huanChongGezi=new int[8];//缓冲水晶 8个
	public GameView(Context context,baoshi b) {
		super(context);
		// TODO Auto-generated constructor stub
		baoshi[0]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_1)).getBitmap();
		baoshi[1]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_2)).getBitmap();
		baoshi[2]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_3)).getBitmap();
		baoshi[3]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_4)).getBitmap();
		baoshi[4]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_5)).getBitmap();
		baoshi[5]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_6)).getBitmap();
		baoshi[6]=((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.com1314080901127_baoshi_7)).getBitmap();
		width=b.width;
		height=b.height;
		danwei=height/8;
		scroll=new Scroller(getContext());


		for(int i=0;i<=6;i++)
		{
			baoshi[i]=Bitmap.createScaledBitmap(baoshi[i], height/8, height/8, false);
		}
		gameMatrix=RandomClass.getInstance().getInt();
		huanChongGezi=RandomClass.getInstance().getInt();

		doMatch();
	}

	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		//交换效果1
		if(!animation2)
		{
			for(int i=0;i<64;i++)
			{
				if(animation1!=0&&controll==i)
					canvas.drawBitmap(baoshi[gameMatrix[i]], pointX,pointY, null);
				else if(animation1==1&&controll==i-1)
				{
					canvas.drawBitmap(baoshi[gameMatrix[i]], i%8*danwei-(pointX-(i-1)%8*danwei),i/8*danwei-(pointY-(i-1)/8*danwei), null);
				}
				else if(animation1==2&&controll==i-8)
				{
					canvas.drawBitmap(baoshi[gameMatrix[i]], i%8*danwei-(pointX-(i-8)%8*danwei),i/8*danwei-(pointY-(i-8)/8*danwei), null);
				}
				else
					canvas.drawBitmap(baoshi[gameMatrix[i]], i%8*danwei,i/8*danwei, null);
			}
		}
		//水晶填补效果2
		else
		{
			for(int i=63;i>=0;i--)
			{
				if(tianbu[i])
				{
					if((i/8-1)*8+i%8>=0)
					{
						tianbu[(i/8-1)*8+i%8]=true;
						if(gameMatrix[(i/8-1)*8+i%8]!=100)
							canvas.drawBitmap(baoshi[gameMatrix[(i/8-1)*8+i%8]], i%8*danwei,(i/8-1)*danwei+pointY, null);
					}
					else
					{
						canvas.drawBitmap(baoshi[huanChongGezi[i%8]], i%8*danwei,(i/8-1)*danwei+pointY, null);
					}
				}
				else
					canvas.drawBitmap(baoshi[gameMatrix[i]], i%8*danwei,i/8*danwei, null);
			}
		}
		//选择框
		if(choose>=0&&choose<64)
		{
			Paint paint=new Paint();
			paint.setColor(Color.WHITE);
			canvas.drawLine(choose%8*danwei, choose/8*danwei, (choose%8+1)*danwei, choose/8*danwei, paint);
			canvas.drawLine(choose%8*danwei, choose/8*danwei, choose%8*danwei, (choose/8+1)*danwei, paint);
			canvas.drawLine((choose%8+1)*danwei, choose/8*danwei, (choose%8+1)*danwei, (choose/8+1)*danwei, paint);
			canvas.drawLine(choose%8*danwei, (choose/8+1)*danwei, (choose%8+1)*danwei, (choose/8+1)*danwei, paint);
		}
	}

	public boolean doMatch()//判断逻辑
	{
		if(xiaochu[0]!=100)
			clearArray();
		int count=0;
		int arrayPoint=0;
		//横查询
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<6;)
			{
				while(j+1<8&&gameMatrix[i*8+j]==gameMatrix[i*8+j+1])
				{
					j++;
					count++;
				}
				if(count>=2)
				{
					for(;count>=0;count--)
					{
						xiaochu[arrayPoint]=i*8+j-count;
						arrayPoint++;
					}
				}
				count=0;
				j++;
			}
		}

		//竖查询
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<6;)
			{
				while(j+1<8&&gameMatrix[j*8+i]==gameMatrix[(j+1)*8+i])
				{
					j++;
					count++;
				}
				if(count>=2)
				{
					for(;count>=0;count--)
					{
						xiaochu[arrayPoint]=(j-count)*8+i;
						arrayPoint++;
					}
				}
				count=0;
				j++;
			}
		}

		serialArray();//数列化数组
		if(xiaochu[0]==100)
			return false;
		else
		{
			doRemove();
			return true;
		}
	}


	//这个是match之后的消除动画已经新的水晶的插入
	private void doRemove()
	{
		animation2=true;
		scroll.startScroll(0, 0, 0, danwei*8,8000);//向下滚一格
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		int x=(int) event.getX();
		int y=(int) event.getY();
		int tempChoose = 64;
		if(animation1==0&&!animation2)
		{
			if(x<danwei*8)
				tempChoose=x/danwei+y/danwei*8;
			if(Math.abs(tempChoose-choose)==1&&tempChoose/8==choose/8)
			{
				controll=tempChoose>choose?choose:tempChoose;

				animation1=1;
				animation1Controll=false;
				scroll.startScroll(controll%8*danwei, controll/8*danwei, danwei, 0);
				choose=100;
			}
			else if(Math.abs(tempChoose/8-choose/8)==1&&tempChoose%8==choose%8)
			{
				controll=tempChoose>choose?choose:tempChoose;

				animation1=2;
				animation1Controll=false;
				scroll.startScroll(controll%8*danwei, controll/8*danwei, 0, danwei);
				choose=100;
			}
			else
			{
				choose=tempChoose;
			}

		}
		super.onTouchEvent(event);
		return false;
	}

	public void computeScroll()
	{

		super.computeScroll();
		if (scroll.computeScrollOffset())
		{
			if(animation2)
			{
				pointX = scroll.getCurrX()%danwei;
				pointY = scroll.getCurrY()%danwei;
				if(doSth())
				{
					animation2=true;
				}
				else
				{
					scroll.abortAnimation();
				}
			}
			else
			{
				pointX = scroll.getCurrX();
				pointY = scroll.getCurrY();
			}
			postInvalidate();
		}
		else
		{
			if(animation2)
			{
				if(doSth())
				{
					animation2=true;
					scroll.startScroll(0, 0, 0, danwei*8,8000);//向下滚一格
				}
				else
				{
					animation2=false;
					doMatch();
				}
			}
			else if(controll>=0&&animation1!=0)
			{
				if(animation1==1)
				{
					int temp;
					temp=gameMatrix[controll+1];
					gameMatrix[controll+1]=gameMatrix[controll];
					gameMatrix[controll]=temp;
				}
				else if(animation1==2)
				{
					int temp;
					temp=gameMatrix[controll+8];
					gameMatrix[controll+8]=gameMatrix[controll];
					gameMatrix[controll]=temp;
				}

				if(!doMatch()&&!animation1Controll)//没有匹配的就回滚
				{
					if(animation1==1)
						scroll.startScroll(controll%8*danwei, controll/8*danwei, danwei, 0);
					else if(animation1==2)
						scroll.startScroll(controll%8*danwei, controll/8*danwei, 0, danwei);
					animation1Controll=true;
				}
				else
				{
					animation1=0;
					controll=-1;
				}
			}
			postInvalidate();
		}
	}

	private void clearArray()//清除数组
	{
		for(int i=0;i<64;i++)
		{
			xiaochu[i]=100;
			tianbu[i]=false;
		}
	}

	private void serialArray()//序列化数组
	{
		Arrays.sort(xiaochu);
		for(int i=0;i<63;i++)
		{
			if(xiaochu[i]==xiaochu[i+1]&&xiaochu[i]!=100)
			{
				xiaochu[i]=100;
			}
		}
		Arrays.sort(xiaochu);
		for(int i=0;xiaochu[i]!=100;i++)
		{
			tianbu[xiaochu[i]]=true;
			gameMatrix[xiaochu[i]]=100;
		}
	}

	private boolean doSth()
	{
		for(int i=63;i>=0;i--)
		{
			if(tianbu[i])
			{
				if((i/8-1)*8+i%8>=0)
				{
					gameMatrix[i]=gameMatrix[(i/8-1)*8+i%8];
				}
				else
				{
					gameMatrix[i]=huanChongGezi[i%8];
				}
			}
		}
		huanChongGezi=RandomClass.getInstance().getEight();
		return clearTianbu();
	}

	private boolean clearTianbu()
	{
		boolean a=false;
		for(int i=0;i<=63;i++)
		{
			if(gameMatrix[i]==100)
			{
				a=true;
				tianbu[i]=true;
			}
			else
				tianbu[i]=false;
		}
		return a;
	}
}
