package com.example.intelligent_restranant_boss.toDo.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.intelligent_restranant_boss.toDo.Single_Note_Object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linco_325 on 2016/4/11.
 */
public class Notes_DataBase_OP  {
    //数据库公共常量
    final static String DB_NAME="LincoData";
    final static String TABLE_NAME="Note_Save_Table";
    //属性
    final static String TABLE_ITEM_1="no_";
    final static String TABLE_ITEM_2="title";
    final static String TABLE_ITEM_3="content";
    final static String TABLE_ITEM_4="time";
    final static String TABLE_ITEM_5="long_time";


    //负责对数据库的操作,包括数据库的增删该查,同时进行对应用与数据库之间数据格式的转化
    Context context;
    //数据库对象
    SQLiteDatabase sqlDB;
    SQLite_Open_Helper sql_Helper;
    //实例化时创建数据库
    public Notes_DataBase_OP(Context context){
        this.context=context;
        sql_Helper=new SQLite_Open_Helper(context);
    }

    //增
    public boolean insert(String title,@Nullable String content,@Nullable String time,@Nullable long long_time){
        //连接数据库
        sqlDB=sql_Helper.getReadableDatabase();
        //执行语句,用单引号包裹字符串,不能忘加
        try{
            sqlDB.execSQL("insert into "+TABLE_NAME+"("+TABLE_ITEM_2+","+TABLE_ITEM_3+","+TABLE_ITEM_4+","+TABLE_ITEM_5+")values('"+title+"','"+content+"','"+time+"',"+long_time+")");
        }catch (SQLException e){
            sqlDB.close();
            return false;
        }
        sqlDB.close();
        return true;
    }

    //删
    public void delete(int no_){
        sqlDB=sql_Helper.getReadableDatabase();
        sqlDB.execSQL("delete  from "+TABLE_NAME+" where "+TABLE_ITEM_1+"="+no_);
        Log.d("delete: ","delete  from "+TABLE_NAME+" where "+TABLE_ITEM_1+"="+no_);
        sqlDB.close();
    }

    //改
    public boolean update(int no_,String[] values,long long_time){
        //传入字符串数组,依次为title,content,time
        String[] keys={TABLE_ITEM_2,TABLE_ITEM_3,TABLE_ITEM_4};
        sqlDB=sql_Helper.getReadableDatabase();
        //记录要修改的属性
        String key_value_str="";
        //基本的校验,虽然不会出现
        if (keys.length!=values.length)
            return false;
        //封装,过程加单引号
        for (int i=0;i<keys.length;i++)
            key_value_str+=(keys[i]+"='"+values[i]+"',");
        key_value_str+=(TABLE_ITEM_5+"= "+long_time);
        try{
            //数据库自动编号从1开始,故索引加一
            sqlDB.execSQL("update " + TABLE_NAME + " set " + key_value_str + " where " + TABLE_ITEM_1 + " = " + no_);
        }catch (SQLException e){
            return false;
        }
        sqlDB.close();
        return true;
    }

    //查 封装获取到的数据并返回
    //因为所需返回的数据是固定格式的(除内容项,性能考虑),所以在这里先封装,但数据库本身的过滤项功能就无法使用了
    public List<Single_Note_Object> select(String addition_str){
        List<Single_Note_Object> note_objects=new ArrayList<>();
        sqlDB=sql_Helper.getReadableDatabase();
        //获取数据
        Cursor cursor=sqlDB.rawQuery("select "+TABLE_ITEM_1+","+TABLE_ITEM_2+","+TABLE_ITEM_4+" from "+TABLE_NAME+" "+addition_str,null);
        //解析到List
        cursor.moveToFirst();
        //遍历cursor,加载数据
        while(!cursor.isAfterLast()){
            //单行,按列索引按类型读取
            int no_=cursor.getInt(cursor.getColumnIndex(TABLE_ITEM_1));
            String title=cursor.getString(cursor.getColumnIndex(TABLE_ITEM_2));
            String time=cursor.getString(cursor.getColumnIndex(TABLE_ITEM_4));
            Single_Note_Object single_note=new Single_Note_Object(no_, title, time);
            note_objects.add(single_note);
            cursor.moveToNext();
        }
        sqlDB.close();
        //可能需要调整次序,后面再看
        return note_objects;
    }

    //根据no_取content
    public String getContent(int no_){
        sqlDB=sql_Helper.getReadableDatabase();
        Cursor cursor=sqlDB.rawQuery("select "+TABLE_ITEM_3+" from "+TABLE_NAME+" where "+TABLE_ITEM_1+" = "+no_,null);
        cursor.moveToFirst();
        String content_str=cursor.getString(cursor.getColumnIndex(TABLE_ITEM_3));
        sqlDB.close();
        return content_str;
    }
    //根据no_取long_time
    public long getLong_time(int no_){
        sqlDB=sql_Helper.getReadableDatabase();
        Cursor cursor=sqlDB.rawQuery("select "+TABLE_ITEM_5+" from "+TABLE_NAME+" where "+TABLE_ITEM_1+" = "+no_,null);
        cursor.moveToFirst();
        long long_time=cursor.getLong(cursor.getColumnIndex(TABLE_ITEM_5));
        sqlDB.close();
        return long_time;
    }

    //将long_time设为0 sql执行报错,存疑,
    /*public boolean close_alarm(int no_){
        try{
            //数据库自动编号从1开始,故索引加一
            Log.d("close_alarm: ","update " + TABLE_NAME + " set " + TABLE_ITEM_5 + " = 0 where " + TABLE_ITEM_1 + " = " + no_);
            sqlDB.execSQL("update " + TABLE_NAME + " set " + TABLE_ITEM_5 + " = 0 where " + TABLE_ITEM_1 + " = " + no_);
        }catch (SQLException e){
            return false;
        }
        *//*catch (Exception e){
            return false;
        }*//*
        return true;
    }*/
}
