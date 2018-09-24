package com.vdin.JxProduct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.vdin.JxProduct.R;
import com.vdin.JxProduct.View.ViewHolder;
import com.vdin.JxProduct.db.HistoryListDB;

import java.util.ArrayList;

/**
 * @开发者 YanSY
 * @日期 2018/9/21
 * @描述 Vdin成都研发部
 */
public class WorkHistoryListAdapter extends BaseAdapter {

    // 存储视图上下文
    Context mContext;
    // 历史信息列表
    ArrayList<HistoryListDB> historyArr;
    // 创建视图持有器
    ViewHolder mHolder = null;
    // 视图点击事件监听
    View.OnClickListener clickListener;

    /**
     * 重写视图适配器的构造方法
     * @param context 持有上下文
     * @param list  DataSource
     * @param clickListener 事件监听
     */
    public WorkHistoryListAdapter(Context context, ArrayList<HistoryListDB> list,View.OnClickListener clickListener){
        this.mContext = context;
        this.historyArr = list;
        this.clickListener = clickListener;
    }

    /**
     * 获取cell个数
     * @return 0 : historyArr.size()
     */
    @Override
    public int getCount() {
        return historyArr == null ? 0 : historyArr.size();
    }

    /**
     * 获取对应cell的数据源
     * @return 0 : historyArr.get(position)
     */
    @Override
    public Object getItem(int position) {
        return historyArr == null ? 0 : historyArr.get(position);
    }

    /**
     * 获取对应cell的id
     * @param position 对应cell的Index
     * @return 返回id position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取对应cell的布局
     * @param position 位置
     * @param convertView 视图
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 初始化视图
        if (convertView == null){
            // 布局泵获取itemView的布局
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_work_history_listview,null);
        }
        // 使用Viewholder类的静态方法缓存控件，并返回缓存后的控件
        ImageView photoImgView = (ImageView)mHolder.get(convertView,R.id.photo_img_view);


        return convertView;
    }

}















