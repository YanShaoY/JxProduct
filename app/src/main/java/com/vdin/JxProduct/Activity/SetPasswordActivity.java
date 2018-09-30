package com.vdin.JxProduct.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.ActivityConllector;
import com.vdin.JxProduct.Util.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {

    @BindView(R.id.rv_Back)
    RelativeLayout rvBack;
    @BindView(R.id.txt_alltitle)
    TextView txtAlltitle;
    @BindView(R.id.pwd_set_first_edit)
    EditText pwdSetFirstEdit;
    @BindView(R.id.pwd_set_first_back)
    RelativeLayout pwdSetFirstBack;
    @BindView(R.id.pwd_set_second_edit)
    EditText pwdSetSecondEdit;
    @BindView(R.id.pwd_set_second_back)
    RelativeLayout pwdSetSecondBack;
    @BindView(R.id.pwd_set_confirm_button)
    Button pwdSetConfirmButton;

    // 参数定义
    // 本类实例
    SetPasswordActivity myActivity;
    // 上个界面传入参数
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);

        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 设置焦点监听 改变输入框背景颜色
        Focused();
    }

    /**
     * 导航栏初始化
     */
    private void initNavBar() {
        fullScreen(this);
        setFitsSystemWindows(this, true);
        txtAlltitle.setText("设置密码");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {
        myActivity = this;
        myIntent = getIntent();
    }

    /**
     * 获取焦点改变输入框颜色
     */
    public void Focused() {

        pwdSetFirstEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pwdSetFirstBack.setBackgroundResource(R.mipmap.input_highlighted);
                } else {
                    pwdSetFirstBack.setBackgroundResource(R.mipmap.input_normal);
                }
            }
        });

        pwdSetSecondEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pwdSetSecondBack.setBackgroundResource(R.mipmap.input_highlighted);
                } else {
                    pwdSetSecondBack.setBackgroundResource(R.mipmap.input_normal);
                }
            }
        });
    }


    @OnClick(R.id.rv_Back)
    public void onRvBackClicked() {
        finish();
    }

    @OnClick(R.id.pwd_set_confirm_button)
    public void onPwdSetConfirmButtonClicked() {

        // 01 取值
        final String firstPwdStr = pwdSetFirstEdit.getText().toString();
        final String secondPwdStr = pwdSetSecondEdit.getText().toString();

        String userName = myIntent.getStringExtra("userName");
        String smsToken = myIntent.getStringExtra("smsToken");
        String type = myIntent.getStringExtra("type");

        // 02 弹窗
        if (firstPwdStr.length() <= 0) {
            showToastWithMessage("请输入密码");
            return;
        }

        if (secondPwdStr.length() <= 0) {
            showToastWithMessage("请输入确认密码");
            return;
        }

        if (!firstPwdStr.equals(secondPwdStr)){
            showToastWithMessage("两次密码输入不一致");
            return;
        }

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(this)) {
            showToastWithMessage("无可用网络，请检查网路设置");
            return;
        }

        // 03 提示
        showProgressDialog("正在验证信息···");

        // 04 网络请求
        MetaDataApiRequest.createPassword(userName,smsToken,firstPwdStr,(isSuccess, object) -> {

            closeProgressDialog();
            if (isSuccess){
                JsonObject jsonObject = (JsonObject) new JsonParser().parse((String) object);
                if (jsonObject.get("success").getAsBoolean()){

                    showToastWithMessage("密码设置成功");
                    if (type.equals("register")){
                        ActivityConllector.GoToLoginActivity();
                    }else {
                        ActivityConllector.GoToLoginActivity();
                    }

                }else {
                    String msg = jsonObject.get("message").getAsString();
                    msg = msg.length() > 0 ? msg : "密码重置失败，请重试";
                    showToastWithMessage(msg);
                }

            }else {

                showToastWithMessage("网络请求失败，请重试");

            }

        });
    }

    /**
     * 点击空白处隐藏键盘
     *
     * @param event touch事件
     * @return 返回响应状态
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imms = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imms != null;
        imms.hideSoftInputFromWindow(pwdSetFirstEdit.getWindowToken(), 0);
        imms.hideSoftInputFromWindow(pwdSetSecondEdit.getWindowToken(), 0);
        return super.onTouchEvent(event);
    }

    /**
     * 界面注销方法
     */
    @Override
    protected void onDestroy() {
        Log.d(this.toString(), "onDestroy");
        super.onDestroy();
    }

}







