package edu.hzuapps.androidworks.homeworks.net1314080903101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.io.FileNotFoundException;

import android.net.Uri;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Net1314080903101Activity extends AppCompatActivity {

    private Uri imageUri;
    private ImageButton imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net1314080903101);

        imageView = (ImageButton) findViewById(R.id.imageView);
        imageUri = Uri.parse("file:///sdcard/temp.jpg");

        Intent intent;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);      //调用照相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);        //指定照相机拍到的照片原图存放位置
        startActivityForResult(intent, 1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_net1314080903101, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

// onActivityResult需要按提示修改
        if(resultCode == Activity.RESULT_OK)
        {Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }


        }
    }
}
