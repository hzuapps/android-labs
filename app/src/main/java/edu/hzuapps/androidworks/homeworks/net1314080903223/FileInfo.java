package single_thread_download.skyward.com.bean;

import java.io.Serializable;

/**
 * Created by skyward on 2016/5/30.
 */
public class FileInfo implements Serializable{
    //编号
    private int id;
    //文件名
    private String fileName;
    //文件大小
    private int length;
    //下载链接
    private String url;
    //已下载文件大小
    private int finished;

    public FileInfo(int id,  String url, String fileName, int length, int finished) {
        this.id = id;
        this.fileName = fileName;
        this.length = length;
        this.url = url;
        this.finished = finished;
    }

    public FileInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", url='" + url + '\'' +
                ", finished=" + finished +
                '}';
    }
}
