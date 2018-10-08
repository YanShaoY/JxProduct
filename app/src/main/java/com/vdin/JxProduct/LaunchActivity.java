package com.vdin.JxProduct;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.vdin.JxProduct.Activity.BaseActivity;
import com.vdin.JxProduct.Activity.GuideActivity;
import com.vdin.JxProduct.Activity.HomeActivity;
import com.vdin.JxProduct.Activity.MainActivity;
import com.vdin.JxProduct.OSSService.PermissionUtil;
import com.vdin.JxProduct.Service.MetaDataService;
import com.vdin.JxProduct.Util.LaunchUtil;

public class LaunchActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 加载启动界面
        setContentView(R.layout.activity_launch);
        // 获取用户存储权限
        boolean result = PermissionUtil.checkExternalStorage(this);
        if (result) {
            // 跳转
            handlerJump();
            //获取元数据
            MetaDataService.getInstance().initMetadata();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.REQUEST_CODE_STORAGE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 手动获取权限
                    showDialogTipUserGoToAppSettting();
                    return;
                }
            }

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

            handlerJump();
            //获取元数据
            MetaDataService.getInstance().initMetadata();
        }
    }

    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("存储权限不可用").setMessage("请在-应用设置-权限-中，允许使用存储权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();

    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, PermissionUtil.REQUEST_CODE_STORAGE);
    }

    // 显示跳转
    private void handlerJump() {

        //设置等待时间，单位为毫秒
        Integer time = 1500;
        // 当计时结束时，跳转
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            // 判断是否第一次启动
            if (LaunchUtil.isFirstLaunch(this)) {

                LaunchUtil.setFirshLaunchFlag(this);

                Intent intent = new Intent(this, GuideActivity.class);
                int[] imageIdArray = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3};
                intent.putExtra("imageIdArray", imageIdArray);
                startActivity(intent);
                finish();

            } else {
                //判断是否已经登录
                if (LaunchUtil.isLogin(this)) {
                    // 跳转主界面
                    Intent mIntent = new Intent(LaunchActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();

                } else {
                    //进入 home界面
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, time);

    }


}
