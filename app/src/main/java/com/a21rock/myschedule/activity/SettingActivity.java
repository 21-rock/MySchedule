package com.a21rock.myschedule.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.a21rock.myschedule.R;


import com.a21rock.myschedule.service.MyService;
import com.a21rock.myschedule.service.RemindClassService;
import com.a21rock.myschedule.utils.LogUtil;
import com.a21rock.myschedule.utils.SharedPreferencesUtil;
import com.a21rock.myschedule.utils.ViewUtil;

public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolbar();
        SwitchCompat mutePhone = (SwitchCompat) findViewById(R.id.switch_mute_phone);
        SwitchCompat remindBeforeClass = (SwitchCompat) findViewById(R.id.switch_remind_before_class);
        if (SharedPreferencesUtil.getRemindClassFlag(this) == true) {
            remindBeforeClass.setChecked(true);
        }
        mutePhone.setOnCheckedChangeListener(this);
        remindBeforeClass.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        View view = ViewUtil.getRootView(SettingActivity.this);
        switch (buttonView.getId()) {
            case R.id.switch_mute_phone:
                if (isChecked) {
                    Snackbar.make(view, "开启上课静音提醒", Snackbar.LENGTH_SHORT).show();
                    Intent StartServiceIntent = new Intent(this, MyService.class);
                    startService(StartServiceIntent);
                } else {
                    Snackbar.make(view, "关闭上课静音提醒", Snackbar.LENGTH_SHORT).show();
                    Intent StopServiceIntent = new Intent(this, MyService.class);
                    stopService(StopServiceIntent);
                }
                break;
            case R.id.switch_remind_before_class:
                if (isChecked) {
                    Snackbar.make(view, "开启课前振动提醒", Snackbar.LENGTH_SHORT).show();
                    SharedPreferencesUtil.setRemindClass(SettingActivity.this, true);
                    Intent StartServiceIntent = new Intent(this, RemindClassService.class);
                    startService(StartServiceIntent);
                } else {
                    Snackbar.make(view, "关闭课前振动提醒", Snackbar.LENGTH_SHORT).show();
                    SharedPreferencesUtil.setRemindClass(SettingActivity.this, false);
                    Intent StopServiceIntent = new Intent(this, RemindClassService.class);
                    stopService(StopServiceIntent);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

}
