package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_Net1314080903221 extends SQLiteOpenHelper
{
  private static final int VERSION = 1;
  private static String DBNAME="account.db";
  public DBHelper_Net1314080903221(Context paramContext)
  {
    this(paramContext, DBNAME, VERSION);
  }

  public DBHelper_Net1314080903221(Context paramContext, String paramString, int paramInt)
  {
    this(paramContext, paramString, null, paramInt);
  }

  public DBHelper_Net1314080903221(Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
  {
    super(paramContext, paramString, paramCursorFactory, paramInt);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("create table tb_outaccount(_id INTEGER PRIMARY KEY,pocketType varchar(20),addTime TEXT,money float,mark TEXT)");
    paramSQLiteDatabase.execSQL("create table tb_inaccount(_id INTEGER PRIMARY KEY,pocketType varchar(20),addTime TEXT,money float,mark TEXT)");
    paramSQLiteDatabase.execSQL("create table tb_note(_id INTEGER PRIMARY KEY,content TEXT,Remind int,addtime date,remindtime date)");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
  }
}