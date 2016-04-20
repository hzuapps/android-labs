package com.example.dell.multichat;

//import java.io.*;
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
public class ReadHistory  {

    public void file_read()
    {
        File    get_path = this.getAbsolutePath();      //
        String  path_str = get_path.toString() + "/history";
        File    path = new File(path_str);
        String  file_str = path_str + "/ChatHistory";
        File    file = new File(path_str, "ChatHistory");


        //如果目录不存在或者目录不存在， 什么也不做
        if (!path.exists() || !file.exists()){
            //nothing to do
        }

        //两个都存在，这读取历史记录，显示到show区域。一行一行存储历史记录。

        BufferedReader read_file = null;
        try {
            read_file = new BufferedReader(new InputStreamReader(new FileInputStream(file_str)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line_data = null;
        try {
            while ((line_data = read_file.readLine()) != null)
            {
                //显示到show区域

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
