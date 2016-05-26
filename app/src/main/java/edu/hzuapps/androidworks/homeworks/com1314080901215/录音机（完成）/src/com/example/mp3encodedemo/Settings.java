package com.example.mp3encodedemo;

import java.util.ArrayList;
import android.os.Environment;

public class Settings {
	
	public static String sdcardPath = Environment.getExternalStorageDirectory().toString();
	public static String recordingPath = sdcardPath + "/mp3_recording/";
	
	
	public static final int MSG_RECORDING_START = 1;
	public static final int MSG_RECORDING_STOP = 2;
	public static final int MSG_RECORDING_STATE_ERROR = 3;
	public static final int MSG_RECORDING_EXCEPTION = 4;
	public static final int MSG_RECORDING_RELEASE = 5;
	public static final int MSG_DRAW_ENDING = 6;
    public static final int MSG_FILE_EXCEPTION = 7;

}
