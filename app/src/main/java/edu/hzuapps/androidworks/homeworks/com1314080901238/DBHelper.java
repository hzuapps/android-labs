package com.example.yaoyiyao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/15.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context) {

        super(context, "cj", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(userID  INTEGER primary key autoincrement,date text not null)";
        db.execSQL(sql);
        String sql1 = "insert into user (date) values ('詹姆斯')";
        String sql2 = "insert into user (date) values ('犀牛')";
        String sql3 = "insert into user (date) values ('大象')";
        String sql4 = "insert into user (date) values ('钓鱼')";
        String sql5 = "insert into user (date) values ('鹤立鸡群')";
        String sql6 = "insert into user (date) values ('天人合一')";
        String sql7 = "insert into user (date) values ('考试')";
        String sql8 = "insert into user (date) values ('驾驶')";
        String sql9 = "insert into user (date) values ('飞机')";
        String sql10 = "insert into user (date) values ('刘翔')";
        String sql11 = "insert into user (date) values ('马马虎虎')";
        String sql12 = "insert into user (date) values ('厕所')";
        String sql13 = "insert into user (date) values ('爱情')";
        String sql14 = "insert into user (date) values ('维纳斯')";
        String sql15 = "insert into user (date) values ('佛祖')";
        String sql16 = "insert into user (date) values ('孙悟空')";
        String sql17 = "insert into user (date) values ('猪八戒')";
        String sql18 = "insert into user (date) values ('大哥')";
        String sql19 = "insert into user (date) values ('马超荣')";

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
        db.execSQL(sql7);
        db.execSQL(sql8);
        db.execSQL(sql9);
        db.execSQL(sql10);
        db.execSQL(sql11);
        db.execSQL(sql12);
        db.execSQL(sql13);
        db.execSQL(sql14);
        db.execSQL(sql15);
        db.execSQL(sql16);
        db.execSQL(sql17);
        db.execSQL(sql18);
        db.execSQL(sql19);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
