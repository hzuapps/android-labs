package edu.hzuapps.androidworks.homeworks.com1314080901127;


import java.util.Random;

public class RandomClass {
	Random r;
	private static RandomClass random;
	private RandomClass()
	{
		r=new Random();
	}
	public static RandomClass getInstance()
	{
		if(random==null)
			random=new RandomClass();
		return random;
	}
	public int[] getInt()//产生一个 8x8的随机数组 要求数组行数不能有三个是一样的
	{
		int[]temp=new int[64];
		for(int i=0;i<=63;i++)
		{
			temp[i]=Math.abs(r.nextInt())%7;//0-6的数
		}
		return temp;
	}

	public int[] getEight()
	{
		int []temp=new int[8];
		for(int i=0;i<=7;i++)
		{
			temp[i]=Math.abs(r.nextInt())%7;//0-6的数
		}
		return temp;
	}

}
