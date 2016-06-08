package edu.hzuapps.androidworks.homeworks.net1314080903147;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class Net1314080903147ReadFile {

	public static byte[] getFileFromUrl(String url, Net1314080903147NetWorkSpeedInfo net1314080903147NetWorkSpeedInfo) {
		int currentByte = 0;
		int fileLength = 0;
		long startTime = 0;
		long intervalTime = 0;

		byte[] b = null;

		int bytecount = 0;
		URL urlx = null;
		URLConnection con = null;
		InputStream stream = null;
		try {
			Log.d("URL:", url);
			urlx = new URL(url);
			con = urlx.openConnection();
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);
			fileLength = con.getContentLength();
			stream = con.getInputStream();
			net1314080903147NetWorkSpeedInfo.totalBytes = fileLength;
			b = new byte[fileLength];
			startTime = System.currentTimeMillis();
			while ((currentByte = stream.read()) != -1) {
				net1314080903147NetWorkSpeedInfo.hadFinishedBytes++;
				intervalTime = System.currentTimeMillis() - startTime;
				if (intervalTime == 0) {
					net1314080903147NetWorkSpeedInfo.speed = 1000;
				} else {
					net1314080903147NetWorkSpeedInfo.speed = (net1314080903147NetWorkSpeedInfo.hadFinishedBytes / intervalTime) * 1000;
				}
				if (bytecount < fileLength) {
					b[bytecount++] = (byte) currentByte;
				}
			}
		} catch (Exception e) {
			Log.e("exception : ", e.getMessage() + "");
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (Exception e) {
				Log.e("exception : ", e.getMessage());
			}

		}
		return b;
	}

}
