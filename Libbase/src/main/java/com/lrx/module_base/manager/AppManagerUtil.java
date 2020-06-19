package com.lrx.module_base.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public class AppManagerUtil {
    private static AppManagerUtil instance;

    private Stack<Activity> mActivities = new Stack<>();
    private AppManagerUtil(){}

    public static AppManagerUtil getInstance(){
        if(instance == null){
            instance = new AppManagerUtil();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        if(!mActivities.contains(activity)){
            mActivities.push(activity);
        }
    }

    public void removeActivity(Activity activity){
        mActivities.remove(activity);
    }

    public void finishAllActivity(){
        for(Activity activity:mActivities){
            activity.finish();
        }
        mActivities.clear();
    }

    public void finishAllButNot(Class cls) {
        for (Activity activity : mActivities) {
            if (!cls.getName().equals(activity.getClass().getName())) {
                activity.finish();
            }
        }
        mActivities.clear();
    }
}
