package com.vdin.JxProduct.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.vdin.JxProduct.R;

/**
 * @开发者 YanSY
 * @日期 2018/9/25
 * @描述 Vdin成都研发部
 */
public class ImagePreviewDialog extends Dialog {

    // 弹窗上下文
    public Context myContext;
    // 预览视图
    private View contentView;

    /**
     * 构造器方法
     * @param context 上下文
     */
    public ImagePreviewDialog(@NonNull Context context) {
        super(context,R.style.dialog);
        this.myContext = context;
        initDialog();
    }

    /**
     * 视图创建时 初始化子视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置显示的image
     * @param Url 图片的URL地址
     * @return 本类实例
     */
    public ImagePreviewDialog setShowImg(String Url){
        ImageView showImg = (ImageView) contentView.findViewById(R.id.dialog_preview_Image);
        Glide.with(myContext).load(Url).into(showImg);
        return this;
    }

    /**
     * 设置背景点击事件
     * @param clickListener 点击响应
     * @return 本类实例
     */
    public ImagePreviewDialog setBgLayout(View.OnClickListener clickListener){
        ConstraintLayout bgLayout = (ConstraintLayout)contentView.findViewById(R.id.dialog_bg_layout);
        bgLayout.setOnClickListener(null);
        bgLayout.setOnClickListener(clickListener);
        return this;
    }

    /**
     * 初始化弹窗视图
     */
    private void initDialog(){
        // 布局控制
        LayoutInflater inflater = LayoutInflater.from(myContext);
        // dialog视图
        View view = inflater.inflate(R.layout.dialog_img_preview, null);
        this.contentView = view;


        setBgLayout(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        WindowManager.LayoutParams wl = getWindow().getAttributes();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        // 设置点击外围解散
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


}
