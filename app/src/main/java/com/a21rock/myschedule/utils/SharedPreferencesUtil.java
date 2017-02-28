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
}
