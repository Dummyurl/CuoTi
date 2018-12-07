package com.chinastis.cuoti.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chinastis.cuoti.MainActivity;
import com.chinastis.cuoti.R;
import com.chinastis.cuoti.util.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends AppCompatActivity {
    private int timeCount;
    public boolean isStop;
    public boolean isShouldTurnToMain;

    private Map<String,String> classMap;
    private Map<String,String> understandMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initClassMap();
        initUnderstandMap();

        Constant.classMap = classMap;
        Constant.understandMap = understandMap;

        timeCount = 1500;
        countTime();
    }

    /**
     * desc: 倒计时跳转界面
     */
    private void countTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!SplashActivity.this.isFinishing()) {
                    if (isStop) {
                        isShouldTurnToMain = true;
                        return;
                    }

                    if(getSharedPreferences("sp",MODE_PRIVATE).getBoolean("isLogin",false)) {
                        Constant.USER = getSharedPreferences("sp",MODE_PRIVATE).getString("account","");
                        Constant.USER_ID = getSharedPreferences("sp",MODE_PRIVATE).getString("userId","");

                        JPushInterface.setAlias(getApplicationContext(),0,Constant.USER);
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    } else {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }


                    SplashActivity.this.finish();
                }
            }
        },timeCount);
    }



    private void initClassMap() {
        classMap = new HashMap<>();
        classMap.put("语文","1001");
        classMap.put("数学","1002");
        classMap.put("英语","1003");
        classMap.put("政治","1004");
        classMap.put("历史","1005");
        classMap.put("生物","1006");
        classMap.put("地理","1007");
        classMap.put("物理","1008");
        classMap.put("化学","1009");
    }

    private void initUnderstandMap() {
        understandMap = new HashMap<>();
        understandMap.put("不懂","2001");
        understandMap.put("略懂","2002");
        understandMap.put("基本懂","2003");
        understandMap.put("完全懂","2004");

    }
}
