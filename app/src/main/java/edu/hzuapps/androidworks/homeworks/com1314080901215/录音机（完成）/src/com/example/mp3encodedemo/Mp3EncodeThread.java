package com.example.mp3encodedemo;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import android.os.Handler;

public class Mp3EncodeThread extends Thread {

	private BlockingQueue<short[]> recordQueue;	
	private Handler handler;	
	private static final long TIME_WAIT_RECORDING = 100;
	private volatile boolean setToStopped = false;
	private JNIMp3Encode mp3Encode = new JNIMp3Encode();
	private int channel = 1;
	private int sampleRate = 16000;
	private int brate = 128;
	
	public Mp3EncodeThread(Handler handler, BlockingQueue<short[]> recordQueue) {
		this.recordQueue = recordQueue;
		this.handler = handler;
	}


	public void stopMp3Encode(){
		setToStopped = true;
	}
	

	@Override
	public void run() {
		
		mp3Encode.init(channel, sampleRate, brate);
		FileOutputStream out = null;
		
		try {
			
			out = new FileOutputStream(Settings.recordingPath  + "recording.mp3");
			short[] queueHeadBuffer = null;
			while(true)	{
				
				queueHeadBuffer = recordQueue.poll(TIME_WAIT_RECORDING, TimeUnit.MILLISECONDS);				
				if(queueHeadBuffer != null) {
					byte[] mp3Datas = mp3Encode.encode(queueHeadBuffer, queueHeadBuffer.length);
					out.write(mp3Datas);
				}
				
				if(setToStopped	&& recordQueue.size()==0) {
					break;
				}
			}
						
			out.close();
			mp3Encode.destroy();
			
		} catch (Exception e) {
			
		}
	}
	
}
