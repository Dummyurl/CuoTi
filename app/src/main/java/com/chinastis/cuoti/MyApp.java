package com.chinastis.cuoti;

import android.app.Application;
import android.content.Context;

import com.chinastis.cuoti.database.MyGreenDaoOpenHelper;
import com.chinastis.cuoti.database.dao.DaoMaster;
import com.chinastis.cuoti.database.dao.DaoSession;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xianglong on 2018/12/5.
 */

public class MyApp extends Application {

    private static Context appContext;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        initGreenDao();

//        LeakCanary.install(this);

    }

    private void initGreenDao() {
        MyGreenDaoOpenHelper helper = new MyGreenDaoOpenHelper(this, "cuoti.db",
                null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
