package com1314080901144;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.example.com1314080901144.util.Coordinate;
import com.example.com1314080901144.util.Function;
import com.example.com1314080901144.util.Utils;

public class Board {

	public static int n = 19;
	public static final int None = 0;
	public static final int Black = 1;
	public static final int White = 2;

	//行棋记录
	private List<PieceProcess> list = new ArrayList<PieceProcess>();
	
	private Grid currentGrid = new Grid();//当前盘面
	
	private int expBw = Black;//轮到哪一方下
	private Function listener;

	//------------------------------------------------------------------
	
	public boolean put(int x, int y) {
		Coordinate c = new Coordinate(x, y);
		PieceProcess p=new PieceProcess(expBw,c);
		
		if (currentGrid.putPiece(p)) {
			if(!check(p)){
				currentGrid.executePieceProcess(p, true);
				return false;
			}
			
			list.add(p);
			finishedPut();
			return true;
		}
		return false;
	}
	
	private void finishedPut(){
		expBw = Utils.getReBW(expBw);
		postEnvet();
	}
	
	//打劫检测
	private boolean check(PieceProcess p){
		int i=0;
		for(PieceProcess pp:list){
			if(pp.resultBlackCount==p.resultBlackCount 
					&& pp.resultWhiteCount==p.resultWhiteCount){
				if(isOverEqualse(i,p)){
					return false;
				}
			}
			i++;
		}
		return true;
	}
	
	private boolean isOverEqualse(int position,PieceProcess p){
		Board sb=getSubBoard(position+1);
		return sb.currentGrid.equals(this.currentGrid);
	}
	
	//------------------------------------------------------------------rebuilt
	
	public SubBoard getSubBoard(int index){
		SubBoard board=new SubBoard(this);
		board.gotoIt(index);
		return board;
	}
	
	protected void cleanGrid(){
		this.currentGrid = new Grid();
	}
	
	protected void addPieceProcess(PieceProcess p) {
		currentGrid.executePieceProcess(p, false);
		list.add(p);
		finishedPut();
	}
	
	protected void removePieceProcess(){
		if(list.size()==0)return;
		PieceProcess p=list.remove(getCount()-1);
		currentGrid.executePieceProcess(p, true);
		finishedPut();
	}

	//------------------------------------------------------------------getter
	

	public int getValue(int x, int y) {
		return currentGrid.getValue(new Coordinate(x, y));
	}

	private void postEnvet() {
		if(listener==null)return;
		listener.apply(getCount(),expBw);
	}
	
	public void setListener(Function listener){
		this.listener=listener;
	}

	public Coordinate getLastPosition() {
		if(getCount()==0)return null;
		return list.get(getCount() - 1).c;
	}

	public int getCount() {
		return list.size();
	}
	
	public PieceProcess getPieceProcess(int i){
		if(i>=getCount())return null;
		return list.get(i);
	}
	
	//------------------------------------------------------------------status
	
	public Bundle saveState() {
        Bundle map = new Bundle();
        map.putInt("count", getCount());
        int i=0;
        for(PieceProcess p:list){
        	map.putInt("x"+i, p.c.x);
        	map.putInt("y"+i, p.c.y);
        	i++;
        }
        return map;
    }
	
	public void restoreState(Bundle map) {
		int n=map.getInt("count");
		for(int i=0;i<n;i++){
			int x=map.getInt("x"+i);
			int y=map.getInt("y"+i);
			
			this.put(x, y);
		}
    }
}
