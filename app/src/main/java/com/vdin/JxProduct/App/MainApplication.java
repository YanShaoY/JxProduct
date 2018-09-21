package com.vdin.JxProduct.App;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class MainApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
    }

    public static Context getContext() {
        return context;
    }
}
