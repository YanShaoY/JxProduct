package com.vdin.JxProduct.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.vdin.JxProduct.Activity.InfoRegistActivity;
import com.vdin.JxProduct.Model.WorkRegistAddPicInfo;
import com.vdin.JxProduct.OSSService.FileUtils;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.View.ViewHolder;

import java.util.ArrayList;

/**
 * @开发者 YanSY
 * @日期 2018/9/19
 * @描述 Vdin成都研发部
 */
public class WorkRegistAddPicAdapter extends BaseAdapter {

    // 存储视图上下文
    Context mContext;
    // 图片信息列表
    ArrayList<WorkRegistAddPicInfo> picInfoArr;
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
    public WorkRegistAddPicAdapter(Context context, ArrayList<WorkRegistAddPicInfo> list, View.OnClickListener clickListener){
        this.mContext = context;
        this.picInfoArr = list;
        this.clickListener = clickListener;
    }

    /**
     * 获取cell个数
     * @return 0 : picInfoArr.size()
     */
    @Override
    public int getCount() {
        return picInfoArr == null ? 0 : picInfoArr.size();
    }

    /**
     * 获取对应cell的数据源
     * @return 0 : picInfoArr.get(position)
     */
    @Override
    public Object getItem(int position) {
        return picInfoArr == null ? 0 : picInfoArr.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_work_addpic_gridview,null);
        }

        // 使用Viewholder类的静态方法缓存控件，并返回缓存后的控件
        // 照片展示视图
        ImageView photoImgView = (ImageView)ViewHolder.get(convertView,R.id.photo_img_view);
        photoImgView.setTag(position);
        // 删除图片视图
        ImageView deleteImgView = (ImageView)ViewHolder.get(convertView,R.id.delete_img_view);
        deleteImgView.setTag(position);

        // 设置照片展示
        photoImgView.setImageBitmap(picInfoArr.get(position).bitmap);

        // 根据path设置删除图片的展示
        if (picInfoArr.get(position).path.equals("-1")){
            deleteImgView.setVisibility(View.GONE);
            photoImgView.setOnClickListener(clickListener);
        }else {
            deleteImgView.setVisibility(View.VISIBLE);
            photoImgView.setOnClickListener(null);
        }

        deleteImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询对应的图片数据
                WorkRegistAddPicInfo delInfo = picInfoArr.get((int)v.getTag());
                // 删除本地路径的原图
                FileUtils.delFileByLocalPath(delInfo.path);
                // 删除对应的照片信息
                picInfoArr.remove((int)v.getTag());
                // 若无默认拍照视图 加载到最后
                if (!picInfoArr.contains(InfoRegistActivity.defaultAddPic)){
                    picInfoArr.add(InfoRegistActivity.defaultAddPic);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }













}
