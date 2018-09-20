package com.vdin.JxProduct.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.StringUtils;

/**
 * @开发者 YanSY
 * @日期 2018/9/20
 * @描述 Vdin成都研发部
 */
public class ConfirmDialog extends Dialog {

    // 保存本类实例
    private ConfirmDialog myConfirmDialog;
    // 弹出上下文
    public Context myContext;
    // 顶部图标
    public int  dialogBgimgRes;
    // 主标题
    public String titleMsg;
    // 主标题颜色
    public String titleColor;
    // 副标题
    public String tipMsg;
    // 确定按钮
    public String sureBtTitle;
    // 取消按钮
    public String cancelBtTitle;
    // 点击响应的回调
    public ConfirmDialogAction dialogAction;

    /**
     * 自定义警告弹窗接口 相当于block
     */
    public interface ConfirmDialogAction {

        // 确定按钮点击响应
        void sureButtonAction();
        // 取消按钮点击响应
        void cancelButtonAction();
    }

    /**
     * 创建本类实例
     * @param context 调用上下文
     */
    public ConfirmDialog(@NonNull Context context) {
        super(context);
        myConfirmDialog = this;
        myContext = context;
        dialogBgimgRes = R.mipmap.ask;
        titleMsg = "默认主标题";
        titleColor = "#ff6464";
        tipMsg = "默认副标题";
        sureBtTitle = "确定";
    }

    /**
     * 弹出警告弹窗
     * @param title
     * @param tip
     * @param action
     */
    public void showWarningDialog(String title,String tip,ConfirmDialogAction action){
        dialogBgimgRes = R.mipmap.ask;
        titleMsg = title;
        titleColor = "#ff6464";
        tipMsg = tip;
        sureBtTitle = "确定";
        cancelBtTitle = "取消";
        dialogAction = action;
        this.show();
    }


    /**
     * 视图创建时 初始化子视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    /**
     * 初始化子视图
     */
    public void initDialog(){

        // 布局控制
        LayoutInflater inflater = LayoutInflater.from(myContext);
        // dialog视图
        View view = inflater.inflate(R.layout.dialog_base, null);
        // 顶部图标
        ImageView dialogBgimg = (ImageView) view.findViewById(R.id.dialog_bgimg);
        // 主标题
        TextView dialogTitlename = (TextView) view.findViewById(R.id.dialog_titlename);
        // 副标题
        TextView dialogTips = (TextView) view.findViewById(R.id.dialog_tips);
        // 确定按钮
        Button dialogBtSure = (Button) view.findViewById(R.id.dialog_bt_sure);
        // 取消按钮
        Button dialogBtCl = (Button) view.findViewById(R.id.dialog_bt_cl);

        // 设置顶部图标图片
        dialogBgimgRes = dialogBgimgRes != 0 ? dialogBgimgRes : R.mipmap.ask;
        dialogBgimg.setBackgroundResource(dialogBgimgRes);

        // 设置主标题
        dialogTitlename.setText(titleMsg);
        dialogTitlename.setTextColor(Color.parseColor(titleColor));

        // 设置副标题
        dialogTips.setText(tipMsg);

        // 设置确定按钮
        dialogBtSure.setVisibility(View.VISIBLE);
        dialogBtSure.setText(sureBtTitle);

        // 设置取消按钮
        if (StringUtils.isEmpty(cancelBtTitle)){
            dialogBtCl.setVisibility(View.GONE);
        }else {
            dialogBtCl.setVisibility(View.VISIBLE);
            dialogBtCl.setText(cancelBtTitle);
        }

        // 设置按钮点击监听 取消
        dialogBtCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAction.cancelButtonAction();
                myConfirmDialog.dismiss();
            }
        });

        // 设置按钮点击监听 确定
        dialogBtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAction.sureButtonAction();
                myConfirmDialog.dismiss();
            }
        });


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        setCanceledOnTouchOutside(false);
        setCancelable(false);

    }



}













