package com.vdin.JxProduct.Util;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、147(TD)、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、177（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或4或5或8或7，其他位置的可以为0-9
    */

        String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }


    }

    /**
     * 验证身份证号是否符合规则(没有判断具体的月份对应的天数)
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {

        String regx = "[0-9]{17}[Xx]";
//        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
//        return text.matches(regx) || text.matches(reg1) || text.matches(regex);

        if ((text.matches(regx) || text.matches(regex)) == true) {
            String strYear = text.substring(6, 10);// 年份
            String strMonth = text.substring(10, 12);// 月份
            int Month = Integer.valueOf(strMonth);
            String strDay = text.substring(12, 14);// 日份
            int Day = Integer.valueOf(strDay);
            String Year = strYear + "-" + strMonth + "-" + strDay;
            // 获取系统时间
            SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
            String year = yyyy.format(new Date());
            try {
                Date minyear = yyyy.parse("1900-01-01");
                Date maxyear = yyyy.parse(year);
                Date years = yyyy.parse(Year);
                if ((minyear.getTime() <= years.getTime()) && (years.getTime() <= maxyear.getTime())) {
                    if ((1 <= Month) && (Month <= 12)) {
                        if ((1 <= Day) && (Day <= 31)) {
                            return true;
                        } else {
                            return false;
                        }

                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;

            }
        } else {
            return false;
        }

    }

}
