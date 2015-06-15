package org.ike.youme.model;

public class EComment {
	
	private Integer commentId;
    private Integer userId;
    private Integer activitiesId;
    private String commentContent;
    
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getActivitiesId() {
		return activitiesId;
	}
	public void setActivitiesId(Integer activitiesId) {
		this.activitiesId = activitiesId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

    
}
