package com1314080901144.sgf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.com1314080901144.Board;
import com.example.com1314080901144.PieceProcess;
import com.example.com1314080901144.util.Coordinate;

import net.mindview.util.TextFile;

/**
 * 读取Sgf文件
 */
public class SgfHelper {
	private static final int ACode = (int) 'a';

	public static List<Coordinate> getCoordList(String fileName)
			throws IOException {
		String str = TextFile.read(fileName);
		List<Coordinate> cs = new ArrayList<Coordinate>();
		if (str == "")
			return cs;

		String data = str.substring(0, str.indexOf(")"));
		String[] ds = data.split(";");

		for (String s : ds) {
			if (s == "")
				continue;

			String ns = s.toLowerCase();

			if (ns.length() > 5
					&& (ns.charAt(0) == 'b' || ns.charAt(0) == 'w')) {
				char xChar = ns.charAt(2);
				char yChar = ns.charAt(3);

				int x = (int) xChar - ACode;
				int y = (int) yChar - ACode;
				Coordinate c = new Coordinate(x, y);
				cs.add(c);
			}
		}
		return cs;
	}

	public static void save(Board b, String fileName) throws IOException {
		if(b.getCount()==0)return;
		
		TextFile textFile = new TextFile();
		textFile.add("(;US[ChunGo]");
		for (int i = 0, n = b.getCount(); i < n; i++) {
			PieceProcess p = b.getPieceProcess(i);
			textFile.add(Coordinate2String(p));
		}
		textFile.add(")");
		
		String dir="/sdcard/ChunGo";
		File file=new File(dir);
		if(!file.exists()){
			file.mkdir();
		}
		
		fileName=dir+"/"+fileName;
		textFile.write(fileName);
	}

	private static String Coordinate2String(PieceProcess p) {
		String s = ";" +pw2String(p.bw)+ "["+int2String(p.c.x) + int2String(p.c.y)+"]";
		return s;
	}

	private static String int2String(int a) {
		int code = a + ACode;
		char c = (char) code;
		return String.valueOf(c);
	}
	private static String pw2String(int bw){
		if(bw==Board.Black)return "B";
		if(bw==Board.White)return "W";
		return "X";
	}
}
