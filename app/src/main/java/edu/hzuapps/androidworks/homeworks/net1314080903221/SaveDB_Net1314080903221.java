package com.example.account;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.util.Log;

public class SaveDB_Net1314080903221 {
	private static String urlNull = "原文件路径不存在";
	private static String isFile = "原文件不是文件";
	private static String canRead = "原文件不能读";
	//private static String notWrite = "备份文件不能写入";
	private static String message = "备份成功";
	private static String cFromFile = "创建原文件出错:";
	private static String ctoFile = "创建备份文件出错:";
	//数据库原文件地址
	private static String fromFileUrl="/sdcard/data/data/com.example.account/databases/account.db";
	//数据库备份地址
	private static String toFileUrl="";
	 /**
	  * 
	 
	  * @return 返回备份文件的信息，ok是成功，其它就是错误
	  */
	 public static String Save( ) {
	  File fromFile = null;
	  File toFile = null;
	  
	  
	  if (Environment.getExternalStorageState().equals((Environment.MEDIA_MOUNTED))){
          File sdCardDir=Environment.getExternalStorageDirectory();
          toFileUrl=sdCardDir.getPath()+"//Zachay-";
      }else{
    	  return "没有sd卡";
      }
	  //读取源文件
	  try {
		  fromFile = new File(fromFileUrl);
	  } catch (Exception e) {
		  return cFromFile + e.getMessage();
	  }
	  //创建备份文件
	  try {
	   toFile = new File(getToFileUrl());
	  } catch (Exception e) {
	   return ctoFile + e.getMessage();
	  }

	  if (!fromFile.exists()) {
	   return urlNull;
	  }
	  if (!fromFile.isFile()) {
	    return isFile;
	  }
	  if (!fromFile.canRead()) {
	    return canRead;
	  }

	  //复制到的路径如果不存在就创建
	  if (!toFile.getParentFile().exists()) {
	   toFile.getParentFile().mkdirs();
	  }
	         //已存在就删除
	  if (toFile.exists()) {
	   toFile.delete();
	  }
	         //不能写入
	  if (!toFile.canWrite()) {
	   // return notWrite;
	  }
	  try {
	   java.io.FileInputStream fosfrom = new java.io.FileInputStream(
	     fromFile);
	   java.io.FileOutputStream fosto = new FileOutputStream(toFile);
	   byte bt[] = new byte[1024];
	   int c;

	   while ((c = fosfrom.read(bt)) > 0) {
	    fosto.write(bt, 0, c); // 将内容写到新文件当中
	   }

	   fosfrom.close();
	   fosto.close();

	  } catch (Exception e) {
		  Log.i("Zachay",e.getMessage());  
		  message = "备份失败!"+e.toString();
	  }
	  return message;

	 }
	 
	 
	 
	 
	 /**
	  * 
	  * @return获得备份数据库新地址ַ
	  */
	 private static String getToFileUrl(){  
	  StringBuffer sb=new StringBuffer(); 
	        sb.append(toFileUrl); 
	        sb.append(getTime());    
	        sb.append(".db");    
	        return sb.toString();  
	 }
	 
	  /**
	  * 
	  * @return 获取当前时间 2016-04-2213:56:02
	  */
	 private static String getTime() {
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String nowTime = sdf.format(new java.util.Date());   
	  return nowTime.replaceAll(" ","");
	 }

}
