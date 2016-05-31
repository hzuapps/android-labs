package edu.hzuapps.androidworks.homeworks.net1314080903212;

//import java.io.*;
import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.FileNameMap;

/**
 * Created by dell on 2016/4/18.
 */

/*读历史记录，如果没有指定目录或文件，只需直接返回，
    不需要创建， 创建会在写历史记录的时候执行
 */
public class Net1314080903212RH {

    public void file_read(TextView show)
    {
        try {
            File get_path =  Environment.getExternalStorageDirectory();     //
            File file = new File(get_path.getCanonicalPath() , "ChatHistory");

            //如果目录不存在或者目录不存在， 什么也不做
            if (!file.exists()){
                //nothing to do
                System.out.println("file_read 文件不存在???????????????????????????????????????????");
                return ;
            }

            System.out.println("file_read 文件存在!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //两个都存在，这读取历史记录，显示到show区域。一行一行存储历史记录。
            FileInputStream fis = new FileInputStream(file); //(get_path.getCanonicalPath() + "ChatHistory");
            BufferedReader read_file = new BufferedReader(new InputStreamReader(fis));
            String line = null;

            while ((line = read_file.readLine()) != null) {
                //显示到show区域
                //将读取的内容追加显示到文本框
                show.append("\n" + line);
                System.out.println("读历史记录:" + line + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
            read_file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



}
