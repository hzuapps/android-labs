package com.hewq.linkgame2.view;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Point;

/**
 * ��������Ϣ
 */
public class Liner {
	private List<Point> pointList=new LinkedList<Point>();
	
	public int addPoint(Point point){
		pointList.add(point);
		return pointList.size();
	}
	
	public boolean needLink(){
		return pointList.size()>0?true:false;
	}
	
	public void clearPoint(){
		if(needLink()){
			pointList.clear();
		}
	}
	
	public List<Point> getPoints(){
		return pointList;
	}
	
	
	
}
