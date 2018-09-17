package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.vdin.JxProduct.R;

public class InfoRegistActivity extends BaseActivity {

    // 参数定义
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_regist);

        fullScreen(this);
        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("新增业务登记");

        myIntent = getIntent();

        String idCardNumber = myIntent.getStringExtra("idCardNumber");
        String name = myIntent.getStringExtra("name");
        String phoneNumber = myIntent.getStringExtra("phoneNumber");
        String scenePhotoPath = myIntent.getStringExtra("scenePhotoPath");

    }
}
