package com.vdin.JxProduct.API;

import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vdin.JxProduct.App.MainApplication;
import com.vdin.JxProduct.Gson.LoginDataResponse;
import com.vdin.JxProduct.Gson.MetaDataResponse;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.MetaDataService;
import com.vdin.JxProduct.Service.UserInfoService;
import com.vdin.JxProduct.Util.HttpUtil;
import com.vdin.JxProduct.Util.StringUtils;
import com.vdin.JxProduct.Util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePalApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class MetaDataApiRequest {

    /**
     * 请求元数据
     *
     * @param callBack 请求回调
     */
    public static void requestMetaDataSource(NetWorkCallBack callBack) {

        String URLStr = MetaDataApiRequest.getMetadataUrl();

        HttpUtil.getRequest(URLStr, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.completeBlock(false, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取请求到的数据
                String responseText = response.body().string();
                if (!TextUtils.isEmpty(responseText)) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseText);
                        String metaDataStr = jsonObject.toString();
                        MetaDataResponse metaDataResponse = new Gson().fromJson(metaDataStr, MetaDataResponse.class);
                        if (metaDataResponse.isSuccess()) {
                            callBack.completeBlock(true, metaDataStr);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callBack.completeBlock(false, e);
                    }

                } else {
                    callBack.completeBlock(false, "请求元数据失败");
                }

            }
        });
    }

    /**
     * 发起登录请求
     *
     * @param username 用户姓名
     * @param password 用户密码
     * @param callBack 请求回调
     */
    public static void login(String username, String password, NetWorkCallBack callBack) {

        // 获取登录接口
        String url = MetaDataService.getInstance().getLoginURL();
        if (StringUtils.isEmpty(url)) {
            MetaDataService.getInstance().initMetadata();
            callBack.completeBlock(false, "正在获取元数据");
            return;
        }

        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("device_token", "1234560001");
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");
        params.put("packageName", "cn.com.vdin.scMVMT.androidphone");
        params.put("password", password);
        params.put("pusher_code", "1");

        //网络请求登录
        HttpUtil.postRequest(url, params, new Callback() {

            // 请求失败回调
            @Override
            public void onFailure(Call call, IOException e) {
                android.os.Handler mainHandle = new android.os.Handler(Looper.getMainLooper());
                mainHandle.post(() -> {
                    callBack.completeBlock(false, e);
                });
            }

            // 请求成功回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取主线程
                android.os.Handler mainHandle = new android.os.Handler(Looper.getMainLooper());

                if (response.code() >= 500){
                    // 回传数据
                    mainHandle.post(() -> {
                        // 回传数据
                        callBack.completeBlock(false, "无法连接服务器");
                    });
                    return;
                }

                // 获取body数据
                String responseText = response.body().string();
                // 判断body数据是否为空
                if (!TextUtils.isEmpty(responseText)) {

                    LoginDataResponse loginDataResponse = new Gson().fromJson(responseText, LoginDataResponse.class);
                    mainHandle.post(() -> {
                        callBack.completeBlock(true, loginDataResponse);
                    });

                } else {
                    mainHandle.post(() -> {
                        callBack.completeBlock(false, "数据解析失败");
                    });
                }
            }
        });
    }

    //退出登录
    public static void logout(NetWorkCallBack callBack) {

        String url = UserInfoService.getInstance().getLogoutUrl();
        HttpUtil.deleteRequest(url, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.completeBlock(false, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取主线程
                android.os.Handler mainHandle = new android.os.Handler(Looper.getMainLooper());

                if (response.code() >= 500){
                    // 回传数据
                    mainHandle.post(() -> {
                        // 回传数据
                        callBack.completeBlock(false, "无法连接服务器");
                    });
                    return;
                }

                // 获取body数据
                String jsonString = response.body().string();
                // 判断body数据是否为空
                if (!TextUtils.isEmpty(jsonString)) {
                    mainHandle.post(() -> {
                        callBack.completeBlock(true, jsonString);
                    });

                } else {
                    mainHandle.post(() -> {
                        callBack.completeBlock(false, "数据解析失败");
                    });
                }
            }
        });
    }

    /**
     * 发起激活账号请求
     *
     * @param username 用户名
     * @param smsToken 备案验证码
     * @param callBack 请求回调
     */
    public static void activations(String username, String smsToken, NetWorkCallBack callBack) {

        // 获取激活接口
        String url = MetaDataService.getInstance().getActivationsURL();

        if (StringUtils.isEmpty(url)) {
            MetaDataService.getInstance().initMetadata();
            callBack.completeBlock(false, "正在获取元数据");
            return;
        }

        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");
        params.put("sms_token", smsToken);

        //网络请求激活账户
        HttpRequestApi.postRequest(url,params,callBack);
    }

    /**
     * 请求重发验证码 激活账户
     *
     * @param username 用户名
     * @param callBack
     */
    public static void resendConfirmationCode(String username, NetWorkCallBack callBack) {

        // 获取重发验证码接口
        String url = MetaDataService.getInstance().getConfirmationCodeURL();

        if (StringUtils.isEmpty(url)) {
            MetaDataService.getInstance().initMetadata();
            callBack.completeBlock(false, "正在获取元数据");
            return;
        }

        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");

        //网络请求重发验证码
        HttpRequestApi.postRequest(url,params,callBack);
    }

    /**
     * 设置密码 适用于激活(忘记密码)界面 第一次设置（通过验证码设置）
     *
     * @param username 用户名
     * @param smsToken 备案验证码
     * @param password 用户密码
     * @param callBack 请求回调
     */
    public static void createPassword(String username, String smsToken, String password, NetWorkCallBack callBack) {
        // 获取设置密码的URL
        String url = MetaDataService.getInstance().getCreatePwdURL();

        if (StringUtils.isEmpty(url)) {
            MetaDataService.getInstance().initMetadata();
            callBack.completeBlock(false, "正在获取元数据");
            return;
        }

        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");
        params.put("password", password);
        params.put("sms_token", smsToken);

        //网络请求设置密码
        HttpRequestApi.postRequest(url,params,callBack);
    }

    /**
     * 生成忘记密码的验证码(从业人员激活状态)/重新设置密码
     *
     * @param username 用户名
     * @param callBack 请求回调
     */
    public static void sendForgetPwdReCode(String username, NetWorkCallBack callBack) {

        // 获取发送证码接口
        String url = MetaDataService.getInstance().getForgetPwdReCodeURL();

        if (StringUtils.isEmpty(url)) {
            MetaDataService.getInstance().initMetadata();
            callBack.completeBlock(false, "正在获取元数据");
            return;
        }

        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");

        //网络请求重发验证码
        HttpRequestApi.postRequest(url,params,callBack);
    }

    /**
     * 校验（重新设置密码的）验证码
     *
     * @param username 用户名
     * @param smsToken 备案验证码
     * @param callBack 请求回调
     */
    public static void checkResetPwdSmsToken(String username, String smsToken, NetWorkCallBack callBack) {

        // 获取校验接口
        String url = MetaDataService.getInstance().getLoadPwdReCodeURL();

        if (StringUtils.isEmpty(url)) {
            MetaDataService.getInstance().initMetadata();
            callBack.completeBlock(false, "正在获取元数据");
            return;
        }

        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");
        params.put("sms_token", smsToken);

        // 校验验证码
        HttpRequestApi.getRequest(url,params,callBack);
    }

    /**
     * 重新设置密码 已激活状态
     * @param username     用户名
     * @param oldPassword  老密码
     * @param password     新密码
     * @param callBack     请求回调
     */
    public static void resetPasswordRequest(String username, String oldPassword, String password, NetWorkCallBack callBack){

        // 获取重新设置密码的URL
        String url = MetaDataService.getInstance().getPwdRecoveryURL();
        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("industry_code", "MVMT");
        params.put("login_name", username);
        params.put("login_type", "1");
        params.put("ownerType", "1");
        params.put("old_password", oldPassword);
        params.put("password", password);

    }

    /**
     * 获取元数据请求接口
     *
     * @return 测试环境接口以及正式环境接口
     */
    private static String getMetadataUrl() {

        if (SystemUtil.isApkDebug(LitePalApplication.getContext())) {

            String URLStr = MainApplication.getContext().getString(R.string.metadata_debug_url);
            return URLStr;

        } else {
            String URLStr = MainApplication.getContext().getString(R.string.metadata_release_url);
            return URLStr;
        }

    }

}








