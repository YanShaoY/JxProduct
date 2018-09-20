package com.vdin.JxProduct.Util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * @开发者 YanSY
 * @日期 2018/9/17
 * @描述 Vdin成都研发部
 */
public class SystemUtil {

    /**
     * 判断是否是测试版本
     * @param context
     * @return
     */
    public static boolean isApkDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
