package org.ike.youme.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User  implements java.io.Serializable {


    // Fields    

     private Integer userId;
     private String account;
     private String pwd;
     private String nickname;
     private String head;
     private Integer age;
     private String sex;
     private String name;
     private String sign;
     private String city;
     private Timestamp createTime;
     private Set comments = new HashSet(0);
     private Set activities = new HashSet(0);
     private Set browses = new HashSet(0);
     private Set collects = new HashSet(0);
     private Set footprints = new HashSet(0);
     private Set attends = new HashSet(0);


    // Constructors

    /** default constructor */
    public User() {
    }

    
    /** full constructor */
    public User(String account, String pwd, String nickname, String head, Integer age, String sex, String name, String sign, String city, Timestamp createTime, Set comments, Set activities, Set browses, Set collects, Set footprints, Set attends) {
        this.account = account;
        this.pwd = pwd;
        this.nickname = nickname;
        this.head = head;
        this.age = age;
        this.sex = sex;
        this.name = name;
        this.sign = sign;
        this.city = city;
        this.createTime = createTime;
        this.comments = comments;
        this.activities = activities;
        this.browses = browses;
        this.collects = collects;
        this.footprints = footprints;
        this.attends = attends;
    }

   
    // Property accessors

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return this.account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return this.pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return this.head;
    }
    
    public void setHead(String head) {
        this.head = head;
    }

    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return this.sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Set getComments() {
        return this.comments;
    }
    
    public void setComments(Set comments) {
        this.comments = comments;
    }

    public Set getActivities() {
        return this.activities;
    }
    
    public void setActivities(Set activities) {
        this.activities = activities;
    }

    public Set getBrowses() {
        return this.browses;
    }
    
    public void setBrowses(Set browses) {
        this.browses = browses;
    }

    public Set getCollects() {
        return this.collects;
    }
    
    public void setCollects(Set collects) {
        this.collects = collects;
    }

    public Set getFootprints() {
        return this.footprints;
    }
    
    public void setFootprints(Set footprints) {
        this.footprints = footprints;
    }

    public Set getAttends() {
        return this.attends;
    }
    
    public void setAttends(Set attends) {
        this.attends = attends;
    }
   








}