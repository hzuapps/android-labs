package com1314080901144.util;

import com.example.com1314080901144.Board;

public class Coordinate {
	public final int x;
	public final int y;

	private Coordinate[] near;
	public static final int up = 0;
	public static final int down = 1;
	public static final int right = 2;
	public static final int left = 3;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private Coordinate up() {
		Coordinate c = new Coordinate(x, y - 1);
		return c.isValid() ? c : null;
	}

	private Coordinate down() {
		Coordinate c = new Coordinate(x, y + 1);
		return c.isValid() ? c : null;
	}

	private Coordinate right() {
		Coordinate c = new Coordinate(x + 1, y);
		return c.isValid() ? c : null;
	}

	private Coordinate left() {
		Coordinate c = new Coordinate(x - 1, y);
		return c.isValid() ? c : null;
	}

	private void initNear() {
		if (near == null) {
			near = new Coordinate[4];
			near[up] = up();
			near[down] = down();
			near[right] = right();
			near[left] = left();
		}
	}

	public Coordinate getNear(int direction) {
		initNear();
		return near[direction];
	}

	//------------------------------------------------------------------------
	
	public boolean isValid() {
		if (x < 0)
			return false;
		if (y < 0)
			return false;
		if (x > Board.n - 1)
			return false;
		if (y > Board.n - 1)
			return false;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
}
