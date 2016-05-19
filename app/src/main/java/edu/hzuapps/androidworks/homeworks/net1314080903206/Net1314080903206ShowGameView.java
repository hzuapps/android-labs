package edu.hzuapps.androidworks.homeworks.net134080903206;

import java.util.ArrayList;
import java.util.List;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Net1314080903206ShowGameView extends GridLayout  {
	
	protected static final int C = 0;
	private Net1314080903206Blocks[][] blocksMap = new Net1314080903206Blocks[5][5];
	private List<Point> emptyPoints = new ArrayList<Point>();	
	private Point p;		
	private int blockWidth;	
	private int i=0,j=0,n,m=0;
	private boolean complete=false,check;
	private boolean frist=false;
	private boolean start=true;

	


	public Net1314080903206ShowGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		initGameView();
		
	}

	
	
	private void initGameView() {
		setColumnCount(5);
		setBackgroundColor(Color.GRAY);
		
		setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(start)
				{
					
					Net1314080903206StepOnWhiteActivity.getSOWActivity().showStart();	
					Net1314080903206StepOnWhiteActivity.getSOWActivity().TimeStart();
				}
				start=false;
				changeColor();

				
			}
		});
		
				
	}
	
	
	
	private void changeColor()
	{
		
		
		for (int x = 0; x < 5; x++) 
		{
			for (int y = 0; y < 5; y++) 
			{
				
					final Net1314080903206Blocks bl;
					bl=blocksMap[x][y];
					bl.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							checkComplete();
							if(complete)
								gameOver();
							else
							{
								if(bl.label.getTextSize()>=100)
								{
							
									bl.setColor(Color.BLACK);
									bl.label.setTextSize(1);
									Net1314080903206StepOnWhiteActivity.getSOWActivity().addScore(1);
								
									n=0;
								
									int k;
									k=(int)(Math.random()*2+1);
									for (int x = 0; x < 5; x++) 
									{
										for (int y = 0; y < 5; y++) 
										{
											if(blocksMap[x][y].label.getTextSize()<=100)
												n++;
											else
												m++;
										}
									}
									if(k>n)
										k=n;
									if(m>0)
										check=false;
									else check=true;
									if(check)
									{
										while(k>=0)
										{
											addRandomColor();
											k--;
										}
									}
									while(m>0)
									{
										m--;
									}
									i=1;
								}
								else Net1314080903206StepOnWhiteActivity.getSOWActivity().addScore(-5);											
							}
						}
					});	
				
				if(i==1)
					break;
			}
			if(i==1)
				break;
		}
					
	}
	
		
	
	@Override  
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{
		super.onSizeChanged(w, h, oldw, oldh);
			
		blockWidth = (Math.min(w, h)-10)/5;		
		addBlocks(blockWidth,blockWidth);
		
		startGame();
		
	}

	
	private void addBlocks(int blockWidth,int blockHeight)
	{
		Net1314080903206Blocks b;
		
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				
				b = new Net1314080903206Blocks(getContext());
				addView(b, blockWidth, blockHeight);
				
				blocksMap[x][y] = b;
			}
		}

	}

	private void startGame()
	{
		
		Net1314080903206StepOnWhiteActivity sowa = Net1314080903206StepOnWhiteActivity.getSOWActivity();
		sowa.showBestScore(sowa.getBestScore());
		sowa.clearScore();
	
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				blocksMap[x][y].setColor(Color.BLACK);
				blocksMap[x][y].label.setTextSize(10);
			}
		}
		complete=false;
		Net1314080903206StepOnWhiteActivity.getSOWActivity().setTime();
		if(frist)		
			Net1314080903206StepOnWhiteActivity.getSOWActivity().TimeStart();								
		frist=true;
		
		addRandomColor();
		Log.d("SowGameView",Net1314080903206StepOnWhiteActivity.getSOWActivity().t+"");
	}

	private void addRandomColor() 
	{
	
		emptyPoints.clear();
		
		if(complete==false)
		{
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {					
					emptyPoints.add(new Point(x, y));								
				}
			}
		
			p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
			if(blocksMap[p.x][p.y].label.getTextSize()>=100)
				addRandomColor();
			blocksMap[p.x][p.y].setColor(Color.WHITE);	
			blocksMap[p.x][p.y].label.setTextSize(200);
		}

	}
	
	
	
	private void checkComplete(){
		
		complete = false;
		
		j=Net1314080903206StepOnWhiteActivity.getSOWActivity().getTime();
		if(j<=0)
			complete=true;
	}
	
	private void gameOver()
	{
		String s="你的分数是："+Net1314080903206StepOnWhiteActivity.getSOWActivity().getScore();
		new AlertDialog.Builder(getContext()).setTitle("gameover").setMessage(s).setCancelable(false).setPositiveButton("重来", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startGame();
				}
			}).setNegativeButton("不玩了",new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Net1314080903206StepOnWhiteActivity.getSOWActivity().exitGame();
					
				}
				
			}).show();
	}
	
}








