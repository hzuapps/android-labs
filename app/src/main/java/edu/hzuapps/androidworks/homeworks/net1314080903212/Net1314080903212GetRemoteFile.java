package package edu.hzuapps.androidworks.homeworks.net1314080903212;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dell on 2016/4/28.
 */
public class Net1314080903212GetRemoteFile implements Runnable{
    public TextView GRFshow;
    public Handler sendHandler;

    public void Net1314080903212GetRemoteFile(Handler handler)
    {
        sendHandler = handler;
    }

    public void run()
    {
        try {
            System.out.println("before new URL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            URL git_url = new URL("https://raw.githubusercontent.com/OurScream/PerIntBank/master/README.md");

            System.out.println("before new URLConnection!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //打开url链接
            URLConnection git_con = git_url.openConnection();

            //获取数据
 //           InputStream is = git_con.getInputStream();                         //方法1
 //           BufferedInputStream bis = new BufferedInputStream(is);
            InputStream bis = git_url.openStream();                              //方法2

            int read_len = 0;
            byte[] readbyte = new byte[1024];

            System.out.println("before new get_path!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //新建文件
            File get_path =  Environment.getExternalStorageDirectory();     //
            File file =  new File(get_path.getCanonicalPath() , "ChatHistory");

            //判断历史记录文件是否存在， 不存在则创建
            if (!file.exists()){
                    file.createNewFile();
                    System.out.println("GetRemoteFile: 创建文件!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }

            while((read_len = bis.read(readbyte)) != -1)
            {
                RandomAccessFile raf = new RandomAccessFile(file,"rw");
                raf.seek(file.length());
                raf.write(readbyte);                    //这里的换行不知道可不可以！
                System.out.println(readbyte.toString() + "GetRemoteFile: 看看一条记录大概都长:" + file.length());
                raf.close();
            }
            //再读一次文件
            Message msg = new Message();
            msg.what = 0x789;
            sendHandler.sendMessage(msg);

//            new Net1314080903212RH().file_read(new Net1314080903212MultiChat().show);
        }
        catch(MalformedURLException murle)
        {
            murle.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
