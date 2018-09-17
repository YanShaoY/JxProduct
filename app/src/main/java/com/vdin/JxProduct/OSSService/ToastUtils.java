package com.vdin.JxProduct.OSSService;

import android.content.Context;
import android.widget.Toast;

/**
 * 管理显示toast
 */
public class ToastUtils {

    public static void showToast(Context mContext, String message){
        Toast.makeText(mContext,message, Toast.LENGTH_SHORT).show();
    }


}
