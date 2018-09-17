/*
 *  Copyright (c) 2015-2025 Founder Ltd. All Rights Reserved.
 *
 *  This software is the confidential and proprietary information of
 *  Founder. You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the agreements
 *  you entered into with Founder.
 */

package com.vdin.JxProduct.OSSService;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by hurong on 2017/9/18.
 */

public class PhotoUtils {

    public static final int RESULT_TAKE_CAMERA = 2;  //拍照
    public static final int RESULT_TAKE_ALBUM = 3;  //相册
    public static final int RESULT_TAKE_CUT = 4;  //剪切
    public static final int RESULT_TAKE_FILE = 5;  //选取附件

    public static String currPicUrl = "";  //拍照的图片存储路径
    public static String currPicCropUrl = "";  //剪裁图片的存储路径
    public static  boolean aspectSquare = false;
    public static  boolean  isFixWhiteAndHeight  = false;
    public static int white = 500;
    public static int height = 500;

    public static void setAspectSquare(boolean aspectSquare) {
        PhotoUtils.aspectSquare = aspectSquare;
    }

    public static void setWhiteAndHeight(int white,int height) {
        PhotoUtils.white = white;
        PhotoUtils.height = height;
    }


    public static void setIsFixWhiteAndHeight(boolean isFixWhiteAndHeight) {
        PhotoUtils.isFixWhiteAndHeight = isFixWhiteAndHeight;
    }

    /**
     * activity打开相机
     *
     * @param activity
     */
    public static void takePicture(Activity activity) {
        if (!PermissionUtil.checkCameraPermission(activity)) {
            return;
        }
        String dftErrorMsg = "";
        try {
//            currPicUrl = FileUtils.PIC_TEMP_DIR + File.separator + System.currentTimeMillis() + ".jpg";
            currPicUrl = FileUtils.getPicTempOnExternal(activity);
            // 判断存储卡是否可以用，可用进行存储
            activity.startActivityForResult(getTakePictureIntent(activity), RESULT_TAKE_CAMERA);
        } catch (ActivityNotFoundException ae) {
            dftErrorMsg = "没有找到相机程序";
        } catch (Exception e) {
            dftErrorMsg = "打开相机失败";
        }
        if (!TextUtils.isEmpty(dftErrorMsg)) {
            ToastUtils.showToast(activity, dftErrorMsg);
        }
    }

    /**
     * Fragment打开相机
     *
     * @param activity
     */
    public static void takePicture(Fragment activity) {
        if (!PermissionUtil.checkCameraPermission(activity)) {
            return;
        }
        String dftErrorMsg = "";
        try {
//            currPicUrl = FileUtils.PIC_TEMP_DIR + File.separator + System.currentTimeMillis() + ".jpg";
            currPicUrl = FileUtils.getPicTempOnExternal(activity.getActivity());
            // 判断存储卡是否可以用，可用进行存储
            activity.startActivityForResult(getTakePictureIntent(activity.getContext()), RESULT_TAKE_CAMERA);
        } catch (ActivityNotFoundException ae) {
            dftErrorMsg = "没有找到相机程序";
        } catch (Exception e) {
            dftErrorMsg = "打开相机失败";
        }
        if (!TextUtils.isEmpty(dftErrorMsg)) {
            ToastUtils.showToast(activity.getContext(), dftErrorMsg);
        }
    }

