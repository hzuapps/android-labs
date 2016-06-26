package edu.hzuapps.androidworks.homeworks.com1314080901137;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class MapFactory {
	public static int[][] a = new int[World.World_Height][World.World_Width];
	
	public static int[][] createMap(FileIO file, int level) {
		String fileName = "map" + level + ".txt";
		InputStream in = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			in = file.readAsset(fileName);
			reader = new InputStreamReader(in);
			br = new BufferedReader(reader);
			String string = null;
			int j = 0;
			while ((string = br.readLine()) != null && j < World.World_Height) {
				char[] num = string.toCharArray();
				for (int i = 0; i < World.World_Width; i++) {
					a[j][i] = num[i] - '0';
				}
				j++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(in!=null)
						in.close();
					if(reader!=null)
						reader.close();
					if(br!=null)
						br.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
		}
		return a;
	}
}