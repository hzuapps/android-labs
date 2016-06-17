package single_thread_download.skyward.com.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import single_thread_download.skyward.com.bean.ThreadInfo;

/**
 * 数据库访问接口的实现
 * Created by skyward on 2016/6/15.
 */
public class ThreadDaoImpl implements ThreadDao {
    private DBHelper mHelper = null;

    public ThreadDaoImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        if (mHelper == null) {
            Log.i("jkf", "insertThread: " + "坑爹的空指针");
            return;
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)", new Object[]{
                threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()
        });
        db.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?", new Object[]{url, thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?", new Object[]{finished, url, thread_id});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<ThreadInfo> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while(cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(url);
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, thread_id + ""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return false;
    }
}
