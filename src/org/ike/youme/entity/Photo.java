package org.ike.youme.entity;



/**
 * Photo entity. @author MyEclipse Persistence Tools
 */

public class Photo  implements java.io.Serializable {


    // Fields    

     private Integer photoId;
     private Footprint footprint;
     private String photoUrl;


    // Constructors

    /** default constructor */
    public Photo() {
    }

    
    /** full constructor */
    public Photo(Footprint footprint, String photoUrl) {
        this.footprint = footprint;
        this.photoUrl = photoUrl;
    }

   
    // Property accessors

    public Integer getPhotoId() {
        return this.photoId;
    }
    
    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public Footprint getFootprint() {
        return this.footprint;
    }
    
    public void setFootprint(Footprint footprint) {
        this.footprint = footprint;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
   








}