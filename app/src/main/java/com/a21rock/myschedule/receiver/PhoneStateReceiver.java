package com.a21rock.myschedule.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 21rock on 2017/2/10.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("action", "[Broadcast]" + action);

        if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            doReceivePhone(context, intent);
        }
    }

    /**
     * 处理电话广播.
     *
     * @param context
     * @param intent
     */
    public void doReceivePhone(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(
                TelephonyManager.EXTRA_INCOMING_NUMBER);
        TelephonyManager telephony =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int state = telephony.getCallState();
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(TAG, "[Broadcast]等待接电话=" + phoneNumber);
                if (phoneNumber != null) {

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phoneNumber, null, "正在上课呢，别打电话过来，我下课再打给你把", null, null);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(TAG, "[Broadcast]电话挂断=" + phoneNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(TAG, "[Broadcast]通话中=" + phoneNumber);
                break;
        }
    }
}
