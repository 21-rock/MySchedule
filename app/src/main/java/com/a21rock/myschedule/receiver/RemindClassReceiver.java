package com.a21rock.myschedule.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.a21rock.myschedule.service.MyService;
import com.a21rock.myschedule.service.RemindClassService;

public class RemindClassReceiver extends BroadcastReceiver {
    public RemindClassReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Intent i = new Intent(context, RemindClassService.class);
        context.startService(i);
    }
}
