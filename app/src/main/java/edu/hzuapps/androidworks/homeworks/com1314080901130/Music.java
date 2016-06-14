package edu.hzuapps.androidworks.homeworks.com1314080901130;

public class Music {
	
	private String filename;
	private String Title;
	private int   duration;
	private	String artist;
	private String location;
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getData() {
		return location;
	}
	public void setData(String location) {
		this.location = location;
	}
	
	
	
//	{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, 
//		MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION,
//		MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA
}
