package com.vdin.JxProduct.Util;

import java.util.Collection;

/**
 * @开发者 YanSY
 * @日期 2018/9/27
 * @描述 Vdin成都研发部
 */
public class ListUtils {
    public static boolean isEmpty(Collection<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

}
