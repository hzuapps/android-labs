package com.example.lwj_pc.my_classwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by LWJ-PC on 2016/5/10.
 */
public class Com1314080901221_MyDBHelper extends SQLiteOpenHelper {
    //定义一个Context变量，用于保存活动调用本参数时传入的Context，并方便本类的其他方法使用。
    private Context mycontext;
    //此处书写数据库建表语句
    public static final String CREATE_TABLE="create table journey ("
            +"title text,"
            +"content text,"
            +"time text）";
    //构造函数
    public Com1314080901221_MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mycontext=context;
    }
    //创建时执行，当检查到数据库不存在时，执行onCreate进行创建。
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mycontext, "建表成功", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
