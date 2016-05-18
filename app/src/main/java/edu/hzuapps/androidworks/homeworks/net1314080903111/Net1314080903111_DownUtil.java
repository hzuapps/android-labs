package edu.hzuapps.androidworks.homeworks.net1314080903111;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net1314080903111_DownUtil {

    // 定义下载资源的路径
    private String path;
    // 指定所下载的文件的保存位置
    private String targetFile;
    // 定义需要使用多少线程下载资源
    private int threadNum;
    // 定义下载的线程对象
    private DownThread[] threads;
    // 定义下载的文件总大小
    private int fileSize;

    public Net1314080903111_DownUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
    }

    public void download() throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 得到文件的大小
        fileSize = conn.getContentLength();
        conn.disconnect();
        int currentPartsSize = fileSize / threadNum + 1;
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        // 设置本地文件的大小
        file.setLength(fileSize);
        file.close();
        for (int i = 0; i < threadNum; i++) {
            // 计算每条线程的下载位置
            int startPos = i * currentPartsSize;
            // 每个线程使用一个RandomAccessFile进行下载
            RandomAccessFile current = new RandomAccessFile(targetFile, "rw");
            // 定义该线程的下载位置
            current.seek(startPos);
            // 创建下载线程
            threads[i] = new DownThread(startPos, currentPartsSize, current);
            // 启动线程下载
            threads[i].start();
        }

    }

    // 获取下载的完成百分比
    public double getCompleteRate() {
        // 统计多条线程已经下载的总大小
        int sumSize = 0;
        for (int i = 0; i < threadNum; i++) {
            sumSize += threads[i].length;
        }
        return sumSize * 1.0 / fileSize;
    }

    private class DownThread extends Thread {
        // 定义当前线程下载的位置
        private int startPos;
        // 定义当前线程下载文件的大小
        private int currentPartsSize;
        // 当前线程下载的文件块
        private RandomAccessFile currentPart;
        // 定义该线程已下载的字节数
        private int length;

        public DownThread(int startPos, int currentPartsSize,
                          RandomAccessFile currentPart) {
            this.startPos = startPos;
            this.currentPart = currentPart;
            this.currentPartsSize = currentPartsSize;

        }

        @Override
        public void run() {
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "*/*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Connection", "Keep-Alive");
                InputStream in = conn.getInputStream();
                in.skip(startPos);
                int hasRead = 0;
                byte[] buffer = new byte[1024];
                // 读取网络数据，并写入本地文件
                while (length < currentPartsSize
                        && (hasRead = in.read(buffer)) > 0) {
                    currentPart.write(buffer, 0, hasRead);
                    // 累计该线程下载的总大小
                    length += hasRead;
                }
                currentPart.close();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
