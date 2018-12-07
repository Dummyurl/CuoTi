package com.chinastis.cuoti.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xianglong on 2018/12/5.
 */

@Entity
public class User {
    @Id
    Long id;

    @Index(unique = true)
    String userId;

    @Property
    String password;

    @Property
    String grade;

    @Property
    String name;

    @Property
    String phone;

    @Property
    String mobile;

    @Property
    String qq;

    @Property
    String year;

    @Property
    String school;

    @Property
    String classNum;

    @Property
    String mail;

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClassNum() {
        return this.classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 736802123)
    public User(Long id, String userId, String password, String grade, String name,
            String phone, String mobile, String qq, String year, String school,
            String classNum, String mail) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.grade = grade;
        this.name = name;
        this.phone = phone;
        this.mobile = mobile;
        this.qq = qq;
        this.year = year;
        this.school = school;
        this.classNum = classNum;
        this.mail = mail;
    }

    @Generated(hash = 586692638)
    public User() {
    }



    
}
