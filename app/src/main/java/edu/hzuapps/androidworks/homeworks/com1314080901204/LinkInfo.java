package com.example.com1314080901204.object;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

/**
 * 该类用于封装两个方块之间的连接信息，两个方块可以通过一条线直接相连，通过两个线相连，通过三条线相连，因此需要保存用于连接线的点的信息。
 * 
 * @author CJP
 * 
 */
public class LinkInfo {

	// 创建一个集合用于保存连接点
	private List<Point> points = new ArrayList<Point>();

	// 提供第一个构造器，表示两个Point可以直接相连，没有转折点
	public LinkInfo(Point p1, Point p2) {
		points.add(p1);
		points.add(p2);
	}

	// 提供第二个构造器，表示三个Point可以相连，p2是p1与p3之间的转折点
	public LinkInfo(Point p1, Point p2, Point p3) {
		points.add(p1);
		points.add(p2);
		points.add(p3);
	}

	// 提供第三个构造器，表示四个Point可以相连，p2，p3是p1与p4的转折点
	public LinkInfo(Point p1, Point p2, Point p3, Point p4) {
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
	}

	// 返回连接集合
	public List<Point> getLinkPoints() {
		return points;
	}
}
