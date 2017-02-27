package com.a21rock.myschedule.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 21rock on 2017/2/20.
 */

public class Course extends DataSupport {

    private int id;

    private String title;  // 课程名称

    private String place;  // 地点

    private String time;     // 时间

    private int week;      // 周次

    private String color; // 背景色

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
