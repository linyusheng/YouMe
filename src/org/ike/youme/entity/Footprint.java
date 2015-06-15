package org.ike.youme.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Footprint entity. @author MyEclipse Persistence Tools
 */

public class Footprint  implements java.io.Serializable {


    // Fields    

     private Integer footprintId;
     private Activity activity;
     private User user;
     private String mood;
     private Timestamp createTime;
     private Set photos = new HashSet(0);


    // Constructors

    /** default constructor */
    public Footprint() {
    }

    
    /** full constructor */
    public Footprint(Activity activity, User user, String mood, Timestamp createTime, Set photos) {
        this.activity = activity;
        this.user = user;
        this.mood = mood;
        this.createTime = createTime;
        this.photos = photos;
    }

   
    // Property accessors

    public Integer getFootprintId() {
        return this.footprintId;
    }
    
    public void setFootprintId(Integer footprintId) {
        this.footprintId = footprintId;
    }

    public Activity getActivity() {
        return this.activity;
    }
    
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getMood() {
        return this.mood;
    }
    
    public void setMood(String mood) {
        this.mood = mood;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Set getPhotos() {
        return this.photos;
    }
    
    public void setPhotos(Set photos) {
        this.photos = photos;
    }
   








}