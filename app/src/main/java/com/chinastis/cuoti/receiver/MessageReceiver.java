package com.chinastis.cuoti.receiver;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by xianglong on 2018/8/8.
 */

public class MessageReceiver extends JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("MENG","receive message1 :"+jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage){
        Log.e("MENG","receive message2 :"+jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("MENG","receive message3 :"+jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("MENG","receive message4 :"+jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }
}
