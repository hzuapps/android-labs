package Ld.videoPlayer;

import java.io.File;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
    /** Called when the activity is first created. */
	VideoView videoView;
	private String fielname;
	MediaController mediaController;
	EditText editText;
	Button openButton;
	Button qiutButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //public Window getWindow () 返回当前activity的window
        //void android.view.Window.setFormat(int format)
        //public void setFormat (int format)  设置窗口的像素格式
        //PixelFormat.TRANSLUCENT 透明格式
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.main);
        videoView=(VideoView)findViewById(R.id.video);
        editText=(EditText)findViewById(R.id.edittext);
        openButton=(Button)findViewById(R.id.starttoplay);
        openButton.setOnClickListener(new startlistener());
        qiutButton=(Button)findViewById(R.id.quit);
        qiutButton.setOnClickListener(new qiutlistener());
        mediaController=new MediaController(this);
        /*
        File video=new File("/sdcard/re.mp4");
        //若文件被找到
        if(video.exists()){
        	videoView.setVideoPath(video.getAbsolutePath());//文件绝对路径
        	videoView.setMediaController(mediaController);//设置videoView与mediaControler的关联
        	mediaController.setMediaPlayer(videoView);
        	videoView.requestFocus();
        }
        */
    }
    class startlistener implements OnClickListener{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			fielname=editText.getText().toString();
			System.out.println(fielname);
			//File video=new File("/sdcard/re.mp4");
			File video=new File(fielname);
	        //若文件被找到
	        if(video.exists()){
	        	videoView.setVideoPath(video.getAbsolutePath());//文件绝对路径
	        	videoView.setMediaController(mediaController);//设置videoView与mediaControler的关联
	        	mediaController.setMediaPlayer(videoView);
	        	videoView.requestFocus();
	        }
	        else{
				 Toast.makeText(VideoPlayerActivity.this,"很抱歉，您输入的文件不存在，请重新输入", Toast.LENGTH_LONG).show();
	        }
			
		}
    }
    class qiutlistener implements OnClickListener{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
    }
}


