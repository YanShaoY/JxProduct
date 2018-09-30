package com.vdin.JxProduct.API;

import android.os.Looper;
import android.text.TextUtils;

import com.vdin.JxProduct.Util.HttpUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @开发者 YanSY
 * @日期 2018/9/27
 * @描述 Vdin成都研发部
 */
public class HttpRequestApi {

    /**
     * GET 请求二次封装
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 回调函数
     */
    public static void getRequest(String url, Map<String, Object> params, NetWorkCallBack callBack) {

        // 01 拼接参数
        StringBuilder requestUrl = new StringBuilder(url);
        requestUrl.append("?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            requestUrl.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue() == null ? "" : entry.getValue())
                    .append("&");
        }
        requestUrl.deleteCharAt(requestUrl.length() - 1);

        // 02 发起请求

        HttpUtil.getRequest(new String(requestUrl), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.completeBlock(false, "网络请求失败，请检查网路设置");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取body数据
                String jsonString = response.body().string();
                // 判断body数据是否为空
                if (!TextUtils.isEmpty(jsonString)) {
                    // 回传数据
                    callBack.completeBlock(true, jsonString);
                } else {
                    // 回传数据
                    callBack.completeBlock(false, "数据解析失败");
                }
            }
        });

    }

    /**
     * POST 请求二次封装
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 回调函数
     */
    public static void postRequest(String url, Object params, NetWorkCallBack callBack) {

        HttpUtil.postRequest(url, params, new Callback() {

            // 请求失败回调
            @Override
            public void onFailure(Call call, IOException e) {

                // 获取主线程
                android.os.Handler mainHandle = new android.os.Handler(Looper.getMainLooper());
                // 回传数据
                mainHandle.post(() -> {
                    callBack.completeBlock(false, "网络请求失败，请检查网路设置");
                });
            }

            // 请求成功回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // 获取主线程
                android.os.Handler mainHandle = new android.os.Handler(Looper.getMainLooper());
                // 获取body数据
                String jsonString = response.body().string();
                // 判断body数据是否为空
                if (!TextUtils.isEmpty(jsonString)) {
                    // 回传数据
                    mainHandle.post(() -> {
                        callBack.completeBlock(true, jsonString);
                    });

                } else {
                    // 回传数据
                    mainHandle.post(() -> {
                        callBack.completeBlock(false, "数据解析失败");
                    });
                }
            }
        });

    }

}
