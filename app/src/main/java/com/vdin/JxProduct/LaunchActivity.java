package com.vdin.JxProduct;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.vdin.JxProduct.Activity.GuideActivity;
import com.vdin.JxProduct.Activity.HomeActivity;
import com.vdin.JxProduct.Util.LaunchUtil;

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载启动界面
        setContentView(R.layout.activity_launch);

        // 判断是否第一次启动
        if (LaunchUtil.isFirstLaunch(this)){

            LaunchUtil.setFirshLaunchFlag(this);
            //设置等待时间，单位为毫秒
            Integer time = 1500;

            Handler handler = new Handler();
            //当计时结束时，跳转至引导页
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LaunchActivity.this, GuideActivity.class);
                    int[] imageIdArray = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3};
                    intent.putExtra("imageIdArray",imageIdArray);
                    startActivity(intent);
                }
            }, time);

        }else {
            Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        LaunchActivity.this.finish();
    }

}
