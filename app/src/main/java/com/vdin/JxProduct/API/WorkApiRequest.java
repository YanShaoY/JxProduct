package com.vdin.JxProduct.API;

import com.vdin.JxProduct.App.MainApplication;
import com.vdin.JxProduct.Gson.WorkAddRegistGson;
import com.vdin.JxProduct.OSSService.OnUploadOssCallbackListener;
import com.vdin.JxProduct.OSSService.UploadFileOSS;
import com.vdin.JxProduct.Service.UserInfoService;
import com.vdin.JxProduct.Util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @开发者 YanSY
 * @日期 2018/9/18
 * @描述 Vdin成都研发部
 */
public class WorkApiRequest {

    //获取历史详情
    public static void queryHistoryBusinessInfo(String maintenanceId, NetWorkCallBack callBack) {
        String url = UserInfoService.getInstance().getVehicleMaintenancesInfoUrl().replace("{maintenanceId}", maintenanceId);
        HttpUtil.getRequest(url, new Callback() {
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

    //历史业务查询
    public static void queryHistoryBusiness(Map<String, Object> params, NetWorkCallBack callBack) {
        String url =UserInfoService.getInstance().getVehicleMaintenancesUrl();
        HttpRequestApi.getRequest(url,params,callBack);
    }

    //新增登记
    public static void addRegister(WorkAddRegistGson params, NetWorkCallBack callBack) {
        String url = UserInfoService.getInstance().getAddRegisterUrl();
        HttpRequestApi.postRequest(url,params,callBack);

//        HttpUtil.postRequest(url, params, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                callBack.completeBlock(false, e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String jsonString = response.body().string();
//                    callBack.completeBlock(true, jsonString);
//                } else {
//                    callBack.completeBlock(false, "请求失败");
//                }
//            }
//        });
    }

    //查询字典表颜色
    public static void queryDicColor(NetWorkCallBack callBack) {

        String url = UserInfoService.getInstance().getDictDisplaysUrl() + "?typeList=vehicle_color";
        HttpUtil.getRequest(url, new Callback() {
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
     * 上传照片到OSS服务器
     *
     * @param photoPath 身份证照片本地路径地址
     */

    public static void upLoadPhoto(String photoPath, int index, NetWorkCallBack callBack) {

        UploadFileOSS.upladFileOSS(MainApplication.getContext(), photoPath, index, new OnUploadOssCallbackListener() {
            @Override
            public void uploadFail(int index, String msg, String filePath) {

                Map<String, Object> map = new HashMap<>();
                map.put("index", index);
                map.put("msg", msg);
                map.put("filePath", filePath);
                callBack.completeBlock(false, map);
            }

            @Override
            public void uploadSuccessBackData(int index, String netUrl, String filePath) {

                Map<String, Object> map = new HashMap<>();
                map.put("index", index);
                map.put("netUrl", netUrl);
                map.put("filePath", filePath);
                callBack.completeBlock(true, map);
            }

            @Override
            public void uploadPercent(int percent) {

            }
        });

    }

}
