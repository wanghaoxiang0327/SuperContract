package com.sskj.common;

import android.app.Activity;
import android.arch.lifecycle.LifecycleObserver;

import java.util.ArrayList;

public class AppManager implements LifecycleObserver {


    private static  AppManager instance;

    private  final ArrayList<Activity> activities = new ArrayList<>();


    public static  AppManager getInstance(){
        if (instance==null){
            instance=new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    public Activity getCurrentActivity() {
        return activities.get(activities.size() - 1);
    }

    public void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    public void finishAllLogin(){
        for (Activity activity:activities){
            if (!activity.getLocalClassName().contains("LoginActivity")){
                activity.finish();
            }
        }
    }



}
