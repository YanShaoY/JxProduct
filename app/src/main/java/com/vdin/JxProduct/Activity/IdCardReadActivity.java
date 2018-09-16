package com.vdin.JxProduct.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vdin.JxProduct.R;

public class IdCardReadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_read);


        fullScreen(this);
        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("身份验证");


    }
}
