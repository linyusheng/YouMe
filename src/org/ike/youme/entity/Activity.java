package org.ike.youme.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Activity entity. @author MyEclipse Persistence Tools
 */

public class Activity  implements java.io.Serializable {


    // Fields    

     private Integer activityId;
     private Type type;
     private User user;
     private String title;
     private String city;
     private String place;
     private String poster;
     private String description;
     private Double latitude;
     private Double longitude;
     private Timestamp createTime;
     private Timestamp startTime;
     private Timestamp endTime;
     private Integer attendNum;
     private Integer browseNum;
     private Set collects = new HashSet(0);
     private Set comments = new HashSet(0);
     private Set browses = new HashSet(0);
     private Set attends = new HashSet(0);
     private Set footprints = new HashSet(0);


    // Constructors

    /** default constructor */
    public Activity() {
    }

    
    /** full constructor */
    public Activity(Type type, User user, String title, String city, String place, String poster, String description, Double latitude, Double longitude, Timestamp createTime, Timestamp startTime, Timestamp endTime, Integer attendNum, Integer browseNum, Set collects, Set comments, Set browses, Set attends, Set footprints) {
        this.type = type;
        this.user = user;
        this.title = title;
        this.city = city;
        this.place = place;
        this.poster = poster;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendNum = attendNum;
        this.browseNum = browseNum;
        this.collects = collects;
        this.comments = comments;
        this.browses = browses;
        this.attends = attends;
        this.footprints = footprints;
    }

   
    // Property accessors

    public Integer getActivityId() {
        return this.activityId;
    }
    
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Type getType() {
        return this.type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace() {
        return this.place;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }

    public String getPoster() {
        return this.poster;
    }
    
    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getAttendNum() {
        return this.attendNum;
    }
    
    public void setAttendNum(Integer attendNum) {
        this.attendNum = attendNum;
    }

    public Integer getBrowseNum() {
        return this.browseNum;
    }
    
    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Set getCollects() {
        return this.collects;
    }
    
    public void setCollects(Set collects) {
        this.collects = collects;
    }

    public Set getComments() {
        return this.comments;
    }
    
    public void setComments(Set comments) {
        this.comments = comments;
    }

    public Set getBrowses() {
        return this.browses;
    }
    
    public void setBrowses(Set browses) {
        this.browses = browses;
    }

    public Set getAttends() {
        return this.attends;
    }
    
    public void setAttends(Set attends) {
        this.attends = attends;
    }

    public Set getFootprints() {
        return this.footprints;
    }
    
    public void setFootprints(Set footprints) {
        this.footprints = footprints;
    }
   








}