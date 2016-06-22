package com.rgb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Net1314080903131RGBActivity extends Activity {
	private TextView tv_rgb;
	private ImageView iv_image;
	private Bitmap bitmap;
	private String TAG = "Net1314080903131RGBActivity";
	private Button btnColor;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final String TEMP_JPG_NAME = "temp.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.net1314080903131activity_rgb);


		tv_rgb = (TextView) findViewById(R.id.textview);
		btnColor = (Button) findViewById(R.id.btnColor);
		iv_image = (ImageView) findViewById(R.id.iv_image);
		btnColor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 openAlbum();
			}
		});
		iv_image.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int x = (int) event.getX();

				int y = (int) event.getY();

				if (event.getAction() == MotionEvent.ACTION_UP) {
					int color = bitmap.getPixel(x, y);
					int r = Color.red(color);
					int g = Color.green(color);
					int b = Color.blue(color);
					int a = Color.alpha(color);
					Log.i(TAG, "r=" + r + ",g=" + g + ",b=" + b);
					tv_rgb.setText("a=" + a + ",r=" + r + ",g=" + g + ",b="
							+ b);
					btnColor.setBackgroundColor(Color.rgb(r, g, b));
				}
				return true;
			}
		});
	}
	
	 // 打开相册
    private void openAlbum() {
      /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image*//*");*/

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
     /*
       Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);*/
        startActivityForResult(intent, PHOTOZOOM);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            if (resultCode == NONE)
                return;

            if (data == null)
                return;

            // 读取相册缩放图片
            if (requestCode == PHOTOZOOM) {
              /*  Uri image = data.getData();
                Toast.makeText(MymessageActivity.this,image+"", Toast.LENGTH_LONG).show();*/

                if (data != null) {
                    startPhotoZoom(data.getData());
                }

            }

            // 处理结果
            if (requestCode == PHOTORESOULT) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmap = extras.getParcelable("data");
                    //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    comp(bitmap);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    iv_image.setImageBitmap(bitmap);
                   /* logoName = FileUtils.getFilename(MainAppUtil.getCustom().getSusername());
                    FileUtils.writeFile(Constants.LOGO_CACHE_PATH, logoName, photo);*/
                }
            }

            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayIn
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {
            //判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            //这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//这里设置高度为800f
        float ww = 500f;//这里设置宽度为500f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }
}
