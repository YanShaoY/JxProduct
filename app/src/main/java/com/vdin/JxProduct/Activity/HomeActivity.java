package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.MetaDataService;
import com.vdin.JxProduct.View.GAConfirmDialog;

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

                //获取元数据
//                MetaDataService.getInstance().initMetadata();

                Intent activationActivity = new Intent(HomeActivity.this,
                        ActivationActivity.class);
                startActivity(activationActivity);

                break;

            case R.id.btn_login:

                //获取元数据
                MetaDataService.getInstance().initMetadata();

                Intent loginActivity = new Intent(HomeActivity.this,
                        LoginActivity.class);
                startActivity(loginActivity);

//                GAConfirmDialog dialog = new GAConfirmDialog(this, GAConfirmDialog.DialogStyle.TIMER);
//                dialog.showTimer("恭喜你，账户激活成功！", "点击确定进入下一步密码设置", 5, v -> {
//
//                });


                break;
        }
    }
}
