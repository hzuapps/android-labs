package com.example.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ObjectFile {
	private String path = "/sdcard/word/word.txt";

	public File createFile() {
		File file = new File(path);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}

	public Object readObjectFile() {
		ObjectInputStream mis = null;
		Object mObject = null;
		File file = new File(path);
		if (!file.exists()) {
			return null;
		} else {
			try {
				mis = new ObjectInputStream(new FileInputStream(file));
				mObject = mis.readObject();
				mis.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mObject;
		}
	}
	public void DeleteObjectFile(Object object){
		
		
	}

	public boolean writeObjectFile(Object object) {
		ObjectOutputStream mos = null;
		try {
			mos = new ObjectOutputStream(new FileOutputStream(createFile()));
			mos.writeObject(object);
			mos.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
