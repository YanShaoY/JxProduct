package com.vdin.JxProduct.Util;

import android.content.Context;

/**
 * @开发者 YanSY
 * @日期 2018/9/14
 * @描述 Vdin成都研发部
 */
public class ToolUtil {

    /**
     * dp 转px
     * @param context 上下文
     * @param dpValue dp
     * @return  转换后的px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
