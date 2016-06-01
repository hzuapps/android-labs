package com.example.messagebrower.domain;

public class Messages {
	
	private String ToUserName;
	private String CreateTime;
	private String Content;
	private String imageUrl;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		this.ToUserName = toUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		this.CreateTime = createTime;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		this.Content = content;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "Messages [ToUserName=" + ToUserName + ", CreateTime="
				+ CreateTime + ", Content=" + Content + ", imageUrl="
				+ imageUrl + "]";
	}
	

	
}
