package com.chinastis.cuoti.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chinastis.cuoti.MainActivity;
import com.chinastis.cuoti.view.SplashActivity;


/**
 * Created by xianglong on 2018/8/8.
 */

public class UserDefineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("MENG","received define message ");

        if (intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")) {
            Intent intent1 = new Intent(context, SplashActivity.class);
            context.startActivity(intent1);
        }
    }
}
