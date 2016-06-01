package edu.hzuapps.androidworks.homeworks.com1314080901123;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LK on 2016/5/19.
 */
public class Com1314080901123_DatabaseAdapter extends SQLiteOpenHelper {

    /**
     * 创建表格的语句
     */
    public static final String CREATE_RECORD = "create table record (buystyle text,money text,buydate text);";

    public Com1314080901123_DatabaseAdapter(Context context, String name,
                                            CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表格
        db.execSQL(CREATE_RECORD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
