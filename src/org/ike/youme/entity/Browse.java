package org.ike.youme.entity;



/**
 * Browse entity. @author MyEclipse Persistence Tools
 */

public class Browse  implements java.io.Serializable {


    // Fields    

     private Integer browseId;
     private Activity activity;
     private User user;


    // Constructors

    /** default constructor */
    public Browse() {
    }

    
    /** full constructor */
    public Browse(Activity activity, User user) {
        this.activity = activity;
        this.user = user;
    }

   
    // Property accessors

    public Integer getBrowseId() {
        return this.browseId;
    }
    
    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
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