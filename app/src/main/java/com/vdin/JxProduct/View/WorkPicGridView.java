package com.vdin.JxProduct.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @开发者 YanSY
 * @日期 2018/9/18
 * @描述 Vdin成都研发部
 */
public class WorkPicGridView extends GridView {

    public WorkPicGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkPicGridView(Context context) {
        super(context);
    }

    public WorkPicGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
