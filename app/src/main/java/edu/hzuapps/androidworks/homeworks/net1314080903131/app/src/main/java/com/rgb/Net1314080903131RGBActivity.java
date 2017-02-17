package com.rgb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

public class Net1314080903131RGBActivity extends Activity {
	private TextView tv_rgb;
	private ImageView iv_image;
	private Bitmap bitmap;
	private String TAG = "Net1314080903131RGBActivity";
	private Button btnColor;
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// ����
	public static final int PHOTOZOOM = 2; // ����
	public static final int PHOTORESOULT = 3;// ���
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final String TEMP_JPG_NAME = "temp.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rgb);

		
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
					// ����������ĸ�ϸ�µĻ� ���԰���ɫֵ��R G B �õ�����Ӧ�Ĵ���
					int r = Color.red(color);
					int g = Color.green(color);
					int b = Color.blue(color);
					int a = Color.alpha(color);
					Log.i(TAG, "r=" + r + ",g=" + g + ",b=" + b);
					tv_rgb.setText("a=" + a + ",r=" + r + ",g=" + g + ",b="
							+ b);
					btnColor.setTextColor(Color.rgb(r, g, b));
				}
				return true;
			}
		});
	}
	
	 // �����
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

            // ��ȡ�������ͼƬ
            if (requestCode == PHOTOZOOM) {
              /*  Uri image = data.getData();
                Toast.makeText(MymessageActivity.this,image+"", Toast.LENGTH_LONG).show();*/

                if (data != null) {
                    startPhotoZoom(data.getData());
                }

            }

            // ������
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
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
            baos.reset();//����baos�����baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ�������ݴ�ŵ�baos��
            options -= 10;//ÿ�ζ�����10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ��������baos��ŵ�ByteArrayInputStream��
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream������ͼƬ
        return bitmap;
    }

    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {
            //�ж����ͼƬ����1M,����ѹ�����������ͼƬ��BitmapFactory.decodeStream��ʱ���
            baos.reset();//����baos�����baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            //����ѹ��50%����ѹ�������ݴ�ŵ�baos��
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //���������ֻ�Ƚ϶���800*500�ֱ��ʣ����ԸߺͿ���������Ϊ
        float hh = 800f;//�������ø߶�Ϊ800f
        float ww = 500f;//�������ÿ��Ϊ500f
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ����ݽ��м��㼴��
        int be = 1;//be=1��ʾ������
        if (w > h && w > ww) {//����ȴ�Ļ���ݿ�ȹ̶���С����
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//���߶ȸߵĻ���ݿ�ȹ̶���С����
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//�������ű���
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//ѹ���ñ����С���ٽ�������ѹ��
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY �ǿ�ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY �ǲü�ͼƬ���
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }
}
