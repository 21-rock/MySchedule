package com.a21rock.myschedule.activity;

import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.a21rock.myschedule.R;

import org.litepal.LitePal;

import java.util.Random;

import com.a21rock.myschedule.core.ActivityCollector;
import com.a21rock.myschedule.utils.LogUtil;
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
        initData();
        LitePal.getDatabase();
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


    /* 初始化课程表数据，后续会改成从数据库获取 */
    private void initData() {
        //分别表示周一到周日
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        //每天的课程设置
        setNoClass(ll1, 2, 0);
        setClass(ll1, "windows编程实践", "国软  4-503", "1-9周，每一周", "9:50-11:25", 1);
        setNoClass(ll1, 2, 0);
        setClass(ll1, "概率论与数理统计", "国软  4-304", "1-15周，每一周", "14:55-17:25", 2);
        setNoClass(ll1, 2, 0);


        setClass(ll2, "大学英语", "国软 4-302", "1-18周，每一周", "8:00-9:35", 3);
        setClass(ll2, "计算机组织体系与结构", "国软 4-204", "1-15，每一周", "9:50-12:15", 5);
        setNoClass(ll2, 2, 0);
        setClass(ll2, "团队激励和沟通", "国软 4-204", "1-9周，每一周", "15:45-17:25", 6);
        setNoClass(ll2, 2, 0);


        setNoClass(ll3, 2, 0);
        setClass(ll3, "中国近现代史纲要", "3区 1-328", "1-9周，每一周", "9:50-12:15", 1);
        setNoClass(ll3, 2, 0);
        setClass(ll3, "体育(网球)", "信息学部 操场", "6-18周，每一周", "14:00-15:40", 2);
        setNoClass(ll3, 2, 0);

        setClass(ll4, "计算机组织体系与结构", "国软 4-204", "1-15，每一周", "8:00-9:35", 5);
        setClass(ll4, "数据结构与算法", "国软 4-304", "1-18周，每一周", "9:50-12:15", 4);
        setNoClass(ll4, 2, 0);
        setClass(ll4, "面向对象程序设计(JAVA)", "国软 1-103", "1-18周，每一周", "14:00-16:30", 5);
        setNoClass(ll4, 2, 0);


        setClass(ll5, "c#程序设计", "国软 4-102", "1-9周，每一周", "8:00-9:35", 6);
        setClass(ll5, "大学英语", "国软 4-302", "1-18周，每一周", "9:50-11:25", 3);
        setNoClass(ll5, 2, 0);
        setClass(ll5, "基础物理", "国软 4-304", "1-18周，每一周", "14:00-16:30", 1);
        setNoClass(ll5, 2, 0);

        setNoClass(ll6, 10, 0);

        setNoClass(ll7, 10, 0);
    }

    /**
     * 设置课程的方法
     *
     * @param ll
     * @param title   课程名称
     * @param place   地点
     * @param last    时间
     * @param time    周次
     * @param color   背景色
     */
    void setClass(LinearLayout ll, String title, String place,
                  String last, String time, int color) {
        View view = LayoutInflater.from(this).inflate(R.layout.item, null);
        int height = ViewUtil.dip2px(this, 100);   //48
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(params);
        Random rand = new Random();
        // 生成随机颜色，除了colors数组的第一项
        view.setBackgroundColor(colors[rand.nextInt(colors.length - 1) + 1]);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.place)).setText(place);
        ((TextView) view.findViewById(R.id.last)).setText(last);
        ((TextView) view.findViewById(R.id.time)).setText(time);
        //为课程View设置点击监听器
        view.setOnClickListener(new OnClickClassListener());
        view.setOnLongClickListener(new OnLongClickClassListener());

        ll.addView(view);
    }

    //点击课程的监听器
    class OnClickClassListener implements OnClickListener {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            String title;
            title = (String) ((TextView) v.findViewById(R.id.title)).getText();
            Toast.makeText(getApplicationContext(), "你点击的是:" + title,
                    Toast.LENGTH_SHORT).show();
            LogUtil.e(title, title);
            // 下面这段代码这是我做的测试
//            ((TextView) v.findViewById(R.id.title)).setText("陈长青" + i);
        }
    }

    class OnLongClickClassListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
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
            }
        });
        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了删除课程~", Toast.LENGTH_SHORT).show();

                popWindow.dismiss();
            }
        });
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
//                ll3.removeAllViews();
                ll3.removeAllViews();
                setNoClass(ll3, 4, 0);
                setNoClass(ll3, 2, 0);
                setClass(ll3, "体育(网球)", "信息学部 操场", "6-18周，每一周", "14:00-15:40", 2);
                setNoClass(ll3, 2, 0);
////                setNoClass(ll3, 10, 0);
//                setClass(ll3, "基础物理", "国软 4-304", "1-18周，每一周", "14:00-16:30", 2, 1);

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
