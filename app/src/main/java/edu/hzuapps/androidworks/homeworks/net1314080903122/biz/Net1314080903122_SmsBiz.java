package edu.hzuapps.androidworks.homeworks.net1314080903122.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import edu.hzuapps.androidworks.homeworks.net1314080903122.bean.Net1314080903122_SendedMsg;
import edu.hzuapps.androidworks.homeworks.net1314080903122.db.Net1314080903122_SmsProvider;

/**
 * Created by Administrator on 2016/5/27.
 */
public class Net1314080903122_SmsBiz {
    private Context context;
    public Net1314080903122_SmsBiz(Context context)
    {
        this.context = context;
    }
    public int sendMsg(String number,String msg,PendingIntent sentPi,PendingIntent deliverPi)
    {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content : contents)
        {
            smsManager.sendTextMessage(number,null,content,sentPi,deliverPi);

        }
        return contents.size();
    }

    public int sendMsg(Set<String> numbers,Net1314080903122_SendedMsg msg,PendingIntent sentPi,PendingIntent deliverPi)
    {
        save(msg);
        int result = 0;
        for (String number : numbers)
        {
            int count = sendMsg(number,msg.getMsg(),sentPi,deliverPi);
            result += count;
        }
        return result;
    }

    private void save(Net1314080903122_SendedMsg sendedMsg)
    {
        sendedMsg.setDate(new Date());
        ContentValues values = new ContentValues();
        values.put(Net1314080903122_SendedMsg.COLUMN_DATE,sendedMsg.getDate().getTime());
        values.put(Net1314080903122_SendedMsg.COLUMN_FES_NAME,sendedMsg.getFestivalName());
        values.put(Net1314080903122_SendedMsg.COLUMN_MSG,sendedMsg.getMsg());
        values.put(Net1314080903122_SendedMsg.COLUMN_NAMES,sendedMsg.getNames());
        values.put(Net1314080903122_SendedMsg.COLUMN_NUMBER,sendedMsg.getNumbers());

        context.getContentResolver().insert(Net1314080903122_SmsProvider.URI_SMS_ALL,values);

    }
}
