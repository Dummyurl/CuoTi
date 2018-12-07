package com.chinastis.cuoti.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chinastis.cuoti.database.dao.DaoMaster;
import com.chinastis.cuoti.database.dao.UserDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * Created by xianglong on 2018/4/3.
 */

public class MyGreenDaoOpenHelper extends DaoMaster.OpenHelper {
    public MyGreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }
            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        },
                UserDao.class);
    }
}
