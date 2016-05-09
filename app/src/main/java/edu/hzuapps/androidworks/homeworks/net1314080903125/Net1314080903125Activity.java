package edu.hzuapps.androidworks.homeworks.net1314080903125;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Net1314080903125Activity extends AppCompatActivity {

    private static int CAMERA_REQUEST_CODE = 1;    //摄像头请求码
    private static int GALLERY_REQUEST_CODE = 2;   //图库请求码
    private static int CROP_REQUEST_CODE=3;        //裁剪器请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net1314080903125);


        Button btn_camera = (Button) findViewById(R.id.btn_camera); //摄像头按钮
        btn_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开拍照界面
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        Button btn_gallery = (Button)findViewById(R.id.btn_gallery);  //图库按钮
        btn_gallery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);   //打开图库界面
                intent.setType("image/*");    //文件类型
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }
    private Uri saveBitmap(Bitmap bm)  //将Bitmap数据保存到sd卡
    {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/zaak");    //获取sd卡中的路径
        if (!tmpDir.exists())  //判断路径是否存在
        {
            tmpDir.mkdir();  //创建文件夹
        }

            File img = new File(tmpDir.getAbsolutePath() + "zaak.png");   //创建图像文件对象
            try {
                FileOutputStream fos = new FileOutputStream(img); //获取输出流
                bm.compress(Bitmap.CompressFormat.PNG, 85, fos);    //将数据写入输出流
                fos.flush();  //刷新
                fos.close();
                return Uri.fromFile(img);   //返回File类型Uri
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


    private Uri convertUri(Uri uri)   //转换Uri
    {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);  //获取InputStream
            Bitmap bitmap = BitmapFactory.decodeStream(is);  //将InputStream转换成bitmap
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private void startImageZoom(Uri uri){   //图像裁剪功能
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");  //数据通过uri传递；类型为image
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);   //宽高的比例
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_REQUEST_CODE);   //启动图像裁剪界面
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   //对拍照数据的处理
        if (requestCode == CAMERA_REQUEST_CODE) {  //判断请求码
            if (data == null) {    //取消
                return;
            } else {
                Bundle extras = data.getExtras();   //取出数据
                if (extras != null) {
                    Bitmap bm = extras.getParcelable("data");   //保存拍摄数据
//                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
//                    imageView.setImageBitmap(bm);   //显示数据（imageView）
                    Uri uri = saveBitmap(bm);      //获取bm的uri
                    startImageZoom(uri);
                }
            }
        }
        else if (requestCode == GALLERY_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();   //所选图片对应的Uri
            Uri fileUri = convertUri(uri);  //转换uri
            startImageZoom(fileUri);

        }
        else if (requestCode == CROP_REQUEST_CODE){  //获取图像数据并显示在imageView
            if(data == null){
                return;
            }
            Bundle extras = data.getExtras();
            Bitmap bm = extras.getParcelable("data");
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            imageView.setImageBitmap(bm);
        }
   }

}
