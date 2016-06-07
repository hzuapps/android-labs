package edu.hzuapps.androidworks.homeworks.Net1314080903135;



import edu.hzuapps.androidworks.homeworks.Net1314080903135.Net1314080903135Filechooser;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.appcompat.*;
import android.os.Bundle;

public class Net1314080903135Activitymain extends Activity implements OnClickListener{
    public static String SDCARD_ROOT_PATH = "";
    public static final String SAVE_PATH_IN_SDCARD = "/iwsns/";
    private static String SAVE_PATH_IN_NAME = "zipData.zip";
    private static String TAG = "MainActivity";
    private Intent fileChooserIntent ;
    private static final int REQUEST_CODE = 1;
    public static final String EXTRA_FILE_CHOOSER = "file_chooser";
    private String savepath=Environment.getExternalStorageDirectory().toString()+"baocun.zip";
    private String savepath2=Environment.getExternalStorageDirectory().toString();
    private String sss[]={"a","zip"};
    private String ssss[]={"a","a"};
    private EditText filepathet;
    private boolean isfile=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net131408093135_activitymain);

        filepathet=(EditText)findViewById(R.id.yasuopathet);


        Button zipButton = (Button) findViewById(R.id.button_zip);
        zipButton.setOnClickListener(this);

        Button unzipButton=(Button) findViewById(R.id.button_unzip);
        unzipButton.setOnClickListener(this);

        Button ysdirbt=(Button)findViewById(R.id.chooseysdirbt);
        ysdirbt.setOnClickListener(this);


        fileChooserIntent =  new Intent(this ,Net1314080903135Filechooser.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void toast(CharSequence hint){
        Toast.makeText(this, hint , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_zip:
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().toString();
                }else{
                    SDCARD_ROOT_PATH = Environment.getDownloadCacheDirectory().toString();
                }
                Log.e("dh", "path:  "+SDCARD_ROOT_PATH);
                try {
//				ZipUitl.zipFile(SDCARD_ROOT_PATH+"/apps", SAVE_PATH_IN_NAME, SDCARD_ROOT_PATH+
//                       SAVE_PATH_IN_SDCARD);
//				ZipUitl.zipFolder(SDCARD_ROOT_PATH+"/jay.png", SDCARD_ROOT_PATH+
//                        SAVE_PATH_IN_SDCARD+SAVE_PATH_IN_NAME);
//				ZipUitl.zipFolder("/storage/sdcard0/DCIM/a.jpg", SDCARD_ROOT_PATH+
//                       SAVE_PATH_IN_NAME);
                    if(sss[sss.length-1].equalsIgnoreCase("zip")){
                        toast("请选择一个文件（非zip）进行压缩!");
                        return;
                    }
                    toast("压缩中，请稍后....");
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                Net1314080903135ZipUi.zipFolder(filepathet.getText().toString(), savepath);
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "文件压缩成功并保存到当前目录", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e("dh", "error:  "+e.toString());
                }

                break;

            case R.id.button_unzip:
                Log.e("dh", "path:  "+SDCARD_ROOT_PATH);
                try {
                    if(!ssss[ssss.length-1].equalsIgnoreCase("zip")){
                        toast("请选择一个zip文件进行解压!");
                        return;
                    }
                    toast("解压中，请稍后....");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try {
                                Net1314080903135ZipUi.unZipFolder(filepathet.getText().toString(),savepath2);
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "文件解压成功并保存到当前目录", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e("dh", "error:  "+e.toString());
                }
                //System.out.println("printed");
                break;


            case R.id.chooseysdirbt:
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                    startActivityForResult(fileChooserIntent , REQUEST_CODE);
                else
                    toast(getText(R.string.sdcard_unmonted_hint));
                break ;


            default:
                break;
        }

    }

    public void onActivityResult(int requestCode , int resultCode , Intent data){

        Log.v(TAG, "onActivityResult#requestCode:"+ requestCode  + "#resultCode:" +resultCode);
        if(resultCode == RESULT_CANCELED){
            toast(getText(R.string.open_file_none));
            return ;
        }
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            String filePath = data.getStringExtra(EXTRA_FILE_CHOOSER);
            System.out.println(filePath);
            Log.v(TAG, "onActivityResult # pptPath : "+ filePath );
            if(filePath != null){
                if(filePath.startsWith("Dir")){
                    filePath=filePath.substring(3);
                    toast("Choose File : " + filePath);
                    String ss[]=filePath.split("/");
                    sss[0]=ss[ss.length-1];
                    sss[sss.length-1]="a";
                    savepath=filePath.substring(0,filePath.length()-ss[ss.length-1].length())+sss[0]+".zip";
                    filepathet.setText(filePath);
                }
                else if(filePath.startsWith("File")){
                    filePath=filePath.substring(4);
                    toast("Choose File : " + filePath);
                    String ss[]=filePath.split("/");
                    sss=ss[ss.length-1].split("\\.");
                    ssss=ss[ss.length-1].split("\\.");
                    //String
                    savepath=filePath.substring(0,filePath.length()-ss[ss.length-1].length())+ss[ss.length-1]+".zip";
                    savepath2=filePath.substring(0,filePath.length()-ss[ss.length-1].length());
                    System.out.println(savepath2);
                    System.out.println(sss[sss.length-1]);
                    filepathet.setText(filePath);
                }
                //System.out.println(sss.length);

            }
            else
                toast(getText(R.string.open_file_failed));
        }
    }

}