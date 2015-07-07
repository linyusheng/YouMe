package org.ike.youme.model;

public class EActivity {
	
	private Integer activityId;		//活动Id
    private Integer typeId;			//活动类型Id
    private String title;			//活动标题
    private String city;			//活动城市
    private String place;			//详细地点
    private String poster;			//活动海报
    private String description;		//活动描述
    private Double latitude;		//纬度
    private Double longitude;		//经度
    private String createTime;		//活动发布时间
    private String startTime;		//活动开始时间
    private String endTime;			//活动结束时间
    private Integer maleNum;		//活动男性人数
    private Integer femaleNum;		//活动女性人数
    private Integer attendNum;		//活动参与人数
    private Integer browseNum;		//活动浏览人数
    private Integer userId;			//发布者ID
    private String account;			//发布者账号
    private String nickname;		//发布者昵称
    private String head;			//发布者头像
    
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getMaleNum() {
		return maleNum;
	}
	public void setMaleNum(Integer maleNum) {
		this.maleNum = maleNum;
	}
	public Integer getFemaleNum() {
		return femaleNum;
	}
	public void setFemaleNum(Integer femaleNum) {
		this.femaleNum = femaleNum;
	}
	public Integer getAttendNum() {
		return attendNum;
	}
	public void setAttendNum(Integer attendNum) {
		this.attendNum = attendNum;
	}
	public Integer getBrowseNum() {
		return browseNum;
	}
	public void setBrowseNum(Integer browseNum) {
		this.browseNum = browseNum;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
    
}
