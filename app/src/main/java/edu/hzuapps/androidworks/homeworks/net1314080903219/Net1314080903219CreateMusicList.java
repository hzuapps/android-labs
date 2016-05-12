package edu.hzuapps.androidworks.homeworks.net1314080903219;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.widget.EditText;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class Net1314080903219CreateMusicList extends Service {
    private static final int FILE_SELECT_CODE =5 ;
   // private String listName;
    Net1314080903219FileStoreReader f;
  //  final EditText inputServer = new EditText(this);
    public Net1314080903219CreateMusicList() {



    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public int onStartCommand(Intent intent, int flags, int startId){

        f=new Net1314080903219FileStoreReader(intent.getStringExtra("listName"));
        String[] path=intent.getStringExtra("songPath").split("/");
        int l=path.length;
        f.store(path[l-1]+":"+intent.getStringExtra("songPath"));
        f.close();
        return START_STICKY;
    }

}
