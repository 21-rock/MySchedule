package com.a21rock.myschedule.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 21rock on 2017/2/20.
 */

public class Course extends DataSupport {

    private int id;

    private String title; // 课程名称

    private String place; // 地点

    private int lesson; // 第几节课，每天最多有五大节课

    private String week; // 周次

    private String color; // 背景色

    private int day; // 星期几

    public Course(String title, String place, int lesson, String week, int day) {
        this.title = title;
        this.place = place;
        this.lesson = lesson;
        this.week = week;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
