package com.hui.multidown;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Net1314080903225MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private int total = 0;
    private boolean downloading = false;
    private URL url;
    private File file;
    private Button btn_down;
    private EditText et_fileUrl;

    private int length;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                progressBar.setProgress(msg.arg1);
                if (msg.arg1 == length) {
                    Toast.makeText(MainActivity.this, "下载完成！", Toast.LENGTH_SHORT).show();
                    total = 0;
                }
            }
            return false;
        }
    });

    private List<HashMap<String, Integer>> threadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_fileUrl = (EditText) findViewById(R.id.FileUrl);
        btn_down = (Button) findViewById(R.id.btn_down);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        threadList = new ArrayList<>();

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloading) {
                    downloading = false;
                    btn_down.setText("下载");
                    return;
                }
                downloading = true;
                btn_down.setText("暂停");

                if (threadList.size() == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                url = new URL(et_fileUrl.getText().toString());
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5000);
                                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
                                length = conn.getContentLength();

                                progressBar.setMax(length);
                                progressBar.setProgress(0);

                                if (length < 0) {
                                    Toast.makeText(MainActivity.this, "文件不存在！", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                file = new File(Environment.getExternalStorageDirectory(), getFileName(et_fileUrl.getText().toString()));
                                RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
                                randomFile.setLength(length);

                                int blockSize = length / 3;
                                for (int i = 0; i < 3; i++) {
                                    int begin = i * blockSize;
                                    int end = (i + 1) * blockSize - 1;
                                    if (i == 2) {
                                        end = length;
                                    }

                                    HashMap<String, Integer> map = new HashMap<String, Integer>();
                                    map.put("begin", begin);
                                    map.put("end", end);
                                    map.put("finished", 0);
                                    threadList.add(map);

                                    //创建新的线程，下载文件。
                                    Thread t = new Thread(new DownloadRunnable(i, begin, end, file, url));
                                    t.start();
                                }

                            } catch (MalformedURLException e) {
                                Toast.makeText(MainActivity.this, "URL 不正确！", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    //恢复下载
                    for (int i = 0; i < threadList.size(); i++) {
                        HashMap<String, Integer> map = threadList.get(i);
                        int begin = map.get("begin");
                        int end = map.get("end");
                        int finished = map.get("finished");
                        Thread t = new Thread(new DownloadRunnable(i, begin + finished, end, file, url));
                        t.start();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getFileName(String url) {
        int index = url.lastIndexOf("/") + 1;
        return url.substring(index);
    }

    class DownloadRunnable implements Runnable {

        private int begin;
        private int end;
        private File file;
        private URL url;
        private int id;

        public DownloadRunnable(int id, int begin, int end, File file, URL url) {
            this.file = file;
            this.id = id;
            this.begin = begin;
            this.end = end;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                if (begin > end) {
                    return;
                }
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727)");
                conn.setRequestProperty("Range", "bytes=" + begin + "-" + end);

                InputStream is = conn.getInputStream();
                byte[] buf = new byte[1024 * 1024];
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
                randomFile.seek(begin);
                int len = 0;
                HashMap<String, Integer> map = threadList.get(id);
                while ((len = is.read(buf)) != -1 && downloading) {
                    randomFile.write(buf, 0, len);
                    updateProgress(len);
                    map.put("finished", map.get("finished") + len);
                    Log.d("Download length: ", "" + total);
                }

                is.close();
                randomFile.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized private void updateProgress(int add) {
        total += add;
        handler.obtainMessage(0, total, 0).sendToTarget();
    }
}
