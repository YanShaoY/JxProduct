package com.vdin.JxProduct.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsManager {

    private static SharedPreferences getSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getPrefs(Context context, String key) {
        SharedPreferences prefs = getSharedPrefs(context);
        return prefs.getString(key, null);
    }

    public static void setPrefs(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPrefs(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void removePrefs(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPrefs(context).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeAll(Context context) {
        SharedPreferences.Editor editor = getSharedPrefs(context).edit();
        editor.clear();
        editor.apply();
    }
}
