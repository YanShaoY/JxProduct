package com.vdin.JxProduct.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class LaunchUtil {

    public final static String IS_FIRST_LAUNCH = "isFirstLaunch";
    public final static String IS_LOGIN = "isLogin";
    public final static String LOGIN_USERNAME = "loginUsername";
    public final static String LOGIN_PASSWORD = "loginPassword";
    public final static String PRES_VERSION = "presVersion";

    //是否是第一次启动
    public static boolean isFirstLaunch(Context context) {

        String flag = PrefsManager.getPrefs(context, IS_FIRST_LAUNCH);
        //这里还要判断 preference保存的版本号是否和 manifest 里面保存的相同,不相同说明安装了
        //新版本的 APP,同样需要显示引导页
        if (flag == null) {
            String currentVersion = getCurrentVersion(context);
            setPrefsVersion(context, currentVersion);
            return true;
        }

        String prefsVersion = getPresVersion(context);
        String currentVersion = getCurrentVersion(context);
        if (!prefsVersion.equals(currentVersion)) {
            setPrefsVersion(context, currentVersion);
            return true;
        }

        return false;
    }

    //设置第一次启动后的标识
    public static void setFirshLaunchFlag(Context context) {
        PrefsManager.setPrefs(context, IS_FIRST_LAUNCH, "firstLaunchFlag");
    }

    //是否登录
    public static boolean isLogin(Context context) {
        String flag = PrefsManager.getPrefs(context, IS_LOGIN);
        if (flag == null) return false;
        return true;
    }

    //设置已登录标识
    public static void setLoginFlag(Context context) {
        PrefsManager.setPrefs(context, IS_LOGIN, "loginFlage");
    }

    //移除登录标识
    public static void removeLoginFlag(Context context) {
        PrefsManager.removePrefs(context, IS_LOGIN);
    }

    //设置登录帐号
    public static void setLoginUsername(Context context, String username) {
        PrefsManager.setPrefs(context, LOGIN_USERNAME, username);
    }

    //获取登录帐号
    public static String getLoginUsername(Context context) {
        return PrefsManager.getPrefs(context, LOGIN_USERNAME);
    }

    //移除登录帐号
    public static void removeLoginusername(Context context) {
        PrefsManager.removePrefs(context, LOGIN_USERNAME);
    }

    //设置登录密码
    public static void setLoginPassword(Context context, String password) {
        PrefsManager.setPrefs(context, LOGIN_PASSWORD, password);
    }

    //获取登录密码
    public static void getLoginPassword(Context context) {
        PrefsManager.getPrefs(context, LOGIN_PASSWORD);
    }

    //删除登录密码
    public static void removePassword(Context context) {
        PrefsManager.removePrefs(context, LOGIN_PASSWORD);
    }

    //设置preference保存 版本号
    public static void setPrefsVersion(Context context, String version) {
        PrefsManager.setPrefs(context, PRES_VERSION, version);
    }

    //获取preference 保存的版本号
    public static String getPresVersion(Context context) {
        return PrefsManager.getPrefs(context, PRES_VERSION);
    }

    //androidManifest 保存的版本号
    public static String getCurrentVersion(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (StringUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }

        return versionName;
    }

    //获取服务器版本号
    public static void getVersionForService(VersionForServiceCallBack callBack) {
        //待实现, 不清楚服务器接口
        HttpUtil.getRequest("url", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析出值
                callBack.version("解析出来的版本号");
            }
        });
    }

    public interface VersionForServiceCallBack {

        void version(String v);
    }
}

