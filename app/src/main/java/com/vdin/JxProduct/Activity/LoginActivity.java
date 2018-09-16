package com.vdin.JxProduct.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.google.gson.Gson;
import com.vdin.JxProduct.API.MetaDataAPI;
import com.vdin.JxProduct.Gson.LoginDataResponse;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.Util.LaunchUtil;
import com.vdin.JxProduct.db.loginDataLinks;
import com.vdin.JxProduct.db.loginDataUserInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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


    }


    /**
     * 返回按钮点击响应事件
     */
    @OnClick(R.id.rv_Back)
    public void onRvBackClicked() {
        finish();
    }


    @OnClick(R.id.et_phone)
    public void onEtPhoneClicked() {
    }

    @OnClick(R.id.rv_phone)
    public void onRvPhoneClicked() {
    }

    @OnClick(R.id.et_password)
    public void onEtPasswordClicked() {
    }

    @OnClick(R.id.rv_password)
    public void onRvPasswordClicked() {
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
        }

        // 02 用户登录 TODO
//        login();
        JumpToMainActivity();

    }

    /**
     * 注册按钮点击响应事件
     */
    @OnClick(R.id.txt_mmcz)
    public void onTxtMmczClicked() {

    }


    /**
     * 登录
     */
    public void login() {

        showProgressDialog("登录中");

        //参数封装
        Map<String, Object> params = new HashMap<>();
        params.put("device_token", "1234560001");
        params.put("industry_code", "MVMT");
        params.put("login_name", etPhone.getText().toString());
        params.put("login_type", "1");
        params.put("ownerType", "1");
        params.put("packageName", "cn.com.vdin.scMVMT.androidphone");
        params.put("password", etPassword.getText().toString());
        params.put("pusher_code", "1");

        String URLStr = MetaDataAPI.getInstance().getLoginURL();

        HttpUtil.postRequest(URLStr, params, new Callback() {

            // 如果请求错误 提示并退出
            @Override
            public void onFailure(Call call, IOException e) {
                closeProgressDialog();
                e.printStackTrace();
                showToastWithMessage("网络错误，请检查网路设置");
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseText = response.body().string();

                if (!TextUtils.isEmpty(responseText)) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseText);
                        String metaDataStr = jsonObject.toString();

                        LoginDataResponse loginDataResponse = new Gson().fromJson(metaDataStr, LoginDataResponse.class);
                        if (loginDataResponse.isSuccess()) {
                            // 入库
                            saveJsonToDataBase(loginDataResponse);
                            // 跳槽
                            JumpToMainActivity();
                            // 跑路
                            return;
                        }else {
                            // 关闭提示进度窗
                            closeProgressDialog();
                            
                            String msg = loginDataResponse.getMessage();
                            msg = msg.length() > 0 ? msg : "网络错误" ;
                            showToastWithMessage(msg);
                            return;
                        }

                    } catch (JSONException e) {
                        closeProgressDialog();
                        e.printStackTrace();
                        showToastWithMessage("网络错误，请检查网路设置");
                        return;
                    }

                }

            }

        });

    }

    /**
     * 将数据存入数据库
     * @param response 请求到的数据Gson模型
     */
    public void saveJsonToDataBase(LoginDataResponse response){

        // 删除所有用户数据
        LitePal.deleteAll(loginDataUserInfo.class);
        LitePal.deleteAll(loginDataLinks.class);

        // 获取collection数据
        LoginDataResponse.CollectionBean collectionBean = response.getCollection().get(0);

        // 赋值数据库
        loginDataUserInfo userInfo = new loginDataUserInfo();

        userInfo.setHead_photo_url(collectionBean.getHead_photo_url());
        userInfo.setHotel_code(collectionBean.getHotel_code());
        userInfo.setHotel_pay_end_date(collectionBean.getHotel_pay_end_date());
        userInfo.setIdentification_number(collectionBean.getIdentification_number());
        userInfo.setName(collectionBean.getName());
        userInfo.setPhone_number(collectionBean.getPhone_number());
        userInfo.setPlace_id(collectionBean.getPlace_id());
        userInfo.setPlace_name(collectionBean.getPlace_name());
        userInfo.setPractitioner_id(collectionBean.getPractitioner_id());
        userInfo.setSession_id(collectionBean.getSession_id());
        userInfo.setSex(collectionBean.getSex());
        userInfo.setTencent_im_sign(collectionBean.getTencent_im_sign());

        // 遍历Links集合 并存入links中
        List<loginDataLinks> links = new ArrayList<loginDataLinks>();
        for (LoginDataResponse.CollectionBean.LinksBean linksBean : collectionBean.getLinks()){

            loginDataLinks dataLinks = new loginDataLinks();
            dataLinks.setRel(linksBean.getRel());
            dataLinks.setHref(linksBean.getHref());
            dataLinks.setTitle(linksBean.getTitle());

            links.add(dataLinks);
            dataLinks.save();
        }
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
        LoginActivity.this.finish();
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
     * 重写返回手机返回按钮事件
     */
    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(mIntent);
        super.onBackPressed();
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


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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






















