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

    private String incoming_number = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);

        if (tm.getCallState() == TelephonyManager.CALL_STATE_RINGING) {
            incoming_number = intent.getStringExtra("incoming_number");
            if (incoming_number != null) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(incoming_number, null, "正在上课呢，等我下课后我在打给你把", null, null);
                Log.i(TAG, "RINGING :" + incoming_number);
            }
        }
    }
}
