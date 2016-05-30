package edu.hzuapps.androidworks.homeworks.net1314080903145;

public class DrawText1 {
int i;
double width;
double height;
double realwidth;
double realheight;
public drawText1(int i,double width,double height){
	this.i=i;
	this.width=width;
	this.height=height;
	if(height*13/21+LiveWallpaper.col*18>height||LiveWallpaper.col==LiveWallpaper.wordCount){
		LiveWallpaper.row++;
		LiveWallpaper.col=0;
	}
}
public String getText(){
	return LiveWallpaper.loveText.substring(i, i+1);
}
public float getX(){
	return 20+LiveWallpaper.row*25;
}
public float getY(){
	return (float)height*13/21+LiveWallpaper.col*18;
	
}
public int getLength(){
	return LiveWallpaper.loveText.length();
}
}
