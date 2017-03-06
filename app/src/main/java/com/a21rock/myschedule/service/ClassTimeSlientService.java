package com.a21rock.myschedule.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import com.a21rock.myschedule.bean.Course;
import com.a21rock.myschedule.receiver.PhoneStateReceiver;
import com.a21rock.myschedule.receiver.RemindClassReceiver;
import com.a21rock.myschedule.utils.DateUtil;
import com.a21rock.myschedule.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

public class ClassTimeSlientService extends Service {

    private AlarmManager manager;
    private PendingIntent pi;
    private PhoneStateReceiver phoneStateReceiver;
    //声明一个获取系统音频服务的类的对象
    private AudioManager audioManager;
    //获取手机之前设置好的铃声模式
    private int orgRingerMode;

    public ClassTimeSlientService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //声明一个获取系统音频服务的类的对象
        audioManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
        //获取手机之前设置好的铃声模式
        orgRingerMode = audioManager.getRingerMode();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 此线程查询课程 如果有课程的话，如果为第一大节课为 8点25 10点05 则hour =8 && minute >= 25 则启动服务
                // 如果hour = 10 && minute > 5 则关闭服务
                // 第一大节课为   8点25 10点05
                // 第二大节课为   10点20 12点00
                // 第三大节课为   14:00 15:40
                // 第四大节课为   15:50 17:30
                // 第五大节课为   18:30 20:10
                Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);

                int minute = c.get(Calendar.MINUTE);
                List<Course> courseList = getTodayCourse();
                for (int i = 0; i < courseList.size(); i++) {
                    switch (courseList.get(i).getLesson()) {
                        case 1:
                            if (hour == 8 && minute >= 25) {
                                setOriginRingerToSlientMode();
                                registerPhoneStateReceiveer();
                            } else if (hour == 10 && minute >= 05){
                                setSlientModeToOriginRingerMode();
                                unRegisterPhoneStateReceiveer();
                            }
                            break;
                        case 2:
                            if (hour == 10 && minute >= 20) {
                                setOriginRingerToSlientMode();
                                registerPhoneStateReceiveer();
                            } else if (hour == 12 && minute >= 00){
                                setSlientModeToOriginRingerMode();
                                unRegisterPhoneStateReceiveer();
                            }
                            break;
                        case 3:
                            if (hour == 14 && minute >= 00) {
                                setOriginRingerToSlientMode();
                                registerPhoneStateReceiveer();
                            } else if (hour == 15 && minute >= 40){
                                setSlientModeToOriginRingerMode();
                                unRegisterPhoneStateReceiveer();
                            }
                            break;
                        case 4:
                            if (hour == 15 && minute >= 50) {
                                setOriginRingerToSlientMode();
                                registerPhoneStateReceiveer();
                            } else if (hour == 17 && minute >= 30){
                                setSlientModeToOriginRingerMode();
                                unRegisterPhoneStateReceiveer();
                            }
                            break;
                        case 5:
                            if (hour == 18 && minute >= 00) {
                                setOriginRingerToSlientMode();
                                registerPhoneStateReceiveer();
                            } else if (hour == 20 && minute >= 10){
                                setSlientModeToOriginRingerMode();
                                unRegisterPhoneStateReceiveer();
                            }
                            break;
                    }
                }
            }
        }).start();
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int minute = 60 * 1000; // 这是一分钟的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + minute;
        Intent i = new Intent(this, ClassTimeSlientService.class);
        pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void unRegisterPhoneStateReceiveer() {
        if (phoneStateReceiver != null) {
            unregisterReceiver(phoneStateReceiver);
        }
    }

    private void registerPhoneStateReceiveer() {
        phoneStateReceiver = new PhoneStateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(phoneStateReceiver, intentFilter);
    }

    private List<Course> getTodayCourse() {
        int day = DateUtil.dayForWeek(DateUtil.getCurrentTime());
        return DataSupport.where("day = ?", String.valueOf(day)).order("lesson asc").find(Course.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pi.cancel();
        if (phoneStateReceiver != null) {
            unregisterReceiver(phoneStateReceiver);
        }
    }

    private void setOriginRingerToSlientMode() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    private void setSlientModeToOriginRingerMode() {
        audioManager.setRingerMode(orgRingerMode);
    }
}
