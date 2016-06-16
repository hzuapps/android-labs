package single_thread_download.skyward.com.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.LoginFilter;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import single_thread_download.skyward.com.bean.FileInfo;

public class DownloadService extends Service {
    DownloadTask downloadTask = null;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (MSG_INIT) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i(TAG, "handleMessage: " + fileInfo);
                    downloadTask = new DownloadTask(DownloadService.this, fileInfo);
                    downloadTask.download();
                    break;
            }
        }
    };
    private static final String TAG = "DownloadService";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final int MSG_INIT = 0;
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        //获得Activity传来的参数
        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
        if(action.equals(ACTION_START)) {
            Log.i(TAG, "Start: " + fileInfo.toString());
            new InitThread(fileInfo).start();
        } else if(action.equals(ACTION_STOP)) {
            Log.i(TAG, "Stop: " +fileInfo.toString());
            downloadTask.isPause = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;
                if(conn.getResponseCode() == 200) {
                    //获取文件长度
                    length = conn.getContentLength();
                }
                if(length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if(!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                //设置文件长度
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(raf != null) {
                        raf.close();
                    }
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
