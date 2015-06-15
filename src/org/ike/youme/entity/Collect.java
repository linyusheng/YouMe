package org.ike.youme.entity;

import java.sql.Timestamp;


/**
 * Collect entity. @author MyEclipse Persistence Tools
 */

public class Collect  implements java.io.Serializable {


    // Fields    

     private Integer collectId;
     private Activity activity;
     private User user;
     private Timestamp createTime;


    // Constructors

    /** default constructor */
    public Collect() {
    }

    
    /** full constructor */
    public Collect(Activity activity, User user, Timestamp createTime) {
        this.activity = activity;
        this.user = user;
        this.createTime = createTime;
    }

   
    // Property accessors

    public Integer getCollectId() {
        return this.collectId;
    }
    
    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
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

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
   








}