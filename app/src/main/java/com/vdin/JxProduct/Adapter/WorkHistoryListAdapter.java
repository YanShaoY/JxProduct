package com.vdin.JxProduct.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.StringUtils;
import com.vdin.JxProduct.View.ImagePreviewDialog;
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

    /**
     * 重写视图适配器的构造方法
     * @param context 持有上下文
     * @param list  DataSource
     */
    public WorkHistoryListAdapter(Context context, ArrayList<HistoryListDB> list){
        this.mContext = context;
        this.historyArr = list;
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
        // 登记图片
        ImageView photoImgView = (ImageView)ViewHolder.get(convertView,R.id.history_list_img);
        Glide.with(mContext).load(historyArr.get(position).getPic()).into(photoImgView);
        photoImgView.setTag(R.id.dialog_tag,position);

        // 车牌号码文本框
        TextView chepaiText = (TextView)ViewHolder.get(convertView,R.id.history_list_chepai);
        chepaiText.setText(historyArr.get(position).getChepai());

        // 车辆颜色
        TextView colorText = (TextView)ViewHolder.get(convertView,R.id.history_list_color);
        colorText.setText(historyArr.get(position).getColor());

        // 姓名
        TextView nameText = (TextView)ViewHolder.get(convertView,R.id.history_list_name);
        String name = historyArr.get(position).getName();
        name = StringUtils.cutStrToName(name);
        nameText.setText(name);

        // 类型
        TextView typeText = (TextView)ViewHolder.get(convertView,R.id.history_list_type);
        typeText.setText(historyArr.get(position).getType());

        // 时间
        TextView timeText = (TextView)ViewHolder.get(convertView,R.id.history_list_time);
        timeText.setText(historyArr.get(position).getTime());


        photoImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionCount = (int)v.getTag(R.id.dialog_tag);
                String url = historyArr.get(positionCount).getPic();
                ImagePreviewDialog dialog = new ImagePreviewDialog(mContext);
                dialog.setShowImg(url).show();
            }
        });

        return convertView;
    }

}















