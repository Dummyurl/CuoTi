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
public class Question {
    @Id
    Long id;

    @Index(unique = true)
    String ques_id;

    @Property
    String ques_class;

    @Property
    String ques_from;

    @Property
    String understand;

    @Property
    String ueser;

    @Property
    String tip;

    @Property
    String reviewTime;

    @Property
    String label;

    @Property
    String date;

    @Property
    String queImg;

    @Property
    String ansImg;

    @Property
    String rightTime;

    @Property
    String wrongTime;

    public String getWrongTime() {
        return this.wrongTime;
    }

    public void setWrongTime(String wrongTime) {
        this.wrongTime = wrongTime;
    }

    public String getRightTime() {
        return this.rightTime;
    }

    public void setRightTime(String rightTime) {
        this.rightTime = rightTime;
    }

    public String getAnsImg() {
        return this.ansImg;
    }

    public void setAnsImg(String ansImg) {
        this.ansImg = ansImg;
    }

    public String getQueImg() {
        return this.queImg;
    }

    public void setQueImg(String queImg) {
        this.queImg = queImg;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getReviewTime() {
        return this.reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getTip() {
        return this.tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUeser() {
        return this.ueser;
    }

    public void setUeser(String ueser) {
        this.ueser = ueser;
    }

    public String getUnderstand() {
        return this.understand;
    }

    public void setUnderstand(String understand) {
        this.understand = understand;
    }

    public String getQues_from() {
        return this.ques_from;
    }

    public void setQues_from(String ques_from) {
        this.ques_from = ques_from;
    }

    public String getQues_class() {
        return this.ques_class;
    }

    public void setQues_class(String ques_class) {
        this.ques_class = ques_class;
    }

    public String getQues_id() {
        return this.ques_id;
    }

    public void setQues_id(String ques_id) {
        this.ques_id = ques_id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 2093724157)
    public Question(Long id, String ques_id, String ques_class, String ques_from,
            String understand, String ueser, String tip, String reviewTime,
            String label, String date, String queImg, String ansImg,
            String rightTime, String wrongTime) {
        this.id = id;
        this.ques_id = ques_id;
        this.ques_class = ques_class;
        this.ques_from = ques_from;
        this.understand = understand;
        this.ueser = ueser;
        this.tip = tip;
        this.reviewTime = reviewTime;
        this.label = label;
        this.date = date;
        this.queImg = queImg;
        this.ansImg = ansImg;
        this.rightTime = rightTime;
        this.wrongTime = wrongTime;
    }

    @Generated(hash = 1868476517)
    public Question() {
    }
}
