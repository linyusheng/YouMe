package org.ike.youme.entity;

import java.util.HashSet;
import java.util.Set;


/**
 * Type entity. @author MyEclipse Persistence Tools
 */

public class Type  implements java.io.Serializable {


    // Fields    

     private Integer typeId;
     private String typeName;
     private Set activities = new HashSet(0);


    // Constructors

    /** default constructor */
    public Type() {
    }

    
    /** full constructor */
    public Type(String typeName, Set activities) {
        this.typeName = typeName;
        this.activities = activities;
    }

   
    // Property accessors

    public Integer getTypeId() {
        return this.typeId;
    }
    
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set getActivities() {
        return this.activities;
    }
    
    public void setActivities(Set activities) {
        this.activities = activities;
    }
   








}