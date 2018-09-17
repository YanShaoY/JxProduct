/*
 *  四川生学教育科技有限公司
 *  Copyright (c) 2015-2025 Founder Ltd. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Founder. You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the agreements
 *  you entered into with Founder.
 */

package com.vdin.JxProduct.OSSService;

/**

 *
 * 文件上传
 *
 */

public interface OnUploadOssCallbackListener {

    void uploadFail(int index, String msg, String filePath);  //上传失败

    void uploadSuccessBackData(int index, String netUrl, String filePath);  //上传成功

    void uploadPercent(int percent);  //上传进度

}
