package com.vdin.JxProduct.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Timer;
import java.util.TimerTask;

public class GAConfirmDialog extends Dialog {

    public enum DialogStyle {
        DEFAULT, SINGLE, MULTI, INPUT,TIMER; //缺省、单选、多选、输入、计时
    }

    // 弹上下文
    public Context myContext;
    private View contentView;
    DialogStyle style;

    public GAConfirmDialog setupTitle(String title) {
        setupTitle(title, "");
        return this;
    }

    public GAConfirmDialog setupTitle(String title, String titleColor) {
        TextView titleText = contentView.findViewById(R.id.dialog_titlename);
        titleText.setText(StringUtils.isEmpty(title) ? "" : title);
        titleText.setTextColor(StringUtils.isEmpty(titleColor) ? Color.parseColor("#ff6464") : Color.parseColor(titleColor));
        return this;
    }

    public GAConfirmDialog bgIcon(int icon) {
        ImageView imageView = contentView.findViewById(R.id.dialog_bgimg);
        imageView.setBackgroundResource(icon);
        return this;
    }

    public GAConfirmDialog tip(String tip) {
        tip(tip, "");
        return this;
    }

    public GAConfirmDialog tip(String tip, String tipColor) {
        TextView titleText = contentView.findViewById(R.id.dialog_tips);
        titleText.setText(StringUtils.isEmpty(tip) ? "" : tip);
        titleText.setTextColor(StringUtils.isEmpty(tipColor) ? Color.parseColor("#b9b9cd") : Color.parseColor(tipColor));
        return this;
    }

    // 时间倒计时弹窗设置
    public GAConfirmDialog timerText(String title){
        timerText(title,"",null);
        return this;
    }

    public GAConfirmDialog timerText(String title, String titleColor, Integer background){

        switch (style){
            case TIMER:
                TextView timerText = contentView.findViewById(R.id.timer_text);
                timerText.setText(StringUtils.isEmpty(title) ? "确定" : title);
                timerText.setTextColor(StringUtils.isEmpty(titleColor) ? Color.parseColor("#ffffff") : Color.parseColor(titleColor));
                int tag;
                if (background == null) {
                    tag = R.drawable.dialog_timer_bt_bg;
                } else {
                    tag = background.intValue();
                }

                if (Build.VERSION.SDK_INT >= 16) {
                    timerText.setBackground(myContext.getResources().getDrawable(tag));
                } else {
                    timerText.setBackgroundDrawable(myContext.getResources().getDrawable(tag));
                }
                break;
        }
        return this;
    }

    //默认显示两个，可配置显示一个按钮, 会隐藏取消按钮
    public GAConfirmDialog isShowSingleButton(boolean isSingle) {
        Button cancelButton = contentView.findViewById(R.id.dialog_bt_cl);
        cancelButton.setVisibility(isSingle ? View.GONE : View.VISIBLE);
        return this;
    }

    public GAConfirmDialog sureButton(String title) {
        sureButton(title, "", null);
        return this;
    }

    public GAConfirmDialog sureButton(String title, String titleColor, Integer background) {
        Button sureButton = contentView.findViewById(R.id.dialog_bt_sure);
        sureButton.setText(StringUtils.isEmpty(title) ? "确定" : title);
        sureButton.setTextColor(StringUtils.isEmpty(titleColor) ? Color.parseColor("#ffffff") : Color.parseColor(titleColor));
        int tag;
        if (background == null) {
            tag = R.drawable.dialog_btnright_bg;
        } else {
            tag = background.intValue();
        }

        if (Build.VERSION.SDK_INT >= 16) {
            sureButton.setBackground(myContext.getResources().getDrawable(tag));
        } else {
            sureButton.setBackgroundDrawable(myContext.getResources().getDrawable(tag));
        }
        return this;
    }

    public GAConfirmDialog cancelButton(String title) {
        cancelButton(title, "", null);
        return this;
    }

