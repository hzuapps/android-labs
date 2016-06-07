package edu.hzuapps.androidworks.homeworks.net1314080903141;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Net1314080903141TrackActivity implements Runnable {

	// 播放暂存
	int out_size;
	// 放音对象
	AudioTrack track = null;

	public void run() {

	}

	// 播送方法
	public void track() {
		out_size = android.media.AudioTrack.getMinBufferSize(8000,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);

		track = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, out_size,
				AudioTrack.MODE_STREAM);
	}
}
