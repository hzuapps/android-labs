package edu.hzuapps.androidworks.homeworks.net1314080903117;
/**
 * 用于显示微博消息的实体类
 * 实现了set和get方法
 * @author Charlie
 *
 */
public class Net1314080903117Message {
	private int messageIcon;//微博消息的头像
	private String messageUser;//微博消息发布的用户名
	private String messageTime;//微博消息发布的时间
	private String messageContent;//微博消息发布的内容
	public int getMessageIcon() {
		return messageIcon;
	}
	public void setMessageIcon(int messageIcon) {
		this.messageIcon = messageIcon;
	}
	public String getMessageUser() {
		return messageUser;
	}
	public void setMessageUser(String messageUser) {
		this.messageUser = messageUser;
	}
	public String getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	
	

}