    public GAConfirmDialog cancelButton(String title, String titleColor, Integer background) {
        Button cancelButton = contentView.findViewById(R.id.dialog_bt_cl);
        cancelButton.setText(StringUtils.isEmpty(title) ? "取消" : title);
        cancelButton.setTextColor(StringUtils.isEmpty(titleColor) ? Color.parseColor("#ffffff") : Color.parseColor(titleColor));
        int tag;
        if (background == null) {
            tag = R.drawable.dialog_btnleft_bg;
        } else {
            tag = background.intValue();
        }

        if (Build.VERSION.SDK_INT >= 16) {
            cancelButton.setBackground(myContext.getResources().getDrawable(tag));
        } else {
            cancelButton.setBackgroundDrawable(myContext.getResources().getDrawable(tag));
        }
        return this;
    }

    public GAConfirmDialog cancel(View.OnClickListener listener) {
        Button sureButton = contentView.findViewById(R.id.dialog_bt_cl);
        sureButton.setOnClickListener(v -> {
            listener.onClick(v);
            dismiss();
        });
        return this;
    }

    public GAConfirmDialog sure(View.OnClickListener listener) {
        Button cancelButton = contentView.findViewById(R.id.dialog_bt_sure);
        cancelButton.setOnClickListener(v -> {

            if (style.equals(DialogStyle.TIMER)){
                dismiss();
                myOffTimer.cancel();
                timerLinstener.onClick(v);
                return;
            }

            listener.onClick(v);
            dismiss();
        });

        return this;
    }

    //构造器方法
    public GAConfirmDialog(@NonNull Context context, @NonNull DialogStyle style) {
        super(context);
        myContext = context;
        this.style = style;
        initDialog();
    }

    //简单弹出
    public void show(String title, String tip, View.OnClickListener sureLinstener, View.OnClickListener cancelLinstener) {

        setupTitle(title)
                .tip(tip)
                .sure(sureLinstener)
                .cancel(cancelLinstener)
                .show();
        this.show();
    }

    // 弹出操作成功计时
    View.OnClickListener timerLinstener;
    Timer myOffTimer;
    Handler handler;
    public void showTimer(String title, String tip,final int timer, View.OnClickListener sureLinstener){

        setupTitle(title,"#69b978")
                .tip(tip)
                .bgIcon(R.mipmap.success)
                .timerText(timer+"秒","#FFFFFF",R.drawable.dialog_timer_bt_bg)
                .isShowSingleButton(true)
                .sure(sureLinstener)
                .show();

        this.timerLinstener = sureLinstener;

        // 计时器
        myOffTimer = new Timer(true);
        TimerTask timerTask = new TimerTask() {

            int countTime = timer;

            @Override
            public void run() {

                if (countTime > 0){
                    countTime -- ;
                }

                Message msg = new Message();
                msg.what = countTime;
                handler.sendEmptyMessage(countTime);
            }
        };
        myOffTimer.schedule(timerTask,1000,1000);


        // 线程处理器
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                if (msg.what > 0){
                    timerText(msg.what + "秒","#FFFFFF",R.drawable.dialog_timer_bt_bg);
                }else {
                    dismiss();
                    myOffTimer.cancel();
                    timerLinstener.onClick(null);
                }

                super.handleMessage(msg);
            }
        };




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //配置
    public void initDialog() {

        switch (style) {
            case DEFAULT:

                View view = LayoutInflater.from(myContext).inflate(R.layout.dialog_base, null);
                this.contentView = view;
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setCanceledOnTouchOutside(false);
                setCancelable(false);

                setupTitle("提示", "#ff6464")
                        .tip("")
                        .isShowSingleButton(false)
                        .bgIcon(R.mipmap.ask)
                        .sure(v -> {
                        })
                        .cancel(v -> {
                        });
                break;
            case SINGLE:
                break;
            case MULTI:
                break;
            case INPUT:
                break;

            case TIMER:

                View timerView = LayoutInflater.from(myContext).inflate(R.layout.dialog_timer, null);
                this.contentView = timerView;
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                setCanceledOnTouchOutside(false);
                setCancelable(false);
                setupTitle("哎哟我去", "#69b978")
                        .tip("")
                        .bgIcon(R.mipmap.success);
                break;
        }
    }
}
