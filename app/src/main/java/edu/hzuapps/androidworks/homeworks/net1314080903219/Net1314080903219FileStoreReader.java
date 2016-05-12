package edu.hzuapps.androidworks.homeworks.net1314080903219;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by j1 on 2016/5/6.
 */
public class Net1314080903219FileStoreReader {
    private File object=null;
    private FileInputStream fin=null;
    private FileOutputStream fout=null;
    private int fileLength=0;
    public Net1314080903219FileStoreReader(String name){
        object=new File("/storage/sdcard1/Music/"+name+".txt");
        System.out.println(object.getPath());
        if(object.exists()){
            try {
                fin=new FileInputStream(object);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ;
            }
        }

        try {
                fout=new FileOutputStream(object);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }

    public boolean close(){
        try {
            if (fin!=null) {
                fin.close();
            }
            if(fout!=null){
                fin.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int store(String data){
        if(fout==null){
            return -1;
        }
        byte[] by=data.getBytes();
        try {
            fout.write(by);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return by.length;
    }

    public int read(byte[] data){
        if(fin==null){
            return -1;
        }
        int length;
        try {
            length=fin.available();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        if(length==0){
            return 0;
        }
        try {
            fin.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.length>length?length:data.length;
    }
}
