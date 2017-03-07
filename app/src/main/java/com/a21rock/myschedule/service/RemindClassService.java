package com.a21rock.myschedule.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;

import com.a21rock.myschedule.R;
import com.a21rock.myschedule.bean.Course;
import com.a21rock.myschedule.receiver.RemindClassReceiver;
import com.a21rock.myschedule.utils.DateUtil;
import com.a21rock.myschedule.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

public class RemindClassService extends Service {

    private AlarmManager manager;
    private PendingIntent pi;

    public RemindClassService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("onCreate", "remindClassService was onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("onStartCommand", "remindClassService was onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);

                int minute = c.get(Calendar.MINUTE);
                LogUtil.d("RemindClassService", "hour" + hour);
                LogUtil.d("RemindClassService", "minute" + minute);
                List<Course> courseList = getTodayCourse();
                for (int i = 0; i < courseList.size(); i++) {
                    LogUtil.d("RemindClassService", courseList.get(i).getTitle()+courseList.get(i).getLesson());
                    switch (courseList.get(i).getLesson()) {
                        case 1:
                            if (hour == 7 && minute == 55) {
                                remindBeforeClass();
                            }
                            break;
                        case 2:
                            if (hour == 9 && minute == 50) {
                                remindBeforeClass();
                            }
                            break;
                        case 3:
                            if (hour == 13 && minute == 30) {
                                remindBeforeClass();
                            }
                            break;
                        case 4:
                            if (hour == 15 && minute == 20) {
                                remindBeforeClass();
                            }
                            break;
                        case 5:
                            if (hour == 18 && minute == 00) {
                                remindBeforeClass();
                            }
                            break;
                        default:

                    }
                }
            }
        }).start();
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int minute = 60 * 1000; // 这是一分钟的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + minute;
        Intent i = new Intent(this, RemindClassReceiver.class);
        pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pi.cancel();
        LogUtil.d("onDestory", "remindClassService was onDestroy");
    }

    private List<Course> getTodayCourse() {
        int day = DateUtil.dayForWeek(DateUtil.getCurrentTime());
        return DataSupport.where("day = ?", String.valueOf(day)).order("lesson asc").find(Course.class);
    }

    private void remindBeforeClass() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        Notification notification = builder
                .setContentTitle("上课提醒")
                .setContentText("老师还有30分钟到达战场，全军出击")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.drawable.app_logo))
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);
    }
}
