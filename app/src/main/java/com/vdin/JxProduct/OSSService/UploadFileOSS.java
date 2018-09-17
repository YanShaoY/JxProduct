package com.vdin.JxProduct.OSSService;

import android.content.Context;
import android.os.Handler;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import java.io.File;

public class UploadFileOSS {

	/**
	 * 
	 * @param mContext
	 * @param objectKey
	 * @param filePath
	 * @param callback
	 */
	public static void upladFileOSS(Context mContext, String objectKey, String filePath, OSSCompletedCallback<PutObjectRequest,
			PutObjectResult> callback){
		OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OssParam.ID, OssParam.KEY);
		OSS oss = new OSSClient(mContext, OssParam.ENDPOINT, credentialProvider);
		PutObjectRequest put = new PutObjectRequest(OssParam.BUCKETNAME, objectKey, filePath);
		OSSAsyncTask task = oss.asyncPutObject(put,callback);
	}

	private static Handler handler = new Handler();

	/**
	 *
	 * @param mContext.
	 * @param filePath 文件本地地址
	 * @param type  请求type listener会返回相同以方便开发者确定是那个请求返回的
	 * @param obj 如需带参数在listener中返回 可传空
	 * @param listener
	 */
	public static void upladFileOSS(Context mContext, final String filePath, final int index, final OnUploadOssCallbackListener listener) {
			final File file = new File(filePath);

			final String fileType[] = filePath.split("\\.");

			String fileName = DateUtils.getNowTimeStr() + "-" + java.util.UUID.randomUUID().toString() + "." + fileType[fileType.length - 1];

			final String objectKey = OssParam.OBJECT_KEY + fileName;
			OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OssParam.ID,
					OssParam.KEY);
			final OSS oss = new OSSClient(mContext, OssParam.ENDPOINT, credentialProvider);
			PutObjectRequest put = new PutObjectRequest( OssParam.BUCKETNAME, objectKey, filePath);
			// 异步上传时可以设置进度回调
			put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
				@Override
				public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
					if (totalSize > 0)
						listener.uploadPercent((int) (currentSize * 100 / totalSize));
				}
			});
			OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
				@Override
				public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							String url = oss.presignPublicObjectURL( OssParam.BUCKETNAME, objectKey);
							System.out.println(url);
							listener.uploadSuccessBackData(index, url, filePath);
						}
					});
				}

				@Override
				public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							listener.uploadFail(index, "上传图片失败", filePath);
						}
					});
					// 请求异常
					if (e != null) {
						// 本地异常如网络异常等
						e.printStackTrace();
					}
				}
			});
	}
}
