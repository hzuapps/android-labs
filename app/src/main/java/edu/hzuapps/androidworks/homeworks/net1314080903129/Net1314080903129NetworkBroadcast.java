package edu.hzuapps.androidworks.homeworks.net1314080903129;

/**
 * Created by Administrator on 2016/4/25.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by dudon on 2016/1/14.
 */
public class Net1314080903129NetworkBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //动态注册广播接收器
        //接收系统网络状态
        ConnectivityManager connectivityManager = null;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //判断是否连接网络
        if (networkInfo != null && networkInfo.isAvailable()) {

            if(networkInfo.getType()==0){
                //mobile连接状态
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.net1314080903129mobile);
                imageView.setMaxHeight(40);
                Toast toast = new Toast(context);
                toast.setView(imageView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
            if(networkInfo.getType()==1){
                //Wifi连接状态
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.net1314080903129wifi);
                imageView.setMaxHeight(40);
                Toast toast = new Toast(context);
                toast.setView(imageView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }

            //Log.d("woider",networkInfo.getType()+networkInfo.getTypeName());
        } else {
            //网络不可用
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.net1314080903129shutdown);
            imageView.setMaxHeight(40);
            Toast toast = new Toast(context);
            toast.setView(imageView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
