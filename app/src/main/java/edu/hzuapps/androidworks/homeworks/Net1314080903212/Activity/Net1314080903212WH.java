package com.example.dell.multichat;

import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.RandomAccess;

/**
 * Created by dell on 2016/4/18.
 */
public class Net1314080903212WH {

    public void write_file(String msg)
    {
        File get_path =  Environment.getExternalStorageDirectory();     //
/*        String  path_str = get_path.toString() + "/MultiChat";

        System.out.println("path_str = " + path_str);

        File    path = new File(path_str);
        //判断历史记录目录是否存在，不存在则创建
        if (!path.exists()){
            path.mkdirs();
            System.out.println("创建文件夹!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
*/
        File file = null; //File(path_str+"/ChatHistory");
        try {
            file = new File(get_path.getCanonicalPath() , "ChatHistory");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断历史记录文件是否存在， 不存在则创建
        if (!file.exists()){
            try {
                file.createNewFile();
                System.out.println("创建文件!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("新建文件之后!");
        //获取时间+历史记录写入文件 ； 从服务器接收到的信息写入文件
        // （自己发送的信息不用写入，因为服务器还会返回发送出去的信息，如果再写会有重复）
        Date    date = new Date();
        DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = date_format.format(date);
        String content = time + "\n" + msg + "\n";
        try {
//            FileWriter fw = new FileWriter(file);
//            FileOutputStream fos = new FileOutputStream(file);

            if (file.length() > 300)             //日后改!!!!
            {
                FileWriter fw = new FileWriter(file);
                fw.write(content);
                System.out.println(content + "看看一条记录大概都长:" + file.length());
                fw.close();
            }
            else
            {
                RandomAccessFile raf = new RandomAccessFile(file,"rw");
                raf.seek(file.length());
                raf.write(content.getBytes());                    //这里的换行不知道可不可以！
                System.out.println(content + "看看一条记录大概都长:" + file.length());
                raf.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
