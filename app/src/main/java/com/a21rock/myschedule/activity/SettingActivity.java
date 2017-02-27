package com.a21rock.myschedule.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.a21rock.myschedule.R;

import com.a21rock.myschedule.utils.ViewUtil;

public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SwitchCompat mutePhone = (SwitchCompat) findViewById(R.id.switch_mute_phone);
        SwitchCompat remindBeforeClass = (SwitchCompat) findViewById(R.id.switch_remind_before_class);
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
                } else {
                    Snackbar.make(view, "关闭上课静音提醒", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_remind_before_class:
                if (isChecked) {
                    Snackbar.make(view, "开启课前振动提醒", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view, "关闭课前振动提醒", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

}
