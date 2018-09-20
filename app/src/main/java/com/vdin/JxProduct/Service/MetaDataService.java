package com.vdin.JxProduct.Service;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.vdin.JxProduct.API.MetaDataApiRequest;
import com.vdin.JxProduct.App.MainApplication;
import com.vdin.JxProduct.Gson.MetaDataResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class MetaDataService {

    private List<MetaDataResponse.CollectionBean.LinksBean> metaDataLinks;

    /**
     * 构造单例
     *
     * @return 返回本类实例
     */
    public static MetaDataService getInstance() {
        return MetaDataServiceHolder.instance;
    }

    private static class MetaDataServiceHolder {
        private static final MetaDataService instance = new MetaDataService();
    }

    private MetaDataService() {};


    //初始化元数据
    public void initMetadata() {
        MetaDataApiRequest.requestMetaDataSource(new MetaDataApiRequest.NetWorkCallBack() {
            @Override
            public void completeBlock(boolean isSuccess, Object object) {
                if (isSuccess) {
                    saveMetaDataFile((String) object);
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
    private Boolean saveMetaDataFile(String metaDataStr) {

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
    public MetaDataResponse getMetaDataFile() {

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

    /**
     * 获取登录的URL
     *
     * @return 返回登录的URL
     */
    public String getLoginURL() {
        return getMetaDataURLForRel("dosser/create/session");
    }


    /**
     * 获取元数据的links
     *
     * @return
     */
    public List<MetaDataResponse.CollectionBean.LinksBean> getMetaDataLinks() {

        if (metaDataLinks == null || metaDataLinks.size() == 0) {
            MetaDataResponse metaDataFile = getMetaDataFile();
            metaDataLinks = metaDataFile.getCollection().get(0).getLinks();
        }

        if (metaDataLinks == null || metaDataLinks.size() == 0) {
            initMetadata();
        }

        return metaDataLinks;
    }


    /**
     * 传入对应的rel 返回对应的url
     *
     * @param rel
     * @return
     */
    public String getMetaDataURLForRel(String rel) {

        if (metaDataLinks == null || metaDataLinks.size() == 0) {
            MetaDataResponse metaDataFile = getMetaDataFile();
            metaDataLinks = metaDataFile.getCollection().get(0).getLinks();
        }

        if (metaDataLinks == null || metaDataLinks.size() == 0) {
            initMetadata();
            return null;
        }

        for (MetaDataResponse.CollectionBean.LinksBean linksBean : metaDataLinks) {
            if (linksBean.getRel().equals(rel)) {
                return linksBean.getHref();
            }
        }
        return null;
    }


}
