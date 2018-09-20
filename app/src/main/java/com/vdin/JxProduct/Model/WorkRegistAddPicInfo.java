package com.vdin.JxProduct.Model;

import android.graphics.Bitmap;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class WorkRegistAddPicInfo {

    // 未上传
    public static final int State_No = 0;
    // 上传中
    public static final int State_In = 1;
    // 上传完成
    public static final int State_Success = 2;

    // 图片上传状态
    public int uploadState = State_No;
    // 图片
    public Bitmap bitmap;
    // 本地路径
    public String path;
    // web URL
    public String url;

}
