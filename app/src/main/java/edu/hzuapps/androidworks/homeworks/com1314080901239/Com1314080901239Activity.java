package com.example.test;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Com1314080901239Activity extends Activity {

	private final String IMAGE_TYPE = "image/*";
	private final int PICK_PHOTO = 1; // 选择照片
	private final int TAKE_PHOTO = 0; // 拍照
	private final int CUT_PHOTO = 2;// 裁剪图片
	private Uri fileUri;
	private ImageView iv;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_com1314080901239);
		iv = (ImageView) findViewById(R.id.iv);
	}

	// 拍照
	public void takePhoto(View v) {

		// 启动一个意图。这里就是调用google给我们提供的，手机自带的拍照应用
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 设置存储路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
		// 设置图片存储路径及名字
				Uri.fromFile(new File("sdcard/nuna.jpg")));

		startActivityForResult(intent, TAKE_PHOTO);

	}

	public void pickPhoto(View v) {
		// 调用系统提供的相册功能
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(IMAGE_TYPE);
		startActivityForResult(intent, PICK_PHOTO);

	}

	public void cutPhoto(View v) {
		 Cutphoto();
	}
	
	public void getJson(View v){
		Intent intent=new Intent(Com1314080901239Activity.this,Com1314080901239GetJson.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bm = null;
		ContentResolver resolver = getContentResolver();
		if (requestCode == TAKE_PHOTO) {
			Toast.makeText(getApplicationContext(), "拍照成功", 0).show();
		} else if (requestCode == PICK_PHOTO) {
			Uri originalUri = data.getData();
			try {
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				iv.setImageBitmap(bm);
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(originalUri, proj, null, null,
						null);
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

				cursor.moveToFirst();
				// 最后根据索引值获取图片路径
				path = cursor.getString(column_index);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (requestCode == CUT_PHOTO) {
			if (resultCode == RESULT_OK) {
				savePhoto(data);
			}
		}
	}

	// 裁剪图片的方法
	public void Cutphoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		//intent.setDataAndType(uri, IMAGE_TYPE);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outoutX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CUT_PHOTO);

	}

	// 保存裁剪后的图片
	public void savePhoto(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			iv.setBackgroundDrawable(drawable);
		}
	}
	
	

}
