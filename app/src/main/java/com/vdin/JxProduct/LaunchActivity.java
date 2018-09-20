package com.vdin.JxProduct;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.vdin.JxProduct.Activity.GuideActivity;
import com.vdin.JxProduct.Activity.HomeActivity;
import com.vdin.JxProduct.Activity.MainActivity;
import com.vdin.JxProduct.Service.MetaDataService;
import com.vdin.JxProduct.Util.LaunchUtil;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载启动界面
        setContentView(R.layout.activity_launch);

        //设置等待时间，单位为毫秒
        Integer time = 1500;
        // 当计时结束时，跳转
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            // 判断是否第一次启动
            if (LaunchUtil.isFirstLaunch(this)) {

                LaunchUtil.setFirshLaunchFlag(this);

                Intent intent = new Intent(this, GuideActivity.class);
                int[] imageIdArray = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3};
                intent.putExtra("imageIdArray", imageIdArray);
                startActivity(intent);
                finish();

            } else {
                //判断是否已经登录
                if (LaunchUtil.isLogin(this)) {
                    // 跳转主界面
                    Intent mIntent = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();

                } else {
                    //进入 home界面
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, time);

        //获取元数据
        MetaDataService.getInstance().initMetadata();

    }

}
