package com1314080901144;

import java.util.ArrayList;
import java.util.List;

import com.example.com1314080901144.util.Coordinate;
import com.example.com1314080901144.util.Function;

/**
 * 棋块
 *
 */
public class Block {
	private List<Coordinate> block=new ArrayList<Coordinate>();
	private int airCount=0;//气数
	private int bw;//颜色
	
	public Block(int bw){
		this.bw=bw;
	}
	
	public int getBw(){
		return bw;
	}
	
	public void add(Coordinate c){
		block.add(c);
	}
	
	public void addAir(int air){
		airCount+=air;
	}
	
	public boolean isLive(){
		if(airCount>0 && block.size()>0)return true;
		return false;
	}
	
	public void each(Function f){
		for(Coordinate c:block){
			f.apply(c);
		}
	}
}
