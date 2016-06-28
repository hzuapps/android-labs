package com.example.com1314080901203;

public class ContactList {
	
	private int imageID;
	private  String name;
	private int ID;
	private String nickName;
	
	public int getImageID(){return imageID;}
	public void setImageID(int imageID){this.imageID=imageID;}
	
	public int getID(){return ID;}
	public void setID(int ID){this.ID=ID;}
	
	public  String getName() {return name;}
	public void setName(String name) {  this.name = name; }
	
	public  String getNickname(){return nickName;}
	public void setNickname(String nickName){this.nickName=nickName;}
	
	public  ContactList(){}
	public  ContactList(int imageID,int ID,String name,String nickName){
		super();
		this.ID=ID;
		this.name = name;
		this.nickName=nickName;
		this.imageID=imageID;
	}
}
