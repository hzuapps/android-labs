package edu.hzuapps.androidworks.homeworks;

import java.util.List;



public interface Net1314080903235NumberGuess {

	public abstract int getGuessTimeLeft();//返回剩余猜测次数
	
	public abstract String getNewNumber();//返回待猜数字
	
	public abstract int getGuessTime();//返回可猜测总次数
	
	public abstract int getGuessedTime();//返回已猜测次数

	public abstract List getResults();//返回猜测结果集
	
	
	/**
	 * 
	 * @param number
	 * @return 0，1，2三个数值。
	 *          0表示游戏结束；
	 *          1表示猜测成功；
	 *          2表示本次猜测失败，可继续猜测
	 */
	public abstract int NumberCompare(String number);

	public abstract boolean isNumberValid(String number);//判断输入数字是否合法（不能重复）

}