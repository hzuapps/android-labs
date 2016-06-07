package edu.hzuapps.androidworks.homeworks.net1314080903141;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

public class Net1314080903141RunActivity implements Runnable {
	// 是否录放的标记
	boolean isRecording = false;
	static final int frequency = 44100;
	@SuppressWarnings("deprecation")
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	// 定义录放音缓冲区大小
	int recBufSize, playBufSize;
	// 实例化录音对象
	AudioRecord audioRecord;
	// 实例化播放对象
	AudioTrack audioTrack;

	public Net1314080903141RunActivity() {
		// 调用getMinBufferSize方法获得录音的最小缓冲空间
		recBufSize = AudioRecord.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);

		// 调用getMinBufferSize方法获得放音最小的缓冲区大小
		playBufSize = AudioTrack.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);
		// -----------------------------------------、
		// 调用构造函数实例化录音对象
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
				channelConfiguration, audioEncoding, recBufSize);

		// 调用构造函数实例化放音对象
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,
				channelConfiguration, audioEncoding, playBufSize,
				AudioTrack.MODE_STREAM);
	}

	// 线程体
	public void run() {

		try {
			byte[] buffer = new byte[recBufSize];
			audioRecord.startRecording();// 开始录制
			audioTrack.play();// 开始播放
			this.isRecording = true;
			while (isRecording) {
				// 从MIC保存数据到缓冲区
				int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);

				byte[] tmpBuf = new byte[bufferReadResult];
				System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
				// 写入数据就播放
				audioTrack.write(tmpBuf, 0, tmpBuf.length);
			}
			audioTrack.stop();
			audioRecord.stop();
		} catch (Throwable t) {
		}
	}

	// 停止方法
	public void no() {
		isRecording = false;
	}

}
