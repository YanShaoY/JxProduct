package com.vdin.JxProduct.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.Gson.BaseResponse;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.Util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPwdActivity extends BaseActivity {

    @BindView(R.id.rv_Back)
    RelativeLayout rvBack;
    @BindView(R.id.txt_alltitle)
    TextView txtAlltitle;
    @BindView(R.id.user_name_edit)
    EditText userNameEdit;
    @BindView(R.id.txt_GetPinCode)
    TextView txtGetPinCode;
    @BindView(R.id.reset_pwd_user_name)
    RelativeLayout resetPwdUserName;
    @BindView(R.id.verification_code_edit)
    EditText verificationCodeEdit;
    @BindView(R.id.reset_verification_code)
    RelativeLayout resetVerificationCode;
    @BindView(R.id.reset_pwd_button)
    Button resetPwdButton;

    // 参数定义
    // 本类实例
    ResetPwdActivity myActivity;
    // 重新发送计时器
    private int timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);

        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 设置焦点监听 改变输入框背景颜色
        Focused();
        // 设置下划线
//        txtGetPinCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        // 加载测试数据
        initTestData();
    }

    /**
     * 导航栏初始化
     */
    private void initNavBar() {
        fullScreen(this);
        setFitsSystemWindows(this, true);
        txtAlltitle.setText("密码重置");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {
        myActivity = this;
        timer = 60;
    }

    /**
     * 获取焦点改变输入框颜色
     */
    public void Focused() {

        // 用户名输入框 设置监听
        userNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetPwdUserName.setBackgroundResource(R.mipmap.input_highlighted);
                } else {
                    resetPwdUserName.setBackgroundResource(R.mipmap.input_normal);
                }
            }
        });

        // 备案验证码输入框设置监听
        verificationCodeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetVerificationCode.setBackgroundResource(R.mipmap.input_highlighted);
                } else {
                    resetVerificationCode.setBackgroundResource(R.mipmap.input_normal);
                }
            }
        });
    }

    /**
     * 初始化测试数据
     */
    private void initTestData() {
        userNameEdit.setText("14988887777");
        verificationCodeEdit.setText("929914");
    }

    /**
     * 返回按钮点击响应事件
     */
    @OnClick(R.id.rv_Back)
    public void onRvBackClicked() {
        finish();
    }

    /**
     * 获取验证码点击响应事件
     */
    @OnClick(R.id.txt_GetPinCode)
    public void onTxtGetPinCodeClicked() {

        // 01 取值
        final String userName = userNameEdit.getText().toString();

        // 02 弹窗
        if (userName.length() <= 0) {
            showToastWithMessage("请输入用户名");
            return;
        }

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(this)) {
            showToastWithMessage("无可用网络，请检查网路设置");
            return;
        }

        // 03 计时器
        txtGetPinCode.setClickable(false);
        txtGetPinCode.setTextColor(ContextCompat.getColor(mContext, R.color.txt_timer));
        txtGetPinCode.setText("获取验证码(" + timer + "s)");
        startResendTimer();

        // 04 请求重发验证码
        MetaDataApiRequest.sendForgetPwdReCode(userName,(isSuccess, object) -> {

            if (isSuccess) {
                JsonObject jsonObject = (JsonObject) new JsonParser().parse((String) object);
                // 验证成功
                if (jsonObject.get("success").getAsBoolean()) {

                    JsonArray collections = jsonObject.get("collection").getAsJsonArray();
                    String phone_number = collections.get(0).getAsJsonObject().get("phone_number").getAsString();
                    String msg = "验证发送成功";
                    if (!StringUtils.isEmpty(phone_number)) {
                        msg = "验证码已发送至" + phone_number;
                    }
                    showToastWithMessage(msg);

                } else {

                    // 重置倒计时
                    restoreResendTimer();

                    String msg = jsonObject.get("message").getAsString();
                    msg = msg.length() > 0 ? msg : "网络错误";
                    showToastWithMessage(msg);
                }
            } else {

                // 重置倒计时
                restoreResendTimer();

                if (object instanceof String) {
                    showToastWithMessage((String) object);

                } else {
                    showToastWithMessage("网络请求错误，请重试");
                }

            }
        });

    }

    @OnClick(R.id.reset_pwd_button)
    public void onResetPwdButtonClicked() {

        // 01 取值
        final String userName = userNameEdit.getText().toString();
        final String smsToken = verificationCodeEdit.getText().toString();

        // 02 弹窗
        if (userName.length() <= 0) {
            showToastWithMessage("请输入用户名");
            return;
        }

        if (smsToken.length() <= 0) {
            showToastWithMessage("请输入验证码");
            return;
        }

        // 判断网络状态
        if (!HttpUtil.isNetworkConnected(this)) {
            showToastWithMessage("无可用网络，请检查网路设置");
            return;
        }

        // 03 提示
        showProgressDialog("正在验证信息···");

        // 04 请求
        MetaDataApiRequest.checkResetPwdSmsToken(userName,smsToken,(isSuccess, object) -> {

            // 隐藏加载弹窗
            closeProgressDialog();

            if (isSuccess) {
                JsonObject jsonObject = (JsonObject) new JsonParser().parse((String) object);
                BaseResponse response = new Gson().fromJson(jsonObject.toString(), BaseResponse.class);
                // 验证成功
                if (response.isSuccess()) {
                    Intent setPwdIntent = new Intent(myActivity, SetPasswordActivity.class);
                    setPwdIntent.putExtra("userName", userName);
                    setPwdIntent.putExtra("smsToken", smsToken);
                    setPwdIntent.putExtra("type", "reset");
                    myActivity.startActivity(setPwdIntent);

                } else {
                    String msg = response.getMessage();
                    msg = msg.length() > 0 ? msg : "网络错误";
                    showToastWithMessage(msg);
                }
            } else {

                if (object instanceof String) {
                    showToastWithMessage((String) object);

                } else {
                    showToastWithMessage("网络请求错误，请重试");
                }

            }

        });

    }

    /**
     * 开始重发验证码倒计时
     */
    private void startResendTimer() {

        // 开启一秒线程
        new Handler().postDelayed(() -> {
            // 减一秒
            timer--;
            // 更新计时显示
            txtGetPinCode.setText("获取验证码(" + timer + "s)");
            // 判断条件 回复UI
            if (timer <= 0) {
                txtGetPinCode.setTextColor(ContextCompat.getColor(mContext, R.color.txt_nomal));
                txtGetPinCode.setText("获取验证码");
                timer = 60;
                txtGetPinCode.setClickable(true);
            }else {
                // 继续启动
                startResendTimer();
                txtGetPinCode.setClickable(false);
            }

        }, 1000);
    }

    /**
     * 回复重发验证码初始状态
     */
    private void restoreResendTimer() {
        timer = 0;
        txtGetPinCode.setClickable(false);
    }

}











