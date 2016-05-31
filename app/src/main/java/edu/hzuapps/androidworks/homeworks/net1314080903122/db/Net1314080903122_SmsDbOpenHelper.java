package edu.hzuapps.androidworks.homeworks.net1314080903122.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_SendedMsg;

/**
 * Created by Administrator on 2016/5/29.
 */
public class Net1314080903122_SmsDbOpenHelper extends SQLiteOpenHelper{
    private static final String DB_NAME ="sms.db";
    private static final int DB_VERSION =1;


    public Net1314080903122_SmsDbOpenHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    private static Net1314080903122_SmsDbOpenHelper mHelper;
    public static  Net1314080903122_SmsDbOpenHelper getInstance(Context context)
    {
        if (mHelper == null)
        {
            synchronized (Net1314080903122_SmsDbOpenHelper.class)
            {
                if (mHelper == null)
                {
                    mHelper = new Net1314080903122_SmsDbOpenHelper(context);
                }
            }
        }
        return mHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Net1314080903122_SendedMsg.TABLE_NAME +" ( "+
                "_id integer primary key autoincrement ,"+
                Net1314080903122_SendedMsg.COLUMN_DATE+" integer ,"+
                Net1314080903122_SendedMsg.COLUMN_FES_NAME +" text ,"+
                Net1314080903122_SendedMsg.COLUMN_MSG +" text ,"+
                Net1314080903122_SendedMsg.COLUMN_NAMES +" text ,"+
                Net1314080903122_SendedMsg.COLUMN_NUMBER +" text "+
                " ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
