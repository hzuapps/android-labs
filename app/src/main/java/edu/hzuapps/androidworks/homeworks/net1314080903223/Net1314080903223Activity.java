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

public class Net1314080903223Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903223);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        bt_operation = (Button) findViewById(R.id.bt_operation);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_fileName = (TextView) findViewById(R.id.tv_fileName);

      
    }

}
