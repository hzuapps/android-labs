package edu.hzuapps.androidworks.homeworks.net1314080903112;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity_net1314080903112 extends AppCompatActivity {

    TextView apkPathText;

    String apkPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_net1314080903112);
        apkPathText = (TextView) findViewById(R.id.apkPathText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            apkPath = data.getStringExtra("apk_path");
            apkPathText.setText(apkPath);
        }
    }

    public void onChooseApkFile(View view) {
        Intent intent = new Intent(this, FileExplorerActivity_net1314080903112.class);
        startActivityForResult(intent, 0);
    }

    public void onSilentInstall(View view) {
        if (!isRoot()) {
            Toast.makeText(this, "没有ROOT权限，不能使用秒装", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(this, "请选择安装包！", Toast.LENGTH_SHORT).show();
            return;
        }
        final Button button = (Button) view;
        button.setText("安装中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SilentInstall_net1314080903112 installHelper = new SilentInstall_net1314080903112();
                final boolean result = installHelper.install(apkPath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result) {
                            Toast.makeText(MainActivity_net1314080903112.this, "安装成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity_net1314080903112.this, "安装失败！", Toast.LENGTH_SHORT).show();
                        }
                        button.setText("秒装");
                    }
                });

            }
        }).start();

    }

    public void onForwardToAccessibility(View view) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    public void onSmartInstall(View view) {
        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(this, "请选择安装包！", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }

    /**
     * 判断手机是否拥有Root权限。
     * @return 有root权限返回true，否则返回false。
     */
    public boolean isRoot() {
        boolean bool = false;
        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

}
