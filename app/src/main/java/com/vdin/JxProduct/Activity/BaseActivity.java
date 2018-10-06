package com.vdin.JxProduct.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vdin.JxProduct.OSSService.PermissionUtil;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Util.ActivityConllector;
import com.vdin.JxProduct.Util.ToolUtil;

public class BaseActivity extends AppCompatActivity {

    // 进度框
    private ProgressDialog progressDialog;
    // 底部弹窗
    private static Toast toast;
    // 上下文
    public Context mContext;

    /**
     * 视图创建时调用
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityConllector.addActivity(this);
    }

    /**
     * 界面注销时调用的方法
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(this.toString(), "onDestroy");
        ActivityConllector.removeActivity(this);
    }
    /**
     * 设置导航栏标题
     * @param title 导航栏的标题
     */
    protected void setHeaderTitle(String title) {
        View view = findViewById(R.id.mainheader_title);
        view.setVisibility(View.VISIBLE);
        if (view instanceof TextView) {
            ((TextView) view).setText(title);
        }
    }

    /**
     * 设置返回按钮
     * @param btTitle
     */
    protected void setHeaderleftTurnBack(String btTitle) {
        View view = findViewById(R.id.mainheader_leftimg);
        view.setVisibility(View.VISIBLE);
        if (view instanceof Button) {
            ((Button) view).setText(btTitle);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backButtonAction();
                }
            });
        }
    }

    /**
     * 返回按钮点击响应事件
     */
    protected void backButtonAction(){
        finish();
    }

    // 设置返回按钮文字大小
    protected void setHeaderleftTurnBackWZ(int sp) {
        View view = findViewById(R.id.mainheader_leftimg);
        view.setVisibility(View.VISIBLE);
        if (view instanceof Button) {
            ((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,sp);
        }
    }

    /**
     * 显示底部弹窗
     *
     * @param message 弹窗内容
     */
    public void showToastWithMessage(final String message) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在主线程中执行
                if (toast == null){
                    toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                }else {
                    toast.setText(message);
                }
                toast.show();
            }
        });

    }

    /**
     * 显示进度对话框
     *
     * @param message 进度弹窗内容
     */
    public void showProgressDialog(String message) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
                progressDialog.setMessage(message);
                progressDialog.show();

            }
        });
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

            }
        });
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public void fullScreen(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
//                Window window = activity.getWindow();
//                View decorView = window.getDecorView();
//                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
//                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                decorView.setSystemUiVisibility(option);
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
//                //导航栏颜色也可以正常设置
////                window.setNavigationBarColor(Color.TRANSPARENT);
//            } else {
//                Window window = activity.getWindow();
//                WindowManager.LayoutParams attributes = window.getAttributes();
//                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                attributes.flags |= flagTranslucentStatus;
////                attributes.flags |= flagTranslucentNavigation;
//                window.setAttributes(attributes);
//            }
//        }
    }

    /**
     * 设置页面最外层布局 FitsSystemWindows 属性
     *
     * @param activity
     * @param value
     */
    public void setFitsSystemWindows(Activity activity, boolean value) {
//        ViewGroup contentFrameLayout = (ViewGroup) activity.findViewById(android.R.id.content);
//        View parentView = contentFrameLayout.getChildAt(0);
//        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
//            parentView.setFitsSystemWindows(value);
//        }
    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 添加状态栏占位视图
     *
     * @param activity
     */
    private void addStatusViewWithColor(Activity activity, int color) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight());
        statusBarView.setBackgroundColor(color);
        contentView.addView(statusBarView, lp);
    }

    /**
     * dp转px
     *
     * @param dpValue dp
     * @return
     */
    public int dp2px(float dpValue) {
        return ToolUtil.dp2px(this,dpValue);
    }

    /**
     * 点击空白区域，隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                ToolUtil.hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 点击手机返回按键
     */
    @Override
    public void onBackPressed() {
        backButtonAction();
    }

    // 跳转到当前应用的设置界面
    public void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, PermissionUtil.REQUEST_CODE_STORAGE);
    }
}














