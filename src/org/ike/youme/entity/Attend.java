package org.ike.youme.entity;



/**
 * Attend entity. @author MyEclipse Persistence Tools
 */

public class Attend  implements java.io.Serializable {


    // Fields    

     private Integer attendId;
     private Activity activity;
     private User user;


    // Constructors

    /** default constructor */
    public Attend() {
    }

    
    /** full constructor */
    public Attend(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

   
    // Property accessors

    public Integer getAttendId() {
        return this.attendId;
    }
    
    public void setAttendId(Integer attendId) {
        this.attendId = attendId;
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
   








}