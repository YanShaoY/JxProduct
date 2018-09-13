package com.vdin.JxProduct.Util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vdin.JxProduct.App.MainApplication;
import com.vdin.JxProduct.Gson.MetaDataResponse;
import com.vdin.JxProduct.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @开发者 YanSY
 * @日期 2018/9/12
 * @描述 Vdin成都研发部
 */
public class MetaDataUtility {

    /**
     * 请求元数据
     */
    public static void requestMetaDataSource() {

        MetaDataResponse metaDataFile = MetaDataUtility.getMetaDataFile();
        if (metaDataFile != null && metaDataFile.isSuccess()){
            return;
        }

        String URLStr = MainApplication.getContext().getString(R.string.metadata_url);

        HttpUtil.getRequest(URLStr, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
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
                            saveMetaDataFile(metaDataStr);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    /**
     * 存储元数据
     *
     * @param metaDataStr 元数据信息
     * @return
     */
    private static Boolean saveMetaDataFile(String metaDataStr) {

        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;
        try {
            fileOutputStream = MainApplication.getContext().openFileOutput("metaData", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(metaDataStr);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * 获取元数据
     *
     * @return 返回元数据信息
     */
    public static MetaDataResponse getMetaDataFile() {

        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            inputStream = MainApplication.getContext().openFileInput("metaData");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (!TextUtils.isEmpty(content.toString())) {
            try {
                JSONObject jsonObject = new JSONObject(content.toString());
                String metaDataStr = jsonObject.toString();
                MetaDataResponse metaDataResponse = new Gson().fromJson(metaDataStr, MetaDataResponse.class);
                if (metaDataResponse.isSuccess()) {
                    return metaDataResponse;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}












