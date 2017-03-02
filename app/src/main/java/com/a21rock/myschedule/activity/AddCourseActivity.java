package com.a21rock.myschedule.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a21rock.myschedule.R;
import com.a21rock.myschedule.bean.Course;
import com.a21rock.myschedule.bean.Note;
import com.a21rock.myschedule.utils.DateUtil;
import com.a21rock.myschedule.utils.LogUtil;
import com.a21rock.myschedule.utils.SharedPreferencesUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    private EditText edTitle;
    private EditText edPlace;
    private EditText edLesson;
    private EditText edWeek;
    private EditText edDay;

    private int id; // 要修改的课程的id
    private Boolean isEdit;  // 如果为true为编辑课程，false为添加课程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initToolbar();
        Intent intent = getIntent();
        LogUtil.d("ddddddddddddddddd", intent.getBooleanExtra("isEdit", false) + "");
        edTitle = (EditText) findViewById(R.id.edit_course_title);
        edPlace = (EditText) findViewById(R.id.edit_course_place);
        edLesson = (EditText) findViewById(R.id.edit_course_lesson);
        edWeek = (EditText) findViewById(R.id.edit_course_week);
        edDay = (EditText) findViewById(R.id.edit_course_day);
        isEdit = intent.getBooleanExtra("isEdit", false);
        if (isEdit) {
            id = SharedPreferencesUtil.getIdFromSharedPreferences(AddCourseActivity.this);
            LogUtil.d("id", id + "");
            List<Course> CourseList = DataSupport.where("id = ?", String.valueOf(id)).find(Course.class);
            Course course = CourseList.get(0);
            LogUtil.d("course", course.getTitle());
            edTitle.setText(course.getTitle());
            edPlace.setText(course.getPlace());
            edLesson.setText(String.valueOf(course.getLesson()));
            edWeek.setText(course.getWeek());
            edDay.setText(String.valueOf(course.getDay()));
        }
    }

//    public static void actionStart(Context context) {
//        Intent intent = new Intent(context, AddCourseActivity.class);
//        context.startActivity(intent);
//    }

    // 如果isEdit为false说明是添加课程，否则为修改课程
    public static void actionStart(Context context, boolean isEdit) {
        Intent intent = new Intent(context, AddCourseActivity.class);
        intent.putExtra("isEdit", isEdit);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_course_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToMainActivity();
                finish();
                break;
            case R.id.menu_save:
                if (isEdit) {
                    ContentValues values = new ContentValues();
                    values.put("title", edTitle.getText().toString());
                    values.put("place", edPlace.getText().toString());
                    values.put("lesson", edLesson.getText().toString());
                    values.put("week", edWeek.getText().toString());
                    values.put("day", edDay.getText().toString());
                    DataSupport.update(Course.class, values, id);
                    returnToMainActivity();
                    finish();
                } else {
                    Boolean hasCourse = hasDuplicateCourse(Integer.parseInt(edLesson.getText().toString()), Integer.parseInt(edDay.getText().toString()));
                    if (hasCourse) {
                        Toast.makeText(getApplicationContext(), "在指定星期的那节课已经添加过了，可以在别的位置添加课程或者删除原有的课程", Toast.LENGTH_SHORT).show();
                        cleanLessonAndDayEditTextData();
                    } else {
                        saveCourse();
                        returnToMainActivity();
                        finish();
                    }
                }
                break;
            default:
        }
        return true;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_course);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void saveCourse() {
        Course course = new Course(
                edTitle.getText().toString(),
                edPlace.getText().toString(),
                Integer.parseInt(edLesson.getText().toString()),
                edWeek.getText().toString(),
                Integer.parseInt(edDay.getText().toString()));
        course.save();
    }

    // 如果在想要添加课程的时间已经被添加了课程会返回true
    private boolean hasDuplicateCourse(int lesson, int day) {
        Cursor cursor = DataSupport.findBySQL("select * from Course where lesson = " + String.valueOf(lesson)
                + " and day = " + String.valueOf(day));
        if (cursor.moveToFirst() == true) {
            return true;
        } else {
            return false;
        }
    }

    private void cleanLessonAndDayEditTextData() {
        edLesson.setText("");
        edDay.setText("");
    }
}
