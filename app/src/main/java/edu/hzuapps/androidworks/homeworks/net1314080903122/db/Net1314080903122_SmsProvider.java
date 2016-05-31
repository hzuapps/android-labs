package edu.hzuapps.androidworks.homeworks.net1314080903122.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_SendedMsg;

/**
 * Created by Administrator on 2016/5/29.
 */
public class Net1314080903122_SmsProvider extends ContentProvider {

    private static final String AUTHORITY = "edu.hzuapps.androidworks.homeworks.sms.provider.SmsProvider";
    public static final Uri URI_SMS_ALL = Uri.parse("content://"+AUTHORITY+"/sms");

    private static UriMatcher matcher;

    private static final int SMS_ALL = 0;
    private static final int SMS_ONE = 1;

    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY,"sms",SMS_ALL);
        matcher.addURI(AUTHORITY,"sms/#",SMS_ONE);
    }

    private Net1314080903122_SmsDbOpenHelper mHelper;
    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {
        mHelper = Net1314080903122_SmsDbOpenHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match =matcher.match(uri);
        switch(match)
        {
            case SMS_ALL:
                break;
            case SMS_ONE:
                long id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(id)};
                break;

            default:
                throw new IllegalArgumentException("Wrong URI ; " + uri);
        }
        mDb = mHelper.getReadableDatabase();
        Cursor c = mDb.query(Net1314080903122_SendedMsg.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(),URI_SMS_ALL);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = matcher.match(uri);
        if (match != SMS_ALL )
        {
            throw new IllegalArgumentException("Wrong URI ; " + uri);
        }
        mDb = mHelper.getWritableDatabase();
        long rowID = mDb.insert(Net1314080903122_SendedMsg.TABLE_NAME,null,values);
        if (rowID > 0)
        {
            notifyDataSetChanged();
            return ContentUris.withAppendedId(uri,rowID);
        }
        return null;
    }

    private void notifyDataSetChanged() {
        getContext().getContentResolver().notifyChange(URI_SMS_ALL,null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
