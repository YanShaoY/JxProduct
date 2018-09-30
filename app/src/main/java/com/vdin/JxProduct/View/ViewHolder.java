package com.vdin.JxProduct.View;

import android.util.SparseArray;
import android.view.View;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class ViewHolder {

    // 添加私有构造函数防止外部实例化
    private ViewHolder() {
    }

    /**
     * 用来缓存控件，优化加载
     *
     * @param view itemView的布局
     * @param id   itemView布局中需要缓存控件的id
     * @return 缓存后的控件（textView、imageView...等控件）
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        // 获取itemView的ViewHolder对象，并将其转型为SparseArray<View>
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            // 如果viewHolder为空，就新建一个
            viewHolder = new SparseArray<View>();
            // 给view设置tag标签
            view.setTag(viewHolder);
        }
        // 根据控件的id获取itemView布局的控件
        View childView = viewHolder.get(id);
        if (childView == null) {
            // 如果还没有缓存该控件，那么就根据itemView找到该控件
            childView = view.findViewById(id);
            // 缓存该控件
            viewHolder.put(id, childView);
        }
        // 返回缓存好的控件
        return (T) childView;
    }

}
