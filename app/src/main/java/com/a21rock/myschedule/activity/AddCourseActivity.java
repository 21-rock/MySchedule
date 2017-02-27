package com.a21rock.myschedule.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a21rock.myschedule.R;

public class AddCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddCourseActivity.class);
        context.startActivity(intent);
    }
}
