package edu.hzuapps.androidworks.homeworks.net1314080903145;

public class Net1314080903145_drawText1 {
int i;
double width;
double height;
double realwidth;
double realheight;
public Net1314080903145_drawText1(int i,double width,double height){
	this.i=i;
	this.width=width;
	this.height=height;
	if(height*13/21+Net1314080903145_LiveWallpaper.col*18>height||Net1314080903145_LiveWallpaper.col==Net1314080903145_LiveWallpaper.wordCount){
		Net1314080903145_LiveWallpaper.row++;
		Net1314080903145_LiveWallpaper.col=0;
	}
}
public String getText(){
	return Net1314080903145_LiveWallpaper.loveText.substring(i, i+1);
}
public float getX(){
	return 20+Net1314080903145_LiveWallpaper.row*25;
}
public float getY(){
	return (float)height*13/21+Net1314080903145_LiveWallpaper.col*18;
	
}
public int getLength(){
	return Net1314080903145_LiveWallpaper.loveText.length();
}
}
