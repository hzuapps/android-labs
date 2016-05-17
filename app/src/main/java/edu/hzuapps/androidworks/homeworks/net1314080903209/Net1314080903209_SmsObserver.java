package edu.hzuapps.androiworks.homeworks.net1314080903209.myapplication;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Silent_Ice on 2016/4/1.
 */
public class Net1314080903209_SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    public Net1314080903209_SmsObserver(Context context, Handler handler){
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.e("DEBUG", "SMS has changed!");
        Log.e("DEBUG",uri.toString());

        String code = " ";

        //第一次调用短信时，不做任何操作
        if (uri.toString().equals("content://sms/raw")){
            return;
        }

        //创建一个指向短信收件箱的URI
        Uri inboxUri = Uri.parse("content://sms//inbox");
        //使用cursor对象进行一次query的操作，
        Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (c != null){
            if (c.moveToFirst()){
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));

                //接收并提取来自号码为18316736360的手机发来的验证码。如果收到的是其他号码发来的，则不做任何操作
                if(!address.equals("18316736360")){
                    return;
                }

                Log.e("DEBUG", "发件人为：" + address + " " + "短信内容为：" + body);

                //提取短信验证码中六个连续的数字
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()){
                    code = matcher.group(0);
                    Log.e("DEBUG","code is " + code);

                    mHandler.obtainMessage(Net1314080903209_MainActivity.MSG_RECEIVES_CODE, code).sendToTarget();
                }
            }
            c.close();
        }
    }
}
