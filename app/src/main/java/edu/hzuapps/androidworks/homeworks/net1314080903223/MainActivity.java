package single_thread_download.skyward.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import single_thread_download.skyward.com.bean.FileInfo;
import single_thread_download.skyward.com.bean.ThreadInfo;
import single_thread_download.skyward.com.db.ThreadDao;
import single_thread_download.skyward.com.db.ThreadDaoImpl;
import single_thread_download.skyward.com.service.DownloadService;

public class MainActivity extends AppCompatActivity {
    private ProgressBar pb_progress;
    private Button bt_operation;
    private TextView tv_fileName;
    private TextView tv_state;
    //默认值为初态
    private int state = 0;

    /**
     * 下载状态
     * 根据下载状态改变按钮文本内容，并执行不同操作
     * 0：初态
     * 按钮的文本内容为“下载”
     * 当用户按下按钮后，根据url开始下载文件，同时将按钮的文本内容改为“停止”，bt_state改为1，进入正在下载状态
     * 1：正在下载状态
     * 按钮文本内容为“停止”
     * 当用户按下按钮后，执行停止下载操作，同时将按钮的文本内容改为“继续下载”，bt_state改为2，进入停止下载状态
     * 2：停止下载状态
     * 按钮文本内容“继续下载”
     * 当用户按下按钮后，从上一次下载的位置开始下载，同时将按钮的文本内容改为“停止”，bt_state改为1，进入正在下载状态
     * 3.下载完成
     * 当文件下载完成时，将按钮内容改为“下载已完成”。同时将按钮设为不可点击。
     */
    public static final int START = 0;
    public static final int DOWNLOADING = 1;
    public static final int STOP = 2;
    public static final int FINISH = 3;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                pb_progress.setProgress(finished);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903223);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        bt_operation = (Button) findViewById(R.id.bt_operation);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_fileName = (TextView) findViewById(R.id.tv_fileName);

        final FileInfo fileInfo = new FileInfo(0, "http://dl.ad-safe.com/adsafe_v4/download/adsafe.v4.2.507.1201.exe", "adsafe.exe",0, 0);
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        bt_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                switch (state) {
                    //初态：开始下载操作，
                    case START:
                        bt_operation.setText("停止");
                        tv_state.setText("正在下载");
                        intent.setAction(DownloadService.ACTION_START);
                        intent.putExtra("fileInfo", fileInfo);
                        startService(intent);
                        state = DOWNLOADING;
                        break;
                    //正在下载状态：停止下载操作，进入停止下载状态
                    case DOWNLOADING:
                        bt_operation.setText("继续下载");
                        tv_state.setText("停止下载");
                        intent.setAction(DownloadService.ACTION_STOP);
                        intent.putExtra("fileInfo", fileInfo);
                        startService(intent);
                        state = STOP;
                        break;
                    //停止下载状态：从上次下载位置继续下载文件，进入正在下载状态
                    case STOP:
                        bt_operation.setText("停止");
                        tv_state.setText("正在下载");
                        intent.setAction(DownloadService.ACTION_START);
                        intent.putExtra("fileInfo", fileInfo);
                        startService(intent);
                        state = DOWNLOADING;
                        break;
                    case FINISH:
                        bt_operation.setText("下载已完成");
                        bt_operation.setEnabled(false);
                        tv_state.setText("下载已完成");
                        break;
                }
            }
        });
        //注册广播接收者
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
