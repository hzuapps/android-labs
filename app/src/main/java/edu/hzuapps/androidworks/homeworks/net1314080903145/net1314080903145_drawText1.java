package edu.hzuapps.androidworks.homeworks.net1314080903145;

public class net1314080903145_drawText1 {
int i;
double width;
double height;
double realwidth;
double realheight;
public net1314080903145_drawText1(int i,double width,double height){
	this.i=i;
	this.width=width;
	this.height=height;
	if(height*13/21+net1314080903145_LiveWallpaper.col*18>height||net1314080903145_LiveWallpaper.col==net1314080903145_LiveWallpaper.wordCount){
		net1314080903145_LiveWallpaper.row++;
		net1314080903145_LiveWallpaper.col=0;
	}
}
public String getText(){
	return net1314080903145_LiveWallpaper.loveText.substring(i, i+1);
}
public float getX(){
	return 20+net1314080903145_LiveWallpaper.row*25;
}
public float getY(){
	return (float)height*13/21+net1314080903145_LiveWallpaper.col*18;
	
}
public int getLength(){
	return net1314080903145_LiveWallpaper.loveText.length();
}
}
