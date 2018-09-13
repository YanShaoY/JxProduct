package com.vdin.JxProduct.Util;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    //get 请求
    public static void getRequest(String url, okhttp3.Callback callback) {
        Request request = new Request.Builder().url(url).build();
        enqueue(request, callback);
    }

    //post请求
    public static void postRequest(String url, Map<String, Object> params, okhttp3.Callback callback) {
        RequestBody body = addParamToJsonBody(params);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        enqueue(request, callback);
    }

    // put请求
    public static void putRequest(String url, Map<String, Object> params, okhttp3.Callback callback) {
        RequestBody body = addParamToJsonBody(params);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        enqueue(request, callback);
    }

    // delete请求
    public static void deleteRequest(String url, Map<String, Object> params, okhttp3.Callback callback) {
        RequestBody body = addParamToJsonBody(params);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        enqueue(request, callback);
    }

    private static RequestBody addParamToFormBody(String reqbody, Map<String, Object> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (!StringUtils.isEmpty(reqbody)) {
            if (reqbody.startsWith("?")) {
                reqbody =reqbody.substring(1);
            }
            String[] params = reqbody.split("&");
            for (int i = 0; i < params.length; i++) {
                if (params[i].equals("")) {
                    continue;
                }
                String [] kv = params[i].split("=");
                builder.add(kv[0], kv[1]);
            }
        }

        if (map != null) {
            Iterator<Map.Entry<String, Object>> iterable = map.entrySet().iterator();
            for (;iterable.hasNext();){
                Map.Entry<String, Object> kv = iterable.next();
                builder.add(kv.getKey(), kv.getValue().toString());
            }
        }

        return builder.build();
    }

    private static RequestBody addParamToJsonBody(Map<String, Object>map) {
        if (map != null) {
            Gson gson = new Gson();
            String json = gson.toJson(map);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            return requestBody;
        }
        return null;
    }

    public static void enqueue(Request request, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }
}
