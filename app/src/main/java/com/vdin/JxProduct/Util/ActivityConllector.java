package com.vdin.JxProduct.Util;

import android.app.Activity;
import android.content.Intent;

import com.vdin.JxProduct.Activity.MainActivity;
import com.vdin.JxProduct.App.MainApplication;

import java.util.ArrayList;
import java.util.List;

public class ActivityConllector {

    // 存储所有的Activity数组
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加Activity
     * @param activity onCreate方法里面调用
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除Activity
     * @param activity onDestroy方法里面调用
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 注销掉所有的Activity
     */
    public static void finishAll() {
        for (Activity activity: activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前的Activity
     * @return 返回最后一个Activity
     */
    public static Activity getThisActivity(){
        return activities.get(activities.size()-1);
    }

    /**
     * 回到主页Activity
     */
    public static void popToMainActivity(){
        // 跳转主界面
        Intent mIntent = new Intent(MainApplication.getContext(), MainActivity.class);
        MainApplication.getContext().startActivity(mIntent);
        finishAll();
    }


}