    /**
     * 得到拍照intent
     *
     * @param context
     * @return
     */
    private static Intent getTakePictureIntent(Context context) {
        Intent intentFromCapture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File fileUri = new File(currPicUrl);
        Uri imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(context, "com.bignerdranch.android.myapplication.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
            intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intentFromCapture;
    }

    /**
     * activity打开相册
     *
     * @param activity
     */
    public static void takeAblum(Activity activity) {
        if (!PermissionUtil.checkExternalStorage(activity)) {
            return;
        }
        String dftErrorMsg = "";
        try {
            dftErrorMsg = "";
            activity.startActivityForResult(getTakeAblumIntent(),RESULT_TAKE_ALBUM);
        } catch (Exception e) {
            dftErrorMsg = "打开相册失败";
        }
        if (!TextUtils.isEmpty(dftErrorMsg)) {
            ToastUtils.showToast(activity, dftErrorMsg);
        }
    }

    /**
     * fragment打开相册
     *
     * @param activity
     */
    public static void takeAblum(Fragment activity) {
        if (!PermissionUtil.checkExternalStorage(activity)) {
            return;
        }
        String dftErrorMsg = "";
        try {
            dftErrorMsg = "";
            activity.startActivityForResult(getTakeAblumIntent(),RESULT_TAKE_ALBUM);
        } catch (Exception e) {
            dftErrorMsg = "打开相册失败";
        }
        if (!TextUtils.isEmpty(dftErrorMsg)) {
            ToastUtils.showToast(activity.getContext(), dftErrorMsg);
        }
    }

    /**
     * 得到打开相册的intent
     *
     * @return
     */
    private static Intent getTakeAblumIntent() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return albumIntent;
    }

    /**
     * activity相册选择后裁剪图片
     * @param activity
     * @param data
     */
    public static void startAblumToCrop(Activity activity, Intent data){
        try {
            if (null == data) {
                ToastUtils.showToast(activity,"选取图片失败");
                return;
            }
            PhotoUtils.startPhotoZoom(activity, BitmapUtil.getFilePath(activity, data.getData()));
        } catch (Exception e) {
            ToastUtils.showToast(activity,"选取图片失败");
        }
    }

    /**
     * fragment相册选择后裁剪图片
     * @param activity
     * @param data
     */
    public static void startAblumToCrop(Fragment activity, Intent data){
        try {
            if (null == data) {
                ToastUtils.showToast(activity.getContext(),"选取图片失败");
                return;
            }
            PhotoUtils.startPhotoZoom(activity, BitmapUtil.getFilePath(activity.getContext(), data.getData()));
        } catch (Exception e) {
            ToastUtils.showToast(activity.getContext(),"选取图片失败");
        }
    }

    /**
     * activity裁剪图片方法实现
     *
     * @param activity
     * @param sourceFilePaht
     */
    public static void startPhotoZoom(Activity activity, String sourceFilePaht) {
        activity.startActivityForResult(getPhotoZoomIntent(activity, sourceFilePaht), RESULT_TAKE_CUT);
    }


    /**
     * fragment裁剪图片方法实现
     *
     * @param activity
     * @param sourceFilePaht
     */
    public static void startPhotoZoom(Fragment activity, String sourceFilePaht) {
        activity.startActivityForResult(getPhotoZoomIntent(activity.getActivity(), sourceFilePaht), RESULT_TAKE_CUT);
    }

    /**
     * 得到剪裁图片的intent
     *
     * @param context
     * @param sourceFilePaht
     * @return
     */
    private static Intent getPhotoZoomIntent(Activity context, String sourceFilePaht) {
        currPicCropUrl =  FileUtils.getPicTempOnExternal(context);
        File fileUri = new File(sourceFilePaht);
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imageUri = Uri.fromFile(fileUri);
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(context, "com.bignerdranch.android.myapplication.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setDataAndType(imageUri, "image/*");
        // 设置裁剪
        if (aspectSquare){
            intent.putExtra("aspectX", 1);//aspectX aspectY 是宽高的比例
            intent.putExtra("aspectY", 1);
        }
        if (isFixWhiteAndHeight){
            intent.putExtra("aspectX", white);//按照比例
            intent.putExtra("aspectY", height);
        }
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(currPicCropUrl)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG);
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * 选取附件
     * @param context
     */
    public static void takeFile(Activity context){
        if (!PermissionUtil.checkExternalStorage(context)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        context.startActivityForResult(intent,RESULT_TAKE_FILE);
    }

}
