package com.a21rock.myschedule.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 21rock on 2017/2/28.
 */

public class SharedPreferencesUtil {

    public static void setIdToSharedPreferences(Activity activity, int id) {
        SharedPreferences.Editor editor = activity.getSharedPreferences("courseId", Context.MODE_PRIVATE).edit();
        editor.putInt("id", id);
        editor.apply();
    }

    public static int getIdFromSharedPreferences(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("courseId", Context.MODE_PRIVATE);
        return pref.getInt("id", 0);
    }

    public static void setRemindClass(Activity activity, boolean flag) {
        SharedPreferences.Editor editor = activity.getSharedPreferences("isRemindClass", Context.MODE_PRIVATE).edit();
        editor.putBoolean("remindClassFlag", flag);
        editor.apply();
    }


    public static boolean getRemindClassFlag(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("isRemindClass", Context.MODE_PRIVATE);
        return pref.getBoolean("remindClassFlag", false);
    }

    public static void setPhoneSlient(Activity activity, boolean flag) {
        SharedPreferences.Editor editor = activity.getSharedPreferences("isSetPhoneSlient", Context.MODE_PRIVATE).edit();
        editor.putBoolean("phoneSlientFlag", flag);
        editor.apply();
    }


    public static boolean getPhoneSlientFlag(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("isSetPhoneSlient", Context.MODE_PRIVATE);
        return pref.getBoolean("phoneSlientFlag", false);
    }
}
