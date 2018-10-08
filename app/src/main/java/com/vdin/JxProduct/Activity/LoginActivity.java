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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.API.NetWorkCallBack;
import com.vdin.JxProduct.Gson.LoginDataResponse;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.UserInfoService;
import com.vdin.JxProduct.Util.ActivityConllector;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.Util.LaunchUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rv_Back)
    RelativeLayout rvBack;
    @BindView(R.id.txt_alltitle)
    TextView txtAlltitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.rv_phone)
    RelativeLayout rvPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.rv_password)
    RelativeLayout rvPassword;
    @BindView(R.id.Lv_zhong)
    LinearLayout LvZhong;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_mmcz)
    TextView txtMmcz;

    public static LoginActivity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setFitsSystemWindows(this, true);

        myActivity = this;
        txtAlltitle.setText("登录");

        // 设置焦点监听 改变输入框背景颜色
        Focused();
        // 填充用户名
        String userName = LaunchUtil.getLoginUsername(myActivity);
        etPhone.setText(userName);
        // 填充密码
        String userPwd = LaunchUtil.getLoginPassword(myActivity);
        etPassword.setText(userPwd);


//        testData();
    }

    /**
     * 测试数据
     */
    private void testData() {
        etPhone.setText("14258585201");
        etPassword.setText("Az123456");
    }

    /**
     * 返回按钮点击响应事件
     */
    @OnClick(R.id.rv_Back)
    public void onRvBackClicked() {
        finish();
    }

    /**
     * 登录按钮点击响应事件
     */
    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {

        InputMethodManager imms = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imms.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);

        // 01 验证输入
        if (etPhone.getText().toString().length() == 0) {
            showToastWithMessage("请输入用户名");
            return;
        }

        if (etPhone.getText().toString().length() < 11) {
            showToastWithMessage("用户名至少11位");
            return;
        }

        if (etPassword.getText().toString().length() == 0) {
            showToastWithMessage("请输入密码");
            return;
        }

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(this)) {
            showToastWithMessage("无可用网络，请检查网路设置");
            return;
        }

        // 02 用户登录
        login();
//        JumpToMainActivity();

    }

    /**
     * 密码重置按钮点击响应事件
     */
    @OnClick(R.id.txt_mmcz)
    public void onTxtMmczClicked() {
        Intent resetPwdItent = new Intent(myActivity,ResetPwdActivity.class);
        startActivity(resetPwdItent);
    }

    /**
     * 登录
     */
    public void login() {

        showProgressDialog("登录中");
        //参数封装
        String username = etPhone.getText().toString();
        String password = etPassword.getText().toString();

        MetaDataApiRequest.login(username, password, new NetWorkCallBack() {
            @Override
            public void completeBlock(boolean isSuccess, Object object) {

                closeProgressDialog();

                if (isSuccess) {
                    LoginDataResponse loginDataResponse = (LoginDataResponse) object;

                    if (loginDataResponse.isSuccess()) {
                        // 入库
                        UserInfoService.getInstance().saveJsonToDataBase(loginDataResponse);
                        // 跳槽
                        JumpToMainActivity();
                    } else {
                        String msg = loginDataResponse.getMessage();
                        msg = msg.length() > 0 ? msg : "网络错误";
                        showToastWithMessage(msg);
                    }

                } else {
                    String textStr = object instanceof String ? (String)object : "网络错误，请检查网路设置";
                    showToastWithMessage(textStr);
                }

            }
        });

    }

    /**
     * 跳转至主界面
     */
    public void JumpToMainActivity() {

        // 存储用户登录数据
        LaunchUtil.setLoginFlag(myActivity);
        LaunchUtil.setLoginUsername(myActivity, etPhone.getText().toString());
        LaunchUtil.setLoginPassword(myActivity, etPassword.getText().toString());

        // 关闭提示进度窗
        closeProgressDialog();

        // 跳转主界面
        Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mIntent);
        ActivityConllector.finishAll();
    }

    /**
     * 获取焦点改变输入框颜色
     */
    public void Focused() {

        // 用户名输入框 设置监听
        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rvPhone.setBackgroundResource(R.mipmap.input_highlighted);
                } else {
                    rvPhone.setBackgroundResource(R.mipmap.input_normal);
                }
            }
        });

        // 密码输入框设置监听
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    rvPassword.setBackgroundResource(R.mipmap.input_highlighted);
                } else {
                    rvPassword.setBackgroundResource(R.mipmap.input_normal);
                }
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
        imms.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);
        return super.onTouchEvent(event);
    }

}






















