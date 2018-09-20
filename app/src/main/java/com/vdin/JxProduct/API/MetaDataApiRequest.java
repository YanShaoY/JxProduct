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
                // 成功
                if (response.isSuccessful()) {

                    String responseText = response.body().string();
                    if (!TextUtils.isEmpty(responseText)) {

                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            LoginDataResponse loginDataResponse = new Gson().fromJson(jsonObject.toString(), LoginDataResponse.class);
                            mainHandle.post(() -> {
                                callBack.completeBlock(true, loginDataResponse);
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mainHandle.post(() -> {
                                callBack.completeBlock(false, "数据解析失败");
                            });
                        }

                    }

                } else { // 失败
                    mainHandle.post(() -> {
                        callBack.completeBlock(false, "网络请求失败");
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
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    callBack.completeBlock(true, jsonString);
                } else {
                    callBack.completeBlock(false, "请求失败");
                }
            }
        });
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

    /**
     * 自定义请求回调接口
     */
    @FunctionalInterface
    public interface NetWorkCallBack {
        void completeBlock(boolean isSuccess, Object object);
    }

}
