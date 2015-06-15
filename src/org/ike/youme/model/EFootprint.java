package org.ike.youme.model;

import java.util.List;

public class EFootprint {
	
	private Integer footprintId;	//足迹Id
	private Integer activityId;		//活动Id
	private Integer userId;			//用户Id
	private String nickname;		//用户昵称
	private String head;			//用户头像
	private String mood;			//心情
	private String createTime;		//足迹时间
	private List<String> photos;	//足迹图片
	
	public Integer getFootprintId() {
		return footprintId;
	}
	public void setFootprintId(Integer footprintId) {
		this.footprintId = footprintId;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	
}
