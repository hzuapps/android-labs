package com.example.jsoupdemo;

public class Blog {
	// 博客头像的路径
	String bloggerIconUrl;
	// 博客头像的ID
	String bloggerId;

	public String getBloggerIconUrl() {
		return bloggerIconUrl;
	}

	public void setBloggerIconUrl(String bloggerIconUrl) {
		this.bloggerIconUrl = bloggerIconUrl;
	}

	public String getBloggerId() {
		return bloggerId;
	}

	public void setBloggerId(String bloggerId) {
		this.bloggerId = bloggerId;
	}
}
