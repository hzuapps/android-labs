package edu.hzuapps.androidworks.homeworks.net1314080903111;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Net1314080903111_MainActivity extends Activity {
    ImageView show;
    Bitmap bitmap;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                // 使用ImageView显示该图片
                show.setImageBitmap(bitmap);

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903111);
        show = (ImageView) findViewById(R.id.show);

        new Thread() {

            @Override
            public void run() {
                // 定义一个URL对象
                URL url;
                try {
                    url = new URL(
                            "https://github.com/hjf1230/android-labs.git");
                    // 打开该URL的资源输入流
                    InputStream is = url.openStream();
                    // 从InputStream中解析出图片
                    bitmap = BitmapFactory.decodeStream(is);
                    // 发送消息
                    handler.sendEmptyMessage(0x123);
                    is.close();
                    // 再次打开RL对应的资源输入流
                    is = url.openStream();
                    // 打开手机文件对应的输出流
                    OutputStream os = openFileOutput("KEQIANG.JPG", MODE_APPEND);
                    byte[] buff = new byte[1024];
                    int hasRead = 0;
                    // 将URL资源下载到本地
                    while ((hasRead = is.read(buff)) > 0) {
                        os.write(buff, 0, hasRead);
                    }
                    is.close();
                    os.close();
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}