package com.chinastis.cuoti.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/********************************************
 * 文件名称: ActivityManager.java
 * 系统名称:
 * 模块名称: 界面管理器
 * 软件版权: 苏州伽利工程技术有限公司
 * 功能说明: 界面管理
 * 系统版本: v1.0
 * 开发人员: 孟祥龙
 * 开发时间: 2017/11/10 上午10:31
 * 审核人员:
 * 相关文档:
 * 修改记录:
 *********************************************/

public class ActivityManager {

    private static ActivityManager activityManager;

    private List<Activity> activities;

    private ActivityManager(){

        activities = new ArrayList<>();

    }

    /**
     * desc:管理器单例获取
     * @return 管理器实例
     */
    public static ActivityManager getManager(){
        if(activityManager== null) {
            synchronized(ActivityManager.class) {
                if(activityManager == null) {
                    activityManager = new ActivityManager();
                }
            }
        }
        return activityManager;
    }

    /**
     * desc：增加界面
     * @param activity 界面实例
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * desc：移除界面
     * @param activity 界面实例
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * desc：销户所有界面
     */
    public void removeAllActivities(){
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }
}
