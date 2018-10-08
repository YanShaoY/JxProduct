package com.vdin.JxProduct.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.vdin.JxProduct.OSSService.BitmapUtil;
import com.vdin.JxProduct.OSSService.FileUtils;
import com.vdin.JxProduct.OSSService.PhotoUtils;
import com.vdin.JxProduct.R;
import com.vdin.JxProduct.Service.IDCardReadService;
import com.vdin.JxProduct.Util.StringUtils;
import com.vdin.JxProduct.Util.ToolUtil;
import com.vdin.JxProduct.View.GAConfirmDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdCardReadActivity extends BaseActivity implements IDCardReadService.IdCardServiceBlock {

    // 控件声明
    @BindView(R.id.card_number_edit)
    EditText cardNumberEdit;
    @BindView(R.id.card_name_edit)
    EditText cardNameEdit;
    @BindView(R.id.card_phone_edit)
    EditText cardPhoneEdit;
    @BindView(R.id.card_image_button)
    Button cardImageButton;
    @BindView(R.id.card_scene_button)
    Button cardSceneButton;
    @BindView(R.id.next_button_id)
    Button nextButtonId;

    // 现场照片的本地路径
    private String scenePhotoUrl = "";
    // 存储本类实例
    public static Activity idCardActivity = null;
    // 身份证读取服务
    private IDCardReadService myService;
    // 身份证信息模型
    private IDCardReadService.Identityinfo myIdentityinfo;

    /**
     * 视图创建方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card_read);
        ButterKnife.bind(this);

        // 导航栏初始化
        initNavBar();
        // 参数初始化
        initParameter();
        // 测试数据
//        testData();

    }

    /**
     * 导航栏初始化
     */
    private void initNavBar() {
//        fullScreen(this);
//        setFitsSystemWindows(this, true);
        setHeaderleftTurnBack("");
        setHeaderTitle("身份验证");
    }

    /**
     * 初始化参数
     */
    private void initParameter() {
        idCardActivity = this;
        myService = new IDCardReadService(this, this);
    }

    /**
     * 测试数据
     */
    public void testData() {

        cardNumberEdit.setText("511528199501151612");
        cardNameEdit.setText("严少颜");
        cardPhoneEdit.setText("17602887120");

        InputMethodManager imms = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imms.hideSoftInputFromWindow(cardNumberEdit.getWindowToken(), 0);
    }

    /**
     * 现场照片按钮点击响应事件
     */
    @OnClick(R.id.card_scene_button)
    public void onCardSceneButtonClicked() {

        // 判断是否身份证读取
        Drawable drawable = cardImageButton.getBackground();
        if ((drawable instanceof BitmapDrawable)) {
            showToastWithMessage("NFC读取无需拍摄现场照片~");
        }else {
            // 拍摄照片
            PhotoUtils.takePicture(this);
        }
    }

    /**
     * 下一步按钮点击响应事件
     */
    @OnClick(R.id.next_button_id)
    public void onNextButtonIdClicked() {
        // 身份证号码判断
        if (!ToolUtil.personIdValidation(cardNumberEdit.getText().toString().trim())) {
            showToastWithMessage("身份证号码输入不正确");
            return;
        }

        // 姓名判断
        if (StringUtils.isEmpty(cardNameEdit.getText().toString().trim())) {
            showToastWithMessage("请填写正确的姓名");
            return;
        }

        // 电话号码判断
        if (!ToolUtil.isMobileNO(cardPhoneEdit.getText().toString().trim())) {
            showToastWithMessage("请输入正确的手机号码");
            return;
        }

        // 现场照片拍照判断
        if (StringUtils.isEmpty(scenePhotoUrl)) {
            Drawable drawable = cardImageButton.getBackground();
            if (!(drawable instanceof BitmapDrawable)) {
                showToastWithMessage("请拍摄现场照片");
                return;
            }
        }

        // 判断无误，跳转下一个界面
        Intent intent = new Intent(this, InfoRegistActivity.class);
        if (myIdentityinfo == null || StringUtils.isEmpty(myIdentityinfo.customer_identification_number)) {
            myIdentityinfo = new IDCardReadService.Identityinfo();
            myIdentityinfo.reuse = "false";
            myIdentityinfo.idtype = "2";
            myIdentityinfo.customer_identification_number = cardNumberEdit.getText().toString().trim();
            myIdentityinfo.customer_name = cardNameEdit.getText().toString().trim();
            myIdentityinfo.customer_identity_card_photos = scenePhotoUrl;
        }

        // 存储电话号码
        myIdentityinfo.customer_mobile_phone_number = cardPhoneEdit.getText().toString().trim();
        // 若为NFC读取 切已经拍照
        if (myIdentityinfo.idtype.equals("1") && !StringUtils.isEmpty(scenePhotoUrl)){
            // 删除本地路径的原图
            FileUtils.delFileByLocalPath(scenePhotoUrl);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("identityinfo", myIdentityinfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 重写父类返回按钮方法
     */
    @Override
    protected void backButtonAction() {
        GAConfirmDialog dialog = new GAConfirmDialog(this, GAConfirmDialog.DialogStyle.DEFAULT);
        dialog.setupTitle("确定退出?", "#ff6464")
                .tip("退出后将丢失已录入信息")
                .isShowSingleButton(false)
                .bgIcon(R.mipmap.ask)
                .sure(v -> {
                    finish();
                })
                .show();

    }

    /******************************************************** Activity 生命周期 ***********************************************************/

    /**
     * 拍摄照片以后的回传接口
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //拍照 成功
        if (requestCode == PhotoUtils.RESULT_TAKE_CAMERA && resultCode == RESULT_OK) {

            // 获取图片的Bitmap
            Bitmap bitmap = BitmapUtil.compressFile(mContext, PhotoUtils.currPicUrl, 250, 310);
            // 版本适配 设置按钮 将图片传入作为背景
            if (Build.VERSION.SDK_INT >= 16)
                cardSceneButton.setBackground(new BitmapDrawable(getResources(), bitmap));
            else {
                cardSceneButton.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }

            // 用URL字符串存储拍照本地路径
            scenePhotoUrl = PhotoUtils.currPicUrl;
            // 删除本地路径的原图
            FileUtils.delFileByLocalPath(scenePhotoUrl);
            // 并将压缩后的图片存储到本地
            boolean result = BitmapUtil.saveBitmap(bitmap, scenePhotoUrl);
            if (!result) {
                scenePhotoUrl = null;
                showToastWithMessage("图片存储本地失败，请重新拍照");
            }

            cardSceneButton.setCompoundDrawables(null, null, null, null);
            cardSceneButton.setText("");

        } else {
//            Drawable top = getResources().getDrawable(R.mipmap.ksdj_btn_camera_big);
//            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
//            cardSceneButton.setCompoundDrawables(null, top, null, null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myService.startToReadIdCard();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myService.stopToReadIdCard();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        myService.onNewIntent(intent);
    }

    /**
     * 界面注销时 删除本地图片
     */
    @Override
    protected void onDestroy() {
        // 删除本地路径的原图
        if (StringUtils.isEmpty(scenePhotoUrl)) {
            FileUtils.delFileByLocalPath(scenePhotoUrl);
        }
        scenePhotoUrl = null;
        myService.onActivityDestroyed(this);
        super.onDestroy();
    }

    /******************************************************** IDCardReadService.IdCardServiceBlock ***********************************************************/

    // 错误回调
    @Override
    public void onFailure(IDCardReadService service, int readMode, int errorCode) {

        Log.d("IDCardReadService", "onFailure: " + service.toString() + "readMode ：" + readMode + " errorCode :" + errorCode + "\n");

        if (errorCode == 102) {
            showNFCDialogForTag(1);
            return;
        } else if (errorCode == 103) {
            showNFCDialogForTag(2);
            return;
        }

        if (errorCode == 10100 || errorCode == 10141) {
            new AlertDialog.Builder(this)
                    .setTitle("提示").setMessage("读卡失败！")
                    .setPositiveButton("确定", null).show();
            return;
        }

        if (errorCode == 10102) {
            new AlertDialog.Builder(this)
                    .setTitle("提示").setMessage("接收数据超时！")
                    .setPositiveButton("确定", null).show();
            return;
        }

        if (errorCode == 10142) {
            new AlertDialog.Builder(this)
                    .setTitle("提示").setMessage("网络连接错误,请重试！")
                    .setPositiveButton("确定", null).show();
            return;
        }
        if (errorCode == 10143) {
            new AlertDialog.Builder(this)
                    .setTitle("提示").setMessage("服务器忙！")
                    .setPositiveButton("确定", null).show();
            return;
        }

    }

    // 成功回调
    @Override
    public void completeBlock(IDCardReadService service, int readMode, IDCardReadService.Identityinfo info) {
        Log.d("IDCardReadService", "onFailure: " + service.toString() + "readMode ：" + readMode + " info :" + info + "\n");
        // 存储NFC读取数据
        myIdentityinfo = info;
        // 刷新页面数据显示
        reloadNFCDataToView();
    }

    /**
     * 弹窗NFC
     *
     * @param tag 弹窗格式
     */
    private void showNFCDialogForTag(int tag) {
        String titleStr;
        String tipStr;
        if (tag == 1) {
            titleStr = "本机不支持NFC功能";
            tipStr = "请手动输入信息";
        } else {
            titleStr = "NFC尚未开启";
            tipStr = "请前往设置界面开启该功能";
        }

        GAConfirmDialog dialog = new GAConfirmDialog(this, GAConfirmDialog.DialogStyle.DEFAULT);
        dialog.setupTitle(titleStr, "#ff6464")
                .tip(tipStr)
                .isShowSingleButton(true)
                .bgIcon(R.mipmap.ask)
                .show();

    }

    /**
     * 刷新页面数据显示
     */
    private void reloadNFCDataToView() {
        // 若无身份信息 则退出
        if (myIdentityinfo == null || StringUtils.isEmpty(myIdentityinfo.customer_identification_number)) {
            return;
        }

        // 身份证号码
        cardNumberEdit.setText(myIdentityinfo.customer_identification_number);
        cardNumberEdit.setEnabled(false);

        // 姓名
        cardNameEdit.setText(myIdentityinfo.customer_name);
        cardNameEdit.setEnabled(false);

        // 获取图片的Bitmap
        Bitmap bitmap = BitmapUtil.bytes2Bimap(myIdentityinfo.headImgData);

        if (bitmap != null) {
            // 版本适配 设置按钮 将图片传入作为背景
            if (Build.VERSION.SDK_INT >= 16)
                cardImageButton.setBackground(new BitmapDrawable(getResources(), bitmap));
            else {
                cardImageButton.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            cardImageButton.setCompoundDrawables(null, null, null, null);
            cardImageButton.setText("");
            cardImageButton.setClickable(false);


        } else {
            // TODO 如果失败 恢复默认图片
            //            Drawable top = getResources().getDrawable(R.mipmap.ksdj_btn_camera_big);
//            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
//            cardSceneButton.setCompoundDrawables(null, top, null, null);
//            bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.card_border);
//            // 版本适配 设置按钮 将图片传入作为背景
//            if (Build.VERSION.SDK_INT >= 16)
//                cardImageButton.setBackground(new BitmapDrawable(getResources(), bitmap));
//            else  {
//                cardImageButton.setBackgroundDrawable(new BitmapDrawable(bitmap));
//            }
//            cardImageButton.setCompoundDrawables(null, null, null, null);
//            cardImageButton.setText("证件照片");
        }

    }
}

















