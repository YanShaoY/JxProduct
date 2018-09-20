package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.MetaDataService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    /**
     * 按钮响应事件
     * @param view
     */
    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:

                break;
            case R.id.btn_login:
                MetaDataService.getInstance().initMetadata();
                Intent mIntent = new Intent(HomeActivity.this,
                        LoginActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
