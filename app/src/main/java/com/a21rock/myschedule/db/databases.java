package com.a21rock.myschedule.db;

import com.a21rock.myschedule.bean.Course;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 21rock on 2017/2/28.
 */

public class Databases {

    // 获取一个星期中指定的一天的课程信息
    public List<Course> getDayCourse(int day) {
        List<Course> courseList = DataSupport
                .where("day = ?", String.valueOf(day))
                .order("lesson asc")
                .find(Course.class);
        return courseList;
    }

    public void editCourse(int id){}

    public void deleteCourse(int id) {
        DataSupport.delete(Course.class, id);
    }
}
