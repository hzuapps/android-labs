package edu.hzuapps.androidworks.homeworks.net1314080903145;

public class Info {
String text;
float realwidth;
float realheight;
public info(String text, float realwidth, float realheight) {
	super();
	this.text = text;
	this.realwidth = realwidth;
	this.realheight = realheight;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public float getRealwidth() {
	return realwidth;
}
public void setRealwidth(float realwidth) {
	this.realwidth = realwidth;
}
public float getRealheight() {
	return realheight;
}
public void setRealheight(float realheight) {
	this.realheight = realheight;
}

}
