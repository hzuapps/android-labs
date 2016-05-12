package edu.hzuapps.androidworks.homeworks.net1314080903118;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络请求封装，用于GET请求和获取图片
 * Created by RImpression on 2016/4/29 0029.
 */
public class Net1314080903118MyRequest {

    Context mContext;

    public Net1314080903118MyRequest(Context context) {
        this.mContext = context;
    }

    public void getRequest(String url, final HttpListener httpListener) {
        //开启一个异步线程来发起网络请求
        new AsyncTask<String,Void,String>() {

            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection conn = null;
                String result = "";
                try {
                    Log.i("myRequest",params[0]);
                    URL url = new URL(params[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(8000);
                    InputStream in = conn.getInputStream();
                    //Log.i("myRequest",in.toString());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    result = response.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null && s.length() >0 ) {
                    httpListener.onSuccess(s);
                } else {
                    httpListener.onError(s);
                }
            }
        }.execute(url);
    }


    public void getImage(final String url,ImageView imageView) {
        DownImage downImage = new DownImage(imageView);
        downImage.execute(url);
    }

    /**
     * 自定义异步线程，用于获取网络图片
     */
    class DownImage extends AsyncTask<String,Integer,Bitmap> {
        private ImageView imageView;

        public DownImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                Log.i("myRequest",bitmap.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }


    public interface HttpListener {
        void onSuccess(String result);

        void onError(String errorResult);
    }

}
