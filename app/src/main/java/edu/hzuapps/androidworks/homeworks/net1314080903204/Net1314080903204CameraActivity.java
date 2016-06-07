package edu.hzuapps.andriodworks.homeworks.net1314080903204;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Net1314080903204CameraActivity extends Activity
{
    private static final String LOG_TAG = "HelloCamera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    private Button takePicBtn = null;
    private Button takeVideoBtn = null;

    private ImageView imageView = null;

    private Uri fileUri;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_net1314080903204);

        takePicBtn = (Button) findViewById(R.id.buttonPicture);
        takePicBtn.setOnClickListener(takePiClickListener);

        takeVideoBtn = (Button) findViewById(R.id.buttonVideo);
        takeVideoBtn.setOnClickListener(takeVideoClickListener);

        imageView = (ImageView) findViewById(R.id.imageView1);

    }

    private final OnClickListener takePiClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Log.d(LOG_TAG, "Take Picture Button Click");
            // 利用系统自带的相机应用:拍照
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // create a file to save the image
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
            // set the image file name
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    };

    private final OnClickListener takeVideoClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Log.d(LOG_TAG, "Take Video Button Click");
            // 摄像
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            // create a file to save the video
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
            // set the image file name
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // set the video image quality to high
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

            startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
        }
    };

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type)
    {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type)
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try
        {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");

            Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                Log.d(LOG_TAG,
                        "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else if (type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        }
        else
        {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult: requestCode: " + requestCode
                + ", resultCode: " + requestCode + ", data: " + data);
        // 如果是拍照
        if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode)
        {
            Log.d(LOG_TAG, "CAPTURE_IMAGE");

            if (RESULT_OK == resultCode)
            {
                Log.d(LOG_TAG, "RESULT_OK");

                // Check if the result includes a thumbnail Bitmap
                if (data != null)
                {
                    // 没有指定特定存储路径的时候
                    Log.d(LOG_TAG,
                            "data is NOT null, file on default position.");

                    // 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
                    // Image captured and saved to fileUri specified in the
                    // Intent
                    Toast.makeText(this, "Image saved to:\n" + data.getData(),
                            Toast.LENGTH_LONG).show();

                    if (data.hasExtra("data"))
                    {
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        imageView.setImageBitmap(thumbnail);
                    }
                }
                else
                {

                    Log.d(LOG_TAG,
                            "data IS null, file saved on target position.");
                    // If there is no thumbnail image data, the image
                    // will have been stored in the target output URI.

                    // Resize the full image to fit in out image view.
                    int width = imageView.getWidth();
                    int height = imageView.getHeight();

                    BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

                    factoryOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);

                    int imageWidth = factoryOptions.outWidth;
                    int imageHeight = factoryOptions.outHeight;

                    // Determine how much to scale down the image
                    int scaleFactor = Math.min(imageWidth / width, imageHeight
                            / height);

                    // Decode the image file into a Bitmap sized to fill the
                    // View
                    factoryOptions.inJustDecodeBounds = false;
                    factoryOptions.inSampleSize = scaleFactor;
                    factoryOptions.inPurgeable = true;

                    Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                            factoryOptions);

                    imageView.setImageBitmap(bitmap);
                }
            }
            else if (resultCode == RESULT_CANCELED)
            {
                // User cancelled the image capture
            }
            else
            {
                // Image capture failed, advise user
            }
        }

        // 如果是录像
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE)
        {
            Log.d(LOG_TAG, "CAPTURE_VIDEO");

            if (resultCode == RESULT_OK)
            {
            }
            else if (resultCode == RESULT_CANCELED)
            {
                // User cancelled the video capture
            }
            else
            {
                // Video capture failed, advise user
            }
        }
    }

}