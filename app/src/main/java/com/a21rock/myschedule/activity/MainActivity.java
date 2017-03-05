package com.a21rock.myschedule.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.a21rock.myschedule.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.a21rock.myschedule.bean.Course;
import com.a21rock.myschedule.core.ActivityCollector;
import com.a21rock.myschedule.db.Databases;
import com.a21rock.myschedule.service.MyService;
import com.a21rock.myschedule.service.RemindClassService;
import com.a21rock.myschedule.utils.DateUtil;
import com.a21rock.myschedule.utils.LogUtil;
import com.a21rock.myschedule.utils.SharedPreferencesUtil;
import com.a21rock.myschedule.utils.ViewUtil;


public class MainActivity extends BaseActivity {


    private int colors[] = {
            Color.rgb(0xee, 0xff, 0xff),
            Color.rgb(0xf0, 0x96, 0x09),
            Color.rgb(0x8c, 0xbf, 0x26),
            Color.rgb(0x00, 0xab, 0xa9),
            Color.rgb(0x99, 0x6c, 0x33),
            Color.rgb(0x3b, 0x92, 0xbc),
            Color.rgb(0xd5, 0x4d, 0x34),
            Color.rgb(0xcc, 0xcc, 0xcc)
    };

    private DrawerLayout mDrawerLayout;
    private Context mContext;

    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;
    private LinearLayout ll5;
    private LinearLayout ll6;
    private LinearLayout ll7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        initToolbar();
        initDrawerLayout();
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        updateScheduleView();
        LitePal.getDatabase();
        Boolean isRemindClass = SharedPreferencesUtil.getRemindClassFlag(this);
        if (isRemindClass) {
            Intent StartServiceIntent = new Intent(this, RemindClassService.class);
            startService(StartServiceIntent);
        }
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        // 点击侧滑栏子项时的逻辑,这里以后会跳转到指定的activity
        navView.setCheckedItem(R.id.nav_note);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_note:
                        NoteActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.nav_friends:
                        View view = ViewUtil.getRootView(MainActivity.this);
                        Snackbar.make(view, "好友功能正在开发中,敬请期待...", Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_logout:
                        // 退出
                        ActivityCollector.finishAll();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView) findViewById(R.id.toolbar_schedule_title);
        textView.setText("课程表");
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 隐藏ToolBar标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 设置一个导航按钮图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    // 把课程填充到界面
    private void displayCourse(List<Course> courseList, LinearLayout linearLayout) {
        int count = 0;
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            if (count > 0) {
                if (course.getLesson() >= 2) {
                    for (int x = 1; x < course.getLesson() - count; x++) {
                        setNoClass(linearLayout, 2, 0);
                    }
                }
            } else {
                if (course.getLesson() >= 2) {
                    for (int y = 1; y < course.getLesson(); y++) {
                        setNoClass(linearLayout, 2, 0);
                        ++count;
                    }
                }
            }
            setClass(linearLayout, course.getId(), course.getTitle(), course.getPlace(), course.getWeek());
            ++count;
        }
    }

    /**
     * 设置课程的方法
     *
     * @param ll
     * @param title 课程名称
     * @param place 地点
     * @param week  周次
     */
    private void setClass(LinearLayout ll, int id, String title, String place, String week) {
        View view = LayoutInflater.from(this).inflate(R.layout.course_item, null);
        int height = ViewUtil.dip2px(this, 100);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(params);
        Random rand = new Random();
        // 生成随机颜色，除了colors数组的第一项(白色)
        view.setBackgroundColor(colors[rand.nextInt(colors.length - 1) + 1]);
        // 这里必须要把int类型的id转为String类型,不然会报错
        ((TextView) view.findViewById(R.id.course_id)).setText(String.valueOf(id));
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.place)).setText(place);
        ((TextView) view.findViewById(R.id.week)).setText(week);
        //为课程View设置长按监听器
        view.setOnLongClickListener(new OnLongClickClassListener());
        ll.addView(view);
    }

    class OnLongClickClassListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            // 每次长按把相应的课程表id存入SharedPreferences
            String id = (String)((TextView)v.findViewById(R.id.course_id)).getText();
            SharedPreferencesUtil.setIdToSharedPreferences(MainActivity.this, Integer.parseInt(id));
            initPopWindow(v);
            return true;
        }
    }

    /**
     * 设置无课（空白）
     *
     * @param ll
     * @param classes 无课的节数（长度）
     * @param color
     */
    void setNoClass(LinearLayout ll, int classes, int color) {
        TextView blank = new TextView(this);
        if (color == 0)
            blank.setMinHeight(ViewUtil.dip2px(this, classes * 50));
        else
            blank.setBackgroundColor(colors[color]);
        ll.addView(blank);
    }


    private void initPopWindow(View v) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
        Button btnEditCourse = (Button) view.findViewById(R.id.btn_edit_course);
        Button btnDeleteCourse = (Button) view.findViewById(R.id.btn_delete_course);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画
        popWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);
        backgroundAlpha(0.5f);
        popWindow.setElevation(8);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        //设置popupWindow里的按钮的事件
        btnEditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了修改课程~", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
                AddCourseActivity.actionStart(MainActivity.this, true);
            }
        });
        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = SharedPreferencesUtil.getIdFromSharedPreferences(MainActivity.this);
                Databases db = new Databases();
                db.deleteCourse(id);
                updateScheduleView();
                popWindow.dismiss();
            }
        });
    }

    // 更新课程表视图
    private void updateScheduleView() {
        Databases db = new Databases();
        ll1.removeAllViews();
        ll2.removeAllViews();
        ll3.removeAllViews();
        ll4.removeAllViews();
        ll5.removeAllViews();
        ll6.removeAllViews();
        ll7.removeAllViews();
        List<Course> courseList = db.getDayCourse(1);
        displayCourse(courseList, ll1);
        List<Course> courseList1 = db.getDayCourse(2);
        displayCourse(courseList1, ll2);
        List<Course> courseList2 = db.getDayCourse(3);
        displayCourse(courseList2, ll3);
        List<Course> courseList3 = db.getDayCourse(4);
        displayCourse(courseList3, ll4);
        List<Course> courseList4 = db.getDayCourse(5);
        displayCourse(courseList4, ll5);
        List<Course> courseList5 = db.getDayCourse(6);
        displayCourse(courseList5, ll6);
        List<Course> courseList6 = db.getDayCourse(7);
        displayCourse(courseList6, ll7);
    }


    // 初始化toolbar按钮
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    // 当toolbar上的按钮被点击时调用
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.setting:
                SettingActivity.actionStart(MainActivity.this);
                break;
            case R.id.add_course:
                LogUtil.d("add_course", "调用了add_course");
                AddCourseActivity.actionStart(MainActivity.this, false);
                finish();
                break;
        }
        return true;
    }

    // 当重新回到应用时不会调用欢迎界面(SplashActivity)
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
